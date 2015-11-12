package controler;

import Model.Manager;
import Model.Task;
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

	public void newTask(){
		DisplayNewTask displayNewTask = new DisplayNewTask();
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
			
			manager.renameTask(t, displayManager.getName());
			manager.changeTaskCategorie(t, displayManager.getCurrentCategorie());
			System.out.println(t);
			
		}else{
			displayManager.switchButton(false);
			displayManager.updateTaskDesc();
		}
	}

	public void setDisplay(DisplayManager d){
		this.displayManager = d;
	}

}
