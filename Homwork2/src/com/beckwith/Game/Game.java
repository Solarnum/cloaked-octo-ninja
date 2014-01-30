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

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;
import com.beckwith.objects.Background;
import com.beckwith.objects.Bullet;
import com.beckwith.objects.Garbage;
import com.beckwith.objects.Player;

public class Game extends Applet implements Runnable, MouseMotionListener,
		MouseListener {

	public static LinkedList<GameObject> objects;
	public static int WIDTH, HEIGHT;
	private Player player;

	Thread t = null;
	Image imageBuffer = null;
	Graphics buffer = null;
	Random rand = new Random();

	Timer timer = new Timer();

	public void init() {
		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();
		objects = new LinkedList<GameObject>();
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void run() {
		Background bg = new Background(0, 0, WIDTH, HEIGHT, 0, Color.white,
				ObjectID.background);
		player = new Player(WIDTH / 2, HEIGHT / 2, 0, ObjectID.player);

		objects.add(bg);
		objects.add(player);
		addGarbage();
		addGarbage();
		addGarbage();
		addGarbage();
		addGarbage();
		while (t != null) {

			repaint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
			}
		}
	}

	public void addGarbage() {
		int boundX = (WIDTH - 150) / 2;
		int boundY = (HEIGHT - 150) / 2;

		int rand1[] = { rand.nextInt(175), rand.nextInt(175) };
		int rand2[] = { rand.nextInt(175) + 325, rand.nextInt(175) + 325 };
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

		System.out.println("rand[0]" + rand1[0] + "  rand[1] " + rand1[1]);
		Garbage garbage = new Garbage(rand1[0], rand1[1], 0, ObjectID.garbage);
		objects.add(garbage);
	}

	public void start() {

		// Start Thread
		t = new Thread(this);
		t.start();

	}

	public void checkCollisions(GameObject object) {
		for (GameObject go : objects) {
			if (go.getCollider() != null) {
				if (go.checkCollision(object.getCollider())
						&& go.getID() != object.getID()) {
					System.out.println("Collision detected between "
							+ go.getID() + " & " + object.getID());
					go.destroy();
					object.destroy();
				}
			}
		}
	}

	public void update(Graphics g) {
		// for (GameObject go : objects) {
		// go.tick();
		//
		// }

		for (Iterator<GameObject> go = objects.iterator(); go.hasNext();) {
			GameObject object = go.next();
			object.tick();
			if (!object.isAlive()) {
				System.out.println("Object destroyed  : " + object.getID());
				go.remove();
				
			}
			if (object.getID() == ObjectID.bullet) {
				checkCollisions(object);
			}
		}

		if (imageBuffer == null) {
			imageBuffer = createImage(WIDTH, HEIGHT);
			buffer = imageBuffer.getGraphics();
		}

		paint(buffer);
		g.drawImage(imageBuffer, 0, 0, null);
	}

	public void paint(Graphics g) {
		for (GameObject go : objects) {
			go.render(g);
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
		if (player != null) {
			double[] pos = player.getTurretPosition();
			Bullet bullet = new Bullet((int) pos[0], (int) pos[1], 5,
					player.getDegrees(), ObjectID.bullet);
			objects.add(bullet);
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
		if (e.isControlDown()) {
			task = new MyTimerTask();
			timer.scheduleAtFixedRate(task, 0, 10);
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
			objects.add(bullet);
		}
	}

}
