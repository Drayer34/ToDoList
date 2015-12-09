package test;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import model.Bilan;
import model.Categorie;
import model.Importance;
import model.Task;
import model.TaskPonctuelle;

public class TestBilan {


	Bilan bilan;

	
	@Before
	public void setUp(){
		GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Task t1 = new TaskPonctuelle(new Date(), "tache1", new Categorie("Travail"), Importance.Faible);
		t1.end();
		Task t2 = new TaskPonctuelle(calendar.getTime(), "tache1", new Categorie("Travail") , Importance.Faible);
		calendar.add(Calendar.DAY_OF_MONTH, 55);
		
		Task t3 = new TaskPonctuelle(calendar.getTime(), "tache1", new Categorie("Travail") , Importance.Faible);

		Vector<Task> savTaskList = new Vector<Task>();
		savTaskList.add(t1);
		savTaskList.add(t2);
		savTaskList.add(t3);
		
		bilan = new Bilan(savTaskList);
	}
	
	@Test
	public void testBilan () {
		GregorianCalendar debut = new java.util.GregorianCalendar();
		GregorianCalendar fin = new java.util.GregorianCalendar();
		debut.setTime(new Date());
		fin.setTime(new Date());
		debut.add(Calendar.DAY_OF_MONTH, -1);
		fin.add(Calendar.DAY_OF_MONTH, 5);
		bilan.generateBilan(debut.getTime(), fin.getTime());
		
		assertThat(bilan.getPerctCurrent(),is((double) 50));
		assertThat(bilan.getPerctOk(),is((double) 50));
		assertThat(bilan.getPerctLate(),is((double) 0));

	}

}
