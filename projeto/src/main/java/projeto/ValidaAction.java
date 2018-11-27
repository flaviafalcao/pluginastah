package projeto;

import javax.swing.JFrame;

import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate.UnExpectedException;
import com.change_vision.jude.api.inf.ui.IWindow;

import parser.Principal;
import ui.ValidaDialog;

public class ValidaAction implements IPluginActionDelegate {

	@Override
	public Object run(IWindow arg0) throws UnExpectedException {
		try {
			
			
			//frame que gera o csp
			ValidaDialog valida = new ValidaDialog((JFrame) arg0.getParent(), true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
