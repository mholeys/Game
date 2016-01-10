package mholeys.game.entity.tile;

import mholeys.game.entity.Entity;
import mholeys.game.level.tile.Tile;

public class EntityTile extends Entity {

	public Tile tile;
	
	public EntityTile(Tile tile, int x, int y) {
		this.tile = tile;
		this.x = x << 4;
		this.y = y << 4;
	}
	
	@Override
	public void checkHealth() {
		
	}
	
}
