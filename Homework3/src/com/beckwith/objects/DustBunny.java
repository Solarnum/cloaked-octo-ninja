package com.beckwith.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class DustBunny extends GameObject {
	private BufferedImage dustBunnyImage;
	private double scaleSize;
	
	private int width = 50;
	private int height = 50;
	public DustBunny(int x, int y, int speed, double scale, ObjectID objID) {
		super(x, y, speed, objID);

		loadResource("DustBunny.png");
		scaleSize = scale;
		
		
		try {
			dustBunnyImage = ImageIO.read(input);
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
		BufferedImage dustBunnySprite = createSprite();
		
		g2d.drawImage(dustBunnySprite, (int) (X - (width/2)*scaleSize),(int)( Y
				- (width/2)*scaleSize), null);
		
		g2d.setColor(Color.red);
		g2d.draw(getCollider());

	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return new Rectangle((int) (X - (width/2)*scaleSize + 10*scaleSize), (int) (Y - (width/2)*scaleSize + 10*scaleSize), (int) (30*scaleSize), (int) (30*scaleSize));
	}
	
	private BufferedImage createSprite() {

		
		BufferedImage bimg = new BufferedImage(50, 50, BufferedImage.TRANSLUCENT);
		Graphics2D g = bimg.createGraphics();
		
		

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.scale(scaleSize, scaleSize);
		g.drawImage(dustBunnyImage,0,0,null);
		
		
		g.dispose();
		return bimg;


	}

}
