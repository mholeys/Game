package server.entity;

import java.net.InetAddress;

public class Player extends Entity {

	private String username;
	private InetAddress address;
	private int port;
	
	private static final int CHECK_INTERVAL = 500;
	private int lastCheck = CHECK_INTERVAL;
	private boolean confirmRequest = false;
	private boolean shouldRemove = false;
	
	public Player(String username, int x, int y, InetAddress address, int port) {
		super(x, y);
		this.username = username;
		this.address = address;
		this.port = port;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player p = (Player) o;
			if (p.getUsername().equals(this.getUsername())) {
				return true;
			}
		}
		return false;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	public byte[] getData() {
		return (getUsername() + ";" + getX() + ";" + getY()).getBytes();
	}
	
	public void update() {
		lastCheck--;
		if (lastCheck == 0) {
			confirmRequest = true;
		}
		if (lastCheck <= -Player.CHECK_INTERVAL) {
			shouldRemove = true;
		}
	}
	
	public boolean shouldConfirm() {
		return confirmRequest;
	}
	
	public boolean shouldRemove() {
		return shouldRemove;
	}
	
	public void sentConfirm() {
		confirmRequest = false;
	}
	
	public void hasConfirmed() {
		confirmRequest = false;
		lastCheck = Player.CHECK_INTERVAL;
		shouldRemove = false;
	}
	
}
