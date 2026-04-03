/**
 * Validation utilities for cryptographic constraints.
 *
 * Ensures:
 *      - Number is prime
 *      - Number satisfies:
 *              p ≡ 3 mod 4
 *
 * This condition is required for Rabin decryption:
 *
 *      sqrt(c) mod p = c^((p+1)/4) mod p
 */

package rabin.util;

import java.math.BigInteger;

public class ValidationUtil {

    /**
     * Validates if number is a valid Rabin prime.
     *
     * Conditions:
     *      1. Prime
     *      2. p mod 4 == 3
     */
    public static void validatePrime(BigInteger x) throws Exception {
        if (!x.isProbablePrime(50)) {
            throw new Exception("Number is not prime.");
        }
        if (!x.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
            throw new Exception("Prime must be ≡ 3 mod 4.");
        }
    }
}