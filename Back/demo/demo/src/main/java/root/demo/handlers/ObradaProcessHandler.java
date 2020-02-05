package root.demo.handlers;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import root.demo.model.Casopis;
import root.demo.repository.CasopisRepository;

public class ObradaProcessHandler implements ExecutionListener{

	@Autowired
	IdentityService identityService;
	
	@Autowired
	CasopisRepository casopisRepository ;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		List<Casopis> casopis = casopisRepository.findAll();
		
	}

}
