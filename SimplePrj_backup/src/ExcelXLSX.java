

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * Genera/Aggiorna Files Excel rendiconti per ogni unità operativa.<br>
 * ATTENZIONE: il nome dello sheet può essere al max 31 char, altrimenti segnala "Si è verificato un problema con una parte del contenuto..."
 */
public class ExcelXLSX {
	private XSSFWorkbook wb = null;
	
	private int[] larghezzeColonne;
	
	private XSSFCellStyle stileTestoNormale = null;
	private XSSFCellStyle stileTestoGrassetto = null;
	private XSSFCellStyle stileNumeroInteroNormale = null;
	private XSSFCellStyle stileNumeroInteroGrassetto = null;
	private XSSFCellStyle stileNumeroRealeNormale = null;
	private XSSFCellStyle stileNumeroRealeGrassetto = null;
	private XSSFCellStyle stileNumeroReale5DecNormale = null;
	private XSSFCellStyle stileDataNormale = null;
	private XSSFCellStyle stileCambioNormale = null;

	protected static final String[] indiceColonnaAlfa = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	/** #,##0.00;-#,##0.00 * */
	public static final String formatoNumRealeConSegno = "#,##0.00;-#,##0.00"; 
	
	/**
	 *  Costruttore NUOVO workbook
	 */
	public ExcelXLSX() {
		// genera workbook
		wb = new XSSFWorkbook();
		// genera gli stili standard
		generaStiliStandard();
	}
	
	/**
	 *  Costruttore workbook da file esistente.<br>
	 */
	public ExcelXLSX(String nomeFileConPath) throws IOException {
		// genera workbook
		FileInputStream fis = new FileInputStream(nomeFileConPath);
		wb = new XSSFWorkbook(fis);
		// genera gli stili standard
		generaStiliStandard();
	}
	
	/**
	 * Genera gli stili standard
	 * @author Maurizio Rosso - 01/giu/09
	 */	
	private void generaStiliStandard() {
		stileTestoNormale = generaStile(XSSFCellStyle.ALIGN_LEFT, null, (short)0, null);
		stileTestoGrassetto = generaStile(XSSFCellStyle.ALIGN_LEFT, null, (short)0, generaFont(null, 0, true, (short)0));
		stileNumeroInteroNormale = generaStile(XSSFCellStyle.ALIGN_RIGHT, null, (short)0, null);
		stileNumeroInteroGrassetto = generaStile(XSSFCellStyle.ALIGN_RIGHT, null,(short)0, generaFont(null, 0, true, (short)0));
		stileNumeroRealeNormale = generaStile(XSSFCellStyle.ALIGN_RIGHT, Excel_OV.formatoNumRealeConSegno,(short)0, null);
		stileNumeroReale5DecNormale = generaStile(XSSFCellStyle.ALIGN_RIGHT, Excel_OV.formatoNumReale5DecConSegno,(short)0, null);
		stileNumeroRealeGrassetto = generaStile(XSSFCellStyle.ALIGN_RIGHT, Excel_OV.formatoNumRealeConSegno, (short)0, generaFont(null, 0, true, (short)0));
		stileDataNormale = generaStileData(XSSFCellStyle.ALIGN_LEFT, null, (short)0, null);
		stileCambioNormale = generaStile(XSSFCellStyle.ALIGN_RIGHT, Excel_OV.formatoCambio,(short)0, null);
	}
	
