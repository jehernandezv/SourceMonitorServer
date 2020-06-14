package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread{

	private ServerSocket serverSocket;
	public final static Logger LOGGER = Logger.getGlobal();
	private ArrayList<ThreadSocket> connections;
	private boolean stop;
	
	public Server(short port) throws IOException{
		this.connections = new ArrayList<ThreadSocket>();
		this.serverSocket = new ServerSocket(port);
		start();
	}
		
	
	public void run() {
		Socket connection;
		System.out.println("Server listen on 4000...");
			while(!this.stop) {    
					    try {
							connection = serverSocket.accept();
							System.out.println("New connection");
							ThreadSocket threadSocket = new ThreadSocket(connection);
							this.connections.add(threadSocket);
							LOGGER.log(Level.INFO, "Conexion aceptada: " + connection.getInetAddress().getHostAddress());
						} catch (IOException e) {
							this.stop = true;
							System.out.println(e.getMessage());
						}
				}		
			}
	
		public static void main(String[] args) {
			try {
				new Server((short)4000);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

