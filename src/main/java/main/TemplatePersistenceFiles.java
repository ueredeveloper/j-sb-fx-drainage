package main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import models.Template;
import services.ServiceResponse;
import services.TemplateService;
import utilities.ReadAndCreateTemplate;
import utilities.ReadAndUpdateTemplate;
import utilities.ResourceFileLister;
import utilities.URLUtility;

public class TemplatePersistenceFiles {

	
	public static void main(String[] args) throws URISyntaxException {

		try {
			// Diretorio dos arquivos web
			String resourceDir = "/html/views/templates/";
		
			// List pastas e nomes de arquivos dentro destas pastas
			List<Map<String, List<String>>> folderStructure = ResourceFileLister.listFilesFromFilesystem(resourceDir);

			folderStructure.forEach(folder -> {

				folder.forEach((folderName, fileList) -> {

					fileList.forEach(fileName -> {
						
						// Para testar o salvamento de apenas um arquivo
						//if (fileName.equals("geographic-table.js")) {
							
							// Pasta resources, diretório dentro desta pasta, pasta específica e arquivo que será persistido
							//String filePath = "src/main/resources" + resourceDir + folderName + "/" + fileName;
							String filePath = resourceDir + folderName + "/" + fileName;

							if (filePath != null) {
								try {
									// Cria um template com os metadados @conteudo etc
									Template template = ReadAndCreateTemplate.readAndCreateTemplate(filePath);
									// Seta o nome e pasta
									template.setArquivo(fileName);
									template.setDiretorio(folderName);
									

									try {
										// Serviço de persistencia
										String localUrl = URLUtility.getURLService();
										TemplateService service = new TemplateService(localUrl);

										ServiceResponse<?> response = service.save(template);

										if (response.getResponseCode() == 201) {

											System.out.println("Objeto salvo com sucesso!");

											Object responseBody = response.getResponseBody();

											if (responseBody instanceof Template) {

												Template responseObject = (Template) responseBody;
												
													ReadAndUpdateTemplate.updateTemplate(filePath, responseObject);
												
											} else {
												System.out.println("Erro ao salvar!");
											}

										} else {

											System.out.println("Erro ao salvar o objeto!");
										}

									} catch (Exception e) {
										System.out.println("Erro ao salvar objeto! \n" + e.getMessage());

									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						//}

					});
				});
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
