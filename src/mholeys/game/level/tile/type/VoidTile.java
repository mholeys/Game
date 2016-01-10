package mholeys.game.level.tile.type;

import mholeys.game.graphics.sprite.Sprite;
import mholeys.game.level.tile.Tile;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite, int id) {
		super(sprite, id);
		setSolid(true);
	}

}
