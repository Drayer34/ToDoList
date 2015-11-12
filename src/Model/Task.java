package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Task {

	private Date deadline;
	private String name;
	private Categorie categorie;
	
	
	public Task(Date deadline, String name, Categorie categorie) {
		super();
		this.deadline = deadline;
		this.name = name;
		this.categorie = categorie;
	}

	public int isLate(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = this.deadline.DateToString();
		java.util.Date date = null;

		try{
			date = formatter.parse(dateString);
			System.out.println(date);
			System.out.println(formatter.format(date));
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		if (date != null){
			java.util.Date today = new java.util.Date();
			if(today.compareTo(date)<=0){
				return 0;
			}
			else {return 1;}
		}
		else {return -1;}
	}
	
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

	@Override
	public String toString() {
		return name;
	}
	
}
