import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GameManager {

	private static final Path DATA_FILE = Path.of("videogames.dat");
	private static final ArrayList<Game> games = new ArrayList<>();

	static {
		try {
			games.addAll(loadGames());
		} catch (IOException e) {
			throw new ExceptionInInitializerError(new UncheckedIOException(
					"Could not load games from " + DATA_FILE, e));
		}
	}

	public static void addGame(String name, String genre, int releaseYear, String platform, double cost) {

		Game game = new Game(name, genre, releaseYear, platform, cost);
		ArrayList<Game> updatedGames = new ArrayList<>(games);
		updatedGames.add(game);
		saveGames(updatedGames);
		replaceGames(updatedGames);

	}

	public static boolean updateGame(String currentName, String name, String genre, int releaseYear, String platform, double cost) {

		int gameIndex = findGameIndex(currentName);
		if (gameIndex == -1) {
			return false;
		}

		ArrayList<Game> updatedGames = new ArrayList<>(games);
		updatedGames.set(gameIndex, new Game(name, genre, releaseYear, platform, cost));
		saveGames(updatedGames);
		replaceGames(updatedGames);
		return true;

	}

	public static Game findGame(String name) {

		int gameIndex = findGameIndex(name);
		return gameIndex == -1 ? null : games.get(gameIndex);

	}

	public static boolean deleteGame(String name) {

		int gameIndex = findGameIndex(name);
		if (gameIndex != -1) {
			ArrayList<Game> updatedGames = new ArrayList<>(games);
			updatedGames.remove(gameIndex);
			saveGames(updatedGames);
			replaceGames(updatedGames);
			return true;
		}

		return false;

	}

	public static String getGameList() {

		if (games.isEmpty()) {
			return "No games have been added yet.";
		}

		StringBuilder gameList = new StringBuilder("Games:");

		for (int i = 0; i < games.size(); i++) {
			gameList.append("\n").append(i + 1).append(". ").append(games.get(i).getName());
		}

		return gameList.toString();

	}

	private static int findGameIndex(String name) {
		for (int i = 0; i < games.size(); i++) {
			if (games.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	private static void replaceGames(List<Game> updatedGames) {
		games.clear();
		games.addAll(updatedGames);
	}

	private static ArrayList<Game> loadGames() throws IOException {

		if (!Files.exists(DATA_FILE) || Files.size(DATA_FILE) == 0) {
			return new ArrayList<>();
		}

		try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(DATA_FILE))) {

			Object data = input.readObject();
			if (!(data instanceof ArrayList<?> storedGames)) {
				throw new IOException("Unexpected data format in " + DATA_FILE);
			}

			ArrayList<Game> loadedGames = new ArrayList<>(storedGames.size());
			for (Object storedGame : storedGames) {
				if (!(storedGame instanceof Game game)) {
					throw new IOException("Invalid game entry in " + DATA_FILE);
				}
				loadedGames.add(game);
			}
			return loadedGames;

		} catch (ClassNotFoundException e) {

			throw new IOException("Could not read games from " + DATA_FILE, e);

		} catch (EOFException e) {

			throw new IOException("Incomplete data in " + DATA_FILE, e);

		}

	}

	private static void saveGames(List<Game> gamesToSave) {

		Path absoluteDataFile = DATA_FILE.toAbsolutePath();
		Path parent = absoluteDataFile.getParent();
		Path temporaryFile = null;

		try {
	
			temporaryFile = Files.createTempFile(parent, "videogames-", ".tmp");
			try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(temporaryFile))) {
				output.writeObject(new ArrayList<>(gamesToSave));
			}

			try {
				Files.move(temporaryFile, absoluteDataFile, StandardCopyOption.ATOMIC_MOVE,
						StandardCopyOption.REPLACE_EXISTING);
			} catch (AtomicMoveNotSupportedException e) {
				Files.move(temporaryFile, absoluteDataFile, StandardCopyOption.REPLACE_EXISTING);
			}
	
		} catch (IOException e) {

			throw new UncheckedIOException("Could not save games to " + DATA_FILE, e);

		} finally {

			if (temporaryFile != null) {

				try {
					Files.deleteIfExists(temporaryFile);
				} catch (IOException e) {
					throw new UncheckedIOException("Could not remove temporary file " + temporaryFile, e);
				}

			}

		}

	}

}
