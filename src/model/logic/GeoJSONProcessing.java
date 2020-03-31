package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.Cola;
import model.data_structures.Comparendo;
import model.data_structures.IArregloDinamico;
import model.data_structures.ICola;
import model.data_structures.IPila;
import model.data_structures.LinearProbing;
import model.data_structures.MaxHeapCP;
import model.data_structures.Pila;
import model.data_structures.SeparateChaining;



public class GeoJSONProcessing {

	private Comparendo comparendoMayor;

	private int totalComps;

	// Solucion de carga de datos publicada al curso Estructuras de Datos 2020-10
	public void cargarDatos(SeparateChaining<String, Comparendo> hashTable1, LinearProbing<String, Comparendo> hashTable2, MaxHeapCP<Comparendo> colaPrioridad, String direccion){

		JsonReader reader;
		try {
			int objectIDMayor = 0;
			int conteo = 0;
			reader = new JsonReader(new FileReader(direccion));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();

			SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

			for(JsonElement e: e2) {
				Comparendo c = new Comparendo();
				c.OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();

				String s = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();	
				c.FECHA_HORA = parser.parse(s);

				c.MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETECCION").getAsString();
				c.CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHICULO").getAsString();
				if(c.CLASE_VEHI.equals("CAMIÓN")){
					c.CLASE_VEHI = "CAMI�N";
				}
				if(c.CLASE_VEHI.equals("AUTOMÓVIL")){
					c.CLASE_VEHI = "AUTOM�VIL";
				}

				c.TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVICIO").getAsString();
				if(c.TIPO_SERVI.equals("Público")){
					c.TIPO_SERVI = "P�blico   ";
				}

				if(c.TIPO_SERVI.equals("Oficial")){
					c.TIPO_SERVI = "Oficial   ";
				}
				c.INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				c.DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRACCION").getAsString();	
				c.LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();
				c.MUNICIPIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("MUNICIPIO").getAsString();

				c.longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				c.latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();


				Calendar fecha = Calendar.getInstance();
				fecha.setTime(c.FECHA_HORA);
				int ano = fecha.get(Calendar.YEAR);
				int mes = fecha.get(Calendar.MONTH) + 1;
				int dia = fecha.get(Calendar.DAY_OF_MONTH);

				String mes1 = convertirIntAString(mes);
				String dia1 = convertirIntAString(dia);

				if(mes1.length() ==1){
					mes1 = "0" + mes1;
				}

				if(dia1.length() ==1){
					dia1 = "0" + dia1;
				}


				//(FECHA (a�o/mes/d�a),CLASE_VEHICULO, INFRACCION
				String key = convertirIntAString(ano) + mes1 + dia1 +  c.CLASE_VEHI.trim() + c.INFRACCION.trim();

				hashTable1.putInSet(key, c);
				hashTable2.put(key, c);
				colaPrioridad.insert(c);

				if(c.OBJECTID>objectIDMayor){
					objectIDMayor = c.OBJECTID;
					comparendoMayor = c;
				}

				conteo++;

			}

			totalComps = conteo;

		} 
		catch (FileNotFoundException | ParseException e) {

			e.printStackTrace();

		}

	}

	public String convertirIntAString(int n){

		return Integer.toString(n);

	}

	public void retornarCompObjectIDMayor(){

		System.out.println("El comparendo con el mayor ObjectID encontrado es: ");
		System.out.println(comparendoMayor.retornarDatos());

	}

	public void retornarTotalComps(){

		System.out.println("El total de comparendos leidos es: " + totalComps);
	}

	public String RetornarDatos(Comparendo comp){

		String rta = "OBJECTID: "+comp.OBJECTID +" FECHA_HORA: " + comp.FECHA_HORA + " INFRACCION: " + comp.INFRACCION + " CLASE_VEHI: "+comp.CLASE_VEHI + " TIPO_SERVI: " +
				comp.TIPO_SERVI + " LOCALIDAD: "+ comp.LOCALIDAD;
		return rta;
	}

}