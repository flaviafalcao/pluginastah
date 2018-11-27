package parser;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IPort;
import com.change_vision.jude.api.inf.model.ISequenceDiagram;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;
import com.change_vision.jude.api.inf.project.ProjectEvent;
import com.change_vision.jude.api.inf.project.ProjectEventListener;
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;

import ui.Util;

import java.util.ArrayList;
import java.util.List;

public class ClassView extends JPanel implements IPluginExtraTabView,
                                       ProjectEventListener {
	
 
	private ProjectAccessor projectAccessor;
	private JComboBox<String> combo1;
	private JTextArea area;
	HashSet<IClass> classeList = new HashSet<IClass>();
	
	
	public ClassView(String uri) {
		try {
    		String uri_local = uri;
    		projectAccessor = AstahAPI.getAstahAPI().getProjectAccessor();
    		projectAccessor.open(uri, true);
 		   // System.out.println("oi passei por aqui");
 		    r_operation();
 		 	}
    	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
	}
	
	public ClassView() {
		try {
			projectAccessor = AstahAPI.getAstahAPI().getProjectAccessor();
			r_operation();
		 		 
    			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//initComponents();
	}
   
	@Override
	public void projectChanged(ProjectEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectClosed(ProjectEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectOpened(ProjectEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSelectionListener(ISelectionListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component getComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "class view";
	}

	@Override
	public String getTitle() {
		return "Class View";
	}
	
	private void addProjectEventListener() {
		projectAccessor.addProjectEventListener(this);
	}

	private INamedElement[] findSequence() throws ProjectNotFoundException {
		INamedElement[] foundElements = projectAccessor
				.findElements(new ModelFinder() {
					public boolean isTarget(INamedElement namedElement) {
						return namedElement instanceof IClassDiagram;
					}
				});
		return foundElements;
	}
	
	
	
	public void r_operation() {
		Declarations declaration = Declarations.getInstance();
		Operation op;
		try {	
		
		//nome das classes
		IModel iCurrentProject = projectAccessor.getProject();
		getAllClasses(iCurrentProject, classeList);
		
		Iterator iteratorclass = classeList.iterator();
		
		while(iteratorclass.hasNext()) {
			
			IClass temp_class = (IClass)iteratorclass.next();
			if (temp_class.getOperations().length  >0 ) {
				for (int j =0 ;j<temp_class.getOperations().length ;j ++) {
					op = new Operation(temp_class.getOperations()[j].getName(), temp_class.getName(),
							temp_class.getStereotypes()[0].toString());
		    		declaration.addOperation(op);
			   }
			}
		}
		
		/*for(int i=0; i <classeList.size() ; i++) {
	    	 if (classeList.get(i).getOperations().length  >0 ) {
	    	     for (int j =0 ;i<classeList.get(i).getOperations().length ;j ++) {
	    		 op = new Operation(classeList.get(i).getOperations()[j].getName(), classeList.get(i).getName(),
	    				 classeList.get(i).getStereotypes()[0].toString());
	    	  //  System.out.println("esteriotipo -> " +classeList.get(i).getStereotypes()[0].toString());
	    	//	System.out.println("op classe -> " +classeList.get(i).getName() );
	    		 declaration.addOperation(op);
	    	 }
	    	 }
	    }*/
		
		}
		catch(Exception e) {
			}
		
	//	return declaration.getOperations();
		
		}
	     
	
	public String to_stringOp() {
		Declarations declaration = Declarations.getInstance();
		String stringOp = "datatype operation = ";
		ArrayList<Operation> op = declaration.getOperations();
		for(int i=0;i<op.size();i =i+1) {
			
			stringOp = stringOp +  op.get(i).getName_in() + " | " + op.get(i).getName_out();
			
			if (i != (op.size() -1)) {
				stringOp = stringOp + " | " ;
			}
			 
		} 
		
		return stringOp;
	}
	
//----------------------------------------------------
//formatar os canais de operacao para cada classe
//so fazer se a classe tiver pelo menos uma opeacao
	
	
	

	public String to_StringOpI() {
		String stringI = "";
		try {
		Declarations declaration = Declarations.getInstance();
		ArrayList<Operation> classOperation;
		HashSet<Operation> interfaceOperation ;
	
		//nome das classes
		IModel iCurrentProject = projectAccessor.getProject();
		getAllClasses(iCurrentProject, classeList);
		String texto = "";
		
		Iterator iclassList = classeList.iterator();
		
		while(iclassList.hasNext()) {
			IClass tempClass = (IClass)iclassList.next();
			interfaceOperation = new HashSet<Operation>();
			classOperation =  declaration.getClassOperation(tempClass.getName());
		    
			// recuperar as operacoes de suas interfaces
		    if (tempClass.getPorts().length >0) {
		  
			for(int k = 0 ; k < tempClass.getPorts().length ;k++) {
				// System.out.println(classeList.get(i).getPorts()[k]);
				//System.out.println(getTypeInterface(classeList.get(i).getPorts()[k]));
				
				if(getTypeInterface(tempClass.getPorts()[k]) == 1) {
					//System.out.println("required");
				  IClass interfaces[] = tempClass.getPorts()[k].getRequiredInterfaces();
				  for( int m =0 ; m <interfaces.length ; m++) {
					  ArrayList<Operation> interfaceOp=	 declaration.getClassOperation(interfaces[m].getName());
		              
					  for(int n = 0; n< interfaceOp.size() ;n ++) {
						  interfaceOperation.add(interfaceOp.get(n));
					  }
					//  System.out.println("size" + interfaceOperation.size());
				  }		
				} 
				if(getTypeInterface(tempClass.getPorts()[k]) == 0) {
				
					IClass interfaces[] = tempClass.getPorts()[k].getProvidedInterfaces();
					  for( int m =0 ; m <interfaces.length ; m++) {
						  ArrayList<Operation> interfaceOp=	 declaration.getClassOperation(interfaces[m].getName());
						  //System.out.println(interfaceOp.get(0).getName_in());
						  for(int n = 0; n< interfaceOp.size() ;n ++) {
							  interfaceOperation.add(interfaceOp.get(n));
						  }
					  }	
		     }
		}	
		Iterator it = 	interfaceOperation.iterator();	
		while(it.hasNext()) {
			classOperation.add((Operation)it.next());
			//System.out.println("while" + classOperation.size());
		}
				
	   if(classOperation.size()>0) {
		  // System.out.println("->>>><<<<<<<" + stringI + i );
		 //  System.out.println("passoou aqui " + classeList.get(i).getStereotypes().length);
		   ArrayList<Operation> op = classOperation;
		  
		   if(!(tempClass.getStereotypes()[0].equalsIgnoreCase("interface"))) {
		   texto = tempClass.getName();
		   stringI =  stringI + "subtype " + texto + "_I = ";
		   
		   for(int j=0;j<op.size();j =j+1) {
				
				stringI = stringI +  op.get(j).getName_in();
				
				if (j != (op.size() -1)) {
					stringI = stringI + " | " ;
				}
				 
		     } 
		   stringI =  stringI + "\n";
	   }
	  }
	}
	}
		
		}
	catch(Exception e ) {}
		
	return stringI;
	}
	
	//----------------------------------------------------

		public String to_StringOpO() {
			String stringO = "";
			try {
			Declarations declaration = Declarations.getInstance();
			ArrayList<Operation> classOperation ;
			HashSet<Operation> interfaceOperation ;
			
			String texto = "";
			//nome das classes
			IModel iCurrentProject = projectAccessor.getProject();
			getAllClasses(iCurrentProject, classeList);
		
			Iterator iclassList = classeList.iterator();
			
			while(iclassList.hasNext()) {
				IClass tempClass = (IClass)iclassList.next();	
				//  System.out.println(tempClass.getName());
				interfaceOperation = new HashSet<Operation>();
				classOperation =  declaration.getClassOperation(tempClass.getName());
				
			// recuperar as operacoes de suas interfaces
			    if (tempClass.getPorts().length >0) {
			    	
				for(int k = 0 ; k < tempClass.getPorts().length ;k++) {
					// System.out.println(classeList.get(i).getPorts()[k]);
					//System.out.println(getTypeInterface(tempClass.getPorts()[k]));
				
					if(getTypeInterface(tempClass.getPorts()[k]) == 1) {
						
					  IClass interfaces[] = tempClass.getPorts()[k].getRequiredInterfaces();
					  for( int m =0 ; m <interfaces.length ; m++) {
						  ArrayList<Operation> interfaceOp=	 declaration.getClassOperation(interfaces[m].getName());
			              
						  for(int n = 0; n< interfaceOp.size() ;n ++) {
							  interfaceOperation.add(interfaceOp.get(n));
						  }
						  // System.out.println(interfaceOperation.size());
					  }		
					}
				
					if(getTypeInterface(tempClass.getPorts()[k]) == 0) {
					
						IClass interfaces[] = tempClass.getPorts()[k].getProvidedInterfaces();
						  for( int m =0 ; m <interfaces.length ; m++) {
							  ArrayList<Operation> interfaceOp=	 declaration.getClassOperation(interfaces[m].getName());
							
							  for(int n = 0; n< interfaceOp.size() ;n ++) {
								  interfaceOperation.add(interfaceOp.get(n));
							  }
						  }	
			     }
			}	
			Iterator it = 	interfaceOperation.iterator();	
			while(it.hasNext()) {
				classOperation.add((Operation)it.next());
				//System.out.println("while" + classOperation.size());
			}
			
			if(classOperation.size()>0) {
				  ArrayList<Operation> op = classOperation;
				  
			if(!(tempClass.getStereotypes()[0].equalsIgnoreCase("interface"))) {
				   texto = tempClass.getName();
				   stringO =   stringO  + "subtype " + texto + "_O = ";
				   	for(int j=0;j<op.size();j =j+1) {
							
							stringO = stringO +  op.get(j).getName_out();
							
							if (j != (op.size() -1)) {
								stringO = stringO + " | " ;
								
							}
					} 
				    stringO =  stringO + "\n";
			 }
		 }
				
			} }}
			 
			catch(Exception e ) {}
			
			return stringO;
			
			
		}

	
	
	
//----------------------------------------------------
	private void updateTextArea() {
		
		String texto = new String(" ");
		IClassDiagram iclassD;
		IClass classe;
		INamedElement[] j;
		IElement[] k;
		try {
			//System.out.println("teste");
			INamedElement[] findSequence = findSequence();
			for (int i = 0; i < findSequence.length; i++) {
				
			   texto = texto + " "+    findSequence[i].getName(); //nome de um diagrama de classes
			   iclassD = (IClassDiagram)findSequence[i];
			
			   
			}
			//System.out.println(texto);
			area.setText(texto);
			
					
		} catch (ProjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

public void updateTextArea2() throws ClassNotFoundException {
	Util utl;
	
	try {
	utl = Util.getIstance();
	ProjectAccessor projectAccessor = ProjectAccessorFactory.getProjectAccessor();
	IModel iCurrentProject = projectAccessor.getProject();  
	if(iCurrentProject instanceof IPackage) {
	//System.out.println("pacote");}
     getAllClasses(iCurrentProject, classeList);
   /*  for(int i=0; i <classeList.size() ; i++) {
    	 area.append(classeList.get(i).getName());
    	 System.out.println(classeList.get(i).getName());
    	 System.out.println("oi");
    	 if (classeList.get(i).getOperations().length  >0 ) {
    	 for (int j =0 ;i<classeList.get(i).getOperations().length ;j ++) {
    		 
    		 utl.op_I[j] = classeList.get(i).getOperations()[j].getName() + "_I  ";    		 
    		 utl.op_O[j] = classeList.get(i).getOperations()[j].getName() + "_O  ";
    	 }
    	
     }
     }
     */}}
	
	 catch (Exception e) {
       
         
     }
	
	
	
}





private void getAllClasses(INamedElement element, HashSet<IClass> classList) throws ClassNotFoundException, ProjectNotFoundException {
    if (element instanceof IPackage) {
        for(INamedElement ownedNamedElement : ((IPackage)element).getOwnedElements()) {
            getAllClasses(ownedNamedElement, classList);
        }
    } else if (element instanceof IClass) {
        classList.add((IClass)element);
        for(IClass nestedClasses : ((IClass)element).getNestedClasses()) {
            getAllClasses(nestedClasses, classList);
        }
    }
}


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



}


