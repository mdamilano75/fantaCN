/*
 * Created on 7-mar-2005
 *
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.mail.Address;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.oreilly.servlet.MultipartRequest;
@SuppressWarnings({"rawtypes", "unchecked"})
/**
 * Gestisce funzionalita' di formattazione STRINGHE
 * @version 1.0 01-mar-2005
 */
public class FrmStringa {
	
	public static final ArrayList<String> lettere_alfabeto = new ArrayList<String>();
	
	static{
		lettere_alfabeto.add("A"); lettere_alfabeto.add("B"); lettere_alfabeto.add("C"); lettere_alfabeto.add("D"); lettere_alfabeto.add("E");
		lettere_alfabeto.add("F"); lettere_alfabeto.add("G"); lettere_alfabeto.add("H"); lettere_alfabeto.add("I"); lettere_alfabeto.add("J");
		lettere_alfabeto.add("K"); lettere_alfabeto.add("L"); lettere_alfabeto.add("M"); lettere_alfabeto.add("N"); lettere_alfabeto.add("O");
		lettere_alfabeto.add("P"); lettere_alfabeto.add("Q"); lettere_alfabeto.add("E"); lettere_alfabeto.add("R"); lettere_alfabeto.add("S");
		lettere_alfabeto.add("T"); lettere_alfabeto.add("U"); lettere_alfabeto.add("V"); lettere_alfabeto.add("W"); lettere_alfabeto.add("X");
		lettere_alfabeto.add("Y"); lettere_alfabeto.add("Z");
	}
	
	/**
	 * allinea stringa a destra  
	 *@param stringa da allineare
	 *@param lunghezza massima stringa
	 *@return stringa allineata a destra
	 *@author Rosso Maurizio
	 */
	public static String allineaDx(String parStringa, int parLung) {

		return allineaDxFilling( parStringa, " ", parLung );
	}

	/**
	 * allinea stringa a destra  
	 *@param stringa da allineare
	 *@param carattere riempimento
	 *@param lunghezza massima stringa
	 *@return stringa allineata a destra
	 *@author Bartolomeo Nicolotti
	 */	
	public static String allineaDxFilling(String parStringa, String fillingChar, int parLung) {
		return allineaDxFilling(parStringa, fillingChar, parLung, true);
	}
	
	/**
	 * allinea stringa a destra  
	 *@param stringa da allineare
	 *@param carattere riempimento
	 *@param lunghezza massima stringa
	 *@return stringa allineata a destra (piempita con carattere di riempimento ANCHE se stringa vuota)
	 *@author Bartolomeo Nicolotti
	 */	
	public static String allineaDxFilling(String parStringa, String fillingChar, int parLung, boolean soloStringaCarica) {

		String stringaDx ="";
		int lunghezza=0;
		int i=0;

		if(parStringa!=null && ( (!parStringa.equals("") && soloStringaCarica) || !soloStringaCarica) ){

			lunghezza=parStringa.trim().length() ;

			if (lunghezza == parLung){
				stringaDx=parStringa.trim();
			}
			else {
				for (i=lunghezza; i<parLung ; i++){
					stringaDx=stringaDx + fillingChar;
				}

				stringaDx=stringaDx + parStringa.trim();
			}
		}			
		return stringaDx;
	}

	/**
	 * checks if the request is on https protocol, also behind a proxy/banancer if 
	 * x-forwarded-procol
	 * is set
	 * https://stackoverflow.com/questions/25911469/request-getscheme-is-returning-http-instead-of-returning-https-in-java
	 * for example apache config:
	 * https://serverfault.com/questions/257616/requestheader-with-apache-environment-variable
	 * @param request
	 * @return
	 */
	public static boolean isHttps(HttpServletRequest request){
		return
		FrmStringa.trimNotNull(request.getScheme() ).toLowerCase().matches("https")
        	|| 
        FrmStringa.trimNotNull(request.getHeader("x-forwarded-proto") ).toLowerCase().matches("https")
        	||
        FrmStringa.trimNotNull(request.getRequestURL() ).toLowerCase().startsWith("https");
	}
	/**
	 * allinea stringa a sinistra (solo stringa carica) 
	 *@param stringa da allineare
	 *@param carattere riempimento
	 *@param lunghezza massima stringa
	 *@return stringa allineata a sinistra
	 *@author Bartolomeo Nicolotti
	 */	
	public static String allineaSxFilling(String parStringa, String fillingChar, int parLung) {
		return allineaSxFilling(parStringa, fillingChar, parLung, true);
	}


	/**
	 * allinea stringa a sinistra  
	 *@return stringa allineata a sinistra (piempita con carattere di riempimento ANCHE se stringa vuota)
	 *@author Maurizio Rosso - 21/mag/2015
	 */	
	public static String allineaSxFilling(String parStringa, String fillingChar, int parLung, boolean soloStringaCarica) {

		String stringaSx ="";
		int lunghezza=0;
		int i=0;

		if(parStringa!=null && ( (!parStringa.equals("") && soloStringaCarica) || !soloStringaCarica) ){

			lunghezza=parStringa.trim().length() ;

			if (lunghezza == parLung){
				stringaSx=parStringa;
			}
			else {
				for (i=lunghezza; i<parLung ; i++){
					stringaSx=stringaSx + fillingChar;
				}

				stringaSx=parStringa.trim() + stringaSx;
			}
		}			
		return stringaSx;
	}

	/**
	 *Allinea stringa a destra  
	 *@param stringa da allineare
	 *@param lunghezza massima stringa
	 *@param soloStringaCarica true/false 
	 *- true: effettua l'allineamento solo se la stringa non è vuota
	 *- false: effettua sempre l'allineamento
	 *@return stringa allineata a sinistra
	 *@author Tonello Luca
	 */
	public static String allineaDx(String parStringa, int parLung, boolean soloStringaCarica) {

		String stringaDx ="";
		int lunghezza=0;
		int i=0;

		if(parStringa!=null && ( (!parStringa.equals("") && soloStringaCarica) || !soloStringaCarica) ){
			lunghezza=parStringa.trim().length() ;          
			if (lunghezza == parLung){
				stringaDx=parStringa;
			} else {
				for (i=lunghezza; i<parLung ; i++){
					stringaDx=stringaDx + " ";
				}                   
				stringaDx=stringaDx + parStringa.trim();
			}
		}           
		return stringaDx;
	}

	/**
	 * allinea stringa a destra senza trimmare la stringa di partenza
	 *@param stringa da allineare
	 *@param lunghezza massima stringa
	 *@param trimma/non trimma la stringa passata come parametro
	 *@param soloStringaCarica (true/false)
	 *@return stringa allineata a destra
	 *@author Alessandro Rossi - 13-nov-2006
	 */
	public static String allineaDx(String parStringa, int parLung, boolean trimma, boolean soloStringaCarica) {

		String stringaDx ="";
		int lunghezza=0;
		int i=0;

		if(parStringa!=null && ((!parStringa.equals("") && soloStringaCarica) || !soloStringaCarica)){			

			if (trimma){
				parStringa=parStringa.trim();
			}
			lunghezza=parStringa.length() ;

			if (lunghezza == parLung){
				stringaDx=parStringa;
			}
			else {
				for (i=lunghezza; i<parLung ; i++){
					stringaDx=stringaDx + " ";
				}

				stringaDx=stringaDx + parStringa;
			}
		}			
		return stringaDx;
	}
	/**
	 *Allinea stringa a sinistra  
	 *@param stringa da allineare
	 *@param lunghezza massima stringa
	 *@param soloStringaCarica true/false 
	 *- true: effettua l'allineamento solo se la stringa non è vuota
	 *- false: effettua sempre l'allineamento
	 *@return stringa allineata a sinistra
	 *@author Tonello Luca
	 */
	public static String allineaSx(String parStringa, int parLung, boolean soloStringaCarica) {

		String stringaSx ="";
		int lunghezza=0;
		int i=0;

		// nel caso si voglia SEMPRE l'allineamento
		if(!soloStringaCarica && parStringa==null){
			parStringa="";
		}

		if(!soloStringaCarica || !parStringa.equals("")){
			lunghezza=parStringa.trim().length() ;          
			if (lunghezza >= parLung){
				stringaSx=parStringa;
			} else {
				for (i=lunghezza; i<parLung ; i++){
					stringaSx=stringaSx + " ";
				}                   
				stringaSx=parStringa.trim() + stringaSx;
			}
		}           
		return stringaSx;
	}  



	/**
	 * tronca la stringa ai primi n caratteri - n > SiapCommonClass.notTruncated 
	 */
	public static String subStrAfter( Object par, String sep ){
		String strRet = "";
		if( par != null ){
			String parStr = par.toString();
			int first = parStr.indexOf(sep);
			int length = parStr.length();
			strRet = subStrByLen( parStr, first+1, length );
		}
		return strRet;
	}
	
	public static String formatKeyBgt(String unitaBgt, String codiceBgt, String serieBgt, long numeroBgt, String flagRimborsoBgt){
		return unitaBgt+"~"+codiceBgt+"~"+serieBgt+"~"+numeroBgt+"~"+flagRimborsoBgt+"~*";
	}

	public static String formatKeyPrtVou(String tipo, int anno, int numero){
		return tipo+"~"+anno+"~"+numero+"~*";
	}

	/**
	 * Funzionalita' identica alla substring della classe String.
	 * Differenza: se l'indice iniziale e' negativo o superiore alla lunghezza della 
	 * stringa in INPUT non produce un Exception, ma restituisce la stringa a 
	 * partire dal primo carattere fino alla lunghezza della stessa.
	 * INDICE INIZIO STRINGA: 0
	 * @param parStr: Stringa iniziale  
	 * @param parBeginIndex: indice iniziale, incluso.
	 * @param parEndIndex: indice finale, escluso.
	 * @return sottostringa specificata
	 * @author Marco Rosa
	 */
	public static String subStrByLen(Object par, int parBeginIndex, int parEndIndex){

		String ret = "";

		if (par != null){
			String parStr = par.toString();
			ret = parStr;

			if(parBeginIndex < 0){
				parBeginIndex = 0;
			}
			if(parEndIndex < 0){
				parEndIndex = 0;
			}

			if(parStr.length() > parBeginIndex){
				if(parStr.length() < parEndIndex){
					ret = parStr.substring(parBeginIndex);
				}else{
					ret = parStr.substring(parBeginIndex,parEndIndex);
				}
			} else {
				ret = "";
			}
		}
		return ret;
	}

	/**
	 * Funzionalita' identica alla substring della classe String.
	 * Differenza: se l'indice iniziale e' negativo o superiore alla lunghezza della 
	 * stringa in INPUT non produce un Exception, ma restituisce la stringa a 
	 * partire dal primo carattere fino alla lunghezza della stessa.
	 * INDICE INIZIO STRINGA: 0
	 * @param parStr: Stringa iniziale  
	 * @param parBeginIndex: indice iniziale, incluso.
	 * @param parEndIndex: indice finale, escluso.
	 * @return sottostringa specificata
	 * @author Marco Rosa
	 */
	public static String subStrByLen(String parStr, int parBeginIndex){

		String ret = "";
		if (parStr != null){
			ret = parStr;

			if(parBeginIndex < 0){
				parBeginIndex = 0;
			}

			if(parStr.length() < parBeginIndex){
				ret = "";
			}else{
				ret = parStr.substring(parBeginIndex);
			}
		}
		return ret;
	}

	/**
	 * Restituisce una stringa troncata con puntini di sospensione alla lunghezza 
	 * specificata (puntini di sospenzione esclusi) 
	 * @param parStr: Stringa da troncare
	 * @param parLen: lunghezza per troncamento 
	 * @return Stringa troncata alla lunghezza specificata
	 */
	public static String setStrByLen(String parStr, int parLen){
		String ret = null;
		ret = parStr;
		if (parStr.length() > parLen){
			ret = ret.substring(0,parLen);
			ret += "...";
		}
		return ret;
	}

	/**
	 * valore senza spazi agli estremi e non null
	 * @param str
	 * @return
	 */
	public static String toStringNotNull( Object obj ){
		String str="";
		if ( obj !=null ){
			str = obj.toString();
		}
		return str;
	}

	/**
	 * valore senza spazi agli estremi e non null
	 * @param str
	 * @return
	 */
	public static String trimNotNull( Object obj ){
		String str="";
		if ( obj !=null ){
			str = trimNotNull( obj.toString() );
		}
		return str;
	}

	/**
	 * valore Stringa non null
	 * @param str
	 * @return
	 */
	public static String valueOfNotNull( Object obj ){
		String str="";
		if ( obj !=null ){
			str = obj.toString();
		}
		return str;
	}


	/**
	 * valore senza spazi agli estremi e non null
	 * @param str
	 * @return
	 */
	public static String trimNotNull( String str ){
		if ( str !=null ){
			str = str.trim();
		}else{
			str = "";
		}
		return str;
	}
	
	/**
     * valore senza spazi agli estremi e non null
     * @param str
     * @return
     */
    public static String trimIncludingNonbreakingSpaceNotNull( String str ){
        if ( str !=null ){
            str = str.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
        }else{
            str = "";
        }
        return str;
    }

	/**
	 * se il valore attuale è null, torna il valore di default. se il default è null, torna blanc
	 * @author Marco Damilano - 07/gen/2016
	 */
	public static String trimNotNullDef( String str, String strDef ){
		if ( str !=null ){
			str = str.trim();
		}else if ( strDef !=null ){
			str = strDef;
		}else{
			str = "";
		}
		return str;
	}

	/**
	 * trim con controllo su null
	 * @param str
	 * @return
	 */
	public static String trim( Object obj ){
		String str= null;
		if ( obj !=null ){
			str = obj.toString().trim();
		}
		return str;
	}

	/** 
	 * Data una stringa, ritorna il trim a destra  
	 * @author Marco Damilano - 18/mag/2012 
	 */  
	public static String rtrim(String s) {
		if (s!=null){
			if(("").equals(s.trim())){
				return "";
			} else {
				int i = s.length() - 1;
				while (i > 0 && Character.isWhitespace(s.charAt(i))) {
					i--;
				}
				return s.substring(0, i + 1);
			}
		} else {
			return s;
		}
	} 

	/**
	 * toUpperCase con controllo su null
	 * @param str
	 * @return
	 */
	public static String toUpper( Object str ){
		String retVal = null;
		if ( str !=null ){
			retVal = str.toString().toUpperCase();
		}
		return retVal;
	}

	/**
	 * toUpperCase con controllo su null
	 * @param str
	 * @return
	 */
	public static String toUpperNotNull( Object str ){
		String retVal = "";
		if ( str !=null ){
			retVal = str.toString().toUpperCase();
		}
		return retVal;
	}

	/**
	 * trim.toUpperCase con controllo su null
	 * @param str
	 * @return
	 */
	public static String trimToUpper( String str ){
		if ( str !=null ){
			str = str.trim().toUpperCase();
		}	
		return str;
	}

	/**
	 * Restituisce parametro senza spazi agli estremi e maiuscolo
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParameterTrimmedUpperCase(  HttpServletRequest request, String parName ){
		return FrmStringa.trimToUpper( request.getParameter( parName ) ) ;
	}

	/**
	 * Restituisce parametro senza spazi agli estremi e maiuscolo
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParameterTrimmedUpperCase(  MultipartRequest request, String parName ){
		return FrmStringa.trimToUpper( request.getParameter( parName ) ) ;
	}

	/**
	 * Restituisce il parametro senza spazi agli estremi, in maiuscolo e non null
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParameterTrimmedUpperCaseNotNull(  HttpServletRequest request, String parName ){
		String retVal = getParameterTrimmedUpperCase(  request, parName );
		if( retVal == null ){
			retVal = "";
		}
		return retVal;
	}

	/**
	 * Restituisce il parametro senza spazi agli estremi, in maiuscolo e non null
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParameterTrimmedUpperCaseNotNull(  MultipartRequest request, String parName ){
		String retVal = getParameterTrimmedUpperCase(  request, parName );
		if( retVal == null ){
			retVal = "";
		}
		return retVal;
	}

	/**
	 * Restituisce il parametro senza spazi agli estremi e non null
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParameterNotNull(  HttpServletRequest request, String parName ){
		String retVal = getParameterNotNullType(request, parName, "S");
		return retVal;
	}

	/**
	 * Restituisce il parametro a seconda del tipo non null
	 * @param request
	 * @param parName
	 * @param type
	 * D --> double
	 * I --> integer
	 * S --> string
	 * @return
	 */
	public static String getParameterNotNullType(HttpServletRequest request, String parName, String type){
		String retVal = request.getParameter(parName);
		if(retVal == null || retVal.equals("null")){
			if(type.equals("D")){
				retVal = "0,00";
			} else if (type.equals("I")){
				retVal = "0";
			} else if (type.equals("S")){
				retVal = "";	
			}
		}
		return retVal;
	}


