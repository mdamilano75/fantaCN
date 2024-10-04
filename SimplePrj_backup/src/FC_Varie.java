import java.util.ArrayList;



public class FC_Varie {

	protected static final String ATT	= "ATT";
	protected static final String CEN	= "CEN";
	protected static final String DIF	= "DIF";
	protected static final String POR	= "POR";
	
	protected static final String POR_Por	= "Por";

	protected static final String DIF_Ds	= "Ds";
	protected static final String DIF_Dd	= "Dd";
	protected static final String DIF_Dc	= "Dc";
	protected static final String DIF_E		= "E";

	protected static final String CEN_W		= "W";
	protected static final String CEN_A		= "A";
	protected static final String CEN_C		= "C";
	protected static final String CEN_T		= "T";
	protected static final String CEN_M		= "M";
	protected static final String CEN_E		= "E";

	protected static final String ATT_W		= "W";
	protected static final String ATT_A		= "A";
	protected static final String ATT_Pc	= "Pc";

	protected static final String PartiteGiocate	= "Pv";
	protected static final String MediaVoto			= "Mv";
	protected static final String FantaMediaVoto	= "Fm";
	
	protected static final String GolFatti			= "Gf";
	protected static final String GolSubiti			= "Gs";
	
	protected static final String RigoriParati		= "Rp";
	
	protected static final String RigoriTirati		= "Rc";
	protected static final String RigoriFatti		= "R+";
	protected static final String RigoriSbagliati	= "R-";
	
	protected static final String Assist			= "Ass";
	
	protected static final String Ammunizioni		= "Amm";
	protected static final String Esplusioni		= "Esp";
	protected static final String Autoreti			= "Au"; 
	
	protected static final String livOffensivo_6	= "-* * * * * *";
	protected static final String livOffensivo_5	= "-* * * * *--";
	protected static final String livOffensivo_4	= "-* * * *----";
	protected static final String livOffensivo_3	= "-* * *------";
	protected static final String livOffensivo_2	= "-* *--------";
	protected static final String livOffensivo_1	= "-*----------";
	
	//////////////////// SQUADRE
	protected static final String Atalanta		= "ATALANTA";
	protected static final String Bologna		= "BOLOGNA";
	protected static final String Cagliari		= "CAGLIARI";
	protected static final String Como			= "COMO";
	protected static final String Empoli		= "EMPOLI";
	protected static final String Fiorentina	= "FIORENTINA";
	protected static final String Genoa			= "GENOA";
	protected static final String Inter			= "INTER";
	protected static final String Juventus		= "JUVENTUS";
	protected static final String Lazio			= "LAZIO";
	protected static final String Lecce			= "LECCE";
	protected static final String Milan			= "MILAN";
	protected static final String Monza			= "MONZA";
	protected static final String Napoli		= "NAPOLI";
	protected static final String Parma			= "PARMA";
	protected static final String Roma			= "ROMA";
	protected static final String Torino		= "TORINO";
	protected static final String Udinese		= "UDINESE";
	protected static final String Venezia		= "VENEZIA";
	protected static final String Verona		= "VERONA";

	//////////////////// RISORSE DAL WEB
	
	protected static final String pathFile						= "/home/marco/myPrivato/fanta24";
	protected static final String pathFileGenerati				= "./fileTxtHtml";
	
	protected static final String nomeFile_quotazioni			= "Quotazioni_Fantacalcio_Stagione_2024_25.xlsx";
	
	protected static final String nomeFile_statistiche24_25		= "Statistiche_Fantacalcio_Stagione_2024_25.xlsx";
	protected static final String nomeFile_statistiche23_24		= "Statistiche_Fantacalcio_Stagione_2023_24.xlsx";
	protected static final String nomeFile_statistiche22_23		= "Statistiche_Fantacalcio_Stagione_2022_23.xlsx";
	
	protected static final String nomeFile_rigoristiCPiazzati	= "rigoristi-calciPiazzati.txt"; 	// 
	protected static final String url__rigoristiCPiazzati		= "https://www.fantacalcio.it/rigoristi-serie-a";
	
	protected static final String nomeFile_angoliPunizioni		= "sos_calciPiazzati_Angoli.txt";	// https://www.sosfanta.com/asta-fantacalcio/tiratori-piazzati-punizioni-corner-serie-a-2024-2025-fantacalcio-asta/
	protected static final String url_angoliPunizioni			= "https://www.sosfanta.com/asta-fantacalcio/tiratori-piazzati-punizioni-corner-serie-a-2024-2025-fantacalcio-asta";
	
	protected static final String nomeFile_infortunati			=  "infortunati.txt";				// https://www.fantacalcio.it/indisponibili-serie-a 
	protected static final String url_infortunati				=  "https://www.fantacalcio.it/indisponibili-serie-a";
	
	protected static final String nomeFile_chiPrendereDif1		=  "chiPrendere_DIF_1.txt";			// https://www.sosfanta.com/box-consigli/la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/2/
	
