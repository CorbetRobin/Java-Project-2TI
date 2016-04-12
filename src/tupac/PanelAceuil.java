package tupac;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelAceuil extends JPanel {

	private JPanel jpBienvenu, jpChoix, jpCon;
	private JLabel jlBienvenu, jlChoix, jlCon;

	public PanelAceuil() {
		jlBienvenu = new JLabel("<html><strong>Binevenu</strong></html>");
		jlChoix = new JLabel(
				"<html><strong>Veuillez choisir une fonctionalitée dans le menu ci dessus.</strong></html>");
		jlCon = new JLabel(
				"<html><strong>N'oubliez pas de vous connecter à une DB.</strong></html>");

		this.setLayout(new GridLayout(6, 1, 1, 1));

		jpBienvenu = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpBienvenu.add(jlBienvenu);
		this.add(jpBienvenu);

		jpChoix = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpChoix.add(jlChoix);
		this.add(jpChoix);

		jpCon = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpCon.add(jlCon);
		this.add(jpCon);
	}

}
