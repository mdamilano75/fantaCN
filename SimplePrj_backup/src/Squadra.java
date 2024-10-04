

import java.util.ArrayList;

class Squadra {
	String nome							= "";
	ArrayList<Attaccante> arrAtt		= new ArrayList<Attaccante>();
	ArrayList<Centrocampista> arrCen	= new ArrayList<Centrocampista>();
	ArrayList<Difensore> arrDif			= new ArrayList<Difensore>();
	ArrayList<Portiere> arrPor			= new ArrayList<Portiere>();
	public Squadra() {
		
	}

	public Giocatore prelevaGiocatoreDaID(int actID){
		Giocatore actGiocatore	= null;
		for (int i = 0; i < arrAtt.size(); i++) {
			if (arrAtt.get(i).id==actID){
				actGiocatore	= arrAtt.get(i);
				break;
			}
		}
		if (actGiocatore==null){
			for (int i = 0; i < arrCen.size(); i++) {
				if (arrCen.get(i).id==actID){
					actGiocatore	= arrCen.get(i);
					break;
				}
			}
		}
		if (actGiocatore==null){
			for (int i = 0; i < arrDif.size(); i++) {
				if (arrDif.get(i).id==actID){
					actGiocatore	= arrDif.get(i);
					break;
				}
			}
		}
		if (actGiocatore==null){
			for (int i = 0; i < arrPor.size(); i++) {
				if (arrPor.get(i).id==actID){
					actGiocatore	= arrPor.get(i);
					break;
				}
			}
		}
		return actGiocatore;
	}

	public Giocatore prelevaGiocatoreDaNomeNoFascia(String actNome){
		return prelevaGiocatoreDaNome(-1, actNome);
	}

	public Giocatore prelevaGiocatoreDaNome(int actFile, String actNome){
		actNome					= actNome.toLowerCase().trim();
		actNome					= actNome.replace("à", "a'");
		actNome					= actNome.replace("á", "a'");
		actNome					= actNome.replace("è", "e'");
		actNome					= actNome.replace("é", "e'");
		actNome					= actNome.replace("ì", "i'");
		actNome					= actNome.replace("í", "i'");
		actNome					= actNome.replace("ò", "o'");
		actNome					= actNome.replace("ó", "o'");
		actNome					= actNome.replace("ù", "u'");
		actNome					= actNome.replace("ú", "u'");
		Giocatore actGiocatore	= null;
		if (actFile==-1 || actFile==4 || actFile==5){
			for (int i = 0; i < arrAtt.size(); i++) {
				if (arrAtt.get(i).nome.toLowerCase().equals(actNome)||
					arrAtt.get(i).getNomeNoAccenti().equals(actNome)||
					arrAtt.get(i).getSoloCognome().equals(actNome)||
					arrAtt.get(i).getNome2().toLowerCase().equals(actNome)){
					actGiocatore	= arrAtt.get(i);
					break;
				}
			}
		}
		if (actGiocatore==null){
			if (actFile==-1 || actFile==2 || actFile==3){
				for (int i = 0; i < arrCen.size(); i++) {UtilityGson.getGson().toJson(arrCen);
					if (arrCen.get(i).nome.toLowerCase().equals(actNome)||
						arrCen.get(i).getNomeNoAccenti().equals(actNome)||
						arrCen.get(i).getSoloCognome().equals(actNome)||
						arrCen.get(i).getNome2().toLowerCase().equals(actNome)){
						actGiocatore	= arrCen.get(i);
						break;
					}
				}
			}
		}
		if (actGiocatore==null){
			if (actFile==-1 || actFile==0 || actFile==1){
				for (int i = 0; i < arrDif.size(); i++) {
					if (arrDif.get(i).nome.toLowerCase().equals(actNome)||
						arrDif.get(i).getNomeNoAccenti().equals(actNome)||
						arrDif.get(i).getSoloCognome().equals(actNome)||
						arrDif.get(i).getNome2().equals(actNome)){
						actGiocatore	= arrDif.get(i);
						break;
					}
				}
			}
		}
		if (actGiocatore==null){
			for (int i = 0; i < arrPor.size(); i++) {
				if (arrPor.get(i).nome.toLowerCase().equals(actNome)||
						arrPor.get(i).getNomeNoAccenti().equals(actNome)||
						arrPor.get(i).getSoloCognome().equals(actNome)||
						arrPor.get(i).getNome2().equals(actNome)){
					actGiocatore	= arrPor.get(i);
					break;
				}
			}
		}
		return actGiocatore;
	}
	
	/**
	 * Marco Damilano - 05/ago/2024
	 */
	public ArrayList<Attaccante> getArrAtt() {
		return arrAtt;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public ArrayList<Centrocampista> getArrCen() {
		return arrCen;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public ArrayList<Difensore> getArrDif() {
		return arrDif;
	}
	/**
	 * Marco Damilano - 06/ago/2024
	 */
	public ArrayList<Portiere> getArrPor() {
		return arrPor;
	}
}

