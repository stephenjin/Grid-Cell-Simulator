package map;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import bio.Agent;
import bio.GridField;
import res.SVList;

public class Map extends Canvas{
	private static final long serialVersionUID = -3693222175295884149L;

	private Dimension dimension;
	
	public BufferedImage img;
	public int[] pixels;
	
	public Agent agent;
	public GridField gField;
	public static boolean trigger = false;
	
	public Map(){
		super();
		/* set size */
		dimension = new Dimension(SVList.MAP_WIDTH, SVList.MAP_HEIGHT);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		setPreferredSize(dimension);
		
		/* set background */
		img = new BufferedImage(SVList.MAP_WIDTH, SVList.MAP_HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	
		/* set agent */
		agent = new Agent();
		
		/* set grid trigger field */
		gField = new GridField();
	}
	
	public void tick(){
		agent.tick();
	}
	
	public void render(){
		BufferStrategy buff = getBufferStrategy();
		if(buff == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = buff.getDrawGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		/* this is where we add to graphics 
		 * START
		 */
		// draw grid field
		g.drawImage(gField.img, 0, 0, null);
		// update grid field
		if(trigger){
			gField.addToImage(agent.posX, agent.posY);
		}
		// draw agent
		g.drawImage(agent.img, (int)agent.posX, (int)translateY(agent.posY), null);
		
		/* End */
		g.dispose();
		buff.show();
	}
	
	public static double translateY(double posY){
		return SVList.MAP_HEIGHT-posY;
	}
}
