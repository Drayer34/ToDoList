package controler;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import MainTask.SerializationManager;
import Model.Bilan;
import Model.Categorie;
import Model.Manager;
import Model.Task;
import Model.TaskLongCours;
import Model.TaskType;
import View.DisplayBilanManager;
import View.DisplayCategorieManager;
import View.DisplayManager;
import View.DisplayNewTask;

public class Controler {

	private Manager manager;
	private Bilan bilan;
	private DisplayManager displayManager;
	private DisplayNewTask displayNewTask;
	private DisplayCategorieManager displayCategorieManager;
	private DisplayBilanManager displayBilanManager;

	public Controler(Manager manager){
		//Initialiser les vue das le controler
		this.manager = manager;
		this.displayManager = new DisplayManager(this, manager);
		displayManager.init();
		displayManager.updateTaskList();
	}

	/* Cr�e une fenetre nouvelle tache */

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

	/*
	 * Fonction de controlle sur les boutton du panel task description
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
				if(displayManager.getPercent() == 100){
					displayManager.showMessage(1);
				}
				manager.percentChange((TaskLongCours) t,displayManager.getPercent());
			}
			if(t.getIs_end()){
				displayManager.updateMainFrame(false);		
			}else{
				displayManager.updateMainFrame(true);
			}
		}else if(b.compareTo("Annuler") == 0){
			displayManager.switchButtonTaskDesc(false);
			displayManager.updateMainFrame(true);
		}else if(b.compareTo("Supprimer") == 0){
			manager.removeTask(displayManager.getSelectedTask());
			displayManager.updateTaskList();
			displayManager.updateMainFrame(false);		
		}else if(b.compareTo("Terminer la tache") == 0){
			manager.endTask(displayManager.getSelectedTask());
			displayManager.updateTaskList();
			displayManager.updateMainFrame(false);
		}
	}

	/* valider ou cancel la classe DisplayNewTask*/

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
		}
		else if(displayNewTask.getEndDate().before(calendar.getTime())){
			displayNewTask.printErrorDate(2);
		}
		else if(displayNewTask.getBeginDate() != null && displayNewTask.getBeginDate().after(displayNewTask.getEndDate())){
			displayNewTask.printErrorDate(3);
		}
		else if(buttons.compareTo("Valider") == 0 ){
			if(displayNewTask.getBeginDate() == null)
				manager.addTask(displayNewTask.getEndDate(), displayNewTask.get_Name(), displayNewTask.getSelectedCategorie(),displayNewTask.getImportance(), displayNewTask.getTaskType());
			else{
				manager.addTask(displayNewTask.getEndDate(),displayNewTask.getBeginDate(), displayNewTask.get_Name(), displayNewTask.getSelectedCategorie(),displayNewTask.getImportance(), displayNewTask.getTaskType());
			}
			displayNewTask.close();
			displayManager.activeMenuBar();//bouton nouvelle tache menu bar
			sortControler(displayManager.getSelectedSort());
		}
	}

	public void sortControler(String button){
		if(button.compareTo("Tri 1") == 0){
			manager.sortTaskList();
		}else if(button.compareTo("Tri 2") == 0){
			manager.sortTaskListPartialDeadLine();
		}else if(button.compareTo("Tri 3") == 0){
			manager.sortTaskListImportance();
			displayManager.updateMainFrame(false);
		}
		displayManager.updateTaskList();
	}


	public void displayManagerClosing() {
		SerializationManager.saveManager(manager);
	}
	/*           FONCTION POUR LE DISPLAY CATEGORIE MANAGER               */


	public void newDisplayCategorieManager(){
		displayCategorieManager = new DisplayCategorieManager(this,manager,displayManager);
		displayManager.disableMenuBar();
	}



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
			displayCategorieManager.updateRemoveTask();
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

	/*Update bouttons task Description */

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

	public void newDisplayBilanManager() {
		bilan = new Bilan(manager.getSavTaskList());
		displayBilanManager = new DisplayBilanManager(this,bilan);
	}

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
