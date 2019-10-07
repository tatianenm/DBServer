package exception;

public class RestauranteNotFoundException extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;

	public RestauranteNotFoundException(String message, Throwable cause) {
		
	}
	
	public RestauranteNotFoundException() {
		super("Restaurante não encontrado.");
	}

}
