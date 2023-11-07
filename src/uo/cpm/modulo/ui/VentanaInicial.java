package uo.cpm.modulo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.cpm.modulo.validacion.GestionDeTickets;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import javax.help.*;
import java.net.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.*;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class VentanaInicial extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnPrincipal;
	private JTextField tfCodigo;
	private JButton btComenzar;
	private JLabel lbCodigo;
	private JPanel pnSuperior;
	private JLabel lbTitulo;
	private JTextField tfTienda;
	private JLabel lbTienda;
	private JButton btAyuda;
	private JButton btnAcercaDe;
	
	private VerificarNoVacio vNV = new VerificarNoVacio();
	private MostrarAcercaDe mAD = new MostrarAcercaDe();
	
	private GestionDeTickets tickets;
	
	// Internacionalización
	private Locale localizacion;
	private ResourceBundle mensajes;
	
	private JRadioButton rbEsp;
	private JRadioButton rbEng;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	public VentanaInicial() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInicial.class.getResource("/img/fantasma.png")));
		setIdioma("es");
		
		tickets = new GestionDeTickets();
		
		setResizable(false);
		setTitle(mensajes.getString("titulo.general"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		pnPrincipal = new JPanel();
		pnPrincipal.setRequestFocusEnabled(false);
		pnPrincipal.setBackground(new Color(255, 255, 255));
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(null);
		pnPrincipal.add(getTfCodigo());
		pnPrincipal.add(getBtComenzar());
		pnPrincipal.add(getLbCodigo());
		pnPrincipal.add(getPanel_1());
		pnPrincipal.add(getLbTitulo());
		pnPrincipal.add(getTfTienda());
		pnPrincipal.add(getLbTienda());
		setLocationRelativeTo(null);
		getRootPane().setDefaultButton(btComenzar);
		
		// Ponemos el foco en el primer cuadro de texto.
		getTfCodigo().requestFocus();
		
		loadHelp();
	}
	
	// Ayuda.
	private void loadHelp(){

		   URL hsURL;
		   HelpSet hs;

		    try {
			    	File fichero = new File("help/Ayuda.hs");
			    	hsURL = fichero.toURI().toURL();
			        hs = new HelpSet(null, hsURL);
			      }

		    catch (Exception e){
		      System.out.println("Help not found!");
		      return;
		   }
		    

		   HelpBroker hb = hs.createHelpBroker();

		   hb.enableHelpKey(getRootPane(),"introduccion", hs);
		   hb.enableHelpOnButton(getBtAyuda(), "introduccion", hs);
		   //hb.enableHelp(componente, "alias html specific", hs);
	}
	
	private JTextField getTfCodigo() {
		if (tfCodigo == null) {
			tfCodigo = new JTextField();
			tfCodigo.setFont(new Font("Courier New", Font.PLAIN, 25));
			tfCodigo.setBounds(212, 245, 373, 45);
			tfCodigo.setColumns(10);
		}
		return tfCodigo;
	}
	private JButton getBtComenzar() {
		if (btComenzar == null) {
			btComenzar = new JButton(mensajes.getString("inicial.comenzar"));
			btComenzar.setRequestFocusEnabled(false);
			btComenzar.addActionListener(vNV);
			btComenzar.setForeground(new Color(255, 255, 255));
			btComenzar.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
			btComenzar.setBackground(new Color(0, 100, 0));
			btComenzar.setBounds(658, 464, 116, 36);
		}
		return btComenzar;
	}
	private JLabel getLbCodigo() {
		if (lbCodigo == null) {
			lbCodigo = new JLabel(mensajes.getString("inicial.codigo"));
			lbCodigo.setHorizontalAlignment(SwingConstants.CENTER);
			lbCodigo.setRequestFocusEnabled(false);
			lbCodigo.setLabelFor(getTfCodigo());
			lbCodigo.setDisplayedMnemonic('C');
			lbCodigo.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
			lbCodigo.setBounds(0, 189, 784, 45);
		}
		return lbCodigo;
	}
	private JPanel getPanel_1() {
		if (pnSuperior == null) {
			pnSuperior = new JPanel();
			pnSuperior.setRequestFocusEnabled(false);
			pnSuperior.setBackground(new Color(0, 128, 0));
			pnSuperior.setBounds(0, 0, 784, 45);
			pnSuperior.setLayout(null);
			pnSuperior.add(getBtAyuda());
			pnSuperior.add(getBtnAcercaDe());
			pnSuperior.add(getRbEsp());
			pnSuperior.add(getRbEng());
		}
		return pnSuperior;
	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel(mensajes.getString("inicial.titulo"));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
			lbTitulo.setBackground(new Color(0, 100, 0));
			lbTitulo.setFont(new Font("OCR A Extended", Font.BOLD, 30));
			lbTitulo.setBounds(0, 84, 784, 78);
		}
		return lbTitulo;
	}
	
	private void acceder() {
		this.dispose();
		mostrarVentanaJuego();
	}
	
	private void mostrarVentanaJuego() {
		try {
			VentanaJuego vj = new VentanaJuego(getTfTienda().getText().trim(), localizacion);
			vj.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private JTextField getTfTienda() {
		if (tfTienda == null) {
			tfTienda = new JTextField();
			tfTienda.setFont(new Font("Courier New", Font.PLAIN, 25));
			tfTienda.setColumns(10);
			tfTienda.setBounds(212, 372, 373, 45);
		}
		return tfTienda;
	}
	private JLabel getLbTienda() {
		if (lbTienda == null) {
			lbTienda = new JLabel(mensajes.getString("inicial.tienda"));
			lbTienda.setHorizontalAlignment(SwingConstants.CENTER);
			lbTienda.setRequestFocusEnabled(false);
			lbTienda.setLabelFor(getTfTienda());
			lbTienda.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
			lbTienda.setDisplayedMnemonic('T');
			lbTienda.setBounds(0, 313, 784, 45);
		}
		return lbTienda;
	}
	private JButton getBtAyuda() {
		if (btAyuda == null) {
			btAyuda = new JButton(mensajes.getString("general.ayuda"));
			btAyuda.setRequestFocusEnabled(false);
			btAyuda.setMnemonic('A');
			btAyuda.setBackground(new Color(255, 255, 255));
			btAyuda.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btAyuda.setBounds(588, 12, 73, 23);
		}
		return btAyuda;
	}
	private JButton getBtnAcercaDe() {
		if (btnAcercaDe == null) {
			btnAcercaDe = new JButton(mensajes.getString("general.acercade"));
			btnAcercaDe.setRequestFocusEnabled(false);
			btnAcercaDe.setMnemonic('D');
			btnAcercaDe.setIcon(null);
			btnAcercaDe.setBackground(new Color(255, 255, 255));
			btnAcercaDe.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btnAcercaDe.setBounds(671, 12, 103, 23);
			btnAcercaDe.addActionListener(mAD);
		}
		return btnAcercaDe;
	}
	
	// Métodos privados de funcionalidad.
	
	private boolean comprobarNoVacio() {
		boolean cond1 = getTfCodigo().getText().isEmpty();
		boolean cond2 = getTfTienda().getText().isEmpty();
		if(cond1 || cond2) {
			JOptionPane.showMessageDialog(null, "Debe rellenar ámbos campos de texto para proceder.");
			return false;
		}
		return true;
		
	}
	
	private void validarCampos() {
		if(comprobarNoVacio()) {
			String codigoTicket = getTfCodigo().getText();
			String codigoTienda = getTfTienda().getText();
			boolean valido = tickets.usarTicket(codigoTicket, codigoTienda);
			if(valido) {
				acceder();
			} else {
				JOptionPane.showMessageDialog(null, tickets.generarMensajeDeError());
			}
		}
	}
	
	private void mostrarAcercaDe() {
		try {
			DialogoAcercaDe aD = new DialogoAcercaDe();
			aD.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	// Gestión de evantos.
	class VerificarNoVacio implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			validarCampos();
		}
		
	}
	
	class MostrarAcercaDe implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mostrarAcercaDe();
			
		}
		
	}
	
	// Internacionalizacion
	
	private void setIdioma(String id) {
		localizacion =  new Locale(id);
		mensajes =  ResourceBundle.getBundle("inter/aplicacion", localizacion);
	}
	
	private void evaluarIdioma() {
		if(getRbEsp().isSelected()) {
			setIdioma("es");
		} if(getRbEng().isSelected()) {
			setIdioma("en");
		}
		
		actualizarTextos();
	}
	
	private void actualizarTextos() {
		
		setTitle(mensajes.getString("titulo.general"));
		
		getLbTitulo().setText(mensajes.getString("inicial.titulo"));
		getLbCodigo().setText(mensajes.getString("inicial.codigo"));
		getLbTienda().setText(mensajes.getString("inicial.tienda"));
		
		getBtComenzar().setText(mensajes.getString("inicial.comenzar"));
		getBtAyuda().setText(mensajes.getString("general.ayuda"));
		getBtnAcercaDe().setText(mensajes.getString("general.acercade"));
		
		
	}

	private JRadioButton getRbEsp() {
		if (rbEsp == null) {
			rbEsp = new JRadioButton("Espa\u00F1ol");
			rbEsp.setMnemonic('E');
			rbEsp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					evaluarIdioma();
				}
			});
			rbEsp.setForeground(new Color(255, 255, 255));
			rbEsp.setBackground(new Color(0, 128, 0));
			rbEsp.setSelected(true);
			buttonGroup.add(rbEsp);
			rbEsp.setBounds(6, 13, 77, 23);
		}
		return rbEsp;
	}
	
	private JRadioButton getRbEng() {
		if (rbEng == null) {
			rbEng = new JRadioButton("English");
			rbEng.setMnemonic('N');
			rbEng.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					evaluarIdioma();
				}
			});
			rbEng.setForeground(new Color(255, 255, 255));
			rbEng.setBackground(new Color(0, 128, 0));
			buttonGroup.add(rbEng);
			rbEng.setBounds(85, 13, 73, 23);
		}
		return rbEng;
	}
}
