import javax.net.ssl.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class SSLUtil {

	public static void trustAllCertificates() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[]{
				new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() { return null; }
					public void checkClientTrusted(X509Certificate[] certs, String authType) { }
					public void checkServerTrusted(X509Certificate[] certs, String authType) { }
				}
		};

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Optional: Disable hostname verification
		HostnameVerifier allHostsValid = (hostname, session) -> true;
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	public static void main(String[] args) {
		try {
			trustAllCertificates();
			// Ora puoi fare chiamate HTTPS senza problemi di certificati

			//    		String url = "https://esempio.com";

			try {


				String urlString = "https://www.sosfanta.com/asta-fantacalcio/la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere";
				int maxRetries = 5;
				int retryDelay = 5000; // 5 seconds

				for (int attempt = 1; attempt <= maxRetries; attempt++) {
					try {
						URL url = new URL(urlString);
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();

						int statusCode = connection.getResponseCode();
						if (statusCode == HttpURLConnection.HTTP_OK) {
							System.out.println("URL fetched successfully.");

							// Caricare il documento da un URL
							Document document = Jsoup.connect(urlString).get();

							// Ottenere il sorgente HTML
							String htmlSource = document.html();

							// Stampare il sorgente HTML
							System.out.println(htmlSource);

							// Process the response
							break;
						} else if (statusCode == HttpURLConnection.HTTP_UNAVAILABLE) {
							System.out.println("Received 503. Retry attempt: " + attempt);
							Thread.sleep(retryDelay);
						} else {
							System.out.println("Error fetching URL. Status=" + statusCode);
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
