package server.entity;

import server.GameServer;

public class Entity {

	protected int x;
	protected int y;
	protected int id = -1;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Entity(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public boolean isFree(int x, int y) {
		for (int corner = 0; corner < 4; corner++) {
			int xTile = (x + 15*(corner % 2)) / 16;
			int yTile = (y + 15*(corner / 2)) / 16;
			if (GameServer.world.getTile(xTile, yTile).isSolid()) {
				return false;
			}
		}
		return true;
	}
	
	public void move(int x, int y) {
		if (x != this.x && y != this.y) {
			move(x, this.y);
			move(this.x, y);
		}
		if (isFree(x, y)) {
			this.x = x;	
			this.y = y;
		}
	}
	
	public void update() {
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public byte[] getData() {
		return (getX() + ";" + getY() + ";" + id).getBytes(); 
	}
	
}
