package parser;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.change_vision.jude.api.inf.model.IStateMachineDiagram;
import com.change_vision.jude.api.inf.model.ITransition;
import com.change_vision.jude.api.inf.model.IVertex;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;
import com.change_vision.jude.api.inf.project.ProjectEvent;
import com.change_vision.jude.api.inf.project.ProjectEventListener;
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;
import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IState;
import com.change_vision.jude.api.inf.model.IStateMachine;

public class STMView extends JPanel implements IPluginExtraTabView,
ProjectEventListener {

	private ProjectAccessor projectAccessor;
	private JTextArea area;
	private static ArrayList<MyTransition>  trs;
    String nome;
    String processo;
    
    private StringBuilder parser = new StringBuilder();
    
    public STMView() {
    	try {
    		//String uri_local = uri;
    		projectAccessor = AstahAPI.getAstahAPI().getProjectAccessor();
    			// projectAccessor.open(uri, true);
 		    
 		     
 		 	}
    	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
	public STMView(String a) {
		try {
			//projectAccessor = AstahAPI.getAstahAPI().getProjectAccessor();
		   // projectAccessor.open("C:/Users/flavi/eclipse-workspace/ref/src/main/resources/modelo.asta");
			projectAccessor = ProjectAccessorFactory.getProjectAccessor();
			INamedElement[] foundElements = findStateMachine();
			for (INamedElement element : foundElements) {
				IStateMachineDiagram stateMachineDiagram = castStateMachineDiagaram(element);
				if (stateMachineDiagram == null) {
					continue;
				}
				IStateMachine machine = stateMachineDiagram.getStateMachine();
				nome =machine.getName() ;
				showStateMachine(machine);
		} 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initComponents();
	}
	
	private void initComponents() { try {
		setLayout(new GridBagLayout());	
	    area = new JTextArea();
	 	this.add(area);
	    area.setColumns(2000);
	    area.setRows(10000);
	    area.setBounds(2000, 1000, 2000, 1000);
	    area.setVisible(true); 
	    
  }
  catch(Exception e) {}
	    
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
		return "State Machine View";
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "State Machine View";
	}
	
	
	//metodo para capiturar state machine
	
	/**
	 * Statemachine Diagram
	 * @param projectAccessor
	 * @return 発見したモデル
	 * @throws Exception
	 */
	public  INamedElement[] findStateMachine() throws Exception {
		    INamedElement[] foundElements = projectAccessor.findElements(new ModelFinder() {
			public boolean isTarget(INamedElement namedElement) {
				return namedElement instanceof IStateMachineDiagram; 
			}
		});
		return foundElements;
	}
	
	
	public IStateMachineDiagram castStateMachineDiagaram(
			INamedElement element) {
		IStateMachineDiagram stateMachineDiagram = null;
		if (element instanceof IStateMachineDiagram) {
			stateMachineDiagram = (IStateMachineDiagram) element;
		}
		return stateMachineDiagram;
	}
	
	
	public  StringBuilder showStateMachine(IStateMachine machine) {
		return showTransitions(machine);
		}

	
	
	
	private  void showVertex(IVertex vertex) {
		System.out.println("vertex : " + vertex);
		showIncoming(vertex);
		showOutgoing(vertex);
		if (vertex instanceof IState) {
			IState state = (IState) vertex;
			showState(state);
		}
		}
	
	
	private void showState(IState state) {
		System.out.println("found vertex is a state.");
		String entry = state.getEntry();
		System.out.println("entry: " + entry);
		String doActivity = state.getDoActivity();
		System.out.println("do activity: " + doActivity);
		String exit = state.getExit();
		System.out.println("exit: " + exit);
		IVertex[] subvertexes = state.getSubvertexes();
		for (IVertex subvertex : subvertexes) {
						System.out.println("found sub vertex");
			showVertex(subvertex);
			
		}
		IStateMachine submachine = state.getSubmachine();
		if (submachine != null) {
			
			System.out.println("found sub machine");
			showStateMachine(submachine);
		}
	}
	
	
	private static void showIncoming(IVertex vertex) {
		System.out.println("incoming start.");
		ITransition[] incomings = vertex.getIncomings();
		for (ITransition incoming : incomings) {
			System.out.println(incoming);
		}
		System.out.println("incoming end.");
	}
	
	
	/**
	 * Vertex
	 * @param vertex
	 */
	private static void showOutgoing(IVertex vertex) {
		System.out.println("outgoing start.");
		ITransition[] getOutgoings = vertex.getOutgoings();
		for (ITransition outgoing : getOutgoings) {
			System.out.println(outgoing);
		}
		System.out.println("outgoing end.");
	}

	/**
	 * 
	 */
	
	private  StringBuilder showTransitions(IStateMachine machine) {
		StringBuilder f_return = new StringBuilder();
		//Declarations declaration = Declarations.getInstance();
		nome =machine.getName() ;
		//System.out.println("Transition start.");
		ITransition[] transitions = machine.getTransitions();
		trs= new ArrayList<MyTransition>();
		
		
		for (ITransition transition : transitions) {
			
			//actions divididas por ; 
			String temp_action =  transition.getAction().toString();
			String[] tem_array_action =  temp_action.split(";|;\\s");  //  \\s -> espaço em branco
			
			String temp_guard = transition.getGuard().toString();
			String[] temp_array_guard = temp_guard.split(";|;\\s");
			//System.out.println(" guarda" + transition.getGuard());
			
		    trs.add(new MyTransition(transition.getSource().getName(),
				                               tem_array_action,
					              transition.getTarget().getName(),
					              temp_array_guard));
		    
		    SplitChannelPort(tem_array_action); 
		}
		
		
		processo = (montaProcesso(trs));
		parser.append(processo);
		f_return = f_return.append(processo);
		
	 return f_return;	
	}
	
	
	public void SplitChannelPort( String[] actions) {
	    //na lista de transições  recupear todas as actions
		
		String[] part_action;
		Declarations declaration = Declarations.getInstance();
		for(int i =0; i<trs.size();i++) {
			
			actions = trs.get(i).getAction();
			 for(int j =0; j < actions.length ;j++) {
			    	 //System.out.println(actions.length);
			    	 //System.out.println(Arrays.toString(actions));
			    	// System.out.println(actions[j]);
			    	 part_action = actions[j].split("\\.");
			 //   	 System.out.println((String)part_action[0]);
			    	    if(!part_action[0].isEmpty()
			    	    		&& (!declaration.getPortas().contains(new Port((String)part_action[0])))) {
			    	    declaration.addPort(new Port((String)part_action[0]));
			    	    }
			}
		}
		
	}
	
	public String SplitChannelPort(String action) {
		String retorno;
		String[] part_action;
	    part_action = action.split("\\.");
	    retorno = part_action[0] +".id." + part_action[1];
	    return retorno;
		
	}
	
	public String r_portas() {
		
		Declarations declaration = Declarations.getInstance();
		ArrayList<Port> ports = declaration.getPortas();
		HashSet<String> hash = new HashSet<String>(); //= new HashSet<Port>(ports);
		
		String result ="";
		
		for(int i=0;i<ports.size();i++) {	
			hash.add(ports.get(i).getName());
		}
		Iterator<String> iterator = hash.iterator();
		while(iterator.hasNext()) {
			result = result + "channel " + (String)iterator.next() + ":id.operation;"+ "\n";
			
		}
		//System.out.println(hash.size());
		
			return result;
		}
	 
	public  String montaProcesso(ArrayList<MyTransition> trs) {
		String retorno ="";
		String processoI ="";
		String processo ="";
		String processoname ="";
		String escolha = "";
		String processocompleto ="";
		ArrayList<MyTransition> visitou = new ArrayList<MyTransition>();
		
		
		for( int i=0; i<trs.size();i++) {
			
			//System.out.println( "guarda " + Arrays.toString(trs.get(i).guard));
			
			 if(trs.get(i).getSource().startsWith("Initial")) {
				 String temp = "";
				 int index = 0;
				 
				 for (String string : trs.get(i).getGuard()) {
					 index = index + 1 ;
					 
					 if (index > 1) {
						 
						 temp  = temp + " & " + string + "";
					 }
					 else {
						 
						 temp  = temp + string + ""; 
					 }
						 
					 
					 //System.out.println("......." + temp);
					}
				 if (temp.length() >0)
				    temp = temp + " ->"; 
				
			
				processoI = temp + trs.get(i).getTarget();
				processo ="";
				}
			else {
				
				
				processoname = trs.get(i).getSource();
				
				if(!visitou.contains(trs.get(i))){
				visitou.add(trs.get(i));
			    
				escolha ="";
				// tipos de action
				// pode ser uma atribuição
				// uma operação
				// um sinal 
				
				for(int j =0; j<trs.size();j++) {
					
					if(trs.get(j).getSource()==processoname &&  !visitou.contains(trs.get(j))) {
						
						// há escolha externa
						
						 escolha = escolha + "[]" + "\n";
						 // ver actions 
						 // operação
						 
						 String temp = "";
						 
						 for (String string : trs.get(j).getAction()) {
							 temp  = temp + SplitChannelPort(string) + "_I ->" + SplitChannelPort(string) +"_O ->";
							}
						    		 
								 
						escolha = escolha + temp  + trs.get(j).getTarget() +"(id)" ;
					  
					    visitou.add(trs.get(j));
					  
						
					}
				}
			
				 String temp = "";
				 	 for (String string : trs.get(i).getAction()) {
					 temp  = temp + SplitChannelPort(string) + "_I ->" + SplitChannelPort(string) +"_O ->";
					}
		
				
				processo = processoname + "(id) = " + temp
				//+ trs.get(i).action+"_I ->" + trs.get(i).action +"_O ->"
						+ trs.get(i).getTarget() +"(id)"+ escolha + "\n" ;
				
				processocompleto = processocompleto+ processo;
				}	
				 
		    
			}
			}
		
		retorno = 	nome + "(id) = " + processoI  +"(id)\n" + processocompleto;
		
		//adiciona uma statemachine do array de state machine
		//---------------------------------------
		Declarations declaration = Declarations.getInstance();
		ArrayList<StateMachine> stateMachine = declaration.getStateMachine();
		String name_comp = splitNameStm(nome);
		StateMachine temp_stm  = new StateMachine(nome +"(id)" ,name_comp);
		stateMachine.add(temp_stm);
		//---------------------------------------------------------------
		
		return retorno;
		
	}

	
	public StringBuilder getParser() {
		return parser;
	}

	public void setParser(StringBuilder parser) {
		this.parser = parser;
	}
	
	//split nome da maquina de estados e nome do component
	
	public String splitNameStm(String name) {
	
	String[] splitname =  name.split("_");
	return splitname[1];
	
	}   
    
}


