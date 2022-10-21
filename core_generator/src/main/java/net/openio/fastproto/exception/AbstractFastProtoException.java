package net.openio.fastproto.exception;


public abstract class AbstractFastProtoException extends RuntimeException {
	
	/**
	 * errorData
	 */
	private Object errorData;
	
	
	public AbstractFastProtoException() {
	}
	
	public AbstractFastProtoException(String message) {
		super(message);
	}
	
	public Object getErrorData() {
		return errorData;
	}
	
	/**
	 * set errorData
	 * @param errorData
	 * @return
	 */
	public AbstractFastProtoException setErrorData(Object errorData) {
		this.errorData = errorData;
		return this;
	}
}
