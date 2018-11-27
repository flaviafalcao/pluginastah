package parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.change_vision.jude.api.inf.model.IConnector;
import com.change_vision.jude.api.inf.model.IPort;

public class Composition {
	
	public int getTypeInterface(IPort port) {
		
		int retorno = -1;
		if( port.getRequiredInterfaces().length >0  ) {
			retorno = 1;
		}
		else if( port.getProvidedInterfaces().length >0  ) {
			retorno = 0;
		} 
	  	return retorno;
	}
	

	
public void firstConection(IConnector connector ) {
		
		Declarations declaration = Declarations.getInstance();
		ArrayList<Port> ports = declaration.getPortas();
		ArrayList<SetSinc> setsinc = declaration.getSetsinc();
			
		SetSinc set_sinc = new SetSinc(connector, connector.getPorts()[0].getName() +  "." +
		                               declaration.getId(connector.getPartsWithPort()[0].getName()),
		                               connector.getPorts()[1].getName() +  "." +
				                       declaration.getId(connector.getPartsWithPort()[1].getName()));
		setsinc.add(set_sinc);
		
		System.out.println(connector.getPorts()[0].getName() +  "." +
        declaration.getId(connector.getPartsWithPort()[0].getName()));
		System.out.println(connector.getPorts()[1].getName() +  "." +
        declaration.getId(connector.getPartsWithPort()[1].getName()));
		
		
		Port temp_portA = new Port(connector.getPorts()[0].getName(),declaration.getPortType(connector.getPartsWithPort()[0].getName()),getTypeInterface(connector.getPorts()[0]));  
		Port temp_portB = new Port(connector.getPorts()[1].getName(),declaration.getPortType(connector.getPartsWithPort()[1].getName()),getTypeInterface(connector.getPorts()[1]));
		ports.add(temp_portA);
		ports.add(temp_portB);
		
		
	}
	
  //*******************************************************************************************


public String getInputComponent() {
	
	String InputComponent ="";
	Declarations declaration = Declarations.getInstance();
	//ArrayList<Port> ports = declaration.getPortas();
	ArrayList<Input_Output> inputoutput = declaration.getInputoutput();
	//lista de instancias
	HashSet<Instance>  instances = declaration.getInstances();
	Iterator it = instances.iterator();
	HashSet<String> ports_comp;
	Iterator<String> it_str;
	
	while(it.hasNext()) {
		 String temp ="";
		Instance instance_temp = (Instance)it.next();
		System.out.print("input_" + instance_temp.getName() + "= {|");
		InputComponent = InputComponent + "input_" + instance_temp.getName() + "= {|";
		
		String input_name = "input_" + instance_temp.getName();
		String output_name = "output_" + instance_temp.getName();
		Input_Output temp_i_o = new  Input_Output(instance_temp.getType(), input_name, output_name);
		inputoutput.add(temp_i_o);
		
		
		ports_comp = getPortComponent(instance_temp.getType());
		it_str = ports_comp.iterator();
		String str_temp ="";
		String str_optype ="";
		while(it_str.hasNext()){
			
			 temp = (String)it_str.next() ;
			str_temp =  str_temp + temp+ "."+ declaration.getId(instance_temp.getName()) + ".op";
			if(it_str.hasNext()) {str_temp = str_temp + ", ";}
		
		}
		//interface provide or required ?
		
		System.out.print(str_temp + "|"	+ "op:");
		InputComponent = InputComponent + str_temp + "|"	+ "op:";
		
		if(declaration.getIsProvReq(temp)==1) {
			
			str_optype = declaration.getType(instance_temp.getName()) +"_O" ;
			//System.out.println(str_optype);
			
		}
		else
			if(declaration.getIsProvReq(temp)==0) {
				
				str_optype = declaration.getType(instance_temp.getName()) +"_I" ;	
			}
		
       System.out.println(str_optype +	"|}");
       InputComponent = InputComponent + str_optype +	"|}  \n";
     //  System.out.println();
		//lista de portas
	} 
	
	
	
	
	return InputComponent;
}

//observar dependencia de c.firstConection

public String getOutputComponent() {
	
	String outputComponent ="";
	Declarations declaration = Declarations.getInstance();
	ArrayList<Port> ports = declaration.getPortas();
	//lista de instancias
	HashSet<Instance>  instances = declaration.getInstances();
	Iterator it = instances.iterator();
	HashSet<String> ports_comp;
	Iterator<String> it_str;
	
	while(it.hasNext()) {
		String temp ="";
		Instance instance_temp = (Instance)it.next();
		System.out.print("output_" + instance_temp.getName() + "= {|");
		outputComponent = outputComponent
				       + " output_" + instance_temp.getName() + "= {|";
		
		//System.out.println (instance_temp.getType());
		
		ports_comp = getPortComponent(instance_temp.getType());
		it_str = ports_comp.iterator();
		String str_temp ="";
		String str_optype ="";
		while(it_str.hasNext()){
			 temp = (String)it_str.next() ;
			str_temp =  str_temp + temp+ "."+ declaration.getId(instance_temp.getName()) + ".op";
			if(it_str.hasNext()) {
				str_temp = str_temp + ", ";
			//	outputComponent = outputComponent + str_temp;
				}
		
		}
		
		
		System.out.print(str_temp + "|" + "op:");
		outputComponent = outputComponent + str_temp + "|" + "op:";
		
		if(declaration.getIsProvReq(temp)==1) {
			
			str_optype = declaration.getType(instance_temp.getName()) +"_I" ;
			//System.out.println(str_optype);
			
		}
		else
			if(declaration.getIsProvReq(temp)==0) {
				
				str_optype = declaration.getType(instance_temp.getName()) +"_O" ;	
			}
		
       System.out.println(str_optype +	"|}");
       outputComponent = outputComponent + str_optype +	"|} \n";
		
		//lista de portas
	} 
	
	
	
	
	
	return outputComponent;
}

public HashSet<String> getPortComponent(String componentName) {
	
	HashSet<String> retorno = new HashSet<String>() ;
	Declarations declaration = Declarations.getInstance();
	ArrayList<Port> portas = declaration.getPortas();
    for(int i =0; i <portas.size();i++) {
    
    
    	if(portas.get(i).getOwner().equalsIgnoreCase(componentName)){
    	  retorno.add(portas.get(i).getName());
    		}
    }
	return retorno;
}

/**********************************************/


