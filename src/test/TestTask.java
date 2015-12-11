package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import model.Categorie;
import model.Importance;
import model.TaskLongCours;
import model.TaskPonctuelle;

@RunWith(Parameterized.class)
public class TestTask {

	TaskLongCours task1;
	TaskLongCours task2;
	TaskPonctuelle task3;
	TaskLongCours task4;
	private Date begin;
	private Date deadline;
	private String name;
	private Categorie categorie;
	private Importance importance;

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection data() {
		Calendar debut = new GregorianCalendar();
		debut.setTime(new Date());
		
		
		Calendar fin = new GregorianCalendar();
		fin.setTime(debut.getTime());
		fin.add(Calendar.DAY_OF_YEAR, 4);
		


		return Arrays.asList(new Object[][]{
				{debut.getTime(),fin.getTime(),"tache 1",new Categorie("Travail"),Importance.Faible},
				{debut.getTime(),debut.getTime(),"tache 3",new Categorie("Travail"),Importance.Importante},
		});
	}

	public TestTask(Date begin, Date deadline, String name,
			Categorie categorie, Importance importance) {
		super();
		this.begin = begin;
		GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.setTime(deadline);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		this.deadline = calendar.getTime();
		this.name = name;
		this.categorie = categorie;
		this.importance = importance;
	}

	@Before
	public void setUp() throws Exception {
		task1 = new TaskLongCours(deadline,name,categorie,importance);
		task2 = new TaskLongCours(begin,deadline,name,categorie,importance);
		task3 = new TaskPonctuelle(deadline, name, categorie, importance);
		task4 = new TaskLongCours(begin,deadline,name,categorie,importance);

	}

	@Test
	public void testIsLongCours() {
		assertThat(task1.isLongCourt(),is(true));
		assertThat(task2.isLongCourt(),is(true));
		assertThat(task3.isLongCourt(),is(false));
	}

	@Test
	public void testUpdateIsLate(){
		assertThat(task1.updateIsLate(),is(false));
		assertThat(task2.updateIsLate(),is(false));
		assertThat(task3.updateIsLate(),is(false));
	}

	@Test
	public void testNbJourRestant(){
		assertThat(task1.nbJourRestant(),is((deadline.getTime()-begin.getTime())/1000/60/60/24));
		assertThat(task2.nbJourRestant(),is((deadline.getTime()-begin.getTime())/1000/60/60/24));
		assertThat(task3.nbJourRestant(),is((deadline.getTime()-begin.getTime())/1000/60/60/24));
	}

	@Test
	/**
	 * Comme la tache 1 est une tache au long cours sans date de début c'est le constructeur qui l'initialise
	 * Donc on compare le temps sans les milliseconde.
	 */
	public void testNextPartialDeadline(){

		Calendar date = new GregorianCalendar();
		date.add(Calendar.DAY_OF_YEAR, -2);
		task4.setBegin(date.getTime());
		
		date.add(Calendar.DAY_OF_YEAR, 3);
		task4.setDeadline(date.getTime());
		
		assertThat(task1.updateIsLate(), is(false));
		assertThat(task2.updateIsLate(), is(false));
		assertThat(task4.updateIsLate(), is(true));

		task1.setPercent(26);
		task2.setPercent(26);
		task4.setPercent(26);

		assertThat(task1.updateIsLate(), is(false));
		assertThat(task2.updateIsLate(), is(false));
		assertThat(task4.updateIsLate(), is(true));

		task1.setPercent(51);
		task2.setPercent(51);
		task4.setPercent(51);

		assertThat(task1.updateIsLate(), is(false));
		assertThat(task2.updateIsLate(), is(false));
		assertThat(task4.updateIsLate(), is(false));

		task1.setPercent(76);
		task2.setPercent(76);
		task4.setPercent(76);

		assertThat(task1.updateIsLate(), is(false));
		assertThat(task2.updateIsLate(), is(false));
		assertThat(task4.updateIsLate(), is(false));

	}
	
	@Test
	public void testEnd(){
		task1.end();
		assertThat(task1.getIs_end(), is(false));
		assertThat(task3.getIs_end(), is(false));
		task1.setPercent(100);
		assertThat(task1.getIs_end(), is(true));
		task3.end();
		assertThat(task3.getIs_end(), is(true));
	}
	
	
}
