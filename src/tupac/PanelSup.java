package tupac;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import accessBD.AccessBDGen;
import accessBD.TableModelGen;

public class PanelSup extends JPanel {

	// Initialisation JComboBox
	private JComboBox<String> jcbLocal;

	// Initialisation jtf
	private JTextField jtfLocal;

	// Initialisation des JPanel
	private JPanel jpChoix, jpTable, jpLocal;

	// Initialisation JScrolPane
	private JScrollPane jspAffichage;

	// Initialisation JButton
	private JButton jbSup, jbRech;

	// Initiation JTable
	private JTable jtAffichage;

	// Initialisation JLabel
	private JLabel jlChoix;

	// Initialisation MyLystener
	private MyListener myListener;

	// Tableau ou stoquer les collomne sélectionées
	private int selectedRow[] = null;

	private FenetrePrincipale fp;

	public PanelSup(FenetrePrincipale f) throws SQLException {

		fp = f;
		myListener = new MyListener();
		this.setBounds(0, 0, 1000, 700);

		jpChoix = new JPanel();
		jpLocal = new JPanel();

		jlChoix = new JLabel("Local : ");

		jcbLocal = new JComboBox<String>();
		jcbLocal.addItem("");
		localConnector();
		jcbLocal.addActionListener(myListener);

		jpTable = new JPanel();
		jpTable.setSize(1000, 400);

		jtAffichage = new JTable();
		jtAffichage.setSize(1000, 400);
		jtAffichage.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtAffichage.getTableHeader().setReorderingAllowed(false);
		jtAffichage.setShowGrid(true);
		jtAffichage.setGridColor(Color.blue);
		jtAffichage.setModel(tableConnector());

		jspAffichage = new JScrollPane(jtAffichage);

		jbSup = new JButton("Suprimer !");
		jbSup.addActionListener(myListener);

		jpLocal.add(jlChoix);
		jpLocal.add(jcbLocal);
		this.add(jpLocal);

		jpTable.add(jspAffichage);
		this.add(jpTable);

		jpChoix.add(jbSup);
		this.add(jpChoix);
	}

	public TableModelGen tableConnector() {
		PreparedStatement psTable;

		try {
			if (jcbLocal.getSelectedItem().equals("")) {
				String tableSql = ("SELECT * FROM intervention WHERE Intervention.Résultat = 'Suspens'");
				psTable = fp.getConection().prepareStatement(tableSql);
				TableModelGen tmgTable = AccessBDGen.creerTableModel(psTable);
				return tmgTable;
			}

			else {
				String tableSql = ("SELECT Intervention.* FROM Intervention, pcunit WHERE Intervention.FkPcUnit = pcunit.IdPcUnit AND pcunit.Local = '"
						+ jcbLocal.getSelectedItem().toString() + "' AND Intervention.Résultat = 'Suspens'");

				psTable = fp.getConection().prepareStatement(tableSql);
				TableModelGen tmgTable = AccessBDGen.creerTableModel(psTable);
				return tmgTable;
			}
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQL ERROR",
					"Une erreur à eu lieux, veuillez recommancer l'oppération",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	public void localConnector() {

		String localSql = ("SELECT DISTINCT Local FROM pcunit");
		try {
			PreparedStatement psLocal = fp.getConection().prepareStatement(
					localSql);
			TableModelGen tmgLocal = AccessBDGen.creerTableModel(psLocal);

			for (int i = 0; i < tmgLocal.getRowCount(); i++)
				jcbLocal.addItem(tmgLocal.getValueAt(i, 0).toString());
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQL ERROR",
					"Une erreur à eu lieux, veuillez recommancer l'oppération",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void supprimer() {
		try {
			selectedRow = jtAffichage.getSelectedRows();

			if (selectedRow.length == 0)
				JOptionPane.showMessageDialog(null,
						"Attention veuillez sélectioner au moins une valeur",
						"Attention", JOptionPane.INFORMATION_MESSAGE);
			else {
				int rep;
				rep = JOptionPane.showConfirmDialog(null,
						"<html><strong>êtes-vous sur de vouloir supprimer "
								+ selectedRow.length
								+ " intervention(s)</html></strong>",
						"Confirmation", JOptionPane.YES_NO_OPTION);

				if (rep == JOptionPane.YES_OPTION) {

					String supSql = ("DELETE FROM Intervention WHERE NoInterv = ?");
					PreparedStatement psSup = fp.getConection()
							.prepareStatement(supSql);

					for (int i = 0; i < selectedRow.length; i++) {
						psSup.setInt(
								1,
								Integer.parseInt(jtAffichage.getValueAt(
										selectedRow[i], 0).toString()));
						psSup.executeUpdate();
					}
				}

			}

			majTable(tableConnector());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQL ERROR",
					"Une erreur à eu lieux, veuillez recommancer.",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void majTable(TableModelGen tmg) {
		jtAffichage.setModel(tmg);

		if (tmg.getRowCount() == 0)
			JOptionPane.showMessageDialog(null,
					"Aucune intervention trouvées pour ce local", "Attention",
					JOptionPane.INFORMATION_MESSAGE);

		this.repaint();
	}

	private class MyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == jbSup)
				supprimer();

			if (e.getSource() == jcbLocal)
				majTable(tableConnector());
		}
	}

}
