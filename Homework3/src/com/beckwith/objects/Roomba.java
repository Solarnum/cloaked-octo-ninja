package com.beckwith.objects;

import java.awt.BasicStroke;
import java.awt.Color;
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

import javax.imageio.ImageIO;

import com.beckwith.Game.Main;
import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Roomba extends GameObject {

	
	private BufferedImage roombaImage;
	BufferedImage vroombaSprite = null;
	
	public Roomba(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);
		
		loadResource("Vroomba2.png");
		
		try {
			roombaImage = ImageIO.read(input);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		vroombaSprite = createRotatedObject();
	}

	@Override
	public void clock() {
		double tempx = X;
		double tempy = Y;
		X += ( 3 * Math.cos(Math.toRadians(rotateDegrees)));
		Y += (3 * Math.sin(Math.toRadians(rotateDegrees)));
		if(Y < 50 || Y > Main.HEIGHT - 50){
			Y = tempy;
			rotateDegrees = rotateDegrees - Math.PI*180;
		}
		
		if(X < 50 || X > Main.WIDTH - 50){
			X=tempx;
			rotateDegrees = rotateDegrees - Math.PI*180;
		}
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		vroombaSprite = createRotatedObject();
		
		g2d.drawImage(vroombaSprite, (int) (X - vroombaSprite.getWidth() / 2),(int)( Y
				- vroombaSprite.getHeight() / 2), null);
		g2d.setColor(Color.cyan);
		g2d.fill(getInnerCollider());

	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return new Rectangle((int) (X - 50) +20, (int)( Y
				- 50) +20 , 60, 60);
	}
	
	
	public Rectangle getInnerCollider() {
		// TODO Auto-generated method stub
		return new Rectangle((int) (X - 50) +20, (int)( Y
				- 50) +20 , 60, 60);
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
		g.drawImage(roombaImage,0,0,null);
		
		
		g.dispose();
		return bimg;


	}
	
	public void incrementRotation(int x){
		rotateDegrees += x;
		

	}

	public void keyPressed(KeyEvent e){
		switch(e.getKeyChar()){
		case 'a':
			incrementRotation(-2);
			break;
		case 'd':
			incrementRotation(+2);
			break;
		}
		
	}
	

}
