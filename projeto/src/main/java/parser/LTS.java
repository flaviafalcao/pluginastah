package parser;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;

/*
 * 
 * mudar o character encoding para UTF-8 
 * o default do eclipse é 1252
 */

import java.util.*;

import fdr.FdrWrapper;
//import uk.ac.ox.cs.fdr.Canceller;
//import uk.ac.ox.cs.fdr.Machine;
//import uk.ac.ox.cs.fdr.MachineEvaluatorResult;
//import uk.ac.ox.cs.fdr.Node;
//import uk.ac.ox.cs.fdr.SemanticModel;
//import uk.ac.ox.cs.fdr.Session;
//import uk.ac.ox.cs.fdr.fdr;


public class LTS {
	
	public static List<Object> lista;  //= new ArrayList<Node>();
	public static List<no> path ;// = new ArrayList<no>();
	//public static Set<no> pathsemtau = new HashSet<no>();
	public static List<no> pathsemtau;// = new ArrayList<no>();
	public static ArrayList<novisitado> visitados;//  = new ArrayList<novisitado>();
    public static String root;
    public FdrWrapper wrapper;
   
    
   public LTS(){
    	lista = new ArrayList<Object>();
    	path = new ArrayList<no>();
    	pathsemtau = new ArrayList<no>();
    	visitados  = new ArrayList<novisitado>();
    	
    }
   
//    public static void main(String[] args) {
//       
//		try {
//			
//			System.out.println("aaaaa");
//		
//			System.out.println(fdr.version());
//	        Session session = new Session();
//               // String pathFile = "C:/Users/flavi/OneDrive/Documentos/doutorado/csp/lts_csp.csp";
//           //  String pathFile = "C:/Users/flavi/OneDrive/Documentos/doutorado/csp/testes_protocolo.csp";
//	       // String pathFile = "C:/Users/flavi/OneDrive/Documentos/doutorado/csp/versao_generica.csp";
//	        
//	       // String pathFile = "C:/Users/flavi/OneDrive/Documentos/doutorado/csp/ts_tau_teste.csp";
//	       // String  pathFile =  "C:/Users/flavi/OneDrive/Documentos/doutorado/ringbuffer/teste_dalay.csp";
//	        
//	       // String pathFile = "C:/Users/flavi/OneDrive/Documentos/doutorado/Dropbox/doutorado/teste.csp";
//	        String pathFile = "C:/Users/flavi/eclipse-workspace/ref/teste.csp";
//            session.loadFile(pathFile);
//            
//	         
//	        
//	        
//	        //String processName = "PHIL1";
//	       // String processName = "protocolo_lts";
//	        String processName = "protocolo2";
//	        
//	        MachineEvaluatorResult mEvaluatorResult =  session.evaluateProcess(processName, SemanticModel.Traces, new Canceller());
//            Machine machine = mEvaluatorResult.result();
//	       	        
//	        Node node = machine.rootNode();
//	        
//	        LTS demo = new LTS();
//	        demo.traces(node, machine, session,lista);
//	        
//	        System.out.println("tam lista" + lista.size());
//	        System.out.println(path.size());
//	        
//	        
//	    //  for(int i= 0; i<path.size() ;i++) {
//	        	
//	      //  	System.out.println("(" + path.get(i).origem +"," + path.get(i).evento +","+ path.get(i).destino +")");
//	 	       
//	        //}
//	        
//	        demo.varre();
//	        
//	        Iterator<no> it = pathsemtau.iterator();
//	        no tmp = demo.new no();
//	        String temp = "";
//	        while(it.hasNext()) {
//	           
//	        	tmp = it.next();
//	        	temp = temp + "(" +tmp.origem +"," + tmp.evento +","+ tmp.destino +") \n";
//	        	System.out.println("(" +tmp.origem +"," + tmp.evento +","+ tmp.destino +")");
//	        }
//	        
//	        System.out.println(pathsemtau.size());
//	        
//			//FileWriter arquivo; 
//			//String write = "include \"teste.csp\" \n";
//	        //arquivo = new FileWriter(new File("C:/Users/flavi/OneDrive/Documentos/doutorado/Dropbox/doutorado/Arquivo_protocolo.csp"));  
//	        //write = write + temp;
//	        //arquivo.write(write);
//			//arquivo.close();
//	        destroy();
//	   		}
//	   // catch (InputFileError error) {
//	     //   System.out.println(error);
//	        
//	    //}
//		
//		catch(Exception e) {
//			
//			  System.out.println(e);
//		}
//	   
//	    fdr.libraryExit();
//	    
//        	//for ( int x =0; x< machine.transitions(tmp).size();x++) {
//        	
//        	///System.out.println("transition -->  " + session.uncompileEvent(machine.transitions(tmp).get(x).event()));
//        	//}
//        	//System.out.println("trace");
//        	
//        	//proximo
//        }
//    
    
    public String root() {
    	
    	return root;
    }
    
