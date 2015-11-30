package Model;

import java.util.Comparator;

public class CompareTaskDate implements Comparator<Task>{

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
