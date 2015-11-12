package View;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DisplayNewTask extends JFrame{

	private JTextField name, categorieName;
	private JPanel buttons;
	private JPanel contain;
	private JButton addTask;
	public DisplayNewTask(){
		setTitle("New Task");
		this.setVisible(true);

		addTask = new JButton("Valider");
		buttons = new JPanel();
		contain = new JPanel(new BorderLayout());
		name = new JTextField("Entrer le nom de la tache");
		categorieName = new JTextField("Entrer le nom de la catégorie");

		buttons.add(addTask,"West");
		contain.add(name,"North");
		contain.add(categorieName,"South");

		add(contain);
		add(buttons,"South");
		pack();
	}
}
