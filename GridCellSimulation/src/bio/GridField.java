package bio;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import res.SVList;

public class GridField {
	public BufferedImage img;
	
	public GridField(){
		img = new BufferedImage(SVList.MAP_WIDTH, SVList.MAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void addToImage(double posX, double posY){
		Graphics2D gbuffer = img.createGraphics();
        gbuffer.setColor(Color.RED);
        gbuffer.setComposite(AlphaComposite.SrcOver.derive(0.2f));
        gbuffer.fillOval((int)posX, (int)(SVList.MAP_HEIGHT-posY), 10, 10);
        gbuffer.dispose();
	}
}
