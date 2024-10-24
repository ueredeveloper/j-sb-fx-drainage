package utilities;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Template;

public class TemplatesFolder {

	public static void create() {
		
		File htmlDir = createFileDir();
		
		System.out.println("create " + htmlDir);
		
		if (!htmlDir.exists()) {
			boolean created = htmlDir.mkdir(); // Cria o diretório
			if (created) {
				System.out.println("Diretório 'templates' criado com sucesso em: " + htmlDir.getAbsolutePath());
			} else {
				System.out.println("Falha ao criar o diretório 'templates'.");
			}
		} else {
			System.out.println("O diretório 'templates' já existe em: " + htmlDir.getAbsolutePath());
		}

	}

	public static Set<Template> read() {

		File htmlDir = createFileDir();

		Set<Template> templates = new HashSet<Template>();

		// List pastas e nomes de arquivos dentro destas pastas
		List<Map<String, List<String>>> folderStructure = null;
		
		System.out.println("read " + htmlDir.toString());

		try {
			try {
				folderStructure = ResourceFileLister.listFilesFromFilesystem(htmlDir.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return templates;
	}
	
	public static File createFileDir () {
		String jarPath = "";
		try {
			jarPath = new File(TemplatesFolder.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getPath();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Caminho do JAR: " + jarPath);

		// Cria o diretório "html" no mesmo local do JAR
		String path = new File(jarPath).getParent();
		String folder = "templates";

		File htmlDir = new File(path, folder);
		
		return htmlDir;
	}

}
