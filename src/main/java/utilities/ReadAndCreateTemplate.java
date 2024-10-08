package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.Template;

public class ReadAndCreateTemplate {

	// Função auxiliar para ler o template do arquivo
	public static Template readAndCreateTemplate(String filePath) {
		try {
			String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

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
			template.setConteudo(fileContent);

			return template;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Função auxiliar para extrair metadados do JSDoc
	private static String extractTag(String content, String tag) {
	    // A regex agora ignora quantos asteriscos houver, e captura até o próximo @ ou o final da linha
	    Pattern pattern = Pattern.compile("\\*" + "\\s*" + tag + "\\s+([^\\n\\r\\*]+)(?=\\n|\\r|@|\\*/)");
	    Matcher matcher = pattern.matcher(content);
	    if (matcher.find()) {
	        // Retorna o conteúdo capturado até encontrar um @, nova linha ou fim do comentário
	        return matcher.group(1).trim();
	    } else {
	        return "";
	    }
	}

}
