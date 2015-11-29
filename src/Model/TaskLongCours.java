package Model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TaskLongCours extends Task{

	private int percent = 0;

	public TaskLongCours(Date deadline, String name, Categorie categorie, int importance) {
		super(deadline, name, categorie, importance);
	}

	public TaskLongCours(Date deadline, String name, Categorie categorie, int importance, Date begin) {
		super(deadline, name, categorie, importance, begin);
	}

	public boolean updateIsLate(){
		if(super.updateIsLate()){
			return true;
		}else{
			Date today = new Date();
			Date theorique =this.nextPartialDeadline();
			if ( theorique.getTime() < today.getTime()){
				//System.out.println(true);
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
		calendar.setTime(getBegin());
		long diff = super.getDeadline().getTime() - getBegin().getTime();
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

}
