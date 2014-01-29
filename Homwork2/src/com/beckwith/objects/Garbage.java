package com.beckwith.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Garbage extends GameObject{

	private Polygon garbage;
	
	public Garbage(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);
		
		
		int xPoints[] = {X, X+15, X+15, X};
		int yPoints[] = {Y, Y,Y+15, Y+15};
		
		garbage = new Polygon(xPoints, yPoints, 4);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(garbage);
		g2d.fill(garbage);
		
		g2d.dispose();
		
	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return null;
	}

}
