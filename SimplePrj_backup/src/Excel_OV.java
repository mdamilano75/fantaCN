

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;


/**
 * Genera/Aggiorna Files Excel rendiconti per ogni unità operativa.<br>
 */
public class Excel_OV {
	private HSSFWorkbook wb = null;
	
	private int[] larghezzeColonne;
	
	private HSSFCellStyle stileTestoNormale = null;
	private HSSFCellStyle stileTestoGrassetto = null;
	private HSSFCellStyle stileNumeroInteroNormale = null;
	private HSSFCellStyle stileNumeroInteroGrassetto = null;
	private HSSFCellStyle stileNumeroRealeNormale = null;
	private HSSFCellStyle stileNumeroRealeGrassetto = null;
	private HSSFCellStyle stileCambioNormale = null;

	protected static final String[] indiceColonnaAlfa = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	/** #,##0.00;-#,##0.00 * */
	public static final String formatoNumRealeConSegno = "#,##0.00;-#,##0.00";
	public static final String formatoCambio = "#,##0.0000000;"; 
	public static final String formatoNumReale5DecConSegno = "#,##0.00000;-#,##0.00000;";
	
	/**
	 *  Costruttore NUOVO workbook
	 */
	public Excel_OV() {
		// genera workbook
		this.wb = new HSSFWorkbook();
		// genera gli stili standard
		generaStiliStandard();
	}
	
	/**
	 *  Costruttore workbook da file esistente.<br>
	 */
	public Excel_OV(String nomeFileConPath) throws IOException {
		// genera workbook
		FileInputStream fis = new FileInputStream(nomeFileConPath);
		this.wb = new HSSFWorkbook(fis);
		// genera gli stili standard
		generaStiliStandard();
	}
	
	/**
	 * Genera gli stili standard
	 * @author Maurizio Rosso - 01/giu/09
	 */	
	private void generaStiliStandard() {
		stileTestoNormale = generaStile(HSSFCellStyle.ALIGN_LEFT, null, (short)0, null);
		stileTestoGrassetto = generaStile(HSSFCellStyle.ALIGN_LEFT, null, (short)0, generaFont(null, 0, true, (short)0));
		stileNumeroInteroNormale = generaStile(HSSFCellStyle.ALIGN_RIGHT, null, (short)0, null);
		stileNumeroInteroGrassetto = generaStile(HSSFCellStyle.ALIGN_RIGHT, null,(short)0, generaFont(null, 0, true, (short)0));
		stileNumeroRealeNormale = generaStile(HSSFCellStyle.ALIGN_RIGHT, Excel_OV.formatoNumRealeConSegno,(short)0, null);
		stileNumeroRealeGrassetto = generaStile(HSSFCellStyle.ALIGN_RIGHT, Excel_OV.formatoNumRealeConSegno, (short)0, generaFont(null, 0, true, (short)0));
		stileCambioNormale = generaStile(HSSFCellStyle.ALIGN_RIGHT, Excel_OV.formatoCambio,(short)0, null);
	}
	
