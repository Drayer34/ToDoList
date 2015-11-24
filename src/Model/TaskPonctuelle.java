package Model;

import java.util.Date;

public class TaskPonctuelle extends Task{
	
	
	public TaskPonctuelle(Date deadline, String name, Categorie categorie,Importance importance) {
		super(deadline, name, categorie, importance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateEnd() {
		super.setIs_end(true);
	}
	
	public boolean isLongCourt(){
		return false;
	}
	public int percent(){
		if(super.getIs_end())
			return 100;
		return 0;
	}
}