	/**
	 * Genera un nuovo stile.<br>
	 * @param allineamento - Es.: XSSFCellStyle.ALIGN_LEFT
	 * @param formato - vedi Excel.formatoNumRealeConSegno
	 * @param coloreSfondo  - Es.: XSSFColor.LIGHT_BLUE.index
	 * @param font - XSSFFont
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public XSSFCellStyle generaStile(short allineamento,String formato, short coloreSfondo,XSSFFont font) {
		// genera nuovo stile sul workbook
		XSSFCellStyle stile = this.getWb().createCellStyle();
	    
		// allineamento
		stile.setAlignment(allineamento);
	    
		// formato cella
		if(formato!=null && !formato.equals("")) {
		    XSSFDataFormat format = wb.createDataFormat();
			stile.setDataFormat(format.getFormat(formato));
		}
	    
		// colore sfondo
		if (coloreSfondo>0) {
			stile.setFillForegroundColor(coloreSfondo);
	        stile.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		}

		// font 
		if (font!=null) {
			stile.setFont(font);
		}
		
	    return stile;
	
	}

	/**
	 * Genera un nuovo stile data<br>
	 * @author Rossi Alessandro - 08/ago/2018
	 */
	public XSSFCellStyle generaStileData(short allineamento,String formato, short coloreSfondo,XSSFFont font) {
		// genera nuovo stile sul workbook
		XSSFCellStyle stile = generaStile(allineamento, formato, coloreSfondo, font);
        CreationHelper createHelper = getWb().getCreationHelper();
        stile.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
	    return stile;
	
	}
		
	/**
	 * Genera un nuovo font.<br>
	 * @param nomeFont - Es.: "Arial"
	 * @param dimensione - dimensione in punti del carattere (moltiplicare per 20 la dimensione classica)
	 * @param isGrassetto
	 * @param coloreCarattere - Es.: XSSFColor.RED.index
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public XSSFFont generaFont(String nomeFont, int dimensione, boolean isGrassetto, short coloreCarattere) {
		return generaFont(nomeFont, dimensione, isGrassetto, false, coloreCarattere);
	}

	/**
	 * Genera un nuovo font.<br>
	 * @param nomeFont - Es.: "Arial"
	 * @param dimensione - dimensione in punti del carattere (moltiplicare per 20 la dimensione classica)
	 * @param isGrassetto
	 * @param isCorsivo
	 * @param coloreCarattere - Es.: XSSFColor.RED.index
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public XSSFFont generaFont(String nomeFont, int dimensione, boolean isGrassetto, boolean isCorsivo, short coloreCarattere) {
		XSSFFont font = this.getWb().createFont();

		// gestione font: nome
		if (nomeFont!=null && !nomeFont.equals("")) {
			font.setFontName(nomeFont);
		}
		// gestione font: dimensione
		if (dimensione >0) {
			dimensione = dimensione * 20;
			font.setFontHeight((short)dimensione);
		}
		
		// gestione font: grassetto
		if (isGrassetto) {
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		}
		
		// gestione font: corsivo
		if (isCorsivo) {
			font.setItalic(true);
		}

		// gestione font: colore carattere
		if (coloreCarattere>0) {
			font.setColor(coloreCarattere);
		}

		return font;
		
	}
	/**
	 * imposta bordi su uno stile
	 */
	public XSSFCellStyle impostaBordi(XSSFCellStyle stile, short dimTop, short dimBottom, short dimLeft, short dimRight) {
		stile.setBorderTop(dimTop);
		stile.setBorderBottom(dimBottom);
		stile.setBorderLeft(dimLeft);
		stile.setBorderRight(dimRight);
		return stile;
	}
	
	/**
	 * Crea cella unione
	 */
	public void creaCellaUnione (XSSFSheet foglio, XSSFRow rigaIniziale,  int numCellaIniziale, int numRigaFinale, int numCellaFinale, XSSFCellStyle stile, String valore){


        XSSFCell cell = rigaIniziale.createCell(numCellaIniziale);
        
        // eventuale stile
        if (stile!=null){
    	    cell.setCellStyle(stile);
        }
        
        // eventuale valore
        if (valore!=null){
        	cell.setCellValue(valore);
        }

        // unione celle
        foglio.addMergedRegion(new CellRangeAddress(rigaIniziale.getRowNum(),numRigaFinale,numCellaIniziale,numCellaFinale));
	}
	
