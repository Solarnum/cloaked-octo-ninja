package com.beckwith.Game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;
import com.beckwith.objects.Background;
import com.beckwith.objects.Bullet;
import com.beckwith.objects.Countdown;
import com.beckwith.objects.Garbage;
import com.beckwith.objects.Player;
import com.beckwith.sound.SynthSound;

public class Game extends Applet implements Runnable, MouseMotionListener,
		MouseListener {

	public static LinkedList<GameObject> objects;
	public static LinkedList<GameObject> waitingObjects;
	public static int WIDTH, HEIGHT;
	private Player player;

	Thread t = null;
	Image imageBuffer = null;
	Graphics buffer = null;
	Random rand = new Random();

	Timer timer = new Timer();
	Countdown cd;
	int level = 0;
	boolean levelComplete = false;
	boolean levelWon = false;
	boolean levelLost = false;
	SynthSound Synth;
	
	public int bulletNote = 55;

	public void init() {
		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();

		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void run() {
		objects = new LinkedList<GameObject>();
		waitingObjects = new LinkedList<GameObject>();
		Background bg = new Background(0, 0, WIDTH, HEIGHT, 0, Color.white,
				ObjectID.background);
		player = new Player(WIDTH / 2, HEIGHT / 2, 0, ObjectID.player);

		waitingObjects.add(bg);
		waitingObjects.add(player);
		populateLevel();
		levelWon=false;
		cd = new Countdown(level, 0, 0, ObjectID.countdown);
		waitingObjects.add(cd);
		Synth = new SynthSound();
		Synth.playChord(55);
		while (t != null) {

			repaint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
			}
			
		}
	}

	public void populateLevel() {
		Garbage garbage = new Garbage(270, 120, 0, ObjectID.garbage);

		// waitingObjects.add(garbage);
		for (int i = 0; i < level + 5; i++) {
			addGarbage();
		}
	}

	/*
	 * Add enemy garbage to the playing field in a random way.
	 */
	public void addGarbage() {
		int boundX = (WIDTH - 120) / 2;
		int boundY = (HEIGHT - 120) / 2;

		int rand1[] = { 30 + rand.nextInt(WIDTH / 2 - 100),
				30 + rand.nextInt(WIDTH / 2 - 100) };
		int rand2[] = { (int) (245 + (Math.random() * (500 - 275))),
				(int) (245 + (Math.random() * (500 - 275))) };
		if (rand.nextBoolean()) {
			if (rand.nextBoolean()) {
				if (rand.nextBoolean()) {
					rand1 = rand2;
				}
			} else {
				rand1[0] = rand2[1];
			}
		} else {
			rand1[1] = rand2[0];
		}

		System.out.println("rand[0] " + rand1[0] + "  rand[1] " + rand1[1]);
		Garbage garbage = new Garbage(rand1[0], rand1[1], 0, ObjectID.garbage);

		if (rand1[0] > WIDTH - 30 || rand1[0] < 30 || rand1[1] > HEIGHT - 30
				|| rand1[1] < 30) {
			addGarbage();
		} else {
			waitingObjects.add(garbage);
		}
	}

	public void start() {

		// Start Thread
		t = new Thread(this);
		t.start();

	}

	// Method for checking collisions
	public void checkCollisions(GameObject object) {

		for (Iterator<GameObject> objecta = objects.iterator(); objecta
				.hasNext();) {
			GameObject go = objecta.next();
			try {
				if (go.getCollider() != null && go.getID() != object.getID()
						&& go.born()) {
					if (object.getID() == ObjectID.background) {
						if (!object.containsRect(go.getCollider())) {
							go.destroy();
							
							
						}
					} else if (go.containsPoint(object.getCollider().x,
							object.getCollider().y)) {
						
						go.destroy();
						object.destroy();
						Synth.playNote(bulletNote);
						bulletNote +=5;
						
					}
				}
			} catch (NullPointerException n) {

			}

		}

	}

	public void resetGame(){
		levelWon = false;
		levelComplete = false;
		levelLost = false;
		bulletNote = 55;

	}
	public void update(Graphics g) {
		if (levelWon) {
			this.stop();
			levelComplete = true;
			cd.gameWon();
			
		}
		// Checks if game is over
		if (cd.isGameOver()) {
			this.stop();
			Synth.playChord(52);
			levelLost = true;
		}
		int numGarbage = 0;
		// Checks object waiting list for any objects waiting to enter the game.
		for (Iterator<GameObject> future = waitingObjects.iterator(); future
				.hasNext();) {

			GameObject object = future.next();

			objects.add(object);
			future.remove();
		}
		// Performs tick on each active object.

		for (Iterator<GameObject> go = objects.iterator(); go.hasNext();) {
			GameObject object = go.next();
			object.tick();
			if (!object.isAlive()) {
				go.remove();
			}
			if (object.getID() == ObjectID.garbage)
				numGarbage++;
			
			// If an object is a bullet then checkCollisions is performed.
			if (object.getID() == ObjectID.bullet
					|| object.getID() == ObjectID.background) {
				checkCollisions(object);
			}
		}
		if (numGarbage == 0) {
			levelWon = true;
		}

		if (imageBuffer == null) {
			imageBuffer = createImage(WIDTH, HEIGHT);
			buffer = imageBuffer.getGraphics();
		}

		paint(buffer);
		g.drawImage(imageBuffer, 0, 0, null);
	}

	public void paint(Graphics g) {
		for (Iterator<GameObject> go = objects.iterator(); go.hasNext();) {
			GameObject object = go.next();
			object.render(g);
		}
	}

	public void stop() {
		t = null; // stop animation thread
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (player != null) {
			player.setDegrees(player.calculateDegrees(WIDTH / 2, HEIGHT / 2,
					e.getX(), e.getY()));

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (levelComplete) {
			level++;
			resetGame();
			start();
		}
		
		if (levelLost) {
			level = 0;
			resetGame();
			start();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

		// If control is held down shoot a barage of bullets.
		if (e.isControlDown()) {
			task = new MyTimerTask();
			timer.scheduleAtFixedRate(task, 0, 10);
		} else {
			if (player != null) {
				double[] pos = player.getTurretPosition();
				Bullet bullet = new Bullet((int) pos[0], (int) pos[1], 5,
						player.getDegrees(), ObjectID.bullet);
				waitingObjects.add(bullet);
				Synth.playNote(53);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		task.cancel();

	}

	TimerTask task = new MyTimerTask();

	private class MyTimerTask extends TimerTask {
		public void run() {
			double[] pos = player.getTurretPosition();
			Bullet bullet = new Bullet((int) pos[0], (int) pos[1], 5,
					player.getDegrees(), ObjectID.bullet);
			waitingObjects.add(bullet);
		}
	}
	


}
