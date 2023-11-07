package uo.cpm.modulo.service;

import java.util.List;

import uo.cpm.modulo.premios.model.Catalogo;
import uo.cpm.modulo.premios.model.Premio;
import uo.cpm.modulo.premios.model.Seleccion;

public class GestionDePremios {
	
	private Catalogo catalogo;
	private Seleccion seleccion;
	
	
	public GestionDePremios(int puntos) {
		this.catalogo = new Catalogo();
		this.seleccion = new Seleccion(puntos);
	}
	
	public Premio[] getCatalogo() {
		return catalogo.getCatalogo();
	}
	
	public Premio[] getCatalogo(String tipo) {
		return catalogo.getCatalogo(tipo);
	}
	
	public int getNumElementosCatalogo() {
		return catalogo.getNumElementos();
	}
	
	public List<Premio> getSeleccion() {
		return seleccion.getSeleccion();
	}
	
	public List<Premio> getSeleccion(String tipo) {
		return seleccion.getSeleccion(tipo);
	}
	
	public void add(Premio premioDelCatalogo, int unidades) {
		seleccion.add(premioDelCatalogo, unidades);
	}
	
	public void remove(int posicion) {
		seleccion.remove(posicion);
	}
	
	public void reiniciarSeleccion() {
		this.seleccion = new Seleccion(getPuntosIniciales());
	}
	
	public int getPuntosRestantes() {
		return seleccion.getPuntosRestantes();
	}
	
	public void setPuntos(int puntos) {
		if(puntos >= 0) {
			seleccion.setPuntos(puntos);
		}
	}
	
	public int getPuntosIniciales() {
		return seleccion.getPuntosIniciales();
	}

	public void grabarPedido(String dNI, String codigoTienda) {
		seleccion.grabarPedido(dNI, codigoTienda);
	}

	public void remove(int[] indicesSeleccionados) {
		seleccion.remove(indicesSeleccionados);
	}

}
