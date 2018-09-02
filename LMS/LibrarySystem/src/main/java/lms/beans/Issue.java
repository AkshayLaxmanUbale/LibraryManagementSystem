package lms.beans;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="issuecollection")
public class Issue {
	int rollno;
	int ISBN;
	String title;
	String issuedate;
	String returndate;
	int fine;
	int status; //0 for inactive and 1 for active
	int finestatus; //0 not paid and 1 paid
	
	public int getFinestatus() {
		return finestatus;
	}

	public void setFinestatus(int finestatus) {
		this.finestatus = finestatus;
	}

	public Issue() {
		super();
	}

	public Issue(int rollno, int iSBN, String title, String issuedate, String returndate, int fine,int status) {
		super();
		this.rollno = rollno;
		ISBN = iSBN;
		this.title = title;
		this.issuedate = issuedate;
		this.returndate = returndate;
		this.fine = fine;
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRollno() {
		return rollno;
	}

	public void setRollno(int rollno) {
		this.rollno = rollno;
	}

	public int getISBN() {
		return ISBN;
	}

	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public String getIssuedate() {
		return issuedate;
	}

	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}

	public String getReturndate() {
		return returndate;
	}

	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}
	
	
	
}
