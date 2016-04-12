package tupac;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;

public class PanelAjout extends JPanel {

	// Déclaration de JPanel (jp)
	private JPanel jpNoInterv, jpDateSign, jpDescr, jpSign, jpPrenCh, jpEtat,
			jpSuivi, jpTemps, jpRes, jpPc, jpType, jpFourn, jpDateContact,
			jpDatePrise, jpDateRetour, jpEtatRetour, jpBouton, jpRemis;

	// Déclaration des JLabel (jl)
	private JLabel jlNoInterv, jlDateSign, jlDescr, jlSign, jlPrenCh, jlEtat,
			jlSuivi, jlTemps, jlRes, jlPc, jlType, jlFourn, jlDateContact,
			jlDatePrise, jlDateRetour, jlEtatRetour, jlIdFourn, jlIdType,
			jlRemise;

	// Déclaration de JTextField (jtf)
	private JTextField jtfNoInterv, jtfDescr, jtfSign, jtfPrenCh, jtfTemps,
			jtfIdFourn, jtfIdType;

	// Déclaration des JComboBox (jcb)
	private JComboBox<String> jcbAnneeSign, jcbMoisSign, jcbJoursSign, jcbPc,
			jcbType, jcbFourn, jcbAnneeContact, jcbMoisContact,
			jcbJoursContact, jcbAnneePrise, jcbMoisPrise, jcbJoursPrise,
			jcbAnneeRetour, jcbMoisRetour, jcbJoursRetour, jcbAnneeRemis,
			jcbMoisRemis, jcbJoursRemis;

	// Déclaration et création du tableau pour les possibilitée de jcbJoursSign
	private String tabJ[] = { "JJ", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
			"31" };

	// Déclaration et création du tableau pour les possibilitée de jcbMoisSign
	private String tabM[] = { "MM", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "11", "12" };

	// Déclaration et création du tableau pour les possibilitée de jcA
	private String tabA[] = { "AAAA", "2014", "2015", "2016", "2017", "2018",
			"2019", "2020" };

	// Déclaration des JCheckBox (jkb)
	private JCheckBox jkbEtatSignale, jkbEtatEnCours, jkbEtatCloture,
			jkbSuiviOui, jkbSuiviNon, jkbResOk, jkbResDeclasse, jkbResSuspens,
			jkbRetourOk, jkbRetourDeclasse, jkbRetourSuspens;

	// Création des JButton
	private JButton jbOk, jbRetour, jbReset;

	// Création de JButtonGroup
	private ButtonGroup jbgEtatInterv, jbgSuivi, jbgEtatRetour, jbgResultat;

	// Création du listener myListener
	private MyListener myListener = new MyListener();

	// Création de la Fenetre Principal
	private FenetrePrincipale fp;

