package lms.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="student")
public class Student {
	
	@Id
	int rollno;
	String name;
	String major;
	int year;
	int div;
	
	@Override
	public String toString() {
		return "Student [rollno=" + rollno + ", name=" + name + ", major=" + major + ", year=" + year + ", div=" + div
				+ "]";
	}

	public Student() {
		super();
	}

	public Student(int rollno, String name, String major, int year, int div) {
		super();
		this.rollno = rollno;
		this.name = name;
		this.major = major;
		this.year = year;
		this.div = div;
	}

	public int getRollno() {
		return rollno;
	}

	public void setRollno(int rollno) {
		this.rollno = rollno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDiv() {
		return div;
	}

	public void setDiv(int div) {
		this.div = div;
	}
	
	
	
	
}
