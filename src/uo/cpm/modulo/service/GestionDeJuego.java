package uo.cpm.modulo.service;

import uo.cpm.modulo.game.model.Casilla;
import uo.cpm.modulo.game.model.Invasor;
import uo.cpm.modulo.game.model.Juego;

public class GestionDeJuego {
	
	private Juego juego;
	
	public GestionDeJuego() {
		this.juego = new Juego();
	}
	
	public Casilla[] getHilera() {
		return juego.getHilera();
	}
	
	public Casilla[][] getTablero() {
		return juego.getTablero();
	}
	
	public int getInvasoresEnHilera() {
		return juego.getInvasoresEnHilera();
	}
	
	public int getPuntuacion() {
		return juego.getPuntuacion();
	}
	
	public boolean validarFinDeRonda() {
		return juego.validarFinDeRonda();
	}
	
	public void selectInvasor(Casilla c) {
		juego.selectInvasor(c);
	}
	
	public Invasor getInvasorSeleccionado() {
		return juego.getInvasorSeleccionado();
	}
	
	public boolean colocarInvasor(int x, int y) {
		return juego.colocarInvasor(x, y);
	}
	
	public int getRonda() {
		return juego.getRonda();
	}
	
	public void setRonda(int ronda) {
		juego.setRonda(ronda);
	}
	
	public int obtenerNumeroDeCasilla(int x, int y) {
		return juego.obtenerNumeroDeCasilla(x, y);
	}
	
	public String toString() {
		return juego.toString();
	}

	public int obtenerCoordenadaX(int i) {
		return juego.obtenerCoordenadaX(i);
	}
	
	public int obtenerCoordenadaY(int i) {
		return juego.obtenerCoordenadaY(i);
	}

	public boolean derrota() {
		return juego.derrota();
	}

	public boolean finalizado() {
		return juego.finalizado();
	}

	public void setSonido(boolean b) {
		juego.setSonido(b);
	}

	public boolean isSonido() {
		return juego.isSonido();
	}

}
