package hw6;

import java.util.NoSuchElementException;

public class EmptyRoomQueue {
	
	EmptyQueueNode front;
	
	/**
	 * 
	 */
	public EmptyRoomQueue() {
		front = null;
	}

	/**
	 * Inserts the object in the Queue
	 * 
	 * @param r The object to inserted
	 * 
	 */
	public void insert(hw6.Room r) {
		if (front == null) {
			front = new EmptyQueueNode(r);
		} else {
			EmptyQueueNode current = front;
			boolean notInserted = true;
			EmptyQueueNode prev = null;
			
			while (notInserted && current != null) {
				double comp = current.rr.getTimeUsed() - r.getTimeUsed();
				prev = (current.prev != null) ? current.prev : prev;
				
				if (comp >= 0) {
					EmptyQueueNode temp = current;
					temp.prev = null;
					
					current = new EmptyQueueNode(r, temp, prev);
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
				current = new EmptyQueueNode(r, null, prev);
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
	public Room remove() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		EmptyQueueNode temp = front;
		if (front.next != null) {
			front = front.next;
		} else {
			front = null;
		}
		return (Room) temp.rr;
	}
	
	
	/**
	 * Returns the object at the front
	 * 
	 * @return The front object
	 * 
	 */
	public Room front() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return (Room)front.rr;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return front == null;
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
		EmptyQueueNode current = front;
		while (current != null) {
			count++;
			current = current.next;
		}
		return count;
	}
	
}
/**
 * Nodes for the Queue
 * 
 * @author Juan
 *
 */
class EmptyQueueNode {
	Room rr;
	EmptyQueueNode next;
	EmptyQueueNode prev;
	
	/**
	 * Constructs empty Node
	 * 
	 */
	EmptyQueueNode() {
		this(null, null, null);
	}
	
	/**
	 * Constructs Node with object e
	 * 
	 * @param e
	 * 
	 */
	EmptyQueueNode(Room r) {
		this(r, null, null);
	}
	
	/**
	 * Constructs Node with object e, next QueueNode
	 * 
	 * @param e
	 * @param next
	 * 
	 */
	EmptyQueueNode(Room r, EmptyQueueNode next) {
		this(r, next, null);
	}
	
	/**
	 * Constructs Node with object e, next QueueNode, previous QueueNode
	 * 
	 * @param e
	 * @param next
	 * 
	 */
	EmptyQueueNode(Room r, EmptyQueueNode next, EmptyQueueNode prev) {
		this.rr = r;
		this.next = next;
		this.prev= prev;
	}
}
