package com.cat.activiti.process;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cat.activiti.model.TaskExt;

@Service("task_sp")
public class Task_Sp extends BaseTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2496027746917963385L;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void notify(DelegateTask delegatetask) {
		String eventName = delegatetask.getEventName();
		if ("create".equals(eventName)) {
			Map<String, Object> maps = delegatetask.getVariables();
			// get流程变量(delegatetask.getProcessInstanceId());
			TaskExt task = save待办任务(delegatetask, maps);
			// 改变实体状态
			saveEntity(delegatetask, task, maps);
		} else if ("complete".equals(eventName)) {
			// 完成时生成任务
			Map<String, Object> maps = delegatetask.getVariables();
			// get流程变量(delegatetask.getProcessInstanceId());
			save审核历史(delegatetask.getName(), maps, false);

			// 将当前处理人加入流程变量
			processContorller.set流程变量(delegatetask.getProcessInstanceId(), getVariable().getValue(delegatetask),
					maps.get("userId"));
		}
	}

}
