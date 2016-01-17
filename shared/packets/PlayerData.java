package packets;


public class PlayerData extends PacketData {

	private String username;
	private int x;
	private int y;
	
	public PlayerData(String username, int x, int y) {
		this.username = username;
		this.x = x;
		this.y = y;
	}
	
	public PlayerData(byte[] data) {
		byte[] id = new byte[4];
		System.arraycopy(data, 0, id, 0, 4);
		PacketType type = PacketType.valueOf(Integer.parseInt(new String(id)));
		data = trimType(data);
		String[] attributes = trimToAttributes(data);
		switch (type) {
		case CONNECT:
			decodeConnect(attributes);
			break;
		case DISCONNECT:
			decodeDisconnect(attributes);
			break;
		case MOVE:
			decodeMove(attributes);
			break;
		case PLAYER:
			decodePlayer(attributes);
			break;
		case CONFIRM_ONLINE:
			decodeConfirmOnline(attributes);
			break;
		default:
			break;
		}
	}
	
	private void decodeConfirmOnline(String[] attributes) {
		if (attributes.length != 1) {
			return;
		}
		this.username = attributes[0];
	}

	private void decodeConnect(final String[] attributes) {
		if (attributes.length != 3) {
			return;
		}
		this.username = attributes[0];
		this.x = Integer.parseInt(attributes[1]);
		this.y = Integer.parseInt(attributes[2]);
	}
	
	private void decodeDisconnect(final String[] attributes) {
		if (attributes.length != 1) {
			return;
		}
		this.username = attributes[0];
	}
	
	private void decodePlayer(final String[] attributes) {
		decodeMove(attributes);
	}
	
	private void decodeMove(final String[] attributes) {
		if (attributes.length != 3) {
			return;
		}
		this.username = attributes[0];
		this.x = Integer.parseInt(attributes[1]);
		this.y = Integer.parseInt(attributes[2]);
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
