

class Ruolo {
	String sigla		= "";
	boolean offensivo	= false;
	
	public Ruolo(String sigla) {
		this.sigla=sigla;
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public String getSigla() {
		return FrmStringa.allineaDx(sigla, 2);
	}

	/**
	 * Marco Damilano - 07/ago/2024
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
