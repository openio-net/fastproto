package net.openio.fastproto.wrapper;


import java.io.File;

public enum PackageType {
	
	JavaOption("java"),
	NetOption("net"),
	OptionIoOption("openio"),
	FastProtoOption("fastproto");
	
	String type;
	
	public String getType() {
		return this.type;
	}
	
	PackageType(String type){
		this.type=type;
	}
}
