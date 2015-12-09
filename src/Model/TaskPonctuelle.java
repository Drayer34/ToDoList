package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe pour implémenter les tâches ponctuelles
 * @author Antoine Laurent et Anthony Brunel
 *
 */
@SuppressWarnings("serial")
public class TaskPonctuelle extends Task implements Serializable{
	
	/**
	 * La couleur du texte
	 */
	private static String colorText = "\"#04B404\"";
	
	/**
	 * Contructeur de tâche ponctuelle
	 * @param deadline la date de fin
	 * @param name le nom
	 * @param categorie la catégorie
	 * @param importance l'importance
	 */
	public TaskPonctuelle(Date deadline, String name, Categorie categorie,Importance importance) {
		super(deadline, name, categorie, importance);
		super.setColorText(colorText);
	}

	/**
	 * Pour une tache ponctuelle : arr�te la t�che.
	 */
	public void end(){
		super.setIs_end(true);
	}
	
	/**
	 * Savoir si c'est une tâche en long cours
	 */
	public boolean isLongCourt(){
		return false;
	}

	/**
	 * methode toString
	 */
	public String toString(){
		return "<html><font color="+super.getColorText()+">"+super.toString()+"</font></html>";
	}
}
