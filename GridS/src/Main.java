
public class Main {

	public static void main(String[] args) {
		System.out.println("*****The simulation begins*****");
		SimCommands sc = new SimCommands();
		//sc.emptyRoom(10, 10, 10);
		
		//sc.obstacleRoom(10, 10,2,10);
		
		//sc.routingNoObstacle(10, 10, 10);
		//for(int k=3;k<10;k++){
			P.errorCounter=0;
			sc.routingWithObstacle(40, 40, 5, 11,2);
			System.out.println(P.errorCounter);
			
		//}
	}

}
