package com.beckwith.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.beckwith.Game.Main;
import com.beckwith.framework.GameObject;
import com.beckwith.framework.ObjectID;

public class DustBunny extends GameObject {
	private BufferedImage dustBunnyImage;
	private double scaleSize;

	private int width = 50;
	private int height = 50;

	private Random rand = new Random();

	
	public DustBunny(int x, int y, int speed, double scale, ObjectID objID) {
		super(x, y, speed, objID);

		loadResource("DustBunny.png");
		scaleSize = scale;
		if (scaleSize < .3)
			scaleSize = .2 + scaleSize;
		
		rotateDegrees = rand.nextDouble() * 360;

		try {
			dustBunnyImage = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void clock() {
		if (isAlive()) {
			double tempx = X;
			double tempy = Y;
			X += (1.5 * Math.cos(Math.toRadians(rotateDegrees)));
			Y += (1.5 * Math.sin(Math.toRadians(rotateDegrees)));
			if (Y < 0){
				Y= Main.HEIGHT -1;
			}else if(Y > Main.HEIGHT ) {
				Y = 0 ;
				//rotateDegrees = rotateDegrees - Math.PI * 180;
			}

			if (X < 0){
				X = Main.WIDTH ;
			}
			else if( X > Main.WIDTH) {
				X = 0;
				//rotateDegrees = rotateDegrees - Math.PI * 180;
			}
		}

	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage dustBunnySprite = createSprite();

		g2d.drawImage(dustBunnySprite, (int) (X - (width / 2) * scaleSize),
				(int) (Y - (width / 2) * scaleSize), null);

		//g2d.setColor(Color.red);
		//g2d.draw(getCollider());

	}

	@Override
	public Rectangle getCollider() {
		// TODO Auto-generated method stub
		return new Rectangle(
				(int) (X - (width / 2) * scaleSize + 10 * scaleSize), (int) (Y
						- (width / 2) * scaleSize + 10 * scaleSize),
				(int) (30 * scaleSize), (int) (30 * scaleSize));
	}

	private BufferedImage createSprite() {

		BufferedImage bimg = new BufferedImage(50, 50,
				BufferedImage.TRANSLUCENT);
		Graphics2D g = bimg.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.scale(scaleSize, scaleSize);
		g.drawImage(dustBunnyImage, 0, 0, null);

		g.dispose();
		return bimg;

	}

	@Override
	public void destroy() {
		alive = false;
		System.out.println("Called");
		loadResource("AD.png");
		try {
			dustBunnyImage = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	


}
