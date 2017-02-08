import java.util.ArrayList;
import java.util.Collections;

public class AStarRepeatedGGreater {

	ArrayList<Block> openBlocks=	new  ArrayList<Block>();
	Maze realMaze;
	Maze tempMaze;
	Block currentState;// = maze[maze.startXDimention][maze.startYDimention];
	
	public AStarRepeatedGGreater(Maze m){
		realMaze=m;
		tempMaze= new Maze(m.maxXDimention,m.maxYDimention);
	}
	public void findPath(){
		
		while((realMaze.startXDimention != realMaze.endXDimention) || (realMaze.startYDimention!= realMaze.endYDimention)){
			//main.countRouting++;
			openBlocks.clear();
			for(int i=0;i<tempMaze.maxXDimention;i++){
				for(int j=0;j<tempMaze.maxYDimention;j++){
					if(tempMaze.returnBlock(i, j).blockStatus == 4){
						tempMaze.returnBlock(i, j).blockStatus=0;
					}
				}
			}
			tempMaze.endXDimention=realMaze.endXDimention;
			tempMaze.endYDimention=realMaze.endYDimention;
			tempMaze.startXDimention=realMaze.startXDimention;
			tempMaze.startYDimention=realMaze.startYDimention;
			currentState = tempMaze.returnBlock(tempMaze.startXDimention, tempMaze.startYDimention); 
			currentState.gValue=0;
			
			while((currentState.xDimention != tempMaze.endXDimention) || (currentState.yDimention!= tempMaze.endYDimention)){
				//main.countComputation++;
				addToOpenBlocks();	
				if(openBlocks.size()>0){
					currentState = chooseNextState();					
					if(currentState.blockStatus==0){
						currentState.blockStatus = 4;
					}
				}
				else{
					System.out.println("Error! There is no way we can reach destination!!");
					return;
				}
			}//tempMaze.showMaze();	System.out.println("Tracing A*");			
			while((currentState.xDimention != tempMaze.startXDimention) || (currentState.yDimention!= tempMaze.startYDimention)){
				currentState.parentBlock.childBlock=currentState;
				currentState= currentState.parentBlock;
				//countShortestPath++;
			}
			while((currentState.xDimention != tempMaze.endXDimention) || (currentState.yDimention!= tempMaze.endYDimention)){
				
				if(realMaze.returnBlock(currentState.xDimention, currentState.yDimention).blockStatus==0)
					realMaze.returnBlock(currentState.xDimention, currentState.yDimention).blockStatus=4;
				if(realMaze.returnBlock(currentState.xDimention, currentState.yDimention).blockStatus==1){
					tempMaze.returnBlock(currentState.xDimention, currentState.yDimention).blockStatus=1;
					currentState=currentState.parentBlock;
					realMaze.startXDimention = currentState.xDimention;
					realMaze.startYDimention = currentState.yDimention;
					realMaze.returnBlock(realMaze.startXDimention, realMaze.startYDimention).blockStatus=2;
					break;
				}
				
				currentState= currentState.childBlock;
				//countShortestPath++;
			}
			if(realMaze.returnBlock(currentState.xDimention, currentState.yDimention).blockStatus==3){
				realMaze.startXDimention = currentState.xDimention;
				realMaze.startYDimention = currentState.yDimention;
				break;
			}
			//realMaze.showMaze();System.out.println("Tracing Repeated A*");
		}	//System.out.println(currentState.gValue);
		//
	}
	public Block chooseNextState() {
		//to get the correct destination we have to find a first the f value and then if f values are equal find nearest h value
		int index=0;
		int counter = 0;
		Collections.sort(openBlocks,new fValueComparator());
		Block minFMinGValue = new Block();
		minFMinGValue=openBlocks.get(0);		
		
		while((counter + 1<openBlocks.size()) && (openBlocks.get(counter).fValue == openBlocks.get(counter+1).fValue)){
			if(openBlocks.get(counter+1) == null)
				break;
			if(openBlocks.get(counter+1).blockStatus == 3){
				minFMinGValue = openBlocks.get(counter+1);
				index=counter+1;
				break;
			}
			if(minFMinGValue.gValue < openBlocks.get(counter+1).gValue){
				minFMinGValue = openBlocks.get(counter+1);
				index=counter+1;
				
			}
			counter++;
		}
		
		openBlocks.remove(index);
		return minFMinGValue;		
		
	}
	private void addToOpenBlocks() {
		if(tempMaze.returnBlock(currentState.xDimention - 1 ,  currentState.yDimention) != null
				&& (tempMaze.returnBlock(currentState.xDimention-1,  currentState.yDimention).blockStatus==0
				|| tempMaze.returnBlock(currentState.xDimention-1,  currentState.yDimention).blockStatus==3)){
			if(openBlocks.contains(tempMaze.returnBlock(currentState.xDimention - 1, currentState.yDimention)) == false){
				tempMaze.returnBlock(currentState.xDimention - 1, currentState.yDimention).hValue=Math.abs((currentState.xDimention - 1)-tempMaze.endXDimention) + Math.abs(currentState.yDimention-tempMaze.endYDimention);				
				tempMaze.returnBlock(currentState.xDimention - 1, currentState.yDimention).gValue=tempMaze.returnBlock(currentState.xDimention, currentState.yDimention).gValue+1;
				tempMaze.returnBlock(currentState.xDimention - 1, currentState.yDimention).fValue=tempMaze.returnBlock(currentState.xDimention - 1, currentState.yDimention).gValue + tempMaze.returnBlock(currentState.xDimention - 1, currentState.yDimention).hValue;
				openBlocks.add(tempMaze.returnBlock(currentState.xDimention - 1, currentState.yDimention));
				tempMaze.returnBlock(currentState.xDimention - 1, currentState.yDimention).parentBlock=tempMaze.returnBlock(currentState.xDimention , currentState.yDimention);	
			}
		}
		if(tempMaze.returnBlock(currentState.xDimention + 1 , currentState.yDimention) != null
				&& (tempMaze.returnBlock(currentState.xDimention+1, currentState.yDimention).blockStatus==0
				|| tempMaze.returnBlock(currentState.xDimention+1, currentState.yDimention).blockStatus==3)){
			if(openBlocks.contains(tempMaze.returnBlock(currentState.xDimention + 1, currentState.yDimention)) == false){
				tempMaze.returnBlock(currentState.xDimention + 1, currentState.yDimention).hValue=Math.abs((currentState.xDimention + 1)-tempMaze.endXDimention) + Math.abs(currentState.yDimention-tempMaze.endYDimention);				
				tempMaze.returnBlock(currentState.xDimention + 1, currentState.yDimention).gValue=tempMaze.returnBlock(currentState.xDimention, currentState.yDimention).gValue+1;
				tempMaze.returnBlock(currentState.xDimention + 1, currentState.yDimention).fValue=tempMaze.returnBlock(currentState.xDimention + 1, currentState.yDimention).gValue + tempMaze.returnBlock(currentState.xDimention + 1, currentState.yDimention).hValue;				
				openBlocks.add(tempMaze.returnBlock(currentState.xDimention + 1, currentState.yDimention));
				tempMaze.returnBlock(currentState.xDimention + 1,  currentState.yDimention).parentBlock=tempMaze.returnBlock(currentState.xDimention ,  currentState.yDimention);	
			}
		}
		if(tempMaze.returnBlock(currentState.xDimention, currentState.yDimention - 1) != null
				&& (tempMaze.returnBlock(currentState.xDimention, currentState.yDimention - 1).blockStatus==0
				|| tempMaze.returnBlock(currentState.xDimention, currentState.yDimention - 1).blockStatus==3)){
			if(openBlocks.contains(tempMaze.returnBlock(currentState.xDimention, currentState.yDimention - 1)) == false){
				tempMaze.returnBlock(currentState.xDimention, currentState.yDimention -1).hValue=Math.abs(currentState.xDimention -tempMaze.endXDimention) + Math.abs((currentState.yDimention-1)-tempMaze.endYDimention);				
				tempMaze.returnBlock(currentState.xDimention, currentState.yDimention-1).gValue=tempMaze.returnBlock(currentState.xDimention, currentState.yDimention).gValue+1;
				tempMaze.returnBlock(currentState.xDimention, currentState.yDimention-1).fValue=tempMaze.returnBlock(currentState.xDimention, currentState.yDimention-1).gValue + tempMaze.returnBlock(currentState.xDimention, currentState.yDimention-1).hValue;
				openBlocks.add(tempMaze.returnBlock(currentState.xDimention, currentState.yDimention - 1));
				tempMaze.returnBlock(currentState.xDimention , currentState.yDimention - 1).parentBlock=tempMaze.returnBlock(currentState.xDimention , currentState.yDimention);
			}
		}
		if(tempMaze.returnBlock(currentState.xDimention,  currentState.yDimention + 1) != null
				&& (tempMaze.returnBlock(currentState.xDimention,  currentState.yDimention + 1).blockStatus==0
				|| tempMaze.returnBlock(currentState.xDimention,  currentState.yDimention + 1).blockStatus==3)){
			if(openBlocks.contains(tempMaze.returnBlock(currentState.xDimention, currentState.yDimention + 1)) == false){
				tempMaze.returnBlock(currentState.xDimention, currentState.yDimention +1).hValue=Math.abs(currentState.xDimention -tempMaze.endXDimention) + Math.abs((currentState.yDimention+1)-tempMaze.endYDimention);								
				tempMaze.returnBlock(currentState.xDimention, currentState.yDimention+1).gValue=tempMaze.returnBlock(currentState.xDimention, currentState.yDimention).gValue+1;
				tempMaze.returnBlock(currentState.xDimention, currentState.yDimention+1).fValue=tempMaze.returnBlock(currentState.xDimention, currentState.yDimention+1).gValue + tempMaze.returnBlock(currentState.xDimention, currentState.yDimention+1).hValue;				
				openBlocks.add(tempMaze.returnBlock(currentState.xDimention, currentState.yDimention + 1));
				tempMaze.returnBlock(currentState.xDimention, currentState.yDimention + 1).parentBlock=tempMaze.returnBlock(currentState.xDimention, currentState.yDimention);	
			}
		}
		
	}
}