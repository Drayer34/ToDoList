package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.toedter.calendar.JDateChooser;

import Model.Bilan;
import Model.Task;
import controler.Controler;

@SuppressWarnings("serial")
public class DisplayBilanManager extends JFrame{
	
	private Controler controler;
	private Bilan bilan;
	private JDateChooser begin = new JDateChooser();
	private JDateChooser end = new JDateChooser();
	private JButton generate = new JButton("Gï¿½nï¿½rer");
	private JPanel datePanel = new JPanel();
	private JScrollPane taskPanel;
	private JPanel percentPanel = new JPanel();
	private JList<Task> listTaskBilan;
	private JLabel percentTaskLate = new JLabel("Taches en retard : ");
	private JLabel percentTaskCurrent = new JLabel("Taches en cours : ");
	private JLabel percentTaskEnd = new JLabel("Taches finie : ");
	private JLabel beginingLabel = new JLabel("Dï¿½but : ");
	private JLabel endLabel = new JLabel("Fin : ");

	public DisplayBilanManager(Controler controler, Bilan bilan) {
		this.controler = controler;
		this.bilan = bilan;
		this.setVisible(true);
		setPreferredSize(new Dimension(450, 450));
		setResizable(false);
		
		init();
		pack();
	}
	
	public void init(){
		generate.addActionListener(new ButonListener());
		
		this.listTaskBilan =  new JList<Task>(bilan.getBilanPeriod());
		taskPanel = new JScrollPane(listTaskBilan);
		taskPanel.setPreferredSize(new Dimension(250, 250));

		begin.setPreferredSize(new Dimension(100,20));
		end.setPreferredSize(new Dimension(100,20));
		
		datePanel.add(beginingLabel);
		datePanel.add(begin);
		datePanel.add(endLabel);
		datePanel.add(end);
		datePanel.add(generate);
		
		percentPanel.setLayout(new BoxLayout(percentPanel, BoxLayout.PAGE_AXIS));
		percentPanel.add(Box.createRigidArea(new Dimension(0,20)));
		percentPanel.add(percentTaskCurrent);
		percentPanel.add(Box.createRigidArea(new Dimension(0,20)));
		percentPanel.add(percentTaskLate);
		percentPanel.add(Box.createRigidArea(new Dimension(0,20)));
		percentPanel.add(percentTaskEnd);
		
		add(datePanel, "North");
		add(taskPanel, "East");
		add(percentPanel, "West");
		
		percentPanel.setVisible(false);
		taskPanel.setVisible(false);
	}
	
	public void updateBilan(){

		percentPanel.setVisible(true);
		taskPanel.setVisible(true);
		percentPanel.revalidate();
		taskPanel.revalidate();
		revalidate();
		
		listTaskBilan.setListData(bilan.getBilanPeriod());
		listTaskBilan.updateUI();
		percentTaskCurrent.setText("Taches courantes : "+Double.toString(bilan.getPerctCurrent())+"%");
		percentTaskLate.setText("Taches en retard : "+Double.toString(bilan.getPerctLate())+"%");
		percentTaskEnd.setText("Taches finies : "+Double.toString(bilan.getPerctOk())+"%");
	}
	
	public class ButonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controler.generateBilan();
		}
	}
	
	public Date getDateBegin(){
		return begin.getDate();
	}
	
	public Date getDateEnd(){
		return end.getDate();
	}
	
	public void showMessage(int message) {
		switch(message){
		case 1 :
			JOptionPane.showMessageDialog(new JFrame(),"Veuillez initialiser les dates.");	
			break;
		case 2 :
			JOptionPane.showMessageDialog(new JFrame(),"La date de début doit être plus grande à la date de fin. ");
			break;
		}
	}
}
