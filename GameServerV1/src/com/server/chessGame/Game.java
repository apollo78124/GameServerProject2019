package com.server.chessGame;

/**
 * Created whenever there are two players waiting for a game
 * 
 * Destroyed whenever one player quits
 * 
 * @author dldms
 *
 */
public class Game {
	
	/**
	 * Refers to one Player object
	 */
	private Player black;
	
	private Player white;
	
	public Game(Player black, Player white) {
		this.setBlack(black);
		this.setWhite(white);
	}

	public Player getWhite() {
		return white;
	}

	public void setWhite(Player white) {
		this.white = white;
	}

	public Player getBlack() {
		return black;
	}

	public void setBlack(Player black) {
		this.black = black;
	}
	
}
