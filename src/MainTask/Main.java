package MainTask;


import controler.Controler;
import Model.Manager;

public class Main {
	public static void main(String args[])
	{ 
		Manager m = new Manager();
		Controler c = new Controler(m);
	}
}
