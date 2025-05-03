package com.nayak.courseEnrolmentsManagementSystem.exception;

public class InstructorIdNotFoundException extends RuntimeException {

	@Override
	public String getMessage() {
		return "Instructor Id Not Found ";
	}
}