	public  String interleaveProcess() {	
		
		Declarations declaration = Declarations.getInstance();
		ArrayList<PartType> instances =  declaration.getPartes();
		HashSet<String> hash = new HashSet<String>();
	
		for(int i =0 ; i < instances.size();i++) {
			hash.add(instances.get(i).getName());
			}
		Iterator iterator = hash.iterator();
		String process ="";
		
		while(iterator.hasNext()) {
			String name = (String)iterator.next();
			
			process = process  + name ;		
			if(iterator.hasNext()) {
				process = process + "|||";
			}
		}
		 process =  process;
		
		return process;
		//System.out.println(process);
	}
	
	public String doComposition() throws IOException {
		
		String composition ="";
		String assertion1 = "";
		String assertion2 = "";
		String assertion3 = "";
		String assertion4 = "";
		
		Declarations declaration = Declarations.getInstance();
		ArrayList<SetSinc> setsinc = declaration.getSetsinc();
		ArrayList<AssertionConection>  assertConn = declaration.getAssertionConection();
		String processcomp = "processcomp";
		String processoanteriorname = "";
		for( int i= 0; i<setsinc.size() ;i ++ ) {
			if(i==0) {
			composition = processcomp + " = " +  "(" + interleaveProcess() + ")" + "\n "+  "[|{|"  + setsinc.get(0).getChannel1() +"," +  setsinc.get(0).getChannel2()  + "|}|]" +"\n"
				+	"BFIO_INIT(" + setsinc.get(0).getChannel1() +"," +  setsinc.get(0).getChannel2() +")"  + "\n \n"   ;
			
			processoanteriorname = processcomp;
			processcomp = processcomp + "_com";
			
			assertion1 =  assertion1 + assertion1(processoanteriorname, setsinc.get(0).getChannel1(), setsinc.get(0).getChannel2()); 
			assertion2 = assertion2 + assertion2(processoanteriorname, setsinc.get(0).getChannel1(), setsinc.get(0).getChannel2());
			assertion3 = assertion3 +assertion3(processoanteriorname, setsinc.get(0).getChannel1(), setsinc.get(0).getChannel2());
			assertion4 = "assert " +  processoanteriorname + ":[deadlock free [FD]] \n";
			
			
			String filename = "assertion"+ i + ".csp";
			
			AssertionConection  ac = new AssertionConection(filename, setsinc.get(0).getConn());
			
			assertConn.add(ac);
			
			// a cada conection colocar num novo arquivo 
			FileWriter arquivo; 
			String write =  "include \"modelo.csp\" \n" + assertion1 + assertion2 + assertion3 + assertion4;
			
			
			
			arquivo = new FileWriter(new File("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/"+filename));  
			arquivo.write(write);
			arquivo.close();
			
			
			// criar assertion passando o nome da composicao e canais
		    
			}
			else {
				
				composition	 = composition +  processcomp +  " = " + processoanteriorname	+ "\n" +
						"[|{|"  + setsinc.get(i).getChannel1() +"," +  setsinc.get(i).getChannel2()  + "|}|]" +"\n" +
						"BFIO_INIT(" + setsinc.get(i).getChannel1() +"," +  setsinc.get(i).getChannel2() +")"  + "\n \n"   ;
				
				processoanteriorname = processcomp;
				processcomp = processcomp + "_com";
				
			assertion1 =  assertion1(processoanteriorname, setsinc.get(i).getChannel1(), setsinc.get(i).getChannel2()); 
			assertion2 =  assertion2(processoanteriorname, setsinc.get(i).getChannel1(), setsinc.get(i).getChannel2());
			assertion3 =  assertion3(processoanteriorname, setsinc.get(i).getChannel1(), setsinc.get(i).getChannel2());
			assertion4 =   "assert " +  processoanteriorname + ":[deadlock free [FD]] \n";
			
				
			
			// a cada conection colocar num novo arquivo 
			FileWriter arquivo; 
			String write = "include \"modelo.csp\" \n" + assertion1 + assertion2 + assertion3 + assertion4;
						
			String filename = "assertion"+ i + ".csp";
			
			AssertionConection  ac = new AssertionConection(filename, setsinc.get(i).getConn());
			
			assertConn.add(ac);
			
			arquivo = new FileWriter(new File("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/"+filename));  
			arquivo.write(write);
			arquivo.close();
					
			
			}
				
				
		}
		return composition ; //+ assertion1 + assertion2 + assertion3 + assertion4 ;
	}
	
	
	//assertiva/
	/*
	 * Verifica se a uma renomeacao do protocolo  deterministica.
	 */
	
