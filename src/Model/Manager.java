package Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

@SuppressWarnings("serial")
public class Manager implements Serializable {


	
	private Vector<Task> listTask;
	private Vector<Task> listTaskSort3;
	private Vector<Categorie> listCategorie;
	private Vector<Task> savTaskList;

	public Manager(){
		listTask = new Vector<Task>();
		savTaskList = new Vector<Task>();
		listCategorie = new Vector<Categorie>();
		listCategorie.add(new Categorie("Default"));
		listCategorie.add(new Categorie("Travail"));
		listCategorie.add(new Categorie("Personnel"));
	}

	public void addTask(Date end, String name, Categorie categorie,int importance,TaskType taskType ){
		if(taskType == TaskType.TacheLongCour){
			TaskLongCours t = new TaskLongCours(end,name,categorie, importance);
			listTask.add(t);
			savTaskList.add(t);
		}
		else if(taskType == TaskType.TachePonctuelle){
			TaskPonctuelle t = new TaskPonctuelle(end,name,categorie, importance);
			listTask.add(t);
			savTaskList.add(t);
		}
	}
	
	public void addTask(Date end,Date begin, String name, Categorie categorie,int importance,TaskType taskType ){
		if(taskType == TaskType.TacheLongCour){
			TaskLongCours t = new TaskLongCours(end,name,categorie, importance,begin);
			listTask.add(t);
			savTaskList.add(t);
		}
	}
	
	public void removeTask(Task selectedTask) {
		listTask.remove(selectedTask);
		savTaskList.remove(selectedTask);
	}

	/**
	 * 
	 * @param selectedTask la tache selectionn� dans la jlist
	 * @result supprime la tache de listTask si elle est termin� 
	 */
	public void endTask(Task selectedTask){
		selectedTask.end();
		if(selectedTask.getIs_end()){
			listTask.remove(selectedTask);
		}
	}

	public void addCategorie(String name){
		listCategorie.add(new Categorie(name));
	}
	public void removeCategorie(Categorie c) {
		for(Task t : listTask){
			if(t.getCategorie() == c){
				t.setCategorie(listCategorie.get(0));//Default
			}
		}
		listCategorie.remove(c);
	}

	public void renameCategorie(Categorie c, String name){
		c.setCatName(name);
	}


	public void renameTask(Task t, String name){
		t.setName(name);
	}
	public void percentChange(TaskLongCours t, int percent){
		t.setPercent(percent);
		t.end();
		t.updateIsLate();
		if(t.getIs_end()){
			endTask(t);
		}
	}
	public void changeTaskCategorie(Task t, Categorie categorie){
		t.setCategorie(categorie);;
	}
	public void changeImportance(Task t, int importance){
		t.setImportance(importance);
	}
	public void changeTaskDate(Task t,Date end){
		t.setDeadline(end);
		t.updateIsLate();
	}
	public Vector<Task> getListTask() {
		return listTask;
	}

	public Vector<Categorie> getListCategorie() {
		return listCategorie;
	}

	public Vector<Task> getListTaskSort3() {
		return listTaskSort3;
	}

	public Vector<Task> getSavTaskList() {
		return savTaskList;
	}

	public void setSavTaskList(Vector<Task> savTaskList) {
		this.savTaskList = savTaskList;
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
		Vector<Task> importante = new Vector<Task>();
		Vector<Task> moyen = new Vector<Task>();
		Vector<Task> faible = new Vector<Task>();
		listTaskSort3 = new Vector<Task>();

		for(Task t : listTask){
			switch(t.getImportance()){
			case 0:	
				faible.add(t);
				break;
			case 1:	
				moyen.add(t);
				break;
			case 2:	
				importante.add(t);
				break;
			}
		}

		if(importante.size() > 0){
			listTaskSort3.add(importante.get(0));
		}
		int count = 0;
		for(Task t : moyen){
			count++;
			listTaskSort3.add(t);
			if(count >= 3){
				break;
			}
		}
		count = 0;
		for(Task t : faible){
			count++;
			listTaskSort3.add(t);
			if(count >= 5){
				break;
			}
		}
	}
}
