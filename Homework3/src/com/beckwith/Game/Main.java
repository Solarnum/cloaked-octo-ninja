/*
 
  Author: Charlie Beckwith

  
  Title: Homework 3
  
  Due Date: January 3rd, 2014
  Objective: Make a roomba that sucks up dust bunnies <3. The dust bunnies can wrap around the screen. The roomba cannot!
  
  Storyline: Vroomba has gained sentience and now has only one mission: vengence upon the dust bunnies. Help Vroomba navigate his physics defying pad to defeat the swarm
  of dust bunnies that has consumed his place of residence and who drank all his Hawaiian Punch! 
  
  How to play: Rotate the Vroomba using the left and right arrow keys or the 'a' key and 'd' key to suck up dust bunnies. Unfortunately, the dust bunnies are filled
   with the stolen Hawaiian punch and explode on being sucked up leaving a nasty stain on your beautiful linoleum floor. A Vroomba isn't a mop though, and doesn't care to clean 
   up that mess. His job is to get the dust OUT! Vacuum up all the dust bunnies before running out of battery. There won't be enough battery to get all of them most 
   likely, which is why it is convenient that batteries will appear occasionaly to be consumed by our hero, Vroomba! Picking up a battery will return the Roomba's
   battery level to 100%. Be careful, though! Every battery that is picked up decreases the chance that another one will appear! Save the batteries until you are low
   on reserves and then suck them up. It's a good strategy, but remember, the Vroomba moves slow and has a wide turn radius. Don't get greedy! 
   
   
  
 */
package com.beckwith.Game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;
import com.beckwith.objects.Background;
import com.beckwith.objects.Battery;
import com.beckwith.objects.Countdown;
import com.beckwith.objects.DustBunny;
import com.beckwith.objects.Roomba;
import com.beckwith.sound.SynthSound;

public class Main extends Applet implements Runnable, MouseMotionListener,
		MouseListener, KeyListener {

	public static LinkedList<GameObject> objects;
	public static LinkedList<GameObject> waitingObjects;
	public static LinkedList<GameObject> destroyedObjects;
	public static int WIDTH, HEIGHT;
	private Roomba player;

	private Background bg;

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

	public int hitNote = 55;
	private int bulletNote = 55;

	private int batteryChance = 150;

	public void init() {
		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();
		Synth = new SynthSound();
		Synth.setInstrument(1, 30);
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
	}

	public void run() {
		objects = new LinkedList<GameObject>();
		waitingObjects = new LinkedList<GameObject>();
		destroyedObjects = new LinkedList<GameObject>();

		bg = new Background(0, 0, WIDTH, HEIGHT, 0, Color.white,
				ObjectID.background);
		player = new Roomba(250, 250, 0, ObjectID.roomba);

		waitingObjects.add(player);
		// populateLevel();
		levelWon = false;
		// cd = new Countdown(level, 0, 0, ObjectID.countdown);

		this.requestFocus();
		populateLevel();

		while (t != null) {

			repaint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
			}

		}
	}

	public void populateLevel() {

		// waitingObjects.add(garbage);
		for (int i = 0; i < 20; i++) {
			DustBunny db = new DustBunny(20 + rand.nextInt(470),
					20 + rand.nextInt(470), 0, rand.nextDouble(),
					ObjectID.dustbunny);
			waitingObjects.add(db);
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
						if (object.checkCollision(go.getCollider())) {
							System.out.println("collision " + go.getID());
							go.switchDirections();
						}
					} else if (go.containsPoint(object.getCollider().x,
							object.getCollider().y)) {

						go.destroy();
						object.destroy();
						Synth.playNote(hitNote);
						hitNote += 5;

					}
				}
			} catch (NullPointerException n) {

			}

		}

	}

	public void checkRoombaCollision() {
		for (Iterator<GameObject> objecta = objects.iterator(); objecta
				.hasNext();) {
			GameObject go = objecta.next();

			if (go.getID() == ObjectID.dustbunny) {
				if (player.checkInnerCollider(go.getCollider())) {
					System.out.println("DESTROY");
					go.destroy();
				}
			} else if (go.getID() == ObjectID.battery) {
				if (player.checkInnerCollider(go.getCollider())) {
					System.out.println("Battery Get!");

					go.destroy();
					player.batteryGet();
				}
			}
		}

	}

	public void resetGame() {
		levelWon = false;
		levelComplete = false;
		levelLost = false;
		hitNote = bulletNote;

		Synth.playChord(55);

	}

	public void update(Graphics g) {
		if (levelWon) {
			this.stop();
			levelComplete = true;
			System.out.println("Level Won");
			cd = new Countdown(0, 0, 0, ObjectID.countdown);
			cd.setText("WIN:)");
			waitingObjects.add(cd);

		}
		if (levelLost) {
			this.stop();
			System.out.println("Level Lost");
			cd = new Countdown(0, 0, 0, ObjectID.countdown);
			cd.setText("LOSE:(");
			waitingObjects.add(cd);
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

		int numBatteries = 0;
		int numDustBunny = 0;

		for (Iterator<GameObject> go = destroyedObjects.iterator(); go
				.hasNext();) {
			GameObject object = go.next();
			object.clock();
		}

		for (Iterator<GameObject> go = objects.iterator(); go.hasNext();) {
			GameObject object = go.next();
			object.clock();
			switch (object.getID()) {
			case roomba:
				checkCollisions(object);
				if (player.isBatteryDead()) {
					levelLost = true;
				}
				break;
			case battery:
				numBatteries++;
				break;
			case background:
				checkCollisions(object);
				break;
			case dustbunny:
				numDustBunny++;
				break;
			default:
				break;
			}
			if (!object.isAlive() && object.getID() != ObjectID.dustbunny) {
				go.remove();
			}
			if (!object.isAlive() && object.getID() == ObjectID.dustbunny) {
				destroyedObjects.add(object);
				go.remove();
			}

		}

		if (numBatteries == 0) {
			if (rand.nextInt(batteryChance) == 50) {
				Battery bat = new Battery(rand.nextInt(400) + 50,
						rand.nextInt(400) + 50, 0, ObjectID.battery);
				waitingObjects.add(bat);
				// Decrease the odds that a new battery will be made
				batteryChance *= 2;
			}
		}

		if (numDustBunny == 0) {
			levelWon = true;

		}

		checkRoombaCollision();

		if (imageBuffer == null) {
			imageBuffer = createImage(WIDTH, HEIGHT);
			buffer = imageBuffer.getGraphics();
		}

		paint(buffer);
		g.drawImage(imageBuffer, 0, 0, null);
	}

	public void paint(Graphics g) {
		try {
			bg.render(g);

			for (Iterator<GameObject> go = destroyedObjects.iterator(); go
					.hasNext();) {
				GameObject object = go.next();
				object.render(g);
			}
			for (Iterator<GameObject> go = objects.iterator(); go.hasNext();) {
				GameObject object = go.next();
				object.render(g);
			}
			player.render(g);

			// cd.render(g);
		} catch (NullPointerException n) {

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

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (levelComplete) {
			level++;
			resetGame();
			cd.setText("");
			start();
		}

		if (levelLost) {
			level = 0;
			resetGame();
			cd.setText("");
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

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		task.cancel();

	}

	TimerTask task = new MyTimerTask();
	private boolean keypressed;

	private class MyTimerTask extends TimerTask {
		public void run() {

		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		keypressed = true;
		player.keyPressed(arg0);

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keypressed = false;
		player.keyReleased();

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
