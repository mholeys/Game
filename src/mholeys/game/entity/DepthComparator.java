package mholeys.game.entity;

import java.util.Comparator;

public class DepthComparator implements Comparator<Entity> {

	@Override
	public int compare(Entity e1, Entity e2) {
		return e2.depth - e1.depth;
	}

}
