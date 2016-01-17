package client.world.tile;

import client.graphics.sprite.Sprite;
import client.graphics.sprite.Sprites;

public class Tiles {

	public static final Tile GRASS_TILE = new Tile(TileType.GRASS.ordinal());
	public static final Tile STONE_TILE = new Tile(TileType.STONE.ordinal());
	public static final Tile VOID_TILE = new Tile(-1);
	
	public static Tile getTile(int id) {
		switch(id) {
		case -1:
			return VOID_TILE;
		case 0:
			return GRASS_TILE;
		case 1:
			return STONE_TILE;
		}
		return VOID_TILE;
	}

	public static Sprite getSprite(int id) {
		switch(id) {
		case -1:
			return Sprites.voidTile;
		case 0:
			return Sprites.grassTile;
		case 1:
			return Sprites.stoneTile;
		}
		return Sprites.voidTile;
	}
	
}
