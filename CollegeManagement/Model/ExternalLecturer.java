package Model;

import java.io.Serializable;

public class ExternalLecturer extends Lecturer implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static int HAPPY_HOLIDAY = 350;
	private int workTime;

	public ExternalLecturer(College coll, String name, int id, int salary, int workTime, academicDegree acDegree,
			String areaOfExpertise) throws HoursException {
		super(coll, name, id, salary, acDegree, areaOfExpertise);
		this.workTime = workTime;
		if (workTime > 14)
			throw new HoursException(workTime);
	}

	public int getWorkTime() {
		return workTime;
	}

	public static int getHappyHoliday() {
		return HAPPY_HOLIDAY;
	}

	@Override
	public String toString() {
		return "External Lecturer: \n" + super.toString() + String.format("Working time: %d", workTime);
	}
}