	protected static final String url_chiPrenderepref1			= "https://www.sosfanta.com/box-consigli/";
	protected static final String url_chiPrenderepref2			= "https://www.sosfanta.com/asta-fantacalcio/";
	protected static final String url_chiPrenderePor			=  "la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere";
	protected static final String url_chiPrendereDif1			=  "la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/2";
	protected static final String nomeFile_chiPrendereDif2		=  "chiPrendere_DIF_2.txt";			// https://www.sosfanta.com/box-consigli/la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/3/
	protected static final String url_chiPrendereDif2			=  "la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/3";
	protected static final String nomeFile_chiPrendereCen1		=  "chiPrendere_CEN_1.txt";			// https://www.sosfanta.com/box-consigli/la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/4/
	protected static final String url_chiPrendereCen1			=  "la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/4";
	protected static final String nomeFile_chiPrendereCen2		=  "chiPrendere_CEN_2.txt";			// https://www.sosfanta.com/box-consigli/la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/5/
	protected static final String url_chiPrendereCen2			=  "la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/5";
	protected static final String nomeFile_chiPrendereAtt1		=  "chiPrendere_ATT_1.txt";			// https://www.sosfanta.com/box-consigli/la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/6/
	protected static final String url_chiPrendereAtt1			=  "la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/6";
	protected static final String nomeFile_chiPrendereAtt2		=  "chiPrendere_ATT_2.txt";			// https://www.sosfanta.com/box-consigli/la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/7/
	protected static final String url_chiPrendereAtt2			=  "la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/7";

//	protected static final String nomeFile_chiPrendereALL		=  "chiPrendere_ALL.txt";			// https://www.sosfanta.com/box-consigli/la-guida-allasta-completa-per-il-fantacalcio-2024-25-la-divisione-in-fasce-e-chi-prendere/
	
	// CATEGORIE SOS FANTA
	protected static final String TOP					= "TOP";
	protected static final String TOP_ALTI				= "TOP ALTI";
	protected static final String TOP_BASSI				= "TOP BASSI";
	protected static final String SEMITOP				= "SEMITOP";
	protected static final String SEMITOP_ALTI			= "SEMITOP ALTI";
	protected static final String SEMITOP_BASSI 		= "SEMITOP BASSI";
	protected static final String OTTIMI_TITOLARI		= "OTTIMI TITOLARI";
	protected static final String BUONI_TITOLARI		= "BUONI TITOLARI";
	protected static final String SCOMMESSE				= "SCOMMESSE";
	protected static final String JOLLY					= "JOLLY";
	protected static final String TITOLARI				= "TITOLARI";
	protected static final String JOLLY_SECONDA_FASCIA	= "JOLLY SECONDA FASCIA";
	protected static final String LOW_COST				= "LOW COST";
	protected static final String JOLLY_TERZA_FASCIA	= "JOLLY TERZA FASCIA";
	protected static final String LEGHE_NUMEROSE		= "LEGHE NUMEROSE";
	protected static final String GIOVANI				= "GIOVANI";
	protected static final String JOLLY_QUARTA_FASCIA	= "JOLLY QUARTA FASCIA";
	protected static final String RISCHI				= "RISCHI";
	protected static final String DA_EVITARE			= "DA EVITARE";
	protected static final String MERCATO				= "MERCATO";

	
	/////////////////// GIOCATORI
	protected static final int ATT_Kean_FIO			= 2097;
	protected static final int ATT_Zapata_TOR		= 608;
	protected static final int ATT_Lucca_UDI		= 6215;
	protected static final int ATT_Yildiz_JUV		= 6434;
	protected static final int ATT_Piccoli_LEC		= 4359;
	protected static final int ATT_Kristovic_CAG	= 6435;
	protected static final int ATT_Djuric_MON		= 6435;
	protected static final int ATT_Taremi_INT		= 6435;
	protected static final int ATT_Pohjanpalo_VEN	= 5079;
	protected static final int ATT_Noslin_LAZ		= 6556;
	protected static final int ATT_Adams_TOR		= 6646;
	protected static final int ATT_Belotti_MON		= 441;
	protected static final int ATT_Dallinga_BOL		= 6643;
	protected static final int ATT_Lautaro_INT		= 2764;
	protected static final int ATT_Livramento_VER	= 6644;
	protected static final int ATT_Gykjaer_VEN		= 5881;

	protected static final int DIF_TheoHernandez_MIL= 4292;
	protected static final int DIF_Miranda_BOL		= 4734;
	protected static final int DIF_Marì_MON			= 4904;
	protected static final int DIF_RafaMarin_NAP	= 6638;
	protected static final int DIF_JoaoFerreira_UDI	= 6256;
	protected static final int DIF_Mercandalli_GEN	= 6660;
	protected static final int DIF_G__Esteves_UDI	= 6679;
	protected static final int DIF_W__Coulibaly		= 6665;
	protected static final int DIF_A__Carboni		= 4925;
	protected static final int DIF_Borna_Sosa		= 6820;
	protected static final int DIF_Jesus			= 256;
	protected static final int DIF_Dembele			= 6826;
	protected static final int DIF_L__Coulibaly		= 5504;
	protected static final int DIF_Gabi_Jean		= 6883; 
	protected static final int DIF_Pellegrini		= 2728;
	protected static final int DIF_I__Touré			= 5900;
	protected static final int DIF_F__Carboni		= 6149;
	protected static final int DIF_M__Moreno		= 6890;
	protected static final int DIF_A__Moreno		= 5435;
	protected static final int DIF_Vasquez			= 5514;
	
