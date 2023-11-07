package uo.cpm.modulo.game.model;

import java.util.Random;

public class Invasor {
	
	private String tipo;
	
	public Invasor() {
		asignarTipo();
	}
	
	private void asignarTipo() {
		String[] tipos = {"mariano","luis","capman","fantasma","monoloco","pato","sonio","bombas"};
		Random r = new Random();
		this.tipo = tipos[r.nextInt(8)];
	}
	
	public Invasor(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean isCabecilla() {
		return this.getTipo().equals("fantasma");
	}
	
	public String getImagen() {
		return "/img/"+getTipo()+".png";
	}

}
