package utilities;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por formatar uma string de CPF ou CNPJ
 * para possibilitar diversos tipos de buscas parciais ou completas.
 * 
 * Exemplo de entrada:
 *  - "699 ou 699322 ou 699.322 ou 699.322.555-49" ou "69932255549"
 * 
 * A classe irá gerar variações da string com e sem pontuações,
 * separando partes e inserindo caracteres típicos de documentos (., -, /),
 * para facilitar buscas em bancos de dados, inclusive as parciais.
 */
public class CpfCnpjSearchFormatter {

    /**
     * Formata a string recebida (CPF ou CNPJ) de forma que sejam geradas
     * múltiplas variações da mesma para facilitar a busca.
     * 
     * @param str String original representando um CPF ou CNPJ.
     * @return Lista de strings formatadas e variantes.
     */
    public static List<String> formatSearch(String str) {
        // Remove todos os caracteres não numéricos da string
        String cleanStr = str.replaceAll("\\D", "");

        // Lista que conterá partes da string limpa
        List<String> partsList = new ArrayList<>();
        
        // Gera partes da string com tamanho 2 e 3 a cada 3 posições
        for (int i = 0; i < cleanStr.length(); i += 3) {
            partsList.add(cleanStr.substring(i, Math.min(i + 2, cleanStr.length())));
            partsList.add(cleanStr.substring(i, Math.min(i + 3, cleanStr.length())));
        }

        // Lista final com todas as variações a serem retornadas
        List<String> formattedList = new ArrayList<>();
        
        //formattedList.add(cleanStr);

        // Adiciona a versão formatada da string original (se válida como CPF ou CNPJ)
        if (cleanStr.length() == 11 || cleanStr.length() == 14) {
            try {
                formattedList.add(CpfCnpjFormatter.format(cleanStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Para cada parte gerada, adiciona variações com pontuações típicas
        partsList.forEach(part -> {
            // Adiciona diretamente a parte como está
            formattedList.add(part);

            // Converte em array de caracteres para manipular individualmente
            char[] charArray = part.toCharArray();

            for (int i = 0; i < part.length(); i++) {
                char c = charArray[i];

                // Se possível, insere um caractere especial (., - ou /) separador após o primeiro caractere
                if (i + 1 < part.length()) {
                    formattedList.add(c + "." + part.substring(i + 1));
                    formattedList.add(c + "-" + part.substring(i + 1));
                    formattedList.add(c + "/" + part.substring(i + 1));
                }

                // Se possível, insere um caractere especial separador após o segundo caractere
                if (i + 2 < part.length()) {
                    formattedList.add(c + part.substring(i + 2) + ".");
                    formattedList.add(c + part.substring(i + 2) + "-");
                    formattedList.add(c + part.substring(i + 2) + "/");
                }
            }
        });

        // Imprime todas as variações geradas
        //formattedList.forEach(System.out::println);

        return formattedList;
    }
}
