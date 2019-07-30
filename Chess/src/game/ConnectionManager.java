package game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionManager {
	
	/**
	 * In the mock server text file
	 * line 1: session number, default 0
	 * line 2: turn
	 */
	private Socket socket;
    private Scanner scanner;
	private int sessionNumber = -1;
	
	public ConnectionManager(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
    }
	
	/**
	 * 
	 * Returns -1 when session is not found
	 * @return session Number
	 */
	public int findSession() {
		return -1;
	}
	
	/**
	 * If findSession returns -1, create a session and return the session number
	 * @return
	 */
	public int createSession() {
		int sessionNumber = 0;
		return sessionNumber;
	}

	public int getSessionNumber() {
		return sessionNumber;
	}

	public void setSessionNumber(int sessionNumber) {
		this.sessionNumber = sessionNumber;
	}
}
