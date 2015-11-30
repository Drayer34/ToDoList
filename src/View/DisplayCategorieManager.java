package View;

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

import controler.Controler;
import Model.Categorie;
import Model.Manager;

@SuppressWarnings("serial")
public class DisplayCategorieManager extends JFrame{

	private Controler controler;
	private Manager manager;
	private DisplayManager displayManagaer;
	
	private JComboBox<Categorie> categorie;
	private JTextField name = new JTextField();
	private JTextField reName = new JTextField();
	private JPanel contents = new JPanel();
	private JPanel b_add = new JPanel();
	private JPanel b_addButton = new JPanel();
	private JPanel b_categorieList = new JPanel();
	private JPanel b_reName = new JPanel();
	private JPanel b_editButton = new JPanel();
	private JButton delete = new JButton("Supprimer");//Supprime une categorie
	private JButton modifer = new JButton("Modifier");//modifier une categorie
	private JButton addCategorie = new JButton("Ajouter");//Ajouter une categorie

	public DisplayCategorieManager(Controler controler, Manager manager, DisplayManager displayManagaer){
		this.controler = controler;
		this.manager = manager;
		this.displayManagaer = displayManagaer;

		setTitle("Option categorie");
		setLocationRelativeTo(null);

		this.setVisible(true);
		this.setMinimumSize(new Dimension(250,250));
		this.addWindowListener(new WindowClosing());
		init();
		pack();
	}

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

	public class ButtonsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			controler.updateCategorieManager(((JButton)e.getSource()).getText());
		}
	}
	public class ComboBoxListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controler.updateEditW();
		}
	}

	
	public class WindowClosing extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			
			controler.updateCategorieManager("exit");
		}

	}
	
	public void updateRemoveTask(){
		categorie.setSelectedIndex(0);
		displayManagaer.getCategorie().setSelectedIndex(0);
	}
	
	public void updateComboBox(){
		categorie.updateUI();
		displayManagaer.getCategorie().updateUI();
	}
	
	/*r�initialise le text field apr�s l'ajout */
	public void updateNameAfterAdd(){
		name.setText("");
	}
	public void updateReName(){
		reName.setText(categorie.getSelectedItem().toString());
	}
	
	public void close(){
		this.dispose();
	}
	
	public String get_Name(){
		return name.getText();
	}
	
	public String get_reName(){
		return reName.getText();
	}

	public void setDeleteEnabled(boolean b){
		delete.setEnabled(b);
	}
	public void setModiferEnabled(boolean b){
		modifer.setEnabled(b);
	}
	public Categorie getSelectedCategorie() {
		return (Categorie) categorie.getSelectedItem();
	}
	public int getSelectedCategorieIndex() {
		return categorie.getSelectedIndex();
	}

	public void printError() {
		JOptionPane.showMessageDialog(new JFrame(),"Il �xiste une categorie portant d�j� ce nom !");		
	}
}
