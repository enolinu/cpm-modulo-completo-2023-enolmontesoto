package uo.cpm.modulo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import uo.cpm.modulo.premios.model.Premio;
import uo.cpm.modulo.service.GestionDePremios;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.Toolkit;

public class VentanaPremios extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// Constantes para limitar el tamaño de los iconos.
	private static final int MAX_IMAGE_DIMENSION = 150;
	
	// Componentes gráficos.
	private JPanel pnPrincipal;
	private JPanel pnSuperior;
	private JLabel lblPuntosRestantes;
	private JTextField tfPuntosRestantes;
	private JLabel lblCatlogoDePremios;
	private JPanel pnCatalogo;
	private JLabel lbInfo;
	private JPanel pnInfo;
	private JLabel lbImagenProducto;
	private JLabel lbDenominacion;
	private JLabel lblPuntos;
	private JTextArea txtrDescripcion;
	private JTabbedPane pnPestanas;
	private JPanel pnSeleccion;
	private JPanel pnFiltros;
	private JPanel pnAuxLista;
	private JScrollPane spLista;
	private JButton btSiguiente;
	private JButton btEliminar;
	private JButton btnReiniciar;
	
	private String filtroActual;
	
	// Lista y modelo de premios.
	private DefaultListModel<Premio> modeloListPremios;
	private GestionDePremios premios;
	private String codigoTienda;
	
	// Gestión de evendos
	private MostrarDatos mD = new MostrarDatos();
	private AnadirProducto aP = new AnadirProducto();
	private EliminarProducto eP = new EliminarProducto();
	private ReiniciarSeleccion rS = new ReiniciarSeleccion();
	private FinalizarPedido fP = new FinalizarPedido();
	
	// Internacionalizacion
	private Locale localizacion;
	private ResourceBundle mensajes;
	
	// Filtros
	private JList<Premio> listaPedido;
	private JButton btFiltroTodos;
	private JButton btFiltroVideojuegos;
	private JButton btFiltroConsolas;
	private JButton btFiltroAccesorios;
	private JButton btAcercaDe;
	private JButton btAyuda;

	/**
	 * Create the frame.
	 */
	public VentanaPremios(int puntos, String codigoTienda, Locale loc) {
		
		setIdioma(loc);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPremios.class.getResource("/img/fantasma.png")));
		setTitle(mensajes.getString("titulo.premios"));
		
		this.premios = new GestionDePremios(puntos);
		this.filtroActual = "All";
		
		this.setCodigoTienda(codigoTienda);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1214, 815);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(new Color(255, 255, 255));
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(null);
		pnPrincipal.add(getPnSuperior());
		pnPrincipal.add(getLblCatlogoDePremios());
		pnPrincipal.add(getPnCatalogo());
		pnPrincipal.add(getLbInfo());
		pnPrincipal.add(getPnInfo());
		pnPrincipal.add(getPnPestanas());
		actualizarCampoPuntos();
		
		crearBotonesCatalogo();
		deshabilitarNoAccesibles();
		
		loadHelp();
		
	}
	
	// Confirmación de salida.
	
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
				hb.enableHelpOnButton(getBtAyuda(), "premios", hs);
				hb.enableHelp(getBtAyuda(), "premios", hs);
			}
			
		
	// Componentes gráficos.
		
	private JPanel getPnSuperior() {
		if (pnSuperior == null) {
			pnSuperior = new JPanel();
			pnSuperior.setBackground(new Color(0, 128, 0));
			pnSuperior.setBounds(0, 0, 1198, 74);
			pnSuperior.setLayout(null);
			pnSuperior.add(getLblPuntosRestantes());
			pnSuperior.add(getTfPuntosRestantes());
			pnSuperior.add(getBtAcercaDe());
			pnSuperior.add(getBtAyuda());
		}
		return pnSuperior;
	}
	private JLabel getLblPuntosRestantes() {
		if (lblPuntosRestantes == null) {
			lblPuntosRestantes = new JLabel(mensajes.getString("premios.txpuntosrestantes"));
			lblPuntosRestantes.setForeground(new Color(255, 255, 255));
			lblPuntosRestantes.setHorizontalAlignment(SwingConstants.CENTER);
			lblPuntosRestantes.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
			lblPuntosRestantes.setBounds(0, 0, 1198, 33);
		}
		return lblPuntosRestantes;
	}
	
	private JTextField getTfPuntosRestantes() {
		if (tfPuntosRestantes == null) {
			tfPuntosRestantes = new JTextField();
			tfPuntosRestantes.setEditable(false);
			tfPuntosRestantes.setFont(new Font("Gill Sans MT", Font.PLAIN, 22));
			tfPuntosRestantes.setHorizontalAlignment(SwingConstants.CENTER);
			tfPuntosRestantes.setText("10000");
			tfPuntosRestantes.setBounds(497, 30, 206, 33);
			tfPuntosRestantes.setColumns(10);
		}
		return tfPuntosRestantes;
	}
	private JLabel getLblCatlogoDePremios() {
		if (lblCatlogoDePremios == null) {
			lblCatlogoDePremios = new JLabel(mensajes.getString("premios.txcatalogo"));
			lblCatlogoDePremios.setHorizontalAlignment(SwingConstants.CENTER);
			lblCatlogoDePremios.setForeground(new Color(0, 128, 0));
			lblCatlogoDePremios.setFont(new Font("Gill Sans MT", Font.BOLD, 18));
			lblCatlogoDePremios.setBounds(20, 85, 641, 24);
		}
		return lblCatlogoDePremios;
	}
	private JPanel getPnCatalogo() {
		if (pnCatalogo == null) {
			pnCatalogo = new JPanel();
			pnCatalogo.setBorder(new LineBorder(new Color(0, 128, 0), 3, true));
			pnCatalogo.setBackground(new Color(245, 245, 245));
			pnCatalogo.setBounds(20, 115, 641, 639);
			pnCatalogo.setLayout(new GridLayout(0, 4, 0, 0));
		}
		return pnCatalogo;
	}
	private JLabel getLbInfo() {
		if (lbInfo == null) {
			lbInfo = new JLabel(mensajes.getString("premios.txinfo"));
			lbInfo.setHorizontalAlignment(SwingConstants.CENTER);
			lbInfo.setForeground(new Color(0, 128, 0));
			lbInfo.setFont(new Font("Gill Sans MT", Font.BOLD, 18));
			lbInfo.setBounds(706, 85, 463, 24);
		}
		return lbInfo;
	}
	private JPanel getPnInfo() {
		if (pnInfo == null) {
			pnInfo = new JPanel();
			pnInfo.setBorder(new LineBorder(new Color(0, 128, 0), 3, true));
			pnInfo.setBackground(new Color(255, 255, 255));
			pnInfo.setBounds(706, 115, 463, 239);
			pnInfo.setLayout(null);
			pnInfo.add(getLbImagenProducto());
			pnInfo.add(getLbDenominacion());
			pnInfo.add(getLblPuntos());
			pnInfo.add(getTxtrDescripcion());
		}
		return pnInfo;
	}
	private JLabel getLbImagenProducto() {
		if (lbImagenProducto == null) {
			lbImagenProducto = new JLabel("");
			lbImagenProducto.setHorizontalAlignment(SwingConstants.CENTER);
			lbImagenProducto.setBounds(20, 22, 170, 201);
		}
		return lbImagenProducto;
	}
	private JLabel getLbDenominacion() {
		if (lbDenominacion == null) {
			lbDenominacion = new JLabel("");
			lbDenominacion.setForeground(new Color(0, 100, 0));
			lbDenominacion.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
			lbDenominacion.setBounds(200, 22, 253, 24);
		}
		return lbDenominacion;
	}
	private JLabel getLblPuntos() {
		if (lblPuntos == null) {
			lblPuntos = new JLabel("");
			lblPuntos.setForeground(new Color(0, 100, 0));
			lblPuntos.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
			lblPuntos.setBounds(200, 199, 253, 24);
		}
		return lblPuntos;
	}
	private JTextArea getTxtrDescripcion() {
		if (txtrDescripcion == null) {
			txtrDescripcion = new JTextArea();
			txtrDescripcion.setEditable(false);
			txtrDescripcion.setWrapStyleWord(true);
			txtrDescripcion.setLineWrap(true);
			txtrDescripcion.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			txtrDescripcion.setBounds(200, 46, 253, 152);
		}
		return txtrDescripcion;
	}
	private JTabbedPane getPnPestanas() {
		if (pnPestanas == null) {
			pnPestanas = new JTabbedPane(JTabbedPane.TOP);
			pnPestanas.setBackground(new Color(255, 255, 255));
			pnPestanas.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
			pnPestanas.setBounds(706, 384, 463, 370);
			pnPestanas.addTab(mensajes.getString("premios.seleccion"), null, getPnSeleccion(), null);
			pnPestanas.setMnemonicAt(0, 1);
			pnPestanas.setForegroundAt(0, new Color(0, 100, 0));
			pnPestanas.addTab(mensajes.getString("premios.filtros"), null, getPnFiltros(), null);
			pnPestanas.setMnemonicAt(1, 2);
			pnPestanas.setForegroundAt(1, new Color(0, 100, 0));
		}
		return pnPestanas;
	}
	private JPanel getPnSeleccion() {
		if (pnSeleccion == null) {
			pnSeleccion = new JPanel();
			pnSeleccion.setBackground(new Color(255, 255, 255));
			pnSeleccion.setLayout(null);
			pnSeleccion.add(getPnAuxLista());
			pnSeleccion.add(getBtSiguiente());
			pnSeleccion.add(getBtEliminar());
			pnSeleccion.add(getBtnReiniciar());
		}
		return pnSeleccion;
	}
	private JPanel getPnFiltros() {
		if (pnFiltros == null) {
			pnFiltros = new JPanel();
			pnFiltros.setBackground(new Color(255, 255, 255));
			pnFiltros.setLayout(null);
			pnFiltros.add(getBtFiltroTodos());
			pnFiltros.add(getBtFiltroVideojuegos());
			pnFiltros.add(getBtFiltroConsolas());
			pnFiltros.add(getBtFiltroAccesorios());
		}
		return pnFiltros;
	}
	private JPanel getPnAuxLista() {
		if (pnAuxLista == null) {
			pnAuxLista = new JPanel();
			pnAuxLista.setBackground(new Color(255, 255, 255));
			pnAuxLista.setBounds(10, 10, 438, 282);
			pnAuxLista.setLayout(new BorderLayout(0, 0));
			pnAuxLista.add(getSpLista(), BorderLayout.CENTER);
		}
		return pnAuxLista;
	}
	private JScrollPane getSpLista() {
		if (spLista == null) {
			spLista = new JScrollPane();
			spLista.setBackground(new Color(255, 255, 255));
			spLista.setViewportView(getListaPedido());
		}
		return spLista;
	}
	private JButton getBtSiguiente() {
		if (btSiguiente == null) {
			btSiguiente = new JButton(mensajes.getString("general.siguiente"));
			btSiguiente.setMnemonic('S');
			btSiguiente.addActionListener(fP);
			btSiguiente.setEnabled(false);
			btSiguiente.setForeground(new Color(255, 255, 255));
			btSiguiente.setBackground(new Color(0, 128, 0));
			btSiguiente.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btSiguiente.setBounds(359, 303, 89, 23);
		}
		return btSiguiente;
	}
	
	private JButton getBtEliminar() {
		if (btEliminar == null) {
			btEliminar = new JButton(mensajes.getString("premios.eliminar"));
			btEliminar.setEnabled(false);
			btEliminar.setMnemonic('E');
			btEliminar.setBackground(new Color(255, 0, 0));
			btEliminar.setForeground(new Color(255, 255, 255));
			btEliminar.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btEliminar.setBounds(10, 303, 89, 23);
			btEliminar.addActionListener(eP);
		}
		return btEliminar;
	}
	
	private JButton getBtnReiniciar() {
		if (btnReiniciar == null) {
			btnReiniciar = new JButton(mensajes.getString("premios.reiniciar"));
			btnReiniciar.setEnabled(false);
			btnReiniciar.setMnemonic('R');
			btnReiniciar.addActionListener(rS);
			btnReiniciar.setForeground(Color.WHITE);
			btnReiniciar.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btnReiniciar.setBackground(new Color(0, 0, 255));
			btnReiniciar.setBounds(115, 303, 89, 23);
		}
		return btnReiniciar;
	}
	
	private JList<Premio> getListaPedido() {
		if (listaPedido == null) {
			
			modeloListPremios = new DefaultListModel<Premio>();
			listaPedido = new JList<Premio>(modeloListPremios);
			
		}
		return listaPedido;
	}
	
	private JButton getBtFiltroTodos() {
		if (btFiltroTodos == null) {
			btFiltroTodos = new JButton(mensajes.getString("premios.todos"));
			btFiltroTodos.setMnemonic('T');
			btFiltroTodos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filtroActual = "All";
					actualizarBotonesCatalogo();
				}
			});
			btFiltroTodos.setBackground(new Color(0, 128, 0));
			btFiltroTodos.setForeground(new Color(255, 255, 255));
			btFiltroTodos.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
			btFiltroTodos.setBounds(29, 80, 179, 45);
		}
		return btFiltroTodos;
	}
	private JButton getBtFiltroVideojuegos() {
		if (btFiltroVideojuegos == null) {
			btFiltroVideojuegos = new JButton(mensajes.getString("premios.videojuegos"));
			btFiltroVideojuegos.setMnemonic('V');
			btFiltroVideojuegos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filtroActual = "Videojuegos";
					actualizarBotonesCatalogo();
				}
			});
			btFiltroVideojuegos.setForeground(Color.WHITE);
			btFiltroVideojuegos.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
			btFiltroVideojuegos.setBackground(new Color(0, 128, 0));
			btFiltroVideojuegos.setBounds(252, 80, 179, 45);
		}
		return btFiltroVideojuegos;
	}
	private JButton getBtFiltroConsolas() {
		if (btFiltroConsolas == null) {
			btFiltroConsolas = new JButton(mensajes.getString("premios.consolas"));
			btFiltroConsolas.setMnemonic('N');
			btFiltroConsolas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filtroActual = "Consolas";
					actualizarBotonesCatalogo();
				}
			});
			btFiltroConsolas.setForeground(Color.WHITE);
			btFiltroConsolas.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
			btFiltroConsolas.setBackground(new Color(0, 128, 0));
			btFiltroConsolas.setBounds(29, 198, 179, 45);
		}
		return btFiltroConsolas;
	}
	private JButton getBtFiltroAccesorios() {
		if (btFiltroAccesorios == null) {
			btFiltroAccesorios = new JButton(mensajes.getString("premios.accesorios"));
			btFiltroAccesorios.setMnemonic('A');
			btFiltroAccesorios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filtroActual = "Accesorios";
					actualizarBotonesCatalogo();
				}
			});
			btFiltroAccesorios.setForeground(Color.WHITE);
			btFiltroAccesorios.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
			btFiltroAccesorios.setBackground(new Color(0, 128, 0));
			btFiltroAccesorios.setBounds(252, 198, 179, 45);
		}
		return btFiltroAccesorios;
	}
	
	
	// Métodos privados de funcionalidad.
	
	private JButton getBtAyuda() {
		if (btAyuda == null) {
			btAyuda = new JButton(mensajes.getString("general.ayuda"));
			btAyuda.setMnemonic('Y');
			btAyuda.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btAyuda.setBackground(Color.WHITE);
			btAyuda.setBounds(986, 31, 79, 23);
		}
		return btAyuda;
	}
	private JButton getBtAcercaDe() {
		if (btAcercaDe == null) {
			btAcercaDe = new JButton(mensajes.getString("general.acercade"));
			btAcercaDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarAcercaDe();
				}
			});
			btAcercaDe.setBackground(new Color(255, 255, 255));
			btAcercaDe.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btAcercaDe.setMnemonic('E');
			btAcercaDe.setBounds(1075, 30, 102, 23);
		}
		return btAcercaDe;
	}
	private void limpiarPanelCatalogo() {
		getPnCatalogo().removeAll();
	}
	
	private void crearBotonesCatalogo() {
		
		limpiarPanelCatalogo();
		
		for(int i=0; i<premios.getCatalogo(filtroActual).length; i++) {
			getPnCatalogo().add(crearBotonCatalogo(i));
		}
		
		validate();
		repaint();
		
	}
	
	private JButton crearBotonCatalogo(int i) {
		
		JButton boton = new JButton();
		boton.setBounds(0,0,150,150);
		String rutaImagen = premios.getCatalogo(filtroActual)[i].getRutaImagen();
		setImagenAdaptada(boton, rutaImagen);
		setImagenAdaptadaBoton(boton, rutaImagen);
		boton.setBackground(Color.WHITE);
		boton.setActionCommand(String.valueOf(i));
		boton.addActionListener(aP);
		boton.addMouseListener(mD);
		return boton;
		
	}
	
	private void actualizarBotonesCatalogo() {
		
		limpiarPanelCatalogo();
		
		for(int i=0; i<premios.getCatalogo(filtroActual).length; i++) {
			
			JButton boton = crearBotonCatalogo(i);
			getPnCatalogo().add(boton);
			
		}
		
		deshabilitarNoAccesibles();
		
		validate();
		repaint();
		
	}
	
	private void setImagenAdaptada(JButton boton, String rutaImagen){
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
				// No se permite un ancho mayor a 150 pixeles (para que las imágenes siempre quepan).
				int nuevoTamano = Math.min(boton.getWidth(), MAX_IMAGE_DIMENSION);
				
				Image imgOriginal = new ImageIcon(getClass().getResource(rutaImagen)).getImage(); 
				Image imgEscalada = imgOriginal.getScaledInstance(nuevoTamano, nuevoTamano, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(imgEscalada);
				//ImageIcon icon = new ImageIcon(imgOriginal);
				boton.setIcon(icon);
				
			}
			
		});
		 
	}
	
	private void setImagenAdaptadaBoton(JButton boton, String rutaImagen){
		
		// No se permite un ancho mayor a 150 pixeles (para que las imágenes siempre quepan).
		int nuevoTamano = Math.min(boton.getWidth(), MAX_IMAGE_DIMENSION);
		
		Image imgOriginal = new ImageIcon(getClass().getResource(rutaImagen)).getImage(); 
		Image imgEscalada = imgOriginal.getScaledInstance(nuevoTamano, nuevoTamano, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(imgEscalada);
		//ImageIcon icon = new ImageIcon(imgOriginal);
		boton.setIcon(icon);
		
	}
	
	
	private void setImagenAdaptada(JLabel label, String rutaImagen){
		 Image imgOriginal = new ImageIcon(getClass().getResource(rutaImagen)).getImage(); 
		 Image imgEscalada = imgOriginal.getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_SMOOTH);
		 ImageIcon icon = new ImageIcon(imgEscalada);
		 //ImageIcon icon = new ImageIcon(imgOriginal);
		 label.setIcon(icon);
	}
	
	private void actualizarLista() {
		modeloListPremios.clear();
		modeloListPremios.addAll(premios.getSeleccion());
	}
	
	private void actualizarCampoPuntos() {
		getTfPuntosRestantes().setText(String.valueOf(premios.getPuntosRestantes()));
	}
	
	private void anadirProducto(ActionEvent e) {
		
		Premio premioAAnadir = premios.getCatalogo(filtroActual)[Integer.parseInt(((JButton) e.getSource()).getActionCommand())];
		premios.add(premioAAnadir, 1);
		actualizarLista();
		actualizarCampoPuntos();
		comprobarCampos();
		deshabilitarNoAccesibles();
		getPnPestanas().setSelectedIndex(0);
		
		
	}
	
	private void reiniciarSeleccion() {
		premios.reiniciarSeleccion();
		deshabilitarNoAccesibles();
		modeloListPremios.clear();
		actualizarCampoPuntos();
		validate();
		repaint();
	}
	
	private void eliminarProducto(ActionEvent e) {
		
		if(getListaPedido().getSelectedValue() == null) {
			return;
		}
		
		int[] indicesSeleccionados = getListaPedido().getSelectedIndices();
		
		premios.remove(indicesSeleccionados);
		
		actualizarLista();
		actualizarCampoPuntos();
		comprobarCampos();
		deshabilitarNoAccesibles();
		validate();
		repaint();
		
	}
	
	private void mostrarDatos(MouseEvent e) {
		JButton boton = (JButton) e.getSource();
		int actionCommand = Integer.parseInt(boton.getActionCommand());
		String denominacion = premios.getCatalogo(filtroActual)[actionCommand].getDenominacion();
		String descripcion = premios.getCatalogo(filtroActual)[actionCommand].getDescripcion();
		int valor = premios.getCatalogo(filtroActual)[actionCommand].getValor();
		
		getLbDenominacion().setText(denominacion);
		getTxtrDescripcion().setText(descripcion);
		getLblPuntos().setText("Puntos: "+String.valueOf(valor)+mensajeAccesible(premios.getCatalogo(filtroActual)[actionCommand]));
		setImagenAdaptada(getLbImagenProducto(), premios.getCatalogo(filtroActual)[actionCommand].getRutaImagen());
		
	}
	
	private String mensajeAccesible(Premio p) {
		if(p.esAccesible(premios.getPuntosRestantes())) {
			return " | "+ mensajes.getString("premios.suficientes");
		} else {
			return " | "+ mensajes.getString("premios.nosuficientes");
		}
	}
	
	private void comprobarCampos() {
		if(premios.getSeleccion().isEmpty()) {
			getBtEliminar().setEnabled(false);
			getBtnReiniciar().setEnabled(false);
			getBtSiguiente().setEnabled(false);
		} else {
			getBtEliminar().setEnabled(true);
			getBtnReiniciar().setEnabled(true);
			getBtSiguiente().setEnabled(true);
		}
	}
	
	private void deshabilitarNoAccesibles() {
		for(int i=0; i<getPnCatalogo().getComponentCount(); i++) {
			Premio p  = premios.getCatalogo(filtroActual)[i];
			if(!p.esAccesible(premios.getPuntosRestantes())) {
				getPnCatalogo().getComponent(i).setEnabled(false);
			} else {
				getPnCatalogo().getComponent(i).setEnabled(true);
			}
		}
		
		comprobarCampos();
	}
	
	private void mostrarFormularioDni() {
		try {
			VentanaDNI vD = new VentanaDNI(premios, getCodigoTienda(), localizacion);
			vD.setVisible(true);
			this.dispose();
		} catch(Exception e) {
			e.printStackTrace();
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
	
	private void mostrarMensajeDeConfirmacion() {
		
		String mensaje = mensajes.getString("cuadro.conf1");
		
		if(premios.getPuntosRestantes() > 0) {
			mensaje =  mensajes.getString("cuadro.conf2") + " " +
					   + premios.getPuntosRestantes() + " " + mensajes.getString("cuadro.conf3");
		}
		
		int respuesta = JOptionPane.showConfirmDialog(this, mensaje);
		
		if(respuesta == JOptionPane.YES_OPTION) {
			mostrarFormularioDni();
		}
		
	}
	
	// Gestión de eventos.

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public void setCodigoTienda(String codigoTienda) {
		this.codigoTienda = codigoTienda;
	}

	class MostrarDatos extends MouseAdapter {

		public void mouseEntered(MouseEvent e) {
			mostrarDatos(e);
		}
		
	}
	
	class AnadirProducto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			anadirProducto(e);
		}
		
	}
	
	class EliminarProducto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			eliminarProducto(e);
		}
		
	}
	
	class ReiniciarSeleccion implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			 reiniciarSeleccion();
		}
		
	}
	
	class FinalizarPedido implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mostrarMensajeDeConfirmacion();
		}
		
	}
	
	// Internacionalizacion
	
	private void setIdioma(Locale loc) {
		localizacion =  loc;
		mensajes =  ResourceBundle.getBundle("inter/aplicacion", localizacion);
	}
}
