package model.data_structures;

import java.util.Comparator;
import java.util.Date;

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
	public String retornarDatosRequerimiento1B(){

		return "OBJECTID: " + OBJECTID + " TIPO_SERVI: " + TIPO_SERVI  + " INFRACCION: " + INFRACCION +" FECHA_HORA: " + FECHA_HORA +
				" LATITUD: " + latitud+ "LONGITUD"+longitud;	
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
	
	public static class ComparadorXCercania implements Comparator<Comparendo>{
		
		double  x1;
		double y1; 
		double x2;
		double y2; 
		Integer comp1;
		Integer comp2;
		private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

	    public static double distance(double startLat, double startLong,
	                                  double endLat, double endLong) {

	        double dLat  = Math.toRadians((endLat - startLat));
	        double dLong = Math.toRadians((endLong - startLong));

	        startLat = Math.toRadians(startLat);
	        endLat   = Math.toRadians(endLat);

	        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
	        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	        return EARTH_RADIUS * c; // <-- d
	    }

	    public static double haversin(double val) {
	        return Math.pow(Math.sin(val / 2), 2);
	    }
	    
		public int compare(Comparendo c1, Comparendo c2)
		{
			
			x1= c1.latitud;
			x2= c2.latitud;
			y1= c1.longitud;
			y2= c2.longitud;
			comp1= (int)distance( x1, y1, 4.647586, 74.078122);
			comp2= (int)distance( x2, y2, 4.647586, 74.078122);
			return comp1.compareTo(comp2);
		}
}
	
	public static class ComparadorXFecha implements Comparator<Comparendo>{

		public int compare(Comparendo c1, Comparendo c2){

			return (c1.FECHA_HORA.compareTo(c2.FECHA_HORA))*(-1); // en este caso se multiplica por -1
																// para que si la fecha de c1 es mayor que la de c2
																// entonces se retorne numero negativo
																// eso asegura que cuando se agregen a la cola de  
																// prioridad la cola quede  en orden ascendente
		}
		public static class ComparadorXLocalidad implements Comparator<Comparendo>{

			public int compare(Comparendo c1, Comparendo c2){

				return (c1.FECHA_HORA.compareTo(c2.FECHA_HORA))*(-1);
		
	}

}}}