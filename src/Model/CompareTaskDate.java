package Model;

import java.util.Comparator;

/**
 * Classe pour implémenter le tris 2
 * @author Antoine Laurent et Anthony Brunel
 *
 */
public class CompareTaskDate implements Comparator<Task>{

	/**
	 * La méthode de comparaison
	 */
	@Override
	public int compare(Task t1, Task t2) {
		if(t1.nextPartialDeadline().before(t2.nextPartialDeadline())){
			return -1;
		}else if(t1.nextPartialDeadline().after(t2.nextPartialDeadline())){
			return 1;
		}
		return 0;
	}
}
