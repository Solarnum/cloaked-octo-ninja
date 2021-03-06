package com.beckwith.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PaintContext;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D.Double;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import javax.swing.border.StrokeBorder;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Player extends GameObject {

	private Color playerColor = Color.GRAY;
	private Polygon turret;
	private double rotateDegrees = 1.0;
	private BufferedImage bufferedImage;
	private Ellipse2D.Double turretBase;
	private Ellipse2D.Double turretSubBase;

	public Player(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);

		int xPoints[] = { 35, 35, 45, 45, 35 };
		int yPoints[] = { 40, 75, 75, 30, 30 };

		turret = new Polygon(xPoints, yPoints, 5);
		turretBase = new Ellipse2D.Double(20, 20, 40, 40);
		turretSubBase= new Ellipse2D.Double(30, 30, 20, 20);
	}

	@Override
	public void clock() {
		bufferedImage = createRotatedObject();

	}

	@Override
	public void render(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		if (bufferedImage != null) {
			g2d.drawImage(bufferedImage, (int) (X - bufferedImage.getWidth() / 2),(int)( Y
					- bufferedImage.getHeight() / 2), null);
		}
	}

	@Override
	public Rectangle getCollider() {

		return null;
	}

	private BufferedImage createRotatedObject() {

		BufferedImage img = new BufferedImage(80, 80, BufferedImage.TRANSLUCENT);

		Graphics2D g = img.createGraphics();
		// g.rotate(rotateDegrees - Math.PI, triangle.getBounds()
		// .getCenterX(), triangle.getBounds().getCenterY());
		g.rotate(rotateDegrees - Math.PI, 40, 40);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLUE);
		g.fill(turretBase);
		g.setColor(Color.black);
		g.draw(turretBase);
		g.setColor(Color.BLUE);
		g.fill(turret);
		g.setColor(Color.black);
		
		g.setStroke(new BasicStroke(4));
		g.draw(turretSubBase);
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.black);
		g.draw(turret);
		g.setColor(Color.BLUE);
		g.fill(turretSubBase);
		
		

		g.dispose();
		return img;

	}

	public double[] getTurretPosition() {
		double degreePos = 0.0;

		double xPosition = Math.cos(rotateDegrees - Math.PI / 2)
				* (turret.getBounds().getHeight() - 10);

		double yPosition = Math.sin(rotateDegrees - Math.PI / 2)
				* (turret.getBounds().getHeight() - 10);

		double turretXPos = X;
		double turretYPos = Y;

		turretXPos = turretXPos + xPosition;
		turretYPos = turretYPos + yPosition;

		double turretPosition[] = { turretXPos, turretYPos };
		return turretPosition;
	}

	public double calculateDegrees(int CenterX, int CenterY, int mouseX,
			int mouseY) {
		double angle = Math.atan2(CenterY - mouseY, CenterX - mouseX) - Math.PI
				/ 2;
		return angle;
	}

	public void setDegrees(double degrees) {
		rotateDegrees = degrees;
	}

	public double getDegrees() {
		return rotateDegrees;
	}

}
