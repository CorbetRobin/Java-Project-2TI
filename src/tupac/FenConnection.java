package tupac;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import accessBD.AccessBDGen;

public class FenConnection extends JFrame {

	private Container cont;
	private JPanel jpNom, jpLog, jpMDP, jpBouton, jpMain;
	private JLabel jlNom, jlLog, jlMDP;
	private JTextField jtfNom, jtfLog;
	private JPasswordField jpfMDP;
	private Connection connection;
	private JButton jbCon;
	private MyListener myListener;
	private FenetrePrincipale fp;

	public FenConnection(FenetrePrincipale fp) {

		super("Connection");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);

		myListener = new MyListener();

		jlNom = new JLabel("Nom : ");
		jlLog = new JLabel("Login : ");
		jlMDP = new JLabel("MDP : ");

		jtfNom = new JTextField("", 20);
		jtfLog = new JTextField("", 20);
		jpfMDP = new JPasswordField("", 20);

		jbCon = new JButton("Connection");
		jbCon.addActionListener(myListener);

		jpNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jpNom.add(jlNom);
		jpNom.add(jtfNom);

		jpLog = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jpLog.add(jlLog);
		jpLog.add(jtfLog);

		jpMDP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jpMDP.add(jlMDP);
		jpMDP.add(jpfMDP);

		jpBouton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpBouton.add(jbCon);

		jpMain = new JPanel(new GridLayout(4, 1, 1, 1));
		jpMain.add(jpNom);
		jpMain.add(jpLog);
		jpMain.add(jpMDP);
		jpMain.add(jpBouton);

		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		cont.add(jpMain, BorderLayout.CENTER);
		setVisible(true);

		this.fp = fp;

	}

	public void setConection() {
		String MDP = new String(jpfMDP.getPassword());
		try {
			connection = AccessBDGen.connecter(jtfNom.getText(),
					jtfLog.getText(), MDP);
			JOptionPane.showMessageDialog(null, "Vous êtes bine conecté",
					"Succès", JOptionPane.INFORMATION_MESSAGE);
			fp.setConnection(connection);

			this.dispose();
		}

		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "MAUVAIS IDENTIFIANTS",
					"Mauvais Identifiants, Accès refusé !",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public Connection getConnection() {
		return connection;
	}

	private class MyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbCon) {
				setConection();
			}
		}
	}

}
