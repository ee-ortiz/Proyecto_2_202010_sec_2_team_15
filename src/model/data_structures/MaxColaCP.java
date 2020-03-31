package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.print.attribute.standard.Sides;

/**
 *  The {@code LinkedQueue} class represents a first-in-first-out (FIFO)
 *  queue of generic items.
 *  It supports the usual <em>enqueue</em> and <em>dequeue</em>
 *  operations, along with methods for peeking at the first item,
 *  testing if the queue is empty, and iterating through
 *  the items in FIFO order.
 *  <p>
 *  This implementation uses a singly linked list with a non-static nested class 
 *  for linked-list nodes.  See {@link Queue} for a version that uses a static nested class.
 *  The <em>enqueue</em>, <em>dequeue</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class MaxColaCP<T extends Comparable<T>>{
	private int n;         // number of elements on queue
	private Node first;    // beginning of queue
	private Node last;     // end of queue

	// helper linked list class
	private class Node {
		private T item;
		private Node next;

	}

	/**
	 * Initializes an empty queue.
	 */
	public MaxColaCP() {
		first = null;
		last  = null;
		n = 0;
		assert check();
	}

	/**
	 * Is this queue empty?
	 * @return true if this queue is empty; false otherwise
	 */
	public boolean isEmpty() {
		return first == null;

	}

	/**
	 * Returns the number of items in this queue.
	 * @return the number of items in this queue
	 */
	public int size() {
		return n;     
	}

	/**
	 * Returns the item least recently added to this queue.
	 * @return the item least recently added to this queue
	 * @throws java.util.NoSuchElementException if this queue is empty
	 */
	public T peek() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		return first.item;
	}

	/**
	 * Adds the item to this queue.
	 * @param item the item to add
	 */
	public void enqueue(T item) {
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty()) first = last;
		else           oldlast.next = last;
		n++;
		assert check();
	}

	public void agregarOrdenado(T item){

		if (isEmpty()){

			enqueue(item);
		}

		else if(size()==1){

			if(item.compareTo(first.item)<=0){

				enqueue(item);
			}

			else{
				Node nuevo = new Node();
				nuevo.item = item;

				Node aAgregar = first;

				first = nuevo;

				nuevo.next = aAgregar;
				last = aAgregar;
				n++;

			}
		}

		else if(last.item.compareTo(item)>=0){
			enqueue(item);	
		}

		else{

			Node actual = first;
			Node siguiente = first.next;

			while(actual.next !=null){

				if(item.compareTo(actual.item)<=0 && item.compareTo(siguiente.item)>0){

					Node aAgregar = new Node();
					aAgregar.item = item;

					actual.next = aAgregar;
					aAgregar.next = siguiente;

					n++;

					break;			
				}

				actual = actual.next;
				siguiente = actual.next;

			}


		}

	}

	/**
	 * Removes and returns the item on this queue that was least recently added.
	 * @return the item on this queue that was least recently added
	 * @throws java.util.NoSuchElementException if this queue is empty
	 */
	public T dequeue() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		T item = first.item;
		first = first.next;
		n--;
		if (isEmpty()) last = null;   // to avoid loitering
		assert check();
		return item;
	}

	// check internal invariants
	private boolean check() {
		if (n < 0) {
			return false;
		}
		else if (n == 0) {
			if (first != null) return false;
			if (last  != null) return false;
		}
		else if (n == 1) {
			if (first == null || last == null) return false;
			if (first != last)                 return false;
			if (first.next != null)            return false;
		}
		else {
			if (first == null || last == null) return false;
			if (first == last)      return false;
			if (first.next == null) return false;
			if (last.next  != null) return false;

			// check internal consistency of instance variable n
			int numberOfNodes = 0;
			for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
				numberOfNodes++;
			}
			if (numberOfNodes != n) return false;

			// check internal consistency of instance variable last
			Node lastNode = first;
			while (lastNode.next != null) {
				lastNode = lastNode.next;
			}
			if (last != lastNode) return false;
		}

		return true;
	} 


	/**
	 * Returns an iterator that iterates over the items in this queue in FIFO order.
	 * @return an iterator that iterates over the items in this queue in FIFO order
	 */
	public Iterator<T> iterator()  {
		return new LinkedIterator();  
	}

	// an iterator, doesn't implement remove() since it's optional
	private class LinkedIterator implements Iterator<T> {
		private Node current = first;

		public boolean hasNext()  { return current != null;                     }
		public void remove()      { throw new UnsupportedOperationException();  }

		public T next() {
			if (!hasNext()) throw new NoSuchElementException();
			T item = current.item;
			current = current.next; 
			return item;
		}
	}

}