package parser;

import java.util.ArrayList;

public class Port {
	
	private String name;
	private String owner;
	private int  req_prov;

	public Port(String name, String owner, int req_prov){
		this.name = name;
		this.owner = owner;
		this.req_prov = req_prov;
	}
	
	public Port(String name){
		this.name = name;
		this.owner = "";
		this.req_prov = -2;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
/*	@Override
	public boolean equals(Object o) {
		
		if (this.getName() == ((Port)o).getName()) {
			return true;
	      }
			
		else return false;
		
	}
	  
	@Override
	 public int hashCode()
	    {
	        return this.getName().length() * 7;
	    }*/
	
	
	public static void main(String[] args) {
		
	
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getReq_prov() {
		return req_prov;
	}

	public void setReq_prov(int req_prov) {
		this.req_prov = req_prov;
	}
	
}
