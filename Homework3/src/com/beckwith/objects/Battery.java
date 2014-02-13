package com.beckwith.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Battery extends GameObject {

	private BufferedImage batteryImage;
	public Battery(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);
		loadResource("Battery.png");
		try {
			batteryImage = ImageIO.read(input);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void clock() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		Graphics2D  g2d = (Graphics2D) g;
		BufferedImage batterySprite = drawBattery();
		g2d.drawImage(batterySprite,(int)	X, (int) Y,null);

	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return new Rectangle((int) X,(int) Y,25,50);
	}
	
	public BufferedImage drawBattery(){
		BufferedImage bimg = new BufferedImage(25, 50, BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D) bimg.createGraphics();
		g.drawImage(batteryImage,0,0,null);
		
		g.dispose();
		return bimg;
		
	}
	
	

}
