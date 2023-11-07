package uo.cpm.modulo.premios.model;

import java.util.ArrayList;
import java.util.List;
import uo.cpm.modulo.util.FileUtil;

public class Catalogo {
	
	private static final String FICHERO_CATALOGO = "files/premios.dat";
	private List<Premio> catalogo;
	private int numElementos;
	
	public Catalogo() {
		catalogo = new ArrayList<Premio>();
		cargarPremios();
		numElementos = catalogo.size();
	}

	public Premio[] getCatalogo() {
		Premio[] arrayCatalogo = catalogo.toArray(new Premio[catalogo.size()]);
		return arrayCatalogo;
	}
	
	public Premio[] getCatalogo(String tipo) {
		
		if(tipo.equals("All")) {
			return getCatalogo();
		}
		
		List<Premio> filtro = filtrarPor(tipo);
		Premio[] arrayFiltro = filtro.toArray(new Premio[filtro.size()]);
		return arrayFiltro;
		
	}
	
	private List<Premio> filtrarPor(String tipo) {
		
		List<Premio> filtro = new ArrayList<Premio>();
		
		for(Premio p: catalogo) {
			if(p.getTipo().equals(tipo)) {
				filtro.add(p);
			}
		}
		
		return filtro;
		
	}
	
	private void cargarPremios() {
		FileUtil.loadFile(FICHERO_CATALOGO, catalogo);
	}

	public int getNumElementos() {
		return numElementos;
	}

}
