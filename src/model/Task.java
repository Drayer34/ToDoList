package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe pour implémenter une tâche
 * @author Antoine Laurent et Anthony Brunel
 *
 */
@SuppressWarnings("serial")
public abstract class Task implements Comparable<Task>, Serializable{

	/**
	 * Date de début
	 */
	private Date begin;
	/**
	 * date de fin
	 */
	private Date deadline;
	/**
	 * nom
	 */
	private String name;
	/**
	 * catégorie
	 */
	private Categorie categorie;
	/**
	 * couleur du texte
	 */
	private String colorText;
	/**
	 * boolean : la tache est finie?
	 */
	private boolean is_end = false;
	/**
	 * boolean : la tâche est en retard?
	 */
	private boolean is_late = false;
	/**
	 * l'importance
	 */
	private Importance importance;// 0 : faible / 1 : moyen / 2 : importante

	/**
	 * constructeur de tâche
	 * @param deadline la date de fin
	 * @param name le nom 
	 * @param categorie la catégorie 
	 * @param importance l'importance
	 */
	public Task(Date deadline, String name, Categorie categorie, Importance importance) {
		super();
		this.begin = new Date(); 
		setDeadline(deadline);
		if(getDeadline().before(getBegin())){
			throw new IllegalArgumentException("Date de fin strictement supperieur à la date de début");
		}
		this.name = name;
		this.categorie = categorie;
		this.importance = importance;
	}

	/**
	 * constructeur de tâche
	 * @param deadline la date de fin
	 * @param name le nom
	 * @param categorie la catégorie
	 * @param importance l'importance
	 * @param begin le début
	 */
	public Task(Date begin,Date deadline, String name, Categorie categorie, Importance importance) {
		super();
		this.begin = begin; 
		setDeadline(deadline);
		if(getDeadline().before(getBegin())){
			throw new IllegalArgumentException("Date de fin > Date de début");
		}

		this.name = name;
		this.categorie = categorie;
		this.importance = importance;
	}

	/**
	 * Savoir si une tâche est retard.
	 * @return boolean : vrai si la tâche est en retard, faux sinon.
	 */
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
	 * Pour une tache ponctuelle : arrête la tâche, pour une tâche long cours : regarde si la tâche est finie via les pourcentage.
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
	
	/**
	 * 
	 * @return Nombre de jours restants avant la fin de la tâche.
	 */
	public long nbJourRestant(){
		if(getIs_end())
			return 0;
		Calendar date = new GregorianCalendar();
		Date today = new Date();
		date.setTime(today);
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);
		date.set(Calendar.MILLISECOND, 999);
		
		long diff = deadline.getTime() - date.getTime().getTime(); //nombre de jour restant en milliseconde
		return ((((diff/1000)/60)/60)/24);
	}

	/**
	 * 
	 * @return le nom
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name le nom que l'on veut mettre
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return la catégorie
	 */
	public Categorie getCategorie() {
		return categorie;
	}

	/**
	 * 
	 * @param categorie la catégorie que l'on veut mettre
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	/**
	 * 
	 * @return is_end 
	 */
	public boolean getIs_end() {
		return is_end;
	}
	
	/**
	 * 
	 * @param is_end le boolean que l'on veut mettre
	 */
	public void setIs_end(boolean is_end) {
		this.is_end = is_end;
	}

	/**
	 * 
	 * @return is_late
	 */
	public boolean getIs_late() {
		return is_late;
	}
	
	/**
	 * 
	 * @param is_late le boolean que l'on veut mettre
	 */
	public void setIs_late(boolean is_late) {
		this.is_late = is_late;
	}

	/**
	 * 
	 * @return l'importance
	 */
	public Importance getImportance() {
		return importance;
	}

	/**
	 * 
	 * @param importance l'importance que l'on veut mettre
	 */
	public void setImportance(Importance importance) {
		this.importance = importance;
	}

	/**
	 * pour comparer deux taches celon la date de fin
	 */
	public int compareTo(Task t) {
		if(this.deadline.before(t.deadline)){
			return -1;
		}else if(this.deadline.after(t.deadline)){
			return 1;
		}
		return 0;
	}

	/**
	 * methode toString
	 */
	public String toString() {
		if(updateIsLate()){
			return name + " <html><font color=red>->  " + this.nbJourRestant() + " jrs   /!\\</font></html>";
		}
		return name + " <html><font color=black>-> " + this.nbJourRestant() + " jrs </font></html>";
	}

	/**
	 * 
	 * @return la porchaine date de fin partielle
	 */
	public Date nextPartialDeadline(){
		return deadline;
	}

	/**
	 * 
	 * @return la date de début
	 */
	public Date getBegin() {
		return begin;
	}

	/**
	 * 
	 * @param begin la date de début que l'on veut mettre
	 */
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	
	/**
	 * 
	 * @return la couleur du texte
	 */
	public String getColorText() {
		return colorText;
	}

	/**
	 * 
	 * @param colorText la couleur de texte que l'on veut mettre
	 */
	public void setColorText(String colorText) {
		this.colorText = colorText;
	}

	/**
	 * méthode abstraite pour savoir dans quel type de tâche on se trouve
	 * @return boolean
	 */
	public abstract boolean isLongCourt();




}