	/**
	 * Genera riga intesatazione sul foglio
	 */
	protected void generaRigaIntestazione(XSSFSheet sheet, String[] intestazioni){
		generaRigaIntestazione(sheet, 0, intestazioni);
	}
	/**
	 * Genera riga intesatazione sul foglio
	 */
	protected void generaRigaIntestazione(XSSFSheet sheet, int numRiga, String[] intestazioni){
        // genera array per memorizzare le larghezze max di ogni colonna
        larghezzeColonne = new int [intestazioni.length];
         
        // stile e font intestazione
        XSSFFont fontRigaIntestaz = generaFont(null, 0, true, HSSFColor.WHITE.index);
        XSSFCellStyle stileRigaIntestaz = generaStile(XSSFCellStyle.ALIGN_LEFT, null, HSSFColor.LIGHT_BLUE.index, fontRigaIntestaz);
        
        // genera riga intestazione
        XSSFRow row = sheet.createRow((short)numRiga);
        // gestione per non far uscire il testo dalla riga
        float heightRowDefault = row.getHeightInPoints();
        if(fontRigaIntestaz.getFontHeightInPoints() > heightRowDefault){
        	heightRowDefault = fontRigaIntestaz.getFontHeightInPoints();
        }
        row.setHeightInPoints(heightRowDefault);
        
        // genera colonne intestazione
        XSSFCell cell = null;
        for (int i=0; i<intestazioni.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(intestazioni[i]);
            cell.setCellStyle(stileRigaIntestaz);
            // salvataggio larghezza massima colonna
            salvaLarghezza(i, intestazioni[i].length());
        }
 		
	}

	/**
	 * Salva larghezza massima colonna
	 */
	protected void salvaLarghezza(int numColonna, int numCaratteri){

		int larghezzaColonna = numCaratteri * 300;	
		if (larghezzaColonna>larghezzeColonne[numColonna]) 	{
			larghezzeColonne[numColonna]=larghezzaColonna;
		}
 	}

	/**
	 * Ridimensiona colonne di un foglio
	 */
	protected void ridimensionaColonne(XSSFSheet sheet){
         
         for (int i=0; i<larghezzeColonne.length; i++) {
            sheet.setColumnWidth(i, larghezzeColonne[i]); 			
         }
 		
 	}
	
	/**
	 * Genera riga subtotali/totali
	 */
	protected void generaRigaTotali(XSSFSheet sheet, int numRigaDaCreare,int numRigaInizioSubTot, String testoPrimaColonna, int[] indiciColonneTotali){
		generaRigaTotali(sheet, numRigaDaCreare, numRigaInizioSubTot, numRigaDaCreare, testoPrimaColonna, false, indiciColonneTotali);
	}

	/**
	 * Genera riga subtotali/totali
	 * @param titoloInColonnaUnione:<br>
	 * 		  false: il titolo del totale viene scritto in prima colonna, allineato a sinistra<br>
	 * 		  true:  il titolo viene scritto in cella unione che va dalla prima 
	 * 				 a quella che precede il primo totale, allineato a destra
	 */
	protected void generaRigaTotali(XSSFSheet sheet, int numRigaDaCreare,int numRigaInizioSubTot,int numRigaFineSubTot, String testoPrimaColonna, Boolean titoloInColonnaUnione, int[] indiciColonneTotali){
		generaRigaTotali(sheet, numRigaDaCreare, numRigaInizioSubTot, numRigaDaCreare, testoPrimaColonna, false, indiciColonneTotali,stileTestoGrassetto,stileNumeroRealeGrassetto,true);
	}

