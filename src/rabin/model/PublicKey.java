/**
 * PublicKey class represents the public key in the Rabin Cryptosystem.
 *
 * Public Key:
 *      n = p * q
 *
 * Used in encryption:
 *      c = m^2 mod n
 *
 * This class is immutable to ensure safety.
 */

package rabin.model;

import java.math.BigInteger;

public class PublicKey {
    private final BigInteger n;

    public PublicKey(BigInteger n) {
        this.n = n;
    }

    public BigInteger getN() {
        return n;
    }
}