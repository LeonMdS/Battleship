package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import control.Game;

public class ColocarNaviosP2 {

	private int shipAmount = 4;
	private JFrame frame;
	private Rectangle2D.Float[] rect;
	private int selectedShip = -1;

	public ColocarNaviosP2(JFrame f) {
		frame = f;
		frame.getContentPane().removeAll();
		frame.repaint();

		MouseMoveScale mms = new MouseMoveScale();
		mms.setDoubleBuffered(true);

		JLabel player;
		player = new JLabel("Jogador 2");
		
		player.setForeground(Color.red);
		player.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 50));
		player.setBounds(800, 400, 250, 125);

		JButton nextButton = new JButton();
		try {
			nextButton.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/PodeJogar.jpg"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		nextButton.setBounds(850, 500, 120, 60);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < shipAmount; i++) {
					Game.getP2().setPosition(i, getColumn(rect[i]), getRow(rect[i]), isHorizontal(rect[i]));
				}

				if (Game.getP2().positionIsValid()) {
					try {
						new Field(f);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane
					.showMessageDialog(new JFrame(),
							"Coloque todos os navios, com que eles nao se toquem",
							"Erro", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		frame.setContentPane(mms);
		frame.getContentPane().add(player);
		frame.getContentPane().add(nextButton);
		frame.setBackground(Color.BLACK);
		frame.validate();
	}

	class MouseMoveScale extends JComponent {

		private static final long serialVersionUID = 1L;

		{
			rect = new Rectangle2D.Float[shipAmount];
			rect[0] = new Rectangle2D.Float(700, 1 * 60 + 2, 2 * 56, 50);
			rect[1] = new Rectangle2D.Float(700, 2 * 60 - 1, 3 * 56, 50);
			rect[2] = new Rectangle2D.Float(700, 3 * 60 - 2, 4 * 56, 50);
			rect[3] = new Rectangle2D.Float(700, 4 * 60 - 3, 5 * 56, 50);
		}

		private static final int ROWS = 10;
		private static final int COLUMNS = 10;

		MovingAdapter ma = new MovingAdapter();

		public MouseMoveScale() {
			addMouseMotionListener(ma);
			addMouseListener(ma);
		}

		public void paint(Graphics g) {
			super.paint(g);

			Graphics2D g2d = (Graphics2D) g;

			java.net.URL imgURL = getClass().getResource("/img/oceano.jpg");
			Image background = Toolkit.getDefaultToolkit().getImage(imgURL);

			g.drawImage(background, 0, 0, 579, 579, this);

			int sqSize = this.getHeight() / ROWS;

			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLUMNS; j++) {
					int x = i * sqSize;
					int y = j * sqSize;

					g.setColor(Color.black);
					g.drawRect(x, y, 57, 57);
				}
			}

			java.net.URL imgURL2 = getClass().getResource("/img/navio-vertical.jpg");
			Image shipHorizontal = Toolkit.getDefaultToolkit().getImage(imgURL2);

			java.net.URL imgURL3 = getClass().getResource("/img/navio-horizontal.jpg");
			Image shipVertical = Toolkit.getDefaultToolkit().getImage(imgURL3);

			for (int i = 0; i < shipAmount; i++) {

				g2d.fill(rect[i]);

				if (rect[i].width > rect[i].height) {
					g2d.drawImage(shipVertical, (int) rect[i].x, (int) rect[i].y, (int) rect[i].width,
							(int) rect[i].height, null);
				} else {
					g2d.drawImage(shipHorizontal, (int) rect[i].x, (int) rect[i].y, (int) rect[i].width,
							(int) rect[i].height, null);
				}

				repaint();
			}
		}

		class MovingAdapter extends MouseAdapter {
			private int x;
			private int y;
			private boolean grab = false;

			public void mouseReleased(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					if(!grab) {
						x = e.getX();
						y = e.getY();
						for (int k = 0; k < shipAmount; k++) {
							if (rect[k].getBounds2D().contains(x, y)) {
								grab = true;
								selectedShip = k;
								break;
							}
						}
					}
					else {
						grab = false;
						selectedShip = -1;
					}
				}
				else if(SwingUtilities.isRightMouseButton(e)) {
					if(grab) {
						float temp = rect[selectedShip].width;
						rect[selectedShip].width = rect[selectedShip].height;
						rect[selectedShip].height = temp;

						if (rect[selectedShip].y + rect[selectedShip].height > 525) {
							rect[selectedShip].y = 576 - rect[selectedShip].height;
						}

						repaint();
					}
				}
			}

			public void mouseMoved(MouseEvent e) {
				if(grab) {
	
					if (selectedShip != -1) {
						x = e.getX();
						y = e.getY();
						
						rect[selectedShip].x = x;
						rect[selectedShip].y = y;
					}
				}
			}
		}
	}

	public int getRow(Rectangle2D.Float myRect) {
		return (int) (myRect.y) / 56;
	}

	public int getColumn(Rectangle2D.Float myRect) {
		return (int) (myRect.x) / 56;
	}

	public boolean isHorizontal(Rectangle2D.Float myRect) {
		return myRect.height < myRect.width;
	}

	public int getSize(Rectangle2D.Float myRect) {
		if (isHorizontal(myRect)) {
			return (int) myRect.getWidth() / 56;
		} else {
			return (int) myRect.getHeight() / 56;
		}
	}
}
