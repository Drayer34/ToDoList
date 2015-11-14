package Model;

import java.util.Date;


public class TaskLongCours extends Task{
	
	private Date begin;
	private int percent = 0;

	public TaskLongCours(Date deadline, String name, Categorie categorie) {
		super(deadline, name, categorie);
		begin = new Date();
		// TODO Auto-generated constructor stub
	}

	public TaskLongCours(Date deadline,Date begin, String name, Categorie categorie) {
		super(deadline, name, categorie);
		this.begin = begin;
		// TODO Auto-generated constructor stub
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



	@Override
	public void isDone() {
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

}
