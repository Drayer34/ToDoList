package Model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DisplayConsole {

	public static void main(String[] args) {
		Manager m = new Manager();
		Date date = new Date();
		// Initialisé à la date. 
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.add (Calendar.DATE, 6);
		date = calendar.getTime();
		m.addTask(date,"Tâche ponctuelle 1",m.getListCategorie().get(0), TaskType.TachePonctuelle);
		calendar.add (Calendar.DATE, 5);
		date = calendar.getTime();

		m.addTask(date,"Tâche ponctuelle 2",m.getListCategorie().get(0),TaskType.TachePonctuelle);
		calendar.add (Calendar.DATE, 5);
		date = calendar.getTime();

		m.addTask(date,"Tâche ponctuelle 3",m.getListCategorie().get(0), TaskType.TachePonctuelle);
		calendar.add (Calendar.DATE, -20);
		date = calendar.getTime();
		m.addTask(date,"Tâche ponctuelle 4",m.getListCategorie().get(0), TaskType.TachePonctuelle);
		
		System.out.println(m.getListTask().toString());
		m.sortTaskList();
		System.out.println(m.getListTask().toString());

		System.out.println(m.getListTask().get(0).getDeadline());
		System.out.println(m.getListTask().get(0).updateIsLate());
		System.out.println(m.getListTask().get(0).getIs_late());
		
		calendar = new GregorianCalendar(); 
		calendar.add (Calendar.DATE, 5);
		date = calendar.getTime();
		TaskLongCours t = new TaskLongCours(date, "Tache",m.getListCategorie().get(0));
		calendar.add (Calendar.DATE, -10);
		date = calendar.getTime();
		t.setBegin(date);
		System.out.println(t.toString());
		System.out.println(t.getDeadline());
		System.out.println(t.getBegin());
		System.out.println(t.updateIsLate());
		t.setPercent(50);
		System.out.println(t.updateIsLate());
	}

}
