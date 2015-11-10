package ToDoLIst;

public class Categorie {

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
		return "Categorie [catName=" + catName + "]";
	}
	
	
	
}
