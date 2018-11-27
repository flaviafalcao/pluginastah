package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.change_vision.jude.api.inf.model.IConnector;



public class Declarations {
	
	//identificador de components
	
	private ArrayList<Name_id> nameid;
	private ArrayList<Port> portas;
	private ArrayList<Operation> operations;
	private ArrayList<Signal> signal;
	private ArrayList<Attribute> attributes;
	private ArrayList<PartType> parts;
	private HashSet<Instance> instances;
	private ArrayList<BasicComponent> basicComponent;
	private ArrayList<Input_Output> inputoutput;
	private ArrayList<SetSinc> setsinc;
	private ArrayList<ComponentType> componentType;
	private ArrayList<StateMachine> stateMachine;
	private ArrayList<AssertionConection> assertionConection;
			
	
	
	private static Declarations instance;
	
	public Declarations() {
		
		this.nameid = new ArrayList<Name_id>();
		this.portas = new ArrayList<Port>();
		this.operations = new ArrayList<Operation>();
		this.signal = new ArrayList<Signal>();
		this.parts = new ArrayList<PartType>();
		this.instances = new HashSet<Instance>();
		this.basicComponent = new ArrayList<BasicComponent>();
		this.inputoutput = new ArrayList<Input_Output>();
		this.setsinc = new ArrayList<SetSinc>();
		this.componentType = new ArrayList<ComponentType>();
		this.stateMachine = new ArrayList<StateMachine>();
		this.setAssertionConection(new ArrayList<AssertionConection>());
	}
	
	
	//singleton
	public static synchronized Declarations getInstance() {
		if(instance == null) {
			instance = new Declarations();
		}
		return instance;
		
		
	}

	public ArrayList<Name_id> getNameid() {
		return nameid;
	}

	public void setNameid(ArrayList<Name_id> nameid) {
		this.nameid = nameid;
	}

	public ArrayList<Port> getPortas() {
		return portas;
	}

	public void setPortas(ArrayList<Port> portas) {
		this.portas = portas;
	}

	public ArrayList<Operation> getOperations() {
		return operations;
	}

	public void setOperations(ArrayList<Operation> operations) {
		this.operations = operations;
	}

	public ArrayList<Signal> getSignal() {
		return signal;
	}

	public void setSignal(ArrayList<Signal> signal) {
		this.signal = signal;
	}
	
    
	public void addOperation(Operation op) {
		
		this.operations.add(op);
	}
	
