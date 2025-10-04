package Presentation;

public class Program {
	public static void main(String[] args) {
		try {
			Menu menu = new Menu();
			menu.proccessMenu();
		} catch (Exception e) {
			System.out.println("Application start error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
