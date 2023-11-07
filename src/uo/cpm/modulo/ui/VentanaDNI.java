package uo.cpm.modulo.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import uo.cpm.modulo.service.GestionDePremios;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class VentanaDNI extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel getPnDNI;
	private JPanel pnBotones;
	private JLabel lbDNI;
	private JTextField tfDNI;
	private JButton btContinuar;
	private JPanel pnConfirmacion;
	private JLabel lbConfirmacion;
	private JButton btConfirmarYFinalizar;
	private JButton btnEditarDni;
	private JPanel pnFinal;
	private JTextPane txtpnenhorabuenaSuPedido;
	private JButton btVolverAInicio;
	
	private String codigoTienda;
	private GestionDePremios premios;
	private String DNI;
	private JLabel lbCorrecto;
	private JLabel llbConfDNI;
	
	// Internacionalización
	private Locale localizacion;
	private ResourceBundle mensajes;

	/**
	 * Create the dialog.
	 */
	public VentanaDNI(GestionDePremios premios, String codigoTienda, Locale loc) {
		
		setIdioma(loc);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		
		this.premios = premios;
		this.setCodigoTienda(codigoTienda);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDNI.class.getResource("/img/fantasma.png")));
		setTitle(mensajes.getString("titulo.confirmacion"));
		setBounds(100, 100, 700, 450);
		getContentPane().setLayout(new CardLayout(0, 0));
		getContentPane().add(getGetPnDNI(), "name_10390623000500");
		getContentPane().add(getPnConfirmacion(), "name_65224785870400");
		getContentPane().add(getPnFinal(), "name_66166113703400");
		setLocationRelativeTo(null);
		setModal(true);

	}
	
	private void salir() {
		if(confirmarSalir()) {
			System.exit(0);
		}
	}
	
	private boolean confirmarSalir() {
		boolean confirmacion = false;
		int respuesta = JOptionPane.showConfirmDialog(this, mensajes.getString("cuadro.salir"));
		if(respuesta == JOptionPane.YES_OPTION) {
			confirmacion = true;
		}
		return confirmacion;
	}

	private JPanel getGetPnDNI() {
		if (getPnDNI == null) {
			getPnDNI = new JPanel();
			getPnDNI.setBackground(new Color(255, 255, 255));
			getPnDNI.setLayout(null);
			getPnDNI.add(getPnBotones());
			getPnDNI.add(getLbDNI());
			getPnDNI.add(getTfDNI());
		}
		return getPnDNI;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setBackground(new Color(0, 128, 0));
			pnBotones.setBounds(0, 365, 684, 46);
			pnBotones.setLayout(null);
			pnBotones.add(getBtContinuar());
		}
		return pnBotones;
	}
	private JLabel getLbDNI() {
		if (lbDNI == null) {
			lbDNI = new JLabel(mensajes.getString("conf.introduzca"));
			lbDNI.setLabelFor(getTfDNI());
			lbDNI.setDisplayedMnemonic('I');
			lbDNI.setHorizontalAlignment(SwingConstants.CENTER);
			lbDNI.setFont(new Font("Gill Sans MT", Font.PLAIN, 17));
			lbDNI.setBackground(new Color(255, 255, 255));
			lbDNI.setBounds(98, 139, 469, 28);
		}
		return lbDNI;
	}
	private JTextField getTfDNI() {
		if (tfDNI == null) {
			tfDNI = new JTextField();
			tfDNI.setFont(new Font("Gill Sans MT", Font.PLAIN, 20));
			tfDNI.setBounds(158, 178, 347, 35);
			tfDNI.setColumns(10);
		}
		return tfDNI;
	}
	private JButton getBtContinuar() {
		if (btContinuar == null) {
			btContinuar = new JButton(mensajes.getString("conf.continuar"));
			btContinuar.setMnemonic('C');
			btContinuar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					acceder();
				}
			});
			btContinuar.setForeground(new Color(0, 128, 0));
			btContinuar.setBackground(new Color(255, 255, 255));
			btContinuar.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btContinuar.setBounds(566, 11, 108, 23);
		}
		return btContinuar;
	}
	
	private JPanel getPnConfirmacion() {
		if (pnConfirmacion == null) {
			pnConfirmacion = new JPanel();
			pnConfirmacion.setBackground(new Color(255, 255, 255));
			pnConfirmacion.setLayout(null);
			pnConfirmacion.add(getLbConfirmacion());
			pnConfirmacion.add(getBtConfirmarYFinalizar());
			pnConfirmacion.add(getBtnEditarDni());
			pnConfirmacion.add(getLbCorrecto());
			pnConfirmacion.add(getLlbConfDNI());
		}
		return pnConfirmacion;
	}
	private JLabel getLbConfirmacion() {
		if (lbConfirmacion == null) {
			lbConfirmacion = new JLabel(mensajes.getString("conf.haintroducido"));
			lbConfirmacion.setHorizontalAlignment(SwingConstants.CENTER);
			lbConfirmacion.setFont(new Font("Gill Sans MT", Font.PLAIN, 20));
			lbConfirmacion.setBounds(0, 93, 684, 36);
		}
		return lbConfirmacion;
	}
	private JButton getBtConfirmarYFinalizar() {
		if (btConfirmarYFinalizar == null) {
			btConfirmarYFinalizar = new JButton(mensajes.getString("conf.finalizar"));
			btConfirmarYFinalizar.setMnemonic('F');
			btConfirmarYFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					grabarPedido();
				}
			});
			btConfirmarYFinalizar.setForeground(new Color(255, 255, 255));
			btConfirmarYFinalizar.setBackground(new Color(0, 128, 0));
			btConfirmarYFinalizar.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btConfirmarYFinalizar.setBounds(430, 317, 161, 23);
		}
		return btConfirmarYFinalizar;
	}
	private JButton getBtnEditarDni() {
		
		if (btnEditarDni == null) {
			btnEditarDni = new JButton(mensajes.getString("conf.editar"));
			btnEditarDni.setMnemonic('E');
			btnEditarDni.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					anteriorPanel();
				}
			});
			btnEditarDni.setForeground(Color.WHITE);
			btnEditarDni.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btnEditarDni.setBackground(new Color(255, 0, 0));
			btnEditarDni.setBounds(102, 317, 161, 23);
		}
		return btnEditarDni;
	}
	
	// Métodos privados de funcionalidad.
	
	private boolean validarNoVacio() {
		if(getTfDNI().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, mensajes.getString("cuadro.dni"));
			return false;
		} else {
			this.DNI = getTfDNI().getText().trim();
		}
		return true;
	}
	
	private void acceder() {
		if(validarNoVacio()) {
			siguientePanel();
			getLlbConfDNI().setText(DNI);
		}
	}
	
	private void grabarPedido() {
		premios.grabarPedido(DNI, getCodigoTienda());
		siguientePanel();
	}
	private JPanel getPnFinal() {
		if (pnFinal == null) {
			pnFinal = new JPanel();
			pnFinal.setBackground(new Color(0, 128, 0));
			pnFinal.setLayout(null);
			pnFinal.add(getTxtpnenhorabuenaSuPedido());
			pnFinal.add(getBtVolverAInicio());
		}
		return pnFinal;
	}
	private JTextPane getTxtpnenhorabuenaSuPedido() {
		if (txtpnenhorabuenaSuPedido == null) {
			txtpnenhorabuenaSuPedido = new JTextPane();
			txtpnenhorabuenaSuPedido.setEditable(false);
			txtpnenhorabuenaSuPedido.setFocusable(false);
			txtpnenhorabuenaSuPedido.setText(mensajes.getString("conf.enhorabuena"));
			txtpnenhorabuenaSuPedido.setForeground(new Color(255, 255, 255));
			txtpnenhorabuenaSuPedido.setBackground(new Color(0, 128, 0));
			txtpnenhorabuenaSuPedido.setFont(new Font("Gill Sans MT", Font.PLAIN, 23));
			txtpnenhorabuenaSuPedido.setBounds(96, 132, 535, 90);
		}
		return txtpnenhorabuenaSuPedido;
	}
	private JButton getBtVolverAInicio() {
		if (btVolverAInicio == null) {
			btVolverAInicio = new JButton(mensajes.getString("conf.terminar"));
			btVolverAInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					finalizar();
				}
			});
			btVolverAInicio.setForeground(new Color(0, 100, 0));
			btVolverAInicio.setBackground(new Color(255, 255, 255));
			btVolverAInicio.setMnemonic('T');
			btVolverAInicio.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btVolverAInicio.setBounds(271, 349, 138, 25);
		}
		return btVolverAInicio;
	}
	
	private void finalizar() {
		mostrarVentanaInicial();
		this.dispose();
	}
	
	private void mostrarVentanaInicial() {
		try {
			VentanaInicial vI = new VentanaInicial();
			vI.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void siguientePanel() {
		((CardLayout)getContentPane().getLayout()).next(getContentPane());
	}
	
	private void anteriorPanel() {
		((CardLayout)getContentPane().getLayout()).previous(getContentPane());
	}

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public void setCodigoTienda(String codigoTienda) {
		this.codigoTienda = codigoTienda;
	}
	private JLabel getLbCorrecto() {
		if (lbCorrecto == null) {
			lbCorrecto = new JLabel(mensajes.getString("conf.pulse"));
			lbCorrecto.setHorizontalAlignment(SwingConstants.CENTER);
			lbCorrecto.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
			lbCorrecto.setBounds(0, 226, 684, 36);
		}
		return lbCorrecto;
	}
	private JLabel getLlbConfDNI() {
		if (llbConfDNI == null) {
			llbConfDNI = new JLabel("00000000X");
			llbConfDNI.setHorizontalAlignment(SwingConstants.CENTER);
			llbConfDNI.setFont(new Font("Gill Sans MT", Font.BOLD, 33));
			llbConfDNI.setBounds(0, 158, 684, 36);
		}
		return llbConfDNI;
	}
	
	// Internacionalizacion
	
	private void setIdioma(Locale loc) {
		localizacion =  loc;
		mensajes =  ResourceBundle.getBundle("inter/aplicacion", localizacion);
	}
}
