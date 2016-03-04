import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * Merging of the sorted files occur here
 */
public class FilesMerger {

	private File sortedDir, unSortedDir;
	private final String HOME = "/home/pi/group2";
//	private final String HOME = System.getProperty("user.home");
	private String fileSeperator = System.getProperty("file.separator");

	/*
	 * Constructor for this class
	 */
	public FilesMerger() {
		sortedDir = new File(HOME+fileSeperator+"Sorted Directory");
		unSortedDir = new File(HOME+fileSeperator+"UnSorted Directory");
	}
	
	/**
	 * Picks two files at a time from UnSorted Directory for merging
	 */
	public void mergeFiles() {
		System.out.println(HOME);
//		String[] filesUnSorted = unSortedDir.list();
		////////
		FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				String lowercasename = name.toLowerCase();
				if (lowercasename.startsWith("data") || lowercasename.startsWith("chunk")) {
					return true;
				} else {
					return false;
				}
			}
			
		};
		
		String[] filesUnSorted = unSortedDir.list(filter);
		
		///////
		System.out.println("Length : " + filesUnSorted.length);
		for (int i = 0; i < filesUnSorted.length; i=i+2) {
			File file1 = null, file2 = null; 
			try {
				file1 = new File(unSortedDir+fileSeperator+filesUnSorted[i]);
				file2 = new File(unSortedDir+fileSeperator+filesUnSorted[i+1]);
				merge(file1, file2, i);
			} catch (ArrayIndexOutOfBoundsException ai) {
				mergeSingleFile(file1);
			}
			
		}
	}
	/**
	 * Merges two sorted files to a single file and saves in Sorted Directory
	 * @param f1 File object to be merged
	 * @param f2 File object to be merged
	 * @param i index added to the newly created merged file
	 */
	public void merge(File f1, File f2, int i) {
		File mergedFile = new File(sortedDir
				+ fileSeperator + "data"+i);
		FileInputStream fis1;
		FileInputStream fis2;
		PrintWriter pw = null;
		Scanner sc1 = null, sc2 = null;
		try {
			fis1 = new FileInputStream(f1);
			fis2 = new FileInputStream(f2);
			if (mergedFile.createNewFile()) {
				System.out.println("Merging file created");
			}
			pw = new PrintWriter(new FileWriter(mergedFile));
			sc1 = new Scanner(fis1);
			sc2 = new Scanner(fis2);
			int previousPick = 0, x = 0, y = 0;
			int linesWritten = 0;
			while (sc1.hasNext() || sc2.hasNext()) {
				if (previousPick == 1) {
					x = Integer.valueOf(sc1.next());
				} else if (previousPick == 2){
					y = Integer.valueOf(sc2.next());
				} else {
					x = Integer.valueOf(sc1.next());
					y = Integer.valueOf(sc2.next());
				}
				if (x <= y) {
					pw.println(Integer.toString(x));
					previousPick = 1;
				} else {
					pw.println(Integer.toString(y));
					previousPick = 2;
				}
				linesWritten++;
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchElementException nse) {
			if (sc1.hasNext()) {
				while(sc1.hasNext()) {
					pw.println(sc1.next());
				}
				
			} else {
				while(sc2.hasNext()) {
					pw.println(sc2.next());
				}
			}
		}
		finally {
			pw.flush();
			pw.close();
			sc1.close();
			sc2.close();
		}
		
	}
	
	/**
	 * This is called when only single file is left in the UnSorted Directory to be merged
	 * @param f File to be merged into the Sorted Directory
	 */
	public void mergeSingleFile(File f) {
		File mergedFile = new File(sortedDir
				+ fileSeperator + "data");
		FileInputStream fis;
		PrintWriter pw;
			try {
				fis = new FileInputStream(f);
				if (mergedFile.createNewFile()) {
					System.out.println("Merging file created");
				}
				pw = new PrintWriter(new FileWriter(mergedFile));
				Scanner sc = new Scanner(fis);
				while (sc.hasNext()) {
					pw.println(sc.next());
				}
				pw.flush();
				pw.close();
				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