	/**
	 * Genera riga subtotali/totali
	 * @param titoloInColonnaUnione:<br>
	 * 		  false: il titolo del totale viene scritto in prima colonna, allineato a sinistra<br>
	 * 		  true:  il titolo viene scritto in cella unione che va dalla prima 
	 * 				 a quella che precede il primo totale, allineato a destra
	 */
	protected void generaRigaTotali(XSSFSheet sheet, int numRigaDaCreare,int numRigaInizioSubTot,int numRigaFineSubTot, String testoPrimaColonna, Boolean titoloInColonnaUnione, int[] indiciColonneTotali, XSSFCellStyle stileTesto, XSSFCellStyle stileNumero,boolean larghColonneGestitaManualmente){
		XSSFRow rigaTotali = sheet.createRow(numRigaDaCreare);
        XSSFCell cell = null;
        
        if (!titoloInColonnaUnione){
            // descrzione totale in prima colonna
            cell = rigaTotali.createCell(0);
            cell.setCellStyle(stileTesto);
            cell.setCellValue(testoPrimaColonna);
            if (larghColonneGestitaManualmente){
            	salvaLarghezza(0, testoPrimaColonna.length());
            }
        } else {
            // descrizione totale in in cella unione che va dalla prima a quella che precede il primo totale
        	creaCellaUnione(sheet, rigaTotali, 0, rigaTotali.getRowNum(), indiciColonneTotali[0]-1, generaStile(XSSFCellStyle.ALIGN_RIGHT, null, (short)0, generaFont(null, 0, true, (short)0)), testoPrimaColonna);
        }
        
        // colonne totali
        for (int c=0; c<indiciColonneTotali.length; c++) {
        	String daCella = indiceColonnaAlfa[indiciColonneTotali[c]] + String.valueOf(numRigaInizioSubTot);
        	String aCella = indiceColonnaAlfa[indiciColonneTotali[c]] + String.valueOf(numRigaFineSubTot); 
            cell = rigaTotali.createCell(indiciColonneTotali[c]);
            cell.setCellStyle(stileNumero);
            cell.setCellFormula("SUBTOTAL(9,"+daCella+":"+aCella+")");
            // salvataggio larghezza massima colonna
            if (larghColonneGestitaManualmente){
            	salvaLarghezza(indiciColonneTotali[c], 12);
            }
        }

	}
	
	/**
	 * Calcola e restituisce il valore di una formula.<br>
	 * @return valore - numero reale con precisione due
	 */
	protected double valutaFormula(XSSFWorkbook wb, XSSFSheet foglio, int numRiga, int numColonna) {
		double valore = 0.0;
			
		XSSFFormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator(wb);
		//XSSFFormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator( foglio, wb);
        XSSFRow riga = foglio.getRow(numRiga);
	    //formulaEvaluator.setCurrentRow(riga);
        CellValue cellValue = formulaEvaluator.evaluate(riga.getCell(numColonna));
	    valore = cellValue.getNumberValue();
	
	    return valore;
	}
	
