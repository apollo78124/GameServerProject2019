package com.server.chessGame;

import java.net.Socket;

public class Player {
	private Socket clinetSocket;

	public Socket getClinetSocket() {
		return clinetSocket;
	}

	public void setClinetSocket(Socket clinetSocket) {
		this.clinetSocket = clinetSocket;
	}


}
