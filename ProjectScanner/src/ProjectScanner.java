import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/*
 * Searches given folder (including subfolders) and
 *  for each file in the folder checks if file name exists in the body of any other file in this folder.
 *  the result is presented on the chart.
 * 
 * @author Maciej Szymczak
 * */

public final class ProjectScanner {

	public static String folderToSearch = "C:\\Users\\macszy\\Documents\\Cision\\tasks\\FR\\DirGen\\CodeSourceGpsPour ImportClientSurveillance\\constructeur\\src";
	public static void main(String[] args) throws IOException {
		System.out.println("#open this file with http://viz-js.com/");
		System.out.println("digraph G{");
		System.out.println("rankdir=LR;");
		displayIt(new File(folderToSearch));
		System.out.println("}");	
	}

	//============================================================
	public static void displayIt(File node) throws IOException{
		if(!node.isDirectory()){
			String fileName  = node.getName();			
			String fileNameNoExt = getFileNameNoExt(fileName);
			String fileExt = getFileExt(fileName);
			//System.out.println( "fileName:"+  node.getAbsolutePath() + " :: " + fileNameNoExt +"  ::  "+ fileExt );	
			if (fileNameNoExt.length() > 0 && (fileExt.equalsIgnoreCase("xml") || fileExt.equalsIgnoreCase("java")) ) 
			    searchInFiles (new File(folderToSearch),getFileNameNoExt(fileName) );
		}
		
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				displayIt(new File(node, filename));
			}
		}	
	}	
	
	//============================================================
	public static String getFileExt (String filename) {
		if (filename.lastIndexOf(".") == -1) {
			return filename; }
			else 
			return filename.substring(filename.lastIndexOf(".")+1, filename.length() );	
	}

	//============================================================
	public static String getFileNameNoExt (String filename) {
		if (filename.lastIndexOf(".") == -1) {
			return filename; }
			else 
			return filename.substring(0, filename.lastIndexOf("."));	
	}
	
	//============================================================
	public static void searchInFiles(File node, String textToFind) throws IOException{
		if(!node.isDirectory()){
			String fullfileName = node.getAbsolutePath();
			String fileName  = node.getName();	
			String fileNameNoExt = getFileNameNoExt(fileName);
			String fileContent = new String(Files.readAllBytes( Paths.get(fullfileName)   )); 
			if (fileNameNoExt.length() > 0 && fileContent.contains(textToFind)) {
				  String textToFlush = textToFind + " -> " + getFileNameNoExt(node.getName());
			      System.out.println( textToFlush.replace("->", "{}{}{}").replace("-", "_").replace("$", "_").replace(".", "_").replace("{}{}{}", "->") );		
	     	      //System.out.println( "# " + fullfileName + " :: "+ fileNameNoExt );
			}      
		}
		
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				searchInFiles(new File(node, filename), textToFind);
			}
		}	
	}	
	
	
}
