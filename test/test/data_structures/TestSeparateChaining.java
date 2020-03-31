package test.data_structures;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.SeparateChaining;

public class TestSeparateChaining {

	private SeparateChaining<String, Integer> separateChaining;

	@Before
	public void setUp1(){

		separateChaining = new SeparateChaining<String, Integer>(2);
	}


	public void setUp2(){
		// agrega ordeandamente elementos de los extremos de un conjunto de 0 a 100

		for(int i = 0; i<100; i++){

			String llave = "";

			int divisionEntera = i/10;
			if(divisionEntera == 0 ){

				llave = "digitos";
			}
			else if(divisionEntera == 1 ){

				llave = "Familia del 10";
			}
			else if(divisionEntera == 2 ){

				llave = "Familia del 20";
			}
			else if(divisionEntera == 3 ){

				llave = "Familia del 30";
			}
			else if(divisionEntera == 4 ){

				llave = "Familia del 40";
			}
			else if(divisionEntera == 5 ){

				llave = "Familia del 50";
			}
			else if(divisionEntera == 6 ){

				llave = "Familia del 60";
			}
			else if(divisionEntera == 7 ){

				llave = "Familia del 70";
			}
			else if(divisionEntera == 8 ){

				llave = "Familia del 80";
			}
			else if(divisionEntera == 9 ){

				llave = "Familia del 90";
			}

			separateChaining.putInSet(llave, (Integer)i);
		}

	}

	@Test
	public void testNumeroDeLavesCorrecto(){

		setUp1();
		setUp2();


		assertEquals(10 ,separateChaining.size());

	}

	@Test
	public void testGetCorrecto(){

		setUp1();
		setUp2();

		Iterator<Integer> iter = separateChaining.getSet("Familia del 90");

		int conteo = 90;
		while(iter.hasNext()){

			Integer actual = iter.next();
			assertEquals(conteo, (int) actual);
			conteo++;
		}

	}

	@Test
	public void testDeleteCorrecto(){

		setUp1();
		setUp2();

		ArrayList<Integer> valores = separateChaining.deleteSetArray("Familia del 80");

		Iterator<Integer> iter = valores.iterator();

		int conteo = 80;
		while(iter.hasNext()){

			Integer actual = iter.next();
			assertEquals(conteo, (int) actual);
			conteo++;
		}

		assertEquals(9, separateChaining.size());
	}

	@Test
	public void KeysCorrecto(){

		setUp1();
		setUp2();

		Iterator<String> iter = separateChaining.keys();

		int conteo = 0;
		while(iter.hasNext()){

			iter.next();
			conteo++;

		}


		assertEquals(conteo, separateChaining.size());
	}




}
