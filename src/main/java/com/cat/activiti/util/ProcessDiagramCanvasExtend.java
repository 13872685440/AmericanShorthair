package com.cat.activiti.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.activiti.image.util.ReflectUtil;

public class ProcessDiagramCanvasExtend extends DefaultProcessDiagramCanvas {

	public ProcessDiagramCanvasExtend(int width, int height, int minX, int minY, String imageType) {
		super(width, height, minX, minY, imageType);
	}

	public ProcessDiagramCanvasExtend(int width, int height, int minX, int minY, String imageType,
			String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
		super(width, height, minX, minY, imageType, activityFontName, labelFontName, annotationFontName,
				customClassLoader);
	}

	@Override
	public void initialize(String imageType) {
		if ("png".equalsIgnoreCase(imageType))
			this.processDiagram = new BufferedImage(this.canvasWidth, this.canvasHeight, 2);
		else {
			this.processDiagram = new BufferedImage(this.canvasWidth, this.canvasHeight, 1);
		}

		this.g = this.processDiagram.createGraphics();
		if (!"png".equalsIgnoreCase(imageType)) {
			this.g.setBackground(new Color(255, 255, 255, 0));
			this.g.clearRect(0, 0, this.canvasWidth, this.canvasHeight);
		}

		this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.g.setPaint(Color.black);

		Font font = new Font(this.activityFontName, 1, 11);
		this.g.setFont(font);
		this.fontMetrics = this.g.getFontMetrics();

		LABEL_FONT = new Font(this.labelFontName, 2, 10);
		try {
			USERTASK_IMAGE = ImageIO.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/user.png"));
			SCRIPTTASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/script.png"));
			SERVICETASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/service.png"));
			RECEIVETASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/receive.png"));
			SENDTASK_IMAGE = ImageIO.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/send.png"));
			MANUALTASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/manual.png"));
			BUSINESS_RULE_TASK_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/business_rule.png"));
			TIMER_IMAGE = ImageIO.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/timer.png"));
			ERROR_THROW_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/error_throw.png"));
			ERROR_CATCH_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/error_catch.png"));
			SIGNAL_CATCH_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/signal_catch.png"));
			SIGNAL_THROW_IMAGE = ImageIO
					.read(ReflectUtil.getResource("org/activiti/engine/impl/bpmn/deployer/signal_throw.png"));
		} catch (IOException e) {
			LOGGER.warn("Could not load image for process diagram creation: {}", e.getMessage());
		}
	}

}
