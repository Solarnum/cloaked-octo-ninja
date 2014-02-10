package com.beckwith.framework;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.URL;

public abstract class GameObject {

	protected double X, Y;
	protected int SPEED, VELX, VELY;
	protected URL input;
	protected boolean alive = true;
	protected boolean born =false;
	protected ObjectID ID;
	protected double rotateDegrees = 0.0;
	
	public GameObject(int x, int y, int speed, ObjectID objID){
		X=x;
		Y=y;
		SPEED = speed;
		ID = objID;
	}
	
	public abstract void clock();
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
	
	protected void print(String s){
		System.out.println(s);
	}
	
	protected void loadResource(String s){
		input = this.getClass().getClassLoader().getResource(s);
	}
	
	public void switchDirections()
	{
		print("switch Directions");
		rotateDegrees = rotateDegrees - Math.PI/2;
	}
	
}
