package Model;

import java.util.ArrayList;
import Model.Lecturer.academicDegree;
import Model.PermanentLecturer.committee;

public class Model {

	public ArrayList<Lecturer> lecArr;
	public Organization permanent;
	public Organization external;
	public ArrayList<College> collegeArr;

	public void NewCollege(ArrayList<College> collArr, String colName) throws Exception {
		if (colName.isEmpty() || colName.contains(" ")) {
			throw new Exception("Wrong input please enter college Name!");
		}
		College c = new College(colName);
		collArr.add(c);
	}

	public void insertNewLecturer(ArrayList<Lecturer> lecArr, char lecType, String name, int id, int salary,
			int seniority, int workTime, academicDegree acDegree, String areaOfExpertise, committee comm, College coll)
			throws HoursException {

		switch (lecType) {

		case 'P': {
			PermanentLecturer p = new PermanentLecturer(coll, name, id, salary, seniority, acDegree, areaOfExpertise,
					comm);
			lecArr.add(p);
			permanent.getMembers().add(p);
			break;
		}

		case 'E': {
			ExternalLecturer e = new ExternalLecturer(coll, name, id, salary, workTime, acDegree, areaOfExpertise);
			lecArr.add(e);
			external.getMembers().add(e);
			break;
		}

		}
	}

	public String happyHoliday(char lecType, College coll) {
		switch (lecType) {
		case 'P': {
			return "Happy Holiday, you got " + PermanentLecturer.getHappyHoliday() + " ILS for the Holiday from "
					+ coll.getColName() + " College";
		}
		case 'E': {
			return "Happy Holiday, you got " + ExternalLecturer.getHappyHoliday() + " ILS for the Holiday from "
					+ coll.getColName() + " College";
		}
		}
		return "";
	}

	public String printList(College coll) {
		String print = "";
		for (int i = 0; i < lecArr.size(); i++) {
			if (coll.getColName().equals(lecArr.get(i).getColl().getColName())) {
				print += lecArr.get(i).toString() + "\n";
			}
		}
		if (print.isEmpty()) {
			return "There are no Lecturers in the college";
		}
		return print;
	}

	public String addLecToOrg(int id) throws Exception {
		if (id < 99999999) {
			throw new Exception();
		}
		for (int i = 0; i < lecArr.size(); i++) {
			if (lecArr.get(i).getId() == id) {
				if (lecArr.get(i).getClass().getSimpleName().equals("PermanentLecturer")) {
					for (int j = 0; j < permanent.getMembers().size(); j++) {
						if (id == permanent.getMembers().get(j).getId()) {
							return "This lecturer is already in the Permanent Organization";
						}
					}
					permanent.getMembers().add(lecArr.get(i));
					return "The Lecturer was added successfully to the Permanent Lecturers Organization";

				} else {
					for (int j = 0; j < external.getMembers().size(); j++) {
						if (id == external.getMembers().get(j).getId()) {
							return "This lecturer is already in the External Organization";
						}
					}
					external.getMembers().add(lecArr.get(i));
					return "The Lecturer was added successfully to the External Lecturers Organization";
				}
			}
		}
		return "There is no such lecturer in any of the Organizations";
	}

	public String removeLecFromOrg(int id) throws Exception {
		if (id < 99999999) {
			throw new Exception();
		}
		for (int i = 0; i < lecArr.size(); i++) {
			if (lecArr.get(i).getId() == id) {
				if (lecArr.get(i).getClass().getSimpleName().equals("PermanentLecturer")) {
					for (int j = 0; j < permanent.getMembers().size(); j++) {
						if (id == permanent.getMembers().get(j).getId()) {
							permanent.getMembers().remove(lecArr.get(i));
							return "The Lecturer was removed successfully from the Permanent Lecturers Organization";
						}
					}
					return "The Lecturer was already removed from the Permanent Organization";

				} else {
					for (int j = 0; j < external.getMembers().size(); j++) {
						if (id == external.getMembers().get(j).getId()) {
							external.getMembers().remove(lecArr.get(i));
							return "The Lecturer was removed successfully from the External Lecturers Organization";
						}
					}
					return "The Lecturer was already removed from the External Organization";
				}
			}
		}
		return "There is no such lecturer in any of the Organizations";
	}

	public String setChairman(Organization org, int id) throws Exception {
		if (id < 99999999) {
			throw new Exception();
		}
		for (int i = 0; i < org.getMembers().size(); i++) {
			if (org.getMembers().get(i).getId() == id) {
				org.setChairman(org.getMembers().get(i));
				return "Congratulations " + org.getMembers().get(i).getName() + "!\n Your'e the new Chairman of the "
						+ org.getOrgName() + " Lecturer Organization";
			}
		}
		return "There is no such lecturer";
	}

	public String compareOrg(ArrayList<Lecturer> permOrg, ArrayList<Lecturer> extOrg) {
		if (permOrg.size() > extOrg.size())
			return "The Permanent Lecturer Organization is Bigger";
		else if (extOrg.size() > permOrg.size())
			return "The External Lecturer Organization is Stronger";
		return "Both Organizations are even";
	}

}