package server.world.tile;

public class Tiles {

	public static final Tile GRASS_TILE = new Tile(TileType.GRASS.ordinal());
	public static final Tile STONE_TILE = new Tile(TileType.STONE.ordinal());
	public static final Tile VOID_TILE = new Tile(-1).setSolid(true);
	
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
	
}
