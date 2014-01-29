package com.schlottke.DrivingBlock.framework;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	protected int X, Y, SPEED, VELX, VELY;
	protected ObjectID ID;
	
	public GameObject(int x, int y, int speed, ObjectID id){
		X = x;
		Y = y;
		SPEED = speed;
		ID = id;		
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getCollider();

	public ObjectID getID(){
		return ID;
	}
}
