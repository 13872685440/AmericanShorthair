package com.cat.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cat.activiti.jsonbean.ProcessDefinitionBean;
import com.cat.activiti.process.ProcessContorller;
import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.IResultConvert;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseNqtQuery;

@RestController
@Scope("prototype")
@RequestMapping("/processdefinitionbean")
public class ProcessDefinitionBeanQuery extends BaseNqtQuery<ProcessDefinitionBean>
		implements IResultConvert<ProcessDefinitionBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3144787163631435689L;

	@Autowired(required = true)
	public ProcessContorller processContorller;

	@Override
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms) throws Exception {
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, results));
	}

	@RequestMapping(value = "/deploy", method = RequestMethod.POST)
	public String deployProcess(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
			processContorller.部署流程(zipInputStream);
			return "{\"status\": \"sucess\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"status\": \"error\"}";
		}
	}

	@RequestMapping(value = "/viewimage", method = RequestMethod.GET)
	public void viewimage(@RequestParam String deploymentId, @RequestParam String diagramResourceName,
			HttpServletResponse response) {
		try {
			InputStream in = processContorller.get流程图(deploymentId, diagramResourceName);

			byte[] b = new byte[1024];
			int len = -1;
			while ((len = in.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/viewimage_now", method = RequestMethod.GET)
	public void viewimage_now(@RequestParam String processInstanceId, HttpServletResponse response) throws Exception {
		// 获取流程图图像字符流
		InputStream imageStream = processContorller.get流程执行轨迹(processInstanceId);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	@Override
	public List<ProcessDefinitionBean> toResult(List<Object> ls) {
		List<ProcessDefinitionBean> result = new ArrayList<ProcessDefinitionBean>();
		for (Iterator<Object> iterator = ls.iterator(); iterator.hasNext();) {
			Object[] row = (Object[]) iterator.next();
			ProcessDefinitionBean obj = new ProcessDefinitionBean();
			obj.setId((String) row[0]);
			obj.setReversion(((BigDecimal) row[1]).intValue());
			obj.setCategory((String) row[2]);
			obj.setName((String) row[3]);
			obj.setKey((String) row[4]);
			obj.setVersion(((BigDecimal) row[5]).intValue());
			obj.setDeploymentId((String) row[6]);
			obj.setResourceName((String) row[7]);
			obj.setDiagramResourceName((String) row[8]);
			obj.setDescription((String) row[9]);
			obj.setStartFormKey(((BigDecimal) row[10]).intValue() == 0 ? false : true);
			obj.setGraphicalNotation(((BigDecimal) row[11]).intValue() == 0 ? false : true);
			obj.setSuspended(((BigDecimal) row[12]).intValue() == 0 ? false : true);
			obj.setTenantId((String) row[13]);
			ProcessDefinitionEntity ped = processContorller.getProcessDefinitionEntity(obj.getId());
			List<ActivityImpl> activities = ped.getActivities();
			List<String> jds = new ArrayList<String>();
			for (ActivityImpl activityImpl : activities) {
				jds.add((String) activityImpl.getProperty("name"));
			}
			obj.setJds(jds);
			result.add(obj);
		}
		return result;
	}

}
