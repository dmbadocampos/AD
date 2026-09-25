import java.io.*;
import java.util.*;

public class Reader {

    public static void main(String[] args) {

        BufferedReader br;
        BufferedWriter bw;

        String originalFile = "original.txt";
        String encryptedFile = "encrypted.txt";
        String decryptedFile = "decrypted.txt";

        System.out.println("""
            Choose an option:
            1. Encrypt
            2. Decrypt
        """);

        int option;
        try (Scanner sc = new Scanner(System.in)) {
            option = sc.nextInt();
        }

        try {

            switch (option) {

                case 1 -> {
                    br = new BufferedReader(new FileReader(originalFile));
                    bw = new BufferedWriter(new FileWriter(encryptedFile));
                    encrypt(br, bw);
                    br.close();
                    bw.close();
                }

                case 2 -> {
                    br = new BufferedReader(new FileReader(encryptedFile));
                    bw = new BufferedWriter(new FileWriter(decryptedFile));
                    decrypt(br, bw);
                    br.close();
                    bw.close();
                }

                default -> throw new AssertionError();

            }

        } catch (IOException e) {

            System.err.println(e.getMessage());

        }

    }

    public static void encrypt(BufferedReader br, BufferedWriter bw) throws IOException {

        String inputLine;
        String outputLine = "";

        while ((inputLine = br.readLine()) != null) {

            for (int i = inputLine.length() - 1; i >= 0; i--) {

                char c = inputLine.charAt(i);
                if (c != ' ') c = (char) ((int) c + 3);
                outputLine += c;

            }

            bw.write(outputLine);
            outputLine = "";
            bw.newLine();

        }

    }

    public static void decrypt(BufferedReader br, BufferedWriter bw) throws IOException {

        String inputLine;
        String outputLine = "";

        while ((inputLine = br.readLine()) != null) {

            for (int i = inputLine.length() - 1; i >= 0; i--) {

                char c = inputLine.charAt(i);
                if (c != ' ') c = (char) ((int) c - 3);
                outputLine += c;

            }

            bw.write(outputLine);
            outputLine = "";
            bw.newLine();

        }

    }

}