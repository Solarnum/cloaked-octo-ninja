package com.schlottke.DrivingBlock;

import java.applet.Applet;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import com.schlottke.DrivingBlock.framework.*;
import com.schlottke.DrivingBlock.objects.*;


public class Game extends Applet implements Runnable {	
	private static final long serialVersionUID = -6742937428111486414L;
	public static LinkedList<GameObject> objects;
	public static int WIDTH, HEIGHT;
	
	Thread t = null;
	Image offscreen = null;
	Graphics buffer;
	Random rand = new Random();
	
	public void init(){
		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();
		objects = new LinkedList<GameObject>();
	}
	
	public void start(){
		//Background
		Block background = new Block(0, 0, WIDTH, HEIGHT, 0, Color.BLACK, ObjectID.Block);
		//Buildings
		Block building1 = new Block(50, 50, 150, 150, 0, Color.GRAY, ObjectID.Block);
		Block building2 = new Block(250, 50, 150, 150, 0, Color.GRAY, ObjectID.Block);
		Block building3 = new Block(50, 250, 150, 150, 0, Color.GRAY, ObjectID.Block);
		Block building4 = new Block(250, 250, 150, 150, 0, Color.GRAY, ObjectID.Block);
		
		// Generate the Car
		Car car = new Car(210, 210, 30, 30, 5, Color.RED, ObjectID.Block);
		
		Person person = new Person(210, 100, 0, ObjectID.Person, Color.magenta);
		
		objects.add(background);
		objects.add(building1);
		objects.add(building2);
		objects.add(building3);
		objects.add(building4);
		objects.add(car);
		objects.add(person);
		
		t = new Thread(this);
	  	t.start();		
	}
	
	public void run() {
		while (t != null)
	    {
		    repaint();
		    try {
		    	Thread.sleep(40);
		    } catch (InterruptedException e) {}
	    }
	}
	
	public void update(Graphics g){
		for(GameObject go : objects){
			go.tick();
		}
		
	  	if (offscreen == null)
	  	{
	  		offscreen = createImage(WIDTH,HEIGHT);
	  		buffer = offscreen.getGraphics();
	  	}
	  	
	  	paint(buffer);
	  	g.drawImage(offscreen,0,0,null);
	}
	
	public void paint(Graphics g) {		
		for(GameObject go : objects){
			go.render(g);
		}
	}
	
	public void stop() {
	    t = null;  // stop animation thread
	  } 

}
