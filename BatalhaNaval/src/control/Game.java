package control;

import javax.swing.JOptionPane;

public class Game extends Thread{

	private static Player p1;
	private static Player p2;


	public  Game() {}

	private static Player TheGame(Player p1, Player p2){
		Player winner;
		if (p1.Alive() && p2.Alive()){
			int [] coordinates=p1.hit(p2);
			if(p2.takeHit(coordinates)){
				winner=TheGame(p2,p1);
			}else{
				winner=TheGame(p1,p2);
			}
		}else{
			if(!p1.Alive()){
				return p2;
			}else
				return p1;
		}

		return winner;

	}

	public static void createPlayers(){
		p1=new Player();
		p2=new Player();
	}

	public static Player getP1() {
		return p1;
	}
	
	public static Player getP2() {
		return p2;
	}

	public void run() {
		Player winner=TheGame(p1,p2);
		String winnerName;
		if(winner == p1) {
			winnerName = "jogador 1";
		}
		else {
			winnerName = "jogador 2";
		}
		JOptionPane.showConfirmDialog(null, "Vitoria do " + winnerName, "FIM DO JOGO",JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		System.exit(0);
	}
}