package Model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Categorie implements Serializable{

	private String catName;
	
	public Categorie(String catName) {
		super();
		this.catName = catName;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	@Override
	public String toString() {
		return catName;
	}
	
}
