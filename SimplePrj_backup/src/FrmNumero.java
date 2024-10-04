


import java.util.Locale;
import java.util.Vector;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

import com.ibm.as400.access.AS400PackedDecimal;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.AS400ZonedDecimal;

/**
 * Gestisce funzionalita' di formattazione numeri
 * @author Tonello Luca
 * @version 1.0.0 14-lug-2005
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class FrmNumero {
	
	private static final long MEGABYTE = 1024L * 1024L;
	private static final long KILOBYTE = 1024L;
	
	/**
	 *Formatta data da numero classico (nnnnnnn.dd) in formato valuta (n.nnn.nnn,dd)
	 *@param numero in formato classico
	 *@return numero in formato valuta
	 *@author Tonello Luca	 
	 **/
	public static String formatValuta(double number) {
		DecimalFormatSymbols symbols=new DecimalFormatSymbols(Locale.ITALIAN);
		DecimalFormat formatter=null;
		formatter = new DecimalFormat("###,##0.00",symbols);
		return formatter.format(number);
	}
	
	/**
	 *Formatta data da numero classico (nnnnnnn.dd) in formato valuta (n.nnn.nnn,dddd)
	 *@param numero in formato classico
	 *@return numero in formato valuta
	 *@author Tonello Luca	 
	 **/
	public static String formatValutaNumDecimaliFisse(double number, int numDec) {
		String parteDecStr = "00";
		if(numDec > 0){
			parteDecStr = "";
			for(int i = 0; i < numDec; i++){
				parteDecStr += "0";
			}
		}
		
		DecimalFormatSymbols symbols=new DecimalFormatSymbols(Locale.ITALIAN);
		DecimalFormat formatter=null;
		formatter = new DecimalFormat("###,##0." + parteDecStr, symbols);
		return formatter.format(number);
	}
	
	/**
	 * Formatta data da numero classico (nnnnnnn.dd) in formato valuta (nnnnnnn,dd) senza separatore di migliaia
	 * @param numero in formato classico (nnnnnnn.dd)
	 * @return numero in formato valuta senza separatore migliaia (nnnnnnn,dd)
	 * @author Rosa Marco - 06/ott/2020	 
	 **/
	public static String formatValutaSenzaSepMigliaia(double number) {
		return formatValuta(number).replaceAll("\\.", "");
	}
	
	/**
	 *Formatta numero da numero formato double a stringa di numeri sempre lunga 12 caratteri senza ne punti ne virgole
	 *@param numero in formato valuta
	 *@return numero in formato classico
	 *@author stagista	 
	 **/
	public static String formatDouble(double number) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALIAN);
		DecimalFormat formatter = null;
		formatter = new DecimalFormat("#####0.00", symbols);
		String numero = formatter.format(number);
		int indice = numero.indexOf(",");
		numero = numero.substring(0, indice) + numero.substring(indice + 1);
		numero = FrmStringa.allineaDx(numero, 15, false);
		numero = numero.replaceAll(" ", "0");
		return numero;
	}
	
	/**
	 *Formatta numero da numero valuta -> "NNN.NNN,NN" a formato double -> NNNNNN.NN
	 *@param numero in formato valuta
	 *@return numero in formato classico
	 *@author Tonello Luca	 
	 **/
	public static double formatNumber(String numValuta){
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALIAN);
		Number n = null; 
		try {
			if(FrmStringa.strVoid(numValuta)){
				n = 0;
			} else {
				n = nf.parse(numValuta);
			}
		}catch(ParseException pe){
			/*gestire eccezione*/
		}
	    return n.doubleValue();
	}

	/**
	 * Formatta numero da numero valuta inglese -> "NNN,NNN.NN" a formato double -> NNNNNN.NN
	 * @param numero in formato valuta inglese "NNN,NNN.NN"
	 * @return numero in formato classico NNNNNN.NN
	 * @author Rosa Marco - 14/dic/2012	 
	 **/
	public static double formatNumberValutaInglese(String numValuta){
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		Number n = null; 
		try {
			n = nf.parse(numValuta);
		}catch(ParseException pe){
			/*gestire eccezione*/
		}
	    return n.doubleValue();
	}

	/**
	 *Formatta un double (nnn.ddddddd) in un formato adatto a rappresentare
	 * un cambio di una valuta ("nnn,ddddddd")
	 * (es. Dollaro: 0.9876 -> "0,9876000")
	 *@param cambio in formato classico (double)
	 *@return cambio in formato valuta (String)
	 *@author Marco Damilano
	 **/
	public static String formatCambio(double number) {
		DecimalFormatSymbols symbols=new DecimalFormatSymbols(Locale.ITALIAN);
		DecimalFormat formatter=null;
		formatter = new DecimalFormat("##,##0.0000000",symbols);
		return formatter.format(number);
	}

	/**
	 *Formatta un double (n.dddddd) in ("n,dddddd")
	 * (es. "13.9876" --> "13,987600")
	 *@author Luca Tonello
	 **/
	public static String formatConsumoCO2(double number) {
		DecimalFormatSymbols symbols=new DecimalFormatSymbols(Locale.ITALIAN);
		DecimalFormat formatter=null;
		formatter = new DecimalFormat("##,##0.000000",symbols);
		return formatter.format(number);
	}

    /**
     * Trasforma un integer in packed (utilizzato per scrivere packed contenuti in un unico campo descritto con DS esterne)
     * @param numero - double - numero da convertire
     * @param dimensioneCampo - int - dimensione totale del campo (ricavabile con DSPFFD nomeDS)
     * @param cifreDecimali - int - numero cifre decimali (ricavabile con DSPFFD nomeDS)
     * @return stringa contenente la codifica packed del numero
     * @author Maurizio Rosso -  17-ago-2006
     */
	public static String toPacked (int numero, int dimensioneCampo, int cifreDecimali){
		String strPacked = null;

		// genera array contenente risultato conversione
		int dimensionePacked = dimensioneCampo/2 +1;
		byte[] bytePack = new byte[dimensionePacked];

		// converte in packed
		AS400PackedDecimal dataPack = new AS400PackedDecimal(dimensioneCampo,cifreDecimali);
        bytePack = dataPack.toBytes(numero);

        // trasforma risultato conversione in stringa
        AS400Text stringaPacked= new AS400Text(dimensionePacked);
        strPacked = stringaPacked.toObject(bytePack).toString();
		
		return strPacked;
	}
	
	public static String toPackedShift (int numero, int dimensioneCampo, int cifreDecimali){
		String strPacked = null;

		// genera array contenente risultato conversione
		int dimensionePacked = dimensioneCampo/2+1;
		byte[] bytePack = new byte[dimensionePacked];			

		// converte in packed
		AS400PackedDecimal dataPack = new AS400PackedDecimal(dimensioneCampo,cifreDecimali);
        bytePack = dataPack.toBytes(numero);
        
        // con un esempio sara spiegato piu bene.
        // il mio numero = 987987 => i bytePack[0]=09; bytePack[1]=87; bytePack[2]=98; bytePack[3]=7F(hexa);
        // una volta che ho i bytes, come il unico tipo senza segno in java e char
        // devo metere i valori dei bytes in variabili di questo tipo
        char ch2=(char)bytePack[1];        
        char ch3=(char)bytePack[2];
        char ch4=(char)bytePack[3];
        ch2 = Character.reverseBytes(ch2);
        ch3 = Character.reverseBytes(ch3);
        ch4 = Character.reverseBytes(ch4);
        
        int nr1 = ((int)bytePack[0])<<24;
        int nr2 = (int)ch2;
        int nr3 = (int)ch3;
        int nr4 = (int)ch4;
        nr2 = (nr2>>8)<<16;
        nr3 = (nr3>>8)<<8;
        nr4 = (nr4>>8);
		int nr = nr1|nr2|nr3|nr4;
		// dopo tutte le instruzioni fatte il mio intero e (in hexa) 09 87 98 7F,
		// adesso posso fare il shift per rimanere con solo 3 byte, quelli che a me interesano
		// il mio numero sara: 00 98 79 87
		nr= nr>>4;
		byte[] bytePackShift= new byte[3];
		bytePackShift[0]= (byte)(nr>>16);	// sara uguale 98
		bytePackShift[1]= (byte)(nr>>8);	// sara uguale 79
		bytePackShift[2]= (byte)nr;			// sara uguale 87
		
        // trasforma risultato conversione in stringa
        AS400Text stringaPacked= new AS400Text(dimensionePacked-1);
        strPacked = stringaPacked.toObject(bytePackShift).toString();        
		
		return strPacked;
	}


    /**
     * Trasforma un long in packed (utilizzato per scrivere packed contenuti in un unico campo descritto con DS esterne)
     * @param numero - double - numero da convertire
     * @param dimensioneCampo - int - dimensione totale del campo (ricavabile con DSPFFD nomeDS)
     * @param cifreDecimali - int - numero cifre decimali (ricavabile con DSPFFD nomeDS)
     * @return stringa contenente la codifica packed del numero
     * @author Maurizio Rosso -  17-ago-2006
     */
	public static String toPacked (long numero, int dimensioneCampo, int cifreDecimali){
		String strPacked = null;

		// genera array contenente risultato conversione
		int dimensionePacked = dimensioneCampo/2 +1;
		byte[] bytePack = new byte[dimensionePacked];

		// converte in packed
		AS400PackedDecimal dataPack = new AS400PackedDecimal(dimensioneCampo,cifreDecimali);
        bytePack = dataPack.toBytes(numero);

        // trasforma risultato conversione in stringa
        AS400Text stringaPacked= new AS400Text(dimensionePacked);
        strPacked = stringaPacked.toObject(bytePack).toString();
		
		return strPacked;
	}

    /**
     * Trasforma un double in packed (utilizzato per scrivere packed contenuti in un unico campo descritto con DS esterne)
     * @param numero - double - numero da convertire
     * @param dimensioneCampo - int - dimensione totale del campo (ricavabile con DSPFFD nomeDS)
     * @param cifreDecimali - int - numero cifre decimali (ricavabile con DSPFFD nomeDS)
     * @return stringa contenente la codifica packed del numero
     * @author Maurizio Rosso -  17-ago-2006
     */
	public static String toPacked (double numero, int dimensioneCampo, int cifreDecimali){
		String strPacked = null;

		// genera array contenente risultato conversione
		int dimensionePacked = dimensioneCampo/2 +1;
		byte[] bytePack = new byte[dimensionePacked];

		// converte in packed
		AS400PackedDecimal dataPack = new AS400PackedDecimal(dimensioneCampo,cifreDecimali);
        bytePack = dataPack.toBytes(numero);

        // trasforma risultato conversione in stringa
        AS400Text stringaPacked= new AS400Text(dimensionePacked);
        strPacked = stringaPacked.toObject(bytePack).toString();
		
		return strPacked;
	}
 
	 /**
     * Formatta un intero con un determinato numero di cifre non significative
     * @param numero  - numero da formattare
     * @param numeroCifre - numero di cifre
     * @return stringa contenente il numero formattato
     * @author Maurizio Rosso -  23-ago-2006
     */
	public static String formattaCifreFisse(long numero, int numeroCifre){
		String strNumero=null;
		String strFormato="";
		
		for (int i=0; i<numeroCifre; i++){
			strFormato+="0";
		}
		
		DecimalFormat formatter = new DecimalFormat(strFormato);
		strNumero=formatter.format(numero);
		
		return strNumero;
	}
		
	 /**
     * Formatta un numero con un determinato numero di cifre decimali non significative
     * @param strNumero  - numero (in formato stringa) da formattare
     * @param separatore
     * @param numeroDecimali - numero di cifre
     * @return stringa contenente il numero formattato
     * @author Alessandro Rossi - 14/mag/2013
     */
	public static String formattaCifreDecimaliFisse(String strNumero, String separatore, int numeroDecimali){
		
		int indexSeparatore = strNumero.indexOf(separatore);
		String strDecimali = "";
		if (indexSeparatore!=-1){
			strDecimali = strNumero.substring(indexSeparatore+1);
		}
		int numDecimaliDaAgg = numeroDecimali - strDecimali.length();
		if (numDecimaliDaAgg>0){
			for (int k=0; k<numDecimaliDaAgg; k++){
				strDecimali += "0";
			}
		}
		if (indexSeparatore!=-1){
			strNumero = strNumero.substring(0, indexSeparatore+1) + strDecimali;
		} else {
			strNumero = strNumero + separatore + strDecimali;
		}
		
		return strNumero;
	}
		
	 /**
     * Formatta un intero con un determinato numero di cifre non significative
     * @param numero  - numero da formattare
     * @param numeroCifre - numero di cifre
     * @return stringa contenente il numero formattato
     * @author BNicolotti -  06-dec-2010
     */
	public static String formattaDecimal(double numero, int numDec, String decSep ){
		String strNumero=null;
		String strFormato=".";
		
		for (int i=0; i<numDec; i++){
			strFormato+="#";
		}
		
		DecimalFormat formatter = new DecimalFormat(strFormato);
		strNumero=formatter.format(numero);
		strNumero= strNumero.replace(",", decSep);
		
		return strNumero;
	}
		
    /**
     * dato un numero zoned, verifica il segno e imposta il valore
     * ultimo byte: il semibyte superiore: "F" positivo, "D" negativo
     * @param campoCompleto (letto da DB come formato stringa)
     * @param ultimaCifraHex (letto da DB come formato esadecimale)
     * @param numCifreDecimali (0 per gli interi, 2 per gli importi, ecc...)
     * @return importo in formato double
     * @author Marco Damilano - 6-nov-2006
     * */
    public static double fromZonedtoDouble (String campoCompleto, String ultimaCifraHex, int numCifreDecimali){
        double importo = 0.0;
        double divisore = 1.0;
        for (int i=0; i<numCifreDecimali;i++){
            divisore = divisore * 10;
        }
        // prendo tutte le cifre dell'importo, ad eccezione dell'ultima cifra, che contiene anche il segno
        try {
        	importo = Double.parseDouble(campoCompleto.substring(0,campoCompleto.length()-1))/(divisore/10.0);
	        // l'ultima cifra si trova nel secondo carattere della stringa di tipo esadecimale
	        double ultimaCifra = (Double.parseDouble(ultimaCifraHex.substring(1, 2)))/divisore;
	        String ultimoByte = ultimaCifraHex.substring(0, 1);
	        importo=importo+ultimaCifra;
	        if (ultimoByte.equals("D")){
	            importo*=-1.0;
	        }
        } catch (NumberFormatException nfE) {
        	importo = 0.0;
		}
        return  importo;
    }

    /**
     * dato un numero double, lo converte in formato zoned
     * @param parNumero (numero double da convertire)
     * @param parNumCifreTot (numero cifre totali)
     * @param parNumCifreDecimali (numero cifre decimali)
     * @return double in formato zoned
     @author Alessandro Rossi - 5-gen-2007
     * */
    public static String fromDoubletoZoned (double parNumero, int parNumCifreTot, int parNumCifreDecimali){
    	
    	String strZoned = null;

		// genera array contenente risultato conversione
		byte[] byteZon = new byte[parNumCifreTot];

		// converte in zoned
		AS400ZonedDecimal dataZon = new AS400ZonedDecimal(parNumCifreTot,parNumCifreDecimali);
		byteZon = dataZon.toBytes(parNumero);

        // trasforma risultato conversione in stringa
        AS400Text stringaZoned = new AS400Text(parNumCifreTot);
        strZoned = stringaZoned.toObject(byteZon).toString();
		
    	return strZoned;
    
    }

	/**
     * Data una stringa controlla se è numerica e <BR>
     * quindi convertibile in double.
     * @param numValuta
     * @return true  -> numerica
     *         false -> non numerica
     *@author Alessandro Rossi - 21/nov/07         
     */
