import java.io.*;

public class Reader {

    public static void main(String[] args) {

        int charAmount = 0;
        int wordAmount = 1;
        int lineAmount = 1;
        int[] charFrequency = new int[65536];
        int highestFrequency = 0;
        char mostFrequentChar = ' ';

        try (FileReader fr = new FileReader("text.txt")) {

            int c;

            while ((c = fr.read()) != -1) {

                char foundChar = (char) c;

                switch (foundChar) {

                    case '\r', '\t' -> {}

                    case ' ' -> {
                        wordAmount += 1;
                    }

                    case '\n' -> {
                        wordAmount += 1;
                        lineAmount += 1;
                    }

                    default -> {
                        charAmount += 1;
                        charFrequency[c] += 1;
                    }

                }

            }

            for (int i = 0; i < charFrequency.length; i++) {

                if (charFrequency[i] > highestFrequency) {

                    mostFrequentChar = (char) i;
                    highestFrequency = charFrequency[i];

                }

            }

            System.out.printf("""
                character amount: %d
                word amount: %d
                line amount: %d
                most frequent character: %c
            """, charAmount, wordAmount, lineAmount, mostFrequentChar);

        } catch (IOException e) {

            System.err.println(e.getMessage());

        }

    }

}