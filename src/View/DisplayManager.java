package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.SpringLayout;
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
	private JMenuBar bar = new JMenuBar();
	private JMenu menu  = new JMenu("Menu");
	private JMenuItem newTask = new JMenuItem("Nouvelle tache");
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
		MenuBarListener mbListener = new MenuBarListener();
		TaskListListener taskListListener = new TaskListListener();

		
		taskDesc.setLayout(new BorderLayout());
		taskList = new JList<Task>(manager.getListTask());
		sc_pan = new JScrollPane(taskList);

		newTask.addActionListener(mbListener);
		taskList.addListSelectionListener(taskListListener);
		bar.add(menu);
		menu.add(newTask);
		add(bar,"North");
		sc_pan.setPreferredSize(new Dimension(200,400));
		add(sc_pan,"West");
		add(taskDesc,"Center");
		initTaskDesc();

		pack();
	}

	public void initTaskDesc(){
		
		JPanel info = new JPanel();
		JPanel buttons = new JPanel();
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
		b_name.setLayout(new BoxLayout(b_name, BoxLayout.LINE_AXIS));
		b_cate.setLayout(new BoxLayout(b_cate, BoxLayout.LINE_AXIS));
		b_name.add(new JLabel("Nom          "));
		b_name.add(name);


		name.setMaximumSize(new Dimension(150,20));


		categorie.setMaximumSize(new Dimension(150,20));
		b_cate.add(new JLabel("Categorie "));
		b_cate.add(categorie);

		info.add(b_name);
		info.add(b_cate);
		categorie.setEnabled(false);
		taskDesc.setVisible(false);

		buttons.add(valide,"West");
		buttons.add(modifer,"Center");
		buttons.add(cancel,"East");
		
		valide.setEnabled(false);
		cancel.setEnabled(false);
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
			controler.newTask();
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

	public String getName() {
		return name.getText();
	}

	public Categorie getCurrentCategorie() {
		return (Categorie) categorie.getSelectedItem();
	}
	
	public Task getSelectedTask(){
		return taskList.getSelectedValue();
	}

	
}