	public String assertion1(String comp, String c1, String c2) {
		
		String assertion1 = "";
		
		
		// assertiva
		assertion1 = assertion1 + "\n"+
		             "assert InBufferProt_" + comp +  "_" + splitChannel(c1) + "_" + splitChannel(c2) + "( "+ c1 + " ) :[deterministic [F]] \n" +
				     "assert InBufferProt_" + comp +  "_" + splitChannel(c2) + "_" + splitChannel(c1) +"( "+ c2 + " ) :[deterministic [F]] \n  \n"   ;
		
		// definicao da funcao InBufferProt_
		
		
		
		assertion1 = assertion1 + InBufferProt_def(comp, c1 , c2);
		assertion1 = assertion1 + protocolImp(comp);
		assertion1 = assertion1 + protocolImpChannel(comp, c1 , c2);
		assertion1 = assertion1 + protocolImpRename(comp, c1 , c2);
		assertion1 = assertion1 + r_io_process( comp, c1, c2);
		assertion1 = assertion1 + inputs_r_io( comp, c1, c2);
		assertion1 = assertion1 + outputs_R_IO(comp, c1, c2);
		assertion1 = assertion1 + inputs_r_io_prot_imp(comp, c1, c2);
		assertion1 = assertion1 + outputs_prot_imp_rio( comp, c1,  c2);
		
		assertion1 = assertion1 + inputs_prot_imp_r( comp, c1,  c2);
		assertion1 = assertion1 + prot_imp_r_prot_imp( comp, c1,  c2);
		assertion1 = assertion1 + prot_imp_r_io( comp, c1,  c2);
		
		assertion1 = assertion1 + outputs_prot_imp_r( comp, c1,  c2);
	  
		assertion1 = assertion1 + dual_prot_imp_r_prot_imp ( comp, c1,  c2);
		assertion1 = assertion1 + dual_prot_imp( comp, c1,  c2);
	    
		assertion1 = assertion1 + inputs_prot_imp_rio ( comp, c1,  c2);
	    
		assertion1 = assertion1 + prot_imp( comp, c1,  c2);
	    
		assertion1 = assertion1 + dual_prot_imp_rio ( comp, c1,  c2);
		
		
		System.out.println(assertion1);
		
		
		return assertion1;
		
	}
	
	
	public String splitChannel(String name) {
		
		
		String[] split = name.split("\\.");
		return split[0] + "_" + split[1];
		
	}
	
