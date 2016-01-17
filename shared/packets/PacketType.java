package packets;

public enum PacketType {

	INVALID(00), CONNECT(01), MOVE(02), ENTITY_UPDATE(03), PLAYER(97), CONFIRM_ONLINE(98), DISCONNECT(99);
	
	private int id;
	
	private PacketType(int i) {
		this.id = i;
	}
	
	public String getIDString() {
		String id = String.valueOf(this.id);
		while(id.length() < 4) {
			id = "0" + id;
		}
		return id;
	}
	
	public int getID() {
		return this.id;
	}
	
	public static PacketType valueOf(int id) {
		for (PacketType p : values()) {
			if (p.getID() == id) {
				return p;
			}
		}
		return INVALID;
	}
	
}
