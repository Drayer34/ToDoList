package MainTask;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Model.Manager;

public class SerializationManager {

	public static final String path = "sauvegarde.todo";
	public static void saveManager(Manager manager){

		try {
			FileOutputStream destination = null;
			ObjectOutputStream oos = null;

			destination = new FileOutputStream(path);
			oos = new ObjectOutputStream(destination);


			try {
				oos.writeObject(manager);
				oos.flush();
			} finally {
				try {
					oos.close();
				} finally {
					destination.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}
	
	public static Manager restoreManager(){
		Manager manager = null;
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois= new ObjectInputStream(fis);
			try {	
				manager = (Manager) ois.readObject();
			} finally {
				try {
					ois.close();
				} finally {
					fis.close();
				}
			}
		} catch(IOException ioe){
			System.out.println("Aucune sauvegarde.");
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return manager;
	}
}
