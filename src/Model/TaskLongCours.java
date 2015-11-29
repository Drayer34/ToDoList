package Model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TaskLongCours extends Task{
	
	private Date begin;
	private int percent = 0;

	public TaskLongCours(Date deadline, String name, Categorie categorie, int importance) {
		super(deadline, name, categorie, importance);
		begin = new Date();
	}

	public TaskLongCours(Date deadline,Date begin, String name, Categorie categorie, int importance) {
		super(deadline, name, categorie, importance);
		this.begin = begin;
	}
	public boolean updateIsLate(){
		if(super.updateIsLate()){
			return true;
		}else{
			Date currentDate = new Date();
			long CONST_DURATION_OF_DAY = 1000l * 60 * 60 * 24;
			long total_diff = super.getDeadline().getTime() - begin.getTime();
			long remaining_diff = super.getDeadline().getTime() - currentDate.getTime();
			long nbTotalDay = (total_diff/CONST_DURATION_OF_DAY);
			long nbRemainingDay = (remaining_diff/CONST_DURATION_OF_DAY);

			System.out.println(nbRemainingDay*100/nbTotalDay);
			if(percent < nbRemainingDay*100/nbTotalDay){
				super.setIs_late(true);
				return true;
			}
		}
		super.setIs_late(false);
		return super.getIs_late();
	}

	public void end() {
		if(percent == 100){
			super.setIs_end(true);
		}
	}
	
	public Date getBegin() {
		return begin;
	}


	public void setBegin(Date begin) {
		this.begin = begin;
	}


	public int getPercent() {
		return percent;
	}


	public void setPercent(int percent) {
		this.percent = percent;
	}

	public Date nextPartialDeadline(){
		GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.setTime(begin);
		long diff = super.getDeadline().getTime() - begin.getTime();
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