	public void addPort(Port port) {
		if(!portas.contains(port)) {
		//	System.out.println("add ->" +  port.getName());
		this.portas.add(port);
		}else {
			//System.out.println(" nao add ->");
		}
	}


	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}


	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}


	public ArrayList<PartType> getPartes() {
		return parts;
	}


	public void setPartes(ArrayList<PartType> parts) {
		this.parts = parts;
	} 
	
	public String  getType(String name) {
		String retorno = "";
		String[] split1;
		for(int i = 0; i< parts.size(); i++) {
			
			
			if(parts.get(i).getName().equals(name)) {
				 retorno = parts.get(i).getType(); 
				 split1 = retorno.split("::");
				//System.out.println(parts.get(i).getType());
				int j = split1.length;
				//System.out.println(split1[j - 1]);
				retorno = split1[j - 1];
				
				return retorno;
			}
		}
		return retorno;
	}
	
	public int getId(String name) {
		Iterator i = this.instances.iterator();
		Instance temp;
		int retorno = 0;
		while(i.hasNext()) {
			
			temp = (Instance)i.next();
			
			if(temp.getName().equals(name)){
				
				retorno = temp.getNum_id();
				return retorno;				
			}
       }
	  return  retorno;	
		
	}
	
	
	public String getPortType(String name) {
		Iterator i = this.instances.iterator();
		Instance temp;
		String retorno = "";
		while(i.hasNext()) {
			
			temp = (Instance)i.next();
			
			if(temp.getName().equals(name)){
				
				retorno = temp.getType();
				return retorno;				
			}
       }
	  return  retorno;	
		
	}
	
	//metodo que me diz se uma porta eh provided ou required
	
	public int getIsProvReq(String portName) {
		int retorno = -1;
		
		for(int i = 0; i< this.portas.size();i++) {
			
			if(this.portas.get(i).getName().equalsIgnoreCase(portName)) {
				if( this.portas.get(i).getReq_prov()!= (-1)) {
					return this.portas.get(i).getReq_prov();
				}
			}
			
			
		}
		
		return retorno;
		
	}



	public HashSet<Instance> getInstances() {
		return instances;
	}


	public void setInstances(HashSet<Instance> instances) {
		this.instances = instances;
	}


	public ArrayList<BasicComponent> getBasicComponent() {
		return basicComponent;
	}


	public void setBasicComponent(ArrayList<BasicComponent> basicComponent) {
		this.basicComponent = basicComponent;
	}
	
	
	
	public ArrayList<Operation> getClassOperation(String class_name){
		 ArrayList<Operation> classOperation = new ArrayList<Operation>();
		 ArrayList<Operation> allClassOperation;
		 allClassOperation = this.getOperations();
		 
		 for(int i = 0; i <allClassOperation.size(); i++) {
			 if (allClassOperation.get(i).getClass_name().equalsIgnoreCase(class_name)){
				 classOperation.add(allClassOperation.get(i));
			 }
			 
		 }
		 return classOperation;
	}


	public ArrayList<Input_Output> getInputoutput() {
		return inputoutput;
	}


	public void setInputoutput(ArrayList<Input_Output> inputoutput) {
		this.inputoutput = inputoutput;
	}
	
	// inputs por tipo
	
	public ArrayList<String> getInput(String name){
		ArrayList<String> inputs = new ArrayList<String>();
		
		for(int i = 0; i< this.inputoutput.size();i++) {
			if(this.inputoutput.get(i).getInstance_name().equalsIgnoreCase(name)) {
				inputs.add(this.inputoutput.get(i).getInputs_name());
			}
		}
		return inputs;
		
	}
	
	public ArrayList<String> getOutput(String name){
		ArrayList<String> outputs = new ArrayList<String>();
		
		for(int i = 0; i< this.inputoutput.size();i++) {
			if(this.inputoutput.get(i).getInstance_name().equalsIgnoreCase(name)) {
				outputs.add(this.inputoutput.get(i).getOutputs_name());
			}
		}
		return outputs;
		
	}
	
	


	public ArrayList<SetSinc> getSetsinc() {
		return setsinc;
	}


	public void setSetsinc(ArrayList<SetSinc> setsinc) {
		this.setsinc = setsinc;
	}


	public ArrayList<ComponentType> getComponentType() {
		
		return componentType;
	}


	public void setComponentType(ArrayList<ComponentType> componentType) {
		this.componentType = componentType;
	}
	
	
	public int countComponentType(String name) {
		int retorno = 1;
		
		
		if(componentType.size()==0 || !contemType(name)) {
			
			ComponentType temp = new ComponentType(name,1);
			this.componentType.add(temp);
			
		} else
			for(int i =0 ; i < componentType.size() ;i++) {
				
				if (componentType.get(i).getType().equalsIgnoreCase(name)) {
					 retorno = componentType.get(i).getCount() +1;
				}
			}
			
		
		return retorno;
	}
	
	
	public boolean contemType(String name) {
		
		boolean retorno = false;
		
		
		
		for( int i =0 ; i < componentType.size();i++) {
		
			if (componentType.get(i).getType().equalsIgnoreCase(name)) {
				retorno = true;
			}
		
	 }
		
		return retorno;
	
}
	
	//retorna as instanacia do tipo passado como parametro 
	
	public ArrayList<String> getInstancebyType(String tipo){
		
		ArrayList<String>  instancias =  new ArrayList<String>();
		
		Iterator<Instance> i = this.instances.iterator();
		Instance temp;
		
		while(i.hasNext()) {
            temp = (Instance)i.next();
			
			if(temp.getType().equals(tipo)){
				
				instancias.add(temp.getName());
								
			}
		
		
		}
		return instancias;
		
		
	}
	public ArrayList<StateMachine> getStateMachine() {
		return stateMachine;
	}


	public void setStateMachine(ArrayList<StateMachine> stateMachine) {
		this.stateMachine = stateMachine;
	}
	
	//retorna a maquina de estados de determinado componente
	
	public String getSTMbytype(String str) {
		String stm ="";
		ArrayList<StateMachine> machine = this.getStateMachine();
		for(StateMachine tmp: machine) {
			
			if( tmp.getComponent_name().equalsIgnoreCase(str)) {
				
				stm = tmp.getStm_str();
				break;
			}
				
			
		}
		
		return stm;
	}

	
	//retorno a IConnection do aquivo passado como parametro
	
	public IConnector getIConnector(String file) {
		
		IConnector con =null;
		
		ArrayList<AssertionConection> assertion = this.getAssertionConection();
		
		for(AssertionConection tmp: assertion) {
			
			if(tmp.getFile().equalsIgnoreCase(file)) {
				
				con = tmp.getConn();
				break;
			}
		}
		
		
		return con;
		
	}

	public ArrayList<AssertionConection> getAssertionConection() {
		return assertionConection;
	}


	public void setAssertionConection(ArrayList<AssertionConection> assertionConection) {
		this.assertionConection = assertionConection;
	}
}




	
