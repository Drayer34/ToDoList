package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import Model.Categorie;
import Model.Importance;
import Model.Manager;
import Model.Task;
import Model.TaskLongCours;
import controler.Controler;

public class DisplayManager extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controler controler;
	private Manager manager;
	private String taskLongCour = "Tache au long cour";
	private String taskPonctuelle = "Tache ponctuelle";

	private JMenuBar bar = new JMenuBar();
	private JMenu menuNewTask  = new JMenu("Nouvelle tache");
	private JMenu menuEdit  = new JMenu("Option");
	private JMenuItem menuCategorie  = new JMenuItem("Editer categorie");
	private JMenu listSort  = new JMenu("Tris");
	private JCheckBoxMenuItem sort1 = new JCheckBoxMenuItem("Tri 1");
	private JCheckBoxMenuItem sort2 = new JCheckBoxMenuItem("Tri 2");
	private JCheckBoxMenuItem sort3 = new JCheckBoxMenuItem("Tri 3");
	private ButtonGroup sortGroup = new ButtonGroup();
	private JMenuItem newTaskItem1 = new JMenuItem(taskLongCour);
	private JMenuItem newTaskItem2 = new JMenuItem(taskPonctuelle);

	private JScrollPane sc_pan;
	private JList<Task> taskList;
	private JPanel taskDesc = new JPanel();
	private JLabel title = new JLabel("");
	private JTextField name = new JTextField();
	private JComboBox<Categorie> categorie;
	private JComboBox<Importance> importance;

	private JButton valide = new JButton("Valider");
	private JButton modifer = new JButton("Modifier");
	private JButton cancel = new JButton("Annuler");
	private JButton delete = new JButton("Supprimer");
	private JButton finish = new JButton("Terminer");

	private JTextField percent = new JTextField("");
	private JProgressBar progressBar = new JProgressBar();
	private JDateChooser dateChooser = new JDateChooser();
	
	private JPanel b_progressBar = new JPanel();
	private JPanel b_finishButton = new JPanel();

	public DisplayManager(Controler controler,Manager manager){
		this.manager = manager;
		this.controler = controler;
		manager.setDisplayManager(this);
		setLocationRelativeTo(null);
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
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		sc_pan = new JScrollPane(taskList);
		taskList.addListSelectionListener(taskListListener);
		sc_pan.setPreferredSize(new Dimension(200,400));
		add(sc_pan,"West");
		initMenuBar();
		initTaskDesc();

		pack();
	}
	public void initMenuBar(){
		newTaskMenuBarListener mbListener = new newTaskMenuBarListener();
		newTaskItem1.addActionListener(mbListener);
		newTaskItem2.addActionListener(mbListener);
		menuNewTask.add(newTaskItem1);
		menuNewTask.add(newTaskItem2);
		
		sort1.addItemListener(new SortListener());
		sort2.addItemListener(new SortListener());
		sort3.addItemListener(new SortListener());

		sortGroup.add(sort1);
		sortGroup.add(sort2);
		sortGroup.add(sort3);

		listSort.add(sort1);
		listSort.add(sort2);
		listSort.add(sort3);
		sort1.setSelected(true);
		menuCategorie.addActionListener(new categorieMenuBarListener());
		menuEdit.add(menuCategorie);
		menuEdit.add(listSort);
		
		bar.add(menuNewTask);
		bar.add(menuEdit);
		add(bar,"North");

	}

	public void initTaskDesc(){

		JPanel info = new JPanel();
		JPanel buttons = new JPanel();
		JPanel b_title = new JPanel();
		JPanel b_name = new JPanel();
		JPanel b_cate = new JPanel();
		JPanel b_importance = new JPanel();
		JPanel b_date = new JPanel();


		valide.addActionListener(new BoutonListener());
		modifer.addActionListener(new BoutonListener());
		cancel.addActionListener(new BoutonListener());
		delete.addActionListener(new BoutonListener());
		finish.addActionListener(new BoutonListener());

		categorie = new JComboBox<Categorie>(manager.getListCategorie());


		info.setPreferredSize(new Dimension(300,350));
		buttons.setMaximumSize(new Dimension(300,100));


		info.setLayout(new BoxLayout(info,BoxLayout.PAGE_AXIS));
		b_title.setLayout(new BoxLayout(b_title, BoxLayout.LINE_AXIS));
		b_name.setLayout(new BoxLayout(b_name, BoxLayout.LINE_AXIS));
		b_cate.setLayout(new BoxLayout(b_cate, BoxLayout.LINE_AXIS));
		b_importance.setLayout(new BoxLayout(b_importance, BoxLayout.LINE_AXIS));
		b_progressBar.setLayout(new BoxLayout(b_progressBar, BoxLayout.LINE_AXIS));
		b_finishButton.setLayout(new BoxLayout(b_finishButton, BoxLayout.LINE_AXIS));

		b_title.add(title);

		name.setMaximumSize(new Dimension(150,20));
		name.setEnabled(false);

		b_name.add(new JLabel("Nom           "));
		b_name.add(name);

		/*Initialisation de categorie */
		categorie.setMaximumSize(new Dimension(150,20));
		categorie.setEnabled(false);
		b_cate.add(new JLabel("Categorie  "));
		b_cate.add(categorie);
		
		/*Initialisation importance */
		
		importance = new JComboBox<Importance>(Importance.values());
		
		importance.setMaximumSize(new Dimension(150,20));
		importance.setEnabled(false);
		
		b_importance.add(new JLabel("Importance "));
		b_importance.add(importance);
		
		/*Initialisation Date Picker */		


		b_date.add(new JLabel("Deadline   "));
		dateChooser.setPreferredSize(new Dimension(150,20));
		dateChooser.setEnabled(false);
		b_date.add(dateChooser);

		/* Init pourcentage */

		b_progressBar.add(new JLabel("Evolution    "));
		percent.setMaximumSize(new Dimension(30,20));
		percent.setPreferredSize(new Dimension(30,20));
		percent.setEnabled(false);
		
		percent.addKeyListener(new percentKeyListener());
		progressBar.setStringPainted(true);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setMaximumSize(new Dimension(120,20));
		b_progressBar.add(percent);
		b_progressBar.add(progressBar);
		b_progressBar.setVisible(false);

		/* boutton terminé tache ponctuelle */
		
		b_finishButton.add(finish);
		b_finishButton.setVisible(false);
		
		/* Ajout des box */
		info.add(b_title);
		info.add(Box.createRigidArea(new Dimension(0,20)));
		info.add(b_name);
		info.add(Box.createRigidArea(new Dimension(0,20)));
		info.add(b_cate);
		info.add(Box.createRigidArea(new Dimension(0,20)));
		info.add(b_importance);
		info.add(Box.createRigidArea(new Dimension(0,20)));
		info.add(b_progressBar,"Center");
		info.add(Box.createRigidArea(new Dimension(0,20)));
		info.add(b_date);
		info.add(Box.createRigidArea(new Dimension(0,20)));
		info.add(b_finishButton);

		taskDesc.setVisible(false);

		buttons.add(valide);
		buttons.add(modifer);
		buttons.add(cancel);
		buttons.add(delete);

		valide.setEnabled(false);
		cancel.setEnabled(false);
		taskDesc.add(info, "Center");
		taskDesc.add(buttons, "South");

		add(taskDesc,"Center");
	}

	public void updateTaskList(){
		if(sort3.isSelected()){
			updateTaskDesc(false);
			taskList.setListData(manager.getListTaskSort3());
		}else{
			updateTaskDesc(false);
			taskList.setListData(manager.getListTask());
		}
		taskList.updateUI();
	}



	public void updateTaskDesc(boolean isVisible){
		taskDesc.setVisible(isVisible);
		if(isVisible){
			name.setText(taskList.getSelectedValue().toString());
			categorie.setSelectedItem(taskList.getSelectedValue().getCategorie());
			importance.setSelectedIndex(taskList.getSelectedValue().getImportance());
			dateChooser.setDate(taskList.getSelectedValue().getDeadline());
			b_progressBar.setVisible(false);
			b_finishButton.setVisible(false);
			if(taskList.getSelectedValue().isLongCourt()){
				percent.setText(Integer.toString(((TaskLongCours)taskList.getSelectedValue()).getPercent()));
				progressBar.setValue(((TaskLongCours)taskList.getSelectedValue()).getPercent());
				b_progressBar.setVisible(true);
				title.setText(taskLongCour);
				progressBar.updateUI();
			}else{
				title.setText(taskPonctuelle);
				b_finishButton.setVisible(true);
			}
			taskList.updateUI();
		}else{
			taskList.clearSelection();
		}
		taskDesc.revalidate();
	}

	public void switchButtonTaskDesc(boolean b){
		valide.setEnabled(b);
		cancel.setEnabled(b);
		modifer.setEnabled(!b);
		name.setEnabled(b);
		importance.setEnabled(b);
		categorie.setEnabled(b);
		dateChooser.setEnabled(b);
		percent.setEnabled(b);
	}

	/*Dï¿½sactive le menu nouvelle tache */
	public void disableMenuNewTask(){
		menuNewTask.setEnabled(false);
		menuNewTask.validate();
	}

	/*Activer le menu nouvelle tache */
	public void activateMenuNewTask(){
		menuNewTask.setEnabled(true);
		menuNewTask.validate();
	}

	public class newTaskMenuBarListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			controler.newTask(((JMenuItem)e.getSource()).getText());
		}
	}

	public class categorieMenuBarListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			controler.newDisplayCategorieManager();
		}
	}
	
	public class percentKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if(c < KeyEvent.VK_0 || c > KeyEvent.VK_9)
				e.consume();
		}
	}
	public class TaskListListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(taskList.getSelectedValue() != null){
				taskDesc.setVisible(true);
				name.setText(taskList.getSelectedValue().toString());
				categorie.setSelectedItem(taskList.getSelectedValue().getCategorie());
				importance.setSelectedIndex(taskList.getSelectedValue().getImportance());
				dateChooser.setDate(taskList.getSelectedValue().getDeadline());
				b_progressBar.setVisible(false);
				b_finishButton.setVisible(false);
				if(taskList.getSelectedValue().isLongCourt()){
					percent.setText(Integer.toString(((TaskLongCours)taskList.getSelectedValue()).getPercent()));
					progressBar.setValue(((TaskLongCours)taskList.getSelectedValue()).getPercent());
					progressBar.updateUI();
					b_progressBar.setVisible(true);
					title.setText(taskLongCour);
				}else{
					title.setText(taskPonctuelle);
					b_finishButton.setVisible(true);
				}
			}
		}
	}
	
	/*Task Desc Bouttons Listener*/
	public class BoutonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			controler.taskModifer(((JButton)e.getSource()).getText());
		}
	}

	public class SortListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			controler.sortControler(((JCheckBoxMenuItem)e.getItem()).getText());
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

	public int getPercent() {
		return Integer.parseInt(percent.getText());
	}

	public void setPercent(JTextField percent) {
		this.percent = percent;
	}

	/*
	 * 1 = message de fin de tache
	 */
	public void showMessage(int message) {
		switch(message){
		case 1 :
			JOptionPane.showMessageDialog(new JFrame(),"Vous venez de terminer votre tache celle-ci a ï¿½tï¿½ transfï¿½rï¿½ dans le bilan.");	
			break;
		}
	}
	public String getSelectedSort(){
		if(sort1.isSelected()){
			return sort1.getText();
		}else if(sort2.isSelected()){
			return sort2.getText();
		}else if(sort3.isSelected()){
			return sort3.getText();
		}
		return "Default";
	}
	public int getImportance(){
		return importance.getSelectedIndex();
	}

}
