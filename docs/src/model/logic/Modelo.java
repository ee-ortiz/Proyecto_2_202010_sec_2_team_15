package model.logic;

import model.data_structures.ArregloDinamico;
import model.data_structures.Cola;
import model.data_structures.Comparendo;
import model.data_structures.IArregloDinamico;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private IArregloDinamico<Comparendo> comps;
	private GeoJSONProcessing objetoJsonGson;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{
		comps = new ArregloDinamico(600000);
		objetoJsonGson = new GeoJSONProcessing();
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return comps.darTamano();
	}


	/**
	 * Requerimiento buscar dato
	 * @param dato Dato a buscar
	 * @return dato encontrado
	 */
	public Comparendo buscar(Comparendo dato)
	{
		return comps.buscar(dato);
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

	public Comparendo darElemento(int numero){
		return comps.darElemento(numero);

	}

	public void cargar(String direccion){

		objetoJsonGson.cargarDatos(comps, direccion);;
	}

	public IArregloDinamico<Comparendo> darArreglo(){

		return comps;
	}

	public String RetornarDatos(Comparendo comp){
		//INFRACCION, OBJECTID,
		//FECHA_HORA, CLASE_VEHI, TIPO_SERVI, LOCALIDAD.
		String rta = "Codigo de infraccion: "+comp.INFRACCION +" ObjectID: " + comp.OBJECTID + " Fecha y hora: " + comp.FECHA_HORA + " Clase de vehiculo "+comp.CLASE_VEHI + " Tipo de servicio: " +
				comp.TIPO_SERVI + " Localidad: "+ comp.LOCALIDAD;
		return rta;
	}
}