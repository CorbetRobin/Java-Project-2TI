package tupac;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;

public class PanelListing extends JPanel {

	// Déclaration des JPanel (jp)
	private JPanel jpJTable, jpRecherche, jpRecherchePc, jpRechercheNom1,
			jpRechercheNom2, jpRechercheNom3, jpButton, jpChoix;

	// Déclaration JLabel (jl)
	private JLabel jlChoix, jlNom, jlType;

	// Déclaration des JCombobox (jcb)
	private JComboBox<String> jcbChoix, jcbFourn, jcbNom, jcbType;

	// Déclaration de la JScrollPane (jsp)
	private JScrollPane jspJTable;

	// Déclaration du JBoutton de choix (jb)
	private JButton jbChoix;

	// Déclaration de la JTable (jt)
	private JTable jtResultat;

	// Déclaration des JCheckBox (jkb)
	private JCheckBox jkbPc, jkbNom;

	// Création et initialisation du gestionaire
	private MyListener myListener = new MyListener();

	// Création d'un JTextField pour l'ID du fournisseur.
	private JTextField jtfIdFourn, jtfIdType;

	// Création du tableau de proposition pour la JComboBox
	private String tabNom[] = { "configurationpc", "fournisseur", "imagesw",
			"intervention", "lotconfiguration", "pcunit", "typeintervention" };

	private FenetrePrincipale fp;

	PanelListing(FenetrePrincipale f) throws SQLException {

		fp = f;

		// Création du JLabel
		jlChoix = new JLabel("Table à afficher : ");
		jlNom = new JLabel("Nom prenneur en charge : ");
		jlType = new JLabel("type d'intervention : ");

		// Créationn des JComboBox
		jcbChoix = new JComboBox<String>(tabNom);

		jcbNom = new JComboBox<String>();
		jcbNom.addItem("");
		jcbNom.setEnabled(false);
		nomConnector();

		jcbType = new JComboBox<String>();
		jcbType.addItem("");
		jcbType.setEnabled(false);
		typeConnector();
		jcbType.addActionListener(myListener);

		jcbFourn = new JComboBox<String>();
		jcbFourn.addItem("");
		jcbFourn.setEnabled(false);
		fournConnector();
		jcbFourn.addActionListener(myListener);

		// Création du jtfIdFourn
		jtfIdFourn = new JTextField("", 3);
		jtfIdFourn.setEnabled(false);

		jtfIdType = new JTextField("", 3);
		jtfIdType.setEnabled(false);

		// Création des JCheckBox
		jkbPc = new JCheckBox("Rechreche Intervention avec PC déclassé : ");
		jkbPc.addActionListener(myListener);

		jkbNom = new JCheckBox(
				"Recherche Intervention Par Nom et Type d'intervention : ");
		jkbNom.addActionListener(myListener);

		// Création du JButton
		jbChoix = new JButton("Rechercher !");
		jbChoix.addActionListener(myListener);

		// Création des JPanel
		jpJTable = new JPanel();

		jpChoix = new JPanel();

		jpRecherchePc = new JPanel();
		jpRecherchePc.setLayout(new FlowLayout());

		jpRechercheNom1 = new JPanel();
		jpRechercheNom1.setLayout(new FlowLayout());

		jpRechercheNom2 = new JPanel();
		jpRechercheNom2.setLayout(new FlowLayout());

		jpRechercheNom3 = new JPanel();
		jpRechercheNom3.setLayout(new FlowLayout());

		jpRecherche = new JPanel();
		jpRecherche.setLayout(new GridLayout(10, 10));

		jpButton = new JPanel();
		jpButton.setLayout(new FlowLayout());

		// Border Layout pour le panel principale
		this.setLayout(new BorderLayout());

		jtInit();

		// Ajout du jpJTable dans le panel principale
		this.add(jpJTable, BorderLayout.WEST);

		// Remplissage des JPanel et ajout sur le panel principale
		jpChoix.add(jlChoix);
		jpChoix.add(jcbChoix);

		jpRecherchePc.add(jkbPc);
		jpRecherchePc.add(jcbFourn);
		jpRecherchePc.add(jtfIdFourn);

		jpRechercheNom1.add(jkbNom);

		jpRechercheNom2.add(jlNom);
		jpRechercheNom2.add(jcbNom);

		jpRechercheNom3.add(jlType);
		jpRechercheNom3.add(jcbType);
		jpRechercheNom3.add(jtfIdType);

		jpButton.add(jbChoix);

		jpRecherche.add(jpChoix);
		jpRecherche.add(jpRecherchePc);
		jpRecherche.add(jpRechercheNom1);
		jpRecherche.add(jpRechercheNom2);
		jpRecherche.add(jpRechercheNom3);
		jpRecherche.add(jpButton);

		this.add(jpRecherche, BorderLayout.CENTER);

	}

