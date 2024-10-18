package templates;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import utilities.ResourceFileLister;

public class ListFilesFromResourcesTest {
	
	  @Test
	    public void listFilesFromResources () {
		  try {
	            // Adjust the path to match the structure in the resources folder
	            List<Map<String, List<String>>> folderStructure = ResourceFileLister.listFilesFromResources("/html/views/templates/");
	            
	            // Output the folder structure
	            if (folderStructure != null) {
	                for (Map<String, List<String>> folder : folderStructure) {
	                    folder.forEach((folderName, files) -> {
	                    	System.out.println(files.size());
	                        System.out.println("Folder: " + folderName);
	                        files.forEach(file -> System.out.println(" - " + file));
	                    });
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }
	    
}
