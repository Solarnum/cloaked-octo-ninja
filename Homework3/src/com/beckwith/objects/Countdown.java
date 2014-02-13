package com.beckwith.objects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;
import com.beckwith.sound.SynthSound;

public class Countdown extends GameObject {

	String time;
	TimerTask task;
	private double timeCounter = 0.0;
	private int currentTime = 10;
	private SynthSound synth;
	private boolean gamewon = false;
	private boolean gameover = false;

	public Countdown(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);
		currentTime = 15;
		time = Integer.toString(currentTime);
		task = new MyTimerTask();
		
		synth = new SynthSound();
		synth.setInstrument(1, 98);
	}

	@Override
	public void clock() {

	}
	
	public void start(){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 10);
	}

	private class MyTimerTask extends TimerTask {
		public void run() {
			decrementTime();

		}
	}

	private void decrementTime() {
		timeCounter += .01;

		if (timeCounter >= 1.0) {
			currentTime++;
			time = Integer.toString(currentTime);
			timeCounter = 0.0;
			if (!gameover && !gamewon)
				synth.playNote(65);
		}
		if (currentTime == 0) {
			gameOver();
		}

	}
	
	public void setText(String s){
		time = s;
	}

	public void gameOver() {
		gameover = true;
		// task.cancel();
		time = "LOSER";
	}

	public boolean isGameOver() {
		return gameover;
	}

	public void gameWon() {
		time = "  WIN";
		timeCounter= 0.2;
		gamewon = true;
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage img = scaleImage(createBufferedImage(), 600, 600, null);
		// g2d.drawImage(img, 0, 0, null);
		g2d.drawImage(img, 0, 0, null);

	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return null;
	}

	private BufferedImage createBufferedImage() {

		BufferedImage img = new BufferedImage(50, 50, BufferedImage.TRANSLUCENT);

		Graphics2D g = img.createGraphics();
		g.setColor(Color.RED);
		g.drawString(time, 0, 25);
		g.dispose();
		return img;

	}

	public BufferedImage scaleImage(BufferedImage img, int width, int height,
			Color background) {
		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();
		if (imgWidth * height < imgHeight * width) {
			width = imgWidth * height / imgHeight;
		} else {
			height = imgHeight * width / imgWidth;
		}
		BufferedImage newImage = new BufferedImage(width, height,
				BufferedImage.TRANSLUCENT);
		Graphics2D g = newImage.createGraphics();
		try {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) (1 - timeCounter)));
			g.drawImage(img, 0, 0, width, height, null);
		} finally {
			g.dispose();
		}
		return newImage;
	}

}
