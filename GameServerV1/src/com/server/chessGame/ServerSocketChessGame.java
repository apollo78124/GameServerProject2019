package com.server.chessGame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocketChessGame {
	
    private ServerSocket server;
    
    private ArrayList<Player> clientsLookingForSession;
    
    public ServerSocketChessGame (String ipAddress) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty()) 
          this.server = new ServerSocket(0, 1, InetAddress.getByName(ipAddress));
        else 
          this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());
        
        clientsLookingForSession = new ArrayList<Player>();
    }
    
    private void newClientConnection() throws Exception {
        String data = null;
        
        Player clientObject = new Player();
        
        if (clientsLookingForSession.isEmpty()) {
        	clientsLookingForSession.add(clientObject);
        } else {
        	
        }
        
        clientObject.setClinetSocket(this.server.accept());
        String clientAddress = clientObject.getClinetSocket().getInetAddress().getHostAddress();
        System.out.println("\r\nNew connection from " + clientAddress);
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientObject.getClinetSocket().getInputStream()));        
        while ( (data = in.readLine()) != null ) {
            System.out.println("\r\nMessage from " + clientAddress + ": " + data);
        }
    }
    
    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }
    
    public int getPort() {
        return this.server.getLocalPort();
    }
    
    public static void main(String[] args) throws Exception {
    	ServerSocketChessGame app = new ServerSocketChessGame("");
        System.out.println("\r\nRunning Server: " + 
                "Host=" + app.getSocketAddress().getHostAddress() + 
                " Port=" + app.getPort());
        try {
        app.newClientConnection();
        } catch (Exception e) {
        	System.out.println("Connection to client lost");
        }
    }
}