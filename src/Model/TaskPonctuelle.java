package Model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TaskPonctuelle extends Task implements Serializable{
	
	private static String colorText = "\"#04B404\"";
	
	public TaskPonctuelle(Date deadline, String name, Categorie categorie,int importance) {
		super(deadline, name, categorie, importance);
		super.setColorText(colorText);
	}

	/**
	 * Pour une tache ponctuelle : arrête la tâche.
	 */
	public void end(){
		super.setIs_end(true);
	}
	
	public boolean isLongCourt(){
		return false;
	}

	public String toString(){
		return "<html><font color="+super.getColorText()+">"+super.toString()+"</font></html>";
	}
}
