package exception;

public class VotacaoNotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	public VotacaoNotFoundException (String message, Throwable cause ) {
		
	}

	public VotacaoNotFoundException(String message) {
		super("O funcionário já votou na data de hoje no mesmo restaurante");
		
	}
	


}
