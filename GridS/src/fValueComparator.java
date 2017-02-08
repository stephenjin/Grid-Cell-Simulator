import java.util.Comparator;

public class fValueComparator implements Comparator<Block>{
	@Override
    public int compare(Block b1, Block b2){
		if(b1.fValue > b2.fValue)
			return 1;
		else if(b1.fValue == b2.fValue)
			return 0;
		else
			return -1;
	}

}
