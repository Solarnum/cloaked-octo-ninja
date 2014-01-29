package com.schlottke.DrivingBlock.framework;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CreateGradientImg {

	public BufferedImage createGradientImg(int width, int height, Color first, Color second, boolean vertical) {
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
