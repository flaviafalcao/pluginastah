package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;
import com.change_vision.jude.api.inf.project.ProjectEvent;
import com.change_vision.jude.api.inf.project.ProjectEventListener;
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;

public class HelloWorldView extends JPanel implements IPluginExtraTabView, ProjectEventListener {

    public HelloWorldView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(createLabelPane(), BorderLayout.CENTER);
        addProjectEventListener();
    }

  private void addProjectEventListener() {
    try {
      ProjectAccessor projectAccessor = ProjectAccessorFactory.getProjectAccessor();
      projectAccessor.addProjectEventListener(this);
    } catch (ClassNotFoundException e) {
      e.getMessage();
    }
  }

  private Container createLabelPane() {
      JLabel label = new JLabel("hello world");
        JScrollPane pane = new JScrollPane(label);
        return pane;
    }

    @Override
    public void projectChanged(ProjectEvent e) {
    }

    @Override
    public void projectClosed(ProjectEvent e) {
    }

     @Override
    public void projectOpened(ProjectEvent e) {
    }

  @Override
  public void addSelectionListener(ISelectionListener listener) {
  }

  @Override
  public Component getComponent() {
    return this;
  }

  @Override
  public String getDescription() {
    return "Show Hello World here";
  }

  @Override
  public String getTitle() {
    return "Hello World View";
  }

  public void activated() {

  }

  public void deactivated() {

  }
}
