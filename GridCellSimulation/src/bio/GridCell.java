package bio;

import res.SVList;

public class GridCell {
	public boolean spike;
	/* spikeValue 
	 * Measuring how close the allocated place is with this specific grid cell,
	 * For now, I intend to leave it as a float, but another implementation 
	 * would be to use this to make a more spike-focused implementation.
	 * 
	 * 1 - on point // 0 - no spike
	 * 
	 * The scale of the area which this spikes is decided by the module scale
	 */
	public float spikeValue;
	
	/* Index
	 * describes phase shift in a module of this specific cell
	 */
	int xIndex;
	int yIndex;
	
	public GridCell(int x, int y){
		spikeValue = 0;
		spike = false;
	}
	
	public void trigger(){
		if(spikeValue >= SVList.thresholdTrigger)
			spike = true;
	}
	
}
