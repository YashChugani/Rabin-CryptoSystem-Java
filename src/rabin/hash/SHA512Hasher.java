/**
 * SHA512Hasher provides hashing functionality using SHA-512.
 *
 * Hash Function:
 *      H(m) = SHA-512(m)
 *
 * Output:
 *      512-bit (64 bytes)
 *
 * Used for:
 *      - Message integrity
 *      - Removing ambiguity in Rabin decryption
 *
 * Pipeline:
 *      message → bytes → SHA-512 → hash
 */

package rabin.hash;

import java.security.MessageDigest;

public class SHA512Hasher {
    
    /**
     * Computes SHA-512 hash of input data.
     *
     * @param data Input byte array
     * @return 64-byte hash
     */
    public static byte[] hash(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return md.digest(data);
    }
}