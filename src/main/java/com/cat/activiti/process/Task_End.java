package com.cat.activiti.process;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cat.activiti.enumable.TaskType;
import com.cat.activiti.model.TaskExt;
import com.cat.activiti.process.BaseTaskListener;
import com.cat.boot.jsonbean.BaseAppBean;
import com.cat.boot.util.NameQueryUtil;

@Service("task_end")
public class Task_End extends BaseTaskListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4023920931711215500L;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void notify(DelegateExecution delegateexecution) throws Exception {
		Map<String, Object> maps = delegateexecution.getVariables();
		maps.replace("shyj", "流程结束");
		maps.replace("shjl", "结束");
		saveEntity(delegateexecution, null, maps);
		save审核历史(delegateexecution.getCurrentActivityName(), maps, true);

		TaskExt taskext = (TaskExt) baseService.getSimple("TaskExt", null, true,
				NameQueryUtil.setParams("processInstanceId", delegateexecution.getProcessInstanceId()));
		taskext.setTaskType(TaskType.已办);
		baseService.save(taskext, BaseAppBean.iniBean(maps));
	}

}
