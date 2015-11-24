package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import Model.Categorie;
import Model.Manager;
import Model.TaskType;
import controler.Controler;

@SuppressWarnings("serial")
public class DisplayNewTask extends JFrame{

	private Controler controler;
	private Manager manager;
	private TaskType taskType;
	private JTextField name = new JTextField("Entrer le nom de la tache");
	private JPanel buttons = new JPanel();
	private JPanel contents = new JPanel(new BorderLayout());
	private JButton valide = new JButton("Valider");
	private JButton cancel = new JButton("Annuler");;
	private JDateChooser beginDateChooser = new JDateChooser();
	private JDateChooser endDateChooser = new JDateChooser();
	private JPanel b_name = new JPanel();
	private JPanel b_cate = new JPanel();
	private JPanel b_dateEnd = new JPanel();
	private JPanel b_dateBegin = new JPanel();

	private JComboBox<Categorie> categorie;

	public DisplayNewTask(Controler controler,Manager manager, TaskType taskType){
		this.controler = controler;
		this.manager = manager;
		this.taskType = taskType;
		if( taskType == TaskType.TacheLongCour)
			setTitle("Nouvelle tache au long cour");
		else
			setTitle("Nouvelle tache ponctuelle");
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

		categorie = new JComboBox<Categorie>(manager.getListCategorie());
		categorie.setMaximumSize(new Dimension(150,20));
		b_cate.add(new JLabel("Categorie?         "));
		b_cate.add(categorie);

		name.setMaximumSize(new Dimension(150,20));
		b_name.add(new JLabel("Nom?                  "));
		b_name.add(name);

		endDateChooser.setMaximumSize(new Dimension(150,20));
		b_dateEnd.add(new JLabel("Date de fin?       "));
		b_dateEnd.add(endDateChooser);

		valide.addActionListener(new ButtonsListener());
		cancel.addActionListener(new ButtonsListener());

		contents.add(Box.createRigidArea(new Dimension(0,20)));
		contents.add(b_name);
		contents.add(Box.createRigidArea(new Dimension(0,20)));
		contents.add(b_cate);
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
		b_dateBegin.add(new JLabel("Date de debut? "));
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
		return endDateChooser.getDate();
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
			JOptionPane.showMessageDialog(new JFrame(),"Attention, la date de fin doit �tre initialis� !");
			break;
		case 2 : 
			JOptionPane.showMessageDialog(new JFrame(),"Attention, date de fin >= date du jour !");
			break;
		case 3 : 
			JOptionPane.showMessageDialog(new JFrame(),"Attention, date de debut < date de fin !");
			break;
		default :
			JOptionPane.showMessageDialog(new JFrame(),"Erreur inattendu !");
			break;
		}
	}

}
