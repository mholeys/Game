package server.world.tile;

public class Tile {

	private int id;
	private boolean solid;
	
	public Tile(TileType type) {
		this.id = type.ordinal();
		this.solid = type.isSolid();
	}
	
	public Tile(final int id) {
		this.id = id;
	}
	
	public Tile setSolid(boolean solid) {
		this.solid = solid;
		return this;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
}
