package utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.Template;

public class ReadAndCreateTemplate {

	// Função auxiliar para ler o template do arquivo
	public static Template readAndCreateTemplate(String fullPath) {

		// System.out.println("Read and create Template: paths get " +
		// Paths.get(filePath));

		try {

			InputStream inputStream = ReadAndCreateSetOfTemplates.class.getResourceAsStream(fullPath);

			if (inputStream == null) {
				throw new IOException("Resource not found: " + fullPath);
			}
			String fileContent = readFromInputStream(inputStream);

			// String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

			String id = extractTag(fileContent, "@id");
			String name = extractTag(fileContent, "@arquivo");
			String folder = extractTag(fileContent, "@diretorio");
			String description = extractTag(fileContent, "@descricao");
			Template template = new Template();

			// Se o id diferente de nulo, inserir para editar e não salvar novamente o mesmo
			// arquivo
			if (!id.equals("*") && !id.equals("@") && !id.equals("") && !id.contains(" ")) {
				template.setId(Long.parseLong(id));
			}
			template.setArquivo(name);
			template.setDiretorio(folder);
			template.setDescricao(description);
			// Adiciona todo arquivo ao atributo conteudo do objeto template
			template.setConteudo(fileContent);

			return template;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Função auxiliar para extrair metadados do JSDoc
	private static String extractTag(String content, String tag) {
		// A regex agora ignora quantos asteriscos houver, e captura até o próximo @ ou
		// o final da linha
		Pattern pattern = Pattern.compile("\\*" + "\\s*" + tag + "\\s+([^\\n\\r\\*]+)(?=\\n|\\r|@|\\*/)");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			// Retorna o conteúdo capturado até encontrar um @, nova linha ou fim do
			// comentário
			return matcher.group(1).trim();
		} else {
			return "";
		}
	}

	private static String readFromInputStream(InputStream inputStream) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8"); // Specify the encoding
	}

}
