package fdr;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;



public class FdrWrapper {

    private static FdrWrapper instance;

    private URLClassLoader urlCl;

    private Class<?> fdrClass;

    private Class<?> sessionClass;

    private Class<?> assertionClass;

    private Class<?> counterexampleClass;

    private Class<?> traceCounterexampleClass;

    private Class<?> debugContextClass;

    private Class<?> refinementCounterexampleClass;

    private Class<?> behaviourClass;

    private Class<?> irrelevantBehaviourClass;

    private Class<?> compiledEventListClass;

    private Class<?> TraceBehaviour;

    private Class<?> Node;

    private Class<?> ProcessName;

    private Class<?> Canceller;

    private Class<?> Machine;

    private Class<?> TransitionList;

    private Class<?> Transition;
    
    private Class<?> MachineEvaluatorResult;
    
    private Class<?> SemanticModel;

    private File fdrJar;

    private List<String> classes;

    private Object session; //Objeto -> instancia da classe 
    private List<Object> counterExamples;
    private List<Object> assertions;

    public boolean loadFDR(String path) {

        File file = new File(path);

        if (file.exists()) {

            fdrJar = file;

            return true;

        } else

            return false;

    }

    private FdrWrapper() {

    }

    public static FdrWrapper getInstance() {
        if (instance == null) {
            instance = new FdrWrapper();
            System.out.println("---------------------------------------------------");
        }

        return instance;
    }

    public void loadClasses() throws MalformedURLException, ClassNotFoundException {

        classes = new ArrayList<String>();
        
        System.out.println( "------>"  +   fdrJar.toURI());

        urlCl = new URLClassLoader(new URL[]{fdrJar.toURI().toURL()}, System.class.getClassLoader());

        fdrClass = urlCl.loadClass("uk.ac.ox.cs.fdr.fdr");

        sessionClass = urlCl.loadClass("uk.ac.ox.cs.fdr.Session");

        assertionClass = urlCl.loadClass("uk.ac.ox.cs.fdr.Assertion");

        counterexampleClass = urlCl.loadClass("uk.ac.ox.cs.fdr.Counterexample");

        traceCounterexampleClass = urlCl.loadClass("uk.ac.ox.cs.fdr.TraceCounterexample");

        debugContextClass = urlCl.loadClass("uk.ac.ox.cs.fdr.DebugContext");

        refinementCounterexampleClass = urlCl.loadClass("uk.ac.ox.cs.fdr.RefinementCounterexample");

        behaviourClass = urlCl.loadClass("uk.ac.ox.cs.fdr.Behaviour");

        irrelevantBehaviourClass = urlCl.loadClass("uk.ac.ox.cs.fdr.IrrelevantBehaviour");

        compiledEventListClass = urlCl.loadClass("uk.ac.ox.cs.fdr.CompiledEventList");

        TraceBehaviour = urlCl.loadClass("uk.ac.ox.cs.fdr.TraceBehaviour");

        Node = urlCl.loadClass("uk.ac.ox.cs.fdr.Node");

        ProcessName = urlCl.loadClass("uk.ac.ox.cs.fdr.ProcessName");

        Machine = urlCl.loadClass("uk.ac.ox.cs.fdr.Machine");

        TransitionList = urlCl.loadClass("uk.ac.ox.cs.fdr.TransitionList");

        Transition = urlCl.loadClass("uk.ac.ox.cs.fdr.Transition");
        
        MachineEvaluatorResult = urlCl.loadClass("uk.ac.ox.cs.fdr.MachineEvaluatorResult");
        
        SemanticModel = urlCl.loadClass("uk.ac.ox.cs.fdr.SemanticModel");

        classes.add(fdrClass.getName());

        classes.add(sessionClass.getName());

        classes.add(assertionClass.getName());

        classes.add(counterexampleClass.getName());

        classes.add(traceCounterexampleClass.getName());

        classes.add(debugContextClass.getName());

        classes.add(refinementCounterexampleClass.getName());

        classes.add(behaviourClass.getName());

        classes.add(TraceBehaviour.getName());

        classes.add(Node.getName());

        classes.add(ProcessName.getName());

        classes.add(TransitionList.getName());

        classes.add(Transition.getName());
        
        classes.add(MachineEvaluatorResult.getName());
        
        classes.add(SemanticModel.getName());

        // Classes extras que são usadas como parametro

        Canceller = urlCl.loadClass("uk.ac.ox.cs.fdr.Canceller");

     
    }

    public List<String> getClasses() {

        return classes;

    }

    public Object getSession() {
    	return this.session;
    }
    
    
    
