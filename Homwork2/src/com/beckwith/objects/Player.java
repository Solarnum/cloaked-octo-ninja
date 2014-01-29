package com.beckwith.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PaintContext;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Player extends GameObject {

	private Color playerColor = Color.GRAY;
	private Polygon triangle;
	private Double rotateDegrees;

	public Player(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);
		
		int xPoints[] = { 0, 20, 0 };
		int yPoints[] = { 0, 20, 20 };
		triangle = new Polygon(xPoints, yPoints, 3);
		rotateDegrees = 0.0;

	}

	@Override
	public void tick() {
		rotateDegrees++;

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage img = createRotatedObject();
		g2d.drawImage(img, X, Y, null);
	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private BufferedImage createRotatedObject(){
		BufferedImage img = new BufferedImage(40, 40, BufferedImage.TRANSLUCENT);
		
		Graphics2D g = img.createGraphics();
		g.rotate(Math.toDegrees(rotateDegrees), 20, 20);
		
		g.setColor(Color.RED);
		g.fill(triangle);
		g.dispose();
		return img;
		
	}

}
