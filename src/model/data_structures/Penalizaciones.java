package model.data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Penalizaciones {

	// los valores de la arrayList son los dias que se demoro el comprando en procesarse

	private ArrayList<Integer> pen400;

	private ArrayList<Integer> pen40;

	private ArrayList<Integer> pen4;

	private double promedioTotal;

	private double prom400;

	private double prom40;

	private double prom4;


	public Penalizaciones(){

		pen400 = new ArrayList<Integer>();
		pen40 = new ArrayList<Integer>();
		pen4 = new ArrayList<Integer>();
	}

	public void agregar(String descripcion, int diferenciaDias){

		if(descripcion.contains("INMOVILIZADO")) pen400.add(diferenciaDias);
		else if(descripcion.contains("LICENCIA DE CONDUCC")) pen40.add(diferenciaDias);
		else pen4.add(diferenciaDias);
	}

	public int costoTotal(){

		int costo = 0;


		Iterator<Integer> iter1 = pen400.iterator();

		while(iter1.hasNext()){

			Integer actual = iter1.next();
			promedioTotal+= actual;
			costo += actual*400;		
		}

		Iterator<Integer> iter2 = pen40.iterator();

		while(iter2.hasNext()){

			Integer actual = iter2.next();

			costo += actual*40;		
		}

		Iterator<Integer> iter3 = pen4.iterator();

		while(iter3.hasNext()){

			Integer actual = iter3.next();

			costo += actual*4;		
		}

		return costo;
	}

	public int totalProcesados(){

		return pen400.size() + pen40.size() + pen4.size();

	}

	public double darPromedioTotal(){

		return promedioTotal;
	}

	public double darPromedio400(){

		Iterator<Integer> iter = pen400.iterator();
		double conteo = 0;

		while(iter.hasNext()){

			Integer actual = iter.next();
			conteo += actual;		
		}

		return conteo/pen400.size();
	}

	public double darPromedio40(){

		Iterator<Integer> iter = pen40.iterator();
		double conteo = 0;

		while(iter.hasNext()){

			Integer actual = iter.next();
			conteo += actual;		
		}

		return conteo/pen40.size();
	}

	public double darPromedio4(){

		Iterator<Integer> iter = pen4.iterator();
		double conteo = 0;

		while(iter.hasNext()){

			Integer actual = iter.next();
			conteo += actual;		
		}

		return conteo/pen4.size();
	}

	public int max400(){

		return Collections.max(pen400);
	}

	public int max40(){

		return Collections.max(pen40);
	}

	public int max4(){

		return Collections.max(pen4);
	}

	public int min400(){

		return Collections.min(pen400);
	}
	
	public int min40(){

		return Collections.min(pen40);
	}
	
	public int min4(){

		return Collections.min(pen4);
	}


}