	/**
	 * Scrive file su disco.<br>
	 * @author Maurizio Rosso - 01/giu/09
	 * @throws IOException 
	 */
	protected void scriviFile(String nomeFileConPath) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(nomeFileConPath);
	    this.getWb().write(fileOut);
	    fileOut.close();

	}
	
	/**
	 * legge contenuto cella e restituisce string
	 */
	protected String leggiString (Cell cella){
		String risultato = "";
		
		try {
			switch (cella.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				risultato = cella.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				risultato = String.valueOf(((Double)cella.getNumericCellValue()).longValue());
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return risultato;
	}

	/**
	 * legge contenuto cella di tipo date e restituisce string
	 */
	protected String leggiDate (Cell cella){
		String risultato = "";

		if (cella.getCellType() == XSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cella)) {
			Date dateValue = cella.getDateCellValue();
			risultato = dateValue.toString();
		}
		
		return risultato;
	}
	
	/**
	 * legge contenuto cella e restituisce long
	 */
	protected long leggiLong(XSSFCell cella){
		long risultato = 0;
		try {
			switch (cella.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				String strValoreCella = cella.getStringCellValue();
				risultato = ((Double)FrmNumero.formatNumber(strValoreCella)).longValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				risultato = ((Double)cella.getNumericCellValue()).longValue();
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return risultato;
	}

	/**
	 * legge contenuto cella e restituisce int
	 */
	protected int leggiInt (XSSFCell cella){
		int risultato = 0;
		try {
			switch (cella.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				String strValoreCella = cella.getStringCellValue();
				risultato = ((Double)FrmNumero.formatNumber(strValoreCella)).intValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				risultato = ((Double)cella.getNumericCellValue()).intValue();
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return risultato;
	}
	
	/**
	 * legge contenuto cella e restituisce int
	 */
	protected double leggiDouble (XSSFCell cella){
		double risultato = 0.0;
		try {
			switch (cella.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				String strValoreCella = cella.getStringCellValue();
				risultato = FrmNumero.formatNumber(strValoreCella);
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				risultato = cella.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				risultato = cella.getNumericCellValue();
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return risultato;
	}
	
    /**
     * Calcola coordinata colonna cella Excel da n° colonna numerico
     * @param col - indice colonna
     * @return String coordinata "A, B, ... AA, AB ecc."
     * @author Rosa Marco - 19/set/2011
     */
	public static String calcolaColonnaExcel(int col){
		String ret = "";
		int asc1 = 0;
		int asc2 = 0;
		String car1 = "";	
		String car2 = "";
		if (col > 26){
			asc1 = col/27;
			asc2 = col-asc1*26;
			if (asc2>26){
				asc1++;
				asc2=1;
			}
		} else {
			asc1 = col;
		}
		if (asc1>0){
			car1 = Character.toString((char)(asc1+64));
		}
		if (asc2>0){
			car2 = Character.toString((char)(asc2+64));
		}
		ret = car1 + car2;
		
		return ret.trim();
	}
	
    /**
     * Calcola numero colonna Excel da coordinata colonna cella Excel Alfanumerica
     * @param String coordinata "A, B, ... AA, AB ecc."
     * @return col - indice colonna
     * @author Gianluca Bruno - 28/feb/2012
     */
	public static int calcolaIndiceColonnaExcel(String colExc){
		int ret = 0;
		colExc=colExc.trim().toUpperCase();
		if (!colExc.equals("")){
			int asc1 = 0;
			int asc2 = 0;
			if (colExc.length()==1){
				asc2=(int)colExc.charAt(0);
			} else {
				asc1=(int)colExc.charAt(0);
				asc2=(int)colExc.charAt(1);
			}
			if (asc1!=0){
				ret += (asc1-64)*26;
			}
			ret += asc2-64;
		}
		
		return ret;
	}
	
	/**
	 * Recupero la data corretta.
	 * Se la cella e' di tipo testo e il campo contiente un valore del genere 10/10/2020 
	 * non faccio nulla restituisco la data.
	 * Se la cella e' di tipo testo/numero e il campo contiente un valore del genere 20201010 
	 * converto il valore in data: 10/10/2020 e la restituisco.
	 * @param cella
	 * @return data formatta del tipo 10/10/2020
	 */
	protected String recuperaData(XSSFCell cella){
		// data documento 
		String data = "";
		boolean isImpostataDataDocumento = false;
		switch (cella.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				data = leggiString(cella);
				if(data.contains("/")){
					String[] vData = data.split("/");
					if(vData.length == 3){
						isImpostataDataDocumento = true;
					}
				}
				
				// se la data è vuota non devo verificare se la cella è di tipo data
				if(FrmStringa.strVoidOrNull(data)){
					isImpostataDataDocumento = true;
				}
				break;
		}
		
		// verifico se la cella e' di tipo data
		if(!isImpostataDataDocumento && DateUtil.isCellDateFormatted(cella)){
			if(!FrmStringa.strVoidOrNull(cella.getDateCellValue())){
				SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy"); 
				data = formattedDate.format(cella.getDateCellValue().getTime());
				isImpostataDataDocumento = true;
			}
		}
		
		if(!isImpostataDataDocumento){
			data = null;
		}
		
		return data;
	}
	
	
	/**
	 * @author Maurizio Rosso - 22/mag/09
	 */
	public int[] getLarghezzeColonne() {
		return larghezzeColonne;
	}

	/**
	 * @author Maurizio Rosso - 22/mag/09
	 */
	public void setLarghezzeColonne(int[] larghezzeColonne) {
		this.larghezzeColonne = larghezzeColonne;
	}

	/**
	 * @author Maurizio Rosso - 22/mag/09
	 */
	public XSSFWorkbook getWb() {
		return wb;
	}

	/**
	 * @author Maurizio Rosso - 22/mag/09
	 */
	public void setWb(XSSFWorkbook wb) {
		this.wb = wb;
		// con la nuova vesione di poi gli stili sono legati al workbook:
		// rigenera genera gli stili standard
		generaStiliStandard();
	}



	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public XSSFCellStyle getStileNumeroRealeGrassetto() {
		return stileNumeroRealeGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileNumeroRealeGrassetto(XSSFCellStyle stileNumeroRealeGrassetto) {
		this.stileNumeroRealeGrassetto = stileNumeroRealeGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public XSSFCellStyle getStileNumeroRealeNormale() {
		return stileNumeroRealeNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileNumeroRealeNormale(XSSFCellStyle stileNumeroRealeNormale) {
		this.stileNumeroRealeNormale = stileNumeroRealeNormale;
	}
	
	/**
	 * @author Rosa Marco - 13/feb/2024
	 */
	public XSSFCellStyle getStileNumeroReale5DecNormale() {
		return stileNumeroReale5DecNormale;
	}

	/**
	 * @author Rosa Marco - 13/feb/2024
	 */
	public void setStileNumeroReale5DecNormale(
			XSSFCellStyle stileNumeroReale5DecNormale) {
		this.stileNumeroReale5DecNormale = stileNumeroReale5DecNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public XSSFCellStyle getStileTestoGrassetto() {
		return stileTestoGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileTestoGrassetto(XSSFCellStyle stileTestoGrassetto) {
		this.stileTestoGrassetto = stileTestoGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public XSSFCellStyle getStileTestoNormale() {
		return stileTestoNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileTestoNormale(XSSFCellStyle stileTestoNormale) {
		this.stileTestoNormale = stileTestoNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public XSSFCellStyle getStileNumeroInteroGrassetto() {
		return stileNumeroInteroGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileNumeroInteroGrassetto(
			XSSFCellStyle stileNumeroInteroGrassetto) {
		this.stileNumeroInteroGrassetto = stileNumeroInteroGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public XSSFCellStyle getStileNumeroInteroNormale() {
		return stileNumeroInteroNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileNumeroInteroNormale(XSSFCellStyle stileNumeroInteroNormale) {
		this.stileNumeroInteroNormale = stileNumeroInteroNormale;
	}

	/**
	 * Alessandro Rossi - 08/ago/2018
	 * @return the stileDataNormale
	 */
	public XSSFCellStyle getStileDataNormale() {
		return stileDataNormale;
	}

	/**
	 * Alessandro Rossi - 08/ago/2018
	 * @return the stileDataNormale
	 */
	public void setStileDataNormale(XSSFCellStyle stileDataNormale) {
		this.stileDataNormale = stileDataNormale;
	}

	/**
	 * @author Oltean Cristina - 11/ott/2018
	 */
	public XSSFCellStyle getStileCambioNormale() {
		return stileCambioNormale;
	}

	/**
	 * @author Oltean Cristina - 11/ott/2018
	 */
	public void setStileCambioNormale(XSSFCellStyle stileCambioNormale) {
		this.stileCambioNormale = stileCambioNormale;
	}

}
