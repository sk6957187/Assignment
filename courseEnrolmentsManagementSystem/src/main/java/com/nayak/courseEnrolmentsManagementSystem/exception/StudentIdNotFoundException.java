package com.nayak.courseEnrolmentsManagementSystem.exception;

public class StudentIdNotFoundException extends RuntimeException {

	@Override
	public String getMessage() {
		return "Student Id Not Found ";
	}
}
