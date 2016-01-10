package mholeys.game.level.tile.type;

import mholeys.game.graphics.sprite.Sprite;
import mholeys.game.level.tile.Tile;

public class BrickTile extends Tile {

	public BrickTile(Sprite sprite, int id) {
		super(sprite, id);
		setSolid(true);
	}

}
