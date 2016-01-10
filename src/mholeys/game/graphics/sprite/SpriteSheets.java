package mholeys.game.graphics.sprite;

public class SpriteSheets {
	private final static int DEFAULT_TRANSPARENT = 0xFFFF00FF;

	public static SpriteSheet test = new SpriteSheet("/texture/test.png", 128);
	public static SpriteSheet tiles = new SpriteSheet("/texture/tiles.png", 128);
	public static SpriteSheet players = new SpriteSheet("/texture/player.png", 64, 64, DEFAULT_TRANSPARENT);
	public static SpriteSheet dummy = new SpriteSheet("/texture/dummy.png", 64, 64, DEFAULT_TRANSPARENT);
	public static SpriteSheet projectiles = new SpriteSheet("/texture/projectiles.png", 64, 64, DEFAULT_TRANSPARENT);

	public static SpriteSheet playerUP = new SpriteSheet(players, 0, 0, 1, 3, 16);
	public static SpriteSheet playerRI = new SpriteSheet(players, 1, 0, 1, 3, 16);
	public static SpriteSheet playerDW = new SpriteSheet(players, 2, 0, 1, 3, 16);
	public static SpriteSheet playerLE = new SpriteSheet(players, 3, 0, 1, 3, 16);

	public static SpriteSheet dummyUP = new SpriteSheet(dummy, 0, 0, 1, 3, 16);
	public static SpriteSheet dummyRI = new SpriteSheet(dummy, 1, 0, 1, 3, 16);
	public static SpriteSheet dummyDW = new SpriteSheet(dummy, 2, 0, 1, 3, 16);
	public static SpriteSheet dummyLE = new SpriteSheet(dummy, 3, 0, 1, 3, 16);
}
