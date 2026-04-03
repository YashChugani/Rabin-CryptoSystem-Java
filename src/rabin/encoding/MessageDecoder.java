/**
 * MessageDecoder validates and extracts original message after decryption.
 *
 * Input:
 *      Candidate root (converted to byte array)
 *
 * Validation steps:
 *      1. Check HEADER
 *      2. Extract MESSAGE
 *      3. Extract HASH
 *      4. Recompute hash
 *      5. Compare hashes
 *
 * Only one of the four Rabin roots will pass.
 *
 * Pipeline:
 *      byte[] → split → validate → String
 */

package rabin.encoding;

import rabin.hash.SHA512Hasher;
import rabin.util.ByteUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageDecoder {

    private static final int HEADER_LEN = 4;
    private static final int HASH_LEN = 64;
    
    /**
     * Attempts to decode a candidate plaintext.
     *
     * Returns:
     *      - Valid message if checks pass
     *      - null if invalid
     *
     * This resolves Rabin ambiguity:
     *      4 roots → only 1 valid
     */
    public static String tryDecode(byte[] data) throws Exception {

        if (data.length < HEADER_LEN + HASH_LEN) return null;

        byte[] header = ByteUtil.subArray(data, 0, HEADER_LEN);

        if (!Arrays.equals(header, "RBIN".getBytes(StandardCharsets.UTF_8))) {
            return null;
        }

        byte[] message = ByteUtil.subArray(data, HEADER_LEN, data.length - HASH_LEN);
        byte[] hash = ByteUtil.subArray(data, data.length - HASH_LEN, data.length);

        byte[] computedHash = SHA512Hasher.hash(message);

        if (!Arrays.equals(hash, computedHash)) {
            return null;
        }

        return new String(message, StandardCharsets.UTF_8);
    }
}