package parser;

public class MyTransition {
	
	
		
		private String source;
		private String[] action; // pode ser uma lista de actions
		   // uma action pode ser uma atribuicao
		// uma operacao
		// um sinal 
		private String target;
		private String[] guard; // pode ser uma lista de guardas
		
		public MyTransition (String source,
		String[] action,
		String target,String[] guard){
			
			this.setSource(source);
			this.setAction(action);
			this.setTarget(target);
			this.setGuard(guard);
			
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String[] getAction() {
			return action;
		}

		public void setAction(String[] action) {
			this.action = action;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}

		public String[] getGuard() {
			return guard;
		}

		public void setGuard(String[] guard) {
			this.guard = guard;
		}
		
	}


