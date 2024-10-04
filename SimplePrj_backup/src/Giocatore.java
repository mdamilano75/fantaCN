

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

class Giocatore {
	int id						= 0;
	String nome					= "";
	String nome2				= "";
	String squadra				= "";
	String ruoloACDP			= "";
	ArrayList<Ruolo> arrRuoli	= new ArrayList<Ruolo>();
	TreeMap<Integer,Statistica> mapStatistiche	= new TreeMap<Integer,Statistica>();
	int quotazioneAtt			= 0;
	int quotazioneIni			= 0;
	double moltiplicatore		= 1;
	String rigorista			= "";
	String infortunato			= "";
	String dettInfortunio		= "";
	String categoriaAsta		= "";
	String calciPiazzati		= "";
	String punizioni			= "";
	String angoli				= "";
	boolean falloso				= false;
	
	// statistiche
	
	protected void addRuoli(String eleRuoli){
		String[] arrStrRuoli	= eleRuoli.split(";");
		Ruolo actRuolo			= new Ruolo("");
		for (int i = 0; i < arrStrRuoli.length; i++) {
			actRuolo			= new Ruolo(arrStrRuoli[i]);
			arrRuoli.add(actRuolo);
		}
	}
	
	protected void addStatistica(int anno,
			int Pv, int Gf, int Gs,
			int Rp,int Rc,int R_piu,int R_meno,
			int Ass,int Amm,int Esp,int Au, 
			double Mv,double Fm){
		Statistica newStatistica	= new Statistica();
		newStatistica.setPartiteGiocate(Pv);

		newStatistica.setMediaVoto(Mv);
		newStatistica.setFantamediaVoto(Fm);
		
		newStatistica.setGolFatti(Gf);
		newStatistica.setGolSubiti(Gs);
		
		newStatistica.setRigoriParati(Rp);
		newStatistica.setRigoriTirati(Rc);
		newStatistica.setRigoriFatti(R_piu);
		newStatistica.setRigoriSbagliati(R_meno);
		
		newStatistica.setAssist(Ass);
		newStatistica.setAmmonizioni(Amm);
		newStatistica.setEspulsioni(Esp);
		newStatistica.setAutoreti(Au);
		
		mapStatistiche.put(anno,newStatistica);
	}
	
	protected String prelevaScheda(){
		StringBuffer scheda = new StringBuffer();
		scheda
		.append(getId())
		.append("; "+getNome())
		.append("; "+quantoOffesivo())
		.append("; "+getSquadra())
		.append("; "+getRuoloACDP())
		.append("; "+getQuotazioneAtt())
		.append("; "+getQuotazioneIni())
		.append("; "+getDifferenza())
		.append("; "+getCategoriaAsta())
		.append("; "+getRigorista())
		.append("; "+getCalciPiazzati())
		.append("; "+getPunizioni())
		.append("; "+getAngoli())
		.append("; "+getStatistiche())
		.append("; "+getInfortunato())
		.append("; "+getDettInfortunio())
		;
		return scheda.toString();
	}
	
