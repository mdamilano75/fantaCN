import javax.net.ssl.*;

import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CustomTrustManager {

    public static void main(String[] args) throws Exception {
        // Path to the certificate file
        String certFilePath = "/home/marco/workspace_SVIL_1/SimplePrj/src/example.crt";
        
        // Load the certificate
        FileInputStream fis = new FileInputStream(certFilePath);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);

        // Calculate the SHA-256 fingerprint of the certificate
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(cert.getEncoded());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02X", b));
        }
        String certFingerprint = sb.toString().replaceAll("(.{2})", "$1 ").trim();

        // Trusted certificates store
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("server", cert);
        
        // Create a TrustManager that trusts only the loaded certificate
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());
        
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Optionally set up a hostname verifier
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return hostname.equals("esempio.com");
            }
        });

        // Now make your HTTPS requests
        try {
             URL url = new URL("https://fantacalcio.it");
             HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
             connection.connect();
             System.out.println("Connected successfully!");
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
}