	//InBufferProt_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_FEED
	public String InBufferProt_def(String str, String c1 , String c2) {
		
		String inbufferProt ="";
		
		inbufferProt = "InBufferProt_" + str +  "_" + splitChannel(c1) + "_" + splitChannel(c2) +
				"(c) = CIO(PROT_IMP_R_IO_" +str + "_" + splitChannel(c1) + "_" + splitChannel(c2) + 
				 "[[ x  <- in, y <- out | x  <- inputs_PROT_IMP_R_IO_" +str + "_" + splitChannel(c1) + "_" + splitChannel(c2) +
				 ", y  <-" + "outputs_PROT_IMP_R_IO_" + str + "_" + splitChannel(c1) + "_" + splitChannel(c2) + "]]) \n " +
				 "InBufferProt_" + str +  "_" + splitChannel(c2) + "_" + splitChannel(c1) +
					"(c) = CIO(PROT_IMP_R_IO_" +str + "_" + splitChannel(c2) + "_" + splitChannel(c1) + 
					 "[[ x  <- in, y <- out | x  <- inputs_PROT_IMP_R_IO_" +str + "_" + splitChannel(c2) + "_" + splitChannel(c1) +
					 ", y  <-" + "outputs_PROT_IMP_R_IO_" + str + "_" + splitChannel(c2) + "_" + splitChannel(c1) + "]]) \n "
				 ;
		
		System.out.println(inbufferProt);
		return inbufferProt;
	}
	
	//protocol implementation 
	
	//-- Protocol Implementation
	//inputs_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(c)  = inter(inputs_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2,{|c|})
	//	outputs_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(c) = inter(outputs_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2,{|c|})

	
	public String protocolImp(String str) { // str nome da composicao 
		
		String protocolImp = "";
		protocolImp = "inputs_PROT_IMP_" + str + "(c) = inter( inputs_all ,{|c|}) \n"+
				      "outputs_PROT_IMP_" + str + "(c) = inter( outputs_all,{|c|}) \n \n" ;
		
		
		System.out.println(protocolImp);
		return protocolImp;
	}
	
	
	public String protocolImpChannel( String str, String channel1, String channel2) {
		
		String protocolImpChannel = "";
		
		protocolImpChannel = "inputs_PROT_IMP_" + str + "_" + splitChannel(channel1) + " =  inputs_PROT_IMP_" + str + "("  + channel1 +" )  \n" +
				             "inputs_PROT_IMP_" + str + "_" + splitChannel(channel2) + " =  inputs_PROT_IMP_" + str + "("  + channel2 +" )  \n \n"  ;
		
	
		
		System.out.println(protocolImpChannel);
	  return protocolImpChannel;
	}
	
	//inputs_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_FEED_FEED_port_fork_ext_2_phil_right_1_fork_right_R_IO_port_phil_ext_2_phil_right_1_fork_right = inputs_R_IO_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_FEED_FEED_port_fork_ext_2_phil_right_1_fork_right(port_fork_right.1, port_phil_right.2)
	//inputs_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_FEED_FEED_port_phil_ext_2_phil_right_1_fork_right_R_IO_port_fork_ext_2_phil_right_1_fork_right = inputs_R_IO_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_FEED_FEED_port_phil_ext_2_phil_right_1_fork_right(port_phil_right.2, port_fork_right.1)
    //inputs_PROT_IMP_R_IO
	