    private static void destroy() {
    	
    	path = null;
    	lista = null;
    	pathsemtau = null;
    	visitados = null;
    }
    
    public String  protocolo_trace(String url, String protocolo) {
    	
    	String temp = "";
		try {
		//Object session = wrapper.getSession();
		String pathFile = url;
		wrapper = FdrWrapper.getInstance();
		
        wrapper.loadFile(pathFile);
        String processName = protocolo;
        
        Object machine = wrapper.getMachineEvaluatorResult(processName); //machine
        Object root = wrapper.getrootNode(machine);
        
        traces(root,machine,wrapper.getSession(),lista);
        
        varre();
        
        Iterator<no> it = pathsemtau.iterator();
        no tmp = new no();
        
        while(it.hasNext()) {
           
        	tmp = it.next();
        	temp = temp + "(" +tmp.origem +"," + tmp.evento +","+ tmp.destino +"),\n";
        	//System.out.println("(" +tmp.origem +"," + tmp.evento +","+ tmp.destino +")");
        }
        
         
        if (temp.length() > 0) {
            temp = temp.substring (0, temp.length() - 2);
        }
        System.out.println(temp.length());
        
		 
        System.out.println( "----------------oloooo--------------");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return temp;
    }
    
    public void traces(Object root,Object machine, Object session,List lista) {
    	
    	Object node = root;
    	no n ;  	
    	AbstractList transitionList = wrapper.getTransitions(machine, node);
    	for(int i =0 ; i< transitionList.size();i++) {
    	//for( int i =0; i< machine.transitions(node).size();i++) {
    		n = new no(); 
    		//n.origem = node.hashCode();
    		n.origem = wrapper.getHashCode(node);
    		
    		Object transition = wrapper.getTransitionIndex(transitionList, i);
    		Long event = wrapper.getEvent(transition);
    		n.evento = wrapper.getUncompileEvent(event).toString();
    				
    		Object tmp = wrapper.getDestination(transition);
    		//n.evento = session.uncompileEvent(machine.transitions(node).get(i).event()).toString();
    		 
    		//Node tmp = machine.transitions(node).get(i).destination();
    		//n.destino = tmp.hashCode();
    		n.destino = wrapper.getHashCode(tmp);
    		//System.out.print(n.destino);
    	 //	System.out.print("(");
    		//System.out.print(n.origem + ",");    
            //System.out.print(n.evento);  
            //System.out.println("," + tmp.hashCode() + " ),");
        	lista.add(node);
        	path.add(n);
        	//CompiledEventList a = machine.alphabet(true);       
        	
        	if (!lista.contains(tmp))    	        	
        	{        		
        		this.traces(tmp,machine,session,lista);
        	}
    	} 

    }

    
/*    public String  protocolo_trace(String url, String protocolo) {
    	String temp = "";
		try {
		   
			Object session = wrapper.getSession();
	      //  Session session = new Session();
            String pathFile = url;
            wrapper.loadFile(pathFile);
          //  session.loadFile(pathFile);            
	     
            String processName = protocolo;
            
	        MachineEvaluatorResult mEvaluatorResult =  session.evaluateProcess(processName, SemanticModel.Traces, new Canceller());
            Machine machine = mEvaluatorResult.result();
	       	        
	        Node node = machine.rootNode();
	        root = node.hashCode() +"";
	        
	        LTS demo = new LTS();
	        demo.traces(node, machine, session,lista);
	        
	        demo.varre();
	        
	        Iterator<no> it = pathsemtau.iterator();
	        no tmp = demo.new no();
	        
	        while(it.hasNext()) {
	           
	        	tmp = it.next();
	        	temp = temp + "(" +tmp.origem +"," + tmp.evento +","+ tmp.destino +"),\n";
	        	//System.out.println("(" +tmp.origem +"," + tmp.evento +","+ tmp.destino +")");
	        }
	        
	         
	        if (temp.length() > 0) {
	            temp = temp.substring (0, temp.length() - 2);
	        }
	        System.out.println(temp.length());
	        
			
	   		}
	    catch (Exception error) {
	        System.out.println(error);
	    }
		
		//catch(Exception e) {}
		
		destroy();
	    fdr.libraryExit();
	    
	    return temp;
    }
   
 public void traces(Node root,Machine machine, Session session,List lista) {
    	
    	Node node = root;
    	//Node ant = root;
    	no n ;  	
    	
    	for( int i =0; i< machine.transitions(node).size();i++) {
    		 n = new no(); 
    		n.origem = node.hashCode();
    		n.evento = session.uncompileEvent(machine.transitions(node).get(i).event()).toString();
    		Node tmp = machine.transitions(node).get(i).destination();
    		n.destino = tmp.hashCode();
    		//System.out.print(n.destino);
    	 //	System.out.print("(");
    		//System.out.print(n.origem + ",");    
            //System.out.print(n.evento);  
            //System.out.println("," + tmp.hashCode() + " ),");
        	lista.add(node);
        	path.add(n);
        	//CompiledEventList a = machine.alphabet(true);       
        	
        	if (!lista.contains(tmp))    	        	
        	{        		
        		this.traces(tmp,machine,session,lista);
        	}
    	} 
        	
}
*/
    	
   class no{
	   
	   int origem;
	   int destino;
	   String evento;
	 
	   
	  @Override
	   public boolean equals(Object o) {
	   if (this.origem == ((no) o).origem 
	     && (this.evento).equals(((no) o).evento)
	     && this.destino == ((no) o).destino 
		  )
		   return true;
				   
		   else
			   
			return false;
	  }
   } 
   
   
   class novisitado {
	   int origem;
	   ArrayList<no> visitou = new ArrayList<no>();
	   
   }
   
   

   public void varre() {
		
	 	 for (int i =0; i<path.size();i++) {
	 	    ArrayList<no> primeiro = new ArrayList<no>();
	 	 		primeiro.add(path.get(i));
	 	 		ehtau(primeiro, path.get(i).origem);
		 primeiro =null;
		 
	 }
	 	 
   }	
   
   private ArrayList<no> proximo(no init,List<no> path){
	   ArrayList<no> proximos= new ArrayList<no>();
	//   no tn = new no();
	   for(int j =0; j<path.size() ;j++) {
		    if(init.destino == path.get(j).origem) {
		    	if(!proximos.contains(path.get(j))) {
		    	//	System.out.println(path.get(j).evento + "' "  + path.get(j).destino);
		    	proximos.add(path.get(j));
		    }
		}   
	   } 
	   return proximos;   
   }
   
   
	private void ehtau(ArrayList<no> i, int origem) { 
	 		
		no e = new no();
		//no temp = new no();
		ArrayList<no> proximos;
		ArrayList<no> visitado_temp; 
		if(i !=null) {
		
		for(int j =0 ; j <i.size(); j++) {
			
			visitado_temp = getVisitados(origem);			
			
			if ( i.get(j).evento.equals(new String("τ")))  {
				// varreu(i.get(j).destino) ||
					
				if ( (i.get(j).origem == i.get(j).destino) ||
					           passou(visitado_temp,i.get(j).destino)) {
					                 // nao coloca no array principal 
				
				    //skip
					
					        
					}  else {
						
						if(!pathsemtau.contains(i.get(j))) {
							 pathsemtau.add(i.get(j));
						      
						 } 
					
						
						
					}
			  
			
			   if(!visitado_temp.contains(i.get(j)) ||  visitado_temp.isEmpty() || visitado_temp == null)
					
		            { addVisitados(origem, i.get(j));
				           proximos =proximo(i.get(j),path);
				             if(!proximos.isEmpty()) {
				             ehtau(proximos,origem);
				     }
				
				} 
			
			
			}
			
			else {
				
			      if(!visitado_temp.contains(i.get(j)) ||  visitado_temp.isEmpty() || visitado_temp == null)
					
			      { addVisitados(origem, i.get(j));
					
					  proximos =proximo(i.get(j),path);
					  if(!proximos.isEmpty()) {
					      ehtau(proximos,origem);
					  }
					
					} 
				
				
				if(!pathsemtau.contains(i.get(j))) {
					 pathsemtau.add(i.get(j));
				      
				 } 
				}   
			
			}	   
			  
			  

		

 }

	}     
	 
	
	 private ArrayList<no> getVisitados(int param){
		   ArrayList<no> retorno= new ArrayList<no>();
		   for(int j =0; j<visitados.size() ;j++ )
		   {
			   
			if( visitados.get(j).origem == param) {
				retorno = visitados.get(j).visitou;
			   break;
			}
			
			   
		   }
		
		   return retorno;
	   }	
	 
	 
	 private boolean varreu(int destino) {
		 boolean retorno = false;
		 for (int i =0; i<pathsemtau.size();i++) {
			 
			 if( pathsemtau.get(i).origem==destino) {
					 retorno = true;
					 break;
			 }
		 }
		 
		 return retorno;
		 
	 }
	 
	 
	  public boolean  passou(List<no> path, int noh){
		  boolean retorno = false;
		  for(int i =0; i<path.size();i++) {
			  
			  if(path.get(i).origem ==noh)
				  retorno = true;
		  }
		  
		  return retorno;
		   
	   }
	  
	  public void addVisitados( int param, no adiciona ) {
		  boolean achou = false;
		   for(int j =0; j<visitados.size() ;j++ )
		   {
			   if( visitados.get(j).origem == param) {
				     visitados.get(j).visitou.add(adiciona);
				     achou = true;
			         break;
				}
		   }
	    
		   if( achou == false) {
			   novisitado novo = new novisitado();
			   novo.origem = param;
			   novo.visitou.add(adiciona);
			   visitados.add(novo);
			   
		   }
			   
	   }

	   
}
   
 
