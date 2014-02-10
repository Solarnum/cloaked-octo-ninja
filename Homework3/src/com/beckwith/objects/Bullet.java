package com.beckwith.objects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Bullet extends GameObject {

	double dirAngle;
	int posX;
	int posY;
	BufferedImage currentBullet;
	double dX;
	double dY;
	double dXdY[][] = new double[5][2];
	int dt = 0;

	Ellipse2D.Double bullet;

	public Bullet(int x, int y, int speed, double degrees, ObjectID objID) {
		super(x, y, speed, objID);
		dX = x;
		dY = y;
		dirAngle = degrees;
		bullet = new Ellipse2D.Double(5, 5, 10, 10);
		for (int i = 0; i <= 4; i++) {
			dXdY[i][0] = 0;
			dXdY[i][1] = 0;
		}
	}

	@Override
	public void clock() {
		dt++;
		dXdY[4][0] = dX;
		dXdY[4][1] = dY;
		for (int i = 0; i < 4; i++) {
				dXdY[i][0] = dXdY[i + 1][0];
				dXdY[i][1] = dXdY[i + 1][1];
		}

		dX += decrementSpeed(dt) * Math.cos(dirAngle - Math.PI / 2);
		dY += decrementSpeed(dt) * Math.sin(dirAngle - Math.PI / 2);

	}
	
	public double decrementSpeed(int dt){
		if(SPEED - dt*.040 > .1){
			
			return SPEED - dt*.040;
		}else{
			destroy();
			return 0;
		}
	}

	@Override
	public void render(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i <= 3; i++) {
			BufferedImage img = createStylizedObject(i);
			g2d.drawImage(img, (int) dXdY[i][0] - img.getWidth() / 2,
					(int) dXdY[i][1] - img.getHeight() / 2, null);
		}
		currentBullet = createStylizedObject(5);
		g2d.drawImage(currentBullet, (int) dX - currentBullet.getWidth() / 2,
				(int) dY - currentBullet.getHeight() / 2, null);

		

	}

	@Override
	public Rectangle getCollider() {
		
		return new Rectangle( (int) dX, (int) dY, (int) bullet.getWidth(), (int) bullet.getHeight());
	}

	private BufferedImage createStylizedObject(int i) {
		BufferedImage img = new BufferedImage(20, 20, BufferedImage.TRANSLUCENT);
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
//		switch(i){
//		case 0:
//			g.setColor(Color.magenta);
//			break;
//		case 1:
//			g.setColor(Color.cyan);
//			break;
//		case 2:
//			g.setColor(Color.green);
//			break;
//		case 3:
//			g.setColor(Color.orange);
//			break;
//		default:
//			g.setColor(Color.black);
//			break;
//		}
		g.setColor(Color.black);
		if (i == 0) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.1f));
		} else {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					i * 0.2f));
		}
		g.fill(bullet);

		g.dispose();

		return img;

	}

}
