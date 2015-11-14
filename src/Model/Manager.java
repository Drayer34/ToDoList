package Model;

import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import View.DisplayManager;

public class Manager {
	
	DisplayManager displayManager;
	Vector<Task> listTask;
	Vector<Categorie> listCategorie;

	public Manager(){
		listTask = new Vector<Task>();
		listCategorie = new Vector<Categorie>();
		listCategorie.add(new Categorie("Default"));
		listCategorie.add(new Categorie("Travail"));
		listCategorie.add(new Categorie("Personel"));
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String dateInString = "31-08-1982";
		Date date;
		try {
			date = sdf.parse(dateInString);
			listTask.add(new TaskPonctuelle(date,"yo",listCategorie.get(1)));
		} catch (ParseException e) {
			System.out.println("Erreur date to string");
			e.printStackTrace();
		}*/
	}
	
	public void addTask(Date date, String name, Categorie categorie,TaskType taskType ){
		if(taskType == TaskType.TacheLongCour){
			listTask.add(new TaskLongCours(date,name,categorie));
		}
		else if(taskType == TaskType.TachePonctuelle){
			listTask.add(new TaskPonctuelle(date,name,categorie));
		}			
	}
	
	public void addCategorie(String name){
		listCategorie.add(new Categorie(name));
	}
	
	public void renameCategorie(Categorie c, String name){
		listCategorie.add(new Categorie(name));
	}
	
	public void deleteCategorie(Categorie c){
		listCategorie.remove(c);
		for(Task t : listTask){
			if(t.getCategorie() == c){
				t.setCategorie(listCategorie.get(0));//Default
			}
		}
	}
	
	public void renameTask(Task t, String name){
		t.setName(name);
	}

	public void changeTaskCategorie(Task t, Categorie categorie){
		t.setCategorie(categorie);;
	}
	
	public void setDisplay(DisplayManager d){
		this.displayManager = d;
	}
	
	public DisplayManager getDisplayManager() {
		return displayManager;
	}

	public Vector<Task> getListTask() {
		return listTask;
	}

	public Vector<Categorie> getListCategorie() {
		return listCategorie;
	}
	public void sortTaskList(){
		Collections.sort(listTask);
	}
}
