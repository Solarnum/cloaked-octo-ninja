package com.beckwith.objects;

/*
 * Roomba Class
 * 
 * Battery state is connected to this object. It probably wasn't the best idea to do this, it could have been a seperate object that was associated with the Roomba.
 * But for all intents and purposes 
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.beckwith.Game.Main;
import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Roomba extends GameObject {

	
	private BufferedImage roombaImage;
	private BufferedImage battery;

	BufferedImage vroombaSprite = null;
	private boolean keyReleased = true;
	
	private final static int LEFT = -2;
	private final static int RIGHT = 2;
	
	// Battery States
	private final static int FULL = 1;
	private final static int HALF = 2;
	private final static int THIRD = 3;
	private final static int EMPTY = 4;
	
	private int state;
	
	private double batteryLevel = 100.0;
	
	private int batteryState = FULL;
	private int newState;
	
	
	
	TimerTask task;
	
	
	public Roomba(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);
		
		loadResource("vroomba4.png");
		
		task = new MyTimerTask();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 10);
		
		
		try {
			roombaImage = ImageIO.read(input);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		loadBattery(FULL);
		
		vroombaSprite = createRotatedObject();
	}
	
	private class MyTimerTask extends TimerTask {
		public void run() {
			decrementBattery();
			if(batteryLevel > (int) 65){
				loadBattery(FULL);
				
			}
			if(batteryLevel< (int) 65  && batteryLevel > 64){
				loadBattery(HALF);
				
			}
			if(batteryLevel < (int) 25 && batteryLevel > 24){
				loadBattery(THIRD);
				
			}
			if(batteryLevel < (int) 5 && batteryLevel > 4){
				loadBattery(EMPTY);
				
			}
		}
	}
	
	public void decrementBattery(){
		batteryLevel -= .03;
	}

	@Override
	public void clock() {
		if(!keyReleased)
			incrementRotation(state);
		
		double tempx = X;
		double tempy = Y;
		X += ( 3 * Math.cos(Math.toRadians(rotateDegrees)));
		Y += (3 * Math.sin(Math.toRadians(rotateDegrees)));
		if(Y < 50 || Y > Main.HEIGHT - 50){
			Y = tempy;
			//rotateDegrees = rotateDegrees - Math.PI*180;
		}
		
		if(X < 50 || X > Main.WIDTH - 50){
			X=tempx;
			//rotateDegrees = rotateDegrees - Math.PI*180;
		}
		
		
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		vroombaSprite = createRotatedObject();
		
		g2d.drawImage(vroombaSprite, (int) (X - vroombaSprite.getWidth() / 2),(int)( Y
				- vroombaSprite.getHeight() / 2),  null);
		
		BufferedImage batterySprite = createBatteryImage();
		g2d.drawImage(batterySprite, 25, 25, null);
//		g2d.setColor(Color.RED);
//		String fontName = "Helvetica";
//		
//		
//		
//		Font titleFont = new Font(fontName, Font.PLAIN, 24);
//		g2d.setFont(titleFont);
//		g2d.drawString("Battery Level = " + (int) batteryLevel + "%", 25,25);
		//g2d.setColor(Color.cyan);
		//g2d.fill(getInnerCollider());

	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return new Rectangle((int) (X - 50) +20, (int)( Y
				- 50) +20 , 60, 20);
	}
	
	
	public Rectangle getInnerCollider() {
		// TODO Auto-generated method stub
		return new Rectangle((int) (X - 50) +25, (int)( Y
				- 50) +25 , 55, 55);
	}
	
	public boolean checkInnerCollider(Rectangle rect){
		return getInnerCollider().getBounds().intersects(rect);
	}
	
	
	
	private BufferedImage createRotatedObject() {

		
		BufferedImage bimg = new BufferedImage(100, 100, BufferedImage.TRANSLUCENT);
		Graphics2D g = bimg.createGraphics();
		
	
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.rotate(Math.toRadians(rotateDegrees) + Math.PI/2, 50, 50);
		g.drawImage(roombaImage,0,0, 100, 100, null);
		
		g.dispose();
		return bimg;

	}
	
	private BufferedImage createBatteryImage() {

		
		BufferedImage bimg = new BufferedImage(100, 50, BufferedImage.TRANSLUCENT);
		Graphics2D g = bimg.createGraphics();
		
		

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		g.drawImage(battery,0,0,null);
		
		g.dispose();
		return bimg;

	}
	
	private void loadBattery(int x){
		switch(x){
		case FULL:
			loadResource("batteryFull.png");

			break;
			
		case HALF:
			loadResource("batteryHalf.png");
			
			break;
		case THIRD:
			loadResource("batteryThird.png");

			break;
		case EMPTY:
			loadResource("batteryEmpty.png");
			break;
		}
		
		try {
			battery = ImageIO.read(input);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	public void incrementRotation(int x){
		
			rotateDegrees += x;
	}
	
	public void keyReleased(){
		keyReleased = true;
	}

	public void keyPressed(KeyEvent e){
		keyReleased = false;
		switch(e.getKeyCode()){
		case KeyEvent.VK_A:
			state = LEFT;
			break;
		case KeyEvent.VK_D:
			state = RIGHT;
			break;
		case KeyEvent.VK_LEFT:
			state = LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			state = RIGHT;
			break;
			
		}
		
	}

	public void batteryGet() {
		batteryLevel = 100.0;
		
	}
	
	public boolean isBatteryDead(){
		return !(batteryLevel > 0);
	}
	

}
