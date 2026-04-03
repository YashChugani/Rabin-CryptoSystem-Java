/**
 * Entry point of the Rabin Cryptosystem application.
 *
 * Execution Flow:
 *
 *      1. Select mode (Demo / Custom)
 *      2. Generate or input keys
 *      3. Input message
 *      4. Encode message:
 *              m' = HEADER || MESSAGE || HASH
 *      5. Convert to BigInteger
 *      6. Encrypt:
 *              c = m'^2 mod n
 *      7. Decrypt:
 *              Compute 4 roots
 *      8. Validate each root
 *      9. Output correct message
 *
 * Demonstrates:
 *      - Rabin encryption/decryption
 *      - CRT usage
 *      - Hash-based validation
 */

package rabin.app;

import rabin.crypto.*;
import rabin.encoding.*;
import rabin.model.*;
import rabin.util.ValidationUtil;
import rabin.util.ConsoleUtil;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    private static int getMaxMessageBytes(BigInteger n) {
        int nBytes = (n.bitLength() + 7) / 8;
        int header = 4;
        int hash = 64;
        return nBytes - header - hash;
    }

    private static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static void printConstraints(BigInteger n) {
        int nBits = n.bitLength();
        int maxBytes = getMaxMessageBytes(n);

        ConsoleUtil.printHeader("SYSTEM CONSTRAINTS");

        ConsoleUtil.info("Modulus size: " + nBits + " bits");
        ConsoleUtil.info("Max message size: " + maxBytes + " bytes");

        if (maxBytes <= 0) {
            ConsoleUtil.error("Current primes are too small for SHA-512");
            ConsoleUtil.info("Suggestion: Use 512-bit primes");
        } else {
            ConsoleUtil.success("Message size is within safe limit (~" + maxBytes + " chars)");
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        ConsoleUtil.printHeader("RABIN CRYPTOSYSTEM");

        System.out.println("1. Demo Mode");
        System.out.println("2. Custom Mode");

        int choice = sc.nextInt();
        sc.nextLine();

        KeyPair kp;

        // ================= KEY GENERATION =================
        ConsoleUtil.printHeader("KEY GENERATION");

        if (choice == 1) {
            ConsoleUtil.step("Generating 512-bit primes...");
            ConsoleUtil.delay();

            kp = RabinKeyGenerator.generate(512);

        } else {

            ConsoleUtil.info("\nEnter prime p:");
            ConsoleUtil.info("Rules:");
            ConsoleUtil.info("- Must be prime");
            ConsoleUtil.info("- Must satisfy p % 4 = 3");
            ConsoleUtil.info("- Recommended: 512-bit");

            BigInteger p = new BigInteger(sc.nextLine());

            ConsoleUtil.info("\nEnter prime q:");
            ConsoleUtil.info("Rules:");
            ConsoleUtil.info("- Must be prime");
            ConsoleUtil.info("- Must satisfy q % 4 = 3");
            ConsoleUtil.info("- Recommended: 512-bit");

            BigInteger q = new BigInteger(sc.nextLine());

            try {
                ConsoleUtil.step("Validating prime p...");
                ConsoleUtil.delay();
                ValidationUtil.validatePrime(p);

                ConsoleUtil.step("Validating prime q...");
                ConsoleUtil.delay();
                ValidationUtil.validatePrime(q);

                ConsoleUtil.success("Prime validation successful");

            } catch (Exception e) {
                ConsoleUtil.error("Invalid input: " + e.getMessage());
                sc.close();
                return;
            }

            kp = new KeyPair(
                    new PublicKey(p.multiply(q)),
                    new PrivateKey(p, q)
            );
        }

        BigInteger p = kp.getPrivateKey().getP();
        BigInteger q = kp.getPrivateKey().getQ();
        BigInteger n = kp.getPublicKey().getN();

        ConsoleUtil.step("Displaying generated keys...");
        ConsoleUtil.delay();

        ConsoleUtil.info("p = " + p);
        ConsoleUtil.info("q = " + q);
        ConsoleUtil.info("n = p*q = " + n);

        // ================= CONSTRAINTS =================
        printConstraints(n);

        // ================= MESSAGE INPUT =================
        ConsoleUtil.printHeader("MESSAGE INPUT");
        ConsoleUtil.info("Enter message:");
        String msg = sc.nextLine();

        // ================= ENCODING =================
        ConsoleUtil.printHeader("ENCODING");

        ConsoleUtil.step("Converting message to bytes");
        ConsoleUtil.delay();

        ConsoleUtil.step("Applying SHA-512 hashing");
        ConsoleUtil.delay();

        ConsoleUtil.step("Constructing: [HEADER][MESSAGE][HASH]");
        ConsoleUtil.delay();

        byte[] encoded = MessageEncoder.encode(msg);
        BigInteger m = new BigInteger(1, encoded);

        // ================= SIZE CHECK =================
        if (m.bitLength() >= n.bitLength()) {

            ConsoleUtil.printHeader("ERROR");

            ConsoleUtil.error("Message too large for modulus");

            ConsoleUtil.info("Required bits: " + m.bitLength());
            ConsoleUtil.info("Available bits: " + n.bitLength());
            ConsoleUtil.info("Max message size: " + getMaxMessageBytes(n) + " bytes");

            ConsoleUtil.info("\nSuggestions:");
            ConsoleUtil.info("- Use larger primes");
            ConsoleUtil.info("- Use Demo Mode");
            ConsoleUtil.info("- Enter smaller message");

            sc.close();
            return;
        }

        // ================= ENCODING VISIBILITY (STRUCTURE-COMPLIANT) =================

        // Show encoded message (hex)
        ConsoleUtil.info("Encoded message (hex): " + toHex(encoded));

        // Show size comparison
        ConsoleUtil.info("Encoded message size: " + m.bitLength() + " bits");
        ConsoleUtil.info("Modulus capacity: " + n.bitLength() + " bits");

        // Get hash
        byte[] messageBytes = msg.getBytes(StandardCharsets.UTF_8);
        byte[] hash = MessageEncoder.getHash(messageBytes);

        // Display hash (truncated for readability)
        String hashHex = toHex(hash);
        String shortHash = hashHex.substring(0, 64) + "...";

        ConsoleUtil.step("SHA-512 hash (truncated):");
        ConsoleUtil.info(shortHash);
        ConsoleUtil.delay();

        // ================= ENCRYPTION =================
        ConsoleUtil.printHeader("ENCRYPTION");

        ConsoleUtil.step("Applying c = m^2 mod n");
        ConsoleUtil.delay();

        BigInteger c = RabinEncryptor.encrypt(m, n);

        ConsoleUtil.info("Ciphertext (hex): " + c.toString(16));
        ConsoleUtil.success("Encryption successful");

        // ================= DECRYPTION =================
        ConsoleUtil.printHeader("DECRYPTION");

        ConsoleUtil.step("Computing rp = c^((p+1)/4) mod p");
        ConsoleUtil.delay();

        ConsoleUtil.step("Computing rq = c^((q+1)/4) mod q");
        ConsoleUtil.delay();

        ConsoleUtil.step("Applying Chinese Remainder Theorem");
        ConsoleUtil.delay();

        BigInteger[] roots = RabinDecryptor.decrypt(c, p, q);

        // ================= ROOT VALIDATION =================
        ConsoleUtil.printHeader("ROOT ANALYSIS");

        for (int i = 0; i < roots.length; i++) {

            ConsoleUtil.printHeader("ROOT " + (i + 1));

            BigInteger r = roots[i];
            ConsoleUtil.info("Value: " + r);

            ConsoleUtil.step("Decoding...");
            ConsoleUtil.delay();
            ConsoleUtil.step("Checking header...");
            ConsoleUtil.delay();

            ConsoleUtil.step("Extracting message + hash...");
            ConsoleUtil.delay();

            ConsoleUtil.step("Verifying SHA-512 hash...");
            ConsoleUtil.delay();

            byte[] data = r.toByteArray();
            String decoded = MessageDecoder.tryDecode(data);

            if (decoded != null) {
                ConsoleUtil.success("Valid message found: " + decoded);
            } else {
                ConsoleUtil.error("Invalid root");
            }
        }

        ConsoleUtil.printHeader("PROCESS COMPLETE");

        sc.close();
    }
}