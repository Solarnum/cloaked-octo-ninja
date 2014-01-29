package com.beckwith.Game;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Random;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;
import com.beckwith.objects.Garbage;
import com.beckwith.objects.Player;

public class Game extends Applet implements Runnable, MouseMotionListener {

	public static LinkedList<GameObject> objects;
	public static int WIDTH, HEIGHT;
	private Player player;

	Thread t = null;
	Image imageBuffer = null;
	Graphics buffer = null;
	Random rand = new Random();

	public void init() {
		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();
		objects = new LinkedList<GameObject>();
		addMouseMotionListener(this);
	}

	public void run() {
		while (t != null) {
			repaint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
			}
		}
	}

	public void start() {
		player = new Player(WIDTH / 2, HEIGHT / 2, 0, ObjectID.player);
		Garbage garbage = new Garbage(20, 35, 0, ObjectID.garbage);

		objects.add(player);
		objects.add(garbage);

		// Start Thread
		t = new Thread(this);
		t.start();

	}

	public void update(Graphics g) {
		for (GameObject go : objects) {
			go.tick();
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
			player.setDegrees(player.calculateDegrees(WIDTH/2, HEIGHT/2, e.getX(), e.getY()));
			
		}

	}

}
