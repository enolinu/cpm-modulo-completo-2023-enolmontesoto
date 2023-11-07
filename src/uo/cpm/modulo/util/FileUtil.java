package uo.cpm.modulo.util;

import java.io.*;
import java.util.*;

import uo.cpm.modulo.premios.model.Premio;
import uo.cpm.modulo.validacion.Ticket;
import uo.cpm.modulo.validacion.Tienda;

public abstract class FileUtil {

	public static void loadFile(String nombreFicheroEntrada, List<Premio> listaCatalogo) {

		String linea;
		String[] datosPremio = null;

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
			while (fichero.ready()) {
				linea = fichero.readLine();
				datosPremio = linea.split("@");
				listaCatalogo.add(new Premio(datosPremio[0], datosPremio[1], datosPremio[2], datosPremio[3], 
						Integer.parseInt(datosPremio[4])));
			}
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha encontrado.");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
	}
	
	public static void loadTicketFile(String nombreFicheroEntrada, List<Ticket> listaTickets) {

		String linea;
		String[] datosPremio = null;

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
			while (fichero.ready()) {
				linea = fichero.readLine();
				datosPremio = linea.split("@");
				listaTickets.add(new Ticket(datosPremio[1], datosPremio[0], Float.parseFloat(datosPremio[2])));
			}
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha encontrado.");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
	}
	
	public static Tienda loadShopFile(String nombreFicheroEntrada) {

		String linea;
		String[] datosTienda;
		Tienda t = null;

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
			linea = fichero.readLine();
			datosTienda = linea.split("@");
			t = new Tienda(datosTienda[0],datosTienda[1]);
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha encontrado.");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
		
		return t;
	}

	public static void saveToFile(String nombreFicheroSalida, String pedido) {
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter(nombreFicheroSalida, true));
			String linea = pedido;
			fichero.write(linea);
			fichero.newLine();
			fichero.close();
		}

		catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha podido guardar");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida");
		}
	}
	
	public static void updateTicketFile(String nombreFicheroSalida, String pedido) {
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter(nombreFicheroSalida));
			String linea = pedido;
			fichero.write(linea);
			fichero.close();
		}

		catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha podido guardar");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida");
		}
	}
}
