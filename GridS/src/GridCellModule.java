
public class GridCellModule {
	public double scale;
	public double numOfCells;
	double half_angle_constant;
	
	public GridCellModule(double scale){
		this.scale = scale;
		this.numOfCells = scale;
		this.half_angle_constant = (Math.PI/scale);
	}
	
	/* module contains scale x scale amount of cells, so cell_to_spike boundaries are 1 ~ scale^2*/
	public void showSpike(Maze m1, int cell_to_spike){
		if(cell_to_spike <= 0 || cell_to_spike > scale*scale)
			return;
		
		//System.out.println("SCALE: " + scale);
		//System.out.println("Cell Spiked: " + cell_to_spike);
		/* determine which cells trigger */
		for(Block[] by: m1.maze){
			for(Block b: by){
				// b.y and b.x are flipped
				if(b.blockStatus == 4 &&
						gridCellTrigger(m1.startXDimention, m1.startYDimention, b.yDimention, b.xDimention, cell_to_spike))
					b.blockStatus = 6;
			}
		}
	}
	
	public boolean gridCellTrigger(int startXIndex, int startYIndex, int x, int y, int cell_to_spike){
		double distanceX = x - (startXIndex - 1);
		double distanceY = (startYIndex - 1) - y;
		
		distanceX+=(P.percentageDistanceXError*distanceX)/P.percentageScale;
		distanceY+=(P.percentageDistanceYError*distanceY)/P.percentageScale;
		
		double phaseShiftX = find_PhaseShift(distanceX);
		double phaseShiftY = find_PhaseShift(distanceY);
		
		
		
		
		
		// make phaseShift strictly in between values -half_angle_constant ~ (2 pi - half_angle_constant)
//		if(phaseShiftX >= 2*Math.PI - half_angle_constant)
//			phaseShiftX -= 2 * Math.PI;
//		else if(phaseShiftX < 0 - half_angle_constant)
//			phaseShiftX += 2 * Math.PI;
//		if(phaseShiftY >= 2*Math.PI - half_angle_constant)
//			phaseShiftY -= 2 * Math.PI;
//		else if(phaseShiftX < 0 - half_angle_constant)
//			phaseShiftY += 2 * Math.PI;
		
		return spike(phaseShiftX, phaseShiftY, cell_to_spike);
	}
	
	/* the formula does not work for walking backwards, that is to say, when distance formula generates
	 * a negative distance
	 * Therefore, we need to add to d the distance that one module covers, 
	 * which happens to be the scale on this implementation
	 */
	public double find_PhaseShift(double d){
		if(d < 0)
			d += scale;
		double phaseShift = ((d % scale)/scale) * (2 * Math.PI);
		//double phaseIndex = phaseShift * (SVList.moduleSize + 1);
		return phaseShift;
	}
	
	public boolean spike(double phaseShiftX, double phaseShiftY, int cell_to_spike){
		int cell_to_spike_shift_X = (int)((cell_to_spike-1)%scale);
		int cell_to_spike_shift_Y = (int)((cell_to_spike-1)/scale);

		// there is no chance that the upper_limits go above 2*Math.PI
		double highestSpike_X = (2*Math.PI*cell_to_spike_shift_X/scale);
		double highestSpike_Y = (2*Math.PI*cell_to_spike_shift_Y/scale);
		double lower_limit_X = highestSpike_X - half_angle_constant;
		double upper_limit_X = highestSpike_X + half_angle_constant;
		double lower_limit_Y = highestSpike_Y - half_angle_constant;
		double upper_limit_Y = highestSpike_Y + half_angle_constant;
		
		if( phaseShiftX < upper_limit_X && phaseShiftX >= lower_limit_X &&
			phaseShiftY < upper_limit_Y && phaseShiftY >= lower_limit_Y){
			return true;
		}else
			return false;
	}
}
