package Model;

import java.util.Comparator;

public class CompareTaskDate implements Comparator<Task>{

	@Override
	public int compare(Task t1, Task t2) {
		return (int) (t1.nextPartialDeadline().getTime() - t2.nextPartialDeadline().getTime()) ;
	}

}