	PanelAjout(FenetrePrincipale f) throws SQLException {

		fp = f;

		// Création des JLabel
		jlNoInterv = new JLabel("Numéro d'intervention : ");
		jlDateSign = new JLabel("Date de l'intervention : ");
		jlDescr = new JLabel("Descriptif : ");
		jlSign = new JLabel("Signaleur : ");
		jlPrenCh = new JLabel("Preneur en charge : ");
		jlEtat = new JLabel("Etat de l'intervention : ");
		jlSuivi = new JLabel("Suivi Fournisseur : ");
		jlTemps = new JLabel("Temps de l'intervention : ");
		jlRes = new JLabel("Résultat : ");
		jlPc = new JLabel("PC : ");
		jlType = new JLabel("Type d'intervention : ");
		jlFourn = new JLabel("Fournisseur : ");
		jlDateContact = new JLabel("Date de contacte : ");
		jlDatePrise = new JLabel("Date de reprise : ");
		jlDateRetour = new JLabel("Date de retours : ");
		jlEtatRetour = new JLabel("Etat de retour");
		jlIdFourn = new JLabel("FournisseurID : ");
		jlIdType = new JLabel("CodeTypeInt : ");
		jlRemise = new JLabel("Date de remise en service : ");

		// Création des JTextField
		jtfNoInterv = new JTextField(this.noInterv(), 3);
		jtfNoInterv.setEditable(false);

		jtfDescr = new JTextField("", 20);
		jtfSign = new JTextField("", 20);
		jtfPrenCh = new JTextField("", 20);
		jtfTemps = new JTextField("", 10);

		jtfIdType = new JTextField("", 3);
		jtfIdType.setEditable(false);

		jtfIdFourn = new JTextField("", 3);
		jtfIdFourn.setEditable(false);

		// Création des JComboBox
		jcbJoursSign = new JComboBox<String>(tabJ);
		jcbJoursSign.setMaximumRowCount(10);

		jcbMoisSign = new JComboBox<String>(tabM);
		jcbMoisSign.setMaximumRowCount(10);

		jcbAnneeSign = new JComboBox<String>(tabA);
		jcbAnneeSign.setMaximumRowCount(10);

		jcbPc = new JComboBox<String>();
		jcbPc.setMaximumRowCount(10);
		jcbPc.addItem("");
		pcConnector();

		jcbType = new JComboBox<String>();
		jcbType.setMaximumRowCount(10);
		jcbType.addItem("");
		jcbType.addActionListener(myListener);
		typeConnector();

		jcbFourn = new JComboBox<String>();
		jcbFourn.setMaximumRowCount(10);
		jcbFourn.addItem("");
		jcbFourn.addActionListener(myListener);
		fournConnector();

		jcbJoursContact = new JComboBox<String>(tabJ);
		jcbJoursContact.setMaximumRowCount(10);

		jcbMoisContact = new JComboBox<String>(tabM);
		jcbMoisContact.setMaximumRowCount(10);

		jcbAnneeContact = new JComboBox<String>(tabA);
		jcbAnneeContact.setMaximumRowCount(10);

		jcbJoursRetour = new JComboBox<String>(tabJ);
		jcbJoursRetour.setMaximumRowCount(10);

		jcbMoisRetour = new JComboBox<String>(tabM);
		jcbMoisRetour.setMaximumRowCount(10);

		jcbAnneeRetour = new JComboBox<String>(tabA);
		jcbAnneeRetour.setMaximumRowCount(10);

		jcbJoursPrise = new JComboBox<String>(tabJ);
		jcbJoursPrise.setMaximumRowCount(10);

		jcbMoisPrise = new JComboBox<String>(tabM);
		jcbMoisPrise.setMaximumRowCount(10);

		jcbAnneePrise = new JComboBox<String>(tabA);
		jcbAnneePrise.setMaximumRowCount(10);

		jcbAnneeRemis = new JComboBox<String>(tabA);
		jcbAnneeRemis.setMaximumRowCount(10);

		jcbMoisRemis = new JComboBox<String>(tabM);
		jcbMoisRemis.setMaximumRowCount(10);

		jcbJoursRemis = new JComboBox<String>(tabJ);
		jcbJoursRemis.setMaximumRowCount(10);

		// Création des JCheckBox
		jkbEtatSignale = new JCheckBox("Signalé");
		jkbEtatSignale.setSelected(true);

		jkbEtatEnCours = new JCheckBox("En Cours");

		jkbEtatCloture = new JCheckBox("Cloturé");

		jkbSuiviOui = new JCheckBox("Oui");
		jkbSuiviOui.addActionListener(myListener);
		jkbSuiviOui.setSelected(true);

		jkbSuiviNon = new JCheckBox("Non");
		jkbSuiviNon.addActionListener(myListener);

		jkbResOk = new JCheckBox("OK");

		jkbResDeclasse = new JCheckBox("Déclassé");

		jkbResSuspens = new JCheckBox("Suspens");

		jkbRetourOk = new JCheckBox("OK");

		jkbRetourDeclasse = new JCheckBox("Déclassé");

		jkbRetourSuspens = new JCheckBox("Suspens");

		// Création et ajout dans les ButtonGroup
		jbgEtatInterv = new ButtonGroup();
		jbgEtatInterv.add(jkbEtatSignale);
		jbgEtatInterv.add(jkbEtatEnCours);
		jbgEtatInterv.add(jkbEtatCloture);

		jbgSuivi = new ButtonGroup();
		jbgSuivi.add(jkbSuiviOui);
		jbgSuivi.add(jkbSuiviNon);

		jbgEtatRetour = new ButtonGroup();
		jbgEtatRetour.add(jkbRetourOk);
		jbgEtatRetour.add(jkbRetourDeclasse);
		jbgEtatRetour.add(jkbRetourSuspens);

		jbgResultat = new ButtonGroup();
		jbgResultat.add(jkbResOk);
		jbgResultat.add(jkbResDeclasse);
		jbgResultat.add(jkbResSuspens);

		// Création des JButton
		jbOk = new JButton("Ok ! ");
		jbOk.addActionListener(myListener);

		jbRetour = new JButton("Retour");
		jbRetour.addActionListener(myListener);

		jbReset = new JButton("Reset");
		jbReset.addActionListener(myListener);

		// Création des JPanel
		jpNoInterv = new JPanel();
		jpNoInterv.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpNoInterv.add(jlNoInterv);
		jpNoInterv.add(jtfNoInterv);

		jpDateSign = new JPanel();
		jpDateSign.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpDateSign.add(jlDateSign);
		jpDateSign.add(jcbJoursSign);
		jpDateSign.add(jcbMoisSign);
		jpDateSign.add(jcbAnneeSign);

		jpDescr = new JPanel();
		jpDescr.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpDescr.add(jlDescr);
		jpDescr.add(jtfDescr);

		jpSign = new JPanel();
		jpSign.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpSign.add(jlSign);
		jpSign.add(jtfSign);

		jpPrenCh = new JPanel();
		jpPrenCh.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpPrenCh.add(jlPrenCh);
		jpPrenCh.add(jtfPrenCh);

		jpEtat = new JPanel();
		jpEtat.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpEtat.add(jlEtat);
		jpEtat.add(jkbEtatSignale);
		jpEtat.add(jkbEtatEnCours);
		jpEtat.add(jkbEtatCloture);

		jpSuivi = new JPanel();
		jpSuivi.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpSuivi.add(jlSuivi);
		jpSuivi.add(jkbSuiviOui);
		jpSuivi.add(jkbSuiviNon);

		jpTemps = new JPanel();
		jpTemps.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpTemps.add(jlTemps);
		jpTemps.add(jtfTemps);

		jpRes = new JPanel();
		jpRes.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpRes.add(jlRes);
		jpRes.add(jkbResOk);
		jpRes.add(jkbResDeclasse);
		jpRes.add(jkbResSuspens);

		jpPc = new JPanel();
		jpPc.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpPc.add(jlPc);
		jpPc.add(jcbPc);

		jpType = new JPanel();
		jpType.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpType.add(jlType);
		jpType.add(jcbType);
		jpType.add(jlIdType);
		jpType.add(jtfIdType);

		jpFourn = new JPanel();
		jpFourn.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpFourn.add(jlFourn);
		jpFourn.add(jcbFourn);
		jpFourn.add(jlIdFourn);
		jpFourn.add(jtfIdFourn);

		jpDateContact = new JPanel();
		jpDateContact.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpDateContact.add(jlDateContact);
		jpDateContact.add(jcbJoursContact);
		jpDateContact.add(jcbMoisContact);
		jpDateContact.add(jcbAnneeContact);

		jpDatePrise = new JPanel();
		jpDatePrise.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpDatePrise.add(jlDatePrise);
		jpDatePrise.add(jcbJoursPrise);
		jpDatePrise.add(jcbMoisPrise);
		jpDatePrise.add(jcbAnneePrise);

		jpDateRetour = new JPanel();
		jpDateRetour.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpDateRetour.add(jlDateRetour);
		jpDateRetour.add(jcbJoursRetour);
		jpDateRetour.add(jcbMoisRetour);
		jpDateRetour.add(jcbAnneeRetour);

		jpEtatRetour = new JPanel();
		jpEtatRetour.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpEtatRetour.add(jlEtatRetour);
		jpEtatRetour.add(jkbRetourOk);
		jpEtatRetour.add(jkbRetourDeclasse);
		jpEtatRetour.add(jkbRetourSuspens);

		jpBouton = new JPanel();
		jpBouton.setLayout(new FlowLayout(FlowLayout.CENTER));
		jpBouton.add(jbOk);
		jpBouton.add(jbRetour);
		jpBouton.add(jbReset);

		jpRemis = new JPanel();
		jpRemis.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpRemis.add(jlRemise);
		jpRemis.add(jcbJoursRemis);
		jpRemis.add(jcbMoisRemis);
		jpRemis.add(jcbAnneeRemis);

		// Ajout des JPanel au panel principal
		this.setLayout(new GridLayout(20, 2, 1, 1));
		this.add(jpNoInterv);
		this.add(jpDateSign);
		this.add(jpDescr);
		this.add(jpSign);
		this.add(jpPrenCh);
		this.add(jpEtat);

		this.add(jpSuivi);
		this.add(jpDateContact);
		this.add(jpDatePrise);
		this.add(jpDateRetour);
		this.add(jpEtatRetour);

		this.add(jpRemis);
		this.add(jpTemps);
		this.add(jpRes);
		this.add(jpPc);
		this.add(jpType);
		this.add(jpFourn);

		this.add(jpBouton);

	}

