package MainTask;

import javax.swing.JFrame;

import controler.Controler;
import Model.Manager;
import View.DisplayManager;

public class Main {
	public static void main(String args[])
	{ 
		Manager m = new Manager();
		Controler c = new Controler(m);
	}
}
