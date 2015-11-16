package MainTask;


import controler.Controler;
import Model.Manager;

public class Main {
	public static void main(String args[])
	{ 
		Manager m = new Manager();
		new Controler(m);
	}
}
