

/**
 * 
 * 
 * @author Ajinkya
 * This class creates the object inorder to process in the priority que
 * it implements the comparable interface used by priority que
 *
 */
public class Elements_identifier implements Comparable<Elements_identifier>{

	int element;
	String slave;
	
	public Elements_identifier(int element, String slave){
		this.element = element;
		this.slave = slave;
	}

	@Override
	public int compareTo(Elements_identifier o) {
		// TODO Auto-generated method stub
		return this.element - o.element;
	}

	
}

