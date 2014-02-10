package com.beckwith.objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Battery extends GameObject {

	public Battery(int x, int y, int speed, ObjectID objID) {
		super(x, y, speed, objID);
		loadResource("Battery.png");
		
	}

	@Override
	public void clock() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return null;
	}

}