	public String inputs_prot_imp_rio (String str, String channel1, String channel2) {
		
		String inputs_prot_imp_rio = "";
		
		inputs_prot_imp_rio ="inputs_PROT_IMP_R_IO_" + str + "_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "= inputs_R_IO_PROT_IMP_"+ str + "_" + splitChannel(channel1)  +"(" + channel1 +","+ channel2 +") \n" +
		"inputs_PROT_IMP_R_IO_" + str + "_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "= inputs_R_IO_PROT_IMP_"+ str + "_" + splitChannel(channel2)  +"(" + channel2 +"," +channel1 +") \n" ;
				
		System.out.println(inputs_prot_imp_rio);
		
		return inputs_prot_imp_rio;
	}
	
	
	//outputs_PROT_IMP_R_IO	
	/*
* outputs_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_port_fork_ext_1_phil_left_2_fork_right_R_IO_port_phil_ext_1_phil_left_2_fork_right = outputs_R_IO_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(port_fork_right.2, port_phil_left.1)
*outputs_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_port_phil_ext_1_phil_left_2_fork_right_R_IO_port_fork_ext_1_phil_left_2_fork_right = outputs_R_IO_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(port_phil_left.1, port_fork_right.2)
*
*/
	
	public String outputs_prot_imp_rio(String str, String channel1, String channel2) {
		
	  String outputs_prot_imp_rio = "";
	  
	  outputs_prot_imp_rio = "outputs_PROT_IMP_R_IO_" + str + "_" + splitChannel(channel1) + "_" + splitChannel(channel2) +"= outputs_R_IO_" + str + "(" + channel1 + "," + channel1 +") \n" +
			  "outputs_PROT_IMP_R_IO_" + str + "_" + splitChannel(channel2) + "_" + splitChannel(channel1) +"= outputs_R_IO_" + str + "(" + channel2 + "," + channel2 +") \n \n" ;
			  
			  
			  
	 System.out.println( outputs_prot_imp_rio);
	  
	  return outputs_prot_imp_rio;
	  
	}
	
	
	
	
	//outputs_R_IO_
	//outputs_R_IO_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(a,b) = { b.x | x <- extensions(a), member(a.x, outputs_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2) }

	
	public String outputs_R_IO( String str, String channel1, String channel2) {
		
		
		String outputs_R_IO = "";
		
		outputs_R_IO = "outputs_R_IO_" + str + "(a,b)  = { b.x | x <- extensions(a), member(a.x, outputs_all)} \n";
		
		System.out.println(outputs_R_IO);
		
		
		return outputs_R_IO;
		
	}
	
	
	//-------------------------
	//protocol implementation R_IO
	//PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_FEED_port_fork_ext_2_phil_left_2_fork_left_R_IO_port_phil_ext_2_phil_left_2_fork_left = PROT_IMP_R_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_FEED_port_fork_ext_2_phil_left_2_fork_left
    //prot_imp_r_io
	
	public String prot_imp_r_io( String str, String channel1, String channel2) {
		
		
		String prot_imp_r_io = "";
		
		prot_imp_r_io ="PROT_IMP_R_IO_" + str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "= PROT_IMP_R_PROT_IMP_" +str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "\n" +
				"PROT_IMP_R_IO_" + str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "= PROT_IMP_R_PROT_IMP_" +str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "\n \n" ;
		
		
		
		System.out.println(prot_imp_r_io);
		
		return prot_imp_r_io;
		
	}
	
	//---------------------------------
	//PROT_IMP_R_PROT_IMP_
	
//PROT_IMP_R_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_port_fork_ext_1_phil_left_2_fork_right= rename(PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_port_fork_ext_1_phil_left_2_fork_right,R_IO_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(port_fork_right.2,port_phil_left.1)) --modifidado por sarah

	public String prot_imp_r_prot_imp( String str, String channel1, String channel2) {
		
		String prot_imp_r_prot_imp ="";
		
		prot_imp_r_prot_imp = "PROT_IMP_R_PROT_IMP_" + str +"_"+ splitChannel(channel1) + "_" + splitChannel(channel2) + "= rename(PROT_IMP_" + str +"_"+ splitChannel(channel1) + ", " + "R_IO_" + str +"(" + channel1 +"," + channel2 +")) \n" +
				"PROT_IMP_R_PROT_IMP_" + str +"_"+ splitChannel(channel2) + "_" + splitChannel(channel1) + "= rename(PROT_IMP_" + str +"_"+ splitChannel(channel2) + ", " + "R_IO_" + str +"(" + channel2 +"," + channel1 +")) \n \n";
		
		System.out.println(prot_imp_r_prot_imp);
		
		return prot_imp_r_prot_imp;
	
	}
	
	
	
	
	//----------------
	
	//protocolo rename 
	
	 //PROT_IMP_R_
	
