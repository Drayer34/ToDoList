package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.Categorie;
import Model.Manager;
import Model.TaskType;
import controler.Controler;

public class DisplayNewTask extends JFrame{

	private Controler controler;
	private Manager manager;
	private TaskType taskType;
	private JTextField name = new JTextField("Entrer le nom de la tache");
	private JPanel buttons = new JPanel();
	private JPanel contents = new JPanel(new BorderLayout());
	private JButton valide = new JButton("Valider");
	private JButton cancel = new JButton("Annuler");;


	private JComboBox<Categorie> categorie;
	
	public DisplayNewTask(Controler controler,Manager manager, TaskType taskType){
		this.controler = controler;
		this.manager = manager;
		this.taskType = taskType;
		if( taskType == TaskType.TacheLongCour)
			setTitle("Nouvelle tâche au long cour");
		else
			setTitle("Nouvelle tâche ponctuelle");
		
		this.setVisible(true);
		init();
		pack();
	}
	
	public void init(){

		categorie = new JComboBox<Categorie>(manager.getListCategorie());
		categorie.setMaximumSize(new Dimension(150,20));
		valide.addActionListener(new ButtonsListener());
		cancel.addActionListener(new ButtonsListener());

		buttons.add(valide,"West");
		buttons.add(cancel,"East");
		contents.add(name,"North");
		contents.add(categorie,"South");

		add(contents);
		add(buttons,"South");
	}
	public void initTachePonctuelle(){
		
	}
	
	public void initTacheAuLongCour(){
		
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

	public class ButtonsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controler.newTaskButtons(((JButton)e.getSource()).getText());
		}
	}
	
}
