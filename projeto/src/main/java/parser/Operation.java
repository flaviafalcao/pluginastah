package parser;

public class Operation {
	private String name_in;
	private String name_out;
	private String class_name;
	private String class_esteriotipo;
	

	public Operation(String name, String class_name ,String esteriotipo) {
		
		this.setName_in(name + "_I");
		this.setName_out(name + "_O");
		this.class_name = class_name;
		this.setClass_esteriotipo(esteriotipo);
	}


	public String getName_in() {
		return name_in;
	}


	public void setName_in(String name_in) {
		this.name_in = name_in;
	}


	public String getName_out() {
		return name_out;
	}


	public void setName_out(String name_out) {
		this.name_out = name_out;
	}


	public String getClass_name() {
		return class_name;
	}


	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}


	public String getClass_esteriotipo() {
		return class_esteriotipo;
	}


	public void setClass_esteriotipo(String class_esteriotipo) {
		this.class_esteriotipo = class_esteriotipo;
	}
	
	
	
}