	public String protocolImpRename( String str, String channel1, String channel2) {
		
		String protocolImpRename ="";
		protocolImpRename = "PROT_IMP_R_" + str + "_"+ splitChannel(channel1) + "= rename(" +
		         "PROT_IMP_" + str + "_" + splitChannel(channel1) + " , " + "R_IO_" + str + "("  + channel1 + "," +
				 channel2 + ")) \n" +
				 "PROT_IMP_R_" + str + "_"+ splitChannel(channel2) + "= rename(" +
		         "PROT_IMP_" + str + "_" + splitChannel(channel2) + " , " + "R_IO_" + str + "("  + channel2 + "," +
				 channel1 + ")) \n" ;
		
		
	    System.out.println(protocolImpRename);
		return protocolImpRename;
		
	}
	
	
	//------------------
	// PROT_IMP_
	
	public String prot_imp(String str, String channel1, String channel2) {
		String prot_imp ="";
		prot_imp ="PROT_IMP_" + str + "_" + splitChannel(channel1) + "= prot_fork" +"(" + channel1 +") \n"
				+ "PROT_IMP_" + str +  "_" + splitChannel(channel2) + "= prot_fork" +"(" + channel2 +") \n";
		System.out.println(prot_imp);
		return prot_imp;
	}
	
	
	//inputs_PROT_IMP_R_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(e,r)   = replace(inputs_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(e), r)
		
	public String inputs_prot_imp_r(String str, String channel1, String channel2) {
	 
		String inputs_prot_imp_r = "";
		
		inputs_prot_imp_r = "inputs_PROT_IMP_R_" + str +"(e,r) = replace(inputs_PROT_IMP_" + str + "(e), r) \n";
		
		System.out.println(inputs_prot_imp_r);
		
		return inputs_prot_imp_r;
		
		
	}
	
	
	//	outputs_PROT_IMP_R_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(e,r)   = replace(outputs_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(e), r)

	public String outputs_prot_imp_r( String str, String channel1, String channel2) {
	
		String outputs_prot_imp_r ="";
		
		outputs_prot_imp_r = "outputs_PROT_IMP_R_" + str +"(e,r)   = replace(outputs_PROT_IMP_" + str+ "(e), r) \n";
		
		System.out.println(outputs_prot_imp_r);
		
		return outputs_prot_imp_r;
	
	
	}
	//----------------------------------------------
	public String r_io_process( String str, String channel1, String channel2) {
		
		String r_io_process = "";
		
		r_io_process = "R_IO_" + str + "(a, b) = seq({(a.x, b.x) | x <- extensions(a), member(a.x, "+ "outputs_all )}) \n";
		System.out.println(r_io_process);
		return r_io_process;
	}
	
	//------------------------
	// inputs_R_IO_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(a,b)  = inputs_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2
	//-----------------------
	
	public String inputs_r_io( String str, String channel1, String channel2) {
		
		String inputs_r_io ="";
		inputs_r_io ="inputs_R_IO_"+ str +"(a,b) = inputs_all" +"\n";
		System.out.println(inputs_r_io);
		return inputs_r_io;
	}
	
	
	
	//inputs_R_IO_PROT_IMP_
	
	public String inputs_r_io_prot_imp( String str, String channel1, String channel2) {
		
		String inputs_r_io_prot_imp ="";
		
		inputs_r_io_prot_imp = "inputs_R_IO_PROT_IMP_" + str + "_" + splitChannel(channel1) + "(a,b)  = inputs_PROT_IMP_" +
				str + "_" + splitChannel(channel1) +  "\n" +
				"inputs_R_IO_PROT_IMP_" + str + "_" + splitChannel(channel2) + "(a,b)  = inputs_PROT_IMP_" +
				str + "_" + splitChannel(channel2) + "\n \n";
		
		System.out.println(inputs_r_io_prot_imp);
		return inputs_r_io_prot_imp;
		
		
	}
	
	
	//-- Dual Protocol
	//DUAL_PROT_IMP_R_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_port_fork_ext_1_phil_left_2_fork_right = rename(DUAL_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_port_fork_ext_1_phil_left_2_fork_right, R_IO_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(port_fork_right.2 ,port_phil_left.1)) --modifidado por sarah
	//DUAL_PROT_IMP_R_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_port_phil_ext_1_phil_left_2_fork_right = rename(DUAL_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2_port_phil_ext_1_phil_left_2_fork_right, R_IO_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2(port_phil_left.1 ,port_fork_right.2)) --modifidado por sarah

	
	public String dual_prot_imp_r_prot_imp( String str, String channel1, String channel2) {
		
		String dual_prot_imp_r_prot_imp ="";
		
		dual_prot_imp_r_prot_imp = "DUAL_PROT_IMP_R_PROT_IMP_" +  str +"_" +splitChannel(channel1)  + "= rename(DUAL_PROT_IMP_" + str +"_"+splitChannel(channel1)  + "," +"R_IO_" +str +"(" + channel1 + "," + channel2 +"))" + "\n" +
				"DUAL_PROT_IMP_R_PROT_IMP_" +  str +"_" +splitChannel(channel2) + "= rename(DUAL_PROT_IMP_" + str +"_"+splitChannel(channel2) + "," +"R_IO_" +str +"(" + channel2 + "," + channel1 +"))" + "\n";
		
				System.out.println(dual_prot_imp_r_prot_imp);
		
		return dual_prot_imp_r_prot_imp;
	}
	
