package templates;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utilities.TemplatesFolder;

import java.io.File;

import static org.junit.Assert.*;

/**
 * O teste visa garantir o comportamento correto da classe TemplatesFolder, que é responsável por criar um diretório chamado 
 * templates. O primeiro teste verifica se o diretório é criado corretamente quando ele não existe, garantindo que o método 
 * funcione como esperado. O segundo teste assegura que, se o diretório já existir, o método não tente recriá-lo ou causar 
 * algum erro, validando assim a robustez e a idempotência do método. Dessa forma, os testes garantem a integridade e o 
 * funcionamento esperado do processo de criação de diretórios.
 * @author fabricio.barrozo
 *
 */

public class TemplateFolderCreateTest {

    private File templatesDir;

    @Before
    public void setUp() {
        // Setup: Define the file directory that should be created
        templatesDir = new File(System.getProperty("user.dir") + File.separator + "templates");
        if (templatesDir.exists()) {
            deleteDirectory(templatesDir);
        }
    }

    @After
    public void tearDown() {
        // Clean up: Delete the directory after each test
        if (templatesDir.exists()) {
            deleteDirectory(templatesDir);
        }
    }

    @Test
    public void testCreateDirectoryWhenNotExists() {
        // Act: Call the method that creates the directory
        TemplatesFolder.create();

        // Assert: Verify the directory was created
        assertTrue(templatesDir.exists());
        assertTrue(templatesDir.isDirectory());
    }

    @Test
    public void testCreateDirectoryWhenAlreadyExists() {
        // Arrange: Create the directory manually before the test
        templatesDir.mkdir();

        // Act: Call the method to check that no error occurs
        TemplatesFolder.create();

        // Assert: Verify the directory still exists (no deletion or recreation)
        assertTrue(templatesDir.exists());
        assertTrue(templatesDir.isDirectory());
    }

    // Helper method to delete the directory
    private void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDirectory(child);
                }
            }
        }
        dir.delete();
    }
}
