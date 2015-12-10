package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Categorie;
import model.Manager;
import controler.Controler;

/**
 * Classe qui implémente la fenêtre modification de catégorie
 * @author Antoine Laurent et Anthony Brunel
 *
 */
@SuppressWarnings("serial")
public class DisplayCategorieManager extends JFrame{

	/**
	 * Instance du controler
	 */
	private Controler controler;
	/**
	 * Instance de manager
	 */
	private Manager manager;
	/**
	 * Instance du displayManger
	 */
	private DisplayManager displayManagaer;
	/**
	 * 
	 */
	private JComboBox<Categorie> categorie;
	/**
	 * Champ  du nom de la catégorie
	 */
	private JTextField name = new JTextField();
	/**
	 * Champs du nouveau nom de la catégorie
	 */
	private JTextField reName = new JTextField();
	/**
	 * panel qui contient les éléments graphiques
	 */
	private JPanel contents = new JPanel();
	/**
	 * panel pour le nom
	 */
	private JPanel b_add = new JPanel();
	/**
	 * panel pour le bouton ajout
	 */
	private JPanel b_addButton = new JPanel();
	/**
	 * panel pour la liste de catégorie
	 */
	private JPanel b_categorieList = new JPanel();
	/**
	 * panel pour le nouveau nom
	 */
	private JPanel b_reName = new JPanel();
	/**
	 * panel pour le boutons d'éditions
	 */
	private JPanel b_editButton = new JPanel();
	/**
	 * bouton supprimer
	 */
	private JButton delete = new JButton("Supprimer");
	/**
	 * bouton modifier 
	 */
	private JButton modifer = new JButton("Modifier");
	/**
	 * bouton ajout catégorie
	 */
	private JButton addCategorie = new JButton("Ajouter");//Ajouter une categorie

	/**
	 * Constucteur
	 * @param controler l'instance courrante du controler
	 * @param manager l'instance courrante du manager
	 * @param displayManagaer linstance courrante du displayManager
	 */
	public DisplayCategorieManager(Controler controler, Manager manager, DisplayManager displayManagaer){
		this.controler = controler;
		this.manager = manager;
		this.displayManagaer = displayManagaer;

		setTitle("Option categorie");

		this.setVisible(true);
		this.setMinimumSize(new Dimension(250,250));
		this.addWindowListener(new WindowClosing());
		init();
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Initialisation de la fenêtre catégorie
	 */
	public void init(){
		contents.setLayout(new BoxLayout(contents,BoxLayout.PAGE_AXIS));

		b_add.setLayout(new BoxLayout(b_add,BoxLayout.LINE_AXIS));
		b_addButton.setLayout(new BoxLayout(b_addButton,BoxLayout.LINE_AXIS));
		b_categorieList.setLayout(new BoxLayout(b_categorieList,BoxLayout.LINE_AXIS));

		name.setMaximumSize(new Dimension(150,20));
		b_add.add(name);

		addCategorie.addActionListener(new ButtonsListener());
		b_addButton.add(addCategorie);

		categorie = new JComboBox<Categorie>(manager.getListCategorie());
		categorie.setMaximumSize(new Dimension(150,20));
		categorie.addActionListener(new ComboBoxListener());
		b_categorieList.add(categorie);

		reName.setText(categorie.getSelectedItem().toString());
		reName.setPreferredSize(new Dimension(150,20));
		b_reName.add(reName);

		modifer.setEnabled(false);
		modifer.addActionListener(new ButtonsListener());
		delete.setEnabled(false);
		delete.addActionListener(new ButtonsListener());

		b_editButton.add(modifer);
		b_editButton.add(delete);

		contents.add(Box.createRigidArea(new Dimension(0,20)));
		contents.add(b_add);
		contents.add(b_addButton);
		contents.add(Box.createRigidArea(new Dimension(0,20)));
		contents.add(Box.createRigidArea(new Dimension(0,20)));
		contents.add(b_categorieList);
		contents.add(b_reName);
		contents.add(b_editButton);

		add(contents);
	}

	/**
	 * listener sur les boutons 
	 * @author Antoine Laurent et Anthony Brunel
	 *
	 */
	public class ButtonsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			controler.updateCategorieManager(((JButton)e.getSource()).getText());
		}
	}
	/**
	 * listener sur la comboBox 
	 * @author Antoine Laurent et Anthony Brunel
	 *
	 */
	public class ComboBoxListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controler.updateEditW();
		}
	}

	/**
	 * listener sur la fenêtre
	 * @author Antoine Laurent et Anthony Brunel
	 *
	 */
	public class WindowClosing extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {

			controler.updateCategorieManager("exit");
		}

	}
	/**
	 * update la comboBox en cas de remove
	 */
	public void updateRemoveCategorie(){
		categorie.setSelectedIndex(0);
		displayManagaer.getCategorie().setSelectedIndex(0);
	}

	/**
	 * update la comboBox
	 */
	public void updateComboBox(){
		categorie.updateUI();
		displayManagaer.getCategorie().updateUI();
	}

	/**
	 * réinitialise le text field après l'ajout
	 */
	public void updateNameAfterAdd(){
		name.setText("");
	}
	/**
	 * update le nom de la catégorie après changement
	 */
	public void updateReName(){
		reName.setText(categorie.getSelectedItem().toString());
	}
	/**
	 * ferme la fenêtre
	 */
	public void close(){
		this.dispose();
	}
	/**
	 * 
	 * @return name le nom de la catégorie
	 */
	public String get_Name(){
		return name.getText();
	}
	/**
	 * 
	 * @return rename le nouveau nom de la catégorie
	 */
	public String get_reName(){
		return reName.getText();
	}
	/**
	 * Active le bouton supprimer
	 * @param b boolean 
	 */
	public void setDeleteEnabled(boolean b){
		delete.setEnabled(b);
	}
	/**
	 * Active le bouton modifier
	 * @param b boolean
	 */
	public void setModiferEnabled(boolean b){
		modifer.setEnabled(b);
	}
	/**
	 * 
	 * @return categorie la catégorie sélectionnéee
	 */
	public Categorie getSelectedCategorie() {
		return (Categorie) categorie.getSelectedItem();
	}
	/**
	 * 
	 * @return index l'index de la catégorie sélectionnée
	 */
	public int getSelectedCategorieIndex() {
		return categorie.getSelectedIndex();
	}
	/**
	 * Affiche un message d'erreur
	 */
	public void printError() {
		JOptionPane.showMessageDialog(new JFrame(),"Il existe une catégorie portant déjà ce nom !");		
	}
}
