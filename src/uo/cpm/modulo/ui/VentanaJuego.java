package uo.cpm.modulo.ui;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import uo.cpm.modulo.game.model.Casilla;
import uo.cpm.modulo.game.sound.GestionDeSonido;
import uo.cpm.modulo.service.GestionDeJuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Toolkit;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.InputEvent;
import javax.swing.JCheckBoxMenuItem;

public class VentanaJuego extends JFrame {

	// Constantes.
	private static final long serialVersionUID = 1L;
	private static final int DIM = 7;
	private static final int NUM_BOTONES = 49;
	
	// Paneles.
	private JPanel pnPrincipal;
	private JPanel pnTablero;
	private JPanel pnHilera;
	
	// Gestión de eventos
	private SeleccionarInvasor sI = new SeleccionarInvasor();
	private ColocarInvasor cI = new ColocarInvasor();
	private SiguienteVentana sV = new SiguienteVentana();
	private MostrarAcercaDe aD = new MostrarAcercaDe();
	
	// Juego.
	GestionDeJuego juego = new GestionDeJuego();
	
	// Componentes.
	private JPanel pnJuego;
	private JPanel panelBotones;
	private JLabel lbRonda;
	private JLabel lbPuntuacion;
	private JMenuBar mbMenu;
	private JPanel pnAyudaInter;
	private JButton btnAyuda;
	private JButton btPremios;
	private JMenu mnAyuda;
	private JMenu mnJuego;
	private JMenuItem mniContenidos;
	private JSeparator spAyuda;
	private JMenuItem mniAcercaDe;
	private JCheckBoxMenuItem chmSonido;
	private JSeparator separadorJuego;
	private JMenuItem mniSalir;
	
	private String codigoTienda;
	
	// Internacionalizacion
	private Locale localizacion;
	private ResourceBundle mensajes;

