package uo.cpm.modulo.game.model;

public class Casilla {
	
	private boolean ocupada;
	private Invasor ocupante;
	private boolean prohibida;
	
	public Casilla() {
		ocupada = false;
		ocupante = null;
	}
	
	public Casilla(Invasor i) {
		ocupada = true;
		ocupante = i;
	}

	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	public Invasor getOcupante() {
		return ocupante;
	}

	public void setOcupante(Invasor ocupante) {
		this.ocupada = true;
		this.ocupante = ocupante;
	}
	
	public void desocupar() {
		this.ocupada = false;
		this.ocupante = null;
	}
	
	public String toString() {
		if(!isOcupada()) {
			return "-";
		} else {
			if(getOcupante() != null) {
				return this.getOcupante().getTipo();
			}
			return "%";
		}
	}

	public boolean isProhibida() {
		return prohibida;
	}

	public void setProhibida(boolean prohibida) {
		this.prohibida = prohibida;
	}

}