	//dual protocol
	//DUAL_PROT_IMP_FORK1_INTER_FORK2_port_fork_ext_1_phil_right_1_fork_left = DUAL_PROT_FORK(port_fork_left.1)
	//DUAL_PROT_IMP_PHIL1_INTER_PHIL2_port_phil_ext_1_phil_right_1_fork_left = DUAL_PROT_PHIL(port_phil_right.1)

	public String dual_prot_imp( String str, String channel1, String channel2) {
		
		String dual_prot_imp ="";
			
		dual_prot_imp ="DUAL_PROT_IMP_" + str + "_" +splitChannel(channel1) + "= DUAL_PROT(" + channel1 +") \n"
				+ "DUAL_PROT_IMP_" + str +"_" + splitChannel(channel2) + "= DUAL_PROT(" + channel2 + ") \n\n";
				
		
		System.out.println(dual_prot_imp);
		
		return dual_prot_imp;
		
	}
	
	public String functionDefault() {
		
		String function = "";
		
		function = "-----CIO funnction \n"
				+ "channel out \n" +
				"channel in \n"  +
				"channel mid \n"  +
				"channel o \n \n" +
				"CP(a,b) = a -> b -> CP(a,b) \n" + 
				"C(a, P) = (P[[ a <- mid ]] [| {| mid |} |] CP(a,mid)) \\ {|mid|} \n" +
				"CIO(P) = C(in, C(out, P)) \n \n "
				//Rename function 
				+ "--------- Rename channels in a process using a mapping < (old1, new1), ..., (oldn, newn)> \n \n"
				+ "rename(P, <>) = P \n"
				+ "rename(P, <(c1,c2)>^rs) = rename(P[[c1 <- c2]], rs) \n \n"
				+ "-- Replaces events in a set using the mapping < (old1, new1), ..., (oldn, newn)> \n"
				+ "replace_aux(oldc, newc, S) = \n"
				+ "let other_events = {e | e <- S, not member(e, productions(oldc))} \n"
				+ "new_events   = {newc.v | v <- inter(extensions(newc), extensions(oldc)), member(oldc.v, S)} \n"
				+ "within union (other_events, new_events) \n \n"
				+ "replace(S, <>) = S \n"
				+ "replace(S, <(c1,c2)>^rs) = replace(replace_aux(c1,c2,S),rs) \n";		
				
		return function;
	}
	
	//---- D.6: Protocols are Strong Compatible
	public String assertion2(String str, String channel1, String channel2) {
		
		String assertion2 ="";
		assertion2 =" --- assertion 2--------------- \n "
				+ "assert PROT_IMP_R_IO_" + str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) + ":[deadlock free [FD]] \n"
				+ "assert PROT_IMP_R_IO_" + str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + ":[deadlock free [FD]] \n"
				
				+ "assert PROT_IMP_R_IO_" + str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "[T= DUAL_PROT_IMP_R_IO_"+ str  +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "\n"
				+ "assert PROT_IMP_R_IO_" + str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "[T= DUAL_PROT_IMP_R_IO_"+ str  +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "\n"
				+ "assert PROT_IMP_R_IO_" + str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "\\ outputs_PROT_IMP_R_IO_" +str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2)+":[divergence free [FD]]"+"\n"
				+ "assert PROT_IMP_R_IO_" + str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "\\ outputs_PROT_IMP_R_IO_" +str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) +":[divergence free [FD]]\n";
	    
		
		System.out.println(assertion2);
	    
	    return assertion2;
	}
	
	//+ " DUAL_PROT_IMP_R_IO";
