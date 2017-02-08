package sim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import map.Map;
import res.SVList;

public class Simulation extends JFrame implements Runnable{
	private static final long serialVersionUID = -3105073493091737856L;

	private Map map;
	public boolean running;
	
	/* we make a JFrame to hold a Canvas called map*/
	public Simulation(){
		super(SVList.NAME);
		map = new Map();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(map, BorderLayout.CENTER);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public synchronized void start(){
		running = true;
		/* call run method of this class */
		new Thread(this).start();
	}
	
	public synchronized void stop(){
		running = false;
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int frames = 0;
		int ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double change = 0;
		
		while(running){
			long now = System.nanoTime();
			change += (now - lastTime)/nsPerTick;
			lastTime = now;
			
			while(change >= 1){
				ticks++;
				tick();
				change -= 1;
				frames++;
				render();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer = System.currentTimeMillis();
				//System.out.println(ticks + ", " + frames);
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void tick(){
		map.tick();
	}
	public void render(){
		map.render();
	}
	public static void main(String[] args){
		new Simulation().start();
	}
}
