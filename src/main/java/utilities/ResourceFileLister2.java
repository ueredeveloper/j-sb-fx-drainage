package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResourceFileLister2 {

    public static List<Map<String, List<String>>> listFilesFromFilesystem(String resourceDir) throws IOException {
    	System.out.println(resourceDir);
    	 // Obtém o URL do diretório no classpath
        URL resource = ResourceFileLister.class.getResource(resourceDir);
        
        System.out.println("resouce uri " + resource);
        if (resource == null) {
            throw new IllegalArgumentException("Resource directory not found: " + resourceDir);
        }

        List<Map<String, List<String>>> folderStructure = new ArrayList<>();

        try (InputStream is = resource.openStream(); 
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            // Caminha pelos arquivos listados no diretório
            List<String> fileNames = br.lines().collect(Collectors.toList());

            // Cria um mapa para a pasta e seus arquivos
            Map<String, List<String>> folderMap = new HashMap<>();
            folderMap.put(resourceDir, fileNames);

            folderStructure.add(folderMap);
        }

        return folderStructure;
    }
}
