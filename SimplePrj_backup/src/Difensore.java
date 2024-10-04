

class Difensore extends Giocatore {
	public Difensore() {
		setRuoloACDP(FC_Varie.DIF);
	}
	
	public boolean isEsterno(){
		boolean isEterno	= false;
		Ruolo actRuolo			= new Ruolo("");
		for (int i = 0; i < arrRuoli.size(); i++) {
			actRuolo			= arrRuoli.get(i);
			if (actRuolo.sigla.equals(FC_Varie.DIF_E)){
				isEterno	= true;
				break;
			}
		}
		return isEterno;
	}
	
}

