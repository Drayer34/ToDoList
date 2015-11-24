package controler;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Model.Categorie;
import Model.Importance;
import Model.Manager;
import Model.Task;
import Model.TaskLongCours;
import Model.TaskType;
import View.DisplayCategorieManager;
import View.DisplayManager;
import View.DisplayNewTask;

public class Controler {

	private Manager manager;
	private DisplayManager displayManager;
	private DisplayNewTask displayNewTask;
	private DisplayCategorieManager displayCategorieManager;

	public Controler(Manager manager){
		//Initialiser les vue das le controler
		this.displayManager = new DisplayManager(this, manager);
		this.manager = manager;
	}

	/* Crée une fenetre nouvelle tache */

	public void newTask(String type){
		if(type.compareTo(displayManager.getTaskLongCour()) == 0){
			displayNewTask = new DisplayNewTask(this,manager,TaskType.TacheLongCour);
		}
		else if(type.compareTo(displayManager.getTaskPonctuelle()) == 0){
			displayNewTask = new DisplayNewTask(this,manager,TaskType.TachePonctuelle);
		}
		displayManager.disableMenuNewTask();
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
			if(t.isLongCourt()){
				if(displayManager.getPercent() == 100){
					displayManager.showMessage(1);
				}
				manager.percentChange((TaskLongCours) t,displayManager.getPercent());
			}
		}else if(b.compareTo("Annuler") == 0){
			displayManager.switchButtonTaskDesc(false);
			displayManager.updateTaskDesc(true);
		}else if(b.compareTo("Supprimer") == 0){
			manager.removeTask(displayManager.getSelectedTask());
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
			displayManager.activateMenuNewTask();
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
			manager.addTask(displayNewTask.getEndDate(), displayNewTask.get_Name(), displayNewTask.getSelectedCategorie(),Importance.Faible, displayNewTask.getTaskType());
			displayNewTask.close();
			displayManager.activateMenuNewTask();//bouton nouvelle tache menu bar
		}
	}

	/*           FONCTION POUR LE DISPLAY CATEGORIE MANAGER               */


	public void newDisplayCategorieManager(){
		displayCategorieManager = new DisplayCategorieManager(this,manager);
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

		}else if(buttonInfo.compareTo("Supprimer") == 0){
			manager.removeCategorie(displayCategorieManager.getSelectedCategorie());
		}else if(buttonInfo.compareTo("Modifier") == 0){
			for(Categorie c : manager.getListCategorie()){
				if(c.getCatName().compareTo(displayCategorieManager.get_reName()) == 0){
					displayCategorieManager.printError();
					return;
				}
			}
			manager.renameCategorie(displayCategorieManager.getSelectedCategorie(), displayCategorieManager.get_reName());
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
}