	public String getStatistiche(){
		StringBuffer statAtt 	= new StringBuffer();
		StringBuffer statSto 	= new StringBuffer();
		StringBuffer statLast 	= new StringBuffer();

		int totGol=0, totAssist=0, partGio=0;
		double totMedia=0.0,totFantaMedia=0.0;
		int numAnniTrovati=0;
		for (Entry<Integer, Statistica> entry : getMapStatistiche().entrySet()) {
			if (entry.getKey()==2024){
				statAtt.append(entry.getValue().getGolFatti())
				.append(";"+entry.getValue().getAssist())
				.append(";"+entry.getValue().getPartiteGiocate())
				.append(";"+entry.getValue().getFantamediaVoto())
				.append(";"+entry.getValue().getMediaVoto())
				.append(";"+entry.getValue().getAmmonizioni())
				.append(";"+entry.getValue().getEspulsioni())
				.append(";"+entry.getValue().getRigoriTirati())
				.append(";"+entry.getValue().getRigoriSbagliati())
				;
			} else {
				totGol			+=entry.getValue().getGolFatti();
				totAssist		+=entry.getValue().getAssist();
				partGio			+=entry.getValue().getPartiteGiocate();
				totMedia		+=entry.getValue().getMediaVoto();
				totFantaMedia	+=entry.getValue().getFantamediaVoto();
				numAnniTrovati++;
				if (entry.getKey()==2023){
					statLast.append(entry.getValue().getGolFatti())
					.append(";"+entry.getValue().getAssist())
					.append(";"+entry.getValue().getPartiteGiocate())
					.append(";"+entry.getValue().getFantamediaVoto())
					.append(";"+entry.getValue().getMediaVoto())
					.append(";"+entry.getValue().getAmmonizioni())
					.append(";"+entry.getValue().getEspulsioni())
					.append(";"+entry.getValue().getRigoriTirati())
					.append(";"+entry.getValue().getRigoriSbagliati());
				}
			}
		}
		
		if (FrmStringa.strVoid(statLast.toString())){
			statLast.append(";;;;;;;;");
		}
		if (numAnniTrovati>1){
			statSto.append(";"+numAnniTrovati)
			.append(";"+totGol)
			.append(";"+totAssist)
			.append(";"+Math.round(partGio/numAnniTrovati))
			.append(";"+Math.round(totFantaMedia/numAnniTrovati))
			.append(";"+Math.round(totMedia/numAnniTrovati))
			;
		} else {
			statSto.append(";;;;;;");
		}
		
		return statAtt.toString()+";"+statLast.toString()+statSto.toString();
	}
	