	/**
	 * Create the frame.
	 */
	public VentanaJuego(String codigoTienda, Locale id) {
		
		this.setCodigoTienda(codigoTienda);
		setIdioma(id);
		
		
		// Indicamos el límite inferior al redimensionar la ventana.
		this.setMinimumSize(new Dimension(100, 100));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaJuego.class.getResource("/img/fantasma.png")));
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Adaptar la imagen al tamaño de los botones.
				repintarHilera();
				repintarTablero();
			}
		});
		
		setTitle(mensajes.getString("titulo.general"));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 700, 720);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setJMenuBar(getMenuBar_1());

		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(new BorderLayout(0, 0));
		pnPrincipal.add(getPnJuego());
		
		crearBotonesTablero();
		crearBotonesHilera();
		repintarHilera();
		actualizarCamposDeTexto();
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
			hb.enableHelpOnButton(getBtnAyuda(), "juego", hs);
			hb.enableHelpOnButton(getBtnAyuda(), "juego", hs);
			hb.enableHelpOnButton(getMniContenidos(), "introduccion", hs);
			
	}
	
	// Componentes gráficos.
	
	private JPanel getPnTablero() {
		if (pnTablero == null) {
			pnTablero = new JPanel();
			pnTablero.setBackground(new Color(169, 169, 169));
			pnTablero.setBorder(null);
			pnTablero.setLayout(new GridLayout(7, 7, 2, 2));
		}
		return pnTablero;
	}
	
	private JPanel getPnHilera() {
		if (pnHilera == null) {
			pnHilera = new JPanel();
			pnHilera.setBackground(new Color(169, 169, 169));
			pnHilera.setBorder(null);
			pnHilera.setLayout(new GridLayout(0, 5, 2, 2));
		}
		return pnHilera;
	}
	
	private JPanel getPnJuego() {
		if (pnJuego == null) {
			pnJuego = new JPanel();
			pnJuego.setLayout(new BorderLayout(0, 0));
			pnJuego.add(getPnHilera(), BorderLayout.NORTH);
			pnJuego.add(getPnTablero(), BorderLayout.CENTER);
			pnJuego.add(getPanelBotones(), BorderLayout.SOUTH);
		}
		return pnJuego;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setBackground(new Color(0, 100, 0));
			panelBotones.setLayout(new GridLayout(1, 3, 0, 0));
			panelBotones.add(getLbRonda());
			panelBotones.add(getLbPuntuacion());
			panelBotones.add(getPnAyudaInter());
		}
		return panelBotones;
	}
	private JLabel getLbRonda() {
		if (lbRonda == null) {
			lbRonda = new JLabel(mensajes.getString("juego.ronda")+": 0");
			lbRonda.setBackground(new Color(34, 139, 34));
			lbRonda.setForeground(new Color(255, 255, 255));
			lbRonda.setFont(new Font("Gill Sans MT", Font.PLAIN, 20));
		}
		return lbRonda;
	}
	private JLabel getLbPuntuacion() {
		if (lbPuntuacion == null) {
			lbPuntuacion = new JLabel(mensajes.getString("juego.puntuacion") + "0");
			lbPuntuacion.setForeground(new Color(255, 255, 255));
			lbPuntuacion.setFont(new Font("Gill Sans MT", Font.PLAIN, 20));
		}
		return lbPuntuacion;
	}
	private JMenuBar getMenuBar_1() {
		if (mbMenu == null) {
			mbMenu = new JMenuBar();
			mbMenu.setBackground(new Color(0, 100, 0));
			mbMenu.add(getMnJuego());
			mbMenu.add(getMnAyuda());
		}
		return mbMenu;
	}
	private JMenu getMnAyuda() {
		if (mnAyuda == null) {
			mnAyuda = new JMenu(mensajes.getString("general.ayuda"));
			mnAyuda.setForeground(Color.WHITE);
			mnAyuda.setBackground(new Color(0, 100, 0));
			mnAyuda.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			mnAyuda.setMnemonic('A');
			mnAyuda.add(getMniContenidos());
			mnAyuda.add(getSpAyuda());
			mnAyuda.add(getMniAcercaDe());
		}
		return mnAyuda;
	}
	private JMenu getMnJuego() {
		if (mnJuego == null) {
			mnJuego = new JMenu(mensajes.getString("juego.menujuego"));
			mnJuego.setForeground(Color.WHITE);
			mnJuego.setBackground(new Color(0, 100, 0));
			mnJuego.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			mnJuego.setMnemonic('I');
			mnJuego.add(getChmSonido());
			mnJuego.add(getSeparadorJuego());
			mnJuego.add(getMniSalir());
		}
		return mnJuego;
	}
	private JPanel getPnAyudaInter() {
		if (pnAyudaInter == null) {
			pnAyudaInter = new JPanel();
			pnAyudaInter.setLayout(new GridLayout(0, 2, 0, 0));
			pnAyudaInter.add(getBtnAyuda());
			pnAyudaInter.add(getBtPremios());
		}
		return pnAyudaInter;
	}
	private JButton getBtnAyuda() {
		if (btnAyuda == null) {
			btnAyuda = new JButton(mensajes.getString("general.ayuda"));
			btnAyuda.setIcon(new ImageIcon(VentanaJuego.class.getResource("/img/icono_ayuda.png")));
			btnAyuda.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btnAyuda.setBackground(new Color(255, 255, 255));
		}
		return btnAyuda;
	}
	private JButton getBtPremios() {
		if (btPremios == null) {
			btPremios = new JButton(mensajes.getString("juego.premios"));
			btPremios.setEnabled(false);
			btPremios.setMnemonic('P');
			btPremios.setIcon(new ImageIcon(VentanaJuego.class.getResource("/img/icono_premios.png")));
			btPremios.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
			btPremios.addActionListener(sV);
			btPremios.setBackground(new Color(255, 255, 255));
		}
		return btPremios;
	}
	
	private JMenuItem getMniContenidos() {
		if (mniContenidos == null) {
			mniContenidos = new JMenuItem(mensajes.getString("juego.menucontenidos"));
			mniContenidos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			mniContenidos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		}
		return mniContenidos;
	}
	private JSeparator getSpAyuda() {
		if (spAyuda == null) {
			spAyuda = new JSeparator();
		}
		return spAyuda;
	}
	private JMenuItem getMniAcercaDe() {
		if (mniAcercaDe == null) {
			mniAcercaDe = new JMenuItem(mensajes.getString("general.acercade"));
			mniAcercaDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
			mniAcercaDe.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			mniAcercaDe.addActionListener(aD);
		}
		return mniAcercaDe;
	}
	
	// Métodos privados
	
	// Método que crea y configura los botones de la hilera.
	private void crearBotonesHilera() {
		getPnHilera().removeAll();
		for(int i=0; i<5; i++) {
			getPnHilera().add(new JButton(""));
			getPnHilera().getComponent(i).setBackground(Color.WHITE);
			((JButton) getPnHilera().getComponent(i)).setActionCommand(String.valueOf(i));
			((JButton)getPnHilera().getComponent(i)).addActionListener(sI);
			
		}
	}
	
	// Método que crea y configura los botones del tablero.
	private void crearBotonesTablero() {
		for(int i=0; i<NUM_BOTONES; i++) {
			getPnTablero().add(new JButton(""));
			JButton boton = ((JButton) getPnTablero().getComponent(i));
			boton.setActionCommand(String.valueOf(i));
			Casilla casilla = juego.getTablero()[juego.obtenerCoordenadaX(i)][juego.obtenerCoordenadaY(i)];
			if(casilla.isProhibida()) {
				boton.setEnabled(false);
				boton.setBackground(Color.GRAY);
			} else {
				boton.setBackground(Color.WHITE);
				boton.addActionListener(cI);
				boton.setToolTipText(mensajes.getString("tooltip.colocar"));
			}
			
			boton.addActionListener(cI);
			
		}
	}
	// Método que "repinta" la hilera del modelo. 
	private void repintarHilera() {
		JButton boton = null;
		for(int i=0; i<juego.getHilera().length; i++) {
			boton = ((JButton) getPnHilera().getComponent(i));
			boton.setToolTipText(mensajes.getString("tooltip.seleccionar")+": "+juego.getHilera()[i].getOcupante().getTipo());
			URL rutaImagen = getClass().getResource(juego.getHilera()[i].getOcupante().getImagen());
			boton.setIcon(new ImageIcon(rutaImagen));
			//setImagenAdaptada(boton, juego.getHilera()[i].getOcupante().getImagen());
		}
	}
	// Método que repinta el estado del tablero
	private void repintarTablero() {
		for(int i=0; i<DIM; i++) {
			for(int j=0; j<DIM; j++) {
				if(juego.getTablero()[i][j].getOcupante() != null) {
					JButton boton = obtenerBoton(i, j);
					setImagenAdaptada(boton, juego.getTablero()[i][j].getOcupante().getImagen());
					if(juego.getTablero()[i][j].isOcupada() == false) {
						boton.setIcon(null);
					//boton.setIcon(new ImageIcon(getClass().getResource(juego.getTablero()[i][j].getOcupante().getImagen())));
					}
				
			} else {
				JButton boton = obtenerBoton(i, j);
				boton.setIcon(null);
			}
			}
		}
		validate();
		repaint();
	}
	
	// Método que asocia las etiquetas al número de ronda y a la puntuacion.
	private void actualizarCamposDeTexto() {
		getLbRonda().setText(mensajes.getString("juego.ronda")+ ": " +String.valueOf(juego.getRonda())+"/10");
		getLbPuntuacion().setText(mensajes.getString("juego.puntuacion")+ ": " +String.valueOf(juego.getPuntuacion()));
	}
	
	private void setImagenAdaptada(JButton boton, String rutaImagen){
		 Image imgOriginal = new ImageIcon(getClass().getResource(rutaImagen)).getImage(); 
		 Image imgEscalada = imgOriginal.getScaledInstance(boton.getWidth(),boton.getHeight(), Image.SCALE_SMOOTH);
		 ImageIcon icon = new ImageIcon(imgEscalada);
		 //ImageIcon icon = new ImageIcon(imgOriginal);
		 boton.setIcon(icon);
	}
	
	// Método que obtiene una referencia al botón que ocupa las coordenadas que se pasan por parámetro.
	private JButton obtenerBoton(int x, int y) {
		return (JButton) getPnTablero().getComponent(juego.obtenerNumeroDeCasilla(x, y));
	}
	
	// Método para que el usuario seleccione un invasor.
	private void seleccionarInvasor(ActionEvent e) {
		if(juego.getInvasorSeleccionado() == null) {
			juego.selectInvasor(juego.getHilera()[Integer.parseInt(e.getActionCommand())]);
			((JButton) e.getSource()).setEnabled(false);
			repintarHilera();
			reproducirAudio("src/sound/coger.wav");
		}
	}
	
	// Método que coloca el invasor seleccionado en el tablero.
	private void colocarInvasor(ActionEvent e) {
		
		if(juego.getInvasorSeleccionado() == null) {
			return;
		}
		
		validarFinDeJuego();
		int coordX = juego.obtenerCoordenadaX(Integer.parseInt(e.getActionCommand()));
		int coordY = juego.obtenerCoordenadaY(Integer.parseInt(e.getActionCommand()));
		boolean cond = juego.colocarInvasor(coordX, coordY);
		if(cond) {
			repintarHilera();
			habilitarBotonesHilera();
			actualizarCamposDeTexto();
		}
		reproducirAudio("src/sound/coloca.wav");
		repintarTablero();
		validarFinDeJuego();
		
	}
	
	// Método para habilitar los botones de la hilera al terminar una ronda.
	private void habilitarBotonesHilera() {
		for(int i=0; i<getPnHilera().getComponentCount(); i++) {
			getPnHilera().getComponent(i).setEnabled(true);
		}
	}
	
	// Método que abre la ventana de premios.
	private void mostrarVentanaPremios() {
		try {
			VentanaPremios vp = new VentanaPremios(juego.getPuntuacion(), getCodigoTienda(), localizacion);
			vp.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void mostrarVentanaInicial() {
		try {
			VentanaInicial vI = new VentanaInicial();
			vI.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void acceder() {
		this.dispose();
		mostrarVentanaPremios();
		
	}
	
	private void derrota() {
		if(juego.derrota()) {
			JOptionPane.showMessageDialog(null, mensajes.getString("cuadro.pierde"));
			dispose();
			mostrarVentanaInicial();
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
	
	private void validarFinDeJuego() {
		derrota();
		if(juego.finalizado()) {
			getBtPremios().setEnabled(true);
			deshabilitarBotones();
			getLbRonda().setText(mensajes.getString("juego.fin"));
			reproducirAudio("src/sound/excellent.wav");
		}
	}
	
	private void deshabilitarBotones() {
		for(int i=0; i<getPnHilera().getComponentCount(); i++) {
			getPnHilera().getComponent(i).setEnabled(false);
		}
		
		for(int i=0; i<getPnTablero().getComponentCount(); i++) {
			getPnTablero().getComponent(i).setEnabled(false);
		}
	}
	
	private void mostrarInvasorSeleccionado() {
		
		if(juego.getInvasorSeleccionado() != null) {
			for(int i=0; i<getPnHilera().getComponentCount(); i++) {
				if(juego.getInvasorSeleccionado().equals(juego.getHilera()[i].getOcupante())) {
					getPnHilera().getComponent(i).setBackground(new Color(152, 251, 152));
				}
			}
		} else {
			for(int i=0; i<getPnHilera().getComponentCount(); i++) {
				getPnHilera().getComponent(i).setBackground(Color.WHITE);
			}
		}
		
	}
	
	
	// Gestión de eventos
	
	class SeleccionarInvasor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			seleccionarInvasor(e);
			mostrarInvasorSeleccionado();
		}
		
	}
	
	class ColocarInvasor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			colocarInvasor(e);
			mostrarInvasorSeleccionado();
		}
		
	}
	
	class SiguienteVentana implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			acceder();
		}
		
	}
	
	class MostrarAcercaDe implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mostrarAcercaDe();
		}
		
	}
	
	private JCheckBoxMenuItem getChmSonido() {
		if (chmSonido == null) {
			chmSonido = new JCheckBoxMenuItem(mensajes.getString("juego.menusonido"));
			chmSonido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
			chmSonido.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					configuracionSonido();
				}
			});
			chmSonido.setSelected(true);
		}
		return chmSonido;
	}
	
	private JSeparator getSeparadorJuego() {
		if (separadorJuego == null) {
			separadorJuego = new JSeparator();
		}
		return separadorJuego;
	}
	
	private JMenuItem getMniSalir() {
		if (mniSalir == null) {
			mniSalir = new JMenuItem(mensajes.getString("juego.menusalir"));
			mniSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
			mniSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					salir();
				}
			});
		}
		return mniSalir;
	}
	
	private void configuracionSonido() {
		if(getChmSonido().isSelected()) {
			juego.setSonido(true);
		} else {
			juego.setSonido(false);
		}
	}

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public void setCodigoTienda(String codigoTienda) {
		this.codigoTienda = codigoTienda;
	}
	
	// Sonido
	
	private void reproducirAudio(String ruta) {
		if(juego.isSonido()) {
			GestionDeSonido.reproducirSonido(ruta);
		}
	}
	
	// Internacionalizacion
	
	private void setIdioma(Locale loc) {
		localizacion =  loc;
		mensajes =  ResourceBundle.getBundle("inter/aplicacion", localizacion);
	}
}
