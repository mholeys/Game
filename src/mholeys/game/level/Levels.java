package mholeys.game.level;

public class Levels {

	public static Level getLevel(String level) {
		switch (level) {
		case "spawn":
			return new Level("level/spawn.dat");
		case "temp":
			return new Level("level/temp.dat");
		case "sam":
			return new Level("level/sam.dat");
		case "random":
			return new Level(32, 32, "level/temp.dat");
		default:
			return new Level(256, 256, "level/temp.dat");
		}
	}
}
