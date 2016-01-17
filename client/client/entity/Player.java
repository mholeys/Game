package client.entity;

import client.graphics.Screen;
import client.graphics.sprite.Sprites;

public class Player extends Entity {

	private String username;
	
	public Player(String username, int x, int y) {
		super(x, y);
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player p = (Player)o;
			if (p.getUsername().equals(this.getUsername())) {
				return true;
			}
		}
		return false;
	}
	
	public void render (Screen screen) {
		screen.renderSprite(x, y, Sprites.player, true);
	}
	
}
