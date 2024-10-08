package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import models.Template;

public class ReadAndUpdateTemplate {

    public static void updateTemplate(String filePath, Template template) {
        Long id = template.getId();
        String name = template.getNome();
        String folder = template.getPasta();
        String description = template.getDescricao();

        try {
            // Lê o conteúdo do arquivo
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

            // Atualizar ou adicionar @id
            fileContent = updateTag(fileContent, "@id", String.valueOf(id));
            
            // Se precisar limpar os ids nos arquivos para salvar novos ids para cada arquivo
            //fileContent = updateTag(fileContent, "@id", "");
            
            // Atualizar ou adicionar @nome
            fileContent = updateTag(fileContent, "@nome", name);

            // Atualizar ou adicionar @pasta
            fileContent = updateTag(fileContent, "@pasta", folder);

            // Atualizar ou adicionar @descricao
            fileContent = updateTag(fileContent, "@descricao", description);
            
          


            // Escreve o conteúdo atualizado de volta no arquivo
            Files.write(Paths.get(filePath), fileContent.getBytes());

            System.out.println("Arquivo atualizado com sucesso!");

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
