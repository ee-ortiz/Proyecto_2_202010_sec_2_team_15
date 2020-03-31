package test.data_structures;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.MaxColaCP;

public class TestMaxColaCP {

	private MaxColaCP<Integer> maxColaCP;

	@Before
	public void setUp1(){

		maxColaCP = new MaxColaCP<Integer>();
	}


	public void setUp2(){
		// agrega ordeandamente elementos de los extremos de un conjunto de 0 a 100
		int j = 100;
		for(int i = 0; i<51; i++){

			if(i == j){
				maxColaCP.agregarOrdenado((Integer)i);
			}
			else{
				maxColaCP.agregarOrdenado((Integer)i);
				maxColaCP.agregarOrdenado((Integer)j);
			}
			j--;
		}
	}

	@Test
	public void testMaxColaCPCorrectaPrioridad(){

		setUp1();
		setUp2();

		Iterator<Integer> iter = maxColaCP.iterator();

		int conteo = 100;
		while(iter.hasNext()){
			// compruebo si la maxColaCP agregó correctamente por la prioridad de Mayor a Menor
			Integer actual = iter.next();
			assertEquals(conteo ,(int)actual);
			conteo--;
		}
	}

	@Test
	public void testMaxColaCPCorrectoTamano(){

		setUp1();
		setUp2();

		assertEquals(101, maxColaCP.size());
	}

	@Test
	public void correctoDarMaxYSacarMax(){

		setUp1();
		setUp2();

		assertEquals(100, (int)maxColaCP.peek());
		assertEquals(100, (int)maxColaCP.dequeue());
		assertEquals(99, (int)maxColaCP.peek());
	}


}


