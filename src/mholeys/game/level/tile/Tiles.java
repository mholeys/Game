package mholeys.game.level.tile;

import mholeys.game.graphics.sprite.Sprite;
import mholeys.game.graphics.sprite.Sprites;
import mholeys.game.level.tile.type.BrickTile;
import mholeys.game.level.tile.type.DirtTile;
import mholeys.game.level.tile.type.FlowerTile;
import mholeys.game.level.tile.type.GrassTile;
import mholeys.game.level.tile.type.PuddleTile;
import mholeys.game.level.tile.type.RockTile;
import mholeys.game.level.tile.type.TreeTile;
import mholeys.game.level.tile.type.VoidTile;
import mholeys.game.lib.ID;

public class Tiles {
	public static Tile grass = new GrassTile(Sprites.grassTall0, ID.grass);
	public static Tile grassTall1 = new GrassTile(Sprites.grassTall1, ID.grassTall1);
	public static Tile grassTall2 = new GrassTile(Sprites.grassTall2, ID.grassTall2);
	public static Tile grassFlower = new FlowerTile(Sprites.grassFlower, ID.grassFlower);
	public static Tile grassRock = new RockTile(Sprites.grassRock, ID.grassRock);
	public static Tile brick = new BrickTile(Sprites.dirt, ID.brick);
	public static Tile grassDirt = new DirtTile(Sprites.grassDirt, ID.grassDirt);
	public static Tile grassPuddle = new PuddleTile(Sprites.grassPuddle, ID.grassPuddle);
	public static Tile stone = new RockTile(Sprites.stone, ID.stone);
	public static Tile voidTile = new VoidTile(Sprites.voidSprite, -1);
	public static Tile portal = new PuddleTile(new Sprite(16,16,0x0000ff), ID.portal);
	
	public static Tile tree = new TreeTile(Sprites.tree, ID.tree); 
	
	/*public static Tile getNewTile(int id) {
		switch (id) {
		case ID.grass:
			return new GrassTile(Sprites.grassTall0, ID.grass);
		case ID.grassTall1:
			return new GrassTile(Sprites.grassTall1, ID.grassTall1);
		case ID.grassTall2:
			return new GrassTile(Sprites.grassTall2, ID.grassTall2);
		case ID.grassFlower:
			return new FlowerTile(Sprites.grassFlower, ID.grassFlower);
		case ID.grassRock:
			return new RockTile(Sprites.grassRock, ID.grassRock);
		case ID.brick:
			return new BrickTile(Sprites.dirt, ID.brick);
		case ID.grassDirt:
			return new DirtTile(Sprites.grassDirt, ID.grassDirt);
		case ID.grassPuddle:
			return new PuddleTile(Sprites.grassPuddle, ID.grassPuddle);
		case 8:
			return new RockTile(Sprites.stone, 8);
		case -1:
			return new VoidTile(Sprites.voidSprite, -1);
		default:
			return null;
		}
	}*/
	
}
