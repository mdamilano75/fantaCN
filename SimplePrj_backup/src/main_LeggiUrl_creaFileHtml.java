import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class main_LeggiUrl_creaFileHtml {

	public static void main(String[] args) {
		String url = "";
		url = FC_Varie.url_chiPrenderepref2+FC_Varie.url_chiPrendereDif1;
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

		url = FC_Varie.url_chiPrenderepref2+FC_Varie.url_chiPrendereDif2;
		try {
			Document doc = Jsoup.connect(url).get();

			// Stampa il titolo della pagina
			System.out.println("Title: " + doc.title());

			// Stampa l'intero contenuto HTML
//			System.out.println(doc.html());
			
			String filePath		= FC_Varie.pathFileGenerati+"/"+FC_Varie.nomeFile_chiPrendereDif2;
			FileWriter writer = new FileWriter(filePath);
	        writer.write(doc.html());
	        writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		url = FC_Varie.url_chiPrenderepref2+FC_Varie.url_chiPrendereCen1;
		try {
			Document doc = Jsoup.connect(url).get();

			// Stampa il titolo della pagina
			System.out.println("Title: " + doc.title());

			// Stampa l'intero contenuto HTML
//			System.out.println(doc.html());
			
			String filePath		= FC_Varie.pathFileGenerati+"/"+FC_Varie.nomeFile_chiPrendereCen1;
			FileWriter writer = new FileWriter(filePath);
	        writer.write(doc.html());
	        writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
