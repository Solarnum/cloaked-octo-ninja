package com.beckwith.framework;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class GameObject {

	protected int X, Y, SPEED, VELX, VELY;
	protected boolean alive = true;
	protected boolean born =false;
	protected ObjectID ID;;
	
	public GameObject(int x, int y, int speed, ObjectID objID){
		X=x;
		Y=y;
		SPEED = speed;
		ID = objID;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getCollider();
	
	public boolean isAlive(){
		return alive;
	}
	public boolean born(){
		return born;
	}
	
	public void destroy(){
		alive = false;
	}
	
	public ObjectID getID(){
		return ID;
	}
	
	public boolean checkCollision(Rectangle intersector){
		return getCollider().getBounds().intersects(intersector);
	}
	
	public boolean containsPoint(int x, int y){
		Rectangle r = getCollider();
		Point p = new Point(x, y);
		return r.contains(p);
	}
	
	public boolean containsRect(Rectangle r){
		return getCollider().contains(r);
	}
	
	
}