	/**
	 * Genera un nuovo stile.<br>
	 * @param allineamento - Es.: HSSFCellStyle.ALIGN_LEFT
	 * @param formato - vedi Excel.formatoNumRealeConSegno
	 * @param coloreSfondo  - Es.: HSSFColor.LIGHT_BLUE.index
	 * @param font - HSSFFont
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public HSSFCellStyle generaStile(short allineamento,String formato, short coloreSfondo,HSSFFont font) {
		// genera nuovo stile sul workbook
		HSSFCellStyle stile = generaStile(formato, coloreSfondo, font);
		// allineamento
		stile.setAlignment(allineamento);
	    return stile;
	
	}

	public HSSFCellStyle generaStile(String formato, short coloreSfondo,HSSFFont font) {
		// genera nuovo stile sul workbook
		HSSFCellStyle stile = this.getWb().createCellStyle();
	    
		// formato cella
		if(formato!=null && !formato.equals("")) {
		    HSSFDataFormat format = wb.createDataFormat();
			stile.setDataFormat(format.getFormat(formato));
		}

		// font 
		if (font!=null) {
			stile.setFont(font);
		}

		// colore sfondo
		if (coloreSfondo>0) {
			stile.setFillForegroundColor(coloreSfondo);
	        stile.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}
	    return stile;
	}
	/**
	 * Genera un nuovo font.<br>
	 * @param nomeFont - Es.: "Arial"
	 * @param dimensione - dimensione in punti del carattere (moltiplicare per 20 la dimensione classica)
	 * @param isGrassetto
	 * @param coloreCarattere - Es.: HSSFColor.RED.index
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public HSSFFont generaFont(String nomeFont, int dimensione, boolean isGrassetto, short coloreCarattere) {
		return generaFont(nomeFont, dimensione, isGrassetto, false, coloreCarattere);
	}

	/**
	 * Genera un nuovo font.<br>
	 * @param nomeFont - Es.: "Arial"
	 * @param dimensione - dimensione in punti del carattere (moltiplicare per 20 la dimensione classica)
	 * @param isGrassetto
	 * @param isCorsivo
	 * @param coloreCarattere - Es.: HSSFColor.RED.index
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public HSSFFont generaFont(String nomeFont, int dimensione, boolean isGrassetto, boolean isCorsivo, short coloreCarattere) {
		HSSFFont font = this.getWb().createFont();

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
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
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
	public HSSFCellStyle impostaBordi(HSSFCellStyle stile, short dimTop, short dimBottom, short dimLeft, short dimRight) {
		stile.setBorderTop(dimTop);
		stile.setBorderBottom(dimBottom);
		stile.setBorderLeft(dimLeft);
		stile.setBorderRight(dimRight);
		return stile;
	}
	
	/**
	 * Crea cella unione
	 */
	public void creaCellaUnione (HSSFSheet foglio, HSSFRow rigaIniziale,  int numCellaIniziale, int numRigaFinale, int numCellaFinale, HSSFCellStyle stile, String valore){


        HSSFCell cell = rigaIniziale.createCell(numCellaIniziale);
        
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
	protected void generaRigaIntestazione(HSSFSheet sheet, int numRiga, String[] intestazioni, short bkColor){
        // genera array per memorizzare le larghezze max di ogni colonna
        larghezzeColonne = new int [intestazioni.length];
         
        // stile e font intestazione
        HSSFCellStyle stileRigaIntestaz = generaStile(HSSFCellStyle.ALIGN_LEFT, null, bkColor, generaFont(null, 0, true, HSSFColor.WHITE.index));

        // genera riga intestazione
        HSSFRow row = sheet.createRow((short)numRiga);

        // genera colonne intestazione
        HSSFCell cell = null;
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
	protected void ridimensionaColonne(HSSFSheet sheet){
         
         for (int i=0; i<larghezzeColonne.length; i++) {
            sheet.setColumnWidth(i, larghezzeColonne[i]); 			
         }
 		
 	}
	
	/**
	 * Genera riga subtotali/totali
	 */
	protected void generaRigaTotali(HSSFSheet sheet, int numRigaDaCreare,int numRigaInizioSubTot, String testoPrimaColonna, int[] indiciColonneTotali){
		generaRigaTotali(sheet, numRigaDaCreare, numRigaInizioSubTot, numRigaDaCreare, testoPrimaColonna, false, indiciColonneTotali);
	}

	/**
	 * Genera riga subtotali/totali
	 * @param titoloInColonnaUnione:<br>
	 * 		  false: il titolo del totale viene scritto in prima colonna, allineato a sinistra<br>
	 * 		  true:  il titolo viene scritto in cella unione che va dalla prima 
	 * 				 a quella che precede il primo totale, allineato a destra
	 */
	protected void generaRigaTotali(HSSFSheet sheet, int numRigaDaCreare,int numRigaInizioSubTot,int numRigaFineSubTot, String testoPrimaColonna, Boolean titoloInColonnaUnione, int[] indiciColonneTotali){
		generaRigaTotali(sheet, numRigaDaCreare, numRigaInizioSubTot, numRigaDaCreare, testoPrimaColonna, false, indiciColonneTotali,stileTestoGrassetto,stileNumeroRealeGrassetto,true);
	}

	/**
	 * Genera riga subtotali/totali
	 * @param titoloInColonnaUnione:<br>
	 * 		  false: il titolo del totale viene scritto in prima colonna, allineato a sinistra<br>
	 * 		  true:  il titolo viene scritto in cella unione che va dalla prima 
	 * 				 a quella che precede il primo totale, allineato a destra
	 */
	protected void generaRigaTotali(HSSFSheet sheet, int numRigaDaCreare,int numRigaInizioSubTot,int numRigaFineSubTot, String testoPrimaColonna, Boolean titoloInColonnaUnione, int[] indiciColonneTotali, HSSFCellStyle stileTesto, HSSFCellStyle stileNumero,boolean larghColonneGestitaManualmente){
		HSSFRow rigaTotali = sheet.createRow(numRigaDaCreare);
        HSSFCell cell = null;
        
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
        	creaCellaUnione(sheet, rigaTotali, 0, rigaTotali.getRowNum(), indiciColonneTotali[0]-1, generaStile(HSSFCellStyle.ALIGN_RIGHT, null, (short)0, generaFont(null, 0, true, (short)0)), testoPrimaColonna);
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
	protected double valutaFormula(HSSFWorkbook wb, HSSFSheet foglio, int numRiga, int numColonna) {
		double valore = 0.0;
			
		HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(wb);
		//HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator( foglio, wb);
        HSSFRow riga = foglio.getRow(numRiga);
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
	protected String leggiString (HSSFCell cella){
		String risultato = "";
		
		try {
			switch (cella.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				risultato = cella.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				risultato = String.valueOf(((Double)cella.getNumericCellValue()).longValue());
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return risultato;
	}
	
	/**
	 * legge contenuto cella e restituisce string
	 */
	protected String leggiString (XSSFCell cella){
		String risultato = "";
		
		try {
			switch (cella.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				risultato = cella.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				risultato = String.valueOf(((Double)cella.getNumericCellValue()).longValue());
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return risultato;
	}

	/**
	 * legge contenuto cella e restituisce long
	 */
	protected long leggiLong(HSSFCell cella){
		long risultato = 0;
		try {
			switch (cella.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				String strValoreCella = cella.getStringCellValue();
				risultato = ((Double)FrmNumero.formatNumber(strValoreCella)).longValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
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
	protected int leggiInt (HSSFCell cella){
		int risultato = 0;
		try {
			switch (cella.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				String strValoreCella = cella.getStringCellValue();
				risultato = ((Double)FrmNumero.formatNumber(strValoreCella)).intValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
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
	protected double leggiDouble (HSSFCell cella){
		double risultato = 0.0;
		try {
			switch (cella.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				String strValoreCella = cella.getStringCellValue();
				risultato = FrmNumero.formatNumber(strValoreCella);
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
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
	 * Restituisce il numero massimo di righe permesso per il file Excel version 1997.
	 * Numero massimo di righe 65535
	 * @return numero massimo di righe
	 */
	public static int getNumMaxRowVersionExcel1997(){
		return SpreadsheetVersion.EXCEL97.getLastRowIndex();
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
	public HSSFWorkbook getWb() {
		return wb;
	}

	/**
	 * @author Maurizio Rosso - 22/mag/09
	 */
	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
		// con la nuova vesione di poi gli stili sono legati al workbook:
		// rigenera genera gli stili standard
		generaStiliStandard();
	}



	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public HSSFCellStyle getStileNumeroRealeGrassetto() {
		return stileNumeroRealeGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileNumeroRealeGrassetto(HSSFCellStyle stileNumeroRealeGrassetto) {
		this.stileNumeroRealeGrassetto = stileNumeroRealeGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public HSSFCellStyle getStileNumeroRealeNormale() {
		return stileNumeroRealeNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileNumeroRealeNormale(HSSFCellStyle stileNumeroRealeNormale) {
		this.stileNumeroRealeNormale = stileNumeroRealeNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public HSSFCellStyle getStileTestoGrassetto() {
		return stileTestoGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileTestoGrassetto(HSSFCellStyle stileTestoGrassetto) {
		this.stileTestoGrassetto = stileTestoGrassetto;
	}

	/**
	 * @author Oltean Cristina - 11/ott/2018
	 */
	public HSSFCellStyle getStileCambioNormale() {
		return stileCambioNormale;
	}

	/**
	 * @author Oltean Cristina - 11/ott/2018
	 */
	public void setStileCambioNormale(HSSFCellStyle stileCambioNormale) {
		this.stileCambioNormale = stileCambioNormale;
	}
	
	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public HSSFCellStyle getStileTestoNormale() {
		return stileTestoNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileTestoNormale(HSSFCellStyle stileTestoNormale) {
		this.stileTestoNormale = stileTestoNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public HSSFCellStyle getStileNumeroInteroGrassetto() {
		return stileNumeroInteroGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileNumeroInteroGrassetto(
			HSSFCellStyle stileNumeroInteroGrassetto) {
		this.stileNumeroInteroGrassetto = stileNumeroInteroGrassetto;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public HSSFCellStyle getStileNumeroInteroNormale() {
		return stileNumeroInteroNormale;
	}

	/**
	 * @author Maurizio Rosso - 01/giu/09
	 */
	public void setStileNumeroInteroNormale(HSSFCellStyle stileNumeroInteroNormale) {
		this.stileNumeroInteroNormale = stileNumeroInteroNormale;
	}

	
}