    public void loadFile(String filename) {
        try {
            this.session = sessionClass.newInstance();
            File f = new File(filename);
            if (f.exists()) {
                System.out.println("O arquivo existe");
            }
            invokeProperty(session.getClass(), this.session, "loadFile", String.class, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
   
    
    public List<Object> getAssertions() {
        List<Object> assertions = new ArrayList<>();
        try {
            for (Object assertion : (Iterable<?>) invokeProperty(session.getClass(), this.session, "assertions", null,
                    null)) {
                assertions.add(assertion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assertions;
    }

    public boolean executeAssertions(List<Object> assertions) {
        this.counterExamples = new ArrayList<>();
        boolean isRefinement = true;
        try {
            for (Object assertion : assertions) {
            	System.out.print(assertion);
                invokeProperty(assertion.getClass(), assertion, "execute", Canceller, null);
                for (Object counterExample : (Iterable<?>) invokeProperty(assertion.getClass(), assertion,
                        "counterexamples", null, null)) {
                    this.counterExamples.add(counterExample);
                    isRefinement = false;
                 }
                System.out.println (" --> " +isRefinement);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return isRefinement;
    }

    public List<Object> getCounterExamples() {
        return this.counterExamples;
    }

    public String getErrorEvent(Object counterExample) {
        String errorEvent = "";
        try {
            Object error = invokeProperty(traceCounterexampleClass, counterExample, "errorEvent", null, null);
            if ((Long) error != 1 && (Long) error != 0) {
                errorEvent = invokeProperty(sessionClass, this.session, "uncompileEvent", long.class, (Long) error).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorEvent;
    }

    public String getTraceSpecificationBehaviour(Object counterExample) {
        String result = "";
        result = getBehaviour(counterExample, "specificationBehaviour");

        return result;
    }

    private String getBehaviour(Object counterExample, String type) {
        try {
            Field IMPL_LOOKUP = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            IMPL_LOOKUP.setAccessible(true);
            MethodHandles.Lookup lkp = (MethodHandles.Lookup) IMPL_LOOKUP.get(null);
            MethodHandle h1 = lkp.findSpecial(refinementCounterexampleClass, type,
                    MethodType.methodType(behaviourClass), traceCounterexampleClass);
            Object behaviour = h1.invoke(counterExample);
            type = traceBehaviour(behaviour);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return type;
    }

    public String getTraceImplementationBehaviour(Object counterExample) {
        String result = "";
        result = getBehaviour(counterExample, "implementationBehaviour");
        return result;
    }
//
//    public List<String> strictCounterExample(Object counterExample, List<String> trace) throws Exception {
//        StringBuilder sb = new StringBuilder();
//
//        // Adiciona o trace do contraExemplo
//        if (counterExample.getClass().getName().equals(traceCounterexampleClass.getName())) {
//            Field IMPL_LOOKUP = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
//            IMPL_LOOKUP.setAccessible(true);
//            MethodHandles.Lookup lkp = (MethodHandles.Lookup) IMPL_LOOKUP.get(null);
//            MethodHandle h1 = lkp.findSpecial(refinementCounterexampleClass, "specificationBehaviour",
//                    MethodType.methodType(behaviourClass), traceCounterexampleClass);
//            Object behaviour = null;
//            try {
//                behaviour = h1.invoke(counterExample);
//                traceBehaviour(behaviour, sb, session);
//                trace.add(sb.toString());
//                //System.out.println(sb.toString());
//                sb = new StringBuilder();
//                h1 = lkp.findSpecial(refinementCounterexampleClass, "implementationBehaviour",
//                        MethodType.methodType(behaviourClass), traceCounterexampleClass);
//                behaviour = h1.invoke(counterExample);
//                traceBehaviour(behaviour);
//                trace.add(sb.toString());
//            } catch (Throwable e) {
//                e.printStackTrace();
//            }
//        }
//        return trace;
//    }

    public List<String> traceCounterExample(Object counterExample) {
        List<String> trace = new ArrayList<>();
        try {
            Constructor[] constructors = debugContextClass.getConstructors();
            Constructor constructor = null;
            for (int i = 0; i < constructors.length; i++) {
                Class[] parameters = constructors[i].getParameterTypes();
                if (parameters[0].getName().equals(refinementCounterexampleClass.getName())) {
                    constructor = constructors[i];
                }
            }
            Object debugContext = constructor.newInstance(counterExample, true);
            invokeProperty(debugContextClass, debugContext, "initialise", Canceller, null);
            for (Object behaviour : (Iterable<?>) invokeProperty(debugContextClass, debugContext, "rootBehaviours",
                    null, null)) {
                trace.add(describeBehaviour(behaviour));
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trace;
    }

    private String describeBehaviour(Object behaviour) throws Exception {

        StringBuilder sb = new StringBuilder();

        for (Long event : (Iterable<Long>) invokeProperty(behaviourClass, behaviour, "trace", null, null)) {
            if (event == 1 || event == 0) {
                // sb.append("-, ");
            } else {
                Object result = invokeProperty(sessionClass, this.session, "uncompileEvent", long.class, event);
                sb.append(result.toString() + ", ");
            }
        }
        return sb.toString();
    }

    private String traceBehaviour(Object behaviour) throws Exception {
        StringBuilder sb = new StringBuilder();
        Object machine = invokeProperty(behaviourClass, behaviour, "machine", null, null);
        Object node = invokeProperty(Machine, machine, "rootNode", null, null);
        Object transitionList;
        while (true) {
            transitionList = invokeProperty(Machine, machine, "transitions", Node, node);
            Object evento = invokeProperty(TransitionList, transitionList, "get", int.class, 0);
            Object eventID = invokeProperty(Transition, evento, "event", null, null);
            Object result = invokeProperty(sessionClass, this.session, "uncompileEvent", long.class, (Long) eventID);
            // System.out.println(result.toString());
            if (!result.equals("τ")) {
                sb.append(result.toString());
            }
            if (result.toString().equals("endInteraction"))
                break;
            sb.append(", ");
            node = invokeProperty(Transition, evento, "destination", null, null);
        }
        return sb.toString();
    }
   
    public Object getMachineEvaluatorResult( String processName) throws Exception {
    	
    	
     Method method, method1,method2, method3;
     Object machine = null;
     
     try {
    	 
    	 ///SemanticModel Traces
    	 
    	 method = SemanticModel.getMethod("swigToEnum",int.class);
    	 method.setAccessible(true);
    	 
    	 Object o = method.invoke(SemanticModel, 1);
    	 
    	 
  	  Class[] cArg = new Class[3];
    	 cArg[0]  =  String.class;
    			cArg[1] = SemanticModel;
    					 cArg[2] =Canceller;
       if(processName !=null) {
    	  method1 = session.getClass().getMethod("evaluateProcess", cArg);
    	  method1.setAccessible(true);
    	  
  	    Object mEvaluatorResult = method1.invoke(this.session,processName, o , Canceller.newInstance() );

 
          method2 = MachineEvaluatorResult.getMethod("result");
     	  method2.setAccessible(true);
     	  machine = method2.invoke(mEvaluatorResult);
    	  return machine;
      }
    	  
     }
     catch(Exception e) {
    	 e.printStackTrace();
     }
    	
     return machine;
    	
    }
    
    
    public Object getrootNode(Object machine) {
    	
    	Object node = null;
    	Method method;
    	try {
    		
         method = Machine.getMethod("rootNode");
         method.setAccessible(true);
         node = method.invoke(machine);	
    	}
    	catch(Exception e) {
    		
    		e.printStackTrace();
    	}
    	
    	return node;
    }
    
    
    public AbstractList getTransitions(Object machine, Object node) {
    
    AbstractList transitionList = null;
	try {
		transitionList = (AbstractList) invokeProperty(Machine, machine, "transitions", Node, node);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
     return transitionList;
    
    }
    
    
    public Object getTransitionIndex( Object transitionList ,int index) {
    	
    	Object transition = null;
    	try {
    		transition = invokeProperty(TransitionList, transitionList, "get", int.class, index);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
         
         return transition;
    }
    
    
    public Long getEvent(Object transition) {
    	
    	
    	Long event = null;
    	
    	try {
    		event = (Long)invokeProperty(Transition, transition, "event", null, null);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
         
         return event;
    	
    }
    
 public Object getDestination(Object transition) {
    	
    	
    	Object destination = null;
    	try {
    		destination = invokeProperty(Transition, transition, "destination", null, null);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
         
         return destination;
    	
    }
 
 public int getHashCode(Object node) {
	 
		Object hash = 0;
	
    	try {
    		hash =  invokeProperty(Node, node, "hashCode", null, null);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
         
         return new Integer(hash.toString()).intValue();
    
 }
 
 
 public Object getUncompileEvent(Long eventId) {
	 Object result = null;
	 try {
		result = invokeProperty(sessionClass, this.session, "uncompileEvent", long.class, (Long) eventId);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
	 return result;
 }
    
    private static Object invokeProperty(Class<?> dsClass, Object ds, String propertyName, Class<?> paramClass,

                                         Object paramValue) throws Exception {

        Method method;

        try {

            if (paramClass != null) {
                method = dsClass.getMethod(propertyName, paramClass);
                method.setAccessible(true);
                return method.invoke(ds, paramValue);

            } else {

                method = dsClass.getMethod(propertyName);
                method.setAccessible(true);
                return method.invoke(ds);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }

}