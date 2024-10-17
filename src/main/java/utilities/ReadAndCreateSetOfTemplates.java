package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Template;

public class ReadAndCreateSetOfTemplates {

	public static Set<Template> getSetOfTemplates() throws URISyntaxException {

		Set<Template> templates = new HashSet<Template>();

		try {
			// Diretorio dos arquivos web
			
			String resourceDir = "/html/views/templates/";
			
			// List pastas e nomes de arquivos dentro destas pastas
			List<Map<String, List<String>>> folderStructure = ResourceFileLister.listFilesFromFilesystem(resourceDir);

			folderStructure.forEach(folder -> {

				folder.forEach((folderName, fileList) -> {

					fileList.forEach(fileName -> {

						// Não criar template com o arquivo README.md
						if (!fileName.contains(".md")) {

							// Para testar o salvamento de apenas um arquivo
							// if (fileName.equals("geographic-table.js")) {

							// Pasta resources, diretório dentro desta pasta, pasta específica e arquivo que
							// será persistido
							//String fullPath = "src/main/resources" + resourceDir + folderName + "/" + fileName;
							String fullPath = resourceDir + folderName + "/" + fileName;
						   // String fullPath = ReadAndCreateSetOfTemplates.class.getResource(resourceDir + folderName + "/" + fileName).toExternalForm();
						    System.out.println("ReadAndCreateSetOfTemplates: full path " + fullPath);

							if (fullPath != null) {
								try {
									// Cria um template com os metadados @conteudo etc
									Template template = ReadAndCreateTemplate.readAndCreateTemplate(fullPath);

									templates.add(template);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						}
			
					});
				});
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return templates;

	}

}
