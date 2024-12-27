package utilities;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceFileLister {

	// Method to list files in a directory from the filesystem
	public static List<Map<String, List<String>>> listFilesFromFilesystem(String resourceDir)
			throws IOException, URISyntaxException {

		URL resource;
		// Leitura da pasta templates utilizando o eclipse
		resource = loadResourceForEclipse(resourceDir);
		// Para compilacao

		if (resource != null) {
			// Convert URL to Path
			Path dirPath = null;
			try {
				dirPath = Paths.get(resource.toURI());
			} catch (Exception e) {

				// Leitura da pasta templates quando compilado.
				resource = loadResourceForJarCompiled();
				dirPath = Paths.get(resource.toURI());

				e.printStackTrace();

			}

			// Lista para armazenar pastas e seus arquivos
			List<Map<String, List<String>>> folderStructure = new ArrayList<>();

			// Caminhar pelo diretório e subdiretórios
			try (Stream<Path> paths = Files.walk(dirPath)) {
				List<Path> filesAndDirs = paths.collect(Collectors.toList());

				for (Path path : filesAndDirs) {
					if (Files.isDirectory(path)) {

						System.out.println("path of paths " + path);
						// Cria um mapa para a pasta atual
						Map<String, List<String>> folderMap = new HashMap<>();

						// Lista arquivos dentro da pasta
						try (Stream<Path> files = Files.list(path)) {
							List<String> fileNames = files.filter(Files::isRegularFile)
									.map(file -> file.getFileName().toString()).collect(Collectors.toList());

							// Adiciona a pasta e seus arquivos no mapa
							folderMap.put(path.getFileName().toString(), fileNames);
						}

						// Adiciona o mapa da pasta à lista principal
						folderStructure.add(folderMap);
					}
				}
			}

			// Exibir o resultado
			// folderStructure.forEach(System.out::println);
			return folderStructure;
		} else {
			System.out.println("Resource directory not found: " + resourceDir);
		}
		return null;
	}

	public static List<Map<String, List<String>>> listFilesFromResources(String resourceDir) throws IOException {
		// Get the resource as a stream of the directory content
		URL resource = ResourceFileLister.class.getResource(resourceDir);

		System.out.println("Resource directory: " + resourceDir + " -> " + resource);

		if (resource != null) {
			List<Map<String, List<String>>> folderStructure = new ArrayList<>();

			// Check if the resource is a directory or inside a JAR
			if (resource.getProtocol().equals("file")) {
				// Handle regular filesystem (e.g., when running from IDE)
				Path dirPath = Paths.get(resource.getPath());

				try (Stream<Path> paths = Files.walk(dirPath)) {
					List<Path> filesAndDirs = paths.collect(Collectors.toList());

					for (Path path : filesAndDirs) {
						if (Files.isDirectory(path)) {
							Map<String, List<String>> folderMap = new HashMap<>();
							try (Stream<Path> files = Files.list(path)) {
								List<String> fileNames = files.filter(Files::isRegularFile)
										.map(file -> file.getFileName().toString()).collect(Collectors.toList());

								folderMap.put(path.getFileName().toString(), fileNames);
							}
							folderStructure.add(folderMap);
						}
					}
				}
			} else if (resource.getProtocol().equals("jar")) {
				// Handle resources inside a JAR file
				String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
				try (JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"))) {
					Enumeration<JarEntry> entries = jarFile.entries();
					Map<String, List<String>> currentFolder = new HashMap<>();

					while (entries.hasMoreElements()) {
						JarEntry entry = entries.nextElement();

						if (entry.getName().startsWith(resourceDir.substring(1)) && !entry.isDirectory()) {
							String entryName = entry.getName();
							String folderName = entryName.substring(0, entryName.lastIndexOf('/'));
							String fileName = entryName.substring(entryName.lastIndexOf('/') + 1);

							if (!currentFolder.containsKey(folderName)) {
								currentFolder.put(folderName, new ArrayList<>());
							}
							currentFolder.get(folderName).add(fileName);
						}
					}

					folderStructure.add(currentFolder);
				}
			}

			return folderStructure;
		} else {
			System.out.println("Resource directory not found: " + resourceDir);
		}

		return null;
	}

	/**
	 * Cria a pasta templates na mesma pasta em que o usuário adicionar o arquivo .jar
	 * 
	 */
	public static File createFileDir() {
		String jarPath = "";
		try {
			jarPath = new File(ResourceFileLister.class.getProtectionDomain().getCodeSource().getLocation().toURI())
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

	/**
	 * Para utilização ao compilar. Será adicionado na pasta do arquivo .jar a pasta
	 * templates e todos os arquivos de modelos de parcer.
	 * 
	 * 
	 */
	public static URL loadResourceForJarCompiled() {

		URL resource = null;

		try {

			File templatesFolder = createFileDir();
			resource = templatesFolder.toURI().toURL();

			// Use the resourceUrl as needed
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception
		}
		return resource;
	}

	/**
	 * Para utilização no Eclipse
	 * 
	 *  resourceDir
	 * 
	 */
	public static URL loadResourceForEclipse(String resourceDir) {

		URL resource = ResourceFileLister.class.getResource(resourceDir);

		return resource;
	}

}
