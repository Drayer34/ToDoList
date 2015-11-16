package Model;

import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import View.DisplayManager;

public class Manager {
	
	DisplayManager displayManager;
	Vector<Task> listTask;
	Vector<Categorie> listCategorie;
	Vector<Task> bilan;
	public double perctOk;
	public double perctLate;
	public double perctCurrent;
	
	public Manager(){
		listTask = new Vector<Task>();
		listCategorie = new Vector<Categorie>();
		listCategorie.add(new Categorie("Default"));
		listCategorie.add(new Categorie("Travail"));
		listCategorie.add(new Categorie("Personel"));
	}
	
	public void addTask(Date date, String name, Categorie categorie,Importance importance,TaskType taskType ){
		if(taskType == TaskType.TacheLongCour){
			listTask.add(new TaskLongCours(date,name,categorie, importance));
		}
		else if(taskType == TaskType.TachePonctuelle){
			listTask.add(new TaskPonctuelle(date,name,categorie,importance));
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
	
	public void bilan (Date fin){
		//Date today = new Date();
		int cptEnd=0;
		int cptLate=0;
		int cptCurt=0;
		int total=0;
		
		for(Task t : listTask){
			if (t.getDeadline().getTime()<=fin.getTime())
				if (!t.getIs_end()){
					bilan.add(t);
					cptCurt++;
				}
				else {
					cptEnd++;
				}
				if (t.updateIsLate()){
					cptLate++;
				}
				total++;
		}
		this.perctCurrent = (double)cptCurt/(double)total *100;
		this.perctLate = (double)cptLate/(double)total *100;
		this.perctOk = (double)cptEnd/(double)total *100;
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
	public void sortTaskListPartialDeadLine(){
		Collections.sort(listTask, new CompareTaskDate());
	}
	//Tri la liste par point (imortance) mais ne prend pas 1-Important 3-moyen  5-faible
	public void sortTaskListImportance(){
		Collections.sort(listTask, new CompareTaskImportance()); 
	}
	
}
