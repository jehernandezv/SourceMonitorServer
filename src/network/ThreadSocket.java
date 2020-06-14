package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadSocket extends Thread{
	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean stop;

	public ThreadSocket(Socket socket) throws IOException {
		this.connection = socket;
		this.input = new DataInputStream(this.connection.getInputStream());
		this.output = new DataOutputStream(this.connection.getOutputStream());
		start();
	}

	public void run() {
		while (!stop) {
			String request;
				try {
					request = input.readUTF();
					if (request != null) {
						System.out.println("llega: " + request);
						managerRequest(request);
					}
				} catch (IOException e) {
					this.stop = true;
					e.printStackTrace();
				}
		}

	}
	
	private void managerRequest(String request) throws IOException {
		switch (EResponse.valueOf(request)) {
		case SALUDO: 
			this.welcome();
			
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + request);
		}
	}
	
	private void welcome() throws IOException {
		this.output.writeUTF("MESSAGE");
		this.output.writeUTF("hola bienvenido");
	}
	
}
	
