package Model;

import java.io.Serializable;

public class PermanentLecturer extends Lecturer implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static int HAPPY_HOLIDAY = 500;
	private int seniority;

	public enum committee {
		Exceptions, Teaching, Improvement, Other
	};

	private committee comm;

	public PermanentLecturer(College coll, String name, int id, int salary, int seniority, academicDegree acDegree,
			String areaOfExpertise, committee comm) {
		super(coll, name, id, salary, acDegree, areaOfExpertise);
		this.seniority = seniority;
		this.comm = comm;
	}

	public int getSeniority() {
		return seniority;
	}

	public committee getComm() {
		return comm;
	}

	public static int getHappyHoliday() {
		return HAPPY_HOLIDAY;
	}

	@Override
	public String toString() {
		return "Permanent Lecturer: \n" + super.toString() + String.format("Seniority: %d\nCommitee: %s", seniority, comm);
	}
}