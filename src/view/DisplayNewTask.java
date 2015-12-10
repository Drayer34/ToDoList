package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Categorie;
import model.Importance;
import model.Manager;
import model.TaskType;

import com.toedter.calendar.JDateChooser;

import controler.Controler;

/**
 * classe qui implémente la fenêtre nouvelle tâche
 * @author Antoine Laurent et Anthony Brunel
 *
 */
@SuppressWarnings("serial")
public class DisplayNewTask extends JFrame{

	/**
	 * une instace du controler
	 */
	private Controler controler;
	/**
	 * une instance du manager
	 */
	private Manager manager;
	/**
	 * le type de la tâche
	 */
	private TaskType taskType;
	/**
	 * le champs pour le nom de la tâche
	 */
	private JTextField name = new JTextField("");
	/**
	 * panel pour les boutons
	 */
	private JPanel buttons = new JPanel();
	/**
	 * panel qui contient des objets graphique
	 */
	private JPanel contents = new JPanel(new BorderLayout());
	/**
	 * bouton valider
	 */
	private JButton valide = new JButton("Valider");
	/**
	 * bouton annuler
	 */
	private JButton cancel = new JButton("Annuler");
	/**
	 * date chooser pour la date de début
	 */
	private JDateChooser beginDateChooser = new JDateChooser();
	/**
	 * date chooser pour la date de fin
	 */
	private JDateChooser endDateChooser = new JDateChooser();
	/**
	 * combo box pour les différentes importances
	 */
	private JComboBox<Importance> importance;
	/**
	 * panel pour le nom de la tâche
	 */
	private JPanel b_name = new JPanel();
	/**
	 * panel pour la catégorie
	 */
	private JPanel b_cate = new JPanel();
	/**
	 * panel pour l'importance
	 */
	private JPanel b_importance = new JPanel();
	/**
	 * panel pour la date de fin
	 */
	private JPanel b_dateEnd = new JPanel();
	/**
	 * panel pour la date de fin
	 */
	private JPanel b_dateBegin = new JPanel();
	/**
	 * combo box pour les différentes catégories
	 */
	private JComboBox<Categorie> categorie;

	/**
	 * Constructeur
	 * @param controler instance du controler
	 * @param manager instance du manager
	 * @param taskType instance du type de la tâche
	 */
	public DisplayNewTask(Controler controler,Manager manager, TaskType taskType){
		this.controler = controler;
		this.manager = manager;
		this.taskType = taskType;
		if( taskType == TaskType.TacheLongCour)
			setTitle("Nouvelle tâche au long cours");
		else
			setTitle("Nouvelle tâche ponctuelle");
		setLocationRelativeTo(null);

		this.setMinimumSize(new Dimension(250,250));
		this.setVisible(true);
		this.addWindowListener(new WindowClosing());
		init();
		pack();
	}

	/**
	 * fonction qui initialise la fenêtre de novelle tâche
	 */
	public void init(){

		contents.setPreferredSize(new Dimension(250,250));

		contents.setLayout(new BoxLayout(contents, BoxLayout.PAGE_AXIS));
		b_dateEnd.setLayout(new BoxLayout(b_dateEnd, BoxLayout.LINE_AXIS));
		b_name.setLayout(new BoxLayout(b_name, BoxLayout.LINE_AXIS));
		b_cate.setLayout(new BoxLayout(b_cate, BoxLayout.LINE_AXIS));
		b_importance.setLayout(new BoxLayout(b_importance, BoxLayout.LINE_AXIS));

		categorie = new JComboBox<Categorie>(manager.getListCategorie());
		categorie.setMaximumSize(new Dimension(150,20));
		b_cate.add(new JLabel("Catégorie?         "));
		b_cate.add(categorie);

		importance = new JComboBox<Importance>(Importance.values());
		importance.setMaximumSize(new Dimension(150,20));
		b_importance.add(new JLabel("Importance?      "));
		b_importance.add(importance);

		name.setMaximumSize(new Dimension(150,20));
		name.setToolTipText("Entrez le nom de la tâche");
		b_name.add(new JLabel("Nom?                  "));
		b_name.add(name);

		endDateChooser.setMaximumSize(new Dimension(150,20));
		endDateChooser.setDateFormatString("dd/MM/yyyy");
		endDateChooser.setToolTipText("dd/mm/yyyy");
		b_dateEnd.add(new JLabel("Date de fin?       "));
		b_dateEnd.add(endDateChooser);

		valide.addActionListener(new ButtonsListener());
		cancel.addActionListener(new ButtonsListener());

		contents.add(Box.createRigidArea(new Dimension(0,20)));
		contents.add(b_name);
		contents.add(Box.createRigidArea(new Dimension(0,20)));
		contents.add(b_cate);
		contents.add(Box.createRigidArea(new Dimension(0,20)));
		contents.add(b_importance);
		contents.add(Box.createRigidArea(new Dimension(0,20)));

		if(taskType == TaskType.TacheLongCour){
			initTacheLongCour();
		}
		contents.add(b_dateEnd);
		buttons.add(valide,"West");
		buttons.add(cancel,"East");

		add(contents);
		add(buttons,"South");
	}
	/**
	 * initialise le panel pour les tâches au long cours
	 */
	public void initTacheLongCour(){
		b_dateBegin.setLayout(new BoxLayout(b_dateBegin, BoxLayout.LINE_AXIS));
		beginDateChooser.setMaximumSize(new Dimension(150,20));
		beginDateChooser.setDateFormatString("dd/MM/yyyy");
		beginDateChooser.setToolTipText("dd/mm/yyyy");
		b_dateBegin.add(new JLabel("Date de début? "));
		b_dateBegin.add(beginDateChooser);
		contents.add(b_dateBegin);
		contents.add(Box.createRigidArea(new Dimension(0,20)));
	}

