package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerListener extends Thread {

	private DatagramSocket socket;
	private GameServer server;
	private boolean running = false;
	
	public ServerListener(DatagramSocket socket, GameServer server) {
		this.socket = socket;
		this.server = server;
		running = true;
	}
	
	public void run() {
		while(running) {
			byte[] receiveData = new byte[Settings.PACKET_LENGTH];
			DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
			if (!running) return;
			try {
				socket.receive(packet);
				String packetData = new String( packet.getData());
				//System.out.println("RECEIVED: " + packetData);
				int type = Integer.parseInt(new String(packetData.substring(0, 4)));
				server.handlePacket(type, packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopRunning() {
		running = false;
	}
	
}
