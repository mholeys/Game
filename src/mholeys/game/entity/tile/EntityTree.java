package mholeys.game.entity.tile;

import java.util.List;

import mholeys.game.Game;
import mholeys.game.entity.Entity;
import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.Sprite;
import mholeys.game.graphics.sprite.Sprites;
import mholeys.game.input.Mouse;
import mholeys.game.level.tile.Tile;

public class EntityTree extends EntityTile {

	private short state = 5 * 60;
	private boolean stump = false;
	
	
	public EntityTree(Tile tile, int x, int y, Sprite sprite) {
		super(tile, x, y);
		this.sprite = sprite;
		this.depth = 1;
	}
	
	@Override
	public void update() {
		if (stump) {
			if (state < 5 * 60) {
				state++;
				return;
			} else {
				stump = false;
			}
		}
		int mb = Mouse.getButton();
		if (mb != Mouse.MOUSE_LEFT) {
			return;
		}
		int mx = Mouse.getX()/Game.pixelScale;
		int my = Mouse.getY()/Game.pixelScale;
		
		double xScroll = (level.getClientPlayer().getX()) - (Game.getScreenWidth() / 2) + (Sprites.player.SIZE / 2);
		double yScroll = (level.getClientPlayer().getY()) - (Game.getScreenHeight() / 2) + (Sprites.player.SIZE / 2);
		
		int mouseX = (int) (xScroll + mx);
		int mouseY = (int) (yScroll + my);
		List<Entity> eList = level.getEntities(mouseX, mouseY, 3);
		if (!eList.contains(this)) {
			return;
		}
		state--;
		if (state % 60 == 0) {
			//sprite.update();
		}
		if (state == 0) {
			stump = true;
		}
	}
	
	@Override
	public void render(Screen screen) {
		if (!stump) {
			screen.renderSprite((int)x, (int)y, sprite, true);
		}
	}
	
}
