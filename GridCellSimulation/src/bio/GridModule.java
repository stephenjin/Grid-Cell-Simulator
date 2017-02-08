package bio;

import map.Map;
import res.SVList;

public class GridModule {
	public float scale;
	public float distanceBetweenCells;
	
	/* xMod / yMod
	 * We only need to keep a row for X and a row for Y
	 * In order to get total spike values for x by y grid cells, 
	 * multiple each X with Y, and if the result comes out to be other than 0,
	 * save the index of X and Y, save the result and store it into spikes
	 */
	public GridCell[] xMod;
	public GridCell[] yMod;
	
	public class Spikes{
		/* x and y on module grid */
		public int x;
		public int y;
		public float spike;
		
		public Spikes(){nullify();};
		public void setSpike(int x, int y, float spike){
			this.x = x;
			this.y = y;
			this.spike = spike;
		}
		public void nullify(){
			x = -1;
			y = -1;
			spike = 0;
		}
	}
	/* spikes
	 * The list of spikes should be recalculated after each significant movement
	 * and keeps the 3 highest (there should only be 3 max that has some spike)
	 * spiking grid cells of the module.
	 * 
	 * In order to be efficient with the calculation time, we can reduce the
	 * number of calculations by only calculating adjacent cell's spike rates
	 */
	public Spikes[] spikes;
	
	/* CONSTRUCTOR */
		/* takes in 
		 * scale (diameter of the module)
		 * moduleSize (number of grid cells in module in a row)
		 * both numbers must be positive
		 */
	public GridModule(float scale, int moduleSize){
		this.scale = scale;
		this.distanceBetweenCells = scale/moduleSize;
		this.spikes = new Spikes[3];
	}
	
	/* tick
	 * Right now we are getting the position of the agent,
	 * but this will need to change to prediction with percepts
	 */
	public void tick(double posX, double posY){
		setSpikeValues(posX, posY);
	}
	
	/* setSpikeValues
	 * this is what should be called from Agent.
	 * it takes in a real world coordinate position (x,y)
	 * does linear mapping to grid cell parallelogram positions
	 * and allocates the appropriate spike value to modX and modY
	 */
	public void setSpikeValues(double x, double y){
		/* translation from 90 (x,y) is required */
		double[] modCoordinates = linearTransformation(x,y);
		
		double shiftX = find_PhaseShift(modCoordinates[0]);
		double shiftY = find_PhaseShift(modCoordinates[1]);
		
		/* testing code */
//		System.out.println(shiftX + ", " + shiftY);
//		System.out.println("-----------------------");
		if( ((shiftX >= (-Math.PI/20) && shiftX <= (Math.PI/20)) || (shiftX >= (19*Math.PI/20) && shiftX <= (21*Math.PI/20))) && 
			((shiftY >= (-Math.PI/20) && shiftY <= (Math.PI/20)) || (shiftY >= (19*Math.PI/20) && shiftY <= (21*Math.PI/20)))){
			System.out.println(shiftX + ", " + shiftY);
			Map.trigger = true;
		}else{
			Map.trigger = false;
		}
	}
	
	/* linearTransformation
	 * takes the coordinate grid in a square-like plane
	 * and transforms it to a coordinate grid in a parallelogram-like plane
	 * Uses the shape of the grid cell triggers found in Mosers' discovery
	 * with the pi/3 angle architecture
	 */
	public double[] linearTransformation(double x, double y){
//		System.out.println(x + ", " + y);
		double newX = x - (y * (1/Math.tan(Math.PI/3)));
		double newY = 0 + (y * (1/Math.sin(Math.PI/3)));
//		System.out.println(newX + ", " + newY);
		double[] modularCoords = {newX, newY};
		return modularCoords;
	}
	
	/* find_PhaseShift
	 * it will find the phase shift on a one dimensional line
	 * algorithm followed from the paper 'Navigation with Grid Cells'
	 * since we are measuring shifts with index, we are omitting the (* 2pi)
	 * and decided to product the outcome with the (numberofCells + 1)
	 * to get a double phase.
	 * 
	 * TODO: turn it in a phase, it will be much easier to see which grid cells fire in a module
	 */
	public double find_PhaseShift(double d){
		double phaseShift = ((d % scale)/scale) * (2 * Math.PI);
		//double phaseIndex = phaseShift * (SVList.moduleSize + 1);
		return phaseShift;
	}
	
	/* addToSpikes
	 * receives an array of grid cells, cannot be more than size 3
	 * and sets to spike
	 */
	public void addToSpikes(GridCell[] gc){
		int i = 0;
		for(; i<3; i++){
			spikes[i].setSpike(gc[i].xIndex, gc[i].yIndex, gc[i].spikeValue);
		}
		for(; i<3; i++){
			spikes[i].nullify();
		}
	}
	
	public boolean set_to_spike(int x, int y){
		for(Spikes s: spikes){
			if(s.x == x && s.y == y && s.spike > SVList.thresholdTrigger){
				return true;
			}
		}
		return false;
	}
}