	/**
	 * Restituisce il parametro senza spazi agli estremi e non null
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParameterNotNull(  MultipartRequest request, String parName ){
		String retVal = request.getParameter( parName );
		if( retVal == null ){
			retVal = "";
		}
		return retVal;
	}

	/**
	 * Restituisce il parametro senza spazi agli estremi e non null
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParametersNotNull(  HttpServletRequest request, String parName ){
		String retVal = "";
		String[] values = request.getParameterValues( parName );
		if( values == null ){
			retVal = "";
		} else {
			for (int index=0; index < values.length; index++){
				retVal += "," + values[index];
			}
			retVal = FrmStringa.subStrByLen(retVal, 1);
		}
		return retVal;
	}

	/**
	 * Restituisce il parametro senza spazi agli estremi e non null
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParametersNotNull(  MultipartRequest request, String parName ){
		String retVal = "";
		String[] values = request.getParameterValues( parName );
		if( values == null ){
			retVal = "";
		} else {
			for (int index=0; index < values.length; index++){
				retVal += "," + values[index];
			}
			retVal = FrmStringa.subStrByLen(retVal, 1);
		}
		return retVal;
	}

	/**
	 * Restituisce il parametro senza spazi agli estremi e non null
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParameterTrimmedNotNull(  HttpServletRequest request, String parName ){
		String retVal = trim( getParameterNotNull( request, parName ) );
		return retVal;
	}

	/**
	 * Restituisce il parametro senza spazi agli estremi e non null
	 * @param request
	 * @param parName
	 * @return
	 */
	public static String getParameterTrimmedNotNull(  MultipartRequest request, String parName ){
		String retVal = trim( getParameterNotNull( request, parName ) );
		return retVal;
	}

	/**
	 * Controlla se l'oggetto corrisponde alla stringa vuota o null
	 * @param obj
	 * @return
	 */
	public static boolean strVoid( Object obj ){
		boolean strNulla = true;
		if( obj != null){
			strNulla = strVoid( obj.toString() );
		}
		return strNulla;
	}

	/**
	 *  Verifica se la stringa e' null o ""
	 * @param str: oggetto da verificare 
	 * @return boolean true se la stringa é null o ""
	 */
	public static boolean strVoid( String str){
		boolean strNulla = true;
		if( str != null){
			if( !str.equals("") ){
				strNulla = false;
			}
		}
		return strNulla;
	}

	/**
	 * Verifica se la stringa e' null o "" oppure "null"(in stringa)
	 * @param obj: oggetto da verificare
	 * @return boolean true se la stringa é null o "" oppure "null"(in stringa)
	 */
	public static boolean strVoidOrNull( Object obj ){
		boolean strNulla = true;
		if( obj != null){
			String strTest = obj.toString().trim();
			strNulla = strVoid(strTest);
			if (!strNulla){
				if (strTest.equals("null")){
					strNulla = true;
				}
			}
		}
		return strNulla;
	}

	/**
	 *  Verifica se la stringa e' null o ""
	 * @param str: oggetto da verificare 
	 * @return boolean true se la stringa é null o ""
	 */
	public static boolean strVoid( String[] str){
		boolean strNulla = true;
		if( str != null){
			if( str.length>0 ){
				if( !strVoid(str[0]) ){
					strNulla = false;
				}
			}
		}
		return strNulla;
	}

	public static String nullIfStrVoid( Object str ){
		String retVal = null;
		if( ! FrmStringa.strVoid(str) ){
			retVal = str.toString();
		}
		return retVal;
	}

	/**
	 * strEq indica se due stringhe sono uguali, gestendo anche null, case sensitive
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean strEq( Object obj1, Object obj2 ){
		return strCmp( obj1, obj2) ==0;
	}

	/**
	 * strEq indica se due stringhe sono uguali, gestendo anche null, parametro per ingoreCase
	 * @param obj1
	 * @param obj2
	 * @param ignoreCase: true case insensitive
	 * @return
	 */
	public static boolean strEq( Object obj1, Object obj2, boolean ignoreCase ){
		return strCmp( obj1, obj2, ignoreCase ) ==0;
	}


	/**
	 * strYesOrSi indica se una stringa e' Y o S
	 * @param obj1
	 * @param obj2
	 * @param ignoreCase: true case insensitive
	 * @return
	 */
	public static boolean strYesOrSi( Object obj1, boolean ignoreCase ){
		return strEq( obj1, "Y", ignoreCase ) || strEq( obj1, "S", ignoreCase );
	}

	/**
	 * strYesOrSi indica se una stringa e' una di "Y", "S", "y", "s" 
	 * @param obj1
	 * @param obj2
	 * @param ignoreCase: true case insensitive
	 * @return
	 */
	public static boolean strYesOrSi( Object obj1 ){
		return strYesOrSi( obj1, true );
	}

	/**
	 * strYesOrSi indica se una stringa e' P|B|V
	 * @param obj1
	 * @param obj2
	 * @param ignoreCase: true case insensitive
	 * @return
	 */
	public static boolean strPBV( Object obj1 ){
		return strEq( obj1, "P", false ) || strEq( obj1, "B", false ) || strEq( obj1, "V", false );
	}

	/**
	 * Confronta 2 oggetti tramite toString(), case sensitive
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static int strCmp( Object obj1, Object obj2 ){
		return strCmp( obj1,obj2, false );
	}

	/**
	 * Confronta 2 oggetti tramite toString() con parametro per ingoreCase
	 * @param obj1
	 * @param obj2
	 * @param ignoreCase = true ignora maiuscole/minuscole
	 * @return obj1-obj2
	 */
	public static int strCmp( Object obj1, Object obj2, boolean ignoreCase ){
		int res = 1;
		if( obj1 == null || obj2 == null){
			if( obj1 != null /*=> obj2 == null*/ ){
				res = obj1.toString().length(); //
			}else if( obj2 != null /*=> obj1 =0 null*/ ){
				res = -obj2.toString().length();
			}else{
				// obj1 != null && obj2 == null 
				res = 0;
			}
		}else{
			String str1 = obj1.toString();
			String str2 = obj2.toString();
			if( ignoreCase ){
				str1 = str1.toUpperCase();
				str2 = str2.toUpperCase();
			}
			res = str1.compareTo( str2 );
		}
		return res;
	}


	/**
	 * obj1.indexOf( obj2 ) with null check
	 * @param obj1
	 * @param obj2
	 * @param ignoreCase = true ignora maiuscole/minuscole
	 * @return obj1-obj2
	 */
	public static int strIndexOf( Object obj1, Object obj2, boolean ignoreCase ){
		int res = -1;
		if( obj1 != null && obj2 != null ){
			String str1 = obj1.toString();
			String str2 = obj2.toString();
			if( ignoreCase ){
				str1 = str1.toUpperCase();
				str2 = str2.toUpperCase();
			}
			res = str1.indexOf( str2 );
		}
		return res;
	}

	/**
	 * Restituisce il numero di caratteri iniziali = tra 2 oggetti tramite toString(), case sensitive
	 * @param obj1
	 * @param obj2
	 * @return obj1-obj2
	 */
	public static int strStartEq( Object obj1, Object obj2 ){
		return strStartEq( obj1, obj2, false );
	}

	/**
	 * Restituisce il numero di caratteri iniziali = tra 2 oggetti tramite toString() con parametro per ingoreCase
	 * @param obj1
	 * @param obj2
	 * @param ignoreCase = true ignora maiuscole/minuscole
	 * @return obj1-obj2
	 */
	public static int strStartEq( Object obj1, Object obj2, boolean ignoreCase ){
		int res = -1;
		if( obj1 == null || obj2 == null){
			if( obj1 != null /*=> obj2 == null*/ ){
				res = obj1.toString().length(); //
			}else if( obj2 != null /*=> obj1 =0 null*/ ){
				res = -obj2.toString().length();
			}else{
				// obj1 != null && obj2 == null 
				res = 0;
			}
		}else{
			String str1 = obj1.toString();
			String str2 = obj2.toString();
			if( ignoreCase ){
				str1 = str1.toUpperCase();
				str2 = str2.toUpperCase();
			}
			int i=0;
			for( i=0; i< str1.length() && i< str2.length(); i++){
				if( str1.charAt(i) != str2.charAt(i) ){
					break;
				}
			}
			res = i;
		}
		return res;
	}

	/**
	 * converte string a short con controllo su null
	 * @param str
	 * @return
	 */
	public static short parseShort( Object obj ){
		short retVal = 0;
		if( !strVoid( obj ) ){
			try{
				retVal = Short.parseShort( obj.toString().trim() );
			}catch( NumberFormatException e ){
			}
		}
		return retVal;
	}

	/**
	 * converte string a intero con controllo su null
	 * @param str
	 * @return
	 */
	public static int parseInt( Object obj ){
		int retVal =0;
		if( !strVoid( obj ) ){
			try{
				retVal = Integer.parseInt( obj.toString().trim() );
			}catch( NumberFormatException e ){
			}
		}
		return retVal;
	}

	/**
	 * converte string a intero con controllo su null
	 * @param str
	 * @return
	 */
	public static long parseLong( Object obj ){
		long retVal =0;
		if( !strVoid( obj ) ){
			try{
				retVal = Long.parseLong( obj.toString().trim() );
			}catch( NumberFormatException e ){
			}
		}
		return retVal;
	}

	/**
	 * converte string a float con controllo su null
	 * @param str
	 * @return
	 */
	public static float parseFloat( Object obj ){
		float retVal =0;
		if( !strVoid( obj ) ){
			try{
				retVal = Float.parseFloat( obj.toString().trim() );
			}catch( NumberFormatException e ){
			}
		}
		return retVal;
	}

	/**
	 * converte string a float con controllo su null
	 * @param str
	 * @return
	 */
	public static double parseDouble( Object obj ){
		double retVal =0;
		if( !strVoid( obj ) ){
			try{
				retVal = Double.parseDouble( obj.toString().trim() );
			}catch( NumberFormatException e ){
			}
		}
		return retVal;
	}

	/**
	 * Inserisce un campo input nascosto prendendolo dalla request se non e' null
	 * @param request
	 * @param AttrName
	 * @return
	 */
	public static String inserInputFromRequestAttrIfNotNull( HttpServletRequest request, 
			String AttrName ){
		String attrVal = "";
		if (request.getAttribute(AttrName )!=null){
			attrVal = (String) request.getAttribute(AttrName );
		}
		String linea = "";
		if (request.getAttribute( AttrName )!=null){
			linea = "<INPUT type=\"hidden\" name=\""+ AttrName + "\" "+ "id=\""+ AttrName + "\""+
					" value=\""+ attrVal + "\">"; 
		}
		return linea; 
	}

	/**
	 * Inserisce immagine link per icone videate
	 * @param imgId
	 * @param icoUrlNoExt
	 * @param icoExt
	 * @param href
	 * @param target
	 * @param title
	 * @param w
	 * @param h
	 * @param border
	 * @return
	 */
	public static String insertImageLinkSimple( String imgId, String icoUrlNoExt, String icoExt, String href, String target, String title, String w, String h, String border ){
		String anchor = "<A href=\"" + href+"\" ";
		if(!strVoid( target ) ){
			anchor += " target=\"" + FrmStringa.replaceDQuote4html( target ) + "\" ";
		}
		anchor += " >\n" +
				"<IMG name=\""+imgId+"\" id=\""+imgId+"\" src=\""+ icoUrlNoExt + icoExt + "\" width=\""+w+"\" height=\""+h+"\" border=\""+ border +"\" title=\""+title+"\"></A>";
		return anchor;
	}


	/**
	 * Inserisce immagine link per icone videate
	 * @param imgId
	 * @param icoUrlNoExt
	 * @param icoExt
	 * @param href
	 * @param target
	 * @param title
	 * @param w
	 * @param h
	 * @param border
	 * @return
	 */
	public static String insertImageLink( String imgId, String icoUrlNoExt, String icoExt, String href, String target, String title, String w, String h, String border ){
		String anchor = "<A onmouseover=\"imageChange('"+imgId+"','" +icoUrlNoExt + "UP" + icoExt + "');\"\n" +
				"onmouseout=\"imageChange('"+imgId+"','"+ icoUrlNoExt + icoExt + "');\"\n" +
				"onfocus=\"imageChange('"+imgId+"','"+ icoUrlNoExt + "UP"+ icoExt + "');\"\n" +
				"onblur=\"imageChange('"+imgId+"','"+ icoUrlNoExt + icoExt + "');\"\n" +
				"href=\"" + href+"\" ";
		if(!strVoid( target ) ){
			anchor += " target=\"" + FrmStringa.replaceDQuote4html( target ) + "\" ";
		}
		anchor += " >\n" +
				"<IMG name=\""+imgId+"\" id=\""+imgId+"\" src=\""+ icoUrlNoExt + icoExt + "\" width=\""+w+"\" height=\""+h+"\" border=\""+ border +"\" title=\""+title+"\"></A>";
		return anchor;
	}

	/**
	 * Inserisce immagine senza link per icone videate
	 * @param imgId
	 * @param icoUrlNoExt
	 * @param icoExt
	 * @param title
	 * @param w
	 * @param h
	 * @param border
	 * @return
	 */
	public static String insertImageNoLink( String imgId, String icoUrlNoExt, String icoExt, String title, String w, String h, String border ){
		String anchor = 
				"<IMG name=\""+imgId+"\" id=\""+imgId+"\" src=\""+ icoUrlNoExt + icoExt + "\" width=\""+w+"\" height=\""+h+"\" border=\""+ border +"\" title=\""+title+"\">";
		return anchor;
	}


	/**
	 * inserisce immagine link  con bordo
	 * @param imgId
	 * @param icoUrl
	 * @param href
	 * @param title
	 * @param w
	 * @param h
	 * @param border
	 * @return
	 */
	public static String insertImageBorder( String imgId, String icoUrl, String href, String title, String w, String h, String border ){
		String img = "<A href=\""+href+"\">\n" +
				"	<IMG name=\""+ imgId +"\" src=\""+ icoUrl + "\" width=\"" + w + "\" height=\"" + w + "\" border=\""+ border + "\" title=\""+ title +"\">" +
				"</a>\n";
		return img;
	}

	/**
	 * codifica ulteriore url, per passaggio come parametro della querystring
	 * @param hrefRitorno
	 * @return
	 */
	public static String hrefRitornoEncode( String hrefRitorno ){
		String href="";
		if( !strVoid( hrefRitorno) ){
			//precodifica per riuscire a individuare ?,&, = in un secondo momento
			href = hrefRitorno.replaceAll( "_", "__");
			href = href.replaceAll( "\\?", "_?");
			href = href.replaceAll( "\\&", "_&");
			href = href.replaceAll( "\\=", "_=");

			href = urlEncodeISO_8859_15( href );
		}
		return href;
	}


	public static String urlEncodeISO_8859_15( Object href ){
		String retVal = "";
		if( href!=null ){
			retVal = urlEncodeISO_8859_15( href.toString() );
		}
		return retVal;
	}

	public static String urlEncodeISO_8859_15( String href ){
		try{
			href = URLEncoder.encode( href, "ISO-8859-15" );
			//            href = URLEncoder.encode( href, "UTF-8" );
		}catch( UnsupportedEncodingException  e ){

		}
		return href;
	}

	public static String urlEncodeUTF_8( String href ){
		try{
			href = URLEncoder.encode( href, "UTF-8" );
		}catch( UnsupportedEncodingException  e ){

		}
		return href;
	}

	public static String urlEncodeSpecialChars( String href ){
		href=href.replace("&","[_]ecom[_]");
		href=href.replace("+","[_]plus[_]");
		href=href.replace("%","[_]perc[_]");
		return href;
	}

	public static String urlDecodeSpecialChars( String href ){
		href=href.replace("[_]ecom[_]","&");
		href=href.replace("[_]plus[_]","+");
		href=href.replace("[_]perc[_]","%");		
		return href;
	}

	/**
	 * Decodifica ulteriore per passaggio url come parametro querystring
	 * @param hrefRitorno
	 * @return
	 */
	public static String hrefRitornoDecode( String hrefRitorno ){
		String href="";
		if( !strVoid( hrefRitorno) ){
			//precodifica per riuscire a individuare ?,&, = in un secondo momento
			try{
				href = URLDecoder.decode( hrefRitorno, "ISO-8859-15" );
				//                href = URLDecoder.decode( hrefRitorno, "UTF-8" );
			}catch( UnsupportedEncodingException  e ){
			}
			href = href.replaceAll( "_\\?", "?");
			href = href.replaceAll( "_\\&", "&");
			href = href.replaceAll( "_\\=", "=");
			href = href.replaceAll( "__", "_");
		}
		return href;		
	}


