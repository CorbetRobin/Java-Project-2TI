package tupac;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class FenetrePrincipale extends JFrame {

	private Container cont;
	private PanelAceuil jpAccueil;
	private JMenuBar jmbMenu;
	private JMenu jmMenu, jmIntervention, jmRecherche, jmCredit;
	private JMenuItem jmiCon, jmiDeco, jmiQuiter, jmiAcceuil, jmiAjout, jmiSup,
			jmiRech, jmiCredit;
	private MyListener myListener;
	private Connection connection;

	public FenetrePrincipale() {
		super("Projet Java Corbet / Deschepper");
		this.pack();
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		jmbMenu = new JMenuBar();
		myListener = new MyListener();

		jmMenu = new JMenu("Menu");
		jmbMenu.add(jmMenu);
		jmiCon = new JMenuItem("Connection");
		jmMenu.add(jmiCon);
		jmiCon.addActionListener(myListener);
		jmiDeco = new JMenuItem("Déconnection");
		jmiDeco.setEnabled(false);
		jmMenu.add(jmiDeco);
		jmiDeco.addActionListener(myListener);
		jmiAcceuil = new JMenuItem("Acceuil");
		jmMenu.add(jmiAcceuil);
		jmiAcceuil.addActionListener(myListener);
		jmiQuiter = new JMenuItem("Quitter");
		jmMenu.add(jmiQuiter);
		jmiQuiter.addActionListener(myListener);

		jmIntervention = new JMenu("Intervention");
		jmIntervention.setEnabled(false);
		jmbMenu.add(jmIntervention);
		jmiAjout = new JMenuItem("Ajouter");
		jmIntervention.add(jmiAjout);
		jmiAjout.addActionListener(myListener);
		jmiSup = new JMenuItem("Supprimer");
		jmIntervention.add(jmiSup);
		jmiSup.addActionListener(myListener);

		jmRecherche = new JMenu("Recherche");
		jmRecherche.setEnabled(false);
		jmbMenu.add(jmRecherche);
		jmiRech = new JMenuItem("Rechercher");
		jmRecherche.add(jmiRech);
		jmiRech.addActionListener(myListener);

		jmCredit = new JMenu("Crédit");
		jmbMenu.add(jmCredit);
		jmiCredit = new JMenuItem("Crédit");
		jmCredit.add(jmiCredit);
		jmiCredit.addActionListener(myListener);

		setJMenuBar(jmbMenu);

		jpAccueil = new PanelAceuil();
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		cont.add(jpAccueil, BorderLayout.CENTER);

		setVisible(true);

	}

	public void setConnection(Connection c) {
		connection = c;
	}

	public Connection getConection() {
		return connection;
	}

	private class MyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == jmiCredit) {
				PanelCredit panelCredit = new PanelCredit();
				cont.removeAll();
				cont.add(panelCredit, BorderLayout.CENTER);
				cont.repaint();
				FenetrePrincipale.this.setVisible(true);
			}

			else if (e.getSource() == jmiCon) {
				FenConnection fenCo = new FenConnection(FenetrePrincipale.this);
				jmiDeco.setEnabled(true);
				jmIntervention.setEnabled(true);
				jmRecherche.setEnabled(true);
			}

			else if (e.getSource() == jmiAcceuil) {
				cont.removeAll();
				cont.add(jpAccueil, BorderLayout.CENTER);
				cont.repaint();
				FenetrePrincipale.this.setVisible(true);
			}

			else if (e.getSource() == jmiDeco) {
				try {
					connection.close();
					jmiDeco.setEnabled(false);
					jmIntervention.setEnabled(false);
					jmRecherche.setEnabled(false);
				}

				catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERREUR", "ERREUR",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			else if (e.getSource() == jmiQuiter) {
				System.exit(0);
			}

			else if (e.getSource() == jmiAjout) {
				try {
					PanelAjout panelAjout = new PanelAjout(
							FenetrePrincipale.this);
					cont.removeAll();
					cont.add(panelAjout, BorderLayout.CENTER);
					cont.repaint();
					FenetrePrincipale.this.setVisible(true);
				}

				catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERREUR", "ERREUR",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			else if (e.getSource() == jmiRech) {
				try {
					PanelListing panelListing = new PanelListing(
							FenetrePrincipale.this);
					cont.removeAll();
					cont.add(panelListing, BorderLayout.CENTER);
					cont.repaint();
					FenetrePrincipale.this.setVisible(true);
				}

				catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			else if (e.getSource() == jmiSup) {
				try {
					PanelSup panelSup = new PanelSup(FenetrePrincipale.this);
					cont.removeAll();
					cont.add(panelSup, BorderLayout.CENTER);
					cont.repaint();
					FenetrePrincipale.this.setVisible(true);
				}

				catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

}
