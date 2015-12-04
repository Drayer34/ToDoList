package Model;

import java.io.Serializable;

/**
 * La classe pour représenter une catégorie
 * @author Antoine Laurent et Anthony Brunel
 * 
 */
@SuppressWarnings("serial")
public class Categorie implements Serializable{

	/**
	 * Le nom de la catégorie
	 */
	private String catName;
	
	/**
	 * Constructeur de catégorie
	 * @param catName nom de la catégorie
	 */
	public Categorie(String catName) {
		super();
		this.catName = catName;
	}

	/**
	 * 
	 * @return le nom de la catégorie 
	 */
	public String getCatName() {
		return catName;
	}

	/**
	 * 
	 * @param catName le nom de la catégorieque l'on veut mettre
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}

	
	/**
	 * Methode toString pour afficher une catégorie
	 */
	@Override
	public String toString() {
		return catName;
	}
	
}
