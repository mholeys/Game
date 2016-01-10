package mholeys.game.level.tile.type;

import mholeys.game.graphics.sprite.Sprite;
import mholeys.game.level.tile.Tile;

public class RockTile extends Tile {

	public RockTile(Sprite sprite, int id) {
		super(sprite, id);
		setSolid(true);
	}
}
