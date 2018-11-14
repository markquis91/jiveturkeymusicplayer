
public class Song {
	private int songId;
	private Artist songartist;
	private String songtitle;
	private double songlength;
	private double songrating;
	private Producer songproducer;
	private SongWriter songwriter;
	
	public Song(int id, Artist sa, String title, double sl, double sr, Producer sp, SongWriter sw) {
		songId = id;
		songartist = sa;
		songtitle = title;
		songlength = sl;
		songrating = sr;
		songproducer = sp;
		songwriter = sw;
	}
	
	public Song() {
		
	}
		
	public int getSongId() {
		return songId;
	}
	
	public Artist getArtist() {
		return songartist;
	}
	
	public String getTitle() {
		return songtitle;
	}

	public double getLength() {
		return songlength;
	}

	public double getRating() {
		return songrating;
	}
	
	public Producer getProducer() {
		return songproducer;
	}
	
	public SongWriter getSongWriter() {
		return songwriter;
	}
	
}

