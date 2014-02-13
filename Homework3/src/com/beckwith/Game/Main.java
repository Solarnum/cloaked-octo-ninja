/*
 
  Author: Charlie Beckwith
  
  Title: Homework 2
  
  Due Date: January 3rd, 2014
  Objective: The objective of this game is to aim and shoot at the black squares. 
  Eliminate all of them to pass on to the next level which will have one more square.
  There is no way to win. The game will continue until you do not manage to shoot all
  the black squares within the time limit at which point you will lose. 
  
  
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
import com.beckwith.objects.Countdown;
import com.beckwith.objects.DustBunny;
import com.beckwith.objects.Garbage;
import com.beckwith.objects.Roomba;
import com.beckwith.sound.SynthSound;


import com.beckwith.*;

public class Main extends Applet implements Runnable, MouseMotionListener,
		MouseListener, KeyListener {

	public static LinkedList<GameObject> objects;
	public static LinkedList<GameObject> waitingObjects;
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
		bg = new Background(0, 0, WIDTH, HEIGHT, 0, Color.white,
				ObjectID.background);
		player = new Roomba(250, 250, 0, ObjectID.roomba);

		
		waitingObjects.add(player);
		//populateLevel();
		levelWon=false;
		cd = new Countdown(level, 0, 0, ObjectID.countdown);
		
		
		DustBunny db = new DustBunny(150, 150, 0 ,.3, ObjectID.dustbunny);
		waitingObjects.add(db);
		
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
						if (object.checkCollision(go.getCollider())) {
							System.out.println("collision " + go.getID());
							go.switchDirections();
						}
					} else if (go.containsPoint(object.getCollider().x,
							object.getCollider().y)) {
						
						go.destroy();
						object.destroy();
						Synth.playNote(hitNote);
						hitNote +=5;
						
					}
				}
			} catch (NullPointerException n) {

			}

		}

	}
	
	public void checkRoombaCollision(){
		for (Iterator<GameObject> objecta = objects.iterator(); objecta
				.hasNext();) {
			GameObject go = objecta.next();
		
			if(go.getID() == ObjectID.dustbunny){
				if(player.checkInnerCollider(go.getCollider())){
					System.out.println("DESTROY");
					go.destroy();
				}
			}
		}
			
	}

	public void resetGame(){
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
			object.clock();
			if (!object.isAlive()) {
				go.remove();
			}
			if (object.getID() == ObjectID.garbage)
				numGarbage++;
			
			// If an object is a bullet then checkCollisions is performed.
			if (object.getID() == ObjectID.roomba
					|| object.getID() == ObjectID.background) {
				checkCollisions(object);
			}
		}
		
		checkRoombaCollision();
		if (numGarbage == 0) {
			//levelWon = true;
		}

		if (imageBuffer == null) {
			imageBuffer = createImage(WIDTH, HEIGHT);
			buffer = imageBuffer.getGraphics();
		}
		
		
		paint(buffer);
		g.drawImage(imageBuffer, 0, 0, null);
	}

	public void paint(Graphics g) {
		try{
		bg.render(g);
		
		for (Iterator<GameObject> go = objects.iterator(); go.hasNext();) {
			GameObject object = go.next();
			object.render(g);
		}
		player.render(g);
		
		cd.render(g);
		}catch(NullPointerException n){
			
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
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		task.cancel();

	}

	TimerTask task = new MyTimerTask();

	private class MyTimerTask extends TimerTask {
		public void run() {
			
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		player.keyPressed(arg0);
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	


}
