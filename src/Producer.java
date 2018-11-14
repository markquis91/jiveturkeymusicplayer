
public class Producer {

	private int producerId;
	private String producername;

	public Producer(int id, String ne) {
		producerId = id;
		producername = ne;
	}

	public int getProducerId() {
		return producerId;
	}
	
	public String getProducerName() {
		return producername;
	}
}
