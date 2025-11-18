package utilities;

import java.util.*;

import models.Finalidade;

//Ordenar para listar primeiro abastecimento humano, depois criação de animais e depois as outras

public class SortPurposes {

    public static Set<Finalidade> orderByPurpouses (Set<Finalidade> finalidades) {
    	
        if (finalidades == null) return Collections.emptySet();

        // Copia para lista para poder ordenar
        List<Finalidade> lista = new ArrayList<>(finalidades);
        
        // Comparator com regras personalizadas
        Comparator<Finalidade> comparador = (s1, s2) -> {
            // se qualquer um dos objetos for nulo
            if (s1 == null && s2 == null) return 0;
            if (s1 == null) return 1;  // nulos vão para o final
            if (s2 == null) return -1;

            String f1 = s1.getFinalidade();
            String f2 = s2.getFinalidade();

            // se qualquer um dos nomes for nulo
            if (f1 == null && f2 == null) return 0;
            if (f1 == null) return 1;
            if (f2 == null) return -1;

            int p1 = getPriority(f1);
            int p2 = getPriority(f2);

            if (p1 != p2) return Integer.compare(p1, p2);
            return f1.compareToIgnoreCase(f2);
        };

        lista.sort(comparador);
       

        // Retorna como LinkedHashSet para manter a ordem
        return new LinkedHashSet<>(lista);
    }

    private static int getPriority(String finalidade) {
        if (finalidade == null) return 3; // padrão
        String f = finalidade.toLowerCase();
        if (f.startsWith("abastecimento humano")) return 1;
        if (f.startsWith("criação de animais")) return 2;
        return 3;
    }
}
