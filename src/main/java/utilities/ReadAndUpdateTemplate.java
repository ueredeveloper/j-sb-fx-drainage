package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import models.Template;

public class ReadAndUpdateTemplate {
	
	 public static void updateTemplate(String filePath, Template template) {
		 
		 Long id = template.getId();
		 String name = template.getNome();
		 String folder = template.getPasta();
		 String description = template.getDescricao();
		 try {
	            // Lê o conteúdo do arquivo
	            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
	            
	            // Update @id
	            if (fileContent.contains("@id")) {
	                // Replace existing @id, even if it's empty
	                fileContent = fileContent.replaceFirst("@id\\s*\\S*", "@id " + (id != null ? id : ""));
	            } else {
	                // If @id does not exist, add it after the start of the JSDoc comment
	                fileContent = fileContent.replaceFirst("/\\*\\*", "/**" + System.lineSeparator() + " * @id " + (id != null ? id : "") + System.lineSeparator());
	            }
	            
	            if (fileContent.contains("@nome")) {
	            	fileContent = fileContent.replaceFirst("@nome\\s+\\w+", "@nome " + name);
	            } else {
	            	fileContent += "\n * @nome " + name; // Add at the end if not found
	            }

	            
	            // Update @pasta
	            if (fileContent.contains("@pasta")) {
	                fileContent = fileContent.replaceFirst("@pasta\\s+\\w+", "@pasta " + folder);
	            } else {
	                fileContent += "\n * @pasta " + folder; // Add at the end if not found
	            }

	            // Update @descricao
	            if (fileContent.contains("@descricao")) {
	                fileContent = fileContent.replaceFirst("@descricao\\s+.*", "@descricao " + description);
	            } else {
	                fileContent += "\n * @descricao " + description; // Add at the end if not found
	            }

	            // Escreve o conteúdo atualizado de volta no arquivo
	            Files.write(Paths.get(filePath), fileContent.getBytes());

	            System.out.println("Arquivo atualizado com sucesso!");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
