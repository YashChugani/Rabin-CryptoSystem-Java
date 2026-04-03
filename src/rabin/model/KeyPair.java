/**
 * KeyPair holds both public and private keys.
 *
 * Structure:
 *      PublicKey  → (n)
 *      PrivateKey → (p, q)
 *
 * Used to pass keys cleanly between modules.
 */

package rabin.model;

public class KeyPair {
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public KeyPair(PublicKey pub, PrivateKey priv) {
        this.publicKey = pub;
        this.privateKey = priv;
    }

    public PublicKey getPublicKey() { return publicKey; }
    public PrivateKey getPrivateKey() { return privateKey; }
}