	// Méthode reste, elle remet l'ensemble de la fenètre à 0

	void reset() throws SQLException {
		// remise à 0 date signalement
		jcbJoursSign.setSelectedIndex(0);
		jcbMoisSign.setSelectedIndex(0);
		jcbAnneeSign.setSelectedIndex(0);

		// Remis à 0 du descriptif
		jtfDescr.setText("");

		// remise à 0 du nom du signaleur
		jtfSign.setText("");

		// remise à 0 du prenneur en charge
		jtfPrenCh.setText("");

		// Remise à 0 de l'état
		jkbEtatSignale.setSelected(true);

		// remise à 0 du suivi
		jkbSuiviOui.setSelected(true);

		// Remise à 0 de la date de contacte
		jcbJoursContact.setSelectedIndex(0);
		jcbMoisContact.setSelectedIndex(0);
		jcbAnneeContact.setSelectedIndex(0);

		// remise à 0 de la date de prise
		jcbJoursPrise.setSelectedIndex(0);
		jcbMoisPrise.setSelectedIndex(0);
		jcbAnneePrise.setSelectedIndex(0);

		// Remise à 0 de la date de retour
		jcbJoursRetour.setSelectedIndex(0);
		jcbMoisRetour.setSelectedIndex(0);
		jcbAnneeRetour.setSelectedIndex(0);

		// Remise à 0 de l'état de retour
		jbgEtatRetour.clearSelection();

		// Remise à 0 de la date de remise en service.
		jcbJoursRemis.setSelectedIndex(0);
		jcbMoisRemis.setSelectedIndex(0);
		jcbAnneeRemis.setSelectedIndex(0);

		// Remise à 0 du temps en interne
		jtfTemps.setText("");

		// remise à 0 du résultat
		jbgResultat.clearSelection();

		// Remise à 0 de PC Unit
		jcbPc.setSelectedIndex(0);

		// Remise à 0 du Type D'intervention
		jcbType.setSelectedIndex(0);

		// Remise à 0 du fournisseur
		jcbFourn.setSelectedIndex(0);

		jtfNoInterv.setText(noInterv());
	}