	public String quantoOffesivo(){
		String isOff		= "";
		Ruolo actRuolo		= new Ruolo("");
		int i 				= 0;
		
		for (i = 0; i < arrRuoli.size(); i++) {
			actRuolo			= arrRuoli.get(i);
			if (actRuolo.sigla.equals(FC_Varie.ATT_Pc)){
				isOff		= FC_Varie.livOffensivo_6;
				break;
			}
		}
		for (i = 0; i < arrRuoli.size(); i++) {
			actRuolo			= arrRuoli.get(i);
			if (actRuolo.sigla.equals(FC_Varie.ATT_A)){
				isOff		= FC_Varie.livOffensivo_5;
				break;
			}
		}
		if (FrmStringa.strVoid(isOff)){
			for (i = 0; i < arrRuoli.size(); i++) {
				actRuolo			= arrRuoli.get(i);
				if (actRuolo.sigla.equals(FC_Varie.CEN_W) ||actRuolo.sigla.equals(FC_Varie.CEN_T)){
					isOff	= FC_Varie.livOffensivo_4;
					break;
				}
			}
		}
		if (FrmStringa.strVoid(isOff)){
			for (i = 0; i < arrRuoli.size(); i++) {
				actRuolo			= arrRuoli.get(i);
				if (actRuolo.sigla.equals(FC_Varie.CEN_E) ||actRuolo.sigla.equals(FC_Varie.CEN_C)){
					isOff	= FC_Varie.livOffensivo_3;
					break;
				}
			}
		}
		if (FrmStringa.strVoid(isOff)){
			for (i = 0; i < arrRuoli.size(); i++) {
				actRuolo			= arrRuoli.get(i);
				if (actRuolo.sigla.equals(FC_Varie.CEN_M)){
					isOff	= FC_Varie.livOffensivo_2;
					break;
				}
			}
		}
		if (FrmStringa.strVoid(isOff)){
			isOff			= FC_Varie.livOffensivo_1;
		}
		return isOff+";"+actRuolo.getSigla();
	}

	
	protected String getNome2(){
		if (id==FC_Varie.DIF_TheoHernandez_MIL){
			nome2	= "Theo Hernandez";
		} else if (id==FC_Varie.DIF_Miranda_BOL){
			nome2	= "Miranda";
		} else if (id==FC_Varie.DIF_Vasquez){
			nome2	= "Vasquez";
		} else if (id==FC_Varie.CEN_Pellegrini){
			nome2	= "Pellegrini";
		} else if (id==FC_Varie.DIF_Pellegrini){
			nome2	= "Pellegrini";
		} else if (id==FC_Varie.DIF_Marì_MON){
			nome2	= "Marì";
		} else if (id==FC_Varie.DIF_RafaMarin_NAP){
			nome2	= "Rafa Marin";
		} else if (id==FC_Varie.DIF_Jesus){
			nome2	= "Jesus";
		} else if (id==FC_Varie.DIF_Dembele){
			nome2	= "Dembele";
		} else if (id==FC_Varie.DIF_L__Coulibaly){
			nome2	= "L. Coulibaly";
		} else if (id==FC_Varie.DIF_Gabi_Jean){
			nome2	= "Gabi Jean";
		} else if (id==FC_Varie.CEN_Nico_Paz){
			nome2	= "Nico Paz";
		} else if (id==FC_Varie.CEN_Anguissa_NAP){
			nome2	= "Anguissa";
		} else if (id==FC_Varie.CEN_Morente_LEC){
			nome2	= "Morente";
		} else if (id==FC_Varie.CEN_Ellertson_VEN){
			nome2	= "Ellertson";
		} else if (id==FC_Varie.CEN_Anderson_VEN){
			nome2	= "Anderson";
		} else if (id==FC_Varie.CEN_Akpa_Apkro_LAZ){
			nome2	= "Akpa Apkro";
		} else if (id==FC_Varie.DIF_JoaoFerreira_UDI){
			nome2	= "Joao Ferreira";
		} else if (id==FC_Varie.DIF_Mercandalli_GEN){
			nome2	= "Mercandalli";
		} else if (id==FC_Varie.DIF_W__Coulibaly){
			nome2	= "W. Coulibaly";
		} else if (id==FC_Varie.DIF_Borna_Sosa){
			nome2	= "Borna Sosa";
		} else if (id==FC_Varie.DIF_A__Carboni){
			nome2	= "A. Carboni";
		} else if (id==FC_Varie.ATT_Lautaro_INT){
			nome2	= "Lautaro";
		} else if (id==FC_Varie.ATT_Livramento_VER){
			nome2	= "Livramento";
		} else if (id==FC_Varie.ATT_Gykjaer_VEN){
			nome2	= "Gykjaer";
		} else if (id==FC_Varie.DIF_G__Esteves_UDI){
			nome2	= "G. Esteves";
		} else if (id==FC_Varie.CEN_K__Thuram_JUV){
			nome2	= "K. Thuram";
		} else if (id==FC_Varie.CEN_S__Vignato_MON){
			nome2	= "S. Vignato";
		} else if (id==FC_Varie.CEN_Iling_Junior){
			nome2	= "Iling-Junior";
		} else if (id==FC_Varie.DIF_I__Touré){
			nome2	= "I. Toure'";
		} else if (id==FC_Varie.DIF_F__Carboni){
			nome2	= "F. Carboni";
		} else if (id==FC_Varie.DIF_M__Moreno){
			nome2	= "M. Moreno";
		} else if (id==FC_Varie.DIF_A__Moreno){
			nome2	= "A. Moreno";
		} else if (id==FC_Varie.CEN_M__Koné){
			nome2	= "M. Kone'";
		}
		return nome2.toLowerCase().trim();
	}
	
	protected String getNomeNoAccenti(){
		return nome.replaceAll("[']", "").toLowerCase();
	}
	
	protected String getSoloCognome(){
		if (nome.toLowerCase().indexOf("thuram")!=-1 
				||nome.toLowerCase().indexOf("pellegrini")!=-1
//				||nome.toLowerCase().indexOf("ilic")!=-1
			){
			return "";
		}
		String[] cognomeNome	= nome.split(" ");
		if (cognomeNome.length>1){
			return cognomeNome[0].toLowerCase();
		}
		return nome;
	}
	
	protected int getDiffQuotazione(){
		return quotazioneAtt-quotazioneIni;
	}
	
