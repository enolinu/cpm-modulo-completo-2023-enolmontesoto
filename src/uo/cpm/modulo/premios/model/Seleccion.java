package uo.cpm.modulo.premios.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uo.cpm.modulo.util.FileUtil;

public class Seleccion {
	
	private List<Premio> listaPedido;
	private int puntos;
	private final int puntosIniciales;
	
	public Seleccion(int puntos) {
		this.setPuntos(puntos);
		this.puntosIniciales = puntos;
		listaPedido = new ArrayList<Premio>();
		listaPedido.clear();
	}
	
	public List<Premio> getSeleccion() {
		List<Premio> aux = new ArrayList<Premio>();
		for(Premio p: listaPedido) {
			aux.add(p);
		}
		return aux;
	}
	
	public List<Premio> getSeleccion(String tipo) {
		
		if(tipo.equals("all")) {
			return getSeleccion();
		}
		
		List<Premio> aux = new ArrayList<Premio>();
		for(Premio p: listaPedido) {
			if(p.getTipo().equals(tipo)) {
				aux.add(p);
			}
		}
		return aux;
	}
	
	public void setPuntos(int puntos) {
		if(puntos >= 0) {
			this.puntos = puntos;
		}
	}

	public int getPuntosRestantes() {
		return puntos;
	}

	public int getPuntosIniciales() {
		return puntosIniciales;
	}

	public void add(Premio premioDelCatalogo, int unidades) {
		Premio premioASeleccion = new Premio(premioDelCatalogo);
		premioASeleccion.setUnidades(unidades);
		listaPedido.add(premioASeleccion);
		setPuntos(puntos - premioDelCatalogo.getValor());
		ordenarLista();
	}
	
	public void remove(int posicion) {
		int puntosARestaurar = listaPedido.get(posicion).getValor();
		listaPedido.remove(posicion);
		setPuntos(puntos + puntosARestaurar);
		ordenarLista();
	}
	
	public void remove(int[] posiciones) {
		
		int puntosARestaurar = 0;
		List<Premio> elementosAEliminar = new ArrayList<>();
		
		for(int i=0; i<posiciones.length; i++) {
			puntosARestaurar += listaPedido.get(posiciones[i]).getValor();
			elementosAEliminar.add(listaPedido.get(posiciones[i]));
		}
		
		for(Premio p: elementosAEliminar) {
			listaPedido.remove(p);
		}
		
		setPuntos(puntos + puntosARestaurar);
		ordenarLista();
		
	}
	
	private void ordenarLista() {
		Collections.sort(listaPedido, (p1, p2) -> p1.getDenominacion().compareToIgnoreCase(p2.getDenominacion()));
	}
	
	public void grabarPedido(String DNI, String codigoTienda) {
		String codigos="";
		for(Premio p: getSeleccion()) {
			codigos = codigos +"@"+p.getCodigo();
		}
		String pedido = DNI+"@"+codigoTienda+codigos;
		
		FileUtil.saveToFile("files/entregas.dat", pedido);
	}

}
