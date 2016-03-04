import java.io.File;

/*
 * Handles calling merging and Sorted and UnSorted Directories
 */
public class MergeManager {

	private File sortedDir, unSortedDir;
	private String fileSeperator = System.getProperty("file.separator");
	private final String HOME = "/home/pi/group2";
//	private final String HOME = System.getProperty("user.home");

	/**
	 * Constructor for the class
	 */
	public MergeManager() {
		sortedDir = new File(HOME+fileSeperator+"Sorted Directory");
		unSortedDir = new File(HOME+fileSeperator+"UnSorted Directory");
	}
	/**
	 * This method calls merge and swap method iteratively until only one sorted file is left
	 */
	public void iterativeMerge() {
		System.out.println("Inside Iterative Merge");
		FilesMerger merger = new FilesMerger();
//		merger.mergeFiles();
		while (!isAllSorted()) {
			System.out.println("Started merging files");
			merger.mergeFiles();
			swapDirectories();
		}
		
	}
	/**
	 * Swaps the directories to make them ready for the next merge iteration
	 */
	public void swapDirectories() {
		String[] files = unSortedDir.list();
		for (int i = 0; i<files.length; i++) {
			File temp = new File(unSortedDir, files[i]);
			temp.setWritable(true);
			temp.delete();
		}
		unSortedDir.delete();
		
		System.out.println(sortedDir.renameTo(unSortedDir));
		File newDir = new File(HOME+fileSeperator+"Sorted Directory");
		newDir.mkdir();
	}
	
	/**
	 * Checks if the merging and sorting process is completed
	 * @return Boolean value
	 */
	public boolean isAllSorted() {
		String[] files = unSortedDir.list();
		boolean returnValue;
		System.out.println("In All Sorted : " + files.length);
		if (files.length == 1) {
			returnValue = true;
		} else{
			returnValue = false;
		}
		
		return returnValue;
	}

}
