package com.beckwith.objects;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Background extends GameObject{

	private int WIDTH = 32;
	private int HEIGHT = 32;
	private Color HUE;
	
	public Background(int x, int y,int width, int height, int speed, Color hue, ObjectID objID) {
		super(x, y, speed, objID);
		HUE = hue;
		WIDTH = width;
		HEIGHT = height;
	}

	@Override
	public void clock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage img = createGradientImg(WIDTH, HEIGHT, HUE.brighter(), HUE.darker(), true);
		g2d.drawImage(img, (int) X, (int) Y, null);
		
	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return new Rectangle(0,0,WIDTH,HEIGHT);
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
