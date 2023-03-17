package gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StartGame {

	public static HitDetection hitReader;
	private static JFrame frame = new JFrame();

	public static void main(String args[]) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.setTitle("Batalha Naval");
				java.net.URL imgPath = getClass().getResource("/img/icone.jpg");
				Image imagem = Toolkit.getDefaultToolkit().getImage(imgPath);
				frame.setIconImage(imagem);

				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						String ObjButtons[] = { "Sim", "Nao" };
						int PromptResult = JOptionPane.showOptionDialog(null, "Voce deseja fechar o jogo?",
								"Fechar", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
								ObjButtons[1]);

						if (PromptResult == JOptionPane.YES_OPTION) {
							System.exit(0);
						} else {
							frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						}
					}
				});

				try {
					ColocarNaviosP1 window = new ColocarNaviosP1(frame);
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
