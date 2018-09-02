package lms.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lms.beans.Issue;
import lms.services.IssueService;

@RestController
public class ReturnBookController {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	IssueService issueService;
	
	@RequestMapping(value = "/returnbook", method = RequestMethod.POST)
	public Issue returnBook(@RequestBody Issue issue) {
		
		return issueService.updateReturnStatus(issue);
	}
	
	@RequestMapping(value="/fine", method = RequestMethod.POST)
	public Issue calcFine(@RequestBody Issue issue) {
		
		return issueService.getFine(issue);
		
	}
}
