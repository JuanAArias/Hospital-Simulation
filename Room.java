package hw6;
public class Room implements Comparable<Room>{
	private int roomNum;
	private boolean inUse;
	private int timeTilEmpty;
	private PatientVisit pv;
	
	private double timeUsed;  //in minutes
	private int patientsProcessed;
	
	/**
	 * Constructs empty room
	 */
	public Room(int roomNum) {
		this.roomNum = roomNum;
		inUse = false;
		timeTilEmpty = 0;
		pv = null;
		timeUsed = 0;
		patientsProcessed = 0;
	}
	
	/**
	 * Adds Patient to room
	 * @param pv PatientVisit
	 * 
	 */
	public void addPatient(PatientVisit pv) {
		if (!inUse) {
			this.pv = pv;
			inUse = true;
			timeTilEmpty = (int) pv.getDuration();
			patientsProcessed++;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPatientsProcessed() {
		return patientsProcessed;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRoomNum() {
		return roomNum;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getTimeTilEmpty() {
		return timeTilEmpty;
	}
	
	/**
	 * 
	 */
	public void printPatientInfo() {
		System.out.printf("Arrival: %f,Urgency: %d Duration %f\n\n",
				pv.getArrivalTime(), 
				pv.getUrgency(),
				pv.getDuration());
	}
	
	/**
	 * 
	 */
	public void passTime() {
		timeTilEmpty--;
	}
	
	/**
	 * 
	 * Resets room if patient can be removed
	 * @return boolean
	 * 
	 */
	public boolean canRemovePatient() {
		if (inUse) {
			if (timeTilEmpty <= 0) {
				timeUsed += pv.getDuration();
				
				pv = null;
				inUse = false;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns time room has been used in minutes
	 * @return timeUsed
	 * 
	 */
	public double getTimeUsed() {
		return timeUsed;
	}
	
	/**
	 * Compares to other Room
	 * @param other
	 * @return comparison
	 * 
	 */
	@Override
	public int compareTo(Room o) {
		return (int) (timeTilEmpty*100 - o.timeTilEmpty*100);
	}
	
	
}
