package org.huel.beasp.exception;
/**
 * 前端异常处理
 * @author 001
 *
 */
public class BeaspFrontException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8379113335527166226L;

	public BeaspFrontException() {
		super();
	}

	public BeaspFrontException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BeaspFrontException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeaspFrontException(String message) {
		super(message);
	}

	public BeaspFrontException(Throwable cause) {
		super(cause);
	}

}
