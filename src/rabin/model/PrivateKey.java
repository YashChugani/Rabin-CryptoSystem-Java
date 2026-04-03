/**
 * PrivateKey class stores the secret primes used in Rabin Cryptosystem.
 *
 * Private Key:
 *      p, q where:
 *          p ≡ 3 mod 4
 *          q ≡ 3 mod 4
 *
 * These are required for decryption:
 *      m^2 ≡ c (mod n)
 *
 * Used to compute square roots modulo p and q.
 */

package rabin.model;

import java.math.BigInteger;

public class PrivateKey {
    private final BigInteger p;
    private final BigInteger q;

    public PrivateKey(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
    }

    public BigInteger getP() { return p; }
    public BigInteger getQ() { return q; }
}