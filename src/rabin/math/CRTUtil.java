/**
 * CRTUtil implements the Chinese Remainder Theorem (CRT).
 *
 * Used in Rabin decryption to combine roots:
 *
 * Given:
 *      x ≡ rp (mod p)
 *      x ≡ rq (mod q)
 *
 * Solution:
 *      x = (rp * q * q⁻¹ mod p + rq * p * p⁻¹ mod q) mod n
 *
 * Produces two solutions:
 *      x and (n - x)
 */

package rabin.math;

import java.math.BigInteger;

public class CRTUtil {

    /**
     * Combines solutions using CRT.
     *
     * @param rp root modulo p
     * @param rq root modulo q
     * @param p prime
     * @param q prime
     *
     * @return two possible combined roots
     */
    public static BigInteger[] combine(
            BigInteger rp, BigInteger rq,
            BigInteger p, BigInteger q) {

        BigInteger n = p.multiply(q);

        BigInteger m1 = q.multiply(q.modInverse(p));
        BigInteger m2 = p.multiply(p.modInverse(q));

        BigInteger x = m1.multiply(rp).add(m2.multiply(rq)).mod(n);
        BigInteger y = n.subtract(x);

        return new BigInteger[]{x, y};
    }
}