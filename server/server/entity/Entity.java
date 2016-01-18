package server.entity;

import java.awt.Rectangle;
import java.util.ListIterator;

import server.GameServer;
import server.world.World;

public class Entity {

	protected int x;
	protected int y;
	protected int id = -1;
	protected World world;
	
	public Entity(int x, int y, World world) {
		this.x = x;
		this.y = y;
		this.world = world;
	}
	
	public Entity(int x, int y, int id, World world) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.world = world;
	}
	
	public boolean isFree(int x, int y) {
		for (int corner = 0; corner < 4; corner++) {
			int xTile = (x + 15*(corner % 2)) / 16;
			int yTile = (y + 15*(corner / 2)) / 16;
			if (world.getTile(xTile, yTile).isSolid()) {
				return false;
			}
		}
		ListIterator<Entity> eIterator = world.getEntities().listIterator();
		while (eIterator.hasNext()) {
			Entity e = eIterator.next();
			Rectangle eR = new Rectangle(e.getX(), e.getY(), 16, 16);
			Rectangle pR = new Rectangle(x, y, 16, 16);
			if (eR.intersects(pR)) {
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
