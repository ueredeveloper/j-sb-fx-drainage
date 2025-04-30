package drafts;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import utilities.CpfCnpjFormatter;

public class FormatNumber {

	public static void main(String[] args) {
		String str = "699749";

		formatSearch(str);

	}

	public static List<String> formatSearch(String str) {
		// Remove caracteres especiais
		String cleanStr = ((String) str).replaceAll("\\D", "");
		
		
		System.out.println(" string " + str);
		
		List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < cleanStr.length(); i += 3) 
			{
			list.add(cleanStr.substring(i, Math.min(i + 2,cleanStr.length())));
			list.add(cleanStr.substring(i, Math.min(i + 3,cleanStr.length())));

			}
		
		List<String> strList = new ArrayList<String>();
		
		// Adiciona a string em formato de cpf ou cpnj
				if (str.length() == 11 || str.length() == 14) {
					try {
						strList.add(CpfCnpjFormatter.format(str));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
		list.forEach(myStr->{
		
		// Adiciona a string solicitada
		strList.add(myStr);


		// Separa em caracteres para adicionar caracteres especiais de cpf e cnpj
		char[] charArray = myStr.toCharArray();

		for (int i = 0; i < myStr.length(); i++) {
			char c = charArray[i];

			if (i + 1 < myStr.length()) {

				strList.add(c + "." + myStr.substring(i + 1));
				strList.add(c + "-" + myStr.substring(i + 1));
				strList.add(c + "/" + myStr.substring(i + 1));

			}

			if (i + 2 < myStr.length()) {

				strList.add(c + myStr.substring(i + 2) + ".");
				strList.add(c + myStr.substring(i + 2) + "-");
				strList.add(c + myStr.substring(i + 2) + "/");

			}
		}
		
		});

		strList.forEach(l -> System.out.println(l));

		return strList;
	}

}
