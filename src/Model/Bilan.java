package Model;

import java.util.Date;
import java.util.Vector;

/**
 * Classe pour gérer le bilan des tâches sur une période
 * @author Antoine Laurent et Anthony Brunel
 *
 */
public class Bilan {

	/**
	 * Le poucentage de taches finies dans la période.
	 */
	private double perctOk;
	
	/**
	 * Le pourcentage de tâches en retard sur la période
	 */
	private double perctLate;
	/**
	 * Le pourcentage de tâches en cours sur la période
	 */
	private double perctCurrent;
	/**
	 * La liste de taches bilan dans la période donnée.
	 */
	private Vector<Task> bilanPeriod;
	/**
	 * La liste de toutes les tâches sauvegardées de la ToDo List
	 */
	private Vector<Task> savTaskList;

	/**
	 * Constructeur bilan
	 * @param savTaskList la liste de tâches sauvegardées.
	 */
	public Bilan(Vector<Task> savTaskList) {
		super();
		this.perctOk = 0;
		this.perctLate = 0;
		this.perctCurrent = 0;
		this.bilanPeriod = new Vector<Task>();
		this.savTaskList = savTaskList;
	}

	/**
	 * Génère le bilan, celon la date de d�but et de fin.
	 * Créer le vecteur de tache et fait les pourcentages.
	 * @param debut date de début
	 * @param fin date de fin
	 */
	public void generateBilan (Date debut, Date fin){
		//Date today = new Date();
		if(debut == null && fin == null){
			System.out.println("Date non initialis�e");
			return;
		}

		bilanPeriod.clear();
		int cptEnd=0;
		int cptLate=0;
		int cptCurt=0;
		int total=0;

		for(Task t : savTaskList){
			if (t.getBegin().getTime() >= debut.getTime()){
				if (t.getDeadline().getTime()<=fin.getTime())
					if (!t.getIs_end()){
						bilanPeriod.add(t);
						cptCurt++;
					}
					else {
						cptEnd++;
					}
				if (t.updateIsLate()){
					cptLate++;
				}
			}
			total++;
		}
		System.out.println(savTaskList);
		System.out.println(total);
		if(total > 0){
			this.perctCurrent = (double)cptCurt/(double)total *100;
			this.perctLate = (double)cptLate/(double)total *100;
			this.perctOk = (double)cptEnd/(double)total *100;
		}


	}

	/**
	 * @return le perctOk
	 */
	public double getPerctOk() {
		return perctOk;
	}

	/**
	 * @param perctOk le perctOk que l'on veut mettre
	 */
	public void setPerctOk(double perctOk) {
		this.perctOk = perctOk;
	}

	/**
	 * @return le perctLate
	 */
	public double getPerctLate() {
		return perctLate;
	}

	/**
	 * @param perctLate le perctLate que l'on veut mettre
	 */
	public void setPerctLate(double perctLate) {
		this.perctLate = perctLate;
	}

	/**
	 * @return le perctCurrent
	 */
	public double getPerctCurrent() {
		return perctCurrent;
	}

	/**
	 * @param perctCurrent le perctCurrent que l'on veut mettre
	 */
	public void setPerctCurrent(double perctCurrent) {
		this.perctCurrent = perctCurrent;
	}

	/**
	 * @return le bilanPeriod
	 */
	public Vector<Task> getBilanPeriod() {
		return bilanPeriod;
	}

	/**
	 * @param bilanPeriod le bilanPeriod que l'on veut mettre
	 */
	public void setBilanPeriod(Vector<Task> bilanPeriod) {
		this.bilanPeriod = bilanPeriod;
	}

	/**
	 * @return le savTaskList
	 */
	public Vector<Task> getSavTaskList() {
		return savTaskList;
	}

	/**
	 * @param savTaskList le savTaskList que l'on veut mettre
	 */
	public void setSavTaskList(Vector<Task> savTaskList) {
		this.savTaskList = savTaskList;
	}

	

}
