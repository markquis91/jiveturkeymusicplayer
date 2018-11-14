import java.util.ArrayList;
import java.util.List;

public class ListOfArtists {

	private List<Artist> listofartists;

	public ListOfArtists() {
		listofartists = new ArrayList<Artist>();
	}

	
	public int getArtistCount() {
		return listofartists.size();
	}

	public Artist getArtist(int i) {
		return listofartists.get(i);
	}

	public Artist findArtist(String you) {
		for (int i = 0; i < listofartists.size(); i++) {
			if (you.equals(listofartists.get(i).getArtistName())) {
				return listofartists.get(i);
			}
		}
		return null;
	}

	public void addArtist(Artist ar) {
		listofartists.add(ar);
	}

	public void removeArtist(Artist rm) {
		listofartists.remove(rm);
	}
	
	
}
