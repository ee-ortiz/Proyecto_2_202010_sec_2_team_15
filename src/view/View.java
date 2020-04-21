package view;

import model.logic.Modelo;

public class View 
{
	/**
	 * Metodo constructor
	 */
	public View()
	{

	}

	public void printMenu()
	{
		System.out.println("1. Cargar Datos");
		System.out.println("2. Requerimiento 1A");
		System.out.println("3. Requerimiento 2A");
		System.out.println("4. Requerimiento 3A");
		System.out.println("5. Requerimiento 1B");
		System.out.println("6. Requerimiento 2B");
		System.out.println("7. Requerimiento 3B");
		System.out.println("8. Requerimiento 1C");
		System.out.println("9. Requerimiento 2C");
		System.out.println("10. Requerimiento 3C");
		
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return:");

	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}		

	public void printModelo(Modelo modelo)
	{
		// TODO implementar
		System.out.println(modelo);
	}
}
