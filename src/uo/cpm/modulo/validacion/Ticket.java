package uo.cpm.modulo.validacion;

public class Ticket {
	
	private final String codigoCompra;
	private final String codigoTienda;
	private final float importe;
	
	public Ticket(String codigoCompra, String codigoTienda, float importe) {
		this.codigoCompra = codigoCompra;
		this.codigoTienda = codigoTienda;
		this.importe = importe;
	}

	public String getCodigoCompra() {
		return codigoCompra;
	}

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public float getImporte() {
		return importe;
	}
	
	

}
