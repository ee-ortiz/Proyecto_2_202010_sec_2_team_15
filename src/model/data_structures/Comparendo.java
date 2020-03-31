package model.data_structures;

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
		//	Mostrar la informaci�n del comparendo (OBJECTID, FECHA_HORA, INFRACCION, CLASE_VEHI, TIPO_SERVI, LOCALIDAD) 

		String rta = "OBJECTID: "+OBJECTID +" FECHA_HORA: " + FECHA_HORA + " INFRACCION: " + INFRACCION + " CLASE_VEHICULO: " + CLASE_VEHI + " TIPO_SERVICIO: " + TIPO_SERVI + " LOCALIDAD: " + LOCALIDAD; 
		return rta;
	}


	public int compareTo(Comparendo comp) {
		return (OBJECTID - comp.OBJECTID);
	}

	public String retornarDatosTaller5(){

		return "OBJECTID: " + OBJECTID + " FECHA_HORA: " + FECHA_HORA + " TIPO_SERVI: " + TIPO_SERVI
				+ " CLASE_VEHI: " + CLASE_VEHI + " INFRACCION: " + INFRACCION;		
	}

}