package com.cat.activiti.process;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cat.activiti.model.TaskExt;
import com.cat.activiti.process.BaseTaskListener;
import com.cat.boot.util.StringUtil;

@Service("task_hq")
public class Task_Hq extends BaseTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3520023073686979243L;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void notify(DelegateTask delegatetask) {
		String eventName = delegatetask.getEventName();
		if ("create".equals(eventName)) {
			// 获取节点变量
			String node = (String) getNode().getValue(delegatetask);
			Map<String, Object> maps = delegatetask.getVariables();
			// 获取节点变量+_hq 的流程变量
			Integer node_hq = (Integer) maps.get(node + "_hq");
			// 获取会签人
			List<String> list = (List<String>) maps.get(node);
			if (node_hq == null) {
				TaskExt task = save待办任务(delegatetask, delegatetask.getVariables(), list.get(0));
				processContorller.set流程变量(delegatetask.getProcessInstanceId(), node + "_hq", 0);

				if (list.size() == 1) {
					saveEntity(delegatetask, task, maps);
				}
			} else {
				int i = (Integer) maps.get(node + "_hq");
				i++;
				save待办任务(delegatetask, delegatetask.getVariables(), list.get(i));
				processContorller.reset流程变量(delegatetask.getProcessInstanceId(), node + "_hq", i);

				if (i == (list.size() - 1)) {
					TaskExt task = save会签待办任务(delegatetask, maps, StringUtil.listToString(list));
					saveEntity(delegatetask, task, maps);
				}
			}

		} else if ("complete".equals(eventName)) {
			// 完成时生成任务
			Map<String, Object> maps = delegatetask.getVariables();
			save审核历史(delegatetask.getName(), maps, false);
		}
	}
}
