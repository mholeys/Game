package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import client.entity.Entity;
import client.entity.Player;
import packets.EntityData;
import packets.PacketType;
import packets.PlayerData;

public class ClientListener extends Thread {

	private boolean running = false;
	private DatagramSocket socket;
	private GameClient client;
	
	public ClientListener(DatagramSocket socket, GameClient client) {
		this.socket = socket;
		this.client = client;
		running = true;
		this.start();
	}
	
	@Override
	public void run() {
		while (running) {
			byte[] data = new byte[Settings.PACKET_LENGTH];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			if (!running) return;
			try {
				socket.receive(packet);
				String packetData = new String( packet.getData());
				System.out.println("RECEIVED: " + packetData);
				int type = Integer.parseInt(new String(packetData.substring(0, 4)));
				handlePacket(type, packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void handlePacket(int type, DatagramPacket packet) {
		byte[] data = packet.getData();
		PlayerData pd = null;
		Player p = null;
		EntityData ed = null;
		Entity e = null;
		switch(PacketType.valueOf(type)) {
		case DISCONNECT:
			pd = new PlayerData(data);
			p = new Player(pd.getUsername(), pd.getX(), pd.getY());
			if (p.equals(client.client)) {
				System.err.println("Disconneted from server");
				System.exit(1);
			}
			System.out.println("Player " + pd.getUsername() + " disconnected");
			client.players.remove(p);
			break;
		case INVALID:
			System.out.println("Error - Packet invalid");
			break;
		case MOVE:
			pd = new PlayerData(data);
			p = new Player(pd.getUsername(), pd.getX(), pd.getY());
			System.out.println("Player " + pd.getUsername() + " moved");
			if (pd.getUsername().equals(client.client.getUsername())) {
				client.client.move(pd.getX(), pd.getY());
			} else {
				if (!client.players.contains(p)) {
					client.players.add(p);
				} else {
					client.players.get(client.players.indexOf(p)).move(pd.getX(), pd.getY());
				}
			}
			break;
		case ENTITY_UPDATE:
			System.out.println("Entity updated");
			ed = new EntityData(data);
			e = new Entity(ed.getID(), ed.getX(), ed.getY());
			if (client.world.entities.contains(e)) {
				int index = client.world.entities.indexOf(e);
				client.world.entities.get(index).move(ed.getX(), ed.getY());
			} else {
				client.world.entities.add(e);
			}
			break;
		case CONFIRM_ONLINE:
			client.confirmStillConnected();
			break;
		default:
			break;
		}
	}
	
	public void stopRunning() {
		running = false;
	}
	
}
