package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

/**
 * Classe qui contient les listes de tâches et de catégories
 * @author Antoine Laurent et Anthony Brunel
 *
 */
@SuppressWarnings("serial")
public class Manager implements Serializable {


	/**
	 * Liste de tâches.
	 */
	private Vector<Task> listTask;
	/**
	 * Liste de tâches triées pour le troisième tris.
	 */
	private Vector<Task> listTaskSort3;
	/**
	 * Liste des catégories.
	 */
	private Vector<Categorie> listCategorie;
	/**
	 * Liste de sauvegarde de tâches pour le bilan
	 */
	private Vector<Task> savTaskList;

	/**
	 * Constructeur de classe, on initialise les listes.
	 */
	public Manager(){
		listTask = new Vector<Task>();
		savTaskList = new Vector<Task>();
		listCategorie = new Vector<Categorie>();
		listCategorie.add(new Categorie("Défault"));
		listCategorie.add(new Categorie("Travail"));
		listCategorie.add(new Categorie("Personnel"));
	}

	/**
	 * Ajoute une tâche � listeTask et savTaskList
	 * @param t tâche
	 */
	public void addTask(Task t){
			listTask.add(t);
			savTaskList.add(t);		
	}


	/**
	 * On supprime la tâche des deux vecteurs de tache, listTask et savTaskList.
	 * @param selectedTask tâche couramment sélectionnée 
	 */
	public void removeTask(Task selectedTask) {
		listTask.remove(selectedTask);
		savTaskList.remove(selectedTask);
	}

	/**
	 * Supprime la tache de listTask si elle est terminé 
	 * @param selectedTask la tache selectionné dans la jlist
	 */
	public void endTask(Task selectedTask){
		selectedTask.end();
		if(selectedTask.getIs_end()){
			listTask.remove(selectedTask);
		}
	}

	/**
	 * Ajout d'une catégorie aux vecteur de catégories.
	 * @param name nom de la catégorie à ajoutée
	 */
	public void addCategorie(String name){
		listCategorie.add(new Categorie(name));
	}

	/**
	 * Supression d'une catégorie aux vecteur de catégories.
	 * @param c la catégorie
	 */
	public void removeCategorie(Categorie c) {
		for(Task t : listTask){
			if(t.getCategorie() == c){
				t.setCategorie(listCategorie.get(0));//Default
			}
		}
		listCategorie.remove(c);
	}

	/**
	 * Renomage d'une catégorie passé en paramètre.
	 * @param c la catégorie
	 * @param name le nouveau nom
	 */
	public void renameCategorie(Categorie c, String name){
		c.setCatName(name);
	}

	/**
	 * Renomage d'une tâches passé en paramètre.
	 * @param t la tâche à renomée
	 * @param name nom de la tâche
	 */
	public void renameTask(Task t, String name){
		t.setName(name);
	}

	/**
	 * CHangement du pourcentage d'une tâche au long cours.
	 * @param t la tâche que l'on modifie
	 * @param percent le poucentage a mettre
	 */
	public void percentChange(TaskLongCours t, int percent){
		t.setPercent(percent);
		if(t.getIs_end()){
			endTask(t);
		}
	}

	/**
	 * Changement de la catégorie d'une tâche.
	 * @param t la tâche que l'on modifie
	 * @param categorie la catégorie souhaitée
	 */
	public void changeTaskCategorie(Task t, Categorie categorie){
		t.setCategorie(categorie);;
	}

	/**
	 * Changement de l'importance d'une tâche.
	 * @param t la tâche que l'on modifie
	 * @param importance l'importance souhaitée
	 */
	public void changeImportance(Task t, Importance importance){
		t.setImportance(importance);
	}

	/**
	 * Changement de la deadline d'une tâche.
	 * @param t la tâche que l'on modifie
	 * @param end la nouvelle date de fin
	 */
	public void changeTaskDate(Task t,Date end){
		t.setDeadline(end);
		t.updateIsLate();
	}

	/**
	 * Retourne la liste de tâches. 
	 * @return la liste de tâche.
	 */
	public Vector<Task> getListTask() {
		return listTask;
	}

	/**
	 * Retoune la liste de catégories.
	 * @return liste de catégorie.
	 */
	public Vector<Categorie> getListCategorie() {
		return listCategorie;
	}

	/**
	 * Retoune la liste de tâches triées pour le troisième tri.
	 * @return liste de tâches triées.
	 */
	public Vector<Task> getListTaskSort3() {
		return listTaskSort3;
	}

	/**
	 * Retoune la liste de tâches sauvegardées.
	 * @return liste de tâches sauvegardées.
	 */
	public Vector<Task> getSavTaskList() {
		return savTaskList;
	}

	/**
	 * Change la liste de tâches sauvegardées.
	 * @param savTaskList nouvelle liste.
	 */
	public void setSavTaskList(Vector<Task> savTaskList) {
		this.savTaskList = savTaskList;
	}

	/**
	 * Tris la liste de tâches en fonction de la deadline.
	 */
	public void sortTaskList(){
		Collections.sort(listTask);
	}

	/**
	 * Tris la liste de tâches en fonctions de la prochaines deadline partielle.
	 */
	public void sortTaskListPartialDeadLine(){
		Collections.sort(listTask, new CompareTaskDate());
	}

	/**
	 * 	Tri la liste par point (imortance) mais ne prend pas 1-Important 3-moyen  5-faible
	 */
	public void sortTaskListImportance(){
		Collections.sort(listTask, new CompareTaskImportance());
		Vector<Task> importante = new Vector<Task>();
		Vector<Task> moyen = new Vector<Task>();
		Vector<Task> faible = new Vector<Task>();
		listTaskSort3 = new Vector<Task>();

		for(Task t : listTask){
			switch(t.getImportance().ordinal()){
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
