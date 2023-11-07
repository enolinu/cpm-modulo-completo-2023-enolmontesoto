package uo.cpm.modulo.game.model;

import java.util.ArrayList;
import uo.cpm.modulo.main.Main;

public class Juego {
	
	public final static int NUM_CASILLAS = 49;
	public final static int DIM = 7;
	public final static int TANDA_DE_INVASORES = 5;
	public static final int TAMAÑO_HILERA = 5;
	
	private Casilla[] hilera = new Casilla[TAMAÑO_HILERA];
	private Casilla[][] tablero = new Casilla[DIM][DIM];
	private Invasor invasorSeleccionado;
	private int invasoresEnHilera;
	private int ronda;
	private int puntos;
	
	private boolean sonido;
	
	public Juego() {
		inicializarJuego();
	}
	
	private void inicializarJuego() {
		inicializarTablero();
		crearHilera();
		this.invasoresEnHilera = TAMAÑO_HILERA;
		this.ronda = 1;
		sonido = true;
		crearCincoOcupantesAleatorios();
	}
	
	public Casilla[][] getTablero() {
		Casilla[][] copiaTablero = new Casilla[DIM][DIM];
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				copiaTablero[i][j] = tablero[i][j];
			}
		}
		return copiaTablero;
	}
	
	public Casilla[] getHilera() {
		Casilla[] arrayHilera = new Casilla[TAMAÑO_HILERA];
		for(int i=0; i<TAMAÑO_HILERA; i++) {
			arrayHilera[i] = hilera[i];
		}
		return arrayHilera;
	}
	
	public int getInvasoresEnHilera() {
		return invasoresEnHilera;
	}
	
	public int getRonda() {
		return ronda;
	}

	public void setRonda(int ronda) {
		this.ronda = ronda;
	}
	
	public int getPuntuacion() {
		return puntos;
	}
	
	public Invasor getInvasorSeleccionado() {
		return invasorSeleccionado;
	}

	public void setInvasorSeleccionado(Invasor invasorSeleccionado) {
		this.invasorSeleccionado = invasorSeleccionado;
	}
	
	private void inicializarTablero() {
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				tablero[i][j] = new Casilla();
				if((i==3 && j==3) || (i==0 && j==0) || (i==0 && j==DIM-1) || (i==DIM-1 && j==0) || (i==DIM-1 && j==DIM-1)) {
					tablero[i][j].setProhibida(true);
				}
			}
		}
	}
	
	private void crearCincoOcupantesAleatorios() {
		for(int j=0; j<TANDA_DE_INVASORES; j++) {
			int coordX = (int)(Math.random()*(-DIM+1)+DIM);
			int coordY = (int)(Math.random()*(-DIM+1)+DIM);
			while(tablero[coordX][coordY].isOcupada() || tablero[coordX][coordY].isProhibida()) {
				coordX = (int)(Math.random()*(-DIM+1)+DIM);
				coordY = (int)(Math.random()*(-DIM+1)+DIM);
			}
			
			int debug = Main.DEBUG;
			if(debug == 0) {
				tablero[coordX][coordY].setOcupante(new Invasor());
			} else if(debug == 1) {
				tablero[coordX][coordY].setOcupante(new Invasor("fantasma"));
			} else if(debug == 2) {
				tablero[coordX][coordY].setOcupante(new Invasor("monoloco"));
			}
		}
	}
	
	private void crearHilera() {
		int debug = Main.DEBUG;
		for(int i=0; i<TAMAÑO_HILERA; i++) {
			if(debug == 0) {
				hilera[i] = new Casilla(new Invasor());
			} else if(debug == 1) {
				hilera[i] = new Casilla(new Invasor("fantasma"));
			} else if(debug == 2) {
				hilera[i] = new Casilla(new Invasor("monoloco"));
			}
			
		}
	}
	
	public void selectInvasor(Casilla c) {
		setInvasorSeleccionado(c.getOcupante());
		invasoresEnHilera--;
	}
	
	public boolean colocarInvasor(int x, int y) {
		if(invasorSeleccionado != null && tablero[x][y].getOcupante() == null) {
			tablero[x][y].setOcupante(invasorSeleccionado);
			invasorSeleccionado = null;
			return validarFinDeRonda();
		}
		return false;
	}
	
	public int obtenerNumeroDeCasilla(int x, int y) {
		int contador = -1;
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				contador++;
				if(i==x && j==y) {
					return contador;
				}
			}
		}
		return -1;
	}
	
	// Método que permite obtener la coordenada "X" que ocupa un botón de la IU.
	public int obtenerCoordenadaX(int numero) {
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				if(obtenerNumeroDeCasilla(i, j) == numero) {
					return i;
				}
			}
		}
		return -1;
	}
		
	// Método que permite obtener la coordenada "Y" que ocupa un botón de la IU.
	public int obtenerCoordenadaY(int numero) {
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				if(obtenerNumeroDeCasilla(i, j) == numero) {
					return j;
				}
			}
		}
		return -1;
	}
	
	private void eliminar() {
		ArrayList<ArrayList<Casilla>> colonias = new ArrayList<ArrayList<Casilla>>();
		for(int i=0; i<DIM; i++) {
			colonias.addAll(eliminarFila(i));
			colonias.addAll(eliminarColumna(i));
		}
		
		for(ArrayList<Casilla> colonia: colonias) {
			boolean grupoCabecilla = (colonia.size() >= 5) && (colonia.get(0).getOcupante().isCabecilla());
			if(grupoCabecilla) {
				puntos += 20000;
				finalizar();
			} else {
				obtenerPuntos(colonia.size());
				for(Casilla casilla: colonia) {
					casilla.desocupar();
				}
			}
			
		}
		
	}

	private boolean invadida(Casilla c) {
		return c.getOcupante() != null;
	}
	
	public void obtenerPuntos(int tamaño) {
		switch(tamaño) {
		case 3:
			puntos += 50;
			break;
		case 4:
			puntos += 200;
			break;
		case 5:
			puntos += 1000;
			break;
		case 6:
			puntos += 5000;
			break;
		case 7:
			puntos += 10000;
			break;
		}
	}
	
	private ArrayList<ArrayList<Casilla>> eliminarFila(int numFila) {
		String tipo = "";
		ArrayList<Casilla> posiciones = new ArrayList<Casilla>();
		ArrayList<ArrayList<Casilla>> colonias = new ArrayList<ArrayList<Casilla>>();
		if(invadida(tablero[numFila][0])) {
			tipo = tablero[numFila][0].getOcupante().getTipo();
		}
		for(int i=0; i<DIM; i++) {
			if(invadida(tablero[numFila][i]) && tipo.equals(tablero[numFila][i].getOcupante().getTipo())) {
				posiciones.add(tablero[numFila][i]);
			}
			if(invadida(tablero[numFila][i]) && !tipo.equals(tablero[numFila][i].getOcupante().getTipo())) {
				tipo = tablero[numFila][i].getOcupante().getTipo();
				if(posiciones.size() >= 3 && !tipo.isBlank()) {
					colonias.add(posiciones);
				}
				posiciones = new ArrayList<Casilla>();
				posiciones.add(tablero[numFila][i]);
			}
			if(!invadida(tablero[numFila][i])) {
				if(posiciones.size() >= 3 && !tipo.isBlank()) {
					colonias.add(posiciones);
				}
				posiciones = new ArrayList<Casilla>();
				tipo = "";
			}
		}
		if(posiciones.size() >= 3 && !tipo.isBlank()) {
			colonias.add(posiciones);	
		}
		return colonias;
	}
	
	private ArrayList<ArrayList<Casilla>> eliminarColumna(int numColumna) {
		String tipo = "";
		ArrayList<Casilla> posiciones = new ArrayList<Casilla>();
		ArrayList<ArrayList<Casilla>> colonias = new ArrayList<ArrayList<Casilla>>();
		if(invadida(tablero[0][numColumna])) {
			tipo = tablero[0][numColumna].getOcupante().getTipo();
		}
		for(int i=0; i<DIM; i++) {
			if(invadida(tablero[i][numColumna]) && tipo.equals(tablero[i][numColumna].getOcupante().getTipo())) {
				posiciones.add(tablero[i][numColumna]);
			}
			if(invadida(tablero[i][numColumna]) && !tipo.equals(tablero[i][numColumna].getOcupante().getTipo())) {
				tipo = tablero[i][numColumna].getOcupante().getTipo();
				if(posiciones.size() >= 3 && !tipo.isBlank()) {
					colonias.add(posiciones);
				}
				posiciones = new ArrayList<Casilla>();
				posiciones.add(tablero[i][numColumna]);
			}
			if(!invadida(tablero[i][numColumna])) {
				if(posiciones.size() >= 3 && !tipo.isBlank()) {
					colonias.add(posiciones);
				}
				posiciones = new ArrayList<Casilla>();
				tipo = "";
			}
		}
		if(posiciones.size() >= 3 && !tipo.isBlank()) {
			colonias.add(posiciones);	
		}
		return colonias;
	}
	
	
	public boolean validarFinDeRonda() {
		if(invasoresEnHilera <= 0 && invasorSeleccionado == null) {
			ronda++;
			eliminar();
			crearHilera();
			invasoresEnHilera = TAMAÑO_HILERA;
			return true;
		}
		return false;
	}
	
	public int validarFinDeJuego() {
		if(validarFinDeRonda()) {
			int contador = 0;
			for(int i=0; i<DIM; i++) {
				for(int j=0; j<DIM; j++) {
					if(invadida(tablero[i][j])) {
						contador++;
					}
				}
			}
			if(contador ==0) return -1; // Se ha vaciado el tablero.
			if(contador >= NUM_CASILLAS-10) return -2; // No caben mas;
			if(ronda == 10) return -3; //se han acabado las rondas.
		}
		return 1;
	}

	public String toString() {
		String cadena = "";
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				cadena = cadena + tablero[i][j].toString()+" ";
				if(j==DIM-1) {
					cadena += "\n";
				}
			}
		}
		return cadena;
	}

	public boolean finalizado() {
		if(ronda >= 11) {
			return true;
		}
		return false;
	}

	private boolean tableroLleno() {
		
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				if(tablero[i][j].getOcupante() == null && !tablero[i][j].isProhibida()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void finalizar() {
		setRonda(11);
		finalizado();
	}
	
	public boolean derrota() {
		return tableroLleno();
	}

	public boolean isSonido() {
		return sonido;
	}

	public void setSonido(boolean sonido) {
		this.sonido = sonido;
	}                

}
