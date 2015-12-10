package controler;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import main.SerializationManager;
import model.Bilan;
import model.Categorie;
import model.Manager;
import model.Task;
import model.TaskLongCours;
import model.TaskPonctuelle;
import model.TaskType;
import view.DisplayBilanManager;
import view.DisplayCategorieManager;
import view.DisplayManager;
import view.DisplayNewTask;

/**
 * Classe pour implémenter le controler danc le model MVC 
 * @author Antoine Laurent et Anthony Brunel
 *
 */
public class Controler {

	/**
	 * une instance de Manager
	 */
	private Manager manager;
	/**
	 * une instance de bilan
	 */
	private Bilan bilan;
	/**
	 * une instance du display principale
	 */
	private DisplayManager displayManager;
	/**
	 * une instance du display de création de nouvelle tâche
	 */
	private DisplayNewTask displayNewTask;
	/**
	 * une instance du display de créattion/modification des catégories
	 */
	private DisplayCategorieManager displayCategorieManager;
	/**
	 * une instance du display du bilan
	 */
	private DisplayBilanManager displayBilanManager;

	/**
	 * Constructeur du controler
	 * @param manager l'instance du manager sur lequel on opère
	 */
	public Controler(Manager manager){
		//Initialiser les vue das le controler
		this.manager = manager;
		this.displayManager = new DisplayManager(this, manager);
		displayManager.init();
	}


	/**
	 * Créer une fenêtre nouvelle tâche
	 * @param type le type de tâche que l'on créer
	 */
	public void newTask(String type){
		if(type.compareTo(displayManager.getTaskLongCour()) == 0){
			displayNewTask = new DisplayNewTask(this,manager,TaskType.TacheLongCour);
		}
		else if(type.compareTo(displayManager.getTaskPonctuelle()) == 0){
			displayNewTask = new DisplayNewTask(this,manager,TaskType.TachePonctuelle);
		}
		displayManager.updateMainFrame(false);
		displayManager.disableMenuBar();
	}

	/**
	 * Fonction de controlle sur les boutton du panel task description
	 * @param b le texte du bouton sur lequel on a cliqué
	 */
	public void taskModifer(String b) {
		if (b.compareTo("Modifier") == 0){
			displayManager.switchButtonTaskDesc(true);
		}else if(b.compareTo("Valider") == 0){
			Task t = displayManager.getSelectedTask();
			displayManager.switchButtonTaskDesc(false);
			manager.renameTask(t, displayManager.get_Name());
			manager.changeTaskCategorie(t, displayManager.getSelectedCategorie());
			manager.changeImportance(t, displayManager.getImportance());
			GregorianCalendar calendar = new java.util.GregorianCalendar(); 
			calendar.setTime(new Date());
			calendar.add (Calendar.DATE, -1);
			if(displayManager.getDeadLine().before(calendar.getTime())){
				displayManager.showMessage(2);
			}else{
				manager.changeTaskDate(t, displayManager.getDeadLine());
			}
			if(t.isLongCourt()){
				if(displayManager.getPercent() >= 100){
					displayManager.showMessage(1);
				}
				manager.percentChange((TaskLongCours) t,displayManager.getPercent());
			}
			if(t.getIs_end()){
				displayManager.updateSortTaskList();
				displayManager.updateMainFrame(false);	
			}else{
				displayManager.refreshTaskList();
				displayManager.updateMainFrame(true);
			}
		}else if(b.compareTo("Annuler") == 0){
			displayManager.switchButtonTaskDesc(false);
			displayManager.updateMainFrame(true);
		}else if(b.compareTo("Supprimer") == 0){
			manager.removeTask(displayManager.getSelectedTask());
			displayManager.updateSortTaskList();
			displayManager.updateMainFrame(false);		
		}else if(b.compareTo("Tâche finie") == 0){
			manager.endTask(displayManager.getSelectedTask());
			displayManager.updateSortTaskList();
			displayManager.updateMainFrame(false);
		}
	}

