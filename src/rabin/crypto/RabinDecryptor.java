/**
 * Handles Rabin decryption.
 *
 * Problem:
 *      m^2 ≡ c (mod n)
 *
 * Steps:
 *      1. Compute roots mod p:
 *              rp = c^((p+1)/4) mod p
 *
 *      2. Compute roots mod q:
 *              rq = c^((q+1)/4) mod q
 *
 *      3. Combine using CRT
 *
 * Result:
 *      4 possible plaintexts
 */

package rabin.crypto;

import rabin.math.CRTUtil;

import java.math.BigInteger;

public class RabinDecryptor {

    /**
     * Decrypts ciphertext and returns all 4 possible roots.
     *
     * @param c ciphertext
     * @param p prime
     * @param q prime
     *
     * @return array of 4 roots
     */
    public static BigInteger[] decrypt(BigInteger c, BigInteger p, BigInteger q) {

        BigInteger rp = c.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
        BigInteger rq = c.modPow(q.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), q);

        BigInteger[] roots1 = CRTUtil.combine(rp, rq, p, q);
        BigInteger[] roots2 = CRTUtil.combine(p.subtract(rp), rq, p, q);

        return new BigInteger[]{
                roots1[0], roots1[1],
                roots2[0], roots2[1]
        };
    }
}