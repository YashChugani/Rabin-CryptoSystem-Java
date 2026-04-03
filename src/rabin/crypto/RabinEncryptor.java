/**
 * Handles Rabin encryption.
 *
 * Encryption formula:
 *      c = m^2 mod n
 *
 * Where:
 *      m = encoded message
 *      n = public key
 *
 * This is computationally efficient (just squaring).
 */

package rabin.crypto;

import java.math.BigInteger;

public class RabinEncryptor {

    /**
     * Encrypts message using Rabin encryption.
     *
     * @param m encoded message as BigInteger
     * @param n modulus
     * @return ciphertext
     */
    public static BigInteger encrypt(BigInteger m, BigInteger n) {
        return m.modPow(BigInteger.valueOf(2), n);
    }
}