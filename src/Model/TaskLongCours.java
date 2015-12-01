package Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@SuppressWarnings("serial")
public class TaskLongCours extends Task implements Serializable{

	private int percent = 0;
	private static String colorText = "\"#0040FF\"";

	public TaskLongCours(Date deadline, String name, Categorie categorie, int importance) {
		super(deadline, name, categorie, importance);
		super.setColorText(colorText);
	}

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
			System.out.println(this.nextPartialDeadline());
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
		if(percent == 100){
			super.setIs_end(true);
		}
	}

	public int getPercent() {
		return percent;
	}


	public void setPercent(int percent) {
		this.percent = percent;
	}

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

	public boolean isLongCourt(){
		return true;
	}


	public String toString(){
		return "<html><font color="+super.getColorText()+">"+super.toString()+"</font></html>";
	}
}
