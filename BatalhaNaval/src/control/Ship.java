package control;


public class Ship{

	private int size;
	private boolean horizontal = true;
	private boolean status;
	private int life;
	private int[] coordinates;

	public Ship(int size) {
		this.size = size;
		status = true;
		life=size;
		coordinates = new int[2];

	}


	public void takeDamage() {
		this.life = this.life-1;
	}
	
	public boolean isDestroyed() {
		if(life == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setHorizontal(boolean direction) {
		this.horizontal = direction;
	}


	public int[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(int[] coor) {
		coordinates[0]=coor[0];
		coordinates[1]=coor[1];
	}

	public int getSize() {
		return size;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public boolean updateLive(int x, int y) {
		boolean flag=false;
		if(horizontal) {
			if(y==coordinates[1]){
				int i = 0;
				while(!flag&&i<size){
					if(coordinates[0]+i==x){
						life--;
						flag=true;
					}
					i++;
				}
			}
		}
		else {
			if(x==coordinates[0]){
				int i = 0;
				while(!flag&&i<size){
					if(coordinates[1]+i==y){
						life--;
						flag=true;
					}
					i++;
				}
			}
		}
			
		if(life==0){
			status=false;
		}
		return flag;
	}

	public boolean getStatus() {
		return status;
	}


}
