package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import control.Game;

public class Field extends Thread {
	private JFrame frame;
	private JLabel player1, player2;
	private JButton sea1[][] = new JButton[10][10];
	private JButton sea2[][] = new JButton[10][10];
	private int i, j;
	private boolean p1Turn = true;

	public Field(JFrame f) throws IOException {

		StartGame.hitReader = new HitDetection();
		this.frame = f;
		frame.getContentPane().removeAll();
		frame.repaint();

		this.start();

		frame.setContentPane(new JLabel());

		player1 = new JLabel("JOGADOR 1");
		player1.setForeground(Color.blue);
		player1.setFont(new Font("Times New Roman", Font.BOLD, 40));
		player1.setBounds(50, 0, 500, 80);
		
		player2 = new JLabel("JOGADOR 2");
		player2.setForeground(Color.red);
		player2.setFont(new Font("Times New Roman", Font.BOLD, 40));
		player2.setBounds(680, 0, 500, 80);

		frame.getContentPane().add(player1);
		frame.getContentPane().add(player2);
		frame.validate();
	}

	public void run() {
		new Game().start();
		
		for (i = 0; i < 10; i++) {
			for (j = 0; j < 10; j++) {
				sea1[i][j] = new JButton();
				try {
					sea1[i][j].setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/Blue_Square.jpg"))));
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				sea1[i][j].setBounds(40 + i * 40, 70 + j * 40, 40, 40);
				sea1[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!p1Turn) {
							JButton button = (JButton) e.getSource();
							
							int x = button.getBounds().x;
							int y = button.getBounds().y;

							x = (x - 40) / 40;
							y = (y - 70) / 40;
							
							int[] coords = new int[2];
							coords[0] = x;
							coords[1] = y;
							
							boolean hit = Game.getP1().takeHit(coords);
							StartGame.hitReader.setHit(x, y);
							
							
							if (hit) { 

								try {
									sea1[x][y].setIcon(
											new ImageIcon(ImageIO.read(ResourceLoader.load("img/acertou.jpg"))));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								
								

							} 
							else {
								try {
									sea1[x][y].setIcon(
											new ImageIcon(ImageIO.read(ResourceLoader.load("img/errou.jpg"))));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
	
							}
							p1Turn = true;
							if (Game.getP1().getLastHitShip().isDestroyed()) {

								int[] sunkCoords = Game.getP1().getLastHitShip().getCoordinates();
								int sunkSize = Game.getP1().getLastHitShip().getSize();
								boolean horizontal = Game.getP1().getLastHitShip().isHorizontal();

								JLabel label = new JLabel();
								if(horizontal) {
									try {
										label.setIcon(new ImageIcon(
												ImageIO.read(ResourceLoader.load("img/navio-horizontal.jpg"))));
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									
									label.setBounds(sunkCoords[0] * 40 + 40, sunkCoords[1] * 40 + 70, 40 * sunkSize, 40);
								}
								else {
									try {
										label.setIcon(new ImageIcon(
												ImageIO.read(ResourceLoader.load("img/navio-vertical.jpg"))));
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									label.setBounds(sunkCoords[0] * 40 + 40, sunkCoords[1] * 40 + 63, 40, 40 * sunkSize);
								}
								frame.getContentPane().add(label);
								frame.repaint();
							}
							sea1[x][y].removeActionListener(this);
						}
					}
				});

				frame.getContentPane().add(sea1[i][j]);
			}
		}

		for (i = 0; i < 10; i++) {
			for (j = 0; j < 10; j++) {
				sea2[i][j] = new JButton();
				try {
					sea2[i][j].setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/Blue_Square.jpg"))));
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				sea2[i][j].setBounds(660 + i * 40, 70 + j * 40, 40, 40);
				sea2[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (p1Turn) {
							JButton button = (JButton) e.getSource();
							
							int x = button.getBounds().x;
							int y = button.getBounds().y;

							x = (x - 660) / 40;
							y = (y - 70) / 40;
							
							int[] coords = new int[2];
							coords[0] = x;
							coords[1] = y;
							
							boolean hit = Game.getP2().takeHit(coords);
							StartGame.hitReader.setHit(x, y);
							
							
							if (hit) { 

								try {
									sea2[x][y].setIcon(
											new ImageIcon(ImageIO.read(ResourceLoader.load("img/acertou.jpg"))));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								
								

							} 
							else {
								try {
									sea2[x][y].setIcon(
											new ImageIcon(ImageIO.read(ResourceLoader.load("img/errou.jpg"))));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
							p1Turn = false;
							if (Game.getP2().getLastHitShip().isDestroyed()) {

								int[] sunkCoords = Game.getP2().getLastHitShip().getCoordinates();
								int sunkSize = Game.getP2().getLastHitShip().getSize();
								boolean horizontal = Game.getP2().getLastHitShip().isHorizontal();

								JLabel label = new JLabel();
								if(horizontal) {
									try {
										label.setIcon(new ImageIcon(
												ImageIO.read(ResourceLoader.load("img/navio-horizontal.jpg"))));
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									
									label.setBounds(sunkCoords[0] * 40 + 40, sunkCoords[1] * 40 + 70, 40 * sunkSize, 40);
								}
								else {
									try {
										label.setIcon(new ImageIcon(
												ImageIO.read(ResourceLoader.load("img/navio-vertical.jpg"))));
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									label.setBounds(sunkCoords[0] * 40 + 660, sunkCoords[1] * 40 + 63, 40, 40 * sunkSize);
								}
								frame.getContentPane().add(label);
								frame.repaint();
							}
							sea2[x][y].removeActionListener(this);
						}
					}
				});

				frame.getContentPane().add(sea2[i][j]);
			}
		}

		frame.revalidate();
		frame.repaint();
	}

}