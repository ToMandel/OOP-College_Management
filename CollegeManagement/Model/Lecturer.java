package Model;

import java.io.Serializable;

public class Lecturer implements Serializable{
	private static final long serialVersionUID = 1L;

	public enum academicDegree {
		First, Second, Doctor, Professor
	};

	private College coll;
	private String name;
	private int id;
	private String areaOfExpertise;
	private academicDegree acDegree;
	private int salary;

	public Lecturer(College coll, String name, int id, int salary, academicDegree acDegree, String areaOfExpertise) {
		this.coll = coll;
		this.name = name;
		this.id = id;
		this.salary = salary;
		this.acDegree = acDegree;
		this.areaOfExpertise = areaOfExpertise;

	}

	public College getColl() {
		return coll;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getAreaOfExpertise() {
		return areaOfExpertise;
	}

	public academicDegree getAcDegree() {
		return acDegree;
	}

	public static int getHappyHoliday() {
		return 0;
	}

	public int getSalary() {
		return salary;
	}

	@Override
	public String toString() {
		return String.format("Name: %s\nAcademic Degree: %s\nArea Of Expertise: %s\n", name, acDegree, areaOfExpertise);
	}
}
