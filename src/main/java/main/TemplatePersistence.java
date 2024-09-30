package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import models.Template;
import services.ServiceResponse;
import services.TemplateService;
import utilities.JsonConverter;
import utilities.ResourceFileLister;
import utilities.URLUtility;

public class TemplatePersistence {

	public static void main(String[] args) throws URISyntaxException {
		try {
			// Directory inside the resources folder
			String resourceDir = "/html/views/templates/";

			// List files from the filesystem or JAR
			List<Map<String, List<String>>> folderStructure = ResourceFileLister.listFilesFromFilesystem(resourceDir);

			folderStructure.forEach(folder -> {

				folder.forEach((folderName, fileList) -> {

					/**
					 * Criar um objeto de template
					 */
					
					String templateString = "template-info.json";

					TemplateWrapper tw = new TemplateWrapper();
					tw.createTemplate(fileList, resourceDir, templateString, folderName);

					// Iterar sobre a lista de arquivos
					fileList.forEach(fileName -> {

						System.out.println(fileName != templateString + " " + fileName);
						if (fileName != templateString) {

							// Construir o caminho completo
							String fullPath = resourceDir + folderName + "/" + fileName;

							// Buscar o recurso
							URL resource = TemplatePersistence.class.getResource(fullPath);

							// Verificar se o recurso foi encontrado
							if (resource != null) {

								Template newObject = tw.getTemplate();

								String json = new ReadFile().readFile(resource);

								// System.out.println(json);

								if (newObject != null) {
									
									newObject.setNome(fileName);
									newObject.setPasta(folderName);

									String rawJson = JsonConverter.convertHtmlContentToRawJson(json);
									rawJson = rawJson.replace("\"", "'");
									//System.out.println(rawJson);
									newObject.setConteudo(rawJson);
									
									//System.out.println(newObject.getConteudo());

									try {

										String localUrl = URLUtility.getURLService();
										TemplateService service = new TemplateService(localUrl);

										ServiceResponse<?> response = service.save(newObject);

										//System.out.println(response.getResponseCode());

										if (response.getResponseCode() == 201) {

											System.out.println("Objeto salvo com sucesso!");

											Object responseBody = response.getResponseBody();

											if (responseBody instanceof Template) {
												Template responseObject = (Template) responseBody;

												//System.out.println(responseObject);

											} else {
												System.out.println("Erro ao salvar!");
											}

										} else {

											System.out.println("Erro ao salvar o objeto!");
										}

									} catch (Exception e) {

										System.out.println("Erro ao salvar objeto! \n" + e.getMessage());

									}

								}

							} else {
								System.out.println("Arquivo não encontrado: " + fullPath);
							}

						}

					});

				});

			});

			// or
			// ResourceFileLister.listFilesFromJar(resourceDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class TemplateWrapper {

	Template template = null;

	public Template createTemplate(List<String> fileList, String resourceDir, String fileName, String folderName) {

		if (fileList.contains(fileName)) {

			// Construir o caminho completo
			String fullPath = resourceDir + folderName + "/" + fileName;

			// Buscar o recurso
			URL resource = TemplatePersistence.class.getResource(fullPath);

			String json = new ReadFile().readFile(resource);
			Gson gson = new Gson();
			template = gson.fromJson(json, Template.class);

		}
		return template;
	}

	public Template getTemplate() {
		return template;
	}

}

class ReadFile {

	public ReadFile() {
		super();
	}

	public String readFile(URL resource) {
		/// Read the contents of the file
		StringBuilder jsonBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				jsonBuilder.append(line);//.append("\n"); // Adiciona uma nova linha para manter a
														// formatação
			}

			return jsonBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
