package controller;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import model.data_structures.ArregloDinamico;
import model.data_structures.Comparendo;
import model.data_structures.IArregloDinamico;
import model.data_structures.ICola;
import model.data_structures.IPila;
import model.data_structures.LinearProbing;
import model.data_structures.MaxHeapCP;
import model.data_structures.Queue;
import model.data_structures.SeparateChaining;
import model.logic.GeoJSONProcessing;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	private boolean cargado;
	public static String PATH = "./data/comparendos_dei_2018_small.geojson";
	public static String PATH2 = "./data/Comparendos_dei_2018_Bogot�_D.C.geojson";
	public static String PATH3 = "./data/comparendos_mediano.geojson";
	private Comparable<Comparendo>[] aOrdenar;
	private Comparable<Comparendo>[] copiaPrimera;
	SeparateChaining<String, Comparendo> comps;
	LinearProbing<String, Comparendo> comps2;
	MaxHeapCP<Comparendo> comps3;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
		cargado = false;
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;


		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:

				if(cargado == false){
					modelo = new Modelo();

					long start = System.currentTimeMillis();
					modelo.cargar(PATH);	
					long end = System.currentTimeMillis();
					view.printMessage("Tiempo de carga (s): " + (end-start)/1000.0);

					comps = modelo.darSeparateChaining();	

					comps2 = modelo.darLinearProbing();

					comps3 = modelo.darMaxHeapCP();

					modelo.darObjetoJsonGson().retornarTotalComps();

					modelo.darObjetoJsonGson().retornarCompObjectIDMayor();

					cargado = true;

				}

				else{

					view.printMessage("Los datos contenidos en los archivos s�lo se pueden leer una vez");
				}

				view.printMessage("");

				break;

			case 2:

				view.printMessage("Ingresa un numero M de comparendos");

				int M;
				M = lector.nextInt();

				modelo.requerimiento1A(M);

				break;

			case 3:

				view.printMessage("Ingresa un numero n�mero para el mes (1-12)");
				int mes = lector.nextInt();
				view.printMessage("Ingresa un dia de la semana (L, M, I, J, V, S, D)");
				String dia = lector.next().trim();

				modelo.requerimiento2A(mes, dia);

				break;

			case 4:

				view.printMessage("Por favor ingrese una fecha inicial con el siguiente formato (YYYY/MM/DD-HH:MM:ss)");
				String fecha1 = lector.next();
				view.printMessage("Por favor ingrese una fecha final con el siguiente formato (YYYY/MM/DD-HH:MM:ss)");
				String fecha2 = lector.next();

				modelo.requerimiento3A(fecha1,fecha2);
				break;

			default: 

				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}

		}

	}	
}