package de.netprojectev.networking.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import de.netprojectev.networking.Messages;

public class Server implements Runnable {

	private final ServerSocket server;
	private CommandQueue commandQueue;
	
	public Server(int listeningPort) throws IOException {
		server = new ServerSocket(listeningPort);
		commandQueue = new CommandQueue();
	}

	private void initListening() throws IOException {

		while (server.isBound()) {
			Socket connection = server.accept();
			new Thread(new ConnectionHandler(connection)).start();
		}

	}

	@Override
	public void run() {
		try {
			initListening();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

final class ConnectionHandler implements Runnable {

	private Socket connection;

	protected ConnectionHandler(Socket connection) {
		this.connection = connection;
	}

	@Override
	public void run() {
		DataInputStream in = null;
		Byte readByte = null;
		try {
			in = new DataInputStream(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (!connection.isClosed()) {

			try {
				readByte = in.readByte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			switch (readByte) {
			case Messages.ADD_MEDIA_FILE:
				System.out.println("ADDING MEDIA FILE");
				break;
			default:
				System.out.println("Read unknown byte message");
			}
		}
	}

}