	protected static final int CEN_Anguissa_NAP		= 4220;
	protected static final int CEN_Morente_LEC		= 6634;
	protected static final int CEN_Ellertson_VEN	= 6020;
	protected static final int CEN_Anderson_VEN		= 6674;
	protected static final int CEN_Akpa_Apkro_LAZ	= 5286;
	protected static final int CEN_K__Thuram_JUV	= 5562;
	protected static final int CEN_S__Vignato_MON	= 5879;
	protected static final int CEN_Nico_Paz			= 6875;
	protected static final int CEN_Pellegrini		= 530;
	protected static final int CEN_Iling_Junior		= 6112;
	protected static final int CEN_M__Koné			= 5589;

	protected static final int POR_Milinkovic		= 2170; 
	
//	-- -- --  Joao Ferreira NON TROVATO cat
//	-- -- --  Di Pardo NON TROVATO cat 
//	-- -- --  Bonifazi NON TROVATO cat 
//	-- -- -- L NON TROVATO cat
//	-- -- --  Gioacchini NON TROVATO cat 
	
	/// nomi con punto
	
	protected static final String ECC_DIF_Esteves_UDI	= "G. Esteves";
	protected static final String ECC_DIF_W__Coulibaly	= "W. Coulibaly";
	protected static final String ECC_DIF_L__Coulibaly	= "L. Coulibaly";
	protected static final String ECC_DIF_A__Carboni	= "A. Carboni";
	protected static final String ECC_DIF_I__Touré		= "I. Touré";
	protected static final String ECC_DIF_F__Carboni	= "F. Carboni";
	protected static final String ECC_DIF_M__Moreno		= "M. Moreno";
	protected static final String ECC_DIF_A__Moreno		= "A. Moreno";

	protected static final String ECC_CEN_K__Thuram_JUV	= "K. Thuram";
	protected static final String ECC_CEN_S__Vignato_MON= "S. Vignato";
	protected static final String ECC_CEN_M__Koné		= "M. Koné";
	
	// manca dif bol  Bonifazi NON TROVATO cat
	
	
	/*
	 EMPOLI Perisan NON TROVATO inf
MILAN Sportiello NON TROVATO inf
MONZA Cragno NON TROVATO inf
-- -- --  Djaló NON TROVATO cat
-- -- --  Joao Ferreira NON TROVATO cat
-- -- -- G NON TROVATO cat
-- -- --  Mercandalli NON TROVATO cat
-- -- --  Bonifazi NON TROVATO cat
-- -- -- K NON TROVATO cat
-- -- --  Anguissa NON TROVATO cat
-- -- --  Morente NON TROVATO cat
-- -- --  Ellertson NON TROVATO cat
-- -- -- S NON TROVATO cat
-- -- --  Anderson NON TROVATO cat
-- -- --  Akpa Apkro NON TROVATO cat
-- -- -- Lautaro NON TROVATO cat
-- -- --  Livramento NON TROVATO cat
-- -- --  Gykjaer NON TROVATO cat
	 * */
	
	/////////////////// COPPIE DI ATTACCANTI NON BIG
	protected static final int[] coppiaATT_Lucca_Kean			= {ATT_Lucca_UDI		,ATT_Kean_FIO}; 
	protected static final int[] coppiaATT_Piccoli_Kristovic	= {ATT_Piccoli_LEC		,ATT_Kristovic_CAG}; 
	protected static final int[] coppiaATT_Djuric_Taremi		= {ATT_Djuric_MON		,ATT_Taremi_INT};
	protected static final int[] coppiaATT_Pohjanpalo_Yildiz	= {ATT_Pohjanpalo_VEN	,ATT_Yildiz_JUV};
	protected static final int[] coppiaATT_Noslin_Adams			= {ATT_Noslin_LAZ		,ATT_Adams_TOR};
	protected static final int[] coppiaATT_Zapata_Kean			= {ATT_Zapata_TOR		,ATT_Kean_FIO}; 
	protected static final int[] coppiaATT_Belotti_Kristovic	= {ATT_Belotti_MON		,ATT_Kristovic_CAG};
	protected static final int[] coppiaATT_Pohjanpalo_Dallingaz	= {ATT_Pohjanpalo_VEN	,ATT_Dallinga_BOL};

	
	protected static String rigaContieneCategoria(String rigaLetta, ArrayList<String >arrCategorie){
		String catTrovata	= "";
		for (int i = 0; i < arrCategorie.size(); i++) {
			if (!FrmStringa.strVoid(rigaLetta) && rigaLetta.indexOf(arrCategorie.get(i))!=-1){
				catTrovata	= arrCategorie.get(i);
				break;
			}
		}
		return catTrovata;
	}
}
