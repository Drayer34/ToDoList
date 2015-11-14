package Model;

import java.util.Date;

public abstract class Task implements Comparable<Task>{

	private Date deadline;
	private String name;
	private Categorie categorie;
	private boolean is_end = false;
	private boolean is_late = false;

	public Task(Date date, String name, Categorie categorie) {
		super();

		this.deadline = date;
		this.name = name;
		this.categorie = categorie;
	}

	public boolean updateIsLate(){
		Date currentDate =  new Date();
		if(currentDate.after(deadline)){
			is_late = true;
			return true;
		}
		is_late = false;
		return false;
	}
	
	public abstract void isDone();
	
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
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

	public int compareTo(Task t) {
		return (int) (this.deadline.getTime() - t.deadline.getTime());
	};
	@Override
	public String toString() {
		return name;
	}
	
}
