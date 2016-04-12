package tupac;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelCredit extends JPanel {

	private JPanel jpProjet, jpCours, jpRea, jpNom1, jpEt, jpNom2;
	private JLabel jlProjet, jlCours, jlRea, jlNom1, jlEt, jlNom2;

	public PanelCredit() {
		jlProjet = new JLabel("<html><strong>Projet de Java</strong><html>");
		jlCours = new JLabel("<html><strong>Cours de 2TI</strong></html>");
		jlRea = new JLabel("<html><strong>Réalisé par :</strong></html>");
		jlNom1 = new JLabel("<html><strong>Corbet Robin</html></html>");
		jlEt = new JLabel("<html><strong>ET</strong></html>");
		jlNom2 = new JLabel("<html><strong>Deschepper François</strong></html>");

		jpProjet = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpProjet.add(jlProjet);

		jpCours = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpCours.add(jlCours);

		jpRea = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpRea.add(jlRea);

		jpNom1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpNom1.add(jlNom1);

		jpEt = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpEt.add(jlEt);

		jpNom2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpNom2.add(jlNom2);

		this.setLayout(new GridLayout(6, 1, 0, 0));
		this.add(jpProjet);
		this.add(jpCours);
		this.add(jpRea);
		this.add(jpNom1);
		this.add(jpEt);
		this.add(jpNom2);
	}
}
