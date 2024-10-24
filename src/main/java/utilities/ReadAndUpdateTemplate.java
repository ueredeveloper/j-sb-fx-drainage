package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import models.Template;

public class ReadAndUpdateTemplate {

    public static void updateTemplate(String filePath, Template template) {
        Long id = template.getId();
        String name = template.getArquivo();
        String folder = template.getDiretorio();
        String description = template.getDescricao();

        try {
            // Lê o conteúdo do arquivo
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

            // Atualizar ou adicionar @id. Também adiciona uma linha de comentário a mais preenchendo com um asterisco
            fileContent = updateTag(fileContent, "@id", String.valueOf(id) + "\n * ");
            
            // Se precisar limpar os ids nos arquivos para salvar novos ids para cada arquivo
            //fileContent = updateTag(fileContent, "@id", "");
            
            // Atualizar ou adicionar @arquivo
            fileContent = updateTag(fileContent, "@arquivo", name);

            // Atualizar ou adicionar @diretorio
            fileContent = updateTag(fileContent, "@diretorio", folder);

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
