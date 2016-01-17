package client.graphics.sprite;

public class Sprites {
	
	public static Sprite voidTile = new Sprite(16, 0x000000);
	public static Sprite grassTile = new Sprite(16, 0, 0, SpriteSheets.tiles);
	public static Sprite stoneTile = new Sprite(16, 0, 1, SpriteSheets.tiles);
	
	public static Sprite entity = new Sprite(16, 0x00FF00);
	
	public static Sprite player = new Sprite(16, 0, 0, SpriteSheets.player);
	
	public static Sprite debug1 = new Sprite(1, 0xFF0000);
	public static Sprite debug16 = new Sprite(16, 0xFF0000);

}
