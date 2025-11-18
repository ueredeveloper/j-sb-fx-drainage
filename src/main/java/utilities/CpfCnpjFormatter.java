package utilities;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class CpfCnpjFormatter {

    /**
     * Formata automaticamente um CPF ou CNPJ com base na quantidade de dígitos.
     * O CPF deve conter 11 dígitos e o CNPJ deve conter 14 dígitos.
     *
     * @param cpfCnpj a string do CPF ou CNPJ (com ou sem máscara)
     * @return a string do CPF ou CNPJ formatada
     * @throws ParseException se a entrada for inválida
     */
    public static String format(String cpfCnpj) throws ParseException {
        if (cpfCnpj == null) {
            throw new IllegalArgumentException("cpfCnpj cannot be null.");
        }

        String formatedCpfCnpj = cpfCnpj.replaceAll("\\D", "");

        MaskFormatter formatter;
        if (formatedCpfCnpj.length() == 11) {
            formatter = new MaskFormatter("###.###.###-##"); // CPF
        } else if (formatedCpfCnpj.length() == 14) {
            formatter = new MaskFormatter("##.###.###/####-##"); // CNPJ
        } else {
        	 //System.out.println("Cpj ou Cnpj não formatado: " + cpfCnpj);
        	 return cpfCnpj;
            //throw new IllegalArgumentException("cpfCnpj must have 11 digits (CPF) or 14 digits (CNPJ).");
        }

        formatter.setValueContainsLiteralCharacters(false);
        return formatter.valueToString(formatedCpfCnpj);
    }
}
