package mholeys.game.graphics.sprite;

import mholeys.game.graphics.sprite.SpriteSheets;

public class Sprites {

	public static Sprite grassTall0 = new Sprite(16, 0, 0, SpriteSheets.tiles);
	public static Sprite grassFlower = new Sprite(16, 1, 0, SpriteSheets.tiles);
	public static Sprite grassRock = new Sprite(16, 2, 0, SpriteSheets.tiles);
	public static Sprite grassTall1 = new Sprite(16, 3, 0, SpriteSheets.tiles);
	public static Sprite grassTall2 = new Sprite(16, 4, 0, SpriteSheets.tiles);
	public static Sprite grassDirt = new Sprite(16, 5, 0, SpriteSheets.tiles);
	public static Sprite grassPuddle = new Sprite(16, 6, 0, SpriteSheets.tiles);
	public static Sprite dirt = new Sprite(16, 7, 0, SpriteSheets.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x000000);
	public static Sprite stone = new Sprite(16, 0, 1, SpriteSheets.tiles);

	// player
	public static Sprite player = new Sprite(16, 0, 0, SpriteSheets.players);
	public static AnimatedSprite pUP = new AnimatedSprite(SpriteSheets.playerUP, 16, 16, 3);
	public static AnimatedSprite pDW = new AnimatedSprite(SpriteSheets.playerDW, 16, 16, 3);
	public static AnimatedSprite pLE = new AnimatedSprite(SpriteSheets.playerLE, 16, 16, 3);
	public static AnimatedSprite pRI = new AnimatedSprite(SpriteSheets.playerRI, 16, 16, 3);

	// dummy
	public static Sprite dummy = new Sprite(16, 0, 0, SpriteSheets.dummy);
	public static AnimatedSprite dummyUP = new AnimatedSprite(SpriteSheets.dummyUP, 16, 16, 3);
	public static AnimatedSprite dummyDW = new AnimatedSprite(SpriteSheets.dummyDW, 16, 16, 3);
	public static AnimatedSprite dummyLE = new AnimatedSprite(SpriteSheets.dummyLE, 16, 16, 3);
	public static AnimatedSprite dummyRI = new AnimatedSprite(SpriteSheets.dummyRI, 16, 16, 3);

	// projectiles
	public static Sprite projectileLaser = new Sprite(16, 0, 0, SpriteSheets.projectiles);
	public static Sprite particleLaser = new Sprite(2, 2, 0xffff0000);
	
	public static Sprite debugSprite0 = new Sprite(1, 1, 0xff0000ff);
	public static Sprite debugSprite1 = new Sprite(1, 1, 0xff00ff00);
	public static Sprite debugSprite2 = new Sprite(1, 1, 0xffff0000);
	public static Sprite debugSprite3 = new Sprite(1, 1, 0xffffff00);
	public static Sprite debugSprite4 = new Sprite(1, 1, 0xffcccccc);
	
	public static Sprite tree = new Sprite(16, 16, 0xffff2211);

}
