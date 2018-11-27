package parser;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.editor.TransactionManager;
import com.change_vision.jude.api.inf.model.*;

import com.change_vision.jude.api.inf.presentation.*;
//import com.change_vision.jude.api.inf.model.IConnector;
//import com.change_vision.jude.api.inf.model.INamedElement;
//import com.change_vision.jude.api.inf.model.IStateMachine;
//import com.change_vision.jude.api.inf.model.IStateMachineDiagram;
//import com.change_vision.jude.api.inf.presentation.IPresentation;
//import com.change_vision.jude.api.inf.presentation.PresentationPropertyConstants;
import com.change_vision.jude.api.inf.project.*;
//import com.change_vision.jude.api.inf.project.ProjectEvent;
//import com.change_vision.jude.api.inf.project.ProjectEventListener;

import fdr.FdrWrapper;



public class Principal  {
	
	public static void main(String args[]) {
		
		FileWriter arquivo; 
		String write = "";
		
		Class iii = IPresentation.class;
		
		try {
			
		arquivo = new FileWriter(new File("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/Arquivo.csp"));  
		 
	    CompositeView c = new CompositeView();
		  
	    ProjectAccessor projectAccessor;
	    projectAccessor = AstahAPI.getAstahAPI().getProjectAccessor();   
	   //   
	    write = "-------------canais das portas-----------------" + "\n";
	    System.out.println(c.getChannelPort());		 // sempre vai ter
		System.out.println(c.getChannelGetSet());    // opcional
		System.out.println(c.getProcessGetSet());   //opcional
		
		
		INamedElement[] foundElements2 = null;
		foundElements2 = c.findConnector();
		for (INamedElement element2 : foundElements2) {
			IConnector temp = (IConnector)element2;
			System.out.println(c.getParts(temp));  // adiciona e cria as instancias 
		}
		
		
		String instancias = c.MontaInstancia(); // monta a instancia
		
		//System.out.println(instancias);
		
		INamedElement[]	foundElements3 = null;
		
		foundElements3 =c.findConnector();
		

		write =  write + c.getChannelPort() + "\n"; 
		write =  write  + "--------------------canais set e gets--------------"+ "\n"
		  + "" + c.getChannelGetSet()
		  + "\n"+ c.getProcessGetSet()
		  + "-----instancias-----------"
		  + "\n" + instancias +"\n";
		
		
		 projectAccessor.close();
		 
		 //class
	     ClassView classv;			
		 classv = new ClassView("C:/Users/flavi/eclipse-workspace/ref/src/main/resources/modelo.asta");
		
		 String op = classv.to_stringOp();
		 System.out.println (classv.to_stringOp());
		 String opI = classv.to_StringOpI();
		 System.out.println(classv.to_StringOpI());
		 String opO = classv.to_StringOpO();
		 System.out.println(classv.to_StringOpO());
		 
		 write = write + 
				 op + "\n"
				 		+ opI + "\n"
				 				+ opO + "\n" ;
				 
		 projectAccessor.close();
		 
		//open astah 
		STMView stm; 
		stm = new STMView("C:/Users/flavi/eclipse-workspace/ref/src/main/resources/modelo.asta");

		StringBuilder stm_fork = new StringBuilder();
		StringBuilder stm_phil = new StringBuilder();
		//StringBuilder stm_cell = new StringBuilder();
		int i =0;
		IStateMachineDiagram stateMachineDiagram[] = new IStateMachineDiagram[3];  
		//identify STMs
				INamedElement[] foundElements = stm.findStateMachine();
				for (INamedElement element : foundElements) {
					stateMachineDiagram[i] = stm.castStateMachineDiagaram(element);
					i= i +1;
				}	
				
				
				IStateMachine machine1 = (IStateMachine)stateMachineDiagram[0].getStateMachine(); //statemachine 1
				IStateMachine machine2 = (IStateMachine)stateMachineDiagram[1].getStateMachine(); // statemachine 2
			
				if (machine1.getName().equalsIgnoreCase("STM_FORK")) {
					stm_fork =stm.showStateMachine(machine1);
					stm_phil = stm.showStateMachine(machine2);
				}
					if(machine1.getName().equalsIgnoreCase("STM_PHIL")){
					stm_phil = stm.showStateMachine(machine1);
					stm_fork = stm.showStateMachine(machine2);
				    }
					
				
				
				System.out.println(stm_fork);
				System.out.println(stm_phil);
				
				write = write + "\n"
						+ stm_fork + "\n"
								+ stm_phil +"\n";
				
				 projectAccessor.close();
		
				 
				 
				 
				// construir composicoes
				 Composition comp = new Composition();
				///criar basic component
				 comp.createBasicComponent();
				 String temp_basic = comp.printBasicComponent();
				 System.out.println(temp_basic);
				 
				 write = write +  temp_basic + "\n";
				 
				 for (INamedElement element3 : foundElements3) {
						IConnector temp = (IConnector)element3;
						comp.firstConection(temp);
				 }
				 
				 
				 
				 String inputComponent = comp.getInputComponent();
				// System.out.println(inputComponent);
				 
				 String outputComponent = comp.getOutputComponent();
				// System.out.println(outputComponent);
				 
				 String doComponent = comp.doComposition();
				 System.out.println(doComponent);
				 
				 String funcDefaut = comp.functionDefault();
				 System.out.println(funcDefaut);
				 comp.tagProtocol();
				//gerar protocolo
				 
				
				 write = write + "\n" + inputComponent + "\n"
				 		+  outputComponent + "\n"
				 				+  doComponent +"\n"
				 						+  funcDefaut +"\n";
				 
				 
				 
				 InputTypeDeclaration input = new InputTypeDeclaration();
				
				 String inputType = input.inputType();
				 String outputType = input.outputType();
				 String inputAll =  input.inputAll();
				 String outputAll = input.outputAll();
				 
				 
				 write = write + "\n" + inputType + "\n"
				 		+ outputType + "\n"
				 				+ inputAll +"\n"
				 						+ outputAll + "\n" ;
						 
				 
				
				 
				OutputCFunction cFunction = new OutputCFunction();
				write = write + cFunction.outputFunctionType();
				write = write + cFunction.outputFunctionTypeAll();
				 
				 
				 Buffer bf = new Buffer();
				 //write = write + bf.bufferInterleave();
				 write = write + bf.bufferAll();
				 write = write + bf.BufferIO();
				 
				 
				 arquivo.write(write);
				 arquivo.close();
				 
				 
				 
				 LTS lts = new LTS();
				 String trace_lts = lts.protocolo_trace("C:/Users/flavi/eclipse-workspace/projeto/teste.csp","protocolo1");
				 System.out.println(trace_lts);
				  
				 LTS lts2 = new LTS();
				// String trace_lts2 = lts2.protocolo_trace("C:/Users/flavi/OneDrive/Documentos/doutorado/Dropbox/doutorado/teste.csp","protocolo2");
				// System.out.println(trace_lts2);
				  
				
				 String map = 
						 
	     "-------------------------------------------------------------------------------"
		 +"\n" +
		 "-- mapping an LTS to (traces) equivalent CSP process"
		 +"\n" +
		 "--------------------------------------------------------------------------------"
		 +"\n" +
		 "-- Let p = (Q, Li, Lo, T, q0) be an LTS(Li,Lo)"
		 +"\n" +
		 "-- available events (visible or not) in state 'q'"
		 +"\n" +
		 " availablee(q,T) = { ev | (q1,ev,q2)<-T, q1==q }"
		 +"\n" +
		 " -- que seja diferente de '?' "
		 +"\n" +
		 "-- adjacent states in state 'q' after event 'e'"
		 +"\n" +
		 "next(q,e,T) = { q2 | (q1,ev,q2)<-T, q1==q, ev==e }"
		 +"\n" +
		 "-- CSP process following 'T' starting from state 'q1' from Q"
		 +"\n" +
		 "M(q1,T) = [] ev : availablee(q1,T)  @ ev -> ( [] q2 : next(q1,ev,T) @ M(q2,T) )"
		 +"\n" +
		 " -- FDR internal event representation"
		 +"\n" +
		 "channel tau , INVALIDEVENT"
		 +"\n" +
		 " -- mapping LTS from FDR to CSP process"
		 +"\n" +
		 " MFDR(q1,T) = M(q1,T)  \\ {tau}"
		 +"\n" ;

			
		 FileWriter arquivo2; 
		 String write2 = "include \"teste.csp\" \n";
		 arquivo2 = new FileWriter(new File("Arquivo_protocolo.csp"));  
		 write2 = write2 + map;
		 //monta protocolo
		 
		 
		 // c
		 String protocolo = "protocolo = MFDR( " + lts.root() +  ",{" +  trace_lts + "}) \n";
		// String protocolo2 = "protocolo2 = MFDR( " + lts2.root() +  ",{" +  trace_lts2 + "}) \n";
		 
		 write2 = write2 + protocolo + "\n" ;//+ protocolo2 ;
		 
		 arquivo2.write(write2);
		 arquivo2.close();	
		 
		 
				 
		}
		catch(Exception e) {
			
			System.out.println(e.toString());
			e.printStackTrace();;
			
		}
	

		
		
		
		
	}




//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
	
public boolean valida() {
		
		FileWriter arquivo; 
		String write = "";
		
		boolean check = false;
		try {
			
	   arquivo =  new FileWriter(new File("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/modelo.csp"));  
		
	   
	
	   CompositeView c = new CompositeView	();
	    ProjectAccessor projectAccessor;
	    projectAccessor = AstahAPI.getAstahAPI().getProjectAccessor();  
	    IModel im = projectAccessor.getProject();
	    IDiagram[] id = im.getDiagrams();
	    IPresentation[] ip = id[0].getPresentations();
	    ip[0].getDepth();
	    
	    write = "-------------canais das portas-----------------" + "\n";
	    System.out.println(c.getChannelPort());		 // sempre vai ter
		System.out.println(c.getChannelGetSet());    // opcional
		System.out.println(c.getProcessGetSet());   //opcional
		
		
		INamedElement[] foundElements2 = null;
		foundElements2 = c.findConnector();
		for (INamedElement element2 : foundElements2) {
			IConnector temp = (IConnector)element2;
			//System.out.println("c.getParts(temp)");
			System.out.println(c.getParts(temp));  // adiciona e cria as instancias 
		}
		
		
		String instancias = c.MontaInstancia(); // monta a instancia
		
		System.out.println(instancias);
		
		INamedElement[]	foundElements3 = null;
		
		foundElements3 =c.findConnector();
		

		write =  write + c.getChannelPort() + "\n"; 
		write =  write  + "--------------------canais set e gets--------------"+ "\n"
		  + "" + c.getChannelGetSet()
		  + "\n"+ c.getProcessGetSet()
		  + "-----instancias-----------"
		  + "\n" + instancias +"\n";
		
		
		 //projectAccessor.close();
		 
		 //class
	     ClassView classv;			
		 classv = new ClassView	();
		
		 String op = classv.to_stringOp();
		 System.out.println (classv.to_stringOp());
		 String opI = classv.to_StringOpI();
		 System.out.println(classv.to_StringOpI());
		 String opO = classv.to_StringOpO();
		 System.out.println(classv.to_StringOpO());
		 
		 write = write + 
				 op + "\n"
				 		+ opI + "\n"
				 				+ opO + "\n" ;
				 
		// projectAccessor.close();
		 
		//open astah 
		STMView stm; 
		stm = new STMView();

		StringBuilder stm_fork = new StringBuilder();
		StringBuilder stm_phil = new StringBuilder();
		//StringBuilder stm_cell = new StringBuilder();
		int i =0;
		IStateMachineDiagram stateMachineDiagram[] = new IStateMachineDiagram[3];  
		//identify STMs
				INamedElement[] foundElements = stm.findStateMachine();
				for (INamedElement element : foundElements) {
					stateMachineDiagram[i] = stm.castStateMachineDiagaram(element);
					i= i +1;
				}	
				
				
				IStateMachine machine1 = (IStateMachine)stateMachineDiagram[0].getStateMachine(); //statemachine 1
				IStateMachine machine2 = (IStateMachine)stateMachineDiagram[1].getStateMachine(); // statemachine 2
			
				if (machine1.getName().equalsIgnoreCase("STM_FORK")) {
					stm_fork =stm.showStateMachine(machine1);
					stm_phil = stm.showStateMachine(machine2);
				}
					if(machine1.getName().equalsIgnoreCase("STM_PHIL")){
					stm_phil = stm.showStateMachine(machine1);
					stm_fork = stm.showStateMachine(machine2);
				    }
					
				
				
				System.out.println(stm_fork);
				System.out.println(stm_phil);
				
				write = write + "\n"
						+ stm_fork + "\n"
								+ stm_phil +"\n";
				
					// construir composicoes
				 Composition comp = new Composition();
				///criar basic component
				 comp.createBasicComponent();
				 String temp_basic = comp.printBasicComponent();
				 System.out.println(temp_basic);
				 
				 write = write +  temp_basic + "\n";
				 
				 for (INamedElement element3 : foundElements3) {
						IConnector temp = (IConnector)element3;
						comp.firstConection(temp);
				 }
				 
				 
				 
				 String inputComponent = comp.getInputComponent();
				 
				 String outputComponent = comp.getOutputComponent();
				 
				 String doComponent = comp.doComposition();
				 System.out.println(doComponent);
				 
				 String funcDefaut = comp.functionDefault();
				 System.out.println(funcDefaut);
				 comp.tagProtocol();
				//gerar protocolo
				 
				
				 write = write + "\n" + inputComponent + "\n"
				 		+  outputComponent + "\n"
				 				+  doComponent +"\n"
				 						+  funcDefaut +"\n";
				 
				 
				 
				 InputTypeDeclaration input = new InputTypeDeclaration();
				
				 String inputType = input.inputType();
				 String outputType = input.outputType();
				 String inputAll =  input.inputAll();
				 String outputAll = input.outputAll();
				 
				 
				 write = write + "\n" + inputType + "\n"
				 		+ outputType + "\n"
				 				+ inputAll +"\n"
				 						+ outputAll + "\n" ;
						 
				 
				
				 
				OutputCFunction cFunction = new OutputCFunction();
				write = write + cFunction.outputFunctionType();
				write = write + cFunction.outputFunctionTypeAll();
				
				 
				 Buffer bf = new Buffer();
			
				 write = write + bf.bufferAll();
				 write = write + bf.BufferIO();
				 
				 
				 write = write + " prot_fork(x) = MFDR(-144481089, {(-144481089,x.picksup_I,1415583828), \n" + 
					 "(-144481089,x.picksup_I,119239090), \n" +
					 "(1428894023,x.putsdown_O,-144481089), \n " +
					 "(1207824812,x.putsdown_I,1428894023), \n " +
					 "(119239090,x.picksup_O,1207824812), \n "+
					 "(-301987748,x.putsdown_O,-144481089), \n" +
					 "(1768610575,x.putsdown_I,-301987748), \n" +
					 "(1415583828,x.picksup_O,1768610575)}) \n";
                 
				 String map = 
						 
					     "-------------------------------------------------------------------------------"
						 +"\n" +
						 "-- mapping an LTS to (traces) equivalent CSP process"
						 +"\n" +
						 "--------------------------------------------------------------------------------"
						 +"\n" +
						 "-- Let p = (Q, Li, Lo, T, q0) be an LTS(Li,Lo)"
						 +"\n" +
						 "-- available events (visible or not) in state 'q'"
						 +"\n" +
						 " availablee(q,T) = { ev | (q1,ev,q2)<-T, q1==q }"
						 +"\n" +
						 " -- que seja diferente de '?' "
						 +"\n" +
						 "-- adjacent states in state 'q' after event 'e'"
						 +"\n" +
						 "next(q,e,T) = { q2 | (q1,ev,q2)<-T, q1==q, ev==e }"
						 +"\n" +
						 "-- CSP process following 'T' starting from state 'q1' from Q"
						 +"\n" +
						 "M(q1,T) = [] ev : availablee(q1,T)  @ ev -> ( [] q2 : next(q1,ev,T) @ M(q2,T) )"
						 +"\n" +
						 " -- FDR internal event representation"
						 +"\n" +
						 "channel tau , INVALIDEVENT"
						 +"\n" +
						 " -- mapping LTS from FDR to CSP process"
						 +"\n" +
						 " MFDR(q1,T) = M(q1,T)  \\ {tau}"
						 +"\n" ;

				 
				 
				 
				 write = write +
						" DUAL_PROT(b) = prot_fork(b) \n "+  map;
				 
				 
				 
				 
				 arquivo.write(write);
				 arquivo.close();
				 
				 
				 LTS lts = new LTS();
				 String trace_lts = lts.protocolo_trace("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/teste.csp","protocolo1");
			     System.out.println(trace_lts);
				  
				 LTS lts2 = new LTS();
				 String trace_lts2 = "";
				// trace_lts2= lts2.protocolo_trace("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/teste.csp","protocolo2");
				// System.out.println(trace_lts2);
				  
				
				 
			
		 FileWriter arquivo2; 
		 String write2 = "include \"teste.csp\" \n";
		 arquivo2 = new FileWriter(new File("C:/Users/flavi/eclipse-workspace/plugin_astah/src/main/resources/Arquivo_protocolo.csp"));  
		 write2 = write2 + map;
		 //monta protocolo
		 
		 
		 // c
		 String protocolo = "protocolo = MFDR( " + lts.root() +  ",{" +  trace_lts + "}) \n";
		 String protocolo2 = "protocolo2 = MFDR( " + lts2.root() +  ",{" +  trace_lts2 + "}) \n";
		 
		 write2 = write2 + protocolo + "\n" + protocolo2 ;
		 
		 arquivo2.write(write2);
		 arquivo2.close();	
		 
		 
		 
		 IConnector conn = null;
		 Declarations declaration =  Declarations.getInstance();
			 
		 
		 
		
		 
		//boolean 
		 boolean check1 =  checkRefinement("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/assertion0.csp");
		 
		 if(!check1) {
			 
			 conn =  declaration.getIConnector("assertion0.csp");
		 }
		
		 boolean check2 =  checkRefinement("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/assertion1.csp");
		 
        if(!check2) {
			 
			 conn =  declaration.getIConnector("assertion1.csp");
		 }
		
		 boolean check3 =  checkRefinement("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/assertion2.csp");
		 
         if(!check3) {
			 
			 conn =  declaration.getIConnector("assertion2.csp");
		 }
		 
		 boolean check4 =  checkRefinement("C:/Users/flavi/eclipse-workspace/projeto/src/main/resources/assertion3.csp");
		 
           if(!check4) {
			 
			 conn =  declaration.getIConnector("assertion3.csp");
		 }
		 
		 
		 
		 
		 conn.setName("!!HERE!!");
		 
		 
	//	 o = conn.getPresentations()[0];
		 
		 
		
	//	IPresentation cp = conn.getPresentations()[0];
	//	if (cp != null) {
		//	System.out.println("nao eh null" + cp.getClass());
		//}
		
		//cp.getDepth();
	  // System.out.println(cp.getProperty(PresentationPropertyConstants.Key.LINE_COLOR));
		

		
		//	System.out.println(cp.getProperty(PresentationPropertyConstants.class.));
		  //cp.setProperty(PresentationPropertyConstants.Key.LINE_COLOR, "#FF0000");
		
			
		
     	check = check1 && check2 && check3 && check4;
		
		 
		// System.out.println(check);
				 
		}
		catch(Exception e) {
			
			System.out.println(e.toString());
			e.printStackTrace();
			
		}
	

		
		
		
     return check;
		
		
		
	}

//verifica assertions
public boolean checkRefinement(String filename) {
	  FdrWrapper wrapper =  FdrWrapper.getInstance();
	      wrapper.loadFile(filename);
	        List<Object> assertions = FdrWrapper.getInstance().getAssertions();
	        System.out.println("Numero de assertions : " + assertions.size());

	        return wrapper.executeAssertions(assertions);
	    }


}



 
