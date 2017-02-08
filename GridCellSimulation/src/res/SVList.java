package res;

public class SVList {
	/* CANVAS */
	public static final int MAP_SCALE = 3;
	public static final int MAP_WIDTH = 500;
	public static final int MAP_HEIGHT = 500;
	
	public static final String NAME = "GridCell Simulation";
	
	/* AGENT */
	public static String AGENT_IMG = "img/Agent.png";
	public static double[] AGENT_STARTING_POS = {100f, 200f};
	public static float startingAngle = (float)Math.PI/5;
	public static float startingSpeed = 1f;
	
	
	/* MODULE */
	/* grid cell related */
	public static double area = 1;
	public static double thresholdTrigger = 0.9;
	
	/* module related */
	public static int moduleNum = 1;
	public static int moduleSize = 10;
	
	/* follows pattern Ai = A(i-1)*(A(i-1)/2) */
	public static int[] moduleScale = {200, 8, 12, 18};
	
	
}
