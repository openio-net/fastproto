package com.github.openio.fastproto.error;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/10/16
 */


public enum ErrorMessage {
	ERROR_OBJ_NULL("addObject", "object is null"),
	ERROR_NAME_NULL("getObjectByName", "Name is null"),
	ERROR_OPTIONAL_NULL("addOption","option is null"),
	ERROR_PACKAGE_NAME_NULL("setPackageName", "PackageName is null"),
	ERROR_PATH_NULL("addPublicPackage", "path is null"),
	ERROR_IMPORT_FILE_PATH_NULL("addImportFile", "path is null"),
	ERROR_GEN_FILE("genFile","can not creatFile:")
	
	
	;
	
	private String methodName;
	private String message;
	
	ErrorMessage(String methodName, String message) {
		this.methodName = methodName;
		this.message = message;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public String getMessage() {
		return message;
	}
}
