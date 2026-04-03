/**
 * MessageEncoder prepares data for encryption.
 *
 * Encoding format:
 *      m' = [HEADER] || [MESSAGE] || [HASH]
 *
 * Where:
 *      HEADER = "RBIN"
 *      HASH = SHA-512(message)
 *
 * Purpose:
 *      - Add structure
 *      - Enable validation after decryption
 *      - Eliminate ambiguity (4 roots problem)
 *
 * Pipeline:
 *      String → bytes → hash → concatenate → byte[]
 */

package rabin.encoding;

import rabin.hash.SHA512Hasher;
import rabin.util.ByteUtil;

import java.nio.charset.StandardCharsets;

public class MessageEncoder {

    private static final byte[] HEADER = "RBIN".getBytes(StandardCharsets.UTF_8);

    /**
     * Encodes a message into structured byte format.
     *
     * Steps:
     *      1. Convert message → bytes
     *      2. Compute SHA-512 hash
     *      3. Construct:
     *              HEADER || MESSAGE || HASH
     *
     * @param message Input string
     * @return encoded byte array
     */
    public static byte[] encode(String message) throws Exception {
        byte[] msgBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] hash = SHA512Hasher.hash(msgBytes);

        return ByteUtil.concat(HEADER, msgBytes, hash);
    }

    
    public static byte[] getHash(byte[] messageBytes) throws Exception {
        return SHA512Hasher.hash(messageBytes);
    }
}