package controler;

import java.util.Date;

import Model.Importance;
import Model.Manager;
import Model.Task;
import Model.TaskType;
import View.DisplayManager;
import View.DisplayNewTask;

public class Controler {

	private Manager manager;
	private DisplayManager displayManager;
	private DisplayNewTask displayNewTask;


	public Controler(Manager manager){
		//Initialiser les vue das le controler
		this.displayManager = new DisplayManager(this, manager);
		this.manager = manager;
	}

	public void newTask(String type){
		if(type.compareTo(displayManager.getTaskLongCour()) == 0){
		displayNewTask = new DisplayNewTask(this,manager,TaskType.TacheLongCour);
		}
		else if(type.compareTo(displayManager.getTaskPonctuelle()) == 0){
			displayNewTask = new DisplayNewTask(this,manager,TaskType.TachePonctuelle);
		}
	}

	public void updateTaskDesc(){
		displayManager.updateTaskDesc();
	}

	public void taskModifer(String b) {
		if (b.compareTo("Modifier") == 0){
			displayManager.switchButton(true);
		}else if(b.compareTo("Valider") == 0){
			Task t = displayManager.getSelectedTask();
			displayManager.switchButton(false);
			
			manager.renameTask(t, displayManager.get_Name());
			manager.changeTaskCategorie(t, displayManager.getSelectedCategorie());
			System.out.println(t);
			
		}else{
			displayManager.switchButton(false);
			displayManager.updateTaskDesc();
		}
	}
	
	public void newTaskButtons(String buttons){
		if(buttons.compareTo("Valider") == 0){
			manager.addTask(new Date(), displayNewTask.get_Name(), displayNewTask.getSelectedCategorie(),Importance.Faible, displayNewTask.getTaskType());
			displayNewTask.close();
			displayManager.updateTaskList();
		}else if(buttons.compareTo("Annuler") == 0){
			displayNewTask.close();
		}
	}
	
	public void setDisplay(DisplayManager d){
		this.displayManager = d;
	}

}
