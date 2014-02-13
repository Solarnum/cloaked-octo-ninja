package com.beckwith.objects;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.beckwith.Game.Main;
import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class Background extends GameObject{

	private int WIDTH = 32;
	private int HEIGHT = 32;
	private Color HUE;
	private BufferedImage carpetImage;
	
	public Background(int x, int y,int width, int height, int speed, Color hue, ObjectID objID) {
		super(x, y, speed, objID);
		HUE = hue;
		WIDTH = Main.WIDTH;
		HEIGHT = Main.HEIGHT;
		loadResource("linoleum.png");
		try {
			carpetImage = ImageIO.read(input);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void clock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage carpetSprite = createImg();
		for(int i = 0; i < (WIDTH/100);i++){
			for(int k = 0; k < (HEIGHT/100);k++){
				g2d.drawImage(carpetImage,i*100,k*100,null);
			}
		}
		
	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return new Rectangle(0,0,WIDTH,HEIGHT);
	}
	
	private BufferedImage createImg() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(carpetImage,0,0,null);
        
        
        return image;
    }

}
