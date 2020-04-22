package model.logic;

import model.data_structures.ArregloDinamico;
import model.data_structures.Comparendo;
import model.data_structures.Comparendo.ComparadorRequerimiento3C;
import model.data_structures.Comparendo.ComparadorXFecha;
import model.data_structures.Comparendo.ComparadorXGravedad;
import model.data_structures.IArregloDinamico;
import model.data_structures.LinearProbing;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
import model.data_structures.Penalizaciones;
import model.data_structures.Queue;
import model.data_structures.RedBlackBST;
import model.data_structures.SeparateChaining;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private SeparateChaining<String, Comparendo> comps;
	private LinearProbing<String, Comparendo> comps2;
	private MaxHeapCP<Comparendo> comps3;
	private RedBlackBST<Date, Comparendo> comps4;
	private RedBlackBST<Calendar, Comparendo> comps5;
	private Queue<Comparendo> comparendos;
	private GeoJSONProcessing objetoJsonGson;
	private int numeroMInicialSP;
	private int numeroMInicialLP;
	public final static int maximoNumeroDatosImpreso = 20;
	public final static int valorAsterisco = 500;
	public final static int valorAsterisco2 = 300;


	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{
		comps = new SeparateChaining<>(3011);
		comps2 = new LinearProbing<>(20011);
		comps3 = new MaxHeapCP<>(300000);
		comps4 = new RedBlackBST<>();
		comps5 = new RedBlackBST<>();
		comparendos = new Queue<>();
		numeroMInicialSP = comps.TamañoDeLaHastTable();
		numeroMInicialLP = comps2.darTamanoHashTable();
		objetoJsonGson = new GeoJSONProcessing();
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return comps.size();
	}


	public void shellSort(Comparable datos[]){

		int tamano = datos.length;
		int h = 1;
		while (h < tamano/3){

			h = 3*h + 1; // 1, 4, 13, 40, 121, 364, ...

		}
		while (h >= 1)
		{ // h-sort the array.
			for (int i = h; i < tamano; i++)
			{
				for (int j = i; j >= h && less(datos[j], datos[j-h]); j -= h){

					exch(datos, j, j-h);
				}

			}
			h = h/3;
		}

	}

	/* This function takes last element as pivot, 
    places the pivot element at its correct 
    position in sorted array, and places all 
    smaller (smaller than pivot) to left of 
    pivot and all greater elements to right 
    of pivot */

	// solucion adaptada de: https://www.geeksforgeeks.org/quick-sort/
	public static int partition(Comparable datos[], int low, int high) 
	{ 
		Comparable pivote = datos[high];  
		int i = (low-1); // index of smaller element 
		for (int j=low; j<high; j++) 
		{ 
			// Si el elemento actual es menor que el pivote
			if (less(datos[j], pivote)) 
			{ 
				i++; 
				// swap datos[i] and datos[j]  
				exch(datos, i, j);
			} 
		} 

		// swap datos[i+1] and datos[high] (o pivote) 
		exch(datos, i+1, high);

		return i+1; 
	} 

	//relacionado al quicksort, de aqui inicia.
	public static void sort(Comparable[] a)
	{
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
	} 
	/* The main function that implements QuickSort() 
   datos[] --> Array to be sorted, 
   low  --> Starting index, 
   high  --> Ending index */

	// solucion adaptada de: https://www.geeksforgeeks.org/quick-sort/
	public static void sort(Comparable datos[], int low, int high) 
	{ 
		if (low < high) 
		{ 
			/* pi is partitioning index, arr[pi] is  
           now at right place */
			int pi = partition(datos, low, high); 

			// Recursively sort elements before 
			// partition and after partition 
			sort(datos, low, pi-1); 
			sort(datos, pi+1, high); 
		} 
	} 

	public static void exch(Comparable[] a, int i, int j ){

		Comparable temporal = a[i];
		a[i] = a[j];
		a[j] = temporal;
	}

	public static boolean less(Comparable a, Comparable b){

		if(a.compareTo(b)<0){
			return true;
		}
		else{
			return false; //mayor o igual a cero
		}
	}
	/*
	 * paso 2 algoritmo mergeSort
	 */
	private static void sortParaMergeSort(Comparable[] a, Comparable[] aux, int lo, int hi, String orden, Comparator<Comparendo> comparador)
	{
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		sortParaMergeSort(a, aux, lo, mid, orden, comparador);
		sortParaMergeSort(a, aux, mid+1, hi, orden, comparador);
		merge(a, aux, lo, mid, hi, orden, comparador);
	}

	/*
	 *  aqui inicia el algoritmo mergeSort sacado del libro algorithms 4 edicion
	 */
	public static void sortParaMerge(Comparable[] a, String orden, Comparator<Comparendo> comparador)
	{
		Comparable[] aux = new Comparable[a.length];
		sortParaMergeSort(a, aux, 0, a.length - 1, orden, comparador);
	}



	/*
	 * ultimo paso
	 */
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi, String orden, Comparator<Comparendo> comparador )
	{
		for (int k = lo; k <= hi; k++)
			aux[k] = a[k];
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++)
		{
			if(comparador==null){

				if(orden.equalsIgnoreCase("descendente")){
					if (i > mid) a[k] = aux[j++];
					else if (j > hi) a[k] = aux[i++];
					else if (less(aux[j], aux[i])) //si izquierda menor true
					{ a[k] = aux[j++];}
					else a[k] = aux[i++];
				}

				if(orden.equalsIgnoreCase("ascendente")){
					if (i > mid) a[k] = aux[j++];
					else if (j > hi) a[k] = aux[i++];
					else if (!less(aux[j], aux[i])) //si izquierda no es menor true
					{ a[k] = aux[j++];}
					else a[k] = aux[i++];
				}
			}

			else{

				if(orden.equalsIgnoreCase("descendente")){
					if (i > mid) a[k] = aux[j++];
					else if (j > hi) a[k] = aux[i++];
					else if (comparador.compare(cambiarDeComparableAComparendo(aux[j]) , cambiarDeComparableAComparendo(aux[i]) )<0) //si izquierda menor true
					{ a[k] = aux[j++];}
					else a[k] = aux[i++];
				}

				if(orden.equalsIgnoreCase("ascendente")){
					if (i > mid) a[k] = aux[j++];
					else if (j > hi) a[k] = aux[i++];
					else if (comparador.compare(cambiarDeComparableAComparendo(aux[j]) , cambiarDeComparableAComparendo(aux[i]) )>=0) //si izquierda no es menor true
					{ a[k] = aux[j++];}
					else a[k] = aux[i++];
				}

			}

		}
	}
	/**
	 * Requerimiento buscar dato
	 * @param dato Dato a buscar
	 * @return dato encontrado
	 */
	public ArrayList<Comparendo> getSet(String dato)
	{
		ArrayList<Comparendo> rta = comps.getSetArray(dato);

		return rta;
	}

	/**
	 * Requerimiento eliminar dato
	 * @param dato Dato a eliminar
	 * @return dato eliminado
	 */
	public String eliminar(String dato)
	{
		return null;
	}


	public void cargar(String direccion){

		objetoJsonGson.cargarDatos(comparendos, direccion);

	}


	public String RetornarDatos(Comparendo comp){
		//INFRACCION, OBJECTID,
		//FECHA_HORA, CLASE_VEHI, TIPO_SERVI, LOCALIDAD.
		String rta = "Codigo de infraccion: "+comp.INFRACCION +" ObjectID: " + comp.OBJECTID + " Fecha y hora: " + comp.FECHA_HORA + " Clase de vehiculo "+comp.CLASE_VEHI + " Tipo de servicio: " +
				comp.TIPO_SERVI + " Localidad: "+ comp.LOCALIDAD;
		return rta;
	}






	public SeparateChaining<String, Comparendo> darSeparateChaining(){

		return comps;
	}
	/**
	 * retorna el lineal probing
	 * @return
	 */
	public LinearProbing<String, Comparendo> darLinearProbing(){

		return comps2;
	}

	public MaxHeapCP<Comparendo> darMaxHeapCP(){

		return comps3;
	}

	public GeoJSONProcessing darObjetoJsonGson(){

		return objetoJsonGson;
	}

	public int darNumeroMInicialSP(){

		return numeroMInicialSP;
	}

	public int darNumeroMInicialLP(){

		return numeroMInicialLP;
	}

	public static Comparendo cambiarDeComparableAComparendo(Comparable a){

		Comparendo rta = (Comparendo) a;
		return rta;
	}

	public Comparable[] copiarArreglo(IArregloDinamico<Comparendo> pComps){

		Comparable[] rta = new Comparable[pComps.darTamano()];
		int i = 0;
		while(i < pComps.darTamano()){
			rta[i] = pComps.darElemento(i);
			i++;
		}

		return rta;

	}

	public IArregloDinamico<Comparendo> retornarArregloDeComparendos(Comparable[] a){

		IArregloDinamico<Comparendo> rta = new ArregloDinamico<>(100);

		for(int i =0; i<a.length; i++){

			Comparable actual = a[i];
			Comparendo aAgregar = cambiarDeComparableAComparendo(actual);
			rta.agregar(aAgregar);
		}

		return rta;

	}

	// M comparendos con mayor gravedad (Codigo de infraccion)
	public void requerimiento1A(int M){

		ComparadorXGravedad comparar = new ComparadorXGravedad();

		Iterator<Comparendo> iter = comparendos.iterator();

		while(iter.hasNext()){

			Comparendo actual = iter.next();
			comps3.insert(actual, comparar);
		}

		Iterator<Comparendo> iter2 = comps3.iterator(comparar);

		int conteo = 0;
		System.out.println("Los primeros " + M + " comparendos con mayor gravedad son: ");

		while(iter2.hasNext() && conteo <M ){

			Comparendo actual = iter2.next();
			System.out.println("-" + actual.retornarDatosRequerimiento1A());
			conteo++;
		}

		// al final deja vaica la cola para ultilizarla en otro requerimiento
		comps3 = new MaxHeapCP<>(300000);

	}
	// El usuario ingresa el número del mes (1-12) y el día de la semana (L, M, I, J, V, S, D).
	public void requerimiento2A(int mes, String dia){

		int diaNumero;

		if(dia.equals("L")) diaNumero = 2;
		else if(dia.equals("M")) diaNumero = 3;
		else if(dia.equals("I")) diaNumero = 4;
		else if(dia.equals("J")) diaNumero = 5;
		else if(dia.equals("V")) diaNumero = 6;
		else if(dia.equals("S")) diaNumero = 7;
		else if(dia.equals("D")) diaNumero = 1;
		else{ System.out.println("Día invalido"); return; }

		Iterator<Comparendo> iter = comparendos.iterator();

		Calendar fecha = Calendar.getInstance();

		while(iter.hasNext()){

			Comparendo actual = iter.next();
			fecha.setTime(actual.FECHA_HORA);
			int mesKey = fecha.get(Calendar.MONTH) + 1;
			int diaKey = fecha.get(Calendar.DAY_OF_WEEK);
			String KeyMes = Integer.toString(mesKey);
			String KeyDia = Integer.toString(diaKey);

			comps2.put(KeyMes+KeyDia, actual);

		}

		String mesBuscar = Integer.toString(mes);
		String diaBuscar = Integer.toString(diaNumero);

		Iterator<Comparendo> iter2 = comps2.getSet(mesBuscar+diaBuscar);
		int numeroComparendos;
		try{

			numeroComparendos = comps2.get(mesBuscar+diaBuscar).size();
			System.out.println("Fueron encontrados " + numeroComparendos +" comparendos que cumplian con el criterio de busqueda.");
			if(numeroComparendos<=maximoNumeroDatosImpreso) System.out.println("Se muestran los primeros " + numeroComparendos + ":");
			else System.out.println("Se muestran los primeros " + maximoNumeroDatosImpreso + ":");

			int conteo = 0;
			while(iter2.hasNext() && conteo< maximoNumeroDatosImpreso ){

				Comparendo actual = iter2.next();
				System.out.println("-" + actual.retornarDatosRequerimiento1A());
				conteo ++;
			}

		}
		catch(Exception e){

			System.out.println("No se encontró nungun comparendo con los criterios de busqueda");	
		}
		System.out.println();
		// vacio el linear probing para usarlo en otro requerimiento
		comps2 = new LinearProbing<>(20011);
	}

	//3A- Buscar los comparendos que tienen una fecha-hora en un rango y que son de una localidad dada formato:(YYYY/MM/DD-HH:MM:ss)
	public void requerimiento3A(String f1, String f2, String pLocalidad){

		SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //año-mes-dia hora:minuto:segundo

		f1 = f1.trim().replaceAll("-", " ");
		f1 = f1.trim().replaceAll("/", "-");

		f2 = f2.trim().replaceAll("-", " ");
		f2 = f2.trim().replaceAll("/", "-");

		try{
			Date fecha1 = parser.parse(f1);
			Date fecha2 = parser.parse(f2);

			Iterator<Comparendo> iter = comparendos.iterator();

			while(iter.hasNext()){

				Comparendo actual = iter.next();
				comps4.put(actual.FECHA_HORA, actual);

			}

			try{

				Iterator<ArrayList<Comparendo>> iter2 = comps4.valuesInRange(fecha1, fecha2);
				ArrayList<Comparendo> paraMostar = new ArrayList<Comparendo>();

				while(iter2.hasNext()){

					ArrayList<Comparendo> actual = iter2.next();

					Iterator<Comparendo> compsIter = actual.iterator();

					while(compsIter.hasNext()){

						Comparendo CompAct = compsIter.next();
						if(CompAct.LOCALIDAD.equalsIgnoreCase(pLocalidad)) paraMostar.add(CompAct);
					}



				}

				if(paraMostar.size() ==0) System.out.println("No se encontraron comparendos en el rango de fechas dado");
				else if(paraMostar.size()<=maximoNumeroDatosImpreso){
					System.out.println("Fueron encontrados " + paraMostar.size() +" comparendos que cumplian con el criterio de busqueda.");
					System.out.println("Se muestran los primeros " + paraMostar.size() + ":");

				}
				else{
					System.out.println("Fueron encontrados " + paraMostar.size() +" comparendos que cumplian con el criterio de busqueda.");
					System.out.println("Se muestran los primeros " + maximoNumeroDatosImpreso + ":");
				}

				Iterator<Comparendo> iter3 =  paraMostar.iterator();

				int conteo = 0;
				while(iter3.hasNext() && conteo <=maximoNumeroDatosImpreso){

					Comparendo actual = iter3.next();
					System.out.println("-" + actual.retornarDatosRequerimiento1A());
					conteo++;

				}
			}

			catch(Exception e){ System.out.println("No se encontraron comparendos en el rango de fechas dado"); }

		}
		catch (Exception e){ System.out.println("El formato de fecha ingresado es inválido"); }

		// vacio el arbol rojo-negro para ultilizaro en otros requerimientos
		System.out.println();
		comps4 = new RedBlackBST<>();

	}

	public void requerimiento1C(int pRango){

		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

		Iterator<Comparendo> iter = comparendos.iterator();

		while(iter.hasNext()){

			Comparendo actual = iter.next();
			comps4.put(actual.FECHA_HORA, actual); // la llave de los comparendos sera su fecha YYYY MM DD

		}

		try{Date fechaInicial = parser.parse("2018-01-01");	

		Calendar fecha = Calendar.getInstance();
		fecha.setTime(fechaInicial);

		System.out.println("Rango de fechas         |  " + "Comparendos durante el año");
		System.out.println("-------------------------------------------------------");

		while(fecha.get(Calendar.YEAR)==2018){
			int anoLimiteInferior = fecha.get(Calendar.YEAR);
			String mesLimiteInferior = String.format("%02d", fecha.get(Calendar.MONTH)+1);
			String diaMesLimiteInferior = String.format("%02d", fecha.get(Calendar.DAY_OF_MONTH));
			String fechaInferior = anoLimiteInferior + "-" + mesLimiteInferior + "-" + diaMesLimiteInferior;

			fecha.add(Calendar.DATE, pRango-1);

			Calendar limiteSup = Calendar.getInstance();
			limiteSup = fecha;

			int anoLimiteSuperior = fecha.get(Calendar.YEAR);
			String mesLimiteSuperior = String.format("%02d", fecha.get(Calendar.MONTH)+1);
			String diaMesLimiteSuperior = String.format("%02d", fecha.get(Calendar.DAY_OF_MONTH));

			Date fechaInf = parser.parse(fechaInferior);

			limiteSup.add(Calendar.DATE, 1);

			int anoLimiteSuperior1 = fecha.get(Calendar.YEAR);
			String mesLimiteSuperior1 = String.format("%02d", fecha.get(Calendar.MONTH)+1);
			String diaMesLimiteSuperior1 = String.format("%02d", fecha.get(Calendar.DAY_OF_MONTH));
			String fechaSuperior = anoLimiteSuperior1 + "-" + mesLimiteSuperior1 + "-" + diaMesLimiteSuperior1;

			Date fechaSup = parser.parse(fechaSuperior);

			Iterator<ArrayList<Comparendo>> valoresEnRango = comps4.valuesInRange(fechaInf, fechaSup);

			int conteo = 0;
			boolean continuar = true;
			while(valoresEnRango.hasNext()){

				ArrayList<Comparendo> actual = valoresEnRango.next();

				Iterator<Comparendo> actuales = actual.iterator();

				while(actuales.hasNext()){

					Comparendo act = actuales.next();
					conteo++;

					if(act.FECHA_HORA.before(limiteSup.getTime())){

					}
					else{

						continuar = false;
						break;
					}

				}

				if(continuar == false){
					break;
				}
			}

			String asteriscos = imprimirNAstericos(conteo/valorAsterisco);


			System.out.println(anoLimiteInferior + "/" + mesLimiteInferior + "/" + diaMesLimiteInferior + "-"
					+ anoLimiteSuperior + "/" + mesLimiteSuperior + "/" + diaMesLimiteSuperior + "   |  " + asteriscos);
		}

		System.out.println("Cada * representa " + valorAsterisco +  " Comparendos");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println();
		comps4 = new RedBlackBST<>();

	}

	// - El costo de los tiempos de espera hoy en día (cola)
	public void requerimiento2C(){



	}

	public void requerimiento3C(){

		Penalizaciones pen = new Penalizaciones();
		Iterator<Comparendo> iter = comparendos.iterator();
		ComparadorRequerimiento3C comparador = new ComparadorRequerimiento3C();

		while(iter.hasNext()){

			Comparendo actual = iter.next();
			comps3.insert(actual, comparador);
		}

		System.out.println("Fecha       | Comparendos procesados                 ***");
		System.out.println("            | Comparendos que están en espera        ###");
		System.out.println("-----------------------------------------------------------");

		int numeroDia = 1;
		while(!comps3.isEmpty()){

			int conteo = 0;
			while(conteo<1500 && !comps3.isEmpty()){

				Comparendo actual = comps3.max();
				if(actual.darNumeroDia() > numeroDia){
					System.out.println(convertirDiaAFecha(numeroDia) + "  |  " + imprimirNAstericos(conteo/valorAsterisco2));
					System.out.println("            |  " + imprimirNNumerales(numeroEnEspera(numeroDia)/valorAsterisco2));			
					numeroDia ++;
					break;
				}
				else{ 

					String descripcion = actual.DES_INFRAC;
					int diferenciaDia = numeroDia - actual.darNumeroDia();

					comps3.delMax(comparador);
					pen.agregar(descripcion, diferenciaDia);
					conteo++;

					if(conteo == 1500){
						System.out.println(convertirDiaAFecha(numeroDia) + "  |  " + imprimirNAstericos(conteo/valorAsterisco2));
						System.out.println("            |  " + imprimirNNumerales(numeroEnEspera(numeroDia)/valorAsterisco2));
						numeroDia++;
					}
				}

				if(comps3.isEmpty()){

					while(numeroDia<=365){

						System.out.println(convertirDiaAFecha(numeroDia) + "  |  " + imprimirNAstericos(conteo/valorAsterisco2));
						System.out.println("            |  " + imprimirNNumerales(numeroEnEspera(numeroDia)/valorAsterisco2));
						numeroDia++;
						conteo = 0;


					}
				}
			}

		}

		System.out.println("Cada * y # representa " + valorAsterisco2 + " Comparendos");
		System.out.println();
		System.out.println("El costo total que generan las penalizaciones en 2018 es: " + pen.costoTotal());
		double prom400 = pen.darPromedio400(); double prom40 = pen.darPromedio4(); double prom4 = pen.darPromedio4();
		double promediosSumados = prom400 + prom40 + prom4;
		System.out.println("El número de días en promedio que debe esperar un comparendo es: " + promediosSumados/3);
		System.out.println();
		System.out.println("Costo diario del comparendo | Tiempo mínimo de espera (días) | Tiempo promedio de espera (días) | tiempo máximo espera (días)" );
		System.out.println("$400                        | " +convertirA32(String.valueOf(pen.min400()))+ "| " + convertirA32(String.valueOf(pen.darPromedio400())) + "  | " + convertirA32(String.valueOf(pen.max400()))); 
		System.out.println("$40                         | " +convertirA32(String.valueOf(pen.min40()))+ "| " + convertirA32(String.valueOf(pen.darPromedio40())) + "  | " + convertirA32(String.valueOf(pen.max40())));
		System.out.println("$4                          | " +convertirA32(String.valueOf(pen.min4()))+ "| " + convertirA32(String.valueOf(pen.darPromedio4())) + "  | " + convertirA32(String.valueOf(pen.max4())));
		
		System.out.println();
		comps3 = new MaxHeapCP<>(300000);

	}

	public int numeroEnEspera(int diaFinal){

		ComparadorRequerimiento3C comparador = new ComparadorRequerimiento3C();
		Iterator<Comparendo> iter = comps3.iterator(comparador);

		int conteo = 0;
		while(iter.hasNext()){

			Comparendo actual = iter.next();
			int dia = actual.darNumeroDia();
			if(dia>diaFinal){
				break;
			}
			else{
				conteo++;
			}
		}

		return conteo;
	}

	public String imprimirNAstericos(int n){

		String rta = "";

		for(int i = 0; i<n; i++){

			rta += "*";
		}

		return rta;
	}

	public String imprimirNNumerales(int n){

		String rta = "";

		for(int i = 0; i<n; i++){

			rta += "#";
		}

		return rta;
	}

	public String convertirALlaveUnaFecha(Calendar fecha){


		int anoAct = fecha.get(Calendar.YEAR);
		int mesAct = fecha.get(Calendar.MONTH) + 1;
		int diaMes = fecha.get(Calendar.DAY_OF_MONTH);
		String mes = String.format("%02d", mesAct);
		String dia = String.format("%02d",diaMes);
		String key = anoAct + mes + dia;

		return key;

	}

	public void prueba(){

		try{
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

			Date fechaInicial = parser.parse("2018-01-01");	

			Calendar fecha = Calendar.getInstance();
			fecha.setTime(fechaInicial);
			int numeroDia = fecha.get(Calendar.DAY_OF_YEAR);
			System.out.println(numeroDia);
			fecha.add(Calendar.DATE, 1);
			numeroDia = fecha.get(Calendar.DAY_OF_YEAR);
			System.out.println(numeroDia);

			fechaInicial = parser.parse("2018-12-31");	
			fecha.setTime(fechaInicial);
			numeroDia = fecha.get(Calendar.DAY_OF_YEAR);
			System.out.println(numeroDia);

		}

		catch(Exception e){

		}

	}

	public String convertirDiaAFecha(int dia){

		Year y = Year.of( 2018 );
		LocalDate ld = y.atDay(dia) ;
		String localDate = ld.toString();
		String rta = localDate.replaceAll("-", "/");
		return rta;
	}

	public String convertirA32(String aConvertir){

		int tamano = aConvertir.length();

		for(int i = tamano; i<=30; i++){

			aConvertir+=" ";
		}

		return aConvertir;


	}



}