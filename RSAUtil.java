import java.math.BigInteger;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

public class RSAUtil {

    public static String encrypt(String plainText, RSAKeyPair.PublicKey key) {
        byte[] data = plainText.getBytes();
        int blockSize = (key.n.bitLength() - 1) / 8;
        List<String> encryptedBlocks = new ArrayList<>();

        for (int i = 0; i < data.length; i += blockSize) {
            int len = Math.min(blockSize, data.length - i);
            byte[] block = new byte[len];
            System.arraycopy(data, i, block, 0, len);

            BigInteger m = new BigInteger(1, block);
            BigInteger c = m.modPow(key.e, key.n);
            String encoded = Base64.getEncoder().encodeToString(c.toByteArray()).replaceAll("\\s+", "");
            encryptedBlocks.add(encoded);
        }

        return String.join(":", encryptedBlocks);
    }

    public static String decrypt(String cipherText, RSAKeyPair.PrivateKey key) {
        try {
            cipherText = cipherText.replaceAll("\\s+", "");
            String[] blocks = cipherText.split(":");
            List<Byte> result = new ArrayList<>();

            for (String block : blocks) {
                byte[] blockBytes = Base64.getDecoder().decode(block);
                BigInteger c = new BigInteger(1, blockBytes);
                BigInteger m = c.modPow(key.d, key.n);
                byte[] plainBlock = m.toByteArray();

                if (plainBlock.length > 1 && plainBlock[0] == 0) {
                    byte[] tmp = new byte[plainBlock.length - 1];
                    System.arraycopy(plainBlock, 1, tmp, 0, tmp.length);
                    plainBlock = tmp;
                }

                for (byte b : plainBlock) {
                    result.add(b);
                }
            }

            byte[] plainBytes = new byte[result.size()];
            for (int i = 0; i < result.size(); i++) {
                plainBytes[i] = result.get(i);
            }

            return new String(plainBytes);
        } catch (Exception e) {
            return "Decryption failed!";
        }
    }
}
