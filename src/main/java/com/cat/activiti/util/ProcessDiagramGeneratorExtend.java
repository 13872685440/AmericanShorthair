package com.cat.activiti.util;

import java.util.List;

import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Gateway;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

public class ProcessDiagramGeneratorExtend extends DefaultProcessDiagramGenerator {

	@Override
	protected ProcessDiagramCanvasExtend generateProcessDiagram(BpmnModel bpmnModel, String imageType,
			List<String> highLightedActivities, List<String> highLightedFlows, String activityFontName,
			String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor) {
		prepareBpmnModel(bpmnModel);

		ProcessDiagramCanvasExtend processDiagramCanvas = (ProcessDiagramCanvasExtend) initProcessDiagramCanvas(
				bpmnModel, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);

		for (Pool pool : bpmnModel.getPools()) {
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
			processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo);
		}

		for (Process process : bpmnModel.getProcesses()) {
			for (Lane lane : process.getLanes()) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
				processDiagramCanvas.drawPoolOrLane(lane.getName(), graphicInfo);
			}

		}

		for (FlowNode flowNode : ((Process) bpmnModel.getProcesses().get(0)).findFlowElementsOfType(FlowNode.class)) {
			drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, highLightedFlows,
					scaleFactor);
		}

		for (Process process : bpmnModel.getProcesses()) {
			for (Artifact artifact : process.getArtifacts()) {
				drawArtifact(processDiagramCanvas, bpmnModel, artifact);
			}
		}

		return processDiagramCanvas;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void drawActivity(DefaultProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel,
			FlowNode flowNode, List<String> highLightedActivities, List<String> highLightedFlows, double scaleFactor) {
		ActivityDrawInstruction drawInstruction = (ActivityDrawInstruction) this.activityDrawInstructions
				.get(flowNode.getClass());
		if (drawInstruction != null) {
			drawInstruction.draw(processDiagramCanvas, bpmnModel, flowNode);

			boolean multiInstanceSequential = false;
			boolean multiInstanceParallel = false;
			boolean collapsed = false;
			if ((flowNode instanceof Activity)) {
				Activity activity = (Activity) flowNode;
				MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = activity.getLoopCharacteristics();
				if (multiInstanceLoopCharacteristics != null) {
					multiInstanceSequential = multiInstanceLoopCharacteristics.isSequential();
					multiInstanceParallel = !multiInstanceSequential;
				}

			}

			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
			if ((flowNode instanceof SubProcess))
				collapsed = (graphicInfo.getExpanded() != null) && (!graphicInfo.getExpanded().booleanValue());
			else if ((flowNode instanceof CallActivity)) {
				collapsed = true;
			}

			if (scaleFactor == 1.0D) {
				processDiagramCanvas.drawActivityMarkers((int) graphicInfo.getX(), (int) graphicInfo.getY(),
						(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), multiInstanceSequential,
						multiInstanceParallel, collapsed);
			}

			if (highLightedActivities.contains(flowNode.getId())) {
				// processDiagramCanvas.drawHighLight((int) graphicInfo.getX(),
				// (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
				// (int) graphicInfo.getHeight());
			}

		}

		for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
			boolean highLighted = highLightedFlows.contains(sequenceFlow.getId());
			String defaultFlow = null;
			if ((flowNode instanceof Activity))
				defaultFlow = ((Activity) flowNode).getDefaultFlow();
			else if ((flowNode instanceof Gateway)) {
				defaultFlow = ((Gateway) flowNode).getDefaultFlow();
			}

			boolean isDefault = false;
			if ((defaultFlow != null) && (defaultFlow.equalsIgnoreCase(sequenceFlow.getId()))) {
				isDefault = true;
			}
			boolean drawConditionalIndicator = (sequenceFlow.getConditionExpression() != null)
					&& (!(flowNode instanceof Gateway));

			String sourceRef = sequenceFlow.getSourceRef();
			String targetRef = sequenceFlow.getTargetRef();
			FlowElement sourceElement = bpmnModel.getFlowElement(sourceRef);
			FlowElement targetElement = bpmnModel.getFlowElement(targetRef);
			List graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
			if ((graphicInfoList != null) && (graphicInfoList.size() > 0)) {
				graphicInfoList = connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement,
						targetElement, graphicInfoList);
				int[] xPoints = new int[graphicInfoList.size()];
				int[] yPoints = new int[graphicInfoList.size()];

				for (int i = 1; i < graphicInfoList.size(); i++) {
					GraphicInfo graphicInfo = (GraphicInfo) graphicInfoList.get(i);
					GraphicInfo previousGraphicInfo = (GraphicInfo) graphicInfoList.get(i - 1);

					if (i == 1) {
						xPoints[0] = ((int) previousGraphicInfo.getX());
						yPoints[0] = ((int) previousGraphicInfo.getY());
					}
					xPoints[i] = ((int) graphicInfo.getX());
					yPoints[i] = ((int) graphicInfo.getY());
				}

				processDiagramCanvas.drawSequenceflow(xPoints, yPoints, drawConditionalIndicator, isDefault,
						highLighted, scaleFactor);

				GraphicInfo labelGraphicInfo = bpmnModel.getLabelGraphicInfo(sequenceFlow.getId());
				if (labelGraphicInfo != null) {
					processDiagramCanvas.drawLabel(sequenceFlow.getName(), labelGraphicInfo, false);
				}
			}

		}

		if ((flowNode instanceof FlowElementsContainer))
			for (FlowElement nestedFlowElement : ((FlowElementsContainer) flowNode).getFlowElements())
				if ((nestedFlowElement instanceof FlowNode))
					drawActivity(processDiagramCanvas, bpmnModel, (FlowNode) nestedFlowElement, highLightedActivities,
							highLightedFlows, scaleFactor);
	}

	protected static ProcessDiagramCanvasExtend initProcessDiagramCanvas(BpmnModel bpmnModel, String imageType,
			String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
		double minX = 1.7976931348623157E+308D;
		double maxX = 0.0D;
		double minY = 1.7976931348623157E+308D;
		double maxY = 0.0D;

		for (Pool pool : bpmnModel.getPools()) {
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
			minX = graphicInfo.getX();
			maxX = graphicInfo.getX() + graphicInfo.getWidth();
			minY = graphicInfo.getY();
			maxY = graphicInfo.getY() + graphicInfo.getHeight();
		}

		List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
		for (FlowNode flowNode : flowNodes) {
			GraphicInfo flowNodeGraphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());

			if (flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth() > maxX) {
				maxX = flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth();
			}
			if (flowNodeGraphicInfo.getX() < minX) {
				minX = flowNodeGraphicInfo.getX();
			}

			if (flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight() > maxY) {
				maxY = flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight();
			}
			if (flowNodeGraphicInfo.getY() < minY) {
				minY = flowNodeGraphicInfo.getY();
			}

			for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
				List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
				if (graphicInfoList != null) {
					for (GraphicInfo graphicInfo : graphicInfoList) {
						if (graphicInfo.getX() > maxX) {
							maxX = graphicInfo.getX();
						}
						if (graphicInfo.getX() < minX) {
							minX = graphicInfo.getX();
						}

						if (graphicInfo.getY() > maxY) {
							maxY = graphicInfo.getY();
						}
						if (graphicInfo.getY() < minY) {
							minY = graphicInfo.getY();
						}
					}
				}
			}
		}

		List<Artifact> artifacts = gatherAllArtifacts(bpmnModel);
		for (Artifact artifact : artifacts) {
			GraphicInfo artifactGraphicInfo = bpmnModel.getGraphicInfo(artifact.getId());

			if (artifactGraphicInfo != null) {
				if (artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth() > maxX) {
					maxX = artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth();
				}
				if (artifactGraphicInfo.getX() < minX) {
					minX = artifactGraphicInfo.getX();
				}

				if (artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight() > maxY) {
					maxY = artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight();
				}
				if (artifactGraphicInfo.getY() < minY) {
					minY = artifactGraphicInfo.getY();
				}
			}

			List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(artifact.getId());
			if (graphicInfoList != null) {
				for (GraphicInfo graphicInfo : graphicInfoList) {
					if (graphicInfo.getX() > maxX) {
						maxX = graphicInfo.getX();
					}
					if (graphicInfo.getX() < minX) {
						minX = graphicInfo.getX();
					}

					if (graphicInfo.getY() > maxY) {
						maxY = graphicInfo.getY();
					}
					if (graphicInfo.getY() < minY) {
						minY = graphicInfo.getY();
					}
				}
			}
		}

		int nrOfLanes = 0;
		for (Process process : bpmnModel.getProcesses()) {
			for (Lane l : process.getLanes()) {
				nrOfLanes++;

				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(l.getId());

				if (graphicInfo.getX() + graphicInfo.getWidth() > maxX) {
					maxX = graphicInfo.getX() + graphicInfo.getWidth();
				}
				if (graphicInfo.getX() < minX) {
					minX = graphicInfo.getX();
				}

				if (graphicInfo.getY() + graphicInfo.getHeight() > maxY) {
					maxY = graphicInfo.getY() + graphicInfo.getHeight();
				}
				if (graphicInfo.getY() < minY) {
					minY = graphicInfo.getY();
				}
			}

		}

		if ((flowNodes.isEmpty()) && (bpmnModel.getPools().isEmpty()) && (nrOfLanes == 0)) {
			minX = 0.0D;
			minY = 0.0D;
		}

		return new ProcessDiagramCanvasExtend((int) maxX + 10, (int) maxY + 10, (int) minX, (int) minY, imageType,
				activityFontName, labelFontName, annotationFontName, customClassLoader);
	}

}
