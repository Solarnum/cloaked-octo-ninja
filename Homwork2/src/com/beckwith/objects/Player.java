package com.beckwith.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PaintContext;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Player extends GameObject {

	private Color playerColor = Color.GRAY;
	private Polygon triangle;
	private Double rotateDegrees = 1.0;
	private BufferedImage bufferedImage;

	public Player(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);

		// int xPoints[] = { 0, 20, 0 };
		// int yPoints[] = { 0, 20, 20 };

		int xPoints[] = { 10, 10, 20, 0, 15, 30, 20, 20, 10 };
		int yPoints[] = { 0, 20, 20, 20, 30, 20, 20, 0, 0 };

		triangle = new Polygon(xPoints, yPoints, 9);
		
		
	}

	@Override
	public void tick() {
		bufferedImage = createRotatedObject();
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bufferedImage, X, Y, null);

		
	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return null;
	}

	private BufferedImage createRotatedObject() {
		
		BufferedImage img = new BufferedImage(30, 30, BufferedImage.TRANSLUCENT);

		Graphics2D g = img.createGraphics();
		g.rotate(rotateDegrees - Math.PI, triangle.getBounds()
				.getCenterX(), triangle.getBounds().getCenterY());
		
		g.setColor(Color.RED);
		g.fill(triangle);
		
		g.dispose();
		return img;

	}



	public double calculateDegrees(int CenterX, int CenterY, int mouseX, int mouseY) {
		double angle = Math.atan2(CenterY + triangle.getBounds()
				.getCenterY() - mouseY, CenterX + triangle.getBounds()
				.getCenterX() - mouseX) - Math.PI
				/ 2;
		return angle;
	}
	
	public void setDegrees(double degrees){
		rotateDegrees = degrees;
	}
}