	/**
	 *  Conditions sur la classe DisplayNewTask
	 *  Si on annule ou ferme la fenêtre sans valider alors on remet la bar des menus valide et on ferme la fenetre
	 *  Condition sur les dates
	 * @param buttons le nom du bouttons sur lequel on a cliqué
	 * 
	 */
	public void newTaskButtons(String buttons){
		/*              Controle des dates          */
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.setTime(new Date());
		calendar.add (Calendar.DATE, -1);
		if(buttons.compareTo("Annuler") == 0 || buttons.compareTo("exit") == 0){
			displayNewTask.close();
			displayManager.activeMenuBar();
		}
		else if(displayNewTask.getEndDate() == null){
			displayNewTask.printErrorDate(1);
		}else if(displayNewTask.getTaskType() == TaskType.TacheLongCour && displayNewTask.getBeginDate() == null){
			displayNewTask.printErrorDate(1);
		}
		else if(displayNewTask.getEndDate().before(calendar.getTime())){
			displayNewTask.printErrorDate(2);
		}
		else if(displayNewTask.getBeginDate() != null && displayNewTask.getBeginDate().after(displayNewTask.getEndDate())){
			displayNewTask.printErrorDate(3);
		}else if(displayNewTask.get_Name().compareTo("") == 0){
			displayNewTask.printErrorDate(4);
		}
		else if(buttons.compareTo("Valider") == 0 ){
			Task t = null;
			if(displayNewTask.getBeginDate() == null){
				if(displayNewTask.getTaskType() == TaskType.TachePonctuelle){
					t = new TaskPonctuelle(displayNewTask.getEndDate(), displayNewTask.get_Name(), displayNewTask.getSelectedCategorie(),displayNewTask.getImportance());
				}else if(displayNewTask.getTaskType() == TaskType.TacheLongCour){
					t = new TaskLongCours(displayNewTask.getEndDate(), displayNewTask.get_Name(), displayNewTask.getSelectedCategorie(),displayNewTask.getImportance());
				}
			}else{
				t = new TaskLongCours(displayNewTask.getBeginDate(),displayNewTask.getEndDate(), displayNewTask.get_Name(), displayNewTask.getSelectedCategorie(),displayNewTask.getImportance());
			}
			manager.addTask(t);
			displayNewTask.close();
			displayManager.activeMenuBar();//bouton nouvelle tache menu bar
			sortControler(displayManager.getSelectedSort());
		}
	}

	/**
	 * On effectu les tris des tâches
	 * @param button le tri est demandé
	 */
	public void sortControler(String button){
		if(button.compareTo("Tri simple") == 0){
			manager.sortTaskList();
		}else if(button.compareTo("Tri avancé") == 0){
			manager.sortTaskListPartialDeadLine();
		}else if(button.compareTo("Tri particulié") == 0){
			manager.sortTaskListImportance();
			displayManager.updateMainFrame(false);
		}
		displayManager.updateSortTaskList();
	}

	/**
	 * Lors de la fermeture de la fenêtre, on souvegarde les données.
	 */
	public void displayManagerClosing() {
		SerializationManager.saveManager(manager);
	}
	
	/*           FONCTION POUR LE DISPLAY CATEGORIE MANAGER               */

	/**
	 * on définie la valeure de displayCatégorieManager
	 */
	public void newDisplayCategorieManager(){
		displayCategorieManager = new DisplayCategorieManager(this,manager,displayManager);
		displayManager.disableMenuBar();
	}

	/**
	 * gère les boutons de la fenêtre catégorie
	 * @param buttonInfo le texte du bouton sur lequel on appuie
	 */
	public void updateCategorieManager(String buttonInfo) {
		if(buttonInfo.compareTo("Ajouter") == 0 && displayCategorieManager.get_Name() != null){
			for(Categorie c : manager.getListCategorie()){
				if(c.getCatName().compareTo(displayCategorieManager.get_Name()) == 0){
					displayCategorieManager.printError();
					return;
				}
			}
			manager.addCategorie(displayCategorieManager.get_Name());
			displayCategorieManager.updateComboBox();
			displayCategorieManager.updateNameAfterAdd();
		}else if(buttonInfo.compareTo("Supprimer") == 0){
			manager.removeCategorie(displayCategorieManager.getSelectedCategorie());
			displayCategorieManager.updateRemoveCategorie();
		}else if(buttonInfo.compareTo("Modifier") == 0){
			for(Categorie c : manager.getListCategorie()){
				if(c.getCatName().compareTo(displayCategorieManager.get_reName()) == 0){
					displayCategorieManager.printError();
					return;
				}
			}
			manager.renameCategorie(displayCategorieManager.getSelectedCategorie(), displayCategorieManager.get_reName());
			displayCategorieManager.updateComboBox();	
		}else if(buttonInfo.compareTo("exit") == 0 ){
			displayCategorieManager.close();
			displayManager.activeMenuBar();
		}
	}

	/**
	 * Update la comboList de task description
	 */
	public void updateEditW(){
		displayCategorieManager.updateReName();
		if(displayCategorieManager.getSelectedCategorieIndex() == 0){
			displayCategorieManager.setDeleteEnabled(false);
			displayCategorieManager.setModiferEnabled(false);
		}else{
			displayCategorieManager.setDeleteEnabled(true);
			displayCategorieManager.setModiferEnabled(true);
		}
	}

	/* Bilan */

	/**
	 * on définie la varibale bilan
	 */
	public void newDisplayBilanManager() {
		bilan = new Bilan(manager.getSavTaskList());
		displayBilanManager = new DisplayBilanManager(this,bilan);
	}

	/**
	 * génération du bilan
	 */
	public void generateBilan() {
		if(displayBilanManager.getDateBegin() == null || displayBilanManager.getDateEnd() == null){
			displayBilanManager.showMessage(1);
		}
		else if(displayBilanManager.getDateBegin().after(displayBilanManager.getDateEnd())){
			displayBilanManager.showMessage(2);
		}
		else{
			bilan.generateBilan(displayBilanManager.getDateBegin(), displayBilanManager.getDateEnd());
			displayBilanManager.updateBilan();
		}
	} 



}
