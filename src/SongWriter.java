
public class SongWriter {

	private int songwriterId;
	private String songwritername;
	
	
	public SongWriter(int id, String sw) {
		songwriterId = id;
		songwritername = sw;
	}
	
	public int getSongwriterId() {
		return songwriterId;
	}
	
	public String getSongWriterName() {
		return songwritername;
	}
	
}
