package model.data_structures;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import sun.security.provider.certpath.DistributionPointFetcher;

public class Comparendo implements Comparable<Comparendo> {

	public int OBJECTID;
	public Date FECHA_HORA;
	public String DES_INFRAC;
	public String MEDIO_DETE;
	public String CLASE_VEHI;
	public String TIPO_SERVI;
	public String INFRACCION;
	public String LOCALIDAD;
	public String MUNICIPIO;

	public double latitud;
	public double longitud;


	public String retornarDatos(){
		//	Mostrar la información del comparendo (OBJECTID, FECHA_HORA, INFRACCION, CLASE_VEHI, TIPO_SERVI, LOCALIDAD) 

		String rta = "OBJECTID: "+OBJECTID +" FECHA_HORA: " + FECHA_HORA + " INFRACCION: " + INFRACCION + " CLASE_VEHICULO: " + CLASE_VEHI + " TIPO_SERVICIO: " + TIPO_SERVI + " LOCALIDAD: " + LOCALIDAD; 
		return rta;
	}


	public int compareTo(Comparendo comp) {
		return (OBJECTID - comp.OBJECTID);
	}

	// id, tipo de servicio, infracción, fecha-hora y clase de vehículo.
	public String retornarDatosRequerimiento1A(){

		return "OBJECTID: " + OBJECTID + " TIPO_SERVI: " + TIPO_SERVI  + " INFRACCION: " + INFRACCION +" FECHA_HORA: " + FECHA_HORA +
				" CLASE_VEHI: " + CLASE_VEHI;	
	}

	// tipo servicio -- infraccion
	public static class ComparadorXGravedad implements Comparator<Comparendo> {

		Integer comp1;
		Integer comp2; 

		public int compare(Comparendo c1, Comparendo c2) {

			if(c1.TIPO_SERVI.compareTo(c2.TIPO_SERVI)==0){

				return c1.INFRACCION.compareTo(c2.INFRACCION);
			}

			else{

				if(c1.TIPO_SERVI.trim().equals("Particular")) comp1 = 1;
				if(c1.TIPO_SERVI.trim().equals("Público")) comp1 = 3;
				if(c1.TIPO_SERVI.trim().equals("Oficial")) comp1 = 2;

				if(c2.TIPO_SERVI.trim().equals("Particular")) comp2 = 1;
				if(c2.TIPO_SERVI.trim().equals("Público")) comp2 = 3;
				if(c2.TIPO_SERVI.trim().equals("Oficial")) comp2 = 2;

				return comp1.compareTo(comp2);

			}

		}

	}

	public int darNumeroDia(){

		Calendar fecha = Calendar.getInstance();
		fecha.setTime(FECHA_HORA);
		int dia = fecha.get(Calendar.DAY_OF_YEAR);
		return dia;
	}

	public static class ComparadorXFecha implements Comparator<Comparendo>{

		public int compare(Comparendo c1, Comparendo c2){

			return (c1.FECHA_HORA.compareTo(c2.FECHA_HORA))*(-1); // en este caso se multiplica por -1
			// para que si la fecha de c1 es mayor que la de c2
			// entonces se retorne numero negativo
			// eso asegura que cuando se agregen a la cola de  
			// prioridad la cola quede  en orden ascendente
		}
	}

	public int darPrecioInfraccion(){

		int precio;
		if(DES_INFRAC.contains("INMOVILIZADO")) precio = 400;
		else if(DES_INFRAC.contains("LICENCIA DE CONDUCC")) precio = 40;
		else precio =  4;

		return precio;
	}


	public static class ComparadorRequerimiento3C implements Comparator<Comparendo>{

		public int compare(Comparendo c1, Comparendo c2){

			Calendar fecha1 = Calendar.getInstance();
			Calendar fecha2 = Calendar.getInstance();

			fecha1.setTime(c1.FECHA_HORA);
			fecha2.setTime(c2.FECHA_HORA);

			int dia1 = fecha1.get(Calendar.DAY_OF_YEAR);
			int dia2 = fecha2.get(Calendar.DAY_OF_YEAR);

			Integer Dia1 = (Integer) dia1;
			Integer Dia2 = (Integer) dia2;

			int diferencia = Dia1.compareTo(Dia2);

			if(diferencia != 0){

				return diferencia*(-1); // se multiplica por -1 para tener el MaxHeap ordenado de menor a mayor
			}

			else{

				String descripcion1 = c1.DES_INFRAC;
				String descripcion2 = c2.DES_INFRAC;

				int prioridad1;
				int prioridad2;

				if(descripcion1.contains("INMOVILIZADO")) prioridad1 = 3;
				else if(descripcion1.contains("LICENCIA DE CONDUCC")) prioridad1 = 2;
				else prioridad1 = 1;

				if(descripcion2.contains("INMOVILIZADO")) prioridad2 = 3;
				else if(descripcion2.contains("LICENCIA DE CONDUCC")) prioridad2 = 2;
				else prioridad2 = 1;

				Integer Prioridad1 = (Integer) prioridad1;
				Integer Prioridad2 = (Integer) prioridad2;

				return Prioridad1.compareTo(Prioridad2);

			}
		}


	}

}