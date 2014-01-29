package com.schlottke.DrivingBlock.objects;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.schlottke.DrivingBlock.framework.CreateGradientImg;
import com.schlottke.DrivingBlock.framework.GameObject;
import com.schlottke.DrivingBlock.framework.ObjectID;
import com.schlottke.DrivingBlock.objects.Car.LocState;

public class Person extends GameObject{

	private int WIDTH = 5;
	private int HEIGHT = 10;
	private Color HUE;
	private boolean move1, move2;
	private LocState state;
	
	public Person(int x, int y, int speed, ObjectID id, Color hue) {
		super(x, y, speed, id);
		
		HUE = hue;
		move1 = false;
		move2 = false;
		VELX = VELY = SPEED;
		state = LocState.MoveRight;
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		CreateGradientImg cGI = new CreateGradientImg();
		BufferedImage img = createGradientImg(WIDTH, HEIGHT, HUE.brighter(), HUE.darker(), true);
		g2d.drawImage(img, X, Y, null);
		
		//Draw the Collider's Bounds
		g2d.setColor(HUE.darker());	
		g2d.draw(getCollider());		
		
	}

	@Override
	public Rectangle getCollider() {
		return new Rectangle( X, Y, WIDTH, HEIGHT);
		
	}
	
	private BufferedImage createGradientImg(int width, int height, Color first, Color second, boolean vertical) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        GradientPaint gradient;
        
        if(vertical)
        	gradient = new GradientPaint(0f, 0f, first, 0f, height, second);
        else
        	gradient = new GradientPaint(0f, 0f, first, width, 0f, second); 
        
        g.setPaint(gradient);
        g.fillRect(0, 0, width, height);
        g.dispose();
        return image;
    }

}
