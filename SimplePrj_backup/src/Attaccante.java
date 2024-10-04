

class Attaccante extends Giocatore {
	public Attaccante() {
		setRuoloACDP(FC_Varie.ATT);
	}
	
	protected String quantoBomber(){
		String isBomber	= "";
		Ruolo actRuolo			= new Ruolo("");
		for (int i = 0; i < arrRuoli.size(); i++) {
			actRuolo			= arrRuoli.get(i);
			if (actRuolo.sigla.equals(FC_Varie.ATT_Pc)){
				isBomber	= "* * * ";
				break;
			}
			if (FrmStringa.strVoid(isBomber)){
				isBomber	= "* *   ";
			}
		}
		return isBomber+actRuolo.getSigla();
	}
}
