package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Categorie;
import Model.Manager;
import Model.Task;
import controler.Controler;

public class DisplayManager extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controler controler;
	private Manager manager;
	private String taskLongCour = "Tâche au long cour";
	private String taskPonctuelle = "Tâche ponctuelle";
	
	private JMenuBar bar = new JMenuBar();
	private JMenu menu  = new JMenu("Nouvelle tâche");
	private JMenuItem newTask1 = new JMenuItem(taskLongCour);
	private JMenuItem newTask2 = new JMenuItem(taskPonctuelle);
	private JScrollPane sc_pan;
	private JList<Task> taskList;
	private JPanel taskDesc = new JPanel();
	private JTextField name = new JTextField();
	private JComboBox<Categorie> categorie;
	private JButton valide = new JButton("Valider");
	private JButton modifer = new JButton("Modifier");
	private JButton cancel = new JButton("Annuler");

	public DisplayManager(Controler controler,Manager manager){
		this.manager = manager;
		this.controler = controler;
		manager.setDisplay(this);
		controler.setDisplay(this);

		setTitle("Task Manager");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		init();
		pack();
	}

	public void init(){
		setMinimumSize(new Dimension(600,400));
		TaskListListener taskListListener = new TaskListListener();

		taskDesc.setLayout(new BorderLayout());
		taskList = new JList<Task>(manager.getListTask());
		sc_pan = new JScrollPane(taskList);

		taskList.addListSelectionListener(taskListListener);
		sc_pan.setPreferredSize(new Dimension(200,400));
		add(sc_pan,"West");
		initMenuBar();
		initTaskDesc();

		pack();
	}
	public void initMenuBar(){
		MenuBarListener mbListener = new MenuBarListener();
		newTask1.addActionListener(mbListener);
		newTask2.addActionListener(mbListener);
		menu.add(newTask1);
		menu.add(newTask2);

		bar.add(menu);
		add(bar,"North");

	}
	public void initTaskDesc(){
		
		JPanel info = new JPanel();
		JPanel buttons = new JPanel();
		JPanel b_title = new JPanel();
		JPanel b_name = new JPanel();
		JPanel b_cate = new JPanel();
		
		valide.addActionListener(new BoutonListener());
		modifer.addActionListener(new BoutonListener());
		cancel.addActionListener(new BoutonListener());
		
		categorie = new JComboBox<Categorie>(manager.getListCategorie());
		name.setEnabled(false);

		taskDesc.add(info, "Center");
		taskDesc.add(buttons, "South");

		info.setPreferredSize(new Dimension(300,350));
		//buttons.setPreferredSize(new Dimension(300,100));
		buttons.setMaximumSize(new Dimension(300,100));


		info.setLayout(new BoxLayout(info,BoxLayout.PAGE_AXIS));
		b_title.setLayout(new BoxLayout(b_title, BoxLayout.LINE_AXIS));
		b_name.setLayout(new BoxLayout(b_name, BoxLayout.LINE_AXIS));
		b_cate.setLayout(new BoxLayout(b_cate, BoxLayout.LINE_AXIS));
		
		b_title.add(new JLabel("Tache faut faire le si machin"));
		name.setMaximumSize(new Dimension(150,20));
		b_name.add(new JLabel("Nom          "));
		b_name.add(name);

		
		categorie.setMaximumSize(new Dimension(150,20));
		b_cate.add(new JLabel("Categorie "));
		b_cate.add(categorie);
		
		info.add(b_title);
		info.add(Box.createRigidArea(new Dimension(0,20)));
		info.add(b_name);
		info.add(Box.createRigidArea(new Dimension(0,20)));
		info.add(b_cate);
		
		categorie.setEnabled(false);
		taskDesc.setVisible(false);

		buttons.add(valide,"West");
		buttons.add(modifer,"Center");
		buttons.add(cancel,"East");
		
		valide.setEnabled(false);
		cancel.setEnabled(false);
		add(taskDesc,"Center");
	}
	public void updateTaskList(){
		taskList.updateUI();
	}
	public void updateTaskDesc(){
		taskDesc.setVisible(true);
		name.setText(taskList.getSelectedValue().toString());
		if(taskList.getSelectedValue().toString().length()*25 > name.getWidth()){
			name.setSize(new Dimension(taskList.getSelectedValue().toString().length()*25,20));
		}
		categorie.setSelectedItem(taskList.getSelectedValue().getCategorie());
	}
	
	
	public void switchButton(boolean b){
		if (b) {
			valide.setEnabled(b);
			cancel.setEnabled(b);
			modifer.setEnabled(!b);
			name.setEnabled(b);
			categorie.setEnabled(b);
		}
		else {
			valide.setEnabled(b);
			cancel.setEnabled(b);
			modifer.setEnabled(!b);
			name.setEnabled(b);
			categorie.setEnabled(b);
		}
	}
	
	public class MenuBarListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			controler.newTask(((JMenuItem)e.getSource()).getText());
		}
	}

	public class TaskListListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			controler.updateTaskDesc();
		}
	}
	
	public class BoutonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controler.taskModifer(((JButton)e.getSource()).getText());
		}
		
	}

	public String get_Name() {
		return name.getText();
	}

	public Categorie getSelectedCategorie() {
		return (Categorie) categorie.getSelectedItem();
	}
	
	public Task getSelectedTask(){
		return taskList.getSelectedValue();
	}

	public String getTaskLongCour() {
		return taskLongCour;
	}

	public String getTaskPonctuelle() {
		return taskPonctuelle;
	}



	
}
