package server.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import server.GameServer;
import server.Settings;
import server.entity.Entity;
import server.entity.Player;
import server.world.tile.Tile;
import server.world.tile.Tiles;

public class World {
	
	private Tile[] tiles;
	private List<Entity> entities = new ArrayList<Entity>();
	private GameServer server;
	private int width, height;
	
	public World(GameServer server) {
		this.server = server;
		loadWorld();
	}
	
	public void loadWorld() {
		
		int width = 0;
		int height = 0;
		ClassLoader classLoader = this.getClass().getClassLoader();
		File worldTiles = new File(classLoader.getResource(Settings.WORLD_PATH).getPath());
		File worldEntities = new File(classLoader.getResource(Settings.WORLD_ENTITIES_PATH).getPath());
		System.out.println("Loading world....");
		try {
			//Load the tiles of the world
			Scanner worldLoader = new Scanner(worldTiles);
			if (worldLoader.hasNextInt()) {
				width = worldLoader.nextInt();
			} else {
				System.err.println("World is corrupt");
				System.exit(2);
			}
			if (worldLoader.hasNextInt()) {
				height = worldLoader.nextInt();
			} else {
				System.err.println("World is corrupt");
				System.exit(2);
			}
			
			if ((width == 0) || (height == 0)) {
				System.err.println("World is corrupt");
				System.exit(2);
			}

			this.width = width;
			this.height = height;
			tiles = new Tile[width * height];
			
			int tile = 0;
			while (worldLoader.hasNextInt()) {
				tiles[tile] = Tiles.getTile(worldLoader.nextInt());
				tile++;
			}
			worldLoader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Could not find the world to load");
		}
		try {
		//Load entities
			Scanner entityLoader = new Scanner(worldEntities);
			int i = 0;
			while (entityLoader.hasNextLine()) {
				String[] entityAttributes = entityLoader.nextLine().split(";");
				if (entityAttributes.length == 2) {
					int x = Integer.parseInt(entityAttributes[0]);
					int y = Integer.parseInt(entityAttributes[1]);
					Entity e = new Entity(x << 4, y << 4, i++);
					addEntity(e);
				}
			}
			
			entityLoader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Could not find the entities of the world to load");
		}
		System.out.println("Loaded world");
	}	
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public Tile getTile(int x, int y) {
		if (x <= 0 || x >= width || y <= 0 || y >= height) {
			return Tiles.VOID_TILE;
		}
		return tiles[x + y * width];
	}
	
	public void update() {
		ListIterator<Entity> entityIterator = entities.listIterator();
		while (entityIterator.hasNext()) {
			Entity e = entityIterator.next();
			e.update();
		}
		ListIterator<Player> playerIterator = server.players.listIterator();
		while (playerIterator.hasNext()) {
			Player p = playerIterator.next();
			p.update();
		}
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
}
