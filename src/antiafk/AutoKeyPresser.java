package antiafk;

import java.awt.Color;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Cette classe permet d'appuyer � intervalle r�gulier sur une touche
 * parmis les suivantes: {@code Z, Q, S, D}.
 * 
 * @version 1.1
 * @author Raffaele Gigantelli
 */
public class AutoKeyPresser implements ActionListener {
	
	private static final int DELAY_SECONDS = 3;
	private static final int[] POSSIBLE_KEYS = new int[] { KeyEvent.VK_Z, KeyEvent.VK_S, KeyEvent.VK_Q, KeyEvent.VK_D };
	
	private Timer presserTimer;
	private Robot robot;
	private Random random;
	private JLabel label;
	
	private int lastKeyGenerated = -1;
	private boolean isStopped;
	
	public AutoKeyPresser() throws Exception {
		this(null);
	}
	
	/**
	 * 
	 * @param label Un label qui servira � afficher l'�tat de l'AutoKeyPresser en temps r�el
	 * @throws Exception
	 */
	public AutoKeyPresser(JLabel label) throws Exception {
		this.isStopped = true;
		this.presserTimer = new Timer(DELAY_SECONDS * 1000, this);
		this.robot = new Robot();
		this.random = new Random();
		this.label = label;
		
		this.updateLabelState();
	}
	
	/**
	 * Permet de d�marrer l'AutoKeyPresser
	 */
	public void start() {
		this.isStopped = false;
		this.updateLabelState();
		wait((int)(DELAY_SECONDS * 0.5) * 1000);
		this.presserTimer.start();
	}
	
	/**
	 * Permet de mettre fin � l'ex�cution de l'AutoKeyPresser
	 */
	public void stop() {
		this.isStopped = true;
		this.updateLabelState();
		this.presserTimer.stop();
	}
	
	/**
	 * Permet de r�cup�rer l'�tat de fonctionnement de l'AutoKeyPresser
	 * 
	 * @return {@code false} si l'AutoKeyPresser fonctionne actuellement et {@code true} s'il est arr�t�.
	 */
	public boolean isStopped() {
		return this.isStopped;
	}
	
	/**
	 * Fonction qui va �tre ex�cut�e � chaque "tick" du timer
	 * et qui va appuyer sur un touche au hasard � intervale r�gulier
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.isStopped()) return;
		
		int randomKey = this.getRandomKey();
		this.pressKey(randomKey);
	}
	
	/**
	 * Permet de g�n�rer al�atoirement une touche contenue dans la liste des touches possibles
	 * 
	 * @return une touche au hasard
	 */
	private int getRandomKey() {
		int randomIndex;
		do {
			randomIndex = random.nextInt(POSSIBLE_KEYS.length);
		} while(randomIndex == this.lastKeyGenerated);
		
		this.lastKeyGenerated = POSSIBLE_KEYS[randomIndex];
		return this.lastKeyGenerated;
	}
	
	/**
	 * Permet d'appuyer sur une touche
	 * 
	 * @param key La touche qui va �tre press�e
	 */
	private void pressKey(int key) {
		this.robot.keyPress(key);
		this.robot.keyPress(KeyEvent.VK_SPACE);
		
		wait(1500);
		
		this.robot.keyRelease(key);
		this.robot.keyRelease(KeyEvent.VK_SPACE);
	}
	
	/**
	 * Permet de mettre � jour le texte du label (s'il y en a un) avec l'�tat de l'AutoKeyPresser
	 */
	private void updateLabelState() {
		if(this.label == null) return;
		this.label.setText(this.isStopped() ? "STOPPED" : "ACTIVATED");
		this.label.setForeground(this.isStopped() ? Color.RED : Color.GREEN);
	}

	// provient de https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
	/**
	 * Permet de mettre pause au processus durant une dur�e d�termin�e
	 * 
	 * @param ms Le temps d'attente (en millisecondes)
	 */
	private void wait(int ms) {
		try {
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