//assert DUAL_PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2
//_port_fork_ext_1_phil_left_2_fork_right_R_IO
//_port_phil_ext_1_phil_left_2_fork_right [T= PROT_IMP_FORK1_INTER_FORK2_COMM_PHIL1_INTER_PHIL2
//_port_fork_ext_1_phil_left_2_fork_right_R_IO
//_port_phil_ext_1_phil_left_2_fork_right

	public String assertion3(String str, String channel1, String channel2) {
		
		String assertion3 ="";
		
		assertion3 ="assert DUAL_PROT_IMP_R_IO_" + str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "[T= PROT_IMP_R_IO_" + str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "\n"
				  + "assert DUAL_PROT_IMP_R_IO_" + str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "[T= PROT_IMP_R_IO_" + str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "\n"
				
				+ "  assert DUAL_PROT_IMP_R_IO_" + str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "[F= PROT_IMP_R_IO_" + str + "_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "\n"
			    + "  assert DUAL_PROT_IMP_R_IO_" + str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "[F= PROT_IMP_R_IO_" + str + "_" + splitChannel(channel1) + "_" + splitChannel(channel2) + "\n";
		
		
		System.out.println(assertion3);
		
		return assertion3;
	}
	
	
	public String dual_prot_imp_rio(String str, String channel1, String channel2) {
	
	  String dual_prot_imp_rio ="";
	  dual_prot_imp_rio = "DUAL_PROT_IMP_R_IO_" + str +"_" + splitChannel(channel1) + "_" + splitChannel(channel2) +" = DUAL_PROT_IMP_R_PROT_IMP_" +str +"_" + splitChannel(channel1) + "\n"
	                    + "DUAL_PROT_IMP_R_IO_" + str +"_" + splitChannel(channel2) + "_" + splitChannel(channel1) + "= DUAL_PROT_IMP_R_PROT_IMP_" + str +"_" + splitChannel(channel2) + "\n \n";
	  System.out.println(dual_prot_imp_rio);
	  
	  return dual_prot_imp_rio;
	}
	
 /*************************************************/
	
// monta tag para protocolo
	
//para  cada tipo de componente criar um protocolo
	
public String tagProtocol() {
	String tag ="";
	Declarations declaration = Declarations.getInstance();
	ArrayList<ComponentType> type = declaration.getComponentType();
	//HashSet<Instance>  instances = declaration.getInstances();
	
	for(int i = 0; i< type.size() ;i ++) {
		
		System.out.println(type.get(i).getType());
		
		System.out.println(declaration.getInstancebyType(type.get(i).getType()).get(0));
        String temp_tag = "#protocolo = " + declaration.getInstancebyType(type.get(i).getType()).get(0);
		System.out.println();
		
		
		// para cada tipo recuperar uma instancia 
		
		
	}
	
	return "";
}

//cria um basicComponent

	public void createBasicComponent() {
		
		Declarations declaration = Declarations.getInstance();
		ArrayList<ComponentType> componentType = declaration.getComponentType();
		ArrayList<BasicComponent> basicComponent = declaration.getBasicComponent();
		
		
		for(ComponentType temp:componentType ) {
			
			String str_temp = temp.getType();
			//verifica qual state machine possui esse nome
			String str_stm = declaration.getSTMbytype(str_temp);
			BasicComponent basicTemp = new BasicComponent(str_temp,str_stm);
			
		basicComponent.add(basicTemp);
		}
		
	
		//ver qual a state machine para este componente
		
	
	}

	
// imprimir basic component
	public String printBasicComponent() {
		String print ="";
		Declarations declaration = Declarations.getInstance();
		ArrayList<BasicComponent> basicComponent = declaration.getBasicComponent();
		
		for(BasicComponent temp:basicComponent ) {
		print = print + temp.getName() + "(id) =" +  temp.getStm() +"\n";
		
	}
	
		return print;
	}
public static void main(String args[]) {
	
	Composition c = new Composition();
	c.tagProtocol();
}




}
