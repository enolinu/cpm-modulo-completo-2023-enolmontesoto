package uo.cpm.modulo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class DialogoAcercaDe extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel pnPrincipal = new JPanel();
	private JLabel lbGuapo;

	/**
	 * Create the dialog.
	 */
	public DialogoAcercaDe() {
		setResizable(false);
		setTitle("\u00A1De Vuelta A Los Cl\u00E1sicos!: Acerca De");
		setBounds(100, 100, 510, 340);
		getContentPane().setLayout(new BorderLayout());
		pnPrincipal.setBackground(new Color(255, 255, 255));
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnPrincipal, BorderLayout.CENTER);
		pnPrincipal.setLayout(null);
		setModal(true);
		{
			JTextArea txtInfo = new JTextArea();
			txtInfo.setEditable(false);
			txtInfo.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
			txtInfo.setText("Autor: Enol Monte Soto (PL-6)\r\nDNI: 58434940M\r\n\r\nComunicaci\u00F3n Persona-M\u00E1quina (2022-2023)\r\nEscuela De Ingenier\u00EDa Inform\u00E1tica\r\nUniversidad De Oviedo");
			txtInfo.setToolTipText("");
			txtInfo.setLineWrap(true);
			txtInfo.setWrapStyleWord(true);
			txtInfo.setBounds(192, 71, 366, 143);
			pnPrincipal.add(txtInfo);
		}
		pnPrincipal.add(getLbGuapo());
		{
			JPanel panelBoton = new JPanel();
			panelBoton.setBackground(new Color(0, 128, 0));
			panelBoton.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(panelBoton, BorderLayout.SOUTH);
			{
				JButton btCerrar = new JButton("Cerrar");
				btCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btCerrar.setBackground(new Color(255, 255, 255));
				btCerrar.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
				btCerrar.setActionCommand("OK");
				panelBoton.add(btCerrar);
				getRootPane().setDefaultButton(btCerrar);
			}
		}
		setLocationRelativeTo(null);
	}
	private JLabel getLbGuapo() {
		if (lbGuapo == null) {
			lbGuapo = new JLabel("");
			lbGuapo.setToolTipText("No necesito ToolTip");
			lbGuapo.setBorder(new LineBorder(new Color(0, 128, 0), 3, true));
			lbGuapo.setHorizontalAlignment(SwingConstants.CENTER);
			lbGuapo.setHorizontalTextPosition(SwingConstants.CENTER);
			lbGuapo.setIcon(new ImageIcon(DialogoAcercaDe.class.getResource("/img/guapo.png")));
			lbGuapo.setBackground(new Color(255, 255, 255));
			lbGuapo.setBounds(10, 40, 172, 189);
		}
		return lbGuapo;
	}
}