	/**
	 * Marco Damilano - 05/ago/2024
	 */
	public String getRuoloACDP() {
		return ruoloACDP;
	}
	/**
	 * Marco Damilano - 05/ago/2024
	 */
	public void setRuoloACDP(String ruoloACDP) {
		this.ruoloACDP = ruoloACDP;
	}
	/**
	 * Marco Damilano - 05/ago/2024
	 */
	public ArrayList<Ruolo> getArrRuoli() {
		return arrRuoli;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public double getMoltiplicatore() {
		return moltiplicatore;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public void setMoltiplicatore(double moltiplicatore) {
		this.moltiplicatore = moltiplicatore;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public String getRigorista() {
		return rigorista.equals("")?" - - -":rigorista+" RIG";
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public void setRigorista(String rigorista) {
		this.rigorista = rigorista;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public String getCalciPiazzati() {
		return calciPiazzati.equals("")?" - - -":calciPiazzati+" CPZ";
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public void setCalciPiazzati(String punizioni) {
		this.calciPiazzati = punizioni;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public boolean isFalloso() {
		return falloso;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public void setFalloso(boolean falloso) {
		this.falloso = falloso;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public void setArrRuoli(ArrayList<Ruolo> arrRuoli) {
		this.arrRuoli = arrRuoli;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public int getId() {
		return id;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public String getNome() {
		return FrmStringa.allineaDx(nome, 30);
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public String getInfortunato() {
		return infortunato.equals("")?" - - -":infortunato+" INF";
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public void setInfortunato(String infortunato) {
		this.infortunato = infortunato;
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public String getCategoriaAsta() {
		if (categoriaAsta.equals("")){
			categoriaAsta = "** N.D. **";
		}
		return FrmStringa.allineaDx(categoriaAsta, 25);
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public void setCategoriaAsta(String categoriaAsta) {
		this.categoriaAsta = categoriaAsta;
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public String getQuotazioneAtt() {
		return FrmNumero.formattaCifreFisse(quotazioneAtt, 2);
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public void setQuotazioneAtt(int quotazioneAtt) {
		this.quotazioneAtt = quotazioneAtt;
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public String getPunizioni() {
		return punizioni.equals("")?" - - -":punizioni+" PUN";
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public void setPunizioni(String calciPiazzati2) {
		this.punizioni = calciPiazzati2;
	}

	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public int getQuotazioneIni() {
		return quotazioneIni;
	}

	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public void setQuotazioneIni(int quotazioneIni) {
		this.quotazioneIni = quotazioneIni;
	}

	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public int getDifferenza() {
		return quotazioneAtt-quotazioneIni;
	}


	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public String getAngoli() {
		return angoli.equals("")?" - - -":angoli+" ANG";
	}

	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public void setAngoli(String angoli) {
		this.angoli = angoli;
	}

	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public void setNome2(String nome2) {
		this.nome2 = nome2;
	}

	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public String getDettInfortunio() {
		String actDettInf	= dettInfortunio;
		actDettInf			= actDettInf.replaceAll("&nbsp;", "");
		actDettInf			= actDettInf.replaceAll("<div class=\"item-description\"><p>", "");
		actDettInf			= actDettInf.replaceAll("</p></div>", "");
		actDettInf			= actDettInf.replaceAll(";", ",");
		return actDettInf;
	}

	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public void setDettInfortunio(String dettInfortunio) {
		this.dettInfortunio = dettInfortunio;
	}

	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public String getSquadra() {
		return FrmStringa.allineaDx(squadra,20);
	}

	/**
	 * Marco Damilano - 08/ago/2024
	 */
	public void setSquadra(String squadra) {
		this.squadra = squadra;
	}

	/**
	 * Marco Damilano - 26/ago/2024
	 */
	public TreeMap<Integer, Statistica> getMapStatistiche() {
		return mapStatistiche;
	}

	/**
	 * Marco Damilano - 26/ago/2024
	 */
	public void setMapStatistiche(TreeMap<Integer, Statistica> mapStatistiche) {
		this.mapStatistiche = mapStatistiche;
	}
}