	/**
	 * inoltra ad una jsp con un nuovo lablelXML. Non usare con url servlet
	 * @param srvContext
	 * @param request
	 * @param response
	 * @param url
	 * @param lingua
	 * @throws IOException
	 * @throws ServletException
	 */
	/* ATTUALMENTE NON UTILIZZATA (VERIFICARE LABEL XML)
	public static void dispatchJspUrl( ServletContext srvContext, 
										HttpServletRequest request, 
										HttpServletResponse response, 
										String url, 
										String lingua,
										String codSocieta ) throws IOException, ServletException{
		String urlSplit[] = url.split( "\\.jsp" );

		if( urlSplit.length > 0 ){//no ho jsp!? non fo la label
			//prendo quello prima (0) del primo .jsp
//			String pathSplit[] = urlSplit[0].split( "/" );
			//prendo l'ultimo (length-1) che dovrebbe essere il nome
//			String name = pathSplit[ pathSplit.length-1 ];

    		LabelXML labelXML = LabelXML.creaOggettoJsp( url.substring(0,url.lastIndexOf("/")+1), lingua, codSocieta, url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".jsp")) );	
	        request.setAttribute(DatiGlobaliCommon.labelXMLStr, labelXML);
		}

        RequestDispatcher reqDispatcher = srvContext.getRequestDispatcher(url);
        reqDispatcher.forward(request, response);
	}
	 */

	/**
	 * allinea mastro gruppo conto sottoconto in formato 2 2 2 6
	 * @param mastro
	 * @param gruppo
	 * @param conto
	 * @param sottoconto
	 * @return
	 */
	static public String allineaMastroGruppoContoSottoconto2226( String mastro, String gruppo, String conto, String sottoconto ){
		String retVal;

		mastro = FrmStringa.toUpperNotNull(mastro);
		gruppo = FrmStringa.toUpperNotNull(gruppo);
		conto = FrmStringa.toUpperNotNull(conto);
		sottoconto = FrmStringa.toUpperNotNull(sottoconto);

		retVal  = FrmStringa.allineaDx(mastro, 2, false );
		retVal += FrmStringa.allineaDx(gruppo, 2, false );
		retVal += FrmStringa.allineaDx(conto, 2, false );
		retVal += FrmStringa.allineaDx(sottoconto, 6, false );

		return retVal;
	}

	/**
	 * allinea i codici per voucher/pratica
	 * @param txtIdRifT
	 * @param txtIdRifA
	 * @param txtIdRifN
	 * @return
	 */
	public static String allineaCodice346( String txtIdRifT, String txtIdRifA, String txtIdRifN ){
		txtIdRifT = FrmStringa.trimNotNull( txtIdRifT );
		txtIdRifA = FrmStringa.trimNotNull( txtIdRifA );
		txtIdRifN = FrmStringa.trimNotNull( txtIdRifN );

		String txtIdRif = FrmStringa.allineaDx( txtIdRifT, 3, true ) + " "
				+ FrmStringa.allineaDx( txtIdRifA, 4, true ) + " " 
				+ FrmStringa.allineaDx( txtIdRifN, 6, true );
		return txtIdRif;
	}

	/**
	 * allinea i codici per voucher/pratica
	 * @param txtIdRifT
	 * @param txtIdRifA
	 * @param txtIdRifN
	 * @return
	 */
	public static String allineaCodice345( String txtIdRifT, int txtIdRifA, int txtIdRifN ){
		txtIdRifT = FrmStringa.trimNotNull( txtIdRifT );
		
		String txtIdRif = FrmStringa.allineaSx( txtIdRifT, 3, false ) +
						  FrmStringa.allineaDx(String.valueOf(txtIdRifA), 4, false) + 
						  FrmStringa.allineaDx(String.valueOf(txtIdRifN), 5, false);
		return txtIdRif;
	}

	/**
	 * allinea i codici per voucher/pratica
	 * @param txtIdRifT
	 * @param txtIdRifA
	 * @param txtIdRifN
	 * @return
	 */
	public static String allineaCodice325( String txtIdRifT, int txtIdRifA, int txtIdRifN ){
		txtIdRifT = FrmStringa.trimNotNull( txtIdRifT );
		
		String txtIdRif = FrmStringa.allineaSx( txtIdRifT, 3, false ) +
						  FrmStringa.subStrByLen(txtIdRifA, 2, 4) +
						  FrmStringa.allineaDxFilling(String.valueOf(txtIdRifN), "0", 5);
		return txtIdRif;
	}
	
	/**
	 * scompone il riferimento in:
	 * [0] - tipo
	 * [1] - anno
	 * [2] - numero
	 * @param txtRiferimento
	 * @return
	 */
	public static String[] retrieveCodice346( String txtRiferimento ){
		String[] array = new String[3];

		array[0] = FrmStringa.subStrByLen(txtRiferimento, 0, 3);
		array[1] = FrmStringa.subStrByLen(txtRiferimento, 4, 8);
		array[2] = FrmStringa.subStrByLen(txtRiferimento, 9, 15);

		return array;		
	}

	/**
	 * scompone il riferimento in:
	 * [0] - tipo
	 * [1] - anno
	 * [2] - numero
	 * @param txtRiferimento
	 * @return
	 */
	public static String[] retrieveCodice345( String txtRiferimento ){
		String[] array = new String[3];
		
		array[0] = FrmStringa.subStrByLen(txtRiferimento, 0, 3);
		array[1] = FrmStringa.subStrByLen(txtRiferimento, 3, 7);
		array[2] = FrmStringa.subStrByLen(txtRiferimento, 7, 12);
		
		return array;		
	}
	
	/**
	 * allinea i codici per biglietti
	 * @param txtIdRifC
	 * @param txtIdRifS
	 * @param txtIdRifN
	 * @param txtUnitaOp
	 * @return
	 */
	public static String allineaCodice_5_2_10_3( String txtIdRifC, String txtIdRifS, String txtIdRifN, String txtUnitaOp ){
		txtIdRifC = FrmStringa.trimNotNull( txtIdRifC );
		txtIdRifS = FrmStringa.trimNotNull( txtIdRifS );
		txtIdRifN = FrmStringa.trimNotNull( txtIdRifN );
		txtUnitaOp = FrmStringa.trimNotNull( txtUnitaOp );

		String txtIdRif = FrmStringa.allineaDx( txtIdRifC, 5, false ) + " "
				+ FrmStringa.allineaDx( txtIdRifS, 2, false ) + " " 
				+ FrmStringa.allineaDx( txtIdRifN, 10, false ) + " "
				+ FrmStringa.allineaDx( txtUnitaOp, 3, false );
		return txtIdRif;
	}	

	/**
	 * genera il tag input per l'inserimento di campo mastro da conto parametrizzto 
	 * @param name
	 * @param conParamVald
	 * @param disabled
	 * @return
	 * Aggiunta gestione validazione
	 * @param Colore validazione
	 * @param Title validazione
	 * @author Alessandro Servetti
	 * @version 1.0.1 09/dic/08
	 */
	static public String campoMasParametrizzato( String name, String conParamVald, boolean disabled, String colorVal, String titleVal){
		String retVal = null;
		retVal = "<INPUT type='text' name='"+name+"' id='"+name+"' value='"+conParamVald+"' size='1' maxlength='2' align='right' " ;
		if(colorVal!=null){
			retVal += "style='background-color: " + colorVal + " ;' "; 
			retVal += "title='" + titleVal + " '";
		}else{
			if( disabled ){
				retVal += " disabled ";
			}else{
				retVal += "readonly style='background-color: #E6E6E6;'" ;
			}
		}

		retVal += ">\n";

		return retVal;
	}

	/**
	 * genera il tag INPUT per conto e gruppo parametrizzati
	 * @param name
	 * @param attribVal
	 * @param conParamVal
	 * @param descField
	 * @param disabled
	 * @return
	 * Aggiunta gestione validazione
	 * @param Colore validazione
	 * @param Title validazione
	 * @author Alessandro Servetti
	 * @version 1.0.1 09/dic/08
	 */
	static public String campoConParametrizzato( String name, Object attribVal, String conParamVal, String descField, boolean disabled,String prColorVal,String prTitleVal){
		String retVal = null;
		retVal = "<INPUT type='text' name='"+name+"' id='"+name+"' \n";

		if ( strVoid(attribVal ) ) {
			retVal += "value='"+ conParamVal + "'\n";  
		} else {
			retVal += "value='" + attribVal +"'\n"; 
		} 

		retVal +="  size='1' maxlength='2' align='right' \n";

		if(prColorVal!=null){
			retVal += "style='background-color: " + prColorVal + " ;' "; 
			retVal += "title='" + prTitleVal + " '";
		}else{
			if( disabled ){
				retVal += " disabled ";
			}else{
				if(!strVoid( conParamVal ) ) { 
					retVal += "readonly style='background-color: #E6E6E6;' \n";
				}
			}
		}

		retVal += "onChange=\"ripulisciCod_and_Desc('"+name+"','"+descField+"');\"> \n";

		return retVal;
	}

	/**
	 * genera HTML per l'input di un sottoconto
	 * @param name
	 * @param attribVal
	 * @param descField
	 * @param disabled
	 * @return
	 * Aggiunta gestione validazione
	 * @param Colore validazione
	 * @param Title validazione
	 * @author Alessandro Servetti
	 * @version 1.0.1 09/dic/08
	 */
	static public String campoSotconParametrizzato( String name, Object attribVal, String conParamVal, String descField, boolean disabled,String prColorVal,String prTitleVal){
		String retVal = null;
		retVal = "<INPUT type='text' name='"+name+"' id='"+name+"' align='right'\n";
		if (FrmStringa.strVoid( attribVal ) ) { 
			retVal += "value=\"\"\n";
		} else { 
			retVal += "value=\""+attribVal+"\"\n"; 
		}
		if(prColorVal!=null){
			retVal += "style='background-color: " + prColorVal + " ;' "; 
			retVal += "title='" + prTitleVal + " '";
		}else{
			if( disabled ){
				retVal += " disabled ";
			}else{
				if(!strVoid( conParamVal ) ) { 
					retVal += "readonly style='background-color: #E6E6E6;' \n";
				}
			}
		}

		retVal += "size='5' maxlength='6' align='right' onChange=\"document.forms[0]."+descField+".value='';gestChangeFornitore();\">\n";

		return retVal;
	}

	public static String doubleQuoted4js( String in ){
		String retVal = "\"";
		retVal += replaceDQuote4js( in );
		retVal += "\"";
		return retVal;
	}

	public static String doubleQuoted4html( String in ){
		String retVal = "\"";
		retVal += replaceDQuote4html( in );
		retVal += "\"";
		return retVal;
	}

	public static String replaceDQuote4html( String in ){
		String retVal ="";
		if( in!= null ){
			retVal += in.replaceAll("\"", "&quot;");
		}
		return retVal;
	}

	public static String replaceDQuote4html( Object in ){
		String retVal ="";
		if( in!= null ){
			retVal = replaceDQuote4html( in.toString());
		}
		return retVal;

	}

	public static String replaceDQuote4js( String in ){
		String retVal ="";
		if( in!= null ){
			retVal += in.replaceAll("\"", "\\\\\"");
		}
		return retVal;

	}

	public static String replaceBackslashWithNothing( String in ){
		String retVal = "";
		if( in != null ){
			retVal += in.replaceAll("\\\\", "");
		}
		return retVal;
	}

	/**
	 * sostituisce tutti gli apici doppi, con apici singoli <br>
	 * " -> '
	 * @author Marco Damilano - 31/ago/2010
	 * */
	public static String replaceDQuoteSQuote( String in ){
		String retVal ="";
		if( in!= null ){
			retVal += in.replaceAll("\"", "'");
		}
		return retVal;

	}

	public static String replaceDQuote4js( Object in ){
		String retVal ="";
		if( in!= null ){
			retVal = in.toString().replaceAll("\"", "\\\\\"" );
		}
		return retVal;

	}

	public static String singleQuoted4js( String in ){
		String retVal = "'";
		retVal += replaceSQuote4js( in );
		retVal += "'";
		return retVal;
	}

	public static String replaceSQuote4js( String in ){
		String retVal ="";
		if( in!= null ){
			retVal += in.replaceAll("'", "\\\\'");
		}
		return retVal;
	}

	public static String replaceSQuote4html( String in ){
		String retVal ="";
		if( in!= null ){
			// &apos; (does not work in IE)
			//			retVal += in.replaceAll("'", "&apos;");
			retVal += in.replaceAll("'", "&#39;");
		}
		return retVal;
	}

	public static String replaceSQuote4sql( String in ){
		String retVal ="";
		if( in!= null && !in.equals("")){
			retVal += in.replaceAll("'", "''");
		}
		return retVal;
	}

	/**
	 * Restituisce n spazi HTML
	 * @param Int
	 * @return n spazi
	 * @author Servetti Alessandro 
	 * @version 1.0.0 24-lug-2007
	 */
	public static String makeSpace(int prNumberSpace) {
		String tmpStringa = "";

		for(int k=1;k<=prNumberSpace;k++){
			tmpStringa = tmpStringa + "&nbsp;";
		}

		return tmpStringa;
	}

	/**
	 * Restituisce n trattini HTML
	 * @param Int
	 * @return n trattini
	 * @author Servetti Alessandro 
	 * @version 1.0.0 19/set/07
	 */
	public static String makeTrattini(int prNumberTrattini) {
		String tmpStringa = "";

		for(int k=1;k<=prNumberTrattini;k++){
			tmpStringa = tmpStringa + " - ";
		}

		return tmpStringa;
	}

	/**
	 * Crea una stringa di valori separati da virgole (CommaSeparatedValue) a partire da String[]
	 * @param vals
	 * @return
	 */
	public static String csvDaStringArray( String[] vals ){
		return stringaDelimitataDaArray(vals, ",");
	}

	/**
	 * Crea una stringa di valori separati da virgole (CommaSeparatedValue) a partire da String[]
	 * @param vals
	 * @return
	 */
	public static String csvDaStringArray( Object[] vals ){
		return stringaDelimitataDaArray(vals, ",");
	}

	/**
	 * Crea una stringa di valori separati da delimitatore a partire da String[]
	 * @param vals
	 * @return
	 */
	public static String stringaDelimitataDaArray( String[] vals, String  delimitatore ){
		return stringaDelimitataDaArray( vals, delimitatore, "" );
	}

	/**
	 * Crea una stringa di valori separati da delimitatore a partire da String[]
	 * @param vals
	 * @return
	 */
	public static String stringaDelimitataDaArray( Object[] vals, String  delimitatore ){
		return stringaDelimitataDaArray( vals, delimitatore, "" );
	}

	/**
	 * Crea una stringa di valori separati da delimitatore a partire da String[]
	 * @param vals
	 * @return
	 */
	public static String stringaDelimitataDaArray( String[] vals, String  delimitatore, String delimtext ){
		String csv = "";
		if( vals!= null ){
			for( int j=0; j< vals.length; j++ ){
				if(j>0){ // sul primo elemento non metto il delimitatore
					csv += delimitatore;
				}
				csv += delimtext+ vals[j] + delimtext;
			}
		}
		return csv;
	}

	/**
	 * Crea una stringa di valori separati da delimitatore a partire da String[]
	 * @param vals
	 * @return
	 */
	public static String stringaDelimitataDaArray( Object[] vals, String  delimitatore, String delimtext ){
		String csv = "";
		if( vals!= null ){
			for( int j=0; j< vals.length; j++ ){
				if( !FrmStringa.strVoid( csv ) ){
					csv += delimitatore;
				}
				csv += delimtext+ vals[j].toString() + delimtext;
			}
		}
		return csv;
	}

	/**
	 * Estrae da una generica stringa il contenuto del nodo delimitato dal tag di apertura-chiusura. 
	 * @param parStr: stringa sulla quale effettuare la ricerca
	 * @param parTagName: nome del tag da ricercare
	 * @return value of the tag element if the tag exist; "" otherwise.
	 * @author Marco Rosa - 24-lug-2007
	 */
	public static String getTagValue (String parStr, String parTagName){
		final String PRE_TAG_OPEN = "<";
		final String PRE_TAG_CLOSE = "</";
		final String SUF_TAG = ">";

		int indTagOpen = 0;
		int indTagClose = 0;
		String ret = "";
		String tagApertura = PRE_TAG_OPEN + parTagName + SUF_TAG;
		String tagChiusura = PRE_TAG_CLOSE + parTagName + SUF_TAG;

		if (parStr == null)
			parStr = "";

		indTagOpen = parStr.indexOf(tagApertura);
		if (indTagOpen != -1){
			indTagOpen += tagApertura.length();
			indTagClose = parStr.indexOf(tagChiusura);
			if (indTagClose != -1 && indTagClose > indTagOpen){
				ret = FrmStringa.subStrByLen(parStr,indTagOpen,indTagClose);
			}
		}

		return ret;
	}	

