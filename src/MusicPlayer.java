import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicPlayer {
	private List<Artist> allArtists;
	private List<Album> allAlbums;
	private ListOfSongs allSongs;
	private List<Song> ratedSongsPlaylist;
	public Artist newArtist;
	private List<Producer> allProducers;
	private List<SongWriter> allSongWriters;

	public MusicPlayer(List<Artist> artists, List<Album> albums, ListOfSongs songs, List<Song> lists) {
		allArtists = artists;
		allAlbums = albums;
		allSongs = songs;
		ratedSongsPlaylist = lists;
	}

	public MusicPlayer() {
	}

	public void addRatedSongsToPlaylist(Song song) {
		ratedSongsPlaylist.add(song);
	}

	public int getRatedSongsPlaylistCount() {
		return ratedSongsPlaylist.size();
	}

	public Song displayRatedSongsPlaylist(int i) {
		return ratedSongsPlaylist.get(i);
	}


	public List<Album> findAlbumsForArtist(Artist artist) throws SQLException {
		int artistId = artist.getArtistId();
		List<Album> album = constructAlbumListFromArtistId(artistId);
		return album;
	}

	private List<Album> constructAlbumListFromArtistId(int artistId) throws SQLException {
		Connection myConnection = getConnection("jdbc:mysql://localhost:3306/musicplayer1?autoReconnect=true&useSSL=false", "root", "Scoobydoo12");
		Statement myStatement = getMyStatement(myConnection);
		ResultSet albumsForArtist = getResultSet(myStatement, "select ArtistId, AlbumId from artist_albums where ArtistId = "+artistId);
		List<Album> albumList = new ArrayList<Album>();
		
		
		while (albumsForArtist.next()) {
			int artistIdFromDb = albumsForArtist.getInt(1);
			int albumIdFromDb = albumsForArtist.getInt(2);
			albumList = findAndReturnListOfAlbums(artistIdFromDb, albumIdFromDb, albumList);
		}
		return albumList;
	}

	private List<Album> findAndReturnListOfAlbums(int artistId, int albumId, List<Album> albumList) throws SQLException {
		for (int i = 0; i < allAlbums.size(); i++) {
			if (albumId == allAlbums.get(i).getAlbumId()) {
				albumList.add(allAlbums.get(i));
			}
		}
		return albumList;
	}

	
	public Artist findArtistByName(String primaryArtist) {
		for (int i = 0; i < allArtists.size(); i++) {
			if (primaryArtist.equals(allArtists.get(i).getArtistName())) {
				return allArtists.get(i);
			}
		}
		return null;
	}

	public Album findAlbum(String acceptinginput) {
		for (int i = 0; i < allAlbums.size(); i++) {
			if (acceptinginput.equalsIgnoreCase(allAlbums.get(i).getAlbumTitle())) {
				return allAlbums.get(i);
			}
		}
		return null;
	}

	public Producer findProducer(String prod) {
		for (int i = 0; i < allProducers.size(); i++) {
			if (prod.equals(allProducers.get(i).getProducerName())) {
				return allProducers.get(i);
			}
		}
		return null;
	}

	public void addToProducerList(Producer producer) {
		allProducers.add(producer);
	}

	public SongWriter findSongWriter(String songwriter) {
		for (int i = 0; i < allSongWriters.size(); i++) {
			if (songwriter.equals(allSongWriters.get(i).getSongWriterName())) {
				return allSongWriters.get(i);
			}
		}
		return null;
	}

	public void addToSongWriterList(SongWriter songwriter) {
		allSongWriters.add(songwriter);
	}

	public int getAlbumSize() {
		return allAlbums.size();
	}

	public Album findAlbum(int i) {
		return allAlbums.get(i);
	}

	public int getSongCount() {
		return allSongs.getSongCount();
	}

	public Song findSong(int i) {
		return allSongs.getSong(i);
	}

	public int getArtistSize() {
		return allArtists.size();
	}

	public Artist getArtist(int i) {
		return allArtists.get(i);
	}

	public Song findSong(String songtitle) {
		for (int i = 0; i < allSongs.getSongCount(); i++) {
			if (songtitle.equals(allSongs.getSong(i).getTitle())) {
				return allSongs.getSong(i);
			}
		}
		return null;
	}

	public Artist findArtist(String acceptinginput) {
		for (int i = 0; i < allArtists.size(); i++) {
			if (acceptinginput.equals(allArtists.get(i).getArtistName())) {
				return allArtists.get(i);
			}
		}
		return null;
	}

	public List<Album> getAlbums() {
		return Collections.unmodifiableList(allAlbums);
	}

	public List<Artist> getArtists() {
		return Collections.unmodifiableList(allArtists);
	}

	public ListOfSongs getSongs() {
		return allSongs;
	}

	private Artist createArtistObject(ResultSet artistResults) throws SQLException {
		int artistId = artistResults.getInt(1);
		String artist = artistResults.getString(2);
		Artist artistObject = new Artist(artist, artistId);
		return artistObject;
	}

	private Album createAlbum(ResultSet albumResults) throws SQLException {
		int albumId = albumResults.getInt(1);
		String albumTitle = albumResults.getString(2);
		int albumLength = albumResults.getInt(3);
		int albumArtist = albumResults.getInt(4);
		ListOfSongs songList = getSongsForAlbumId(albumId);
		Artist artist = findArtist(albumArtist);
		Album album = new Album(albumId, artist, albumTitle, albumLength, songList);
		return album;
	}

		private Artist findArtist(int artistId) {
		for (int i = 0; i < allArtists.size(); i++) {
			if (artistId == allArtists.get(i).getArtistId()) {
				Artist artist = allArtists.get(i);
				return artist;
			}
		}
		return newArtist;
	}

	private ListOfSongs getSongsForAlbumId(int albumId) throws SQLException {
		Connection myConnection = getConnection(
				"jdbc:mysql://localhost:3306/musicplayer1?autoReconnect=true&useSSL=false", "root", "Scoobydoo12");
		Statement myStatement = getMyStatement(myConnection);
		ResultSet albumSongs = getResultSet(myStatement, "select * from album_songs where AlbumId = " + albumId);
		ListOfSongs albumSongsList = new ListOfSongs();

		while (albumSongs.next()) {
			int albumIdDb = albumSongs.getInt(1);
			int songIdDb = albumSongs.getInt(2);
			findSongAndAddToList(albumIdDb, songIdDb, albumSongsList);
		}
		closeConnections(myConnection, myStatement, albumSongs);
		return albumSongsList;

	}

	private void findSongAndAddToList(int albumIdDb, int songIdDb, ListOfSongs albumSongsList) {
		for (int i = 0; i < allSongs.getSongCount(); i++) {
			if (songIdDb == allSongs.getSong(i).getSongId()) {
				albumSongsList.addSongToListOfSongs(allSongs.getSong(i));
			}
		}

	}

	private Artist getArtistFromId(int artistId) {
		for (int i = 0; i < allArtists.size(); i++) {
			if (artistId == allArtists.get(i).getArtistId()) {
				return allArtists.get(i);
			}
		}
		return null;
	}

	private SongWriter getSongwriterFromId(int songwriterId) {
		for (int i = 0; i < allSongWriters.size(); i++) {
			if (songwriterId == allSongWriters.get(i).getSongwriterId()) {
				return allSongWriters.get(i);
			}
		}
		return null;
	}

	private Producer getProducerFromId(int producerId) {
		for (int i = 0; i < allProducers.size(); i++) {
			if (producerId == allProducers.get(i).getProducerId()) {
				return allProducers.get(i);
			}
		}
		return null;
	}

	public void closeConnections(Connection myConnection, Statement myStatement, ResultSet myResultSet)
			throws SQLException {
		myConnection.close();
		myStatement.close();
		myResultSet.close();
	}

	public Producer createProducerObject(ResultSet producerResults) throws SQLException {
		int producerId = producerResults.getInt(1);
		String producer = producerResults.getString(2);
		Producer producerObject = new Producer(producerId, producer);
		return producerObject;
	}

	private SongWriter createSongwriterObject(ResultSet songwriterResults) throws SQLException {
		int songwriterId = songwriterResults.getInt(1);
		String songwriter = songwriterResults.getString(2);
		SongWriter songwriterObject = new SongWriter(songwriterId, songwriter);
		return songwriterObject;
	}

	private void nullObjectException() {
		System.out.println("Your object is null!");
	}

	public Connection getConnection(String mysql, String username, String password) throws SQLException {
		Connection myConn = DriverManager.getConnection(mysql, username, password);
		return myConn;
	}

	public Statement getMyStatement(Connection mySqlConnection) throws SQLException {
		Statement myStmt = mySqlConnection.createStatement();
		return myStmt;
	}

	public ResultSet getResultSet(Statement myStatement, String mySqlQuery) throws SQLException {
		ResultSet producerResults = myStatement.executeQuery(mySqlQuery);
		return producerResults;
	}

	public void readAndSaveArtistsFromDatabase(String mysql, String username, String password) throws SQLException {
		Connection myConnection = DriverManager.getConnection(mysql, username, password);
		Statement myStatement = myConnection.createStatement();
		ResultSet artistResults = myStatement.executeQuery("select * from artists;");
		allArtists = new ArrayList<Artist>();

		while (artistResults.next()) {
			Artist artistObject = createArtistObject(artistResults);
			if (artistObject == null) {
				nullObjectException();
				break;
			}
			allArtists.add(artistObject);
		}
		closeConnections(myConnection, myStatement, artistResults);
	}

	public void readAndSaveSongsProducersAndSongwritersFromDatabase(String mysql, String username, String password)
			throws SQLException {
		Connection myConnection = getConnection(mysql, username, password);
		Statement myStatement = getMyStatement(myConnection);
		ResultSet producerResults = getResultSet(myStatement, "select * from producer;");
		allProducers = new ArrayList<Producer>();

		while (producerResults.next()) {
			Producer producer = createProducerObject(producerResults);
			if (producer == null) {
				nullObjectException();
				break;
			}
			allProducers.add(producer);
		}

		allSongWriters = new ArrayList<SongWriter>();
		ResultSet songwriterResults = getResultSet(myStatement, "select * from songwriter");
		while (songwriterResults.next()) {
			SongWriter songwriterObject = createSongwriterObject(songwriterResults);
			if (songwriterObject == null) {
				nullObjectException();
				break;
			}
			allSongWriters.add(songwriterObject);
		}

		allSongs = new ListOfSongs();
		ResultSet songResults = getResultSet(myStatement, "select * from songs");
		while (songResults.next()) {
			int songId = songResults.getInt(1);
			String songTitle = songResults.getString(2);
			int songLength = songResults.getInt(3);
			int songRating = songResults.getInt(4);
			int producerId = songResults.getInt(5);
			Producer producerObject = getProducerFromId(producerId);
			int songwriterId = songResults.getInt(6);
			SongWriter songwriterObject = getSongwriterFromId(songwriterId);
			int artistId = songResults.getInt(7);
			Artist artistObject = getArtistFromId(artistId);

			Song songObject = new Song(songId, artistObject, songTitle, songLength, songRating, producerObject,
					songwriterObject);
			allSongs.addSong(songObject);
		}
		closeConnections(myConnection, myStatement, producerResults);
	}

	public void readAndSaveAlbumsFromDatabase(String mysql, String username, String password) throws SQLException {
		Connection myConnection = getConnection(mysql, username, password);
		Statement myStatement = getMyStatement(myConnection);
		ResultSet albumResults = getResultSet(myStatement, "select * from albums;");
		allAlbums = new ArrayList<Album>();

		while (albumResults.next()) {
			Album album = createAlbum(albumResults);
			allAlbums.add(album);
		}
		closeConnections(myConnection, myStatement, albumResults);
	}
}