	// Méthode noInterv qui retourne le nb d'interv + 1 pour remplire le jtf
	private String noInterv() throws SQLException {
		String sqlNoInterv = ("SELECT MAX(NoInterv) FROM intervmaintenance.intervention;");
		PreparedStatement psNoInterv = fp.getConection().prepareStatement(
				sqlNoInterv);
		TableModelGen tmgNoInterv = AccessBDGen.creerTableModel(psNoInterv);
		int noInter = (int) tmgNoInterv.getValueAt(0, 0);
		noInter++;
		return String.valueOf(noInter);
	}

	// Création de la méthode pcConnector. Elle a pour bute de remplire la
	// JComboBox avec les valeurs présente dans la db

	public void pcConnector() {

		// Création de la requete à utiliser
		String pcSql = ("select distinct IdPcUnit from PcUnit");

		try {

			// Création du PreparedStatement qui utilise la requete pcSql
			PreparedStatement psPC = fp.getConection().prepareStatement(pcSql);

			// On récupère le résultat dans un objet de type TableModelGen
			TableModelGen tmgPC = AccessBDGen.creerTableModel(psPC);

			// On créee un boucle for affin d'ajouter tout le élément du tableau
			// tmgPC dans la JComboBox
			for (int i = 0; i < tmgPC.getRowCount(); i++)
				jcbPc.addItem(tmgPC.getValueAt(i, 0).toString());
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	// Méthode Descri

	// Création de la méthode typeConnector. Elle a pour but de remplire la jcb
	// avec les données de la db

	public void typeConnector() throws SQLException {

		String sqlType = ("SELECT DISTINCT LibelleTypeInt FROM TypeIntervention");

		try {
			PreparedStatement psType = fp.getConection().prepareStatement(
					sqlType);
			TableModelGen tmgType = AccessBDGen.creerTableModel(psType);

			for (int i = 0; i < tmgType.getRowCount(); i++)
				jcbType.addItem(tmgType.getValueAt(i, 0).toString());
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Création de la méthode fournConnector qui remplis le jcb Fournisseur

	public void fournConnector() throws SQLException {
		String sqlFourn = ("SELECT DISTINCT NomFourn FROM Fournisseur");

		try {

			PreparedStatement psFourn = fp.getConection().prepareStatement(
					sqlFourn);
			TableModelGen tmgFourn = AccessBDGen.creerTableModel(psFourn);

			for (int i = 0; i < tmgFourn.getRowCount(); i++)
				jcbFourn.addItem(tmgFourn.getValueAt(i, 0).toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Méthode pour les date (transforme les string de jcombo box en jours et
	// renvois NULL si l'une d'elle est pas remplie).
	public GregorianCalendar date(JComboBox j, JComboBox m, JComboBox a) {
		if (j.getSelectedItem().toString() == ("AAAA")
				|| m.getSelectedItem().toString() == ("MM")
				|| a.getSelectedItem().toString() == ("JJ"))
			return null;
		else {
			int jr = Integer.parseInt(j.getSelectedItem().toString());
			int mo = Integer.parseInt(m.getSelectedItem().toString());
			int an = Integer.parseInt(a.getSelectedItem().toString());

			GregorianCalendar date;
			date = new GregorianCalendar();
			date.set(GregorianCalendar.DAY_OF_MONTH, jr);
			date.set(GregorianCalendar.MONTH, mo);
			date.set(GregorianCalendar.YEAR, an);

			return date;

		}
	}

	public boolean verifDateSign() {

		if (date(jcbJoursSign, jcbMoisSign, jcbAnneeSign) == null) {
			jlDateSign.setForeground(new Color(200, 27, 65));
			jcbJoursSign.setForeground(new Color(200, 27, 65));
			jcbMoisSign.setForeground(new Color(200, 27, 65));
			jcbAnneeSign.setForeground(new Color(200, 27, 65));
			return false;
		} else {
			jlDateSign.setForeground(new Color(0, 0, 0));
			jcbJoursSign.setForeground(new Color(0, 0, 0));
			jcbMoisSign.setForeground(new Color(0, 0, 0));
			jcbAnneeSign.setForeground(new Color(0, 0, 0));
			return true;
		}
	}

	public boolean verifPrenCh() {
		if (jtfPrenCh.getText().isEmpty()) {
			jlPrenCh.setForeground(new Color(200, 27, 65));
			jtfPrenCh.setBackground(new Color(200, 80, 141));
			return false;
		} else {
			jlPrenCh.setForeground(new Color(0, 0, 0));
			jtfPrenCh.setForeground(new Color(0, 0, 0));
			return true;
		}
	}

	public boolean verifPc() {
		if (jcbPc.getSelectedItem() == ("")) {
			jlPc.setForeground(new Color(200, 27, 65));
			return false;
		} else {
			jlPc.setForeground(new Color(0, 0, 0));
			return true;
		}
	}

	public boolean verifType() {
		if (jcbType.getSelectedItem() == ("")) {
			jlType.setForeground(new Color(200, 27, 65));
			return false;
		} else {
			jlType.setForeground(new Color(0, 0, 0));
			return true;
		}
	}

	public String etat() {
		if (jkbEtatSignale.isSelected() == true)
			return ("Signalé");
		else if (jkbEtatEnCours.isSelected() == true)
			return ("Encours");
		else
			return ("Clôturé");
	}

	private class MyListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jkbSuiviNon) {
				if (jkbSuiviNon.isSelected() == true) {
					jcbAnneeContact.setEnabled(false);
					jcbMoisContact.setEnabled(false);
					jcbJoursContact.setEnabled(false);
					jcbAnneePrise.setEnabled(false);
					jcbMoisPrise.setEnabled(false);
					jcbJoursPrise.setEnabled(false);
					jcbAnneeRetour.setEnabled(false);
					jcbMoisRetour.setEnabled(false);
					jcbJoursRetour.setEnabled(false);
					jkbRetourOk.setEnabled(false);
					jkbRetourDeclasse.setEnabled(false);
					jkbRetourSuspens.setEnabled(false);

				}
			}
			if (e.getSource() == jkbSuiviOui) {
				jcbAnneeContact.setEnabled(true);
				jcbMoisContact.setEnabled(true);
				jcbJoursContact.setEnabled(true);
				jcbAnneePrise.setEnabled(true);
				jcbMoisPrise.setEnabled(true);
				jcbJoursPrise.setEnabled(true);
				jcbAnneeRetour.setEnabled(true);
				jcbMoisRetour.setEnabled(true);
				jcbJoursRetour.setEnabled(true);
				jkbRetourOk.setEnabled(true);
				jkbRetourDeclasse.setEnabled(true);
				jkbRetourSuspens.setEnabled(true);
			}

			if (e.getSource() == jcbType) {
				if (jcbType.getSelectedItem() != ("")) {
					String sqlIdType = ("SELECT CodeTypeInt FROM TypeIntervention WHERE LibelleTypeInt=?");

					try {
						PreparedStatement psIdType = fp.getConection()
								.prepareStatement(sqlIdType);
						psIdType.setString(1, jcbType.getSelectedItem()
								.toString());
						TableModelGen tmgIdType = AccessBDGen
								.creerTableModel(psIdType);
						jtfIdType
								.setText(tmgIdType.getValueAt(0, 0).toString());
					} catch (SQLException s) {
						s.printStackTrace();
					}
				}

				else
					jtfIdType.setText("");
			}

			if (e.getSource() == jcbFourn) {
				if (jcbFourn.getSelectedItem() != "") {

					String sqlIdFourn = ("SELECT FournisseurId FROM Fournisseur WHERE NomFourn=?");

					try {

						PreparedStatement psIdFourn = fp.getConection()
								.prepareStatement(sqlIdFourn);
						psIdFourn.setString(1, jcbFourn.getSelectedItem()
								.toString());
						TableModelGen tmgIdFourn = AccessBDGen
								.creerTableModel(psIdFourn);
						jtfIdFourn.setText(tmgIdFourn.getValueAt(0, 0)
								.toString());
					}

					catch (SQLException s) {
						s.printStackTrace();
					}
				} else
					jtfIdFourn.setText("");
			}

			if (e.getSource() == jbOk) {
				boolean verifDateSigne = verifDateSign();
				boolean verifPrenCh = verifPrenCh();
				boolean verifPc = verifPc();
				boolean verifType = verifType();

				if (verifDateSigne == true && verifPrenCh == true
						&& verifPc == true && verifType == true) {

					String sqlAjout = ("INSERT INTO intervmaintenance.Intervention (NoInterv, DateSignalement, DescriptifBrefProblème, SignaleurIncident, PreneurEnCharge, EtatInterv, SuiviViaFournisseur, DateContact, DatePrise, DateRetour, EtatRetour, DateRemiseService, TempsInterne, Résultat, FkPcUnit, FkTypeInterv, FkFournisseurIntervenant) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

					try {
						PreparedStatement psAjout = fp.getConection()
								.prepareStatement(sqlAjout);

						// Ajout du Numéro dans la requete.
						int no = Integer.parseInt(noInterv());
						psAjout.setInt(1, no);

						// Ajout de la date de signalement dans la requete.
						GregorianCalendar dS = date(jcbJoursSign, jcbMoisSign,
								jcbAnneeSign);
						psAjout.setDate(2,
								new java.sql.Date(dS.getTimeInMillis()));

						// Ajout du descriptif dans la requète
						psAjout.setString(3, jtfDescr.getText());

						// Ajout du signaleur
						psAjout.setString(4, jtfSign.getText());

						// Ajout du prenneur en charge
						psAjout.setString(5, jtfPrenCh.getText());

						// Ajout de l'état dans la requete
						psAjout.setString(6, etat());

						// Ajout du suivi fournisseur
						if (jkbSuiviOui.isSelected() == true) {
							psAjout.setBoolean(7, true);

							// Ajout de la date de prise de contact.
							GregorianCalendar dC = date(jcbJoursContact,
									jcbMoisContact, jcbAnneeContact);
							if (dC != null)
								psAjout.setDate(8,
										new java.sql.Date(dC.getTimeInMillis()));
							else
								psAjout.setNull(8, Types.DATE);

							// Ajout de la date de prise
							GregorianCalendar dP = date(jcbJoursPrise,
									jcbMoisPrise, jcbAnneePrise);
							if (dP != null)
								psAjout.setDate(9,
										new java.sql.Date(dP.getTimeInMillis()));
							else
								psAjout.setNull(9, Types.DATE);

							// Ajout de la date de retour
							GregorianCalendar dR = date(jcbJoursRetour,
									jcbMoisRetour, jcbAnneeRetour);
							if (dR != null)
								psAjout.setDate(10,
										new java.sql.Date(dR.getTimeInMillis()));
							else
								psAjout.setNull(10, Types.DATE);

							// Ajout de l'état de retour
							if (jkbRetourOk.isSelected() == true)
								psAjout.setString(11, "OK");
							else if (jkbRetourDeclasse.isSelected() == true)
								psAjout.setString(11, "Déclassé");
							else if (jkbRetourSuspens.isSelected() == true)
								psAjout.setString(11, "Suspens");
							else
								psAjout.setNull(11, Types.VARCHAR);
						}

						else {
							psAjout.setBoolean(7, false);
							psAjout.setNull(8, Types.DATE);
							psAjout.setNull(9, Types.DATE);
							psAjout.setNull(10, Types.DATE);
							psAjout.setNull(11, Types.VARCHAR);
						}

						// Ajout de la date de remise en service.
						GregorianCalendar dRS = date(jcbJoursRemis,
								jcbMoisRemis, jcbAnneeRemis);
						if (dRS != null)
							psAjout.setDate(12,
									new java.sql.Date(dRS.getTimeInMillis()));
						else
							psAjout.setNull(12, Types.DATE);

						// Ajout du temps en interne
						if (jtfTemps.getText().isEmpty() == true)
							psAjout.setNull(13, Types.INTEGER);
						else
							psAjout.setInt(13,
									Integer.parseInt(jtfTemps.getText()));

						// Ajout du résultat
						if (jkbResOk.isSelected() == true)
							psAjout.setString(14, "OK");
						else if (jkbResDeclasse.isSelected() == true)
							psAjout.setString(14, "Déclassé");
						else if (jkbResSuspens.isSelected() == true)
							psAjout.setString(14, "Suspens");
						else
							psAjout.setNull(14, Types.VARCHAR);

						// Ajout de FkPcUnit
						psAjout.setString(15, jcbPc.getSelectedItem()
								.toString());

						// Ajout de FkTypeInterv
						psAjout.setString(16, jtfIdType.getText());

						// Ajout de l'ID fournisseur.
						if (jcbFourn.getSelectedItem().toString() == (""))
							psAjout.setNull(17, Types.VARCHAR);
						else
							psAjout.setString(17, jtfIdFourn.getText());

						int nbLigneMaj = psAjout.executeUpdate();

						reset();

						JOptionPane.showMessageDialog(null,
								"Ajout effectué avec succès. \nLignes ajoutée : "
										+ nbLigneMaj, "Succès",
								JOptionPane.INFORMATION_MESSAGE);

					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
								"Echec", JOptionPane.ERROR_MESSAGE);
					}

				}

				else
					JOptionPane.showMessageDialog(null,
							"Veuillez remplire tous les champs obligatoire !",
							"ERREUR", JOptionPane.ERROR_MESSAGE);
			}

			if (e.getSource() == jbReset) {
				try {
					reset();
				}

				catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

}
