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
		long par4 = diff/4;
		
		if(percent<25){
			calendar.add(Calendar.MILLISECOND, (int) par4);
			return calendar.getTime();
		}
		else if(percent <50){
			calendar.add(Calendar.MILLISECOND, (int) par4*2);
			return calendar.getTime();
		}
		else if(percent <75){
			calendar.add(Calendar.MILLISECOND, (int) par4*3);
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