// 	public static boolean isNumeric(String numValuta){
//		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALIAN);
//		boolean numeric = true;
//		try {
//			nf.parse(numValuta);
//		}catch(ParseException pe){
//			/*gestire eccezione*/
//			numeric = false;
//		}
//	    return numeric;
//	}
 	
	/**
	 * Controlla se la stringa è numerica 
	 * @param Stringa
	 * @return True->Numerica; False->Non Numerica
	 * @author Servetti Alessandro - 20/apr/11
	 */
 	public static boolean isNumeric(String prNumValuta){
 		boolean isNum=false;
 		if (prNumValuta!=null){
	 		if(prNumValuta.indexOf("/")!=-1){
	 			//Controllo 1: 02-12-2009 AS: necessario perchè in caso di stringa
	 			//del tipo 000/00 il controllo viene superato con successo, mentre
	 			//in realtà non è propriamente un numero.
	 			isNum=false;
	 		} else {
				try {
			 		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALIAN);
					nf.parse(prNumValuta);
					isNum=true;
				}catch(Exception e){
					isNum=false;
				}
	 		}
 		}
 		return isNum;
	} 

 	/**
     * Data una stringa, restituisce le cifre intere del numero passato escludendo la parte decimale SEPARATA con la virgola.<BR>
     * Se la stringa passata e' null o non valorizzata restituisce 0.
     * @param parStrNumero: stringa contenente il numero
     * @return stringa con le sole cfre intere del numero passato
     * @author Marco Rosa - 18/dic/07         
     */
 	public static String troncaACifreIntere( String parStrNumero){
 		return troncaACifreIntere(parStrNumero, ",");
 	}

 	/**
     * Data una stringa, restituisce le cifre intere del numero passato.<BR>
     * Se la stringa passata e' null o non valorizzata restituisce 0.
     * @param parStrNumero: stringa contenente il numero
     * @param sepDecimali il simbolo che separa la parte intera da quella decimale <br>
     * esempi: <br>
     * - virgola "," <br>
     * - punto "." <br>
     * @return stringa con le sole cfre intere del numero passato
     * @author Marco Damilano - 28/apr/2010         
     */
 	public static String troncaACifreIntere( String parStrNumero, String sepDecimali){
		String ret = "0";
 		int indSeparatore = 0; 
 			
 		if (parStrNumero != null && !parStrNumero.trim().equals("")){
 			ret = parStrNumero;
 	 		indSeparatore = ret.indexOf(sepDecimali);
 			if (indSeparatore != -1){
 				ret = FrmStringa.subStrByLen(ret, 0, indSeparatore);
 			}
 		}

 		return ret;
	}

 	/**
 	 * Riceve un numero intero come Stringa e ne elimina gli zeri non significativi.
 	 * Se la stringa non è un numero restituisce "".
     * @param parStrNumero: stringa contenente il numero 
     * @return stringa reppresentante il numero intero senza gli zeri non significati 
     * @author Marco Rosa - 21/dic/07         
     */
 	public static String cancZeroNonSignificativiSx( String parStrNumero ){
		String ret = "";
		int num = 0;
		
		try {
			num = Integer.parseInt(parStrNumero);
			ret = String.valueOf(num);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ret;
	}

	/**
	 * Formatta numero da numero classico (nnnnnnn.dd) in formato valuta (n.nnn.nnn)
	 * senza decimali
	 * @param numero in formato classico
	 * @return numero in formato valuta senza decimali
	 * @author Alessandro Servetti
	 * @version 1.0.0 24/ott/08	 
	 */
	public static String formatValutaNoDecimal(double prNumber) {
		DecimalFormatSymbols symbols=new DecimalFormatSymbols(Locale.ITALIAN);
		DecimalFormat formatter=null;
		formatter = new DecimalFormat("###,##0",symbols);
		return formatter.format(prNumber);
	}
 	
	public static String getValString(String val){
		String valueLei  = val.substring(0, val.indexOf(",")).replace(".", "/");
		String valueBani = val.substring(val.indexOf(",")+1,val.indexOf(",")+3);
		String[] value = valueLei.split("/");		
		Vector unit = new Vector();
		Vector zeci = new Vector();
		Vector sute = new Vector();		
		
		for(int i=0; i<value.length;i++){
			if(value[i].length()==3){
				sute.add(value[i].substring(0, 1));
				zeci.add(value[i].substring(1, 2));
				unit.add(value[i].substring(2, 3));
			}
			if(value[i].length()==2){
				sute.add(0);
				zeci.add(value[i].substring(0, 1));
				unit.add(value[i].substring(1, 2));
			}
			if(value[i].length()==1){
				sute.add(0);
				zeci.add(0);
				unit.add(value[i].substring(0, 1));
			}
		}
		
		String[] cifre0_9  = {"zero","unu", "doi", "trei", "patru", "cinci", "sase", "sapte", "opt", "noua"};
		
		String valLitere="";
		for(int i= 0; i<value.length; i++){
			switch (Integer.parseInt(sute.get(i).toString())) {
			case 0:	valLitere += getLitereZeciUnitati(Integer.parseInt(zeci.get(i).toString()),Integer.parseInt(unit.get(i).toString()),value.length,i, false);			
					break;
			case 1:	valLitere += "osuta" +			
								 getLitereZeciUnitati(Integer.parseInt(zeci.get(i).toString()),Integer.parseInt(unit.get(i).toString()),value.length,i, false);
					break;
			case 2:	valLitere += "douasute" +			
			 					 getLitereZeciUnitati(Integer.parseInt(zeci.get(i).toString()),Integer.parseInt(unit.get(i).toString()),value.length,i, false);			
					break;
			default:valLitere += cifre0_9[Integer.parseInt(sute.get(i).toString())] + "sute" +			
			 					 getLitereZeciUnitati(Integer.parseInt(zeci.get(i).toString()),Integer.parseInt(unit.get(i).toString()),value.length,i, false);
					break;
			}
		}
		valLitere +=" si " + getLitereZeciUnitati(Integer.parseInt(valueBani.substring(0, 1)),Integer.parseInt(valueBani.substring(1, 2)),0,0, true);;
		System.out.println("valDouble = "+ valLitere);
		return valLitere;
	}
	
	private static String getLitereZeciUnitati(int zeci, int unitati, int nrGiri, int i, boolean decimale){
		String[] grupuriPl 	= {"triliarde", "trilione", "biliarde", "bilioane", "miliarde", "milioane", "mii", "lei" };
		String[] grupuriSg 	= {"triliard", "trilion", "biliard", "bilioan", "miliard", "milion", "mie", "leu"};
		String[] cifre10_19 = {"zece","unsprezece", "doisprezece", "treisprezece", "paisprezece", "cincisprezece", "saisprezece", "saptesprezece", "optsprezece", "nouasprezece"};
		String[] cifre0_9  	= {"zero","unu", "doi", "trei", "patru", "cinci", "sase", "sapte", "opt", "noua"};
		String valZeciUnit="";
		if(zeci==0){
			if(nrGiri==0){
				switch (unitati) {
				case 0: valZeciUnit += cifre0_9[0];
						if(decimale){
							valZeciUnit += " bani";												
						}else{
							valZeciUnit += " "+grupuriPl[grupuriPl.length-1];
						}
						break;
				case 1: valZeciUnit += "un"; 
						if(decimale){
							valZeciUnit += " ban";												
						}else{
							valZeciUnit += " "+grupuriSg[grupuriSg.length-1];
						}
						break;				
				default: valZeciUnit += cifre0_9[unitati];
						if(decimale){
							valZeciUnit += " bani";												
						}else{
							valZeciUnit += " "+grupuriPl[grupuriPl.length-1];
						}
						break;
				}				
			}else{
				switch (unitati) {
				case 0:	if((grupuriPl.length - nrGiri+i) ==grupuriPl.length-1){
							valZeciUnit += " " + grupuriPl[grupuriPl.length-1];
						}
						break;
				case 1:	if(grupuriPl.length - nrGiri+i!=grupuriPl.length-2){
							valZeciUnit += "un";
							if(i==nrGiri-1) valZeciUnit += " ";
							valZeciUnit += grupuriSg[grupuriSg.length - nrGiri+i];
						}else{
							valZeciUnit += "o" ;
							if(i==nrGiri-1) valZeciUnit += " ";
							valZeciUnit += grupuriSg[grupuriSg.length - nrGiri+i];
						}
						break;
				case 2:	valZeciUnit += "doua";
						if(i==nrGiri-1) valZeciUnit += " ";
						valZeciUnit += grupuriPl[grupuriPl.length - nrGiri+i];				
						break;
				default: valZeciUnit += cifre0_9[unitati];
						 if(i==nrGiri-1) valZeciUnit += " ";
						 valZeciUnit += grupuriPl[grupuriPl.length - nrGiri+i];
						 break;
				}
			}
		}else{
			switch (zeci) {
			case 1:	valZeciUnit += cifre10_19[unitati];		
					if(decimale){
						valZeciUnit += " bani";												
					}else{						
						if(i==nrGiri-1) valZeciUnit += " ";
						valZeciUnit += grupuriPl[grupuriPl.length - nrGiri+i];						
					}
					break;
			case 2:	if(unitati==0){
						valZeciUnit += "douazeci";
						if(decimale){
							valZeciUnit += " bani";												
						}else{
							if(i==nrGiri-1) valZeciUnit += " ";
							valZeciUnit += grupuriPl[grupuriPl.length - nrGiri+i];							
						}
					}else{
						valZeciUnit += "douazecisi"+cifre0_9[unitati];
						if(decimale){
							valZeciUnit += " bani";												
						}else{
							if(i==nrGiri-1) valZeciUnit += " ";
							valZeciUnit += grupuriPl[grupuriPl.length - nrGiri+i];
						}
					}			
					break;
			case 6:	if(unitati==0){
						valZeciUnit += "saizeci";
						if(decimale){
							valZeciUnit += " bani";												
						}else{
							if(i==nrGiri-1) valZeciUnit += " ";
							valZeciUnit += grupuriPl[grupuriPl.length - nrGiri+i];
						}
					}else{
						valZeciUnit += "saizecisi"+cifre0_9[unitati];
						if(decimale){
							valZeciUnit += " bani";												
						}else{
							if(i==nrGiri-1) valZeciUnit += " ";
							valZeciUnit += grupuriPl[grupuriPl.length - nrGiri+i];
						}
					}			
					break;
			default: if(unitati==0){
						valZeciUnit += cifre0_9[zeci]+ "zeci";
						if(decimale){
							valZeciUnit += " bani";												
						}else{
							if(i==nrGiri-1) valZeciUnit += " ";
							valZeciUnit += grupuriPl[grupuriPl.length - nrGiri+i];
						}
					 }else{
						 valZeciUnit += cifre0_9[zeci]+ "zecisi"+cifre0_9[unitati];
						 if(decimale){
							valZeciUnit += " bani";												
						}else{
							if(i==nrGiri-1) valZeciUnit += " ";
							valZeciUnit += grupuriPl[grupuriPl.length - nrGiri+i];
						}
					 }
					break;
			}
		}
		return valZeciUnit;
	}
	

    /**
     * Converte un numero in lettere
     * @param n
     * @return
     * @author Alessandro Rossi - 01/giu/2012
     */
    public static String ricavaNumeroInLettere(long numero_lng) {
    	String numero_str = String.valueOf(numero_lng);
        String decimale_stringa = "";

        if (numero_str.length() > 2) {
            decimale_stringa = numero_str.substring(numero_str.length() - 2,
                    numero_str.length());
            numero_str = numero_str.replace(',', '.');
            double numero = (Double.parseDouble(numero_str)) / 100;
            int intero = (int) numero;
            String test = NumberToText(intero);
            return test + "/" + decimale_stringa;
        } else {
            if (numero_str.length() > 1) {
                return "0," + numero_str;
            }
            if (numero_str.length() == 1) {
                return "0,0" + numero_str;
            }

        }
        return "";

    }

    
	/**
	 * @param n
	 * @return
	 * @author Alessandro Rossi - 01/giu/2012
	 */
    public static String NumberToText(int n) {
        if (n < 0) {
            return "meno" + NumberToText(-n);
        } else if (n == 0) {
            return "";
        } else if (n <= 19) {
            return new String[]{"uno", "due", "tre", "quattro", "cinque",
                        "sei", "sette", "otto", "nove", "dieci", "undici",
                        "dodici", "tredici", "quattordici", "quindici", "sedici",
                        "diciasette", "diciotto", "diciannove"}[n - 1] + "";
        } else if (n <= 99) {
            String num_successivo = NumberToText(n % 10);
            if (num_successivo.equals("")) {
                return new String[]{"venti", "trenta", "quaranta",
                            "cinquanta", "sessanta", "settanta", "ottanta",
                            "novanta"}[n / 10 - 2] + "" + num_successivo;
            }
            if (vocale(num_successivo.charAt(0))) {
                return new String[]{"vent", "trent", "quarant", "cinquant",
                            "sessant", "settant", "ottant", "novant"}[n / 10 - 2] + "" + num_successivo;
            } else {
                return new String[]{"venti", "trenta", "quaranta",
                            "cinquanta", "sessanta", "settanta", "ottanta",
                            "novanta"}[n / 10 - 2] + "" + num_successivo;
            }
        } else if (n <= 199) {
            return "cento" + NumberToText(n % 100);
        } else if (n <= 999) {
            return NumberToText(n / 100) + "cento" + NumberToText(n % 100);
        } else if (n <= 1999) {
            return "mille" + NumberToText(n % 1000);
        } else if (n <= 999999) {
            return NumberToText(n / 1000) + "mila" + NumberToText(n % 1000);
        } else if (n <= 1999999) {
            return "unmilione" + NumberToText(n % 1000000);
        } else if (n <= 999999999) {
            return NumberToText(n / 1000000) + "milioni" + NumberToText(n % 1000000);
        } else if (n <= 1999999999) {
            return "unmiliardo" + NumberToText(n % 1000000000);
        } else {
            return NumberToText(n / 1000000000) + "miliardi" + NumberToText(n % 1000000000);
        }

    }

    /**
     * 
     * @param c
     * @return
     * @author Alessandro Rossi - 01/giu/2012
     */
    private static boolean vocale(char c) {
        if ((c == 'a') || (c == 'e') || (c == 'i') || (c == 'o') || (c == 'u')) {
            return true;
        }

        return false;

    }
    
    /**
     * Approssima importo per eccesso in base al livello di approsimazione indicato,
     * se non indicato alcun livello approssima all'intero superiore
     * @param importo - importo da arrotondare per eccesso
     * @param livelloApprossimazione - livello di approssimazione
     * @return
     * @throws SQLException
     * @author Rosa Marco - 18/ago/2014
     */
	public static double approssimaImportoPerEccesso(double importo, double livelloApprossimazione){
		// arrotondamento ad "euro" superiore
		double impFinale = 0.0;
		if(livelloApprossimazione > 0){
			impFinale = Math.ceil( importo / livelloApprossimazione ) * livelloApprossimazione;
		} else {
			// approssimazione ad euro superiore (default)
			impFinale = Math.ceil(importo);
		}
		return impFinale;
	}

	/**
	 * conversione size to string
     * @author Davide Sponselli - 08/ott/2014    
     **/
	public static String readableFileSize(long size) {
	    if(size <= 0) return "0";
	    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
	/**
	 * Il metodo ritorna una stringa con il numero(int) passato come parametro, se il numero è 0 ritorna ""
	 * @author Alex Nota - 13/dic/2017
	 * */
	public static String stringaVuotaSeZero(int numero){
		
		if(numero == 0){
			return "";
		} else {
			return Integer.toString(numero);
		}		
	}

	/**
	 * Converte l'importo da double a formato String senza separatore decimali. Esempio: 120.23 -> "12023"
	 */
	public static String formattaDoubleSenzaSeparatore(double amount) {
		String[] amountArr = String.valueOf(amount).split("\\.");
		String decimaliStr = amountArr[1].length() == 2 ? amountArr[1] : amountArr[1] + "0";
		return amountArr[0] + decimaliStr;
	}

	/**
	 * Controlla se una stringa è un intero
	 */
	public static boolean isInteger(String str) {
		if (FrmStringa.strVoidOrNull(str)) {
			return false;
		}
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Converte un array di importi in formato valuta in un Vector di double
	 * @param arrImportiInValuta
	 * @author Oltean Cristina - 14/feb/24
	 */
	public static Vector<Double> convertArrInValutaToVector (String[] arrImportiInValuta){
		Vector<Double> ret = new Vector<Double>(0,1);

		if (arrImportiInValuta != null){
			int lenVet = arrImportiInValuta.length;
			for (int indice = 0; indice < lenVet; indice++){
				ret.addElement(FrmNumero.formatNumber(arrImportiInValuta[indice])); 
			}
		}

		return ret;		
	}
	
	/**
	 * Converte un vettore double in un array di String formato valuta
	 * @param vecImporti
	 * @author Oltean Cristina - 19/feb/24
	 */
	public static String[] convertVecToArrInValuta (Vector<Double> vecImporti){
		String[] ret = null;

		if (vecImporti != null){
			int lenVet = vecImporti.size();
			ret = new String[lenVet];
			
			for (int indice = 0; indice < lenVet; indice++){
				ret[indice] = (FrmNumero.formatValuta(vecImporti.get(indice))); 
			}
		}

		return ret;		
	}
}