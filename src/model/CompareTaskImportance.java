package model;

import java.util.Comparator;
/**
 * Classe pour implémenter le tri 3
 * @author Antoine Laurent et Anthony Brunel
 *
 */
public class CompareTaskImportance implements Comparator<Task>{

	@Override
	/**
	 * La méthode de comparaison
	 */
	public int compare(Task o1, Task o2) {
		
		return o1.getImportance().ordinal() - o2.getImportance().ordinal();
	}
}
