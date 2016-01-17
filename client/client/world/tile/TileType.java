package client.world.tile;

public enum TileType {

	GRASS, STONE(true);
	
	private boolean solid;
	
	private TileType() {
		solid = false;
	}
	
	private TileType(boolean solid) {
		this.solid = solid;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
}
