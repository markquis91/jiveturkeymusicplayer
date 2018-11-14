import java.util.ArrayList;
import java.util.List;

public class ListOfSongs {

	private List<Song> listOfSongs;

	public ListOfSongs() {
		listOfSongs = new ArrayList<Song>();
	}


	public void addSong(Song s) {
		listOfSongs.add(s);
	}

	public void removeSong(Song r) {
		listOfSongs.remove(r);
	}

	public int getSongCount() {
		return listOfSongs.size();
	}

	public Song getSong(int i) {
		return listOfSongs.get(i);
	}

	public void addSongToListOfSongs(Song songObj) {
		listOfSongs.add(songObj);
		
	}

	

}