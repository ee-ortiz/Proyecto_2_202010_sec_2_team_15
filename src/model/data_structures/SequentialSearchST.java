package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;

public class SequentialSearchST<Key, Value>
{
	private Node first; // primer nodo de la lista encadenada
	private int n;      // number of key-value pairs

	private class Node
	{ // linked-list node
		Key key;
		ArrayList<Value> vals;
		Node next;
		public Node(Key key, ArrayList<Value> val, Node next)
		{
			this.key = key;
			this.vals = val;
			this.next = next;
		}
	}

	public SequentialSearchST() {
	}

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 *
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns true if this symbol table is empty.
	 *
	 * @return {@code true} if this symbol table is empty;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns true if this symbol table contains the specified key.
	 *
	 * @param  key the key
	 * @return {@code true} if this symbol table contains {@code key};
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");
		return getValues(key) != null;
	}

	/**
	 * Returns the value associated with the given key in this symbol table.
	 *
	 * @param  key the key
	 * @return the value associated with the given key if the key is in the symbol table
	 *     and {@code null} if the key is not in the symbol table
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public ArrayList<Value> getValues(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null"); 
		for (Node x = first; x != null; x = x.next) {
			if (key.equals(x.key))
				return x.vals;
		}
		return null;
	}

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is {@code null}.
	 *
	 * @param  key the key
	 * @param  val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(Key key, Value val) {

		if (key == null) throw new IllegalArgumentException("first argument to put() is null"); 
		if (val == null) {
			delete(key);
			return;

		}

		for (Node x = first; x != null; x = x.next) {
			if (key.equals(x.key)) {

				boolean agregar = true;

				for(int i = 0; i<x.vals.size(); i++){
					
					if(val.hashCode()==x.vals.get(i).hashCode()){

						agregar = false;
						break;
					}
				}

				if(agregar ==true){

					x.vals.add(val);
					return;
				}

			}
		}
		ArrayList<Value> nuevo = new ArrayList<>();
		nuevo.add(val);
		first = new Node(key, nuevo , first);
		n++;
	}

	public Iterator<Value> values(Key key){

		return getValues(key).iterator();


	}

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to delete() is null"); 
		first = delete(first, key);
	}

	// delete key in linked list beginning at Node x
	// warning: function call stack too large if table is large
	private Node delete(Node x, Key key) {
		if (x == null) return null;
		if (key.equals(x.key)) {
			n--;
			return x.next;
		}
		x.next = delete(x.next, key);
		return x;
	}


	/**
	 * Returns all keys in the symbol table as an {@code Iterable}.
	 * To iterate over all of the keys in the symbol table named {@code st},
	 * use the foreach notation: {@code for (Key key : st.keys())}.
	 *
	 * @return all keys in the symbol table
	 */
	public Iterable<Key> keys()  {
		Queue<Key> queue = new Queue<Key>();
		for (Node x = first; x != null; x = x.next)
			queue.enqueue(x.key);
		return queue;
	}

}
