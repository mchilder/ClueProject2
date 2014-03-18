package clueGame;

public class BadConfigFormatException extends RuntimeException {
	private String message;

	public BadConfigFormatException(String msg) {
		super(msg);

	}
}
