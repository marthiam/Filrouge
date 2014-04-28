package fr.uv1.bettingServices.exceptions;

public class CompetitionException extends Exception{
	public CompetitionException(){
		super();
	}
	public CompetitionException(String reason){
		super(reason);
	}	
}
