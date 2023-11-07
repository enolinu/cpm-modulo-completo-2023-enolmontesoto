package uo.cpm.modulo.validacion;

import java.util.ArrayList;

import uo.cpm.modulo.util.FileUtil;

public class GestionDeTickets {
	
	private static final int CORRECTO = 0;
	private static final int NO_FIGURA = 1;
	private static final int NO_CONCUERDA = 2;
	private static final int IMPORTE_INSUFICIENTE = 3;
	
	private static final String FICHERO_TICKETS = "files/tickets.dat";
	private static final String FICHERO_CONFIG = "files/config.dat";
	
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	private Tienda tienda;
	private int error;
	
	public GestionDeTickets() {
		setTienda(leerInfoTienda());
		leerTickets();
	}
	
	private void leerTickets() {
		FileUtil.loadTicketFile(FICHERO_TICKETS, tickets);
	}
	
	private Tienda leerInfoTienda() {
		return FileUtil.loadShopFile(FICHERO_CONFIG);
	}
	
	private void actualizarFichero() {
		
		StringBuilder sb = new StringBuilder();
		
		for(Ticket t: tickets) {
			sb.append(t.getCodigoTienda()+"@"+t.getCodigoCompra()+"@"+t.getImporte()+"\n");
		}
		
		FileUtil.updateTicketFile(FICHERO_TICKETS, sb.toString());
		
	}
	
	public boolean usarTicket(String codigoIntroducido, String tiendaIntroducida) {
		
		if(!tiendaIntroducida.equals(tienda.getCodigoTienda())) {
			error = NO_CONCUERDA;
			return false;
		}
		
		for(Ticket t: tickets) {
			if(t.getCodigoCompra().equals(codigoIntroducido)) {
				if(t.getImporte() >= 20) {
					error = CORRECTO;
					tickets.remove(t);
					actualizarFichero();
					return true;
				} if(t.getImporte() < 20) {
					error = IMPORTE_INSUFICIENTE;
					return false;
				}
			}
		}
		
		error = NO_FIGURA;
		return false;
		
	}
	
	public String generarMensajeDeError() {
		
		switch(error) {
		case CORRECTO:
			return "";
		case NO_FIGURA:
			return "El ticket introducido no figura en la base de datos.";
		case IMPORTE_INSUFICIENTE:
			return "Debe gastar al menos 20 euros para poder jugar.";
		case NO_CONCUERDA:
			return "La tienda donde se ha emitido este ticket no coincide con la ubicación.";
		}
		return "";
		
	}
	
	@Override
	public String toString() {
		
		String cadena = "";
		for(Ticket t: tickets) {
			cadena = cadena + "Compra: " + t.getCodigoCompra()+" Tienda: "+t.getCodigoTienda()+" Importe: "+t.getImporte()+"€\n";
		}
		return cadena;
		
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}
	
	

}
