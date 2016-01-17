package client.graphics.sprite;

public class SpriteSheets {
	
	private static final int DEFAULT_TRANSPARENT = 0xFFFF00FF;
	
	public static SpriteSheet tiles = new SpriteSheet("/texture/tiles.png", 128);
	
	public static SpriteSheet player = new SpriteSheet("/texture/player.png", 64, 64, DEFAULT_TRANSPARENT);
	
	public static SpriteSheet playerUP = new SpriteSheet(player, 0, 0, 1, 3, 16);
	public static SpriteSheet playerRI = new SpriteSheet(player, 1, 0, 1, 3, 16);
	public static SpriteSheet playerDW = new SpriteSheet(player, 2, 0, 1, 3, 16);
	public static SpriteSheet playerLF = new SpriteSheet(player, 3, 0, 1, 3, 16);
	
}
