package fr.uv1.bettingServices.exceptions;

public class ExistingCompetitionException extends Exception {
	public ExistingCompetitionException() {
		super();
	}

	public ExistingCompetitionException(String reason) {
		super(reason);
	}
}
