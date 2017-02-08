package bio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import map.Map;
import res.SVList;

/* test class to test grid modules */
public class Agent {
	public BufferedImage img;
	
	/* THESE POSITIONS
	 * are from a 0,0 scale, increasing. 
	 * These are not drawn to the map just like this, when being drawn
	 * the Y needs to be subtracted from the total height of the map,
	 * which Map.translateY() function does.
	 */
	public double posX;
	public double posY;
	
	public float speed;
	public float angle;
	
	GridModule[] modules;
	
	public Agent(){
		/* set image of agent */
		try {
			img = ImageIO.read(new File(SVList.AGENT_IMG));
		} catch (IOException e) {e.printStackTrace();}
		
		/* set brain */
		modules = new GridModule[SVList.moduleNum];
		for(int x=0; x<SVList.moduleNum; x++){
			modules[x] = new GridModule(SVList.moduleScale[x], SVList.moduleSize);
		}
		
		/* set starting position */
		move(SVList.AGENT_STARTING_POS[0], SVList.AGENT_STARTING_POS[1]);
		
		/* set attributes */
		speed = SVList.startingSpeed;
		angle = SVList.startingAngle;
	}

	public void move(double posX, double posY){
		this.posX = posX;
		this.posY = posY;
	}
	
	public void tick(){
		double newPosX = posX + speed * Math.cos(angle);
		double newPosY = posY + speed * Math.sin(angle);
		
		double imgWidth = img.getWidth();
		double imgHeight = img.getHeight();
		
		if(newPosX >= SVList.MAP_WIDTH-imgWidth ||
				newPosX <= 0+imgWidth){
			angle = (float)Math.PI - angle;
		}
		if(newPosY >= SVList.MAP_HEIGHT-imgHeight ||
				newPosY <= 0+imgHeight){
			angle = 0f - angle;
		}
		
		for(int x=0; x<SVList.moduleNum; x++){
			modules[x].tick(newPosX, newPosY);
		}
		
		/* testing
		if((int)newPosX % 50 == 0)
			Map.trigger = true;
		else
			Map.trigger = false;
		*/
		move(newPosX, newPosY);
	}
	
	public void printPosition(){
		System.out.println(posX + ", " + posY);
	}
}
