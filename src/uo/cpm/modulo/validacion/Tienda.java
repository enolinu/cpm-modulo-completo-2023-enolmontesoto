package uo.cpm.modulo.validacion;

public class Tienda {
	
	private final String codigoTienda;
	private final String nombre;
	
	public Tienda(String codigoTienda, String nombre) {
		this.codigoTienda = codigoTienda;
		this.nombre = nombre;
	}

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public String getNombre() {
		return nombre;
	}

}
