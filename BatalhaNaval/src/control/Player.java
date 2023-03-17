package control;

import gui.StartGame;



public class Player {

	private boolean status;
	private int[][] positionChecker;
	private Ship[] ship;
	private int shipAmount = 4;
	private int aliveShips = 4;
	private int lastHitShip;

	
	public Player() {
		this.status = true;
		
		ship = new Ship[shipAmount];
		
		
		ship[0] = new Ship(2);
		ship[1] = new Ship(3);
		ship[2] = new Ship(4);
		ship[3] = new Ship(5);
		
		positionChecker = new int[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				positionChecker[i][j] = 0;
			}
		}
	} 
	
	public Ship getLastHitShip() {
		return ship[lastHitShip];
	}
	
	public void loseShip() {
		aliveShips = aliveShips -1;
		if(aliveShips == 0) {
			System.out.println("Died!");
			status = false;
		}
	}
	
	public boolean takeHit(int[] coordinates) {
		for(int i = 0; i < shipAmount; i++) {
			if(ship[i].isHorizontal()) {
				for(int j = 0; j < ship[i].getSize(); j++) {
					if(coordinates[0] == ship[i].getCoordinates()[0]+j && coordinates[1] == ship[i].getCoordinates()[1]) {
						ship[i].takeDamage();
						if(ship[i].isDestroyed()) {
							this.loseShip();
						}
						lastHitShip = i;
						return true;
					}
				}
			}
			else {
				for(int j = 0; j < ship[i].getSize(); j++) {
					if(coordinates[0] == ship[i].getCoordinates()[0] && coordinates[1] == ship[i].getCoordinates()[1]+j) {
						ship[i].takeDamage();
						if(ship[i].isDestroyed()) {
							this.loseShip();
						}
						lastHitShip = i;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void setPosition(int i, int x, int y, boolean isHorizontal){
		int[] coords = new int[2];
		coords[0] = x;
		coords[1] = y;
		ship[i].setCoordinates(coords);
		ship[i].setHorizontal(isHorizontal);
		if(isHorizontal) {
			for(int j = x; j < ship[i].getSize()+x; j++) {
				if(j >= 0 && y >= 0 && j <= 9 && y <= 9) {
					positionChecker[j][y] = 1;
				}
			}
		}
		else {
			for(int j = y; j < ship[i].getSize()+y; j++) {
				if(x >= 0 || j >= 0 || x <= 9 || j <= 9) {
					positionChecker[x][j] = 1;
				}
			}
		}
		
	}
	
	public boolean positionIsValid() {
		int counter = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				counter += positionChecker[i][j];
			}
		}
		if(counter != 14) {
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					 positionChecker[i][j] = 0;
				}
			}
			return false;
		}
		else {
			return true;
		}
	}
	
	public int[] hit(Player p){
		int[] hitCoordinates= new int[1];

		boolean performedHit=false;
		
		while(!performedHit){
			
			hitCoordinates=StartGame.hitReader.getHit();
			
			performedHit = p.takeHit(hitCoordinates);
		}
		
		return hitCoordinates;
		
	}
	
	public boolean Alive(){
			return status;
		}

	public Ship getShip(int i){
		return ship[i];
	}
	
	public Ship[] getShip(){
		return ship;
	}
}
