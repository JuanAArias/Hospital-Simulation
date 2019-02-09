package hw6;

import java.util.*;

public class Simulation {
	
	private static Scanner scan = new Scanner(System.in);

	private PriorityQueue<PatientVisit> waiting;
	private PriorityQueue<Room> exam;
	private EmptyRoomQueue emptyRooms;
	@SuppressWarnings("unused")
	private double waitingTime;

	private int time; // minutes
	private boolean timeIsOut;

	private boolean rand;

	private int rooms;
	private int arrivalEvents; // add assertion somewhere
	private int departureEvents;
	private int patientsWaited;

	public static final int MINUTES = 100;

	/**
	 * Runs simulation
	 */
	public static void main(String[] args) {
		Simulation s = Simulation.get();
		s.start();
	}

	/**
	 * 
	 */
	private Simulation() {
	}

	/**
	 * Passes 1 minute
	 */
	public void passTime() {
		printUpdate();
		if (!waiting.isEmpty()) {
			waitingTime++;
		}
		assert (exam.size() + emptyRooms.size() == 5);
		time++;
		for (PriorityQueue<Room>.QueueNode r = exam.front; r != null; r = r.next) {
			r.e.passTime();
		}
		// if (MINUTES % time == 0) {
		// printUpdate();
		// assert (exam.size() + emptyRooms.size() == 5);
		// }

		if (!timeIsOut) {
			timeIsOut = (time == MINUTES) ? true : false;
		}
	}

	public void printUpdate() {
		System.out.println("                   TIME: " + time);
		System.out.println("                   ARRIVAL EVENTS: " + arrivalEvents);
		System.out.println("                   DEPARTUTE EVENTS: " + departureEvents);
		System.out.println("EXAM ROOM: ");
		for (PriorityQueue<Room>.QueueNode r = exam.front; r != null; r = r.next) {
			System.out.println(r.e.getRoomNum() + " " + r.e.getTimeTilEmpty());
		}
		System.out.println("\nEMPTY ROOM: ");
		for (EmptyQueueNode r = emptyRooms.front; r != null; r = r.next) {
			System.out.println(r.rr.getRoomNum() + " " + r.rr.getTimeUsed());
		}
		System.out.println("\nWAITING ROOM: ");
		for (PriorityQueue<PatientVisit>.QueueNode p = waiting.front; p != null; p = p.next) {
			System.out.println(p.e);
		}
		System.out.println("--------------------------------------------------");
		System.out.println();
	}

	/**
	 * Sets invariable instance fields
	 */
	private void set() {
		waiting = new PriorityQueue<>();
		exam = new PriorityQueue<>();
		emptyRooms = new EmptyRoomQueue();
		time = 0;
		timeIsOut = false;
		arrivalEvents = 0;
		departureEvents = 0;
		patientsWaited = 0;
	}

	/**
	 * Begins the Simulation
	 */
	private void start() {
		do {
			departureEvent();
			if (!timeIsOut) {
				PatientVisit pv;
				if (!waiting.isEmpty()) {
					pv = waiting.remove();
				} else {
					if (rand) {
						pv = VisitGenerator.getNextRandomArrival(time);
					} else {
						pv = VisitGenerator.getNextProgrammedArrival(time, emptyRooms.size()+exam.size());
					}
				}
				arrivalEvent(pv);
			}
		} while (!exam.isEmpty());
		printResults();
	}

	public void printResults() {
		System.out.println();
		int avgDuration = 0;
		for (EmptyQueueNode r = emptyRooms.front; r != null; r = r.next) {
			avgDuration += r.rr.getTimeUsed();
		}
		avgDuration /= emptyRooms.size();
		System.out.println("Number of treatment rooms = " + emptyRooms.size());
		int hours = time / 60;
		int min = time % 60;
		System.out.println("Duration of the simulation = " + hours + " hours " + min + " min");
		System.out.println("Number of treated patients = " + departureEvents);
		System.out.println("Average wait = " + patientsWaited ); // to do
		System.out.println("Average duration of a treatment = " + avgDuration);
		
		for (EmptyQueueNode r = emptyRooms.front; r != null; r = r.next) {
			int room = r.rr.getRoomNum();
			double percentage = 100 * (r.rr.getTimeUsed() / time);
			int patients = r.rr.getPatientsProcessed();
			System.out.printf("Treatment Room %s: patients treated = %d busy time = %.2f%%\n", room, patients,
					percentage);
		}
		System.out.println("Average interval between arrivals = ");

		// UNSURE ABOUT THIS LAST PART
		//i = 1;
		// for (PriorityQueue<PatientVisit>.QueueNode p = waiting.front; p != null; p =
		// p.next, i++) {
		// System.out.printf("Urgency: %d waitTime = %d min %d sec patients = %d");
		// }
	}

	/**
	 * 
	 */
	public void arrivalEvent(PatientVisit pv) {
		if (!timeIsOut && !emptyRooms.isEmpty()) { // straight into examination
			if (!emptyRooms.isEmpty()) {
				Room r = emptyRooms.remove();
				r.addPatient(pv);
				exam.insert(r);
				arrivalEvents++;
			}
		} else if (!timeIsOut) { // go to waiting queue
			waiting.insert(pv);
			System.out.println("WAITING++");
			patientsWaited++;
		}
		if (rooms != exam.size() + emptyRooms.size()) {
			System.out.println("problems");
//			throw new IllegalArgumentException();
		}
		passTime();
	}

	public void departureEvent() {
		if (!exam.isEmpty() && exam.front().canRemovePatient()) {
			Room r = exam.remove();
			emptyRooms.insert(r);
			departureEvents++;
		}
		if (rooms != exam.size() + emptyRooms.size()) {
			throw new IllegalArgumentException();
		}
		passTime();
	}

	/**
	 * Factory method for a Simulation via user input
	 * 
	 * @return Simulation instance
	 * 
	 */
	public static Simulation get() {
		Simulation sim = new Simulation();
		sim.set();
		System.out.print("How many rooms? ");
		sim.rooms = scan.nextInt();
		for (int i = 0; i < sim.rooms; i++) {
			sim.emptyRooms.insert(new Room(i + 1));
		}
		System.out.print("\nRandom or preset? ");
		String s = scan.next().toLowerCase();
		sim.rand = (s.startsWith("rand")) ? true : false;
		return sim;
	}

}
