package utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceFileLister {

	// Method to list files in a directory from the filesystem
	public static List<Map<String, List<String>>> listFilesFromFilesystem(String resourceDir) throws IOException, URISyntaxException {
		URL resource = ResourceFileLister.class.getResource(resourceDir);
		
		System.out.println("Resource File Lister , resource  res dir "  + resourceDir + " resource " +  resource);
		
		if (resource != null) {
			// Convert URL to Path
			Path dirPath = Paths.get(resource.toURI());
			
			System.out.println("dir path " + dirPath);
			
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
	                        List<String> fileNames = files
	                            .filter(Files::isRegularFile)
	                            .map(file -> file.getFileName().toString())
	                            .collect(Collectors.toList());
	                        
	                        // Adiciona a pasta e seus arquivos no mapa
	                        folderMap.put(path.getFileName().toString(), fileNames);
	                    }
	                    
	                    // Adiciona o mapa da pasta à lista principal
	                    folderStructure.add(folderMap);
	                }
	            }
	        }

	        // Exibir o resultado
	        //folderStructure.forEach(System.out::println);
	        return folderStructure;
		} else {
			System.out.println("Resource directory not found: " + resourceDir);
		}
		return null;
	}
	
	// Helper method to read a file using getResourceAsStream
    public static String readFileFromClasspath(String resourceDir, String folderName, String fileName) throws IOException {
        String fullPath = resourceDir + folderName + "/" + fileName;
        InputStream inputStream = ResourceFileLister.class.getResourceAsStream(fullPath);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + fullPath);
        }

        return readFromInputStream(inputStream);
    }

    // Helper method to read from InputStream
    private static String readFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }

}
