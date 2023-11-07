package uo.cpm.modulo.premios.model;


public class Premio {
	
	private String codigo;
	private String denominacion;
	private String descripcion;
	private String tipo;
	private int valor;
	private int unidades;
	
	public Premio(String codigo, String denominacion, String descripcion, String tipo, int valor) {
		this.codigo = codigo;
		this.denominacion = denominacion;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.valor = valor;
		this.unidades = 1;
	}
	
	public Premio(String codigo, String denominacion, String descripcion, String tipo, int valor, int unidades) {
		this.codigo = codigo;
		this.denominacion = denominacion;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.valor = valor;
		this.unidades = unidades;
	}
	
	public Premio(Premio otroPremio) {
		this(otroPremio.codigo, otroPremio.denominacion, otroPremio.descripcion, otroPremio.tipo,
				otroPremio.valor, otroPremio.unidades);
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDenominacion() {
		return denominacion;
	}
	
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public int getUnidades() {
		return unidades;
	}
	
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	
	public String getRutaImagen() {
		return "/img/"+getCodigo()+".png";
	}
	
	public String toString() {
		String strPremio;
		strPremio = this.denominacion + " - (" + this.valor + " puntos)";
		return strPremio;
	}
	
	public boolean esAccesible(int puntos) {
		return this.getValor() <= puntos;  
	}
	

}
