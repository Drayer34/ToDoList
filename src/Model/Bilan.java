package Model;

import java.util.Date;
import java.util.Vector;


public class Bilan {

	private double perctOk;
	private double perctLate;
	private double perctCurrent;
	private Vector<Task> bilanPeriod;
	private Vector<Task> savTaskList;

	public Bilan(Vector<Task> savTaskList) {
		super();
		this.perctOk = 0;
		this.perctLate = 0;
		this.perctCurrent = 0;
		this.bilanPeriod = new Vector<Task>();
		this.savTaskList = savTaskList;
	}

	public void generateBilan (Date debut, Date fin){
		//Date today = new Date();
		if(debut == null && fin == null){
			System.out.println("Date non initialisé");
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
		if(total > 0){
			this.perctCurrent = (double)cptCurt/(double)total *100;
			this.perctLate = (double)cptLate/(double)total *100;
			this.perctOk = (double)cptEnd/(double)total *100;
		}


	}

	public Vector<Task> getBilanPeriod() {
		return bilanPeriod;
	}

	public void setBilanPeriod(Vector<Task> bilanPeriod) {
		this.bilanPeriod = bilanPeriod;
	}

	public double getPerctOk() {
		return perctOk;
	}

	public void setPerctOk(double perctOk) {
		this.perctOk = perctOk;
	}

	public double getPerctLate() {
		return perctLate;
	}

	public void setPerctLate(double perctLate) {
		this.perctLate = perctLate;
	}

	public double getPerctCurrent() {
		return perctCurrent;
	}

	public void setPerctCurrent(double perctCurrent) {
		this.perctCurrent = perctCurrent;
	}


	public Vector<Task> getSavTaskList() {
		return savTaskList;
	}

	public void setSavTaskList(Vector<Task> savTaskList) {
		this.savTaskList = savTaskList;
	}

}
