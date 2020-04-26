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
	public static String PATH = "./data/Comparendos_DEI_2018_Bogotá_D.C_small.geojson";
	public static String PATH2 = "./data/Comparendos_dei_2018_Bogotá_D.C.geojson";
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

					view.printMessage("Los datos contenidos en los archivos sólo se pueden leer una vez");
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

				view.printMessage("Ingresa un numero número para el mes (1-12)");
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
				view.printMessage("Por favor ingrese una localidad con el siguiente formato (MARTIRES)");
				String pLocalidad = lector.next();

				modelo.requerimiento3A(fecha1,fecha2, pLocalidad);
				break;
			case 5:
				view.printMessage("Ingresa un numero M de comparendos");

				int m;
				m = lector.nextInt();

				modelo.requerimiento1B(m);

				break;

			case 6:
				
				view.printMessage("Ingresa  medio de detección");
				String deteccion = lector.next();
				view.printMessage("Ingresa  clase de vehículo");
				String vehiculo = lector.next();
				view.printMessage("Ingresa  tipo de servicio");
				String servicio = lector.next();
				view.printMessage("Ingresa  tipo de localidad");
				String localidad = lector.next();
			

				modelo.requerimiento2b(deteccion, vehiculo, servicio,localidad);
				break;

			case 7:
				view.printMessage("Por favor ingrese la latitud menor");
				double latitud1 = Double.parseDouble( lector.next());
				view.printMessage("Por favor ingrese la latitud mayor)");
				double latitud2 = Double.parseDouble(lector.next());
				view.printMessage("Por favor ingrese un vehiculo");
				String vehiculop = lector.next();

				modelo.requerimiento3B(latitud1,latitud2, vehiculop);

				break;

			case 8:

				view.printMessage("Por favor ingrese un numero para el rango de fechas");
				int rango = lector.nextInt();
				modelo.requerimiento1C(rango);

				break;

			case 9:
				modelo.requerimiento2C();
				break;

			case 10:

				modelo.requerimiento3C();
				break;

			default: 

				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}

		}

	}	
}