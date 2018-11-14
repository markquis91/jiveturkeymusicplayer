import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class MusicPlayerProgram {

	public static void main(String[] args) throws SQLException {

		MusicPlayer player = new MusicPlayer();
		welcomeMessage();
		menuMessage();

		try {
			BufferedReader inputpath = new BufferedReader(new InputStreamReader(System.in));

			player.readAndSaveArtistsFromDatabase(
					"jdbc:mysql://localhost:3306/musicplayer1?autoReconnect=true&useSSL=false", "root", "Scoobydoo12");
			player.readAndSaveSongsProducersAndSongwritersFromDatabase(
					"jdbc:mysql://localhost:3306/musicplayer1?autoReconnect=true&useSSL=false", "root", "Scoobydoo12");
			player.readAndSaveAlbumsFromDatabase(
					"jdbc:mysql://localhost:3306/musicplayer1?autoReconnect=true&useSSL=false", "root", "Scoobydoo12");

			/*
			 * for (int i = 0; i < player.getSongCount(); i++) { if
			 * (player.findSong(i).getSongRating() >= 4.5) { Song song = player.findSong(i);
			 * player.addRatedSongsToPlaylist(song); } }
			 */
			boolean accessgranted = true;
			while (accessgranted) {
				String acceptinginput = inputpath.readLine();
				if (acceptinginput.trim().equalsIgnoreCase("")) {
					emptyUserInputMessage();
				}

				switch (acceptinginput.toLowerCase()) {

				case "albums":
					List<Album> album = player.getAlbums();
					printAlbums(album);
					continue;

				case "artists":
					List<Artist> artist = player.getArtists();
					printArtistNames(artist);
					continue;

				case "songs":
					ListOfSongs songs = player.getSongs();
					printAllSongs(songs);
					continue;
				/*
				 * case "playlist": System.out.println("Ratings Playlist"); for (int i = 0; i <
				 * player.getRatedSongsPlaylistCount(); i++) {
				 * System.out.println(player.displayRatedSongsPlaylist(i).getSongTitle()); }
				 * continue;
				 */
				case "exit":
					exitMessage();
					accessgranted = false;
					continue;
				}

				Album albummatch = player.findAlbum(acceptinginput);
				if (albummatch != null) {
					printSongsForAlbum(albummatch);
					continue;
				}

				Artist artistmatch = player.findArtist(acceptinginput);
				if (artistmatch != null) {
					printArtistAlbums(artistmatch, player);
					continue;
				}

				unrecognizedInputMessage();
			}
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void welcomeMessage() {
		System.out.println("Welcome to Jive Turkey Music Player!");
	}

	private static void menuMessage() {
		System.out.println("Menu options are Albums, Artists, Songs or Playlist. Enter one option to display music.");
	}

	private static void exitMessage() {
		System.out.println("Thanks for using Jive Turkey Music Player!");
	}

	private static void emptyUserInputMessage() {
		System.out.println("Input is empty, please enter a valid option.");
	}

	private static void unrecognizedInputMessage() {
		System.out.println("Your input is not recognized");
	}

	
	private static void printAllSongs(ListOfSongs songs) {
		for (int i = 0; i < songs.getSongCount(); i++) {
			Song song = songs.getSong(i);
			printSong(song);
		}
	}

	private static void printSong(Song song) {
		System.out.println(song.getTitle() + " " + song.getLength() + " " + song.getRating());
	}

	private static void printArtist(Artist artists) {
		System.out.println(artists.getArtistName());
	}

	private static void printArtistNames(List<Artist> artists) {
		for (int i = 0; i < artists.size(); i++) {
			Artist artist = artists.get(i);
			printArtist(artist);
		}
	}

	private static void printAlbums(List<Album> albums) {
		for (int i = 0; i < albums.size(); i++) {
			System.out.println(albums.get(i).getAlbumTitle()+" "+albums.get(i).getAlbumLength());
		}
	}

	private static void printSongsForAlbum(Album albummatch) {
		System.out.println(albummatch.getAlbumTitle());
		ListOfSongs songs = albummatch.getListOfSongs();
		for (int i = 0; i < songs.getSongCount(); i++) {
			Song song = songs.getSong(i);
			printSong(song);
		}

	}

	// todo : make it compile and work before code review.
	private static void printArtistAlbums(Artist artistmatch, MusicPlayer player) throws SQLException {
		System.out.println(artistmatch.getArtistName());
		List<Album> albums = player.findAlbumsForArtist(artistmatch);
		for (int i = 0; i < albums.size(); i++) {
			Album album = albums.get(i);
			System.out.println(album.getAlbumTitle() + " " + album.getAlbumLength());
		}

	}

}
