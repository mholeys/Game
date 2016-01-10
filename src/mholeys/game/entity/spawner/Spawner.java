package mholeys.game.entity.spawner;

import java.util.ArrayList;
import java.util.List;

import mholeys.game.entity.Entity;
import mholeys.game.level.Level;

public abstract class Spawner extends Entity {

	@SuppressWarnings("unused")
	private List<Entity> entities = new ArrayList<Entity>();

	public enum eType {
		MOB, PARTICLE;
	}

	@SuppressWarnings("unused")
	private eType type;

	public Spawner(int x, int y, eType type, int amount, Level level) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.type = type;
	}

}
