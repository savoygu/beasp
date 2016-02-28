package org.huel.beasp.exception;
/**
 * 后台异常处理
 * @author 001
 *
 */
public class BeaspException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8567822001909748683L;

	public BeaspException() {
		super();
	}

	public BeaspException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BeaspException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeaspException(String message) {
		super(message);
	}

	public BeaspException(Throwable cause) {
		super(cause);
	}

}
