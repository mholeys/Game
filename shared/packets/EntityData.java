package packets;

public class EntityData extends PacketData {

	protected int x;
	protected int y;
	protected int id;
	
	public EntityData(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public EntityData(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public EntityData(byte[] data) {
		byte[] id = new byte[4];
		System.arraycopy(data, 0, id, 0, 4);
		PacketType type = PacketType.valueOf(Integer.parseInt(new String(id)));
		data = trimType(data);
		String[] attributes = trimToAttributes(data);
		switch (type) {
		case ENTITY_UPDATE:
			decodeEntity(attributes);
			break;
		default:
			break;
		}
	}
	
	private void decodeEntity(String[] attributes) {
		if (attributes.length != 3) {
			return;
		}
		this.x = Integer.parseInt(attributes[0]);
		this.y = Integer.parseInt(attributes[1]);
		this.id = Integer.parseInt(attributes[2]);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getID() {
		return id;
	}
	
}
