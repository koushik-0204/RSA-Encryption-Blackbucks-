import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private JFrame frame;
    private JTextArea inputArea, outputArea;
    private JButton generateKeyBtn, encryptBtn, decryptBtn;

    private RSAKeyPair keyPair;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }

    public MainGUI() {
        frame = new JFrame("RSA Only Encryption Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        inputArea = new JTextArea("Enter your text here...");
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JPanel buttonPanel = new JPanel();
        generateKeyBtn = new JButton("Generate 1024-bit RSA Keys");
        encryptBtn = new JButton("Encrypt (RSA)");
        decryptBtn = new JButton("Decrypt (RSA)");

        buttonPanel.add(generateKeyBtn);
        buttonPanel.add(encryptBtn);
        buttonPanel.add(decryptBtn);

        frame.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        frame.add(new JScrollPane(outputArea), BorderLayout.SOUTH);
        frame.add(buttonPanel, BorderLayout.NORTH);

        attachActions();

        frame.setVisible(true);
    }

    private void attachActions() {
        generateKeyBtn.addActionListener(e -> {
            keyPair = RSAKeyPair.generate(1024);
            JOptionPane.showMessageDialog(frame, "RSA 1024-bit keys generated!");
        });

        encryptBtn.addActionListener(e -> {
            if (keyPair == null) {
                JOptionPane.showMessageDialog(frame, "Please generate keys first!");
                return;
            }
            String plain = inputArea.getText();
            long start = System.currentTimeMillis();
            String cipher = RSAUtil.encrypt(plain, keyPair.getPublicKey());
            long end = System.currentTimeMillis();
            outputArea.setText(cipher + "\n\nTime taken: " + (end - start) + " ms");
        });

        decryptBtn.addActionListener(e -> {
            if (keyPair == null) {
                JOptionPane.showMessageDialog(frame, "Please generate keys first!");
                return;
            }
            String cipher = inputArea.getText().trim();
            long start = System.currentTimeMillis();
            String decrypted = RSAUtil.decrypt(cipher, keyPair.getPrivateKey());
            long end = System.currentTimeMillis();
            outputArea.setText(decrypted + "\n\nTime taken: " + (end - start) + " ms");
        });
    }
}
