package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadAndUpdateTemplate {
	
	 public static void updateTemplate(String filePath, Long id, String name, String folder) {
		 
	        try {
	            // Lê o conteúdo do arquivo
	            String conteudo = new String(Files.readAllBytes(Paths.get(filePath)));
	            
	            // Update @id
	            if (conteudo.contains("@id")) {
	                // Replace existing @id, even if it's empty
	                conteudo = conteudo.replaceFirst("@id\\s*\\S*", "@id " + (id != null ? id : ""));
	            } else {
	                // If @id does not exist, add it after the start of the JSDoc comment
	                conteudo = conteudo.replaceFirst("/\\*\\*", "/**" + System.lineSeparator() + " * @id " + (id != null ? id : "") + System.lineSeparator());
	            }
	            
	            if (conteudo.contains("@nome")) {
	                conteudo = conteudo.replaceFirst("@nome\\s+\\w+", "@nome " + name);
	            } else {
	                conteudo += "\n * @nome " + name; // Add at the end if not found
	            }

	            String currentNomeValue = "";
	            if (conteudo.contains("@nome")) {
	                currentNomeValue = conteudo.replaceFirst(".*?(@nome\\s+)(\\S+).*", "$2"); // Capture current value
	                conteudo = conteudo.replaceFirst("@nome\\s+\\S+", "@nome " + name);
	            } else {
	                conteudo += "\n * @nome " + name; // Add at the end if not found
	            }

	            // Escreve o conteúdo atualizado de volta no arquivo
	            Files.write(Paths.get(filePath), conteudo.getBytes());

	            System.out.println("Arquivo atualizado com sucesso!");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