	/**
	 * Converte un vettore di {@link javax.mail.Address} in un oggetto {@link Vector}
	 * @param parVAddress - array di {@link javax.mail.Address}
	 * @return omologo oggetto di tipo Vector
	 * @author Rosa Marco - 03/apr/2010
	 */
	public static Vector<String> convertToVector (Address[] parVAddress){
		Vector<String> ret = new Vector<String>(0,1);

		if (parVAddress != null){
			int lenVet = parVAddress.length;
			for (int indice = 0; indice < lenVet; indice++){
				ret.addElement((parVAddress[indice])!=null?parVAddress[indice].toString():""); 
			}
		}

		return ret;		
	}

	/**
	 * Converte un generico vettore di String in un oggetto Vector
	 * @param parVString
	 * @return omologo oggetto di tipo Vector
	 * @author Marco Rosa - 10/gen/08
	 */
	public static Vector<String> convertVStringToVector (String[] parVString){
		Vector<String> ret = new Vector<String>(0,1);

		if (parVString != null){
			int lenVet = parVString.length;
			for (int indice = 0; indice < lenVet; indice++){
				ret.addElement((parVString[indice])!=null?parVString[indice].trim():null); 
			}
		}

		return ret;		
	}

	/**
	 * Controlla se la striga è 0 ed eventualmente la sostituisce con uno spazio.
	 * @param Stringa
	 * @param Separatore
	 * @return Stringa
	 * @author Servetti Alessandro - 20/apr/11
	 */
	public static String replaceZeroWithSpace(String prRigaTmp,String prSeparatore){
		String result = null;
		if(prSeparatore!=null && prRigaTmp.indexOf(prSeparatore)!=-1){
			String[] array = prRigaTmp.split(prSeparatore);
			for(int i=0;i<array.length;i++){
				if(FrmNumero.isNumeric(array[i]) && FrmNumero.formatNumber(array[i])==0){
					array[i] = " ";
				}
			}
			result = getStringFromArray(array,prSeparatore,true);
		}else{
			if(FrmNumero.isNumeric(prRigaTmp) && FrmNumero.formatNumber(prRigaTmp)==0){
				result = "";
			}else{
				result = prRigaTmp;
			}
		}
		return result;
	}

	/**
	 * Restituisce una stringa con i singoli valori dell'array passato a parametro opportunamente separati.
	 * @param String[] array
	 * @param Separatore
	 * @param Aggiungi terminatore
	 * @return Stringa
	 * @author Servetti Alessandro - 20/apr/11
	 */
	public static String getStringFromArray(String[] prArray,String prSeparatore,boolean prAddTerminatore){
		String result = "";
		if(prArray!=null && !(prArray.length == 1 && FrmStringa.strVoid(prArray[0]))){
			for(int i=0;i<prArray.length;i++){
				result += prArray[i] + prSeparatore;
			}
			if(prAddTerminatore){
				result += "*";
			}
		}
		return result;
	}

	public static StringBuffer mapsLongToString( String prefix, Map map1, Map map2, boolean html, boolean mean2on1, boolean withTotal ){
		Iterator it = map1.keySet().iterator();
		long total1 = 0;
		long total2 = 0;
		long addendum1=0;
		long addendum2=0;
		StringBuffer mapStr = new StringBuffer( "" );
		while (it.hasNext()) {
			Object key = it.next();
			if( key != null ){
				if( !FrmStringa.strVoid(prefix)){
					mapStr.append(prefix+":");
				}
				mapStr.append( key.toString() );
				Long lval = (Long) map1.get(key);
				addendum1 = lval.longValue();
				lval = (Long) map2.get(key);
				addendum2 = lval.longValue();
				mapStr.append( ":"+addendum1+":"+addendum2 );
				if( mean2on1 && addendum1 != 0 ){
					mapStr.append( ":" +(addendum2/addendum1));
				}
				if( html ){
					mapStr.append("<br>");
				}
				mapStr.append("\n");
				total1 += addendum1;
				total2 += addendum2;
			}
		}
		if( withTotal){
			mapStr.append( "Total:"+total1+":"+total2 );
			if( mean2on1 && total1 != 0 ){
				mapStr.append( ":"+ (total2/total1) );
			}
		}
		return mapStr;
	}

