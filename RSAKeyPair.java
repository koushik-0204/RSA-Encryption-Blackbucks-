import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyPair {
    public static class PublicKey {
        public BigInteger e, n;

        public PublicKey(BigInteger e, BigInteger n) {
            this.e = e;
            this.n = n;
        }
    }

    public static class PrivateKey {
        public BigInteger d, n;

        public PrivateKey(BigInteger d, BigInteger n) {
            this.d = d;
            this.n = n;
        }
    }

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSAKeyPair(PublicKey pub, PrivateKey priv) {
        this.publicKey = pub;
        this.privateKey = priv;
    }

    public static RSAKeyPair generate(int bitLength) {
        SecureRandom rnd = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitLength / 2, rnd);
        BigInteger q = BigInteger.probablePrime(bitLength / 2, rnd);
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(65537);
        BigInteger d = e.modInverse(phi);
        return new RSAKeyPair(new PublicKey(e, n), new PrivateKey(d, n));
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
