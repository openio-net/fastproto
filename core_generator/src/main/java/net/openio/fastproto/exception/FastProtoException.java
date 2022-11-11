package net.openio.fastproto.exception;


public class FastProtoException extends RuntimeException {
	
	public FastProtoException() {
	}
	
	public FastProtoException(String message) {
		super(message);
	}
	
	public static class AttributeNameConflictException extends FastProtoException {
		
		public AttributeNameConflictException() {
		}
		
		public AttributeNameConflictException(String message) {
			super(message);
		}
	}
	
	
	public static class FailToMakeDirException extends FastProtoException {
		public FailToMakeDirException() {
		}
		
		public FailToMakeDirException(String message) {
			super(message);
		}
	}
	
	public static class FailToCreateFileException extends FastProtoException {
		public FailToCreateFileException() {
		}
		
		public FailToCreateFileException(String message) {
			super(message);
		}
	}

	
	
}
