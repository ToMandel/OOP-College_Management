package Controller;

import Model.Lecturer.academicDegree;
import Model.Model;
import Model.Organization;
import Model.PermanentLecturer.committee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Model.College;
import Model.HoursException;
import Model.Lecturer;
import View.View;

public class Controller {
	private Model model;
	private View view;
	private char lecType;
	private String chairman;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;

		try {
			ObjectInputStream inCollegeFile = new ObjectInputStream(new FileInputStream("college.dat"));
			model.collegeArr = (ArrayList<College>) inCollegeFile.readObject();
			inCollegeFile.close();
			ObjectInputStream inExternalOrgFile = new ObjectInputStream(new FileInputStream("external.dat"));
			model.external = (Organization) inExternalOrgFile.readObject();
			inExternalOrgFile.close();
			ObjectInputStream inPermOrgFile = new ObjectInputStream(new FileInputStream("permanent.dat"));
			model.permanent = (Organization) inPermOrgFile.readObject();
			inPermOrgFile.close();
			ObjectInputStream inLecturersFile = new ObjectInputStream(new FileInputStream("lecturers.dat"));
			model.lecArr = (ArrayList<Lecturer>) inLecturersFile.readObject();
			inLecturersFile.close();
			view.insertNewCol(model.collegeArr);

		} catch (FileNotFoundException e2) {
			model.collegeArr = new ArrayList<>();
			model.external = new Organization("External", null, null);
			model.permanent = new Organization("Permanent", null, null);
			model.lecArr = new ArrayList<>();
		} catch (IOException e2) {
		} catch (ClassNotFoundException e1) {
		}

		view.colInsertAction(e -> {
			view.setExFlag(false);
			try {
				model.NewCollege(model.collegeArr, view.getText1());
			} catch (Exception ex) {
				view.noCollege(ex.getMessage());
				view.setExFlag(true);
			}
			view.insertNewCol(model.collegeArr);
			view.clearFields();
		});

		view.lecInsertAction(e -> {
			try {
				view.setExFlag(false);
				if (getID() < 99999999)
					throw new Exception();
				if (getID() < 0 || getSalary() < 0 || getWorkTime() < 0 || getSeniority() < 0) {
					throw new IllegalArgumentException();
				} else {
					model.insertNewLecturer(model.lecArr, getLecType(), getName(), getID(), getSalary(), getSeniority(),
							getWorkTime(), getAcDegree(), getAreaOfExpertise(), getComm(), getCollege());
					view.clearFields();
				}

			} catch (HoursException ex) {
				view.hoursException(ex.getMessage());
				view.setExFlag(true);

			} catch (NumberFormatException ex) {
				view.invalidFormat();
				view.setExFlag(true);

			} catch (IllegalArgumentException ex) {
				view.negativeNumber();
				view.setExFlag(true);

			} catch (Exception ex) {
				view.invalidID();
				view.setExFlag(true);
			}
		});

		view.setChairAction(e -> {
			try {
				if (model.lecArr.size() != 0) {
					if (getLecType() == 'E') {
						chairman = model.setChairman(model.external, getID());
					} else {
						chairman = model.setChairman(model.permanent, getID());
					}
				} else {
					chairman = "The Lecturers Organizations are empty";
				}
				view.chairmanMessage(chairman);
				view.clearFields();
			} catch (Exception ex) {
				view.invalidID();
				view.setExFlag(true);
			}
		});

		view.addToOrgAction(e -> {
			try {
				if (model.lecArr.size() != 0) {
					view.addSuccess(getAddAnswer());
					view.clearFields();
				} else {
					view.addSuccess("The Lecturers Organizations are empty");
					view.clearFields();
				}
			} catch (Exception ex) {
				view.invalidID();
				view.setExFlag(true);
			}
		});

		view.removeFromOrgAction(e -> {
			try {
				if (model.lecArr.size() != 0) {
					view.removeSuccess(getRemoveAnswer());
					view.clearFields();
				} else {
					view.removeSuccess("The Lecturers Organizations are empty");
					view.clearFields();
				}
			} catch (Exception ex) {
				view.invalidID();
			}
		});

		view.compareAction(e -> {
			view.setPrint(getCompare());
			view.setText1(getPermOrg());
			view.setText2(getExtOrg());
		});

		view.sendMsgAction(e -> {
			view.sendEmail();
			view.clearFields();
		});

		view.sendGiftAction(e -> {
			try {
				view.sendGift(getGift());
			} catch (Exception ex) {
				view.chooseException();
			}
			view.clearFields();
		});

		view.printAction(e -> {
			view.setPrint(model.printList(getCollege()));
		});

		view.finishAction(e -> {
			try {
				ObjectOutputStream outCollegeFile = new ObjectOutputStream(new FileOutputStream("college.dat"));
				outCollegeFile.writeObject(model.collegeArr);
				outCollegeFile.close();
				ObjectOutputStream outExternalOrgFile = new ObjectOutputStream(new FileOutputStream("external.dat"));
				outExternalOrgFile.writeObject(model.external);
				outExternalOrgFile.close();
				ObjectOutputStream outPermOrgFile = new ObjectOutputStream(new FileOutputStream("permanent.dat"));
				outPermOrgFile.writeObject(model.permanent);
				outPermOrgFile.close();
				ObjectOutputStream outLecturersFile = new ObjectOutputStream(new FileOutputStream("lecturers.dat"));
				outLecturersFile.writeObject(model.lecArr);
				outLecturersFile.close();

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

	}

	public String getAddAnswer() throws Exception {
		return model.addLecToOrg(getID());
	}

	public String getRemoveAnswer() throws Exception {
		return model.removeLecFromOrg(getID());
	}

	public char getLecType() throws Exception {
		return view.lecUpdate();
	}

	public String getName() {
		return view.getText1();
	}

	public int getID() {
		return Integer.parseInt(view.getText2());
	}

	public int getSalary() {
		return Integer.parseInt(view.getText3());
	}

	public int getSeniority() {
		return Integer.parseInt(view.getText4());
	}

	public int getWorkTime() {
		return Integer.parseInt(view.getText4());
	}

	public String getAreaOfExpertise() {
		return (view.getText5());
	}

	public academicDegree getAcDegree() {
		return view.degreeUpdate();
	}

	public committee getComm() {
		return view.commUpdate();
	}

	public void setLecType(char lecType) {
		this.lecType = lecType;
	}

	public String getCompare() {
		return model.compareOrg(model.permanent.getMembers(), model.external.getMembers());
	}

	public String getGift() throws Exception {
		return model.happyHoliday(getLecType(), getCollege());
	}

	public String getPermOrg() {
		return String.valueOf(model.permanent.getMembers().size());
	}

	public String getExtOrg() {
		return String.valueOf(model.external.getMembers().size());
	}

	public College getCollege() {
		return view.collUpdate();
	}

}
