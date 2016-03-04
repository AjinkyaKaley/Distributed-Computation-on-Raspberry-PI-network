import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Calculate_average {

	/**
	 * 
	 * This method calculates the avergae on the sorted file
	 * Output.txt is the sorted file which is the result of the
	 * first task. The sorted file will be then processed to get the 
	 * unique values and calculating the summation
	 * 
	 * The summatation is the divided using the counter values, which keeps the
	 * track of the unique values / lines
	 */
	public static void Average(){
		
		int sum = 0;
		int prev = 0;
		File f = new File("Output.txt");
		int counter=0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			
			while((line = br.readLine())!=null){
				
				if(prev != Integer.parseInt(line)){
					prev = Integer.parseInt(line);		// used to check for duplicaties
					sum = sum + Integer.parseInt(line);
					counter++;
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
