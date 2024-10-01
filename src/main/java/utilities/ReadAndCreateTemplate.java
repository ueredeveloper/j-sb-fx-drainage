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
			String conteudo = new String(Files.readAllBytes(Paths.get(filePath)));

			String id = extractTag(conteudo, "@id");
			String nome = extractTag(conteudo, "@nome");
			String descricao = extractTag(conteudo, "@descricao");
			String pasta = extractTag(conteudo, "@pasta");

			Template template = new Template();

			// Se o id diferente de nulo, inserir para editar e não salvar novamente o mesmo arquivo
			if (!id.equals("*") && !id.equals("@")) {
				template.setId(Long.parseLong(id));
			} 
			template.setNome(nome);
			template.setDescricao(descricao);
			template.setPasta(pasta);
			template.setConteudo(conteudo);

			return template;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Função auxiliar para extrair metadados do JSDoc
	private static String extractTag(String content, String tag) {
		  // Combine @ and * in a character class for flexibility
		// Finaliza a análise da linha ao encontrar * ou @
		  Pattern pattern = Pattern.compile("\\s*" + tag + "\\s+([^\\n\\r]+?)(\\*|@)");
		  Matcher matcher = pattern.matcher(content);
		  if (matcher.find()) {
		    // Capture everything up to, but not including, the ending symbol
		    return matcher.group(1).trim();
		  } else {
		    return "";
		  }
		}

}
