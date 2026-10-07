import java.util.Scanner;

public class Reader {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int option;

        do {

            option = menu();

            switch (option) {

                case 1 -> addGame();
                case 2 -> listGames();
                case 3 -> searchGame();
                case 4 -> updateGame();
                case 5 -> deleteGame();

            }

            System.out.println();

        } while (option != 6);

    }

    public static int menu() {

        System.out.println("""
        Choose an option:
            1. Add game
            2. List games
            3. Search game
            4. Update game
            5. Delete game
            6. Exit
        """);

        while (true) {

            int option = readInt("Option: ");
            if (option >= 1 && option <= 6) {
                return option;
            }
            System.out.println("Error: choose an option from 1 to 6.");

        }

    }

    public static void addGame() {

        String name = readText("Name: ");
        String genre = readText("Genre: ");
        int releaseYear = readInt("Release year: ");
        String platform = readText("Platform: ");
        double cost = readDouble("Cost: ");
        GameManager.addGame(name, genre, releaseYear, platform, cost);
        System.out.println("Game added successfully");

    }

    public static void listGames() {

        System.out.println(GameManager.getGameList());

    }

    public static void searchGame() {

        String name = readText("Name of the game to search: ");
        Game game = GameManager.findGame(name);

        if (game != null) {
            System.out.println(game);
        } else {
            System.out.println("Game not found");
        }

    }

    public static void updateGame() {

        String name = readText("Name of the game to update: ");
        Game game = GameManager.findGame(name);

        if (game == null) {
            System.out.println("Game not found");
            return;
        }

        System.out.println("Enter the new game details:");
        String newName = readText("Name: ");
        String genre = readText("Genre: ");
        int releaseYear = readInt("Release year: ");
        String platform = readText("Platform: ");
        double cost = readDouble("Cost: ");
        if (GameManager.updateGame(name, newName, genre, releaseYear, platform, cost)) {
            System.out.println("Game updated");
        } else {
            System.out.println("Game not found");
        }

    }

    public static void deleteGame() {

        String name = readText("Name of the game to delete: ");

        if (GameManager.deleteGame(name)) {
            System.out.println("Game deleted");
        } else {
            System.out.println("Game not found");
        }

    }

    private static String readText(String prompt) {

        while (true) {

            System.out.print(prompt);
            String value = sc.nextLine();
            if (!value.isBlank()) {
                return value;
            }
            System.out.println("Error: value cannot be empty");

        }

    }

    private static int readInt(String prompt) {

        while (true) {

            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: enter a valid whole number");
            }

        }

    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: enter a valid number");
            }
        }
    }
}