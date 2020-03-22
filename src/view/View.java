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
		System.out.println("2. Buscar comparendos por fecha, clase de vehículo e infracción (Tabla de Hash Linear Probing)");
		System.out.println("3. Buscar comparendos por fecha, clase de vehículo e infracción (Tabla de Hash Separate Chaining)");
		System.out.println("4. Pruebas de desempeño haciendo 10.000 consultas aleatorias (8.000 llaves existentes, 2.000 llaves inexistentes) Tablas de Hash Separate Chaining y Linear Probing");
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
