package com.schlottke.DrivingBlock.objects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.schlottke.DrivingBlock.Game;
import com.schlottke.DrivingBlock.framework.GameObject;
import com.schlottke.DrivingBlock.framework.ObjectID;

public class Car extends GameObject {
  public enum LocState{
    MoveRight,
    MoveLeft,
    MoveUp,
    MoveDown
  }
  
	private int WIDTH = 32;
	private int HEIGHT = 32;
	private Color HUE;
	private boolean move1, move2;
	private LocState state;
	
	public Car(int x, int y, int width, int height, int speed, Color hue, ObjectID id) {
		super(x, y, speed, id);
		
		HUE = hue;
		WIDTH = width;
		HEIGHT = height;
		move1 = false;
		move2 = false;
		VELX = VELY = SPEED;
		state = LocState.MoveRight;
	}


	public void tick() {
		if(!move1 && !move2){
			X += VELX;
			
			if(X >= 410){
				X = 410;
				move1 = true;
			}
		}
		else if(move1 && !move2){
			Y -= VELY;
			
			if(Y <= 10){
				Y = 10;
				move2 = true;
			}			
		}
		else if(move1 && move2){
			X -= VELX;
			
			if(X <= 210){
				X = 210;
				move1 = false;
			}				
		}
		else{
			Y += VELY;
			
			if(Y >= 210){
				Y = 210;
				move2 = false;
			}			
		}	
		
		// Keeps the block in the game window
		if(Y < 0)
			Y = 0;
		if(X < 0)
			X = 0;
		if((Y+HEIGHT) > Game.HEIGHT)
			Y = Game.HEIGHT - HEIGHT;
		if((X+WIDTH) > Game.WIDTH)
			X = Game.WIDTH - WIDTH;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage img = createGradientImg(WIDTH, HEIGHT, HUE.brighter(), HUE.darker(), true);
		g2d.drawImage(img, X, Y, null);
		
		//Draw the Collider's Bounds
		g2d.setColor(HUE.darker());	
		g2d.draw(getCollider());		
	}

	public Rectangle getCollider() {
		return new Rectangle( X, Y, WIDTH, HEIGHT);
	}
	
	public int getWidth(){
		return WIDTH;
	}
	
	public int getHeight(){
		return HEIGHT;
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
