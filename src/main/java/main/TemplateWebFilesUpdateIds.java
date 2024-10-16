package main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import utilities.ResourceFileLister;

/*
 * Lê os arquivos e edita somente os ids, limpando para uma nova de todos os arquivos no banco de dados, 
 * onde receberá novos ids.Isto é necessário para que se possa ser criado novos arquivos, templates etc, 
 * nessecitando assim reorganizar os ids no banco de dados.
 * 
 */
public class TemplateWebFilesUpdateIds {

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
						// if (fileName.equals("geographic-table.js")) {

						// Pasta resources, diretório dentro desta pasta, pasta específica e arquivo que
						// será persistido
						String filePath = "src/main/resources" + resourceDir + folderName + "/" + fileName;

						if (filePath != null) {
							try {

								String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

								// Fazer update apenas do id, limpando para futura persistência no banco.
								fileContent = updateTag(fileContent, "@id", "");

								// Escreve o conteúdo atualizado de volta no arquivo
								Files.write(Paths.get(filePath), fileContent.getBytes());

								System.out.println("Arquivo atualizado com sucesso!");

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						// }

					});
				});
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Função auxiliar para atualizar ou adicionar uma tag no conteúdo
	private static String updateTag(String content, String tag, String value) {
		// Verifica se a tag já existe no conteúdo
		if (content.contains(tag)) {
			// Substitui o valor existente da tag
			return content.replaceFirst(tag + "\\s+.*", tag + " " + value);
		} else {
			// Se a tag não existir, adiciona ela após o início do comentário JSDoc
			return content.replaceFirst("/\\*\\*", "/**" + System.lineSeparator() + " * " + tag + " " + value);
		}
	}

}