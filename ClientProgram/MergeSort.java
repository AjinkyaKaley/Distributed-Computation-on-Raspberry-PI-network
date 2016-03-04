import java.util.ArrayList;
/**
 * Sorts the initial unsorted files received by the Slave
 * @author Pavan Kumar
 * @author Ajinkya 
 *
 */
public class MergeSort {

	/**
	 * Sorts the Arraylist of Integer values
	 * @param arraylist 
	 * @return Sorted ArrayList
	 */
	public ArrayList<Integer> sort(ArrayList<Integer>array) {
		double start = System.currentTimeMillis();
		if (array.size() <= 1) {
			return array;
		}
		divide(array);
		double end = System.currentTimeMillis();
		double timeTaken = end - start;
//		print(array);
		System.out.println("Time taken to execute (in millis) : "  + timeTaken);
		return array;
	}
	
	/**
	 * Divides the arraylist into two for the next stage of MergeSort
	 * @param A ArrayList
	 * @return Arraylist
	 */
	private ArrayList<Integer> divide(ArrayList<Integer> A) {
		int size = A.size();
		if (size <= 1) {
			return A;
		}
		ArrayList<Integer> left = new ArrayList<Integer>();
		
		int leftSize = size/2;
		int rightSize = size - leftSize;
		ArrayList<Integer> right = new ArrayList<Integer>();
		
		for (int i = 0; i < size/2; i++) {
			left.add(A.get(i));
		}
		for(int i = 0; i < rightSize; i++) {
			right.add(A.get(i + leftSize));
		}
		divide(left);
		divide(right);
		conquer(A, left, right);
		
		return A;
	}
	
	/**
	 * Merges the sorted ArrayLists from the lower levels
	 * @param A ArrayList to be merged into
	 * @param left Sorted ArrayList
	 * @param right Sorted ArrayList
	 */
	private void conquer(ArrayList<Integer> A, ArrayList<Integer> left, ArrayList<Integer> right) {
		int i = 0, j = 0, k = 0;
		
		while((i < left.size()) && (j < right.size())) {
			if(left.get(i) <= right.get(j) ) {
				A.set(k, left.get(i));
				i++;
			} else {
				A.set(k, right.get(j));
				j++;
			}
			k++;
		}
		
		if(i < left.size()) {
			for(int p = i+j; p < A.size(); p++) {
				A.set(k, left.get(i));
				k++; i++;
			}
		}
		if(j < right.size()) {
			for(int p = i+j; p < A.size(); p++) {
				A.set(k, right.get(j));
				k++; j++;
			}
		}

	}
	
	/**
	 * Prints the sorted arraylist
	 */
	private void print(ArrayList<Integer> A ) {
		for (int i = 0; i < A.size(); i++) {
			System.out.println(A.get(i));
		}
	}
}
