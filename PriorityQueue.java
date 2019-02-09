package hw6;
import java.util.*;
/**
 * A priority queue behaves like a queue,
 * except that objects are not always added at the rear of the queue.
 * Instead, objects are added according to their priority.
 * If two objects are equal, they are handled first-in, first-out.
 * Removal is always from the front.
 * Linked list implementation based on generics.
 * The nodes (or links) doubly-linked.
 * The type of the data stored in the nodes should be
 * a generic type that is comparable.
 * 
 * @author Juan
 *
 * @param <E>
 */
public class PriorityQueue<E extends Comparable<E>> {
	QueueNode front;
	
	/**
	 * Constructs an empty Queue
	 */
	public PriorityQueue() {
		front = null;
	}
	
	/**
	 * Inserts the object in the Queue
	 * 
	 * @param e The object to inserted
	 * 
	 */
	public void insert(E e) {
		if (front == null) {
			front = new QueueNode(e);
		} else {
			QueueNode current = front;
			boolean notInserted = true;
			QueueNode prev = null;
			
			while (notInserted && current != null) {
				int comp = current.e.compareTo(e);
				prev = (current.prev != null) ? current.prev : prev;
				
				if (comp >= 0) {
					QueueNode temp = current;
					temp.prev = null;
					
					current = new QueueNode(e, temp, prev);
					notInserted = false;
					
					temp.prev = current;
					
					if (prev == null) {
						front = current;
					} else {
						prev.next = current;
					}
				
				} else {
					prev = current;
					current = current.next;
				}
			}
			if (notInserted) {
				current = new QueueNode(e, null, prev);
				current.prev.next = current;
			}
		}
	}
	
	/**
	 * Removes and returns the object from the front
	 * 
	 * @return The front object
	 * 
	 */
	public E remove() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		QueueNode temp = front;
		if (front.next != null) {
			front = front.next;
		} else {
			front = null;
		}
		return temp.e;
	}
	
	/**
	 * Returns the object at the front
	 * 
	 * @return The front object
	 * 
	 */
	public E front() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return front.e;
	}
	
	/**
	 * Returns true if Queue is empty, false if not
	 * 
	 * @return boolean
	 * 
	 */
	public boolean isEmpty() {
		return front == null;
	}
	
	/**
	 * Unit test to make sure all links work going forward
	 * 
	 * @return The Queue as a List
	 * 
	 */
	public List<E> testForwardTraversal() {
		List<E> list = new LinkedList<>();
		QueueNode current = front;
		while (current != null) {
			list.add(current.e);
			current = current.next;
		}
		return list;
	}
	
	/**
	 * Unit test to make sure all links work going backward
	 * 
	 * @return The Queue as a List
	 * 
	 */
	public List<E> testBackwardTraversal() {
		List<E> list = new LinkedList<>();
		QueueNode current = front;
		while (current.next != null) {   //get node to last node before becoming null
			current = current.next;
		}
		while (current != null) {   //go from last back to front using prev
			list.add(current.e);
			current = current.prev;
		}
		return list;
	}
	
	/**
	 * Returns size of this Queue
	 * @return int size
	 * 
	 */
	public int size() {
		if (isEmpty()) {
			return 0;
		}
		int count = 0;
		QueueNode current = front;
		while (current != null) {
			count++;
			current = current.next;
		}
		return count;
	}
	
	/**
	 * Shows objects in Queue for unit testing
	 */
	public void print() {
		if (front != null) {
			QueueNode p = front;
			while (p != null) {
				System.out.println(p.e);
				p = p.next;
			}
		}
	}
	
	/**
	 * Nodes for the Queue
	 * 
	 * @author Juan
	 *
	 */
	class QueueNode {
		E e;
		QueueNode next;
		QueueNode prev;
		
		/**
		 * Constructs empty Node
		 * 
		 */
		QueueNode() {
			this(null, null, null);
		}
		
		/**
		 * Constructs Node with object e
		 * 
		 * @param e
		 * 
		 */
		QueueNode(E e) {
			this(e, null, null);
		}
		
		/**
		 * Constructs Node with object e, next QueueNode
		 * 
		 * @param e
		 * @param next
		 * 
		 */
		QueueNode(E e, QueueNode next) {
			this(e, next, null);
		}
		
		/**
		 * Constructs Node with object e, next QueueNode, previous QueueNode
		 * 
		 * @param e
		 * @param next
		 * 
		 */
		QueueNode(E e, QueueNode next, QueueNode prev) {
			this.e = e;
			this.next = next;
			this.prev= prev;
		}
	}
	

}