	public static StringBuffer mapsPercLongToString( Map map1, Map map2, boolean html ){
		Iterator it = map1.keySet().iterator();
		long total1 = 0;
		long total2 = 0;
		long addendum1=0;
		long addendum2=0;
		//total computation
		while (it.hasNext()) {
			Object key = it.next();
			if( key != null ){
				Long lval = (Long) map1.get(key);
				addendum1 = lval.longValue();
				lval = (Long) map2.get(key);
				addendum2 = lval.longValue();
				total1 += addendum1;
				total2 += addendum2;
			}
		}
		StringBuffer mapStr = new StringBuffer( "" );
		it = map1.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			if( key != null ){
				mapStr.append( key.toString() );
				Long lval = (Long) map1.get(key);
				addendum1 = lval.longValue();
				lval = (Long) map2.get(key);
				addendum2 = lval.longValue();
				mapStr.append( ":"+addendum1+":"+addendum2+":" );
				if( addendum1>0 ){
					mapStr.append( String.valueOf( addendum2/addendum1 ) );
				}
				mapStr.append( ":"+ Math.round(addendum1*1000.0/total1)/10.0 +"%:"+Math.round(addendum2*1000.0/total2)/10.0+"%" );
				if( html ){
					mapStr.append("<br>");
				}
				mapStr.append("\n");
			}
		}
		mapStr.append( "Total:"+total1+":"+total2 );
		if( total1 > 0 ){
			mapStr.append( ":"+total2/total1 );
		}
		return mapStr;
	}

	public static StringBuffer mapToString( Map map, boolean html ){
		Iterator it = map.keySet().iterator();
		StringBuffer mapStr = new StringBuffer( "" );
		while (it.hasNext()) {
			Object key = it.next();
			if( key != null ){
				mapStr.append( key.toString() );
				String strval = "";
				if (map.get(key)!=null){
					strval = map.get(key).toString();
				} else {
					strval ="null";
				}
				mapStr.append( "::"+strval );
				if( html ){
					mapStr.append("<br>");
				}
				mapStr.append("\n");
			}
		}
		return mapStr;
	}

	public static Map sortMapByVal(Map hmap ){
		HashMap map = new LinkedHashMap();
		List mapKeys = new ArrayList(hmap.keySet());
		List mapValues = new ArrayList(hmap.values());
		//hmap.clear();
		TreeSet sortedSet = new TreeSet(mapValues);
		Object[] sortedArray = sortedSet.toArray();
		int size = sortedArray.length;
		// Descending sort
		for (int i=size-1; i>=0; i--)
		{

			map.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), sortedArray[i]);

		}
		return map;
	}

	public static Map sortMapAsSorted( Map sorted, Map toBeSorted ){
		HashMap new_map = new LinkedHashMap();
		Iterator it = sorted.keySet().iterator();
		Object val;
		while (it.hasNext()) {
			Object key = it.next();
			val = toBeSorted.get(key);
			new_map.put(key, val);
		}
		return new_map;
	}

	public static String listToString( LinkedList l, boolean html ){
		StringBuffer retVal = new StringBuffer("");
		Iterator it = l.iterator();
		while( it.hasNext()){
			Object obj = it.next();
			retVal.append( obj.toString() );
			if( html ){
				retVal.append( "<br>" );
			}
			retVal.append( "\n" );
		}
		return retVal.toString();

	}

	public static boolean findRegExp( String str, String patterns ){
		if( patterns!= null && str != null ){
			String[] stra = {patterns};
			return findRegExpV(str, stra);
		} else {
			return false;
		}
	}


	public static boolean findRegExpV( String str, Vector<String> patterns ){
		if( patterns!= null ){
			return findRegExpV(str, patterns.toArray());
		} else {
			return false;
		}
	}

	public static boolean findRegExpV( String str, Object[] patterns ){
		boolean retVal = false;
		if( patterns!= null ){
			for( int i=0; i< patterns.length && !retVal; i++ ){
				if( patterns[i]!= null ){
					Pattern pat =  Pattern.compile(patterns[i].toString());
					Matcher matcher = pat.matcher(str);
					if( matcher!=null){
						retVal = matcher.find();
					}
				}
			}
		}
		return retVal;
	}

	public static String getFirstRegExpOccurrence( String str, String pattern  ){
		String retVal = null;
		if( pattern!= null ){
			Pattern pat =  Pattern.compile( pattern );
			Matcher matcher = pat.matcher(str);
			if( matcher!=null){
				if( matcher.find() ){
					MatchResult res= matcher.toMatchResult();
					retVal = res.group();
				}
			}
		}
		return retVal;
	}

	/**
	 * gets the substring matched by the parentesys, like PERL $1-$9 (grouping)
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static String[] getRegExpGroup( String str, String pattern  ){
		String[] retVal = null;
		if( pattern!= null ){
			Pattern pat =  Pattern.compile( pattern );
			retVal = getRegExpGroup( str, pat );
		}
		return retVal;
	}
	public static String[] getRegExpGroup( String str, Pattern pat ){
		String[] retVal = null;
		if( pat != null ){
			Matcher matcher = pat.matcher(str);
			if( matcher!=null){
				if( matcher.find() ){
					MatchResult res= matcher.toMatchResult();
					/*see help of getGroup:
					Returns the input subsequence captured by the given group during the previous match operation.

					For a matcher m, input sequence s, and group index g, the expressions m.group(g) and s.substring(m.start(g), m.end(g)) are equivalent.

					Capturing groups are indexed from left to right, starting at one. Group zero denotes the entire pattern, so the expression m.group(0) is equivalent to m.group().

					If the match was successful but the group specified failed to match any part of the input sequence, then null is returned. Note that some groups, for example (a*), match the empty string. This method will return the empty string when such a group successfully matches the empty string in the input.
					Specified by: group(...) in MatchResult
					 */
					/* int java.util.regex.MatchResult.groupCount()
						Returns the number of capturing groups in this match result's pattern.
						Group zero denotes the entire pattern by convention. It is not included in this count.
					 */
					int numGroup = res.groupCount()+1;//group 0 is the whole expression, 1 is the first parentesys, 2 the 2nd, ... 
					retVal = new String[numGroup];
					for( int i=0; i<numGroup; i++){
						//we return also the whole matched pattern 
						retVal[i]= matcher.group(i);
					}

				}
			}
		}
		return retVal;
	}

	public static HashMap<String,String> explodeDataStoreUrl( String dataStoreUrl ){
		HashMap<String,String> retVal = new HashMap<String,String>();
		if( !FrmStringa.strVoid(dataStoreUrl) ){
			String[] dsplit = dataStoreUrl.split(":");
			if( dsplit != null ){
				if( dsplit.length > 1 ){
					String[] dsplitsplit = dsplit[1].split(";");
					if( dsplitsplit != null ){
						if( dsplitsplit.length> 3 ){
							retVal.put( "srv", dsplitsplit[0] );
							retVal.put( "usr", dsplitsplit[1] );
							retVal.put( "pwd", dsplitsplit[2] );
							retVal.put( "soc", dsplitsplit[3] );
							if( dsplitsplit.length > 4 ){
								retVal.put( "tab", dsplitsplit[4] );
							}
						}
					}
				}
			}
		}
		return retVal;
	}

	public static class DesEncrypter {
		Cipher ecipher;
		Cipher dcipher;

		// 8-byte Salt
		byte[] salt = {
				(byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
				(byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
		};

		// Iteration count
		int iterationCount = 19;

		DesEncrypter(String passPhrase) {
			try {
				// Create the key
				KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
				SecretKey key = SecretKeyFactory.getInstance(
						"PBEWithMD5AndDES").generateSecret(keySpec);
				ecipher = Cipher.getInstance(key.getAlgorithm());
				dcipher = Cipher.getInstance(key.getAlgorithm());

				// Prepare the parameter to the ciphers
				AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

				// Create the ciphers
				ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
				dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			} catch (java.security.InvalidAlgorithmParameterException e) {
			} catch (java.security.spec.InvalidKeySpecException e) {
			} catch (javax.crypto.NoSuchPaddingException e) {
			} catch (java.security.NoSuchAlgorithmException e) {
			} catch (java.security.InvalidKeyException e) {
			}
		}

		public String encrypt(String str) {
			try {
				// Encode the string into bytes using utf-8
				byte[] utf8 = str.getBytes("UTF8");

				// Encrypt
				byte[] enc = ecipher.doFinal(utf8);

				// Encode bytes to base64 to get a string
				return new sun.misc.BASE64Encoder().encode(enc);
			} catch (javax.crypto.BadPaddingException e) {
			} catch (IllegalBlockSizeException e) {
			} catch (UnsupportedEncodingException e) {
			} 
			return null;
		}

		public String decrypt(String str) {
			try {
				// Decode base64 to get bytes
				byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

				// Decrypt
				byte[] utf8 = dcipher.doFinal(dec);

				// Decode using utf-8
				return new String(utf8, "UTF8");
			} catch (javax.crypto.BadPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private static HashMap<String, DesEncrypter> encrypterMap = new HashMap<String, DesEncrypter>(); 

	public static String cifraStringa( String daCifrare, String passPhrase ){
		String retVal =daCifrare;

		// Here is an example that uses the class
		try {

			// Create encrypter/decrypter class
			// Rimosso per probabile errore random
//			DesEncrypter encrypter = encrypterMap.get(passPhrase);
//			if( encrypter== null ){
//				encrypter = new DesEncrypter(passPhrase);
//				encrypterMap.put(passPhrase, encrypter);
//			}
			
			// Aggiunto per ovviare probabile errore random
			DesEncrypter encrypter = new DesEncrypter(passPhrase);
			
			// Encrypt
			retVal = encrypter.encrypt(daCifrare);

			// Decrypt
		} catch (Exception e) {
		}

		return retVal;
	}

	public static String decifraStringa( String daDecifrare, String passPhrase ){
		String retVal =daDecifrare;

		// Here is an example that uses the class
		try {
			// Create encrypter/decrypter class
			// Rimosso per probabile errore random 
//			DesEncrypter encrypter = encrypterMap.get(passPhrase);
//			if( encrypter== null ){
//				encrypter = new DesEncrypter(passPhrase);
//				encrypterMap.put(passPhrase, encrypter);
//			}

			// Aggiunto per ovviare probabile errore random
			DesEncrypter encrypter = new DesEncrypter(passPhrase);
			
			// was encrypted this way:
			//retVal = encrypter.encrypt("Don't tell anybody!");

			// Decrypt
			retVal = encrypter.decrypt(retVal);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retVal;
	}

	////////////////end bart

	////////////////begin marcoD

	/**
	 * stabilisce se un elenco di caratteri sono presenti o no in una stringa
	 * @param str - stringa da valutare
	 * @param elencoChar - stringa contentente l'elenco di caratteri da valutare
	 * @author Marco Damilano - 27/giu/08
	 * */
	public static boolean containSetsOfChars(String str,String elencoChar){
		boolean trovato=false;
		// eventuale sostituzione caratteri speciali '[' e ']'
		String elencoCharNormalizzato=elencoChar.replaceAll("[/\\[]", "\\\\[");
		elencoCharNormalizzato=elencoCharNormalizzato.replaceAll("[/\\]]", "\\\\]");

		// eventuale sostituzione caratteri speciali '[' e ']'
		String strNormalizzata=str.replaceAll("[/\\]]", "\\\\]");
		strNormalizzata=strNormalizzata.replaceAll("[/\\[]", "\\\\[");

		String patternStr = "["+elencoCharNormalizzato+"]";
		Pattern pattern = Pattern.compile(patternStr);

		Matcher matcher = pattern.matcher(strNormalizzata);
		trovato=matcher.find();
		//       	System.out.println("\""+str + "\" - \"" + elencoChar + "\" - trovato? " + trovato);
		return trovato; 
	}

	////////////////end marcoD

	// <!-- BEGIN - MARCOR -->

	/**
	 * Restituisce una stringa così formattata:<BR>
	 * codice1-descrizione1,codice2-descrizione2 ....... codiceX,descrizioneX<BR>
	 * dove:<BR>
	 * codiceX è l'elemento i-esimo dell'array di Codici<BR>
	 * descrizioneX è l'elementi i-esimo dell'array di descrizioni<BR>
	 * - (trattino) è il carattere separatore tra codice/descrizione<BR>
	 * , (virgola) è il carattere separatore delle coppie codice/descrizione<BR>
	 * Eventuali caratteri separatori contenuti nei Codici/Descrizioni saranno sostituiti con blank;
	 * @param vCod - array di Codici
	 * @param vDes - array di Descrizioni
	 * @param charSeparatoreCodDes - carattere separatore codice/descrizione
	 * @param charSeparatoreCoppie - carattere separatore coppie codice/descrizione
	 * @return stringa formattata secondo regola descritta sopra
	 */
	public static String returnStringOfCampoTab(Vector<String> vCod, Vector<String> vDes, String charSeparatoreCodDes, String charSeparatoreCoppie){
		String ret = "";
		StringBuffer temp = new StringBuffer();
		int numCod = 0, numDes = 0;
		String cod = "", des = "";

		if (vCod!=null && vDes!=null){
			numCod = vCod.size();
			numDes = vDes.size();
			for (int indArray=0; indArray < numCod; indArray++){
				cod = trimNotNull(vCod.get(indArray)); 
				des = trimNotNull(((indArray<numDes)?vDes.get(indArray):""));
				cod = cod.replace(charSeparatoreCodDes, " ");
				cod = cod.replace(charSeparatoreCoppie, " ");
				des = des.replace(charSeparatoreCodDes, " ");
				des = des.replace(charSeparatoreCoppie, " ");
				temp.append(charSeparatoreCoppie + cod + charSeparatoreCodDes + des);
			}
		}
		ret = subStrByLen(temp.toString(), 1);

		return ret;
	}

	/**
	 * Splitta la stringa con il carattere separatore passato in input e restituisce un {@link Vector}
	 * contenente i codici.<BR>
	 * es. parStr = "A,B,C,D,E,F..." con charSeparatore = "," return il seguente Vector ret:
	 * ret[0] = "A" - ret[1] = "B" - ....... 
	 * @param parStr - stringa da splittare
	 * @param charSeparatore - carattere separatore
	 * @return {@link Vector} dei codici ricavati dalla stringa data in input
	 */
	public static Vector<String> splitValues( String parStr, String charSeparatore){
		Vector<String> ret = new Vector<String>(0,1);
		String[] splitComma = parStr.split(charSeparatore);
		for( int i=0; i<splitComma.length; i++ ){
			ret.add(splitComma[i]);
		}
		return ret;
	}

	/**
	 * Riceve in input un file e restituisce se si tratta di un'immagine o no;
	 * @param nomeFile - nome file
	 * @return true, se il file è un'immagine; false, altrimenti
	 */
	public static boolean isImage ( String nomeFile ){
		boolean ret = false;
		String estensione = FrmStringa.getEstensione(nomeFile);

		if (estensione.equalsIgnoreCase("JPG") || 
				estensione.equalsIgnoreCase("JPEG") || 
				estensione.equalsIgnoreCase("PNG") || 
				estensione.equalsIgnoreCase("GIF") ) {
			ret = true;
		}

		return ret;
	}

	/**
	 * Riceve in input un file e restituisce se si tratta di una pagina html o no;
	 * @param nomeFile - nome file
	 * @return true, se il file è una pagina html; false, altrimenti
	 */
	public static boolean isHtml ( String nomeFile ){
		boolean ret = false;
		String estensione = FrmStringa.getEstensione(nomeFile);

		if (estensione.equalsIgnoreCase("HTM") || 
				estensione.equalsIgnoreCase("HTML") ) {
			ret = true;
		}

		return ret;
	}

	/**
	 * Riceve in input il nome di un file, e stabilisce se selezionabile opzione EMBEDDED.  
	 * @param nomeFile - nome del file
	 * @return true - se possibile selezionare opzione EMBEDDED, false - altrimenti
	 */
	public static boolean abilitaEmbedded( String nomeFile ){
		boolean ret = false;

		// abilito l'opzione EMBEDDED solo per le immagini o pagine html
		if (FrmStringa.isImage(nomeFile) || FrmStringa.isHtml(nomeFile)){
			ret = true;
		}

		return ret;
	}

	/**
	 * Riceve in input il nome di un file e ne restituisce l'estensione.
	 * @param nomeFile - nome file
	 * @return estensione del file
	 */
	public static String getEstensione ( String nomeFile ){
		String ret = "";

		int indice = nomeFile.lastIndexOf(".");
		if (indice != -1 ){
			ret = FrmStringa.subStrByLen(nomeFile, indice+1);
		}
		return ret;
	}
	
	/**
	 * Spezza una stringa su più righe, ricevendo come parametri il numero massimo di caratteri per singola riga,
	 * il carattere di forzatura rottura riga ed il carattere di separazione parole.
	 * @param str - stringa da splittare su più righe 
	 * @param maxLengthForElement - lunghezza massima per riga
	 * @param charBreakRow - carattere di forzatura a capo
	 * @param charSeparateWord - carattere di separazione parole
	 * @return
	 * @author Rosa Marco - 31/mag/2018
	 */
	public static Vector<String> splitStringInRows(String str, int maxLengthForElement, String charBreakRow, String charSeparateWord){
		Vector<String> ret = new Vector<String>();
		
		String phrase = "";
		StringBuffer tmpPhrase = null;
		String tmp = null;
		
		String[] arrayWords = null;
		String[] arrayRigheACapoForzato = str.split(charBreakRow);
		// ciclo per il numero di elementi che devo obbligatoriamente separare
		for (int i = 0; i < arrayRigheACapoForzato.length; i++) {
			phrase = arrayRigheACapoForzato[i];
			// la stringa per ogni elemento deve essere al massimo @maxLengthForElement
			arrayWords = phrase.split(charSeparateWord);
			
			tmpPhrase = new StringBuffer();
			// ciclo sulle singole parole
			for (int j = 0; j < arrayWords.length; j++) {
				// valuto se la parola può stare sulla stessa riga
				tmp = arrayWords[j] + charSeparateWord;
				if(tmpPhrase.length() + tmp.length() > maxLengthForElement){
					ret.add(tmpPhrase.toString());
					tmpPhrase = new StringBuffer();
				}
				tmpPhrase.append(tmp);
			}
			if(!StringUtils.isEmpty(tmpPhrase.toString())){
				ret.add(tmpPhrase.toString());
			}
		}
		
		return ret;
	}

	/**
	 * Escape characters for text appearing as XML data, between tags.
	 * 
	 * <P>The following characters are replaced with corresponding character entities : 
	 * <table border='1' cellpadding='3' cellspacing='0'>
	 * <tr><th> Character </th><th> Encoding </th></tr>
	 * <tr><td> < </td><td> &lt; </td></tr>
	 * <tr><td> > </td><td> &gt; </td></tr>
	 * <tr><td> & </td><td> &amp; </td></tr>
	 * <tr><td> " </td><td> &quot;</td></tr>
	 * <tr><td> ' </td><td> &#039;</td></tr>
	 * </table>
	 * 
	 * <P>Note that JSTL's {@code <c:out>} escapes the exact same set of 
	 * characters as this method. <span class='highlight'>That is, {@code <c:out>}
	 *  is good for escaping to produce valid XML, but not for producing safe HTML.</span>
	 *  
	 * @author Rosa Marco - 29/dic/08
	 */
	public static String forXML(String aText){
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character =  iterator.current();
		while (character != CharacterIterator.DONE ){
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			} else if (character == '\"') {
				result.append("&quot;");
			} else if (character == '\'') {
				result.append("&#039;");
			} else if (character == '&') {
				result.append("&amp;");
			} else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	/**
	 * @author Luca Tonello - 28/nov/22
	 */
	public static String replaceForXML(String aText){
		if(aText.indexOf("&lt;")!=-1) aText = aText.replaceAll("&lt;", "<");
		if(aText.indexOf("&gt;")!=-1) aText = aText.replaceAll("&gt;", ">");
		if(aText.indexOf("&amp;")!=-1) aText = aText.replaceAll("&amp;", "&");
		if(aText.indexOf("&quot;")!=-1) aText = aText.replaceAll("&quot;", "\"");
		if(aText.indexOf("&#039;")!=-1) aText = aText.replaceAll("&#039;", "'");
		if(aText.indexOf("&egrave;")!=-1) aText = aText.replaceAll("&egrave;", "e");
		if(aText.indexOf("&agrave;")!=-1) aText = aText.replaceAll("&agrave;", "a");
		if(aText.indexOf("&ograve;")!=-1) aText = aText.replaceAll("&ograve;", "o");
		if(aText.indexOf("&eacute;")!=-1) aText = aText.replaceAll("&eacute;", "e");
		if(aText.indexOf("&oacute;")!=-1) aText = aText.replaceAll("&oacute;", "o");
		if(aText.indexOf("&iacute;")!=-1) aText = aText.replaceAll("&iacute;", "i");
		if(aText.indexOf("&rsquo;")!=-1) aText = aText.replaceAll("&rsquo;", "'");
		if(aText.indexOf("&ocirc;")!=-1) aText = aText.replaceAll("&ocirc;", "o");
		if(aText.indexOf("&aacute;")!=-1) aText = aText.replaceAll("&aacute;", "a");
		if(aText.indexOf("&uuml;")!=-1) aText = aText.replaceAll("&uuml;", "u");
		if(aText.indexOf("&ouml;")!=-1) aText = aText.replaceAll("&ouml;", "o");
		if(aText.indexOf("&auml;")!=-1) aText = aText.replaceAll("&auml;", "a");
		if(aText.indexOf("&acirc;")!=-1) aText = aText.replaceAll("&acirc;", "a");
		if(aText.indexOf("&ecirc;")!=-1) aText = aText.replaceAll("&ecirc;", "e");
		if(aText.indexOf("&igrave;")!=-1) aText = aText.replaceAll("&igrave;", "i");
		if(aText.indexOf("&nbsp;")!=-1) aText = aText.replaceAll("&nbsp;", " ");
		if(aText.indexOf("&ntilde;")!=-1) aText = aText.replaceAll("&ntilde;", "n");
		if(aText.indexOf("&atilde;")!=-1) aText = aText.replaceAll("&atilde;", "a");
		if(aText.indexOf("&yacute;")!=-1) aText = aText.replaceAll("&yacute;", "y");
		if(aText.indexOf("&ndash;")!=-1) aText = aText.replaceAll("&ndash;", "-");

		return aText;
	}
	
	/**
	 * Rimuove caratteri strani per xml
	 * @author Rossi Alessandro - 23/ott/2020
	 */
	public static String rimuoviCaratteriStraniforXML(String aText){
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character =  iterator.current();
		while (character != CharacterIterator.DONE ){
			if ( character == '<' ||
				 character == '>' ||
				 character == '\"' ||
				 character == '\'' ||
				 character == '°' ||
				 character == '&' ) {
				result.append("");
			} else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
	
	/**
	 * Sostituisce il carattere ISO con la squenza neccessaria per XML .
	 * @author Oltean Cristina - 17/set/2018
	 */
	public static String replaceCharForXML(String aText){
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character =  iterator.current();
		while (character != CharacterIterator.DONE ){
			result.append("&#" + ((int)character) + ";");
			character = iterator.next();
		}
		return result.toString();
	}
	
	/**
	 * Escape characters for text appearing in HTML markup.
	 * 
	 * <P>This method exists as a defence against Cross Site Scripting (XSS) hacks.
	 * The idea is to neutralize control characters commonly used by scripts, such that they will not 
	 * be executed by the browser. This is done by replacing the control characters with  
	 * their escaped equivalents.  See {@link hirondelle.web4j.security.SafeText} as well.
	 * 
	 * <P>The following characters are replaced with corresponding HTML character entities : 
	 * <table border='1' cellpadding='3' cellspacing='0'>
	 * <tr><th> Character </th><th>Replacement</th></tr>
	 * <tr><td> < </td><td> &lt; </td></tr>
	 * <tr><td> > </td><td> &gt; </td></tr>
	 * <tr><td> & </td><td> &amp; </td></tr>
	 * <tr><td> " </td><td> &quot;</td></tr>
	 * <tr><td> \t </td><td> &#009;</td></tr>
	 * <tr><td> ! </td><td> &#033;</td></tr>
	 * <tr><td> # </td><td> &#035;</td></tr>
	 * <tr><td> $ </td><td> &#036;</td></tr>
	 * <tr><td> % </td><td> &#037;</td></tr>
	 * <tr><td> ' </td><td> &#039;</td></tr>
	 * <tr><td> ( </td><td> &#040;</td></tr> 
	 * <tr><td> ) </td><td> &#041;</td></tr>
	 * <tr><td> * </td><td> &#042;</td></tr>
	 * <tr><td> + </td><td> &#043; </td></tr>
	 * <tr><td> , </td><td> &#044; </td></tr>
	 * <tr><td> - </td><td> &#045; </td></tr>
	 * <tr><td> . </td><td> &#046; </td></tr>
	 * <tr><td> / </td><td> &#047; </td></tr>
	 * <tr><td> : </td><td> &#058;</td></tr>
	 * <tr><td> ; </td><td> &#059;</td></tr>
	 * <tr><td> = </td><td> &#061;</td></tr>
	 * <tr><td> ? </td><td> &#063;</td></tr>
	 * <tr><td> @ </td><td> &#064;</td></tr>
	 * <tr><td> [ </td><td> &#091;</td></tr>
	 * <tr><td> \ </td><td> &#092;</td></tr>
	 * <tr><td> ] </td><td> &#093;</td></tr>
	 * <tr><td> ^ </td><td> &#094;</td></tr>
	 * <tr><td> _ </td><td> &#095;</td></tr>
	 * <tr><td> ` </td><td> &#096;</td></tr>
	 * <tr><td> { </td><td> &#123;</td></tr>
	 * <tr><td> | </td><td> &#124;</td></tr>
	 * <tr><td> } </td><td> &#125;</td></tr>
	 * <tr><td> ~ </td><td> &#126;</td></tr>
	 * </table>
	 * 
	 * <P>Note that JSTL's {@code <c:out>} escapes <em>only the first 
	 * five</em> of the above characters.
	 * 
	 * @author Rosa Marco - 29/dic/08
	 */
	public static String forHTML(String aText){
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character =  iterator.current();
		while (character != CharacterIterator.DONE ){
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			} else if (character == '&') {
				result.append("&amp;");
			} else if (character == '\"') {
				result.append("&quot;");
			} else if (character == '\t') {
				addCharEntity(9, result);
			} else if (character == '!') {
				addCharEntity(33, result);
			} else if (character == '#') {
				addCharEntity(35, result);
			} else if (character == '$') {
				addCharEntity(36, result);
			} else if (character == '%') {
				addCharEntity(37, result);
			} else if (character == '\'') {
				addCharEntity(39, result);
			} else if (character == '(') {
				addCharEntity(40, result);
			} else if (character == ')') {
				addCharEntity(41, result);
			} else if (character == '*') {
				addCharEntity(42, result);
			} else if (character == '+') {
				addCharEntity(43, result);
			} else if (character == ',') {
				addCharEntity(44, result);
			} else if (character == '-') {
				addCharEntity(45, result);
			} else if (character == '.') {
				addCharEntity(46, result);
			} else if (character == '/') {
				addCharEntity(47, result);
			} else if (character == ':') {
				addCharEntity(58, result);
			} else if (character == ';') {
				addCharEntity(59, result);
			} else if (character == '=') {
				addCharEntity(61, result);
			} else if (character == '?') {
				addCharEntity(63, result);
			} else if (character == '@') {
				addCharEntity(64, result);
			} else if (character == '[') {
				addCharEntity(91, result);
			} else if (character == '\\') {
				addCharEntity(92, result);
			} else if (character == ']') {
				addCharEntity(93, result);
			} else if (character == '^') {
				addCharEntity(94, result);
			} else if (character == '_') {
				addCharEntity(95, result);
			} else if (character == '`') {
				addCharEntity(96, result);
			} else if (character == '{') {
				addCharEntity(123, result);
			} else if (character == '|') {
				addCharEntity(124, result);
			} else if (character == '}') {
				addCharEntity(125, result);
			} else if (character == '~') {
				addCharEntity(126, result);
			} else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	private static void addCharEntity(Integer aIdx, StringBuilder aBuilder){
		String padding = "";
		if( aIdx <= 9 ){
			padding = "00";
		} else if( aIdx <= 99 ){
			padding = "0";
		} else {
			//no prefix
		}
		String number = padding + aIdx.toString();
		aBuilder.append("&#" + number + ";");
	}

	/**
	 * Estrae un numero contenuto in una stringa.
	 * Es. se la stringa contiene: "333-123456 Marco"
	 * restituisce: "333123456"
	 * ATTENZIONE:
	 * error: se la stringa contiene: "333-123456 Marco_1"
	 * restituisce: "3331234561"
	 * 
	 * Utile per mantenere su DB il numero di cellurare parlante, ma che per essere utilizzato
	 * deve essere ripulito di ogni cosa NON è un numero (spazi compresi)
	 * @param parStringa - stringa contenente un numero e altro
	 * @return la succesione di numeri ossia il numero puro
	 * @author Rosa Marco - 24/apr/09
	 */
	public static String estraiNumeroFromString( String parStringa ){
		String ret = "";

		if (!strVoid(parStringa))
			ret = parStringa.replaceAll("[^0-9+]", "");

		return ret;
	}
	
	/**
	 * Incrementa di 1 la parte numerica finale di una stringa alfanumerica.
	 * es. TM0000109610 --> TM0000109611
	 * @param testo
	 * @return
	 */
	public static String incrementLastNumberOfString(String testo){
		String ret = testo;
		
		String prefix = "";
		String onlyNumber = "";
		boolean end = false;
		for(int i = testo.length()-1; i >= 0 && !end; i--){
			String tmp = FrmStringa.subStrByLen(testo, i, i+1);
			if(StringUtils.isNumeric(tmp)){
				onlyNumber = tmp + onlyNumber;
			} else {
				end = true;
				prefix = FrmStringa.subStrByLen(testo, 0, i+1);
			}
		}
		int lengthNumber = onlyNumber.length();
		if(lengthNumber > 0){
			int valoreCampo = FrmStringa.parseInt(onlyNumber);
			valoreCampo += 1;
			ret = prefix + FrmStringa.allineaDxFilling(String.valueOf(valoreCampo), "0", lengthNumber);
		}

		return ret;
	}

	/**
	 * Formatta il nome di un file ricevuto in input, 
	 * lasciando nel nome solo lettere [a-zA-Z] e numeri [0-9] ed underscore [_] 
	 * @param parFileName - file name to encoding
	 * @return 
	 * @author Rosa Marco - 13/apr/2010
	 */
	public static String formatFileName( String parFileName ){
		String ret = parFileName;

		if (!strVoid(ret)){
			ret = ret.replaceAll("à", "a");
			ret = ret.replaceAll("è", "e");
			ret = ret.replaceAll("é", "e");
			ret = ret.replaceAll("ì", "i");
			ret = ret.replaceAll("ò", "o");
			ret = ret.replaceAll("ù", "u");
			ret = ret.replaceAll("[^0-9a-zA-Z.-]", "_");
		}

		return ret;
	}	

	/**
	 * Imposta l'elenco su 5 colonne
	 * @param elenco - elenco
	 * @return elenco formattato
	 * @author Rosa Marco - 24/mag/2010
	 */
	public static String formattaElenco(String elenco, String classAttribute){
		String ret = "";
		int indElement = 0, indInputElementBegin = 0, indFontElementBegin = 0, indFontElementEnd = 0;
		final int NUM_ELEMENT_PER_ROW = 5;

		elenco = elenco.replaceAll("<font>", "<font class='font_check'>");

		// Definizione Tabella
		ret += "<TABLE width='100%' cellpadding='0' cellspacing='0'>";
		ret += "\n<TR>" +
				"\n\t<TD class='"+classAttribute+"' width='3%'></TD>" +
				"\n\t<TD class='"+classAttribute+"' width='17%'></TD>" +
				"\n\t<TD class='"+classAttribute+"' width='3%'></TD>" +
				"\n\t<TD class='"+classAttribute+"' width='17%'></TD>" +
				"\n\t<TD class='"+classAttribute+"' width='3%'></TD>" +
				"\n\t<TD class='"+classAttribute+"' width='17%'></TD>" +
				"\n\t<TD class='"+classAttribute+"' width='3%'></TD>" +
				"\n\t<TD class='"+classAttribute+"' width='17%'></TD>" +
				"\n\t<TD class='"+classAttribute+"' width='3%'></TD>" +
				"\n\t<TD class='"+classAttribute+"' width='17%'></TD>";

		indInputElementBegin = elenco.indexOf("<INPUT");
		indFontElementBegin = elenco.indexOf("<font");
		indFontElementEnd = elenco.indexOf("</font>") + "</font>".length();

		while(indInputElementBegin != -1){
			if (indElement % NUM_ELEMENT_PER_ROW == 0){
				ret += "</TR>";	
				ret += "\n<TR>";
			}
			// checkbox
			ret += "\n\t<TD class='"+classAttribute+"'>";
			ret += FrmStringa.subStrByLen(elenco, indInputElementBegin, indFontElementBegin);
			ret += "</TD>";
			// label
			ret += "\n\t<TD class='"+classAttribute+"'>";
			ret += FrmStringa.subStrByLen(elenco, indFontElementBegin, indFontElementEnd);
			ret += "</TD>";

			indInputElementBegin = elenco.indexOf("<INPUT", indFontElementEnd);
			indFontElementBegin = elenco.indexOf("<font", indInputElementBegin);
			indFontElementEnd = elenco.indexOf("</font>", indInputElementBegin) + "</font>".length();

			indElement++;
		}

		ret += "</TR>";
		ret += "</TABLE>";

		return ret;
	}    

	// <!-- END - MARCOR -->

	/**
	 * Restituisce n spazi PDF
	 * @param Numero spazi
	 * @return n spazi
	 * @author Alessandro Servetti
	 * @version 1.0.0 24/apr/08
	 */
	public static String makeSpacePDF(int prNumberSpace) {
		String tmpStringa = " ";

		for(int k=1;k<=prNumberSpace;k++){
			tmpStringa += " ";
		}

		return tmpStringa;
	}

	/**
	 * Converte un Vector in una stringa con gli elementi separati da virgola (,)
	 * @param vettore da confertire in stringa
	 * @author Rosa Marco - 30/dic/2014
	 */
	public static String vettoreToStringa(Vector<String> v){
		String ret = v.toString();
		// tolgo le parentesi quadre... (es, [a,b,c] --> a,b,c)
		ret = FrmStringa.subStrByLen(ret, 1, ret.length() - 1);
		return ret;
	}

	public static String vettoreObjToStringa(Vector<Object> v){
		String ret = v.toString();
		// tolgo le parentesi quadre... (es, [a,b,c] --> a,b,c)
		ret = FrmStringa.subStrByLen(ret, 1, ret.length() - 1);
		return ret;
	}

	/**
	 * Compatta vettore in stringa con specifico separatore
	 * @param Vettore da scompattare
	 * @param Separatore
	 * @param Boolean aggiungi terminatore
	 * @param Boolean gestisci null
	 * @author Alessandro Servetti
	 * @version 1.0.0 30/ott/08
	 */
	public static String compattaVettore(Vector prVector,String prSeparatore,boolean prTerminatore,boolean prGestisciNull){
		String strCompattata = null;
		for(int k=0;k<prVector.size();k++){
			if(k==0){
				strCompattata = "";
			}
			if(prGestisciNull){
				if(prVector.get(k)==null){
					strCompattata += "null" + prSeparatore;
				}else{
					strCompattata += prVector.get(k).toString() + prSeparatore;
				}
			}else{
				strCompattata += prVector.get(k).toString() + prSeparatore;
			}
			if((k==prVector.size()-1) && prTerminatore){
				strCompattata += "*";
			}
		}		
		return strCompattata;
	}

	/**
	 * Scompatta stringa in vettore splittando con specifico separatore
	 * @param Stringa da compattare
	 * @param Separatore
	 * @param Terminatore stringa
	 * @param Boolean escludi terminatore stringa
	 * @author Servetti Alessandro - 17/feb/11
	 */
	public static Vector<String> scompattaStringa(String prStringa,String prSeparatore,String prTerminatoreStr,boolean prIncludiTerminatoreStr){
		Vector<String> vecScompattato = new Vector<String>();
		String[] strScompattata = prStringa.split(prSeparatore);
		int maxLenght = 0;
		if(prIncludiTerminatoreStr){
			maxLenght = strScompattata.length;
		}else{
			if(strScompattata.length>0 && strScompattata[strScompattata.length-1].equals(prTerminatoreStr)){
				maxLenght = strScompattata.length-1;
			}else{
				maxLenght = strScompattata.length;
			}
		}
		for(int k=0;k<maxLenght;k++){
			vecScompattato.add(strScompattata[k]);
		}		
		return vecScompattato;
	}

	/**
	 * Rende NON null una stringa impostando il valore di default se passato, vuoto se valore default null.<br>
	 * @param String stringa da rendere NON null
	 * @param String valore default
	 * @author Maurizio	Rosso
	 * @version 1.0.0 20/nov/08
	 */
	public static String rendiNonNull(String stringa, String valoreDefault){
		if (stringa==null) {
			if (valoreDefault==null) {
				valoreDefault="";
			}
			stringa=valoreDefault;
		}

		return stringa;
	}


	/**
	 * Formatta vendita giorni e posti disponibili per rotazione operativo voli tropico
	 * @param dato da formattare
	 * @param tipo dato<BR>
	 * <B>GV</B> giorni vendita<BR>
	 * <B>PV</B> posti vendita
	 * @author Luca Tonello
	 * @version 1.0.0 08/apr/2009
	 */
	public static String formattaGiorniDisponibilita(long prDato, String prTipo) {
		String tmpStringa = null;
		DecimalFormat formatter = null;

		if(prTipo.equals("GV")){
			formatter = new DecimalFormat("00000000");
		} else if(prTipo.equals("PV")){
			formatter = new DecimalFormat("000000000000");			
		}
		tmpStringa=String.valueOf(formatter.format(prDato));

		return tmpStringa;
	}

	public static String dom3NodeToString( Node doc ){
		String retVal="";
		if( doc != null ){
			NodeList children = doc.getChildNodes();
			if( children != null ){
				Node node = null;
				for( int i=0; i<children.getLength(); i++ ){
					node = children.item(i);
					switch( node.getNodeType() ){
					case 1: //tag:
						retVal+= node.getNodeName()+":";
						NodeList nephew = children.item(i).getChildNodes();
						if( nephew!=null ){
							if( nephew.getLength()>0 ){
								retVal += dom3NodeToString( node );
							}
						}
						retVal += "\n";
						break;
					case 3: //text
						if( !FrmStringa.strVoid(FrmStringa.trimNotNull(node.getNodeValue()))){
							//retVal += node.getNodeName()+"("+node.getNodeType()+"):"+FrmStringa.trimNotNull( node.getNodeValue() )+"\n";
							retVal+= node.getNodeValue();
						}
						break;
					case 8: //comment
						break;
					default:
						retVal += node.getNodeName()+"("+node.getNodeType()+"):"+FrmStringa.trimNotNull( node.getNodeValue() )+"\n";
						break;
					}

				}
			}
		}
		return retVal;
	}

	/**
	 * Verifica la validita' dell' indirizzo email
	 * @param indMail
	 * @author Alessandro Rossi - 18/set/09
	 */
	public static boolean validaIndirizzoEmail(String indMail){
		boolean ret = true;

		String strRegEx = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,})$";

		Pattern pat =  Pattern.compile( strRegEx );
		Matcher matcher = pat.matcher( indMail );
		if( matcher!=null){
			if( !matcher.find() ){
				ret = false;
			}
		}

		return ret;
	}

	/**
	 * Verifica la validita' del cellulare
	 * @param cellulare
	 * @author Rosa Marco - 09/mag/2012
	 */
	public static boolean validaCellulare(String cellulare){
		boolean ret = true;

		String strRegEx = "^((00|\\+)\\d{2})??\\d{3}\\d{6,7}$";
		if( !Pattern.matches(strRegEx, cellulare ) ){
			ret = false;
		}

		return ret;
	}
	
	/**
     * Verifica se contiene un indirizzo ip con porta valida
     * @param cellulare
     * @author Davide Sponselli - 17/gen/2019
     */
	public static boolean validaIpPorta(String ipPorta){
	  Pattern p = Pattern.compile("^"
          + "(((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}" // Domain name
          + "|"
          + "localhost" // localhost
          + "|"
          + "(([0-9]{1,3}\\.){3})[0-9]{1,3})" // Ip
          + ":"
          + "[0-9]{1,5}$"); // Port
	  return p.matcher(ipPorta).matches();
	}

	/**
	 * Genera una stringa di caratteri minuscoli e maiuscoli + cifre casuale di n bytes
	 * @param int lunghezza della stringa da generare
	 * @author Gianluca Bruno - 30/apr/2010
	 */
	public static String generaStringaCasuale(int nCaratteri){
		String ret = "";
		for (int i=0;i<nCaratteri;i++){
			Random generator = new Random();
			int randomIndex = generator.nextInt(62);
			if (randomIndex<=9){
				// Numero
				ret+=String.valueOf(randomIndex).trim();
			} else if (randomIndex<=35){
				// Carattere Maiuscolo
				int offset=randomIndex-10;
				ret+=(char)(65+offset);
			} else {
				// Carattere Minuscolo
				int offset=randomIndex-36;
				ret+=(char)(97+offset);
			}
		}
		return ret;
	}

	public static final String siap_concatStr = "siap-concat";

	public static String genTag( String tagname, boolean end ){
		String retVal ="<";
		if( end ){
			retVal += "/";
		}
		retVal += siap_concatStr+">";
		return retVal;
	}

	/**
	 * @param srcstr
	 * @return
	 */
	public static StringBuffer concatXML(StringBuffer srcstr, String xml_addendum) {
		StringBuffer concatenated = new StringBuffer();
		concatenated.append(genTag(siap_concatStr,false)+"\n");
		if( srcstr!=null && srcstr.length()>0 ){
			concatenated.append( srcstr );
			concatenated.append("\n");
		}
		concatenated.append( xml_addendum );
		concatenated.append(genTag(siap_concatStr,true)+"\n");
		return concatenated;
	}

	/**
	 * Wait n seconds 
	 * @author Adela Ciobra - 11/Jan/2010
	 */

	public static void waiting (int n) {        
		try{
			Object wait_semaphore= new Object();
			int wait_time_ms = n*1000;
			synchronized (wait_semaphore) {
				wait_semaphore.wait(wait_time_ms);
			}
		}catch( InterruptedException e){

		}
	}

	/**
	 * Remove element at index in an int array
	 * @author Adela Ciobra - 29/March/2010
	 */

	public static int[] removeElementInt(int[] a, int index) {
		for(int i = index; i < a.length-1; i++) {
			a[i] = a[i+1];
		}
		int[] arr = new int[a.length-1];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = a[i];
		}
		return arr;
	}

	/** 
	 * @param filePath the name of the file to open
	 * @author Adela Ciobra - 13/April/2010
	 */ 

	public static String readFileAsString(String filePath) {
		String retStr = "";        
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			while (reader.readLine() != null) {
				retStr += reader.readLine() + '\n';
			}
			reader.close();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}

		return retStr;
	}

	/** strval.replaceAll( arrChar[i], arrCharRep[i]
	 * @param strval
	 * @param arrChar
	 * @param arrCharRep
	 * @return
	 */
	public static String replaceAllArray(String strval, String[] arrChar, String[] arrCharRep) {
		if( strval != null ){
			for( int j=0; j< arrChar.length; j++){
				strval = strval.replaceAll(arrChar[j], arrCharRep[j]);
			}
		}
		return strval;
	}

	/**
	 * dato uno stringBuilder, sostituisce tutte le occorrenze della stringa FROM con la stringa TO
	 * @author Marco Damilano - 24/set/2019
	 * */
	public static void replaceAll(StringBuilder builder, String from, String to){
	    int index = builder.indexOf(from);
	    while (index != -1){
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = builder.indexOf(from, index);
	    }
	}
	
	/**
	 * Calcola e restituisce il numero di occorrenze di "stringaDaRicercare" all'interno
	 * di "stringaSuCuiRicercare".<br>
	 * Es.: occorrenze("MCO-I--9074316457-","-") restituisce 4, che rappresenta il numero di
	 * di volte che il "-" (stringaDaRicercare) compare all'interno di "MCO-I--9074316457-" (stringaSuCuiRicercare) 
	 */
	public static int occorrenze(String stringaSuCuiRicercare, String stringaDaRicercare) {
		int indiceOccorrenza = stringaSuCuiRicercare.indexOf(stringaDaRicercare);
		if (indiceOccorrenza!=-1){
			return 1 + occorrenze(stringaSuCuiRicercare.substring(indiceOccorrenza+1,stringaSuCuiRicercare.length()),stringaDaRicercare);
		} else {
			return 0;
		}
	}

	private static TreeMap<Character, Character> mapAccent = null;

	/**
	 * imposta i valori per eliminare i caratteri accentati che possono dare fastidio con alcune librerie
	 *
	 */
	private static void init_char_map(){
		char c;

		if( mapAccent== null ){
			mapAccent = new TreeMap<Character,Character>();

			c= 192;
			mapAccent.put( new Character(c++), new Character('A') );//192 À
			mapAccent.put( new Character(c++), new Character('A') );//193 Á
			mapAccent.put( new Character(c++), new Character('A') );//194 Â
			mapAccent.put( new Character(c++), new Character('A') );//195 Ã
			mapAccent.put( new Character(c++), new Character('A') );//197 Å
			mapAccent.put( new Character(c++), new Character('A') );//198 Æ

			mapAccent.put( new Character(c++), new Character('C') );//199 Ç

			mapAccent.put( new Character(c++), new Character('E') );//200 È
			mapAccent.put( new Character(c++), new Character('E') );//201 É
			mapAccent.put( new Character(c++), new Character('E') );//202 Ê
			mapAccent.put( new Character(c++), new Character('E') );//203 Ë

			mapAccent.put( new Character(c++), new Character( 'I' ));//204 Ì
			mapAccent.put( new Character(c++), new Character( 'I' ));//205 Í
			mapAccent.put( new Character(c++), new Character( 'I' ));//206 Î
			mapAccent.put( new Character(c++), new Character( 'I' ));//207 Ï

			c=209;
			mapAccent.put( new Character(c++), new Character( 'N' ));//209 N

			mapAccent.put( new Character(c++), new Character( 'O' ));//210 Ò
			mapAccent.put( new Character(c++), new Character( 'O' ));//211 Ó
			mapAccent.put( new Character(c++), new Character( 'O' ));//212 Ô
			mapAccent.put( new Character(c++), new Character( 'O' ));//213 Õ
			mapAccent.put( new Character(c++), new Character( 'O' ));//214 Ö

			c= 217;
			mapAccent.put( new Character(c++), new Character( 'U' ));//217 Ù
			mapAccent.put( new Character(c++), new Character( 'U' ));//218 Ú
			mapAccent.put( new Character(c++), new Character( 'U' ));//219 Û
			mapAccent.put( new Character(c++), new Character( 'U' ));//220 Û

			mapAccent.put( new Character(c++), new Character( 'Y' ));//221 Y

			///minuscoli

			c= 224;
			mapAccent.put( new Character(c++), new Character('a') );//224 à
			mapAccent.put( new Character(c++), new Character('a') );//225 à
			mapAccent.put( new Character(c++), new Character('a') );//226 â
			mapAccent.put( new Character(c++), new Character('a') );//227 ã
			mapAccent.put( new Character(c++), new Character('a') );//228 ä
			mapAccent.put( new Character(c++), new Character('a') );//229 å
			mapAccent.put( new Character(c++), new Character('a') );//230 æ

			mapAccent.put( new Character(c++), new Character('c') );//231 ç

			mapAccent.put( new Character(c++), new Character('e') );//232 è
			mapAccent.put( new Character(c++), new Character('e') );//233 é
			mapAccent.put( new Character(c++), new Character('e') );//234 ê
			mapAccent.put( new Character(c++), new Character('e') );//235 ë

			mapAccent.put( new Character(c++), new Character( 'i' ));//236 ì
			mapAccent.put( new Character(c++), new Character( 'i' ));//237 í
			mapAccent.put( new Character(c++), new Character( 'i' ));//238 î
			mapAccent.put( new Character(c++), new Character( 'i' ));//239 ï

			c=241;
			mapAccent.put( new Character(c++), new Character( 'n' ));//241 ñ

			mapAccent.put( new Character(c++), new Character( 'o' ));//242 ò
			mapAccent.put( new Character(c++), new Character( 'o' ));//243 ó
			mapAccent.put( new Character(c++), new Character( 'o' ));//244 ô
			mapAccent.put( new Character(c++), new Character( 'o' ));//245 õ
			mapAccent.put( new Character(c++), new Character( 'o' ));//247 ö

			c= 249;
			mapAccent.put( new Character(c++), new Character( 'u' ));//249 ù
			mapAccent.put( new Character(c++), new Character( 'u' ));//250 ú
			mapAccent.put( new Character(c++), new Character( 'u' ));//251 û
			mapAccent.put( new Character(c++), new Character( 'u' ));//252 ü

			mapAccent.put( new Character(c++), new Character( 'y' ));//253 ý

		}

	}

	/**
	 * pulisce la stringa src dai caratteri accentati sostituendoli con corrispondenti non accentati
	 * @param src
	 * @return
	 */
	public static String cleanAccent( String src ){
		String clean = src;

		init_char_map();

		char c;
		char[] cArray = src.toCharArray();
		boolean found=false;
		Character cObj = null;
		for( int i=0; i< cArray.length; i++ ){
			c= cArray[ i ];
			cObj = new Character(c) ;
			if( mapAccent.containsKey( cObj ) ){
				found = true;
				cObj = (Character) mapAccent.get( cObj );
				cArray[i] = cObj.charValue(); 
			}
		}

		if( found ){
			clean = String.valueOf( cArray );
		}
		return clean;
	}

	public static String formataCodDescPDF(String cod, String descrizione) {

		return formataCodDesc(cod, descrizione, "-");	
	}

	public static String formataCodDesc(String data1, String data2, String character) {
		String ret = "";
		if(!FrmStringa.trimNotNull(data1).equals("") && !FrmStringa.trimNotNull(data2).equals("")){
			ret = data1 + character + data2;
		} else {
			ret = data1 + data2;
		}
		return ret;	
	}

	/** 
	 * Restituisce spazio vuoto "" se stringa NULL o vuota (lunghezza = 0).  
	 *@param str
	 *@return String
	 *@author Angelo Iavarone (14/05/2014)
	 */
	public static String spaceIfNullOrEmpty( String str ) {
		return str == null || str.trim().length() == 0 ? " " : str.trim();
	}


	/**
	 * toglie i caratteri che possono dare problemi sul database
	 * @author Marco Damilano - 13/giu/2014
	 * */
	public static String togliCaratteriStrani(String stringa){
		// PROBLEMA x il simbolo "/" che diventa "-"
		stringa=stringa.replaceAll("\n", "__N__");
		stringa=stringa.replaceAll("\t", "__T__");
		//		stringa=stringa.replaceAll("[à]", "a'");
		//		stringa=stringa.replaceAll("[è]", "e'");
		//		stringa=stringa.replaceAll("[é]", "e'");
		//		stringa=stringa.replaceAll("[ì]", "i'");
		//		stringa=stringa.replaceAll("[ò]", "o'");
		//		stringa=stringa.replaceAll("[ù]", "u'");
		stringa=stringa.replaceAll("[€]", "EUR");
		stringa=stringa.replaceAll("[$]", "USD");
		stringa=stringa.replaceAll("[`]", "'");
		stringa=stringa.replaceAll("[^0-9a-zA-Z._()àèéìòù=£@#/%&?!,:;'^°-ÁÉÍÓÚÜÑ¿¡ÄÖÀÂÆÊÈËÎÏÔÙÛÇ]", " ");
		stringa=stringa.replaceAll("__N__", "\n");
		stringa=stringa.replaceAll("__T__", "\t");
		return stringa;
	}

	/**
	 * toglie tutti i caratteri strani. Mantiene solo le lettere (maiuscole/minuscole), numeri e lo slash "/" e lo spazio <br>
	 * I caratteri accentati vengono sostituiti dai caratteri senza accento
	 * @author Alessandro Rossi - 25/ott/2016
	 * */
	public static String togliTuttiCaratteriStrani(String stringa){
		stringa=cleanAccent(stringa);
		stringa=stringa.replaceAll("[^0-9a-zA-Z/]", "");
		return stringa;
	}

	/**
	 * Sostituisce tutti i valori che non sono numeri con un "", quindi gli elimina
	 * @param 
	 * @author Alex Nota - 25/05/2016
	 * */
	public static String lasciaSoloNumeri(String stringa){

		String str = stringa.replaceAll("[^0-9]", "");

		return str;
	}

	/**
	 * input Y / N
	 * output Yes / No
	 * @author Darius Daianu - Feb 4, 2014
	 */
	public static String formatYesNo(String data) {
		String ret = "";
		if(data.equals("Y")) {
			ret = "Yes";
		} else if(data.equals("N")) {
			ret = "No";
		}
		return ret;
	}
	
	/**
	 * @author Rosso Ivan - 30/set/2022
	 * @param data
	 * @param strDef
	 * @return
	 */
	public static String formatYesNoWithDefaul(String data, String strDef) {
		String ret = strDef;
		if(data.equals("Y")) {
			ret = "Yes";
		} else if(data.equals("N")) {
			ret = "No";
		}
		return ret;
	}

	/**
	 * input S / N
	 * output Si / No
	 * @author Rosa Marco - 01/ott/2015
	 */
	public static String formatSiNo(String data) {
		String ret = "";
		if(data.equals("S")) {
			ret = "Si";
		} else if(data.equals("N")) {
			ret = "No";
		}
		return ret;
	}

	/**
	 * controllo per il separatoreKey per una stringa da chiamata ajax jquery
	 * @author Davide Sponselli - 08/01/2015
	 */
	public static String getKeyWithSeparatoreKeyFromAjaxJquery(String val) throws UnsupportedEncodingException{
		String value = null;
		if (val.contains("Ã")){
			value = new String(val.getBytes("iso-8859-15"),"UTF-8");
		}else{
			value = val;
		}
		return value;
	}

	/**
	 * Funzione per il controllo del codice fiscale.
	 * esempio CF errati:
	 * - XXXXXXXXXXXXXXXX
	 * - NTNGTN64011H090B
	 * - BNNGSP49G78H501P (attenzione lo valuta come corretto, da migliorare algoritmo...)
	 * @author Rosa Marco - 16/ott/2015
	 */
	public static boolean controllaCF(String cf) {

		/* espr. regolare per controllare la correttezza sintattica del CF */
		String regex = "[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]";
		int somma = 0;

		/* Creazione dell'HashMap che realizza l'associazione chiave valore per
	       i caratteri dispari */
		Map<String, String> chrDispari = new HashMap<String, String>();
		chrDispari.put("0", "1");
		chrDispari.put("1", "0");
		chrDispari.put("2", "5");
		chrDispari.put("3", "7");
		chrDispari.put("4", "9");
		chrDispari.put("5", "13");
		chrDispari.put("6", "15");
		chrDispari.put("7", "17");
		chrDispari.put("8", "19");
		chrDispari.put("9", "21");
		chrDispari.put("A", "1");
		chrDispari.put("B", "0");
		chrDispari.put("C", "5");
		chrDispari.put("D", "7");
		chrDispari.put("E", "9");
		chrDispari.put("F", "13");
		chrDispari.put("G", "15");
		chrDispari.put("H", "17");
		chrDispari.put("I", "19");
		chrDispari.put("J", "21");
		chrDispari.put("K", "2");
		chrDispari.put("L", "4");
		chrDispari.put("M", "18");
		chrDispari.put("N", "20");
		chrDispari.put("O", "11");
		chrDispari.put("P", "3");
		chrDispari.put("Q", "6");
		chrDispari.put("R", "8");
		chrDispari.put("S", "12");
		chrDispari.put("T", "14");
		chrDispari.put("U", "16");
		chrDispari.put("V", "10");
		chrDispari.put("W", "22");
		chrDispari.put("x", "25");
		chrDispari.put("Y", "24");
		chrDispari.put("Z", "23");

		/* HashMap che realizza l'associazione chiave-valore per i caratteri
	        pari all'interno del codice fiscale. */
		Map<String, String> chrPari = new HashMap<String, String>();
		chrPari.put("0", "0");
		chrPari.put("1", "1");
		chrPari.put("2", "2");
		chrPari.put("3", "3");
		chrPari.put("4", "4");
		chrPari.put("5", "5");
		chrPari.put("6", "6");
		chrPari.put("7", "7");
		chrPari.put("8", "8");
		chrPari.put("9", "9");
		chrPari.put("A", "0");
		chrPari.put("B", "1");
		chrPari.put("C", "2");
		chrPari.put("D", "3");
		chrPari.put("E", "4");
		chrPari.put("F", "5");
		chrPari.put("G", "6");
		chrPari.put("H", "7");
		chrPari.put("I", "8");
		chrPari.put("J", "9");
		chrPari.put("K", "10");
		chrPari.put("L", "11");
		chrPari.put("M", "12");
		chrPari.put("N", "13");
		chrPari.put("O", "14");
		chrPari.put("P", "15");
		chrPari.put("Q", "16");
		chrPari.put("R", "17");
		chrPari.put("S", "18");
		chrPari.put("T", "19");
		chrPari.put("U", "20");
		chrPari.put("V", "21");
		chrPari.put("W", "22");
		chrPari.put("X", "23");
		chrPari.put("Y", "24");
		chrPari.put("Z", "25");

		/* HashMap che associa un valore numerico per ogni carattere che potrebbe
	        essere presente come ultimo carattere del codice fiscale */
		Map<String, String> chrControllo = new HashMap<String, String>();
		chrControllo.put("0", "A");
		chrControllo.put("1", "B");
		chrControllo.put("2", "C");
		chrControllo.put("3", "D");
		chrControllo.put("4", "E");
		chrControllo.put("5", "F");
		chrControllo.put("6", "G");
		chrControllo.put("7", "H");
		chrControllo.put("8", "I");
		chrControllo.put("9", "J");
		chrControllo.put("10", "K");
		chrControllo.put("11", "L");
		chrControllo.put("12", "M");
		chrControllo.put("13", "N");
		chrControllo.put("14", "O");
		chrControllo.put("15", "P");
		chrControllo.put("16", "Q");
		chrControllo.put("17", "R");
		chrControllo.put("18", "S");
		chrControllo.put("19", "T");
		chrControllo.put("20", "U");
		chrControllo.put("21", "V");
		chrControllo.put("22", "W");
		chrControllo.put("23", "X");
		chrControllo.put("24", "Y");
		chrControllo.put("25", "Z");

		try {
			/* Effettua la somma di tutti i valori ricavati da ogni singolo carattere
	        del CF. Se il carattere è pari, il valore è prelevato dall'HashMap dei
	        numeri pari, dispari altrimenti. */
			for(int i=0; i<15; i++) {
				if (i % 2 == 1)
				{
					somma += Integer.parseInt((String)
							chrPari.get(Character.toString(cf.charAt(i))));
				}
				else
				{
					somma += Integer.parseInt((String)
							chrDispari.get(Character.toString(cf.charAt(i))));
				}
			}

			/* Calcolo l'esatto carattere di controllo in base al codice fiscale
	        fornito dall'utente */
			String strControlloCalcolato = (String)chrControllo.get(""+somma%26);
			Character chrControlloCalcolato = strControlloCalcolato.charAt(0);

			/* Ricavo il carattere di controllo fornito dall'utente */
			Character chrControlloInserito = cf.charAt(15);


			if (!Pattern.matches(regex, cf) || 
					!chrControlloInserito.equals(chrControlloCalcolato)){
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

		return true;
	}

	/**
	 * funzione che sostituisce caratteri i strani provenienti 
	 * da "copia/incolla" da word o email con caratteri che 
	 * non generano problemi.
	 * @author Marco Damilano - 27/gen/2016
	 * */
	public static String sostituisciCaratteriStrani(String strDaConv){
		String ret = strDaConv;
		
		ret = ret.replaceAll("â","'");
		ret = ret.replaceAll("âE","'");
		ret = ret.replaceAll("Ã©","é");
		
		ret = ret.replaceAll("","'");
		ret = ret.replaceAll("&#8216;","'");
		ret = ret.replaceAll("‘","'");

		ret = ret.replaceAll("","'");
		ret = ret.replaceAll("&#8217;","'");
		ret = ret.replaceAll("’","'");
		ret = ret.replaceAll("ʼ","'");

		ret = ret.replaceAll("","\"");
		ret = ret.replaceAll("&#8220;","\"");
		ret = ret.replaceAll("“","\"");

		ret = ret.replaceAll("","\"");
		ret = ret.replaceAll("&#8221;","\"");
		ret = ret.replaceAll("”","\"");

		ret = ret.replaceAll("", "-");
		ret = ret.replaceAll("", "-");

		ret = ret.replaceAll("Ø", "-");
		ret = ret.replaceAll("", "E");

		// replace ESTREMO - carattere x tutti
		ret = ret.replaceAll(""," ");

		ret = ret.replaceAll("","");
		
		ret = ret.replaceAll("Ř", "R");

		return ret;
	}

	/**
	 * Funzione che sostituisce caratteri accentati maiuscoli. 
	 * L'AS400, se si scrive un accentata in un campo che ammette solo il maiuscolo, scrive il carattere 
	 * accentato in maiuscolo (Es.: À) 
	 * @author Maurizio Rosso - 09/giu/2017
	 * */
	public static String sostituisciAccentatiMaiuscoli(String strDaConv){
		String ret = strDaConv;
		
		ret = ret.replaceAll("À","A'");
		ret = ret.replaceAll("È","E'");
		ret = ret.replaceAll("É","E'");
		ret = ret.replaceAll("Ì","I'");
		ret = ret.replaceAll("Ò","O'");
		ret = ret.replaceAll("Ù","U'");
		
		
		return ret;
	}

	/**
	 * Funzione che sostituisce caratteri accentati maiuscoli. 
	 * L'AS400, se si scrive un accentata in un campo che ammette solo il maiuscolo, scrive il carattere 
	 * accentato in maiuscolo (Es.: À) 
	 * @author Luca Tonello - 30/gen/2021
	 * */
	public static String sostAccentatiMaiuscoliNoApice(String strDaConv){
		String ret = strDaConv;
		
		ret = ret.replaceAll("À","A");
		ret = ret.replaceAll("È","E");
		ret = ret.replaceAll("É","E");
		ret = ret.replaceAll("Ì","I");
		ret = ret.replaceAll("Ò","O");
		ret = ret.replaceAll("Ù","U");
		
		
		return ret;
	}


	/**
	 * Restituice un vettore 
	 * <br><br> posizione 1 - natura, 
	 * <br><br> posizione 2 - mastro, 
	 * <br><br> posizione 3 - gruppo, 
	 * <br><br> posizione 4 - conto.
	 * @author Alexandra Burlacu - Oct 20, 2015
	 */
	public static Vector<String> splitCPDC7conNatura (String CPDC) {
		Vector<String> vCPDC = new Vector<String>();

		if(!FrmStringa.strVoid(CPDC)) {
			String natura = FrmStringa.subStrByLen(CPDC, 0, 1);
			String mastro = FrmStringa.subStrByLen(CPDC, 1, 3);
			String gruppo = FrmStringa.subStrByLen(CPDC, 3, 5);
			String conto = FrmStringa.subStrByLen(CPDC, 5, 7);

			vCPDC.add(natura);
			vCPDC.add(mastro);
			vCPDC.add(gruppo);
			vCPDC.add(conto);	
		}

		return vCPDC;
	}
	
	/**
	 * Restituice un vettore da il conto lungo 13
	 * <br><br> posizione 0 - natura, 
	 * <br><br> posizione 1 - mastro, 
	 * <br><br> posizione 2 - gruppo, 
	 * <br><br> posizione 3 - conto.
	 * <br><br> posizione 4 - sotto conto.
	 * @author Rosso Ivan - 28/mar/2019
	 */
	public static Vector<String> splitCPDC13 (String CPDC) {
		Vector<String> vCPDC = new Vector<String>();

		if(!FrmStringa.strVoid(CPDC)) {
			String natura = FrmStringa.subStrByLen(CPDC, 0, 1);
			String mastro = FrmStringa.subStrByLen(CPDC, 1, 3);
			String gruppo = FrmStringa.subStrByLen(CPDC, 3, 5);
			String conto = FrmStringa.subStrByLen(CPDC, 5, 7);
			String sottoConto = FrmStringa.subStrByLen(CPDC, 7, 13);

			vCPDC.add(natura);
			vCPDC.add(mastro);
			vCPDC.add(gruppo);
			vCPDC.add(conto);	
			vCPDC.add(sottoConto);
		}

		return vCPDC;
	}

	/**
	 * Ritorna la stringa tutta in lower case tranne la prima lettera di ogni parola
	 * @author Alessandro Rossi - 09/dic/2015
	 */
	public static String upperCaseFirstLetterEveryWord(String str){
		String strRet = "";
		if (str!=null && !str.trim().equals("")) {
			str = str.toLowerCase();
			String[] arrayApp = str.split(" ");
			if (arrayApp.length>0) {
				String primoCarattere = "";
				for (int i=0; i<arrayApp.length; i++) {
					if (arrayApp[i].length()>0){
						primoCarattere = arrayApp[i].substring(0, 1);
						strRet += primoCarattere.toUpperCase();
						if (arrayApp[i].length()>1) {
							strRet += arrayApp[i].substring(1, arrayApp[i].length());
						}
						strRet += " ";
					}
				}
				strRet = strRet.trim();
			}
		}
		return strRet;
	}
	/**
	 * Ritorna la stringa tutta in lower case tranne la prima lettera di ogni parola
	 * @author Alessandro Rossi - 09/dic/2015
	 */
	public static String prefixTmpFile(String iCodSocieta, String iIdUtenteADV){
		return iCodSocieta + "_" + iIdUtenteADV + "_";
	}
	
	/**
     * Ritorna la stringa da database utf8
     * @author Davide Sponselli - 16/apr/2018
     */
	public static String getStringResultFromUTF8(ResultSet rsSql, String columnName){
	  String value = "";
	  try {
	    value = getStringFromUTF8(rsSql.getString(columnName));         
	  } catch (SQLException e) {
	    e.printStackTrace();
	  }
	  return value;
	}
	
	public static String getStringFromUTF8(String stringa){
		  String value = stringa
		        .replaceAll("Ã¨", "è")
		        .replaceAll("Ã©", "é")
		        .replaceAll("Ã¬", "ì")
		        .replaceAll("Ã²", "ò")
		        .replaceAll("Ã¹", "ù")
		        .replaceAll("Ã", "à");           

		  return value;
	}

	/**
	 * Formatta il numero di Carta di Credito passato in input
	 * @param numeroCarta
	 * @return
	 */
	public static String formattaNumeroCdC(String numeroCarta) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < numeroCarta.length(); i++) {
			if (i % 4 == 0 && i != 0) {
				result.append(" ");
			}
			result.append(numeroCarta.charAt(i));
		}
		return result.toString();
	}

	/**
	 * Verifica se la stringa contiene solo caratteri e cifre
	 * @return true -> controllo ok, false viceversa
	 * @author Rossi Alessandro - 02/nov/2017
	 */
	public static boolean checkStringSoloCifreCaratteri(String stringDaValidare){
		return gestCheckStringSoloCifreLettere(1, stringDaValidare);
	}

	/**
	 * Verifica se la stringa contiene solo caratteri
	 * @return true -> controllo ok, false viceversa
	 * @author Rossi Alessandro - 08/gen/2018
	 */
	public static boolean checkStringSoloCaratteri(String stringDaValidare){
		return gestCheckStringSoloCifreLettere(2, stringDaValidare);
	}

	/**
	 * dato un String[], riduce gli elementi presenti - utile x eliminare i primi o gli ultimi elementi di un String[] <br>
	 * (copiato da internet)
	 * @author Marco Damilano - 27/gen/2021
	 */
	public static String[] sliceArray(String[] arrayToSlice, int startIndex, int endIndex) throws ArrayIndexOutOfBoundsException {
	    if (startIndex < 0)
	        throw new ArrayIndexOutOfBoundsException("Wrong startIndex = " + startIndex);
	    if (endIndex >= arrayToSlice.length)
	        throw new ArrayIndexOutOfBoundsException("Wrong endIndex = " + endIndex);

	    if (startIndex > endIndex) { // Then swap them!
	        int x = startIndex;
	        startIndex = endIndex;
	        endIndex = x;
	    }

	    ArrayList<String> newArr = new ArrayList();
	    Collections.addAll(newArr, arrayToSlice);
	    for (int i = 0; i < arrayToSlice.length; i++) {
	        if (!(i >= startIndex && i <= endIndex)) // If not with in the start & end indices, remove the index
	            newArr.remove(i);
	    }
	    return newArr.toArray(new String[newArr.size()]);
	}
	
	/**
	 * Verifica se la stringa contiene solo cifre
	 * @return true -> controllo ok, false viceversa
	 * @author Rossi Alessandro - 08/gen/2018
	 */
	public static boolean checkStringSoloCifre(String stringDaValidare){
		return gestCheckStringSoloCifreLettere(3, stringDaValidare);
	}

	/**
	 * Verifica se la stringa contiene solo cifre. <br>
	 * @param controllo: <br>
	 * &nbsp;1 -> devono esserci solo caratteri e cifre; <br>
	 * &nbsp;2 -> devono esserci solo caratteri; <br>
	 * &nbsp;3 -> devono esserci solo cifre; <br>
	 * @return true -> controllo ok, false viceversa
	 * @author Rossi Alessandro - 08/gen/2018
	 */
	private static boolean gestCheckStringSoloCifreLettere(int controllo, String stringDaValidare){
		boolean controlloOk = true;
		final String regex;
		if (controllo==1){
			// solo caratteri e cifre
			regex = "[^0-9a-zA-Z]";
		} else if (controllo==2){
			// solo caratteri
			regex = "[^a-zA-Z]";
		} else {
			// solo cifre
			regex = "[^0-9]";
		}
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(stringDaValidare);
		if (matcher.find()){
			controlloOk = false;
		}
		return controlloOk;
	}
	
	public static String getFormatMD5(String value) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(value.getBytes());
        
        byte byteData[] = md.digest();
 
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

    	return sb.toString();
	}
	
	public static String getPwdCasuale(){
		final String ALPHABET1 = "0123456789";
		final String ALPHABET2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String ALPHABET3 = "abcdefghijklmnopqrstuvwyz";
		final String ALPHABET4 = "$!@*-.+^";

	    Random rnd = new Random(System.currentTimeMillis());
	    final int LENGHT0 = 10;

	    StringBuilder sb = new StringBuilder(LENGHT0);
	    
	    Random random = new Random();
	    int randomInt = 0;
        for (int i = 0; i < LENGHT0; i++) {
        	randomInt = random.nextInt(100);
        	if(randomInt>=0 && randomInt<=29){
				sb.append(ALPHABET1.charAt(rnd.nextInt(ALPHABET1.length())));
        	} else if(randomInt>=30 && randomInt<=59){
				sb.append(ALPHABET2.charAt(rnd.nextInt(ALPHABET2.length())));
    		} else if(randomInt>=60 && randomInt<=89){
				sb.append(ALPHABET3.charAt(rnd.nextInt(ALPHABET3.length())));
			//dato un peso minore ai caratteri speciali
    		} else if(randomInt>=90 && randomInt<=99){
				sb.append(ALPHABET4.charAt(rnd.nextInt(ALPHABET4.length())));
			}
        }
        return sb.toString();
	}
	
	public static String getAlphaNumericString(int n) {

		// chose a Character random from this String
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (alphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(alphaNumericString.charAt(index));
		}

		return sb.toString();
	}
	
	/**
	 * Sostituisce caratteri strani
	 * @param strDaConv
	 * @author Simona Pansa - 21/08/2018
	 */
	public static String sostituisciCaratteriStraniMaiuscoli(String strDaConv){
		String ret = strDaConv;
		ret = ret.replaceAll("Ã","Á");
		ret = ret.replaceAll("Ã","À");
		ret = ret.replaceAll("Ã","È");
		ret = ret.replaceAll("Ã","É");
		ret = ret.replaceAll("Ã","Ì");
		ret = ret.replaceAll("Ã","Í");
		ret = ret.replaceAll("Ã","Ò");
		ret = ret.replaceAll("Ã","Ó");
		ret = ret.replaceAll("Ã","Ù");
		ret = ret.replaceAll("Ã","Ú");
		ret = ret.replaceAll("Ã","Ý");
		return ret;
	}
	
	
	public static Vector<String> getElementsIBAN(String IBAN) {
		Vector<String> elementsIBAN = new Vector<String>();
		String codPaese = null;
		String chekcDigit = null;
		String cin = null;
		String abi = null;
		String cab = null;
		String contoCorrente = null;
		if(IBAN.length()==27){
			codPaese = IBAN.substring(0, 2);
			chekcDigit = IBAN.substring(2, 4);
			cin = IBAN.substring(4, 5);
			abi = IBAN.substring(5, 10);
			cab = IBAN.substring(10, 15);
			contoCorrente = IBAN.substring(15);
			
			elementsIBAN.add(codPaese);
			elementsIBAN.add(chekcDigit);
			elementsIBAN.add(cin);
			elementsIBAN.add(abi);
			elementsIBAN.add(cab);
			elementsIBAN.add(contoCorrente);
		}
		return elementsIBAN;
	}
	
	/**
	 * allinea natura mastro gruppo conto in formato 1 2 2 2
	 * @param natura
	 * @param mastro
	 * @param gruppo
	 * @param conto
	 * @return natura + mastro + gruppo + conto
	 */
	static public String allineaNaturaMastroGruppoConto1222( String natura, String mastro, String gruppo, String conto ){
		String retVal;

		natura = FrmStringa.toUpperNotNull(natura);
		mastro = FrmStringa.toUpperNotNull(mastro);
		gruppo = FrmStringa.toUpperNotNull(gruppo);
		conto = FrmStringa.toUpperNotNull(conto);

		retVal = FrmStringa.allineaDx(natura, 1, false );
		retVal += FrmStringa.allineaDx(mastro, 2, false );
		retVal += FrmStringa.allineaDx(gruppo, 2, false );
		retVal += FrmStringa.allineaDx(conto, 2, false );

		return retVal;
	}
	
	/**
	 * allinea mastro gruppo conto in formato 2 2 2
	 * @param mastro
	 * @param gruppo
	 * @param conto
	 * @return natura + mastro + gruppo + conto
	 */
	static public String allineaMastroGruppoConto222( String mastro, String gruppo, String conto ){
		String retVal = null;

		mastro = FrmStringa.toUpperNotNull(mastro);
		gruppo = FrmStringa.toUpperNotNull(gruppo);
		conto = FrmStringa.toUpperNotNull(conto);

		retVal = FrmStringa.allineaDx(mastro, 2, false );
		retVal += FrmStringa.allineaDx(gruppo, 2, false );
		retVal += FrmStringa.allineaDx(conto, 2, false );

		return retVal;
	}
	
	public static int getIntFromString(int valueLength, String value) {
		return Integer.valueOf(getValueFromString(valueLength, value));
	}
	
	public static String getValueFromString(int valueLength, String value) {
		return value.substring(valueLength);
	}
	
    /**
     * Restituisce una stringa dove i caratteri speciali HTML vengono tradotti da codice a carattere<br>
     * Esempio: &#8363;	diventa	€<br>
     * @author Enrico Turco - 04/gen/2023
     * */
    public static String replace_specialChars_HTML(String html_str){
    	String[] special_chars_codes = {
    			"&#180;", 			// 0
    			"&amp;", 			// 1
    			"&#162;",  			// 2
    			"&#169;",  			// 3
    			"&#8224;",  		// 4
    			"&deg;", 			// 5
    			"&#247;",  			// 6
    			"&euro;",  			// 7
    			"&#189;",  			// 8
    			"&#188;",  			// 9
    			"&#190;",  			// 10
    			"&#171;", 			// 11
    			"&#215;",  			// 12
    			"&#177;",  			// 13
    			"&#8220;",  		// 14
    			"&#8221;",  		// 15
    			"&#174;",  			// 16
    			"&#187;",  			// 17
    			"&#185;",  			// 18
    			"&#8482;",  		// 19
    			"&nbsp;", 			// 20
    			"&quot;", 			// 21
    			"&pound;", 			// 22
    			"&#39;", 			// 23
    			"&igrave;", 		// 24
    			"&egrave;", 		// 25
    			"&eacute;", 		// 26
    			"&ograve;", 		// 27
    			"&ccedil;", 		// 28
    			"&agrave;", 		// 29
    			"&ugrave;", 		// 30
    			"&sect;", 			// 31
    			"&rsquo;"			// 32
    	};
    	String[] special_chars = {
    			"´", 				// 0
    			"&", 				// 1
    			"¢",  				// 2
    			"©",  				// 3
    			"†",  				// 4
    			"°",  				// 5
    			"÷",  				// 6
    			"€",  				// 7
    			"½",  				// 8
    			"¼",  				// 9
    			"¾", 				// 10
    			"«",   				// 11
    			"x",  				// 12
    			"±",  				// 13
    			"“",  				// 14
    			"”",  				// 15
    			"®",  				// 16
    			"»",  				// 17
    			"¹",  				// 18
    			"™",  				// 19
    			" ", 				// 20
    			"\"", 				// 21
    			"£", 				// 22
    			"'", 				// 23
    			"ì", 				// 24
    			"è", 				// 25
    			"é", 				// 26
    			"ò", 				// 27
    			"ç", 				// 28
    			"à", 				// 29
    			"ù", 				// 30
    			"§", 				// 31
    			"'"					// 32
    	};
    	for(int i = 0; i < special_chars_codes.length; i++){
    		html_str = html_str.replace(special_chars_codes[i].trim(), special_chars[i]);
    	}
    	return html_str;
    }

    //Per ora non gestito necessita di icu4j-4.0.1.jar per ora non in lib
//  public static String trasformaCarattereNonLatino(String frase) {
//  	Transliterator transliterator = Transliterator.getInstance("Any-Latin");
//     	return transliterator.transliterate(frase);
//	}
}