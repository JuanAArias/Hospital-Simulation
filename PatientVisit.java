package hw6;

/**
 * A class to describe the visit of a patient, defined by the arrival time, the
 * urgency of the visit, and the length of the visit.
 * 
 * @param <E>
 */
public class PatientVisit implements Comparable<PatientVisit> {

	private double arrivalTime; // in minutes

	private int urgency; // 1 to 10 (1 is the most urgent)

	private double duration; // duration of the visit in minutes

	/**
	 * Creates a patient visit given its characteristics
	 * 
	 * @param theArrivalTime
	 *            the time of the arrival (in minutes)
	 * @param theUrgency
	 *            the urgency level (1 to 10, 1 being most urgent)
	 * @param theDuration
	 *            the length of the patient visit (in minutes)
	 */
	public PatientVisit(double theArrivalTime, int theUrgency, double theDuration) {
		arrivalTime = theArrivalTime;
		urgency = theUrgency;
		duration = theDuration;
	}

	/**
	 * Returns the arrival time (in minutes)
	 * 
	 * @return the arrival time (in minutes)
	 */
	public double getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Returns the duration of the visit (in minutes)
	 * 
	 * @return the duration of the visit (in minutes)
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Returns the urgency of the visit (1 to 10, 1 being most urgent)
	 * 
	 * @return the urgency of the visit
	 */
	public int getUrgency() {
		return urgency;
	}

	/**
	 * Compares the urgency between this and another PatientVisit
	 * 
	 * @param o
	 *            The object this is compared to
	 * @return int value
	 * 
	 */
	@Override
	public int compareTo(PatientVisit other) {
		if (urgency != other.urgency) {
			return urgency - other.urgency;
		}
		return (int) (arrivalTime * 100 - other.arrivalTime * 100);
	}
	
	public String toString() {
		return "Arrival time: " + this.arrivalTime
				+ "Urgency: " + this.urgency
				+ "Duration: " + this.duration;
	}

}
