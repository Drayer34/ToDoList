package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import model.Importance;
import model.Manager;
import model.Task;
import model.TaskLongCours;
import model.TaskPonctuelle;

import org.junit.Before;
import org.junit.Test;

public class TestManager {
	
	Manager manager;
	
	@Before
	public void setUp(){
		manager = new Manager();
	}
	
	@Test
	public void testAdd_RemoveTask(){
		Task t = new TaskLongCours(new Date(), "tache1", manager.getListCategorie().get(0), Importance.Faible);
		manager.addTask(t);
		assertThat(manager.getListTask().contains(t), is(true));
		manager.removeTask(t);
		assertThat(manager.getListTask().contains(t), is(false));

	}
	
	@Test
	public void testEndTask(){
		Task t = new TaskPonctuelle(new Date(), "tache1", manager.getListCategorie().get(0), Importance.Faible);
		manager.addTask(t);
		manager.endTask(t);
		assertThat(manager.getListTask().contains(t), is(false));
		assertThat(manager.getSavTaskList().contains(t), is(true));
	}
	
	@Test
	public void testSortTaskList(){
		GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		Task t1 = new TaskPonctuelle(new Date(), "tache1", manager.getListCategorie().get(0), Importance.Faible);
		Task t2 = new TaskPonctuelle(calendar.getTime(), "tache1", manager.getListCategorie().get(0), Importance.Faible);
		manager.addTask(t2);
		manager.addTask(t1);
		manager.sortTaskList();
		assertThat(manager.getListTask().get(0), is(t1));
		assertThat(manager.getListTask().get(1), is(t2));
	}
	
	@Test
	public void testSortTaskListPartialDeadLine(){
		GregorianCalendar debut = new java.util.GregorianCalendar();
		GregorianCalendar fin = new java.util.GregorianCalendar();
		debut.setTime(new Date());
		debut.add(Calendar.DAY_OF_MONTH, -5);
		fin.setTime(new Date());
		fin.add(Calendar.DAY_OF_MONTH, 2);

		Task t1 = new TaskLongCours(debut.getTime(),fin.getTime(), "tache1", manager.getListCategorie().get(0), Importance.Faible);
		Task t2 = new TaskPonctuelle(new Date(), "tache1", manager.getListCategorie().get(0), Importance.Faible);
		manager.addTask(t2);
		manager.addTask(t1);
		manager.sortTaskList();
		assertThat(manager.getListTask().get(0), is(t2));
		assertThat(manager.getListTask().get(1), is(t1));
		manager.sortTaskListPartialDeadLine();
		assertThat(manager.getListTask().get(0), is(t1));
		assertThat(manager.getListTask().get(1), is(t2));
	}
	
	
	@Test
	public void testSortTaskListImportance(){
		Task t1 = new TaskPonctuelle(new Date(), "tache1", manager.getListCategorie().get(0), Importance.Importante);
		Task t2 = new TaskPonctuelle(new Date(), "tache1", manager.getListCategorie().get(0), Importance.Importante);
		Task t3 = new TaskPonctuelle(new Date(), "tache1", manager.getListCategorie().get(0), Importance.Moyenne);
		Task t4 = new TaskPonctuelle(new Date(), "tache1", manager.getListCategorie().get(0), Importance.Faible);
		manager.addTask(t4);
		manager.addTask(t1);
		manager.addTask(t2);
		manager.addTask(t3);
		manager.sortTaskListImportance();
		assertThat(manager.getListTaskSort3().get(0).getImportance(),is(Importance.Importante));
		assertThat(manager.getListTaskSort3().get(1).getImportance(),is(Importance.Moyenne));
		assertThat(manager.getListTaskSort3().get(2).getImportance(),is(Importance.Faible));
	}
}
