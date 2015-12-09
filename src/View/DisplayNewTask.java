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

@SuppressWarnings("serial")
public class DisplayNewTask extends JFrame{

	private Controler controler;
	private Manager manager;
	private TaskType taskType;
	private JTextField name = new JTextField("");
	private JPanel buttons = new JPanel();
	private JPanel contents = new JPanel(new BorderLayout());
	private JButton valide = new JButton("Valider");
	private JButton cancel = new JButton("Annuler");;
	private JDateChooser beginDateChooser = new JDateChooser();
	private JDateChooser endDateChooser = new JDateChooser();
	private JComboBox<Importance> importance;
	private JPanel b_name = new JPanel();
	private JPanel b_cate = new JPanel();
	private JPanel b_importance = new JPanel();
	private JPanel b_dateEnd = new JPanel();
	private JPanel b_dateBegin = new JPanel();

	private JComboBox<Categorie> categorie;

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


	public void close(){
		this.dispose();
	}

	public String get_Name() {
		return name.getText();
	}

	public void set_Name(JTextField name) {
		this.name = name;
	}

	public Categorie getSelectedCategorie() {
		return (Categorie) categorie.getSelectedItem();
	}

	public TaskType getTaskType() {
		return taskType;
	}
	public Date getBeginDate(){
		return beginDateChooser.getDate();
	}
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
	public class ButtonsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controler.newTaskButtons(((JButton)e.getSource()).getText());
		}
	}

	public class WindowClosing extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {

			controler.newTaskButtons("exit");
		}

	}

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

	public Importance getImportance() {
		return (Importance) importance.getSelectedItem();
	}

}
