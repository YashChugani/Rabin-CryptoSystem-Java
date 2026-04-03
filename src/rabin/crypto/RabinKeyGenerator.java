/**
 * Generates Rabin key pair.
 *
 * Steps:
 *      1. Generate primes p, q
 *      2. Ensure:
 *              p ≡ 3 mod 4
 *              q ≡ 3 mod 4
 *      3. Compute:
 *              n = p * q
 *
 * Output:
 *      PublicKey(n)
 *      PrivateKey(p, q)
 */

package rabin.crypto;

import rabin.model.*;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RabinKeyGenerator {

    public static KeyPair generate(int bits) {
        SecureRandom rand = new SecureRandom();

        BigInteger p, q;

        do {
            p = BigInteger.probablePrime(bits, rand);
        } while (!p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)));

        do {
            q = BigInteger.probablePrime(bits, rand);
        } while (!q.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)));

        BigInteger n = p.multiply(q);

        return new KeyPair(new PublicKey(n), new PrivateKey(p, q));
    }
}