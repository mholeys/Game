package mholeys.game.entity.spawner;

import mholeys.game.entity.particle.Particle;
import mholeys.game.level.Level;

public class SpawnerParticle extends Spawner {

	private int life;

	public SpawnerParticle(int x, int y, int life, int amount, Level level) {
		super(x, y, eType.PARTICLE, amount, level);
		this.life = life;
		for (int iii = 0; iii < amount; iii++) {
			level.add(new Particle(x, y, this.life));
		}
	}

}
