package Model;

import java.util.Date;

public class TaskPonctuelle extends Task{
	
	
	public TaskPonctuelle(Date deadline, String name, Categorie categorie) {
		super(deadline, name, categorie);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void isDone() {
		super.setIs_end(true);
	}
	
}
