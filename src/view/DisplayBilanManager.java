package view;

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

import model.Bilan;
import model.Task;

import com.toedter.calendar.JDateChooser;

import controler.Controler;

/**
 * Classe qui s'occupe du display du bilan et de ses options
 * @author Antoine Laurent et Anthony Brunel
 *
 */
@SuppressWarnings("serial")
public class DisplayBilanManager extends JFrame{
	
	/**
	 * Instance de controler
	 */
	private Controler controler;
	/**
	 * Instance de bilan
	 */
	private Bilan bilan;
	/**
	 * DateChooser pour la date de début
	 */
	private JDateChooser begin = new JDateChooser();
	/**
	 * DateChooser pour la date de fin
	 */
	private JDateChooser end = new JDateChooser();
	/**
	 * Bouton permettant de générer le bilan
	 */
	private JButton generate = new JButton("Générer");
	/**
	 * Panel contenant les options de date et le bouton générer
	 */
	private JPanel datePanel = new JPanel();
	/**
	 * JScrollPane pour la liste des tache du bilan
	 */
	private JScrollPane taskPanel;
	/**
	 * Panel pour afficher les infos du bilan
	 */
	private JPanel percentPanel = new JPanel();
	/**
	 * Liste des taches contenue dans le bilan
	 */
	private JList<Task> listTaskBilan;
	/**
	 * JLabel pour afficher le pourcentage des tâches en retard
	 */
	private JLabel percentTaskLate = new JLabel("Tâches en retard : ");
	/**
	 * JLabel pour afficher le pourcentage des tâches en cours
	 */
	private JLabel percentTaskCurrent = new JLabel("Tâches en cours : ");
	/**
	 * JLabel pour afficher le pourcentage des tâches finies
	 */
	private JLabel percentTaskEnd = new JLabel("Tâches finies : ");
	/**
	 * JLabel pour afficher début pour les dates
	 */
	private JLabel beginingLabel = new JLabel("Début : ");
	/**
	 * JLabel pour afficher fin pour les dates
	 */
	private JLabel endLabel = new JLabel("Fin : ");

	/**
	 * Constructeur
	 * @param controler Classe de controle liée au bilan
	 * @param bilan Instance de la classe bilan
	 */
	public DisplayBilanManager(Controler controler, Bilan bilan) {
		this.controler = controler;
		this.bilan = bilan;
		this.setVisible(true);
		this.setTitle("Bilan");
		setPreferredSize(new Dimension(450, 450));
		setResizable(false);
		
		init();
		pack();
		setLocationRelativeTo(null);
	}
	
	/**
	 * Initialisation du display bilan
	 */
	public void init(){
		generate.addActionListener(new ButonListener());
		
		this.listTaskBilan =  new JList<Task>(bilan.getBilanPeriod());
		taskPanel = new JScrollPane(listTaskBilan);
		taskPanel.setPreferredSize(new Dimension(250, 250));

		begin.setPreferredSize(new Dimension(100,20));
		begin.setDateFormatString("dd/MM/yyyy");
		begin.setToolTipText("dd/mm/yyyy");
		end.setPreferredSize(new Dimension(100,20));
		end.setDateFormatString("dd/MM/yyyy");
		end.setToolTipText("dd/mm/yyyy");
		
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
	
	/**
	 * Mise a jour du display Bilan
	 */
	public void updateBilan(){

		percentPanel.setVisible(true);
		taskPanel.setVisible(true);

		revalidate();
		listTaskBilan.updateUI();
		listTaskBilan.setListData(bilan.getBilanPeriod());
		percentTaskCurrent.setText("Tâches courantes : "+Double.toString(bilan.getPerctCurrent())+"%");
		percentTaskLate.setText("Tâches en retard : "+Double.toString(bilan.getPerctLate())+"%");
		percentTaskEnd.setText("Tâches finies : "+Double.toString(bilan.getPerctOk())+"%");
	}
	
	/**
	 * Listener sur le bouton générer le bilan
	 * @author Antoine Laurent et Anthony Brunel
	 *
	 */
	public class ButonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controler.generateBilan();
		}
	}
	
	/**
	 * 
	 * @return Date de debut
	 */
	public Date getDateBegin(){
		return begin.getDate();
	}
	/**
	 * 
	 * @return Date de fin
	 */
	public Date getDateEnd(){
		return end.getDate();
	}
	/**
	 * Affiche un message en cas d'erreur
	 * @param message cas d'erreur
	 */
	public void showMessage(int message) {
		switch(message){
		case 1 :
			JOptionPane.showMessageDialog(new JFrame(),"Veuillez initialiser les dates.");	
			break;
		case 2 :
			JOptionPane.showMessageDialog(new JFrame(),"La date de début doit être plus grande que la date de fin. ");
			break;
		}
	}
}
