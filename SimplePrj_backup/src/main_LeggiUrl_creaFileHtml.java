import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class main_LeggiUrl_creaFileHtml {

	public static void main(String[] args) {
		String url = "https://www.fantacalcio.it/rigoristi-serie-a";

		
		
//		url = "https://www.sosfanta.com/box-consigli/la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/2";
		url = FC_Varie.url_chiPrendereDif1;
		try {
			Document doc = Jsoup.connect(url).get();

			// Stampa il titolo della pagina
			System.out.println("Title: " + doc.title());

			// Stampa l'intero contenuto HTML
//			System.out.println(doc.html());
			
			String filePath		= FC_Varie.pathFileGenerati+"/"+FC_Varie.nomeFile_chiPrendereDif1;
			FileWriter writer = new FileWriter(filePath);
	        writer.write(doc.html());
	        writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
