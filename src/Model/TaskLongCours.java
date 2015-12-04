package Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe pour implémenter une tâche au long cours
 * @author Antoine Laurent et Anthony Brunel
 *
 */
@SuppressWarnings("serial")
public class TaskLongCours extends Task implements Serializable{

	/**
	 * le pourcentage d'acomplissement de la tâche
	 */
	private int percent = 0;
	/**
	 * la couleur du texte
	 */
	private static String colorText = "\"#0040FF\"";

	/**
	 * Constructeur d'une tâche au long cours
	 * @param deadline la date de fin
	 * @param name le nom 
	 * @param categorie la catégorie
	 * @param importance l'importance
	 */
	public TaskLongCours(Date deadline, String name, Categorie categorie, int importance) {
		super(deadline, name, categorie, importance);
		super.setColorText(colorText);
	}

	/**
	 * Constructeur d'une tâche long cours
	 * @param deadline la date de fin
	 * @param name le nom
	 * @param categorie la catégorie
	 * @param importance l'importance
	 * @param begin la date de début
	 */
	public TaskLongCours(Date deadline, String name, Categorie categorie, int importance, Date begin) {
		super(deadline, name, categorie, importance, begin);
		super.setColorText(colorText);
	}

	/**
	 * Savoir si une tâche long cours est retard.
	 * @return boolean : vrai si la tâche est en retard, faux sinon.
	 */
	public boolean updateIsLate(){
		if(super.updateIsLate()){
			return true;
		}else{
			GregorianCalendar calendar = new java.util.GregorianCalendar(); 
			calendar.setTime(new Date());
			Date theorique =this.nextPartialDeadline();
			if (calendar.getTime().after(theorique)){
				super.setIs_late(true);
				return true;
			}
			else{
				super.setIs_late(false);
				return false;
			}
		}

	}

	/**
	 * Pour une tâche long cours : regarde si la tâche est finie via les pourcentage.
	 */
	public void end() {
		if(percent >= 100){
			super.setIs_end(true);
		}
	}

	/**
	 * 
	 * @return le pourcentage
	 */
	public int getPercent() {
		return percent;
	}

	/**
	 * 
	 * @param percent le pourcentage que l'on veut mettre
	 */
	public void setPercent(int percent) {
		this.percent = percent;
	}

	/**
	 * Renvoie la prochaine date partielle en fonction du pourcentage d'avancement
	 */
	public Date nextPartialDeadline(){
		GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.setTime(super.getBegin());
		long diff = super.getDeadline().getTime() - super.getBegin().getTime();
		long par4 = (((diff/4)/1000)/60)/60;

		if(percent <= 25){
			calendar.add(Calendar.HOUR, (int) par4);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			return calendar.getTime();
		}
		else if(percent <= 50){
			calendar.add(Calendar.HOUR, (int) par4*2);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			return calendar.getTime();
		}
		else if(percent <= 75){
			calendar.add(Calendar.HOUR, (int) par4*3);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			return calendar.getTime();
		}
		return super.getDeadline();
	}

	/**
	 * Savoir si la tâche est long cours
	 */
	public boolean isLongCourt(){
		return true;
	}

	/**
	 * methode toString
	 */
	public String toString(){
		return "<html><font color="+super.getColorText()+">"+super.toString()+"</font></html>";
	}
}
