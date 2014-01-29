/*
 * 
 * Author: Charlie Beckwith
 * 
 * Title: Homework 1
 * 
 * Goal: "Write a Java applet which displays a picture. The applet should be 600 pixels wide by 500 pixels high. 
 * Your picture should involve at least two types of shapes (lines, rectangles, ovals, etc.) and at least two colors. 
 * Remember, you can find details of what types of shapes, colors, etc. are available by reading the Java API for the 
 * Graphics class."
 * 
 * 
 */


import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Hw1 extends Applet implements Runnable, KeyListener {

	int screenWidth = 600;
	int screenHeight = 500;

	Color colorOrange = new Color(255, 153, 51);

	Color colorPurple = new Color(138, 43, 226);
	Color colorArray[] = { Color.red, colorOrange, Color.yellow, Color.green,
			Color.cyan, colorPurple, Color.magenta, null };

	boolean seizureMode = true;

	int sleepLen = 20;

	Thread seizurize = null;

	public void init() {

		addKeyListener(this);
	}

	public void start() {
		System.out.println("start() called");
		seizurize = new Thread(this);
		// seizurize.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (seizureMode)
				switchColors();

			repaint();
			try {
				Thread.sleep(sleepLen);
			} catch (InterruptedException e) {

			}

		}

	}

	public void switchColors() {
		colorArray[7] = colorArray[0];
		for (int g = 0; g < 7; g++) {
			colorArray[g] = colorArray[g + 1];
		}

	}

	public void paint(Graphics g) {
		// Fill Background Color
		fillBg(g, screenWidth, screenHeight);
		// Draw rainbow box method
		// /drawRainBox(g, 150, 0, 100, 100);
		// /drawRainBox(g, 40, 200, 100, 100);
		drawRainBox(g, 40, 15, 100, 50);

		// draw flowers
		drawFlower(g, 250, 35, 20);
		drawFlower(g, 300, 50, 30);
		drawFlower(g, 250, 340, 40);
		drawFlower(g, 430, 230, 60);
		// Draw string to screen
		printText(g);
	}

	public void printText(Graphics g) {

		String fontName = "Helvetica";
		g.setColor(Color.BLACK);
		Font titleFont = new Font(fontName, Font.PLAIN, 24);
		g.setFont(titleFont);
		if (seizurize.isAlive()) {
			if (seizureMode) {
				g.drawString(
						"''t'' to decrease speed ''r'' to increase ''z'' to stop",
						20, 480);
			} else {
				g.drawString("press z to enter seizure mode", 120, 480);
			}
		} else {
			g.drawString("press z to enter seizure mode", 120, 480);
		}

	}

	public void fillBg(Graphics g, int x, int y) {
		if (colorArray[4] == Color.cyan) {
			g.setColor(Color.blue);
		} else {
			g.setColor(colorArray[4]);
		}
		if(seizurize.isAlive() && seizureMode){
		int k = 0;
		int tt = 0;
		while (y != 0) {
			tt++;
			g.setColor(colorArray[k]);
			k++;
			if (k > 7) {
				k = 0;
			}

			g.fillRect(0+5*tt, 0+5*tt, x, y);
			x -= 10;
			y -= 10;
			
		}
		}else{
			g.fillRect(0, 0, x, y);
		}
	}

	public void drawFlower(Graphics g, int flPosx, int flPosy, int flDia) {
		g.setColor(Color.BLACK);

		// Draw Center Circle
		g.fillOval(flPosx, flPosy, flDia, flDia);
		for (int d = 0; d < 4; d++) {

			switch (d) {
			case 0:
				g.setColor(colorArray[6]);
				break;
			case 1:
				g.setColor(colorArray[1]);

				flDia = flDia / 2;
				flPosx = flPosx + flDia / 2;
				flPosy = flPosy + flDia / 2;
				break;
			case 2:
				g.setColor(colorArray[3]);
				flDia = flDia / 2;
				flPosx = flPosx + flDia / 2;
				flPosy = flPosy + flDia / 2;
				break;
			case 3:
				g.setColor(colorArray[2]);
				flDia = flDia / 2;
				flPosx = flPosx + flDia / 2;
				flPosy = flPosy + flDia / 2;
				break;
			}

			// Upper Right Petal
			g.fillOval(flPosx + flDia - flDia / 5, flPosy - flDia / 3,
					flDia / 2, flDia / 2);
			// Right Petal
			g.fillOval(flPosx + flDia, flPosy + flDia / 4, flDia / 2, flDia / 2);
			// Lower Right Petal
			g.fillOval(flPosx + flDia - flDia / 5, flPosy + flDia / 2 + flDia
					/ 3, flDia / 2, flDia / 2);
			// Left Petal
			g.fillOval(flPosx - flDia / 2, flPosy + flDia / 4, flDia / 2,
					flDia / 2);
			// Lower Left Petal
			g.fillOval(flPosx - flDia / 3, flPosy + flDia / 2 + flDia / 3,
					flDia / 2, flDia / 2);
			// Top Petal
			g.fillOval(flPosx + flDia / 4, flPosy - flDia / 2, flDia / 2,
					flDia / 2);
			// Top Left Petal
			g.fillOval(flPosx - flDia / 3, flPosy - flDia / 3, flDia / 2,
					flDia / 2);
			// Bottom Petal
			g.fillOval(flPosx + flDia / 4, flPosy + flDia, flDia / 2, flDia / 2);
		}

	}

	// Draw a Rainbowbox
	public void drawRainBox(Graphics g, int x, int y, int width, int height) {
		int origX = x;
		int origY = y;
		// 600 pixels wide
		// 500 pixels tall

		int origWidth = width;
		int origHeight = height;
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 6; k++) {
				for (int i = 0; i < 7; i++) {
					g.setColor(colorArray[i]);

					switch (j) {
					case 0:
						x = x + 5;
						y = y + 5;
						break;
					case 1:
						x = x - 5;
						y = y + 5;
						break;
					case 2:
						x = x - 5;
						y = y - 5;
						break;
					case 3:
						x = x + 5;
						y = y - 5;
						break;
					}

					g.fillRect(x, y, width, height);
					width = width - 2;
					height = height - 1;
				}
			}
			switch (j) {
			case 0:
				width = origWidth;
				height = origHeight;
				x = screenWidth - width - origX;
				y = origY;
				break;
			case 1:
				width = origWidth;
				height = origHeight;
				x = screenWidth - width - origX;
				y = screenHeight - height - origY;
				break;
			case 2:
				width = origWidth;
				height = origHeight;
				x = origX;
				y = screenHeight - height - origY;
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		char key = e.getKeyChar();
		System.out.println("Key Pressed = " + key);
		if (key == 'z') {
			if (seizurize.isAlive()) {
				// if Seizurize is already running then do etc..
				seizureMode = !seizureMode;
				System.out.println("isAlive");

			} else {
				// start thread seizurize
				seizurize.start();
				System.out.println("Starting Seizurize");
			}
		} else if (key == 't') {
			sleepLen += 5;
		} else if (key == 'r' && sleepLen != 0) {
			sleepLen -= 5;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