	/**
	 * méthode qui ferme la fenêtre de nouvelle tâche
	 */
	public void close(){
		this.dispose();
	}
	/**
	 * 
	 * @return name le nom de la tâche créée
	 */
	public String get_Name() {
		return name.getText();
	}
	/**
	 * 
	 * @param name le nom de la nouvelle tâche que l'on veut mettre
	 */
	public void set_Name(JTextField name) {
		this.name = name;
	}
	/**
	 * 
	 * @return catégorie la catégorie que l'on a choisie
	 */
	public Categorie getSelectedCategorie() {
		return (Categorie) categorie.getSelectedItem();
	}
	/**
	 * 
	 * @return le type de la tâche créée
	 */
	public TaskType getTaskType() {
		return taskType;
	}
	/**
	 * 
	 * @return date la date de début
	 */
	public Date getBeginDate(){
		return beginDateChooser.getDate();
	}
	/**
	 * 
	 * @return date la date de fin
	 */
	public Date getEndDate(){
		if(endDateChooser.getDate() != null){
			Calendar date = new GregorianCalendar();
			date.setTime(endDateChooser.getDate());
			date.set(Calendar.HOUR_OF_DAY, 23);
			date.set(Calendar.MINUTE, 59);
			date.set(Calendar.SECOND, 59);
			date.set(Calendar.MILLISECOND, 999);
			return date.getTime();
		}
		return null;
	}
	/**
	 * Listener sur les boutons
	 * @author Antoine Laurent et Anthony Brunel
	 *
	 */
	public class ButtonsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controler.newTaskButtons(((JButton)e.getSource()).getText());
		}
	}
	/**
	 * Listener sur la fenêtre
	 * @author Antoine Laurent et Anthony Brunel
	 *
	 */
	public class WindowClosing extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {

			controler.newTaskButtons("exit");
		}
	}
	/**
	 * affiche un message en cas d'erreur
	 * @param codeError le code d'erreur renvoyé par le controler
	 */
	public void printErrorDate(int codeError) {
		switch (codeError){
		case 1 :
			JOptionPane.showMessageDialog(new JFrame(),"Attention, vous avez mal initialisé vos dates !");
			break;
		case 2 : 
			JOptionPane.showMessageDialog(new JFrame(),"Attention, date de fin >= date du jour !");
			break;
		case 3 : 
			JOptionPane.showMessageDialog(new JFrame(),"Attention, date de debut < date de fin !");
			break;
		case 4 : 
			JOptionPane.showMessageDialog(new JFrame(),"Attention, veuillez entrer un nom !");
			break;
		default :
			JOptionPane.showMessageDialog(new JFrame(),"Erreur inattendue !");
			break;
		}
	}
	/**
	 * 
	 * @return importance l'importance
	 */
	public Importance getImportance() {
		return (Importance) importance.getSelectedItem();
	}

}
