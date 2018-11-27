package parser;

public class BasicComponent {

	private String name;
	private String stm;
	private String memory;
	
	
	
	public BasicComponent(String name, String stm) {
		
		this.name = name;
		this.stm = stm;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStm() {
		return stm;
	}
	public void setStm(String stm) {
		this.stm = stm;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
}
