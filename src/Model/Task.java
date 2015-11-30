package Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressWarnings("serial")
public abstract class Task implements Comparable<Task>, Serializable{

	
	private Date begin;
	private Date deadline;
	private String name;
	private Categorie categorie;
	private String colorText;
	

	private boolean is_end = false;
	private boolean is_late = false;
	private int importance;// 0 : faible / 1 : moyen / 2 : importante

	public Task(Date deadline, String name, Categorie categorie, int importance) {
		super();
		this.begin = new Date(); 
		setDeadline(deadline);
		this.name = name;
		this.categorie = categorie;
		this.importance = importance;
	}
	
	public Task(Date deadline, String name, Categorie categorie, int importance, Date begin) {
		super();
		this.begin = begin; 
		setDeadline(deadline);
		this.name = name;
		this.categorie = categorie;
		this.importance = importance;
	}

	public boolean updateIsLate(){
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.setTime(new Date());
		if(calendar.getTime().after(deadline)){
			is_late = true;
			return true;
		}

		is_late = false;
		return false;
	}
	/**
	 * update is end
	 */
	public abstract void end();
	
	public Date getDeadline() {
		return deadline;
	}

	/**
	 * initialise la deadline a j+1 00h00
	 * @param deadline date de fin
	 */
	public void setDeadline(Date deadline) {
		Calendar date = new GregorianCalendar();
		date.setTime(deadline);
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);
		date.set(Calendar.MILLISECOND, 999);
		this.deadline = date.getTime();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public boolean getIs_end() {
		return is_end;
	}
	public void setIs_end(boolean is_end) {
		this.is_end = is_end;
	}

	public boolean getIs_late() {
		return is_late;
	}
	public void setIs_late(boolean is_late) {
		this.is_late = is_late;
	}

	public int getImportance() {
		return importance;
	}
	
	public void setImportance(int importance) {
		this.importance = importance;
	}

	public int compareTo(Task t) {
		if(this.deadline.before(t.deadline)){
			return -1;
		}else if(this.deadline.after(t.deadline)){
			return 1;
		}
		return 0;
	}

	public String toString() {
		if(updateIsLate()){
			return name+"<html><font color=red>   /!\\</font></html>";
		}
		return name;
	}

	public Date nextPartialDeadline(){
		return deadline;
	}
	
	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public String getColorText() {
		return colorText;
	}

	public void setColorText(String colorText) {
		this.colorText = colorText;
	}
	
	public abstract boolean isLongCourt();




}
