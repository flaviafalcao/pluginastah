package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class InputTypeDeclaration {
	
	public String inputType() {
		
		//
		Declarations declaration = Declarations.getInstance();
		ArrayList<Input_Output> io = declaration.getInputoutput();
		HashSet<String> types = new HashSet<String>();
		String inputs_str = "";
		ArrayList<String> tmp = new ArrayList<String>();
		
		for (int i =0 ; i< io.size(); i ++) {
			
			types.add(io.get(i).getInstance_name());
		}
		
		Iterator<String> it = types.iterator();
		
		while(it.hasNext()) {
			
			tmp.add((String)it.next());	
			
		}
		
		ArrayList<String> inputs;
		for(int j =0 ; j<tmp.size(); j++) {
			
			inputs = declaration.getInput(tmp.get(j));
			String inputs_str_in = " ";
			for( int k =0 ; k < inputs.size() ; k ++) {
				
				inputs_str_in = inputs_str_in + inputs.get(k) + "  "; 
				
				if (k < (inputs.size() -1))
					inputs_str_in = inputs_str_in + ",";
			} 
		
			inputs_str = inputs_str +   "inputs_"  + tmp.get(j) + " =  union ( " + inputs_str_in + " ) \n";  
		}
		
		
		
		System.out.println(inputs_str);
		
		return inputs_str;
	}

	
	public String outputType() {
		//
		Declarations declaration = Declarations.getInstance();
		ArrayList<Input_Output> io = declaration.getInputoutput();
		HashSet<String> types = new HashSet<String>();
		String outputs_str = "";
		ArrayList<String> tmp = new ArrayList<String>();
		
		for (int i =0 ; i< io.size(); i ++) {
			
			types.add(io.get(i).getInstance_name());
		}
		
		Iterator<String> it = types.iterator();
		
		while(it.hasNext()) {
			
			tmp.add((String)it.next());	
			
		}
		
		ArrayList<String> outputs;
		for(int j =0 ; j<tmp.size(); j++) {
			
			outputs = declaration.getOutput(tmp.get(j));
			String outputs_str_in = " ";
			for( int k =0 ; k < outputs.size() ; k ++) {
				
				outputs_str_in = outputs_str_in + outputs.get(k) + "  "; 
				
				if (k < (outputs.size() -1))
					outputs_str_in = outputs_str_in + ",";
			} 
		
			outputs_str = outputs_str +   "outputs_"  + tmp.get(j) + " =  union ( " + outputs_str_in + " ) \n";  
		}
		
		
		
		System.out.println(outputs_str);
		
		return outputs_str;
	}
	
	
	public String inputAll() {
		
		Declarations declaration = Declarations.getInstance();
		ArrayList<Input_Output> io = declaration.getInputoutput();
		HashSet<String> types = new HashSet<String>();
		String inputs_str = "";
		ArrayList<String> tmp = new ArrayList<String>();
		
		for (int i =0 ; i< io.size(); i ++) {
			
			types.add(io.get(i).getInstance_name());
		}
		
		Iterator it = types.iterator();
		
		while(it.hasNext()) {
			
			tmp.add((String)it.next());	
			
		}
		
		ArrayList<String> inputs;
		String inputs_str_in = " ";
		for(int j =0 ; j<tmp.size(); j++) {
			
			//inputs = declaration.getInput(tmp.get(j));
			
			inputs_str_in =  inputs_str_in + "inputs_"  + tmp.get(j) + " " ;
			
			//for( int k =0 ; k < inputs.size() ; k ++) {
				
				//inputs_str_in = inputs_str_in + inputs.get(k) + "  "; 
				
				if (j<tmp.size() -1)
					inputs_str_in = inputs_str_in + ",";
			//} 
		
			inputs_str =  "inputs_all  =  union ( " + inputs_str_in + " ) \n";  
		}
		
		
		
		System.out.println(inputs_str);
		
		return inputs_str;

		
		
	}
	
	public String outputAll() {
		
		Declarations declaration = Declarations.getInstance();
		ArrayList<Input_Output> io = declaration.getInputoutput();
		HashSet<String> types = new HashSet<String>();
		String outputs_str = "";
		ArrayList<String> tmp = new ArrayList<String>();
		
		for (int i =0 ; i< io.size(); i ++) {
			
			types.add(io.get(i).getInstance_name());
		}
		
		Iterator<String> it = types.iterator();
		
		while(it.hasNext()) {
			
			tmp.add((String)it.next());	
			
		}
		
		ArrayList<String> outputs;
		String outputs_str_in = " ";
		for(int j =0 ; j<tmp.size(); j++) {
			
			outputs_str_in =  outputs_str_in + "outputs_"  + tmp.get(j) + " " ;
				
				if ((j<tmp.size() -1))
					outputs_str_in = outputs_str_in + ",";
		
		
			outputs_str =  "outputs_all  =  union ( " + outputs_str_in + " ) \n";  
		}
		
		
		
		System.out.println(outputs_str);
		
		return outputs_str;

		
		
	}


	
}
