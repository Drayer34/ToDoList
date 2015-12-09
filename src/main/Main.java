package main;


import model.Manager;
import controler.Controler;

public class Main {
	public static void main(String args[])
	{
		Manager m;
		if((m = SerializationManager.restoreManager()) == null)
			m = new Manager();
		new Controler(m);
	}
}
