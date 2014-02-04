package com.beckwith.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Garbage extends GameObject {

	private Polygon garbage;
	double rotateDegrees = 0.0;
	double turnSpeed = 0.0;
	Random rand = new Random();
	private Area garbageArea;
	private double VelocityX;
	private double VelocityY;

	public Garbage(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);

		int xPoints[] = { 15, 30, 30, 15 };
		int yPoints[] = { 15, 15, 30, 30 };

		garbage = new Polygon(xPoints, yPoints, 4);

		turnSpeed = rand.nextInt(4);
		System.out.println("Speed == " + speed);
	}

	@Override
	public void clock() {
		double increment = turnSpeed * .1;

		rotateDegrees += increment;
		double dX;
		double direction;
		double xDif = 250 - X;
		double yDif = 250 - Y;
		direction = Math.atan((yDif) / (xDif));

		if (X > 250 && VelocityX > -3) {
			VelocityX-=.2;
		} else if (X < 250 && VelocityX < 3) {
			VelocityX+=.2;
		}
		if (Y > 250 && VelocityY > -3) {
			VelocityY-=.2;
			
		} else if (Y <= 250 && VelocityY < 3) {
			VelocityY+=.2;
		}
		X += ( VelocityX * Math.cos(Math.toRadians(direction)));
		Y += -(VelocityY * Math.sin(direction));
		
		
		//System.out.println("X = "+ X + " :  Y = " + Y);
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// BufferedImage img = createRotatedObject();
		GeneralPath path = createRotatedObject();
		// g2d.drawImage(img, X, Y, null);
		g2d.fill(path);
		if (born != true) {
			born = true;
		}

	}

	@Override
	public Rectangle getCollider() {
		Rectangle2D rec = new Rectangle2D.Double(X, Y, 30, 30);
		AffineTransform at = AffineTransform.getRotateInstance(rotateDegrees);
		Shape rotatedGarbage = at.createTransformedShape(rec);
		Rectangle garbageBounds = rotatedGarbage.getBounds();

		return garbageArea.getBounds();
	}

	public boolean containsPoint(int x, int y) {
		Rectangle r = getCollider();
		Point p = new Point(x, y);
		return r.contains(p);
	}

	private GeneralPath createRotatedObject() {

		AffineTransform at = new AffineTransform();

		double centerX = garbage.getBounds().getCenterX();
		double centerY = garbage.getBounds().getCenterY();

		at.translate(X + centerX, Y + centerY);
		at.rotate(rotateDegrees - Math.PI, centerX, centerY);
		GeneralPath path = new GeneralPath();
		path.append(garbage.getPathIterator(at), true);

		garbageArea = new Area(path);

		return path;

	}

}
