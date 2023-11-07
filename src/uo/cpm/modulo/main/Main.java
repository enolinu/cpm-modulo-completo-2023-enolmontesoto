package uo.cpm.modulo.main;

import java.awt.EventQueue;

import uo.cpm.modulo.ui.VentanaInicial;

public class Main {
	
	/*
	 * DEBUG 0 => Juego normal
	 * DEBUG 1 => Modo solo cabecilla.
	 * DEBUG 2 => Modo invasor no cabecilla.
	 */
	public static final int DEBUG = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicial frame = new VentanaInicial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
