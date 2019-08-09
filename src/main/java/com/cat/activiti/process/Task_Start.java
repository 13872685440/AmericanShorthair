package com.cat.activiti.process;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("task_start")
public class Task_Start extends BaseTaskListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4023920931711215500L;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void notify(DelegateExecution delegateexecution) throws Exception {
		Map<String, Object> maps = delegateexecution.getVariables();
		save审核历史(delegateexecution.getCurrentActivityName(), maps, true);
		// 将申请人加入流程变量
		processContorller.set流程变量(delegateexecution.getProcessInstanceId(), getVariable().getValue(delegateexecution),
				maps.get("userId"));
	}

}
