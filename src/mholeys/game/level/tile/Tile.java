package mholeys.game.level.tile;

import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.Sprite;

public class Tile {
	protected Sprite sprite;
	protected final int ID;

	private boolean solid;

	public Tile(Sprite sprite, final int id) {
		this.sprite = sprite;
		this.ID = id;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderSprite(x << 4, y << 4, this.sprite, true);
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public int getID() {
		return ID;
	}
}
