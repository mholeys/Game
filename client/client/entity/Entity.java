package client.entity;

import client.graphics.Screen;
import client.graphics.sprite.Sprites;

public class Entity {

	protected int x;
	protected int y;
	
	private int id;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Entity(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Entity) {
			Entity e = ((Entity) o);
			if (e.id != -1) {
				if (e.id == this.id) {
					return true;
				}
			}
		}
		return false;
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, Sprites.entity, true);
	}
	
}
