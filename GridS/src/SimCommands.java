//We nake the command functions here and call them from the main file 
public class SimCommands {
	//this is x, y dimentions of the room and seed (for making simulation stable)
	void emptyRoom(int x, int y,int seed){
		System.out.println("\n*****Making Empty Room*****");
		
		Maze m1=new Maze(x, y,0,seed); 
		m1.showMaze();
	}
	//this is x, y dimentions of the room, probability of obstacles and seed.
	void obstacleRoom(int x, int y,int probability, int seed){
		System.out.println("\n*****Making Room with Obstacle*****");
		Maze m1=new Maze(x, y,probability,seed); 
		m1.showMaze();
	}
	//routing by considering no obstacle
	void routingNoObstacle(int x, int y, int seed){
		System.out.println("\n*****Routing Room without Obstacle*****");
		Maze m1=new Maze(x, y,0,seed); 
		
		AStarRepeatedGGreater aStarRGG = new AStarRepeatedGGreater(m1);		
		aStarRGG.findPath();
		
		GridCellModule gc = new GridCellModule(4);
		gc.showSpike(m1, 1);
		
		m1.showMaze();
	}
	//routing by considering obstacle
	void routingWithObstacle(int x, int y,int probability, int seed, int scale){
		//System.out.println("\n*****Routing Room with Obstacle*****");
		Maze m1=new Maze(x, y,probability,seed); 
		AStarRepeatedGGreater aStarRGG = new AStarRepeatedGGreater(m1);		
		aStarRGG.findPath();
		
		GridCellModule gc = new GridCellModule(scale);
		gc.showSpike(m1, 1);
		
		
		//P.percentageDistanceXError=0;
		//P.percentageDistanceYError=0;
		
		//for(int i=0;i<P.percentageScale;i++){
			Maze m2=new Maze(x, y,probability,seed); 
			AStarRepeatedGGreater aStarRGG2 = new AStarRepeatedGGreater(m2);		
			aStarRGG2.findPath();
			GridCellModule gc2 = new GridCellModule(scale);
			
			P.percentageDistanceXError=30;
			P.percentageDistanceYError=30;
			
			gc2.showSpike(m2, 1);
			
			//outerloop:
			for(int j=0;j<m1.maxXDimention;j++){
				for(int k=0;k<m1.maxYDimention;k++){
					if(m1.returnBlock(j, k).blockStatus==6)
					if(m1.returnBlock(j, k).blockStatus!=m2.returnBlock(j, k).blockStatus){
						P.errorCounter++;
						//break outerloop;
					}
				}
				
			}
			
			//System.out.println(i+1);
		//}
		
		//m1.showMaze();
	}
}
