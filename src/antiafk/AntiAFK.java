package antiafk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class AntiAFK {
	
	public static void main(String[] args) throws Exception {
		
		// Spécifier le style de l'OS courant
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		JFrame window = new JFrame();
		window.setResizable(false);
		window.setTitle("AntiAFK Roblox");
		window.setSize(300, 80);
				
		JPanel panel = new JPanel();
		JButton startBtn = new JButton("Démarrer");
		JButton stopBtn = new JButton("Arreter");
		
		JLabel stateLabel = new JLabel("STOPPED");
		AutoKeyPresser autoKeyPresser = new AutoKeyPresser(stateLabel);
		
		// Gérer le cas de fermeture de la fenêtre
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				autoKeyPresser.stop();
				window.setVisible(false);
				window.dispose();
				System.exit(0);
			}
		});
		
		// Ajouter un padding au dessus
		panel.setBorder(new EmptyBorder(10, 0, 0, 0));
		
		// Ajouter les composants de l'UI
		panel.add(startBtn);
		panel.add(stopBtn);
		panel.add(stateLabel);
		
		// Gestion du click pour le bouton start
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autoKeyPresser.start();
			}
		});
		
		// Gestion du click pour le bouton stop
		stopBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
					autoKeyPresser.stop();
			}
		});
		
		window.setLocationRelativeTo(null);
		window.setContentPane(panel);
		window.setVisible(true);
	}

}
