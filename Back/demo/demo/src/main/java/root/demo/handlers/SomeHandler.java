package root.demo.handlers;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// radi sa task-om, pa mora da implementira TaskListener
@Service
public class SomeHandler implements TaskListener {
	
	@Autowired
	IdentityService identityService;
	
	// okine se kad se napravi
	  public void notify(DelegateTask delegateTask) {
		  System.out.println("Kreiran prvi task");
		  
		  // izvucem usere iz baze
		  List<User> users = identityService.createUserQuery().userIdIn("pera", "mika", "zika").list();
			
		  users = identityService.createUserQuery().list();
		  
		  // task na koji se okinuo ovaj Listener
		  DelegateExecution execution = delegateTask.getExecution();
		  
		  // postavim Execution variablu na users
		  // ** SVI OBJEKTI MORAJU BITI SERIALIZABLE
		  execution.setVariable("users", users);
		  
	  }
}