package test.data_structures;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;

public class TestMaxHeapCP {

	private MaxHeapCP<Integer> maxHeapCP;

	@Before
	public void setUp1(){

		maxHeapCP = new MaxHeapCP<Integer>();
	}


	public void setUp2(){
		// agrega ordeandamente elementos de los extremos de un conjunto de 0 a 100
		int j = 100;
		for(int i = 0; i<51; i++){

			if(i == j){
				maxHeapCP.insert((Integer)i, null);
			}
			else{
				maxHeapCP.insert((Integer)i, null);
				maxHeapCP.insert((Integer)j, null);
			}
			j--;
		}
	}

	@Test
	public void testMaxHeapCPCorrectaPrioridad(){

		setUp1();
		setUp2();


		Iterator<Integer> iter = maxHeapCP.iterator(null);

		int conteo = 100;
		while(iter.hasNext()){
			// compruebo si la maxColaCP agregó correctamente por la prioridad de Mayor a Menor
			Integer actual = iter.next();
			assertEquals(conteo ,(int)actual);
			conteo--;
		}
	}

	@Test
	public void testMaxHeapCPCorrectoTamano(){

		setUp1();
		setUp2();

		assertEquals(101, maxHeapCP.size());
	}

	@Test
	public void correctoDarMaxYSacarMax(){

		setUp1();
		setUp2();

		assertEquals(100, (int)maxHeapCP.max());
		assertEquals(100, (int)maxHeapCP.delMax(null));
		assertEquals(99, (int)maxHeapCP.max());
	}


}
