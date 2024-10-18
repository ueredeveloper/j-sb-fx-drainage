package templates;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.Template;
import utilities.TemplatesFolder;

public class TemplateFolderReadTest {

    

    @Test
    public void testCreateDirectoryWhenNotExists() {
        // Act: Call the method that creates the directory
    	Set<Template> set =  TemplatesFolder.read();

        // Assert: Verify the directory was created
        assertTrue(set.size()>0);

    }
    
    
}