	// Création de la méthode nomConnector qui remplis la jcb nom
	public void nomConnector() {

		// Création de la requete SQL
		String nomSql = ("SELECT DISTINCT PreneurEnCharge FROM Intervention");

		try {
			// Création du prepared statement
			PreparedStatement psNom = fp.getConection()
					.prepareStatement(nomSql);

			// récuperation des données
			TableModelGen tmgNom = AccessBDGen.creerTableModel(psNom);

			// Boucle pour ajouter les valeur dans la jcb
			for (int i = 0; i < tmgNom.getRowCount(); i++)
				jcbNom.addItem(tmgNom.getValueAt(i, 0).toString());
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	// Création de la méthode typeConnector qui remplis la jcb type
	public void typeConnector() {
		String typeSql = ("SELECT DISTINCT LibelleTypeInt FROM TypeIntervention");

		try {
			PreparedStatement psType = fp.getConection().prepareStatement(
					typeSql);
			TableModelGen tmgType = AccessBDGen.creerTableModel(psType);

			for (int i = 0; i < tmgType.getRowCount(); i++)
				jcbType.addItem(tmgType.getValueAt(i, 0).toString());
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	// Création de la méthode fournConector qui ajoute le noms des fourniisseur
	// dans la jcbFourn

	public void fournConnector() {

		// Création de la requete
		String fournSql = ("SELECT DISTINCT NomFourn FROM Fournisseur");

		try {

			// Création du PreparedStatement
			PreparedStatement psFourn = fp.getConection().prepareStatement(
					fournSql);

			// On récupère le résultat dans un objet de type TableModelGen
			TableModelGen tmgFourn = AccessBDGen.creerTableModel(psFourn);

			// On crée une boucle affin d'ajouter les données dans la jcb
			for (int i = 0; i < tmgFourn.getRowCount(); i++)
				jcbFourn.addItem(tmgFourn.getValueAt(i, 0).toString());

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	// Création de la m"thode jtConnector. Cette méthode à pour but de remplire
	// la JTable avec le résultat de la recherche.
	public void jtInit() {

		try {

			// Création de la JTable avec la recherche de l'utilisateur (Donnée
			// par la méthode jtConnector() )
			jtResultat = new JTable(jtConnector());

			// AUTO_RESIZE_OFF pour évitter les Bug graphique
			jtResultat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			// On place la JTable dans la JScrollPane
			jspJTable = new JScrollPane(jtResultat);

			// On ajoute le tout dans le Panel jpJTable
			jpJTable.add(jspJTable);

		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Création de la Méthode jtConnector, elle a pour but de renvoyer le
	// résultat de la recherche de l'utilisateur en format TableModelGen
	public TableModelGen jtConnector() throws SQLException {

		// Création de la requete SQL
		String jtSql = ("select * from "
				+ jcbChoix.getSelectedItem().toString() + ";");

		// Création de la PreparedStatement
		PreparedStatement psJt = fp.getConection().prepareStatement(jtSql);

		// Réponse de la requete sous forme d'objet TableModelGen
		TableModelGen tmgJt = AccessBDGen.creerTableModel(psJt);

		return tmgJt;
	}

	// Création de la méthode majJTable qui met a jours les données dans la
	// JTable
	public void majJTable(TableModelGen tableModel) {
		jtResultat.setModel(tableModel);
	}

	// Création de la méthode rechercheInterv qui va effectuer la recherche sur
	// les Intervention demander par l'utilisateur et le renvoyer sous forme de
	// TableModelGen
	public TableModelGen rechercheInterv() throws SQLException {

		String rechercheIntervSql = ("SELECT * FROM Intervention WHERE FkFournisseurIntervenant = '"
				+ jtfIdFourn.getText() + "' AND Résultat = 'Déclassé'");

		PreparedStatement psRechercheInterv = fp.getConection()
				.prepareStatement(rechercheIntervSql);

		TableModelGen tmgRechercheInterv = AccessBDGen
				.creerTableModel(psRechercheInterv);

		return tmgRechercheInterv;

	}

	// création de la méthode connectorIdfourn qui va remplire le jtfIdfourn
	// avec l'ID correspondant au nom sélectioné.
	public void connectorIdFourn() {

		// Création de la requete sql
		String idFournSql = ("SELECT FournisseurId FROM Fournisseur WHERE NomFourn = ?");

		try {
			// Création du prepared statement
			PreparedStatement psFournId = fp.getConection().prepareStatement(
					idFournSql);

			// Ajout du nom contenu dans la jcb à la recherche
			psFournId.setString(1, jcbFourn.getSelectedItem().toString());

			// On place le résultat de l'instruction dans un TableModelGen
			TableModelGen tmgIdFourn = AccessBDGen.creerTableModel(psFournId);

			// On place l'ID dans le JTF
			jtfIdFourn.setText(tmgIdFourn.getValueAt(0, 0).toString());
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void connectorIdType() {
		String idTypesql = ("SELECT CodeTypeInt FROM TypeIntervention WHERE LibelleTypeInt = ?");

		try {
			PreparedStatement psIdType = fp.getConection().prepareStatement(
					idTypesql);
			psIdType.setString(1, jcbType.getSelectedItem().toString());
			TableModelGen tmgIdtype = AccessBDGen.creerTableModel(psIdType);
			jtfIdType.setText(tmgIdtype.getValueAt(0, 0).toString());
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERREUR !", "ERREUR !",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public TableModelGen rechercheNom() throws SQLException {
		String rechercheSql = ("SELECT * FROM Intervention WHERE PreneurEnCharge = '"
				+ jcbNom.getSelectedItem().toString()
				+ "' AND FkTypeInterv = '" + jtfIdType.getText() + "'");
		PreparedStatement psRecherche = fp.getConection().prepareStatement(
				rechercheSql);
		TableModelGen tmgRecherche = AccessBDGen.creerTableModel(psRecherche);
		return tmgRecherche;
	}

	// Création de la calss prévie MyListener qui va servir d'écouteur à nos
	// différents objets
	private class MyListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == jcbType)
				connectorIdType();

			// On regarde si l'utilisateur sélectionne un item dans la jcbFourn.
			if (e.getSource() == jcbFourn)
				// On exécute la méthode prévue pour remplire le JTF
				connectorIdFourn();

			// On vérifie si l'utilisateur a cliquer sur le bouton
			// "Rechercher !"
			if (e.getSource() == jbChoix) {
				try {
					// On regarde si jkbPc et jkbLot sont décoché
					if (jkbPc.isSelected() == false
							&& jkbNom.isSelected() == false)
						majJTable(jtConnector());
					// On regarde si jkbPc est coché
					else if (jkbPc.isSelected() == true) {
						// On renvois une erreur si rien n'est sélectioner dans
						// le combo box
						if (jcbFourn.getSelectedItem().equals("") == true)
							JOptionPane
									.showMessageDialog(
											null,
											"Veuillez sélectionner un fournisseur valide !",
											"Erreur sélection",
											JOptionPane.ERROR_MESSAGE);
						else {
							// On vérifie l'éventualité qu'il n'y ait pas de
							// corespondance à la requète
							if (rechercheInterv().getRowCount() == 0)
								JOptionPane.showMessageDialog(null,
										"Aucune intervention trouvée",
										"Information", JOptionPane.NO_OPTION);
							else
								majJTable(rechercheInterv());
						}
					}
					// On vérifie si jkbLot est coché
					else if (jkbNom.isSelected() == true) {
						if (jcbNom.getSelectedItem().equals("") == true
								|| jcbType.getSelectedItem().equals(""))
							JOptionPane
									.showMessageDialog(
											null,
											"Veuillez sélectionner un Nom et un Type !",
											"Erreur sélection",
											JOptionPane.ERROR_MESSAGE);
						else {
							// On vérifie l'éventualité qu'il n'y ait pas de
							// corespondance à la requète
							if (rechercheNom().getRowCount() == 0)
								JOptionPane.showMessageDialog(null,
										"Aucune intervention trouvée !",
										"Information", JOptionPane.NO_OPTION);
							else
								majJTable(rechercheNom());
						}
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			// Nous allons rendre jcbPC utilisable et jcbLot inutilisable si
			// jkbPc est coché
			else if (e.getSource() == jkbPc) {
				if (jkbPc.isSelected() == true) {
					jcbFourn.setEnabled(true);

					jcbChoix.setSelectedItem("intervention");
					jcbChoix.setEnabled(false);

					jcbNom.setEnabled(false);
					jcbType.setEnabled(false);
				}

				// On regarde si jcbPC est décoché et on rend leur état au autre
				// élèment
				else if (jkbPc.isSelected() == false) {
					jcbFourn.setEnabled(false);

					jcbChoix.setEnabled(true);

					jkbNom.setEnabled(true);
				}
			}

			// On regarde si jkbLot change d'état
			else if (e.getSource() == jkbNom) {
				// On regarde si jkbLot à été coché si oui on active jcbLot et
				// désactive jcbChoix
				if (jkbNom.isSelected() == true) {

					jcbNom.setEnabled(true);
					jcbType.setEnabled(true);

					jcbChoix.setSelectedItem("intervention");
					jcbChoix.setEnabled(false);

					jkbPc.setEnabled(false);
					jkbPc.setSelected(false);
					jcbFourn.setEnabled(false);
				}

				else if (jkbNom.isSelected() == false) {

					jcbNom.setEnabled(false);
					jcbType.setEnabled(false);

					jcbChoix.setEnabled(true);

					jkbPc.setEnabled(true);
				}
			}

		}
	}

}
