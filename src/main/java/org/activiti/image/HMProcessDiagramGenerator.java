package org.activiti.image;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.image.ProcessDiagramGenerator;

public abstract interface HMProcessDiagramGenerator extends ProcessDiagramGenerator {
    public abstract InputStream generateDiagram(BpmnModel paramBpmnModel, String paramString1, List<String> paramList1,
            List<String> paramList2, String paramString2, String paramString3, String paramString4,
            ClassLoader paramClassLoader, double paramDouble);

    public abstract InputStream generateDiagram(BpmnModel paramBpmnModel, String paramString1, List<String> paramList1,
            List<String> paramList2, String paramString2, String paramString3, String paramString4,
            ClassLoader paramClassLoader, double paramDouble, List<String> paramList3);

    public abstract InputStream generateDiagram(BpmnModel paramBpmnModel, String paramString, List<String> paramList1,
            List<String> paramList2);

    public abstract InputStream generateDiagram(BpmnModel paramBpmnModel, String paramString, List<String> paramList1,
            List<String> paramList2, double paramDouble);

    public abstract InputStream generateDiagram(BpmnModel paramBpmnModel, String paramString, List<String> paramList);

    public abstract InputStream generateDiagram(BpmnModel paramBpmnModel, String paramString, List<String> paramList,
            double paramDouble);

    public abstract InputStream generateDiagram(BpmnModel paramBpmnModel, String paramString1, String paramString2,
            String paramString3, String paramString4, ClassLoader paramClassLoader);

    public abstract InputStream generateDiagram(BpmnModel paramBpmnModel, String paramString1, String paramString2,
            String paramString3, String paramString4, ClassLoader paramClassLoader, double paramDouble);

    public abstract InputStream generatePngDiagram(BpmnModel paramBpmnModel);

    public abstract InputStream generatePngDiagram(BpmnModel paramBpmnModel, double paramDouble);

    public abstract InputStream generateJpgDiagram(BpmnModel paramBpmnModel);

    public abstract InputStream generateJpgDiagram(BpmnModel paramBpmnModel, double paramDouble);

    public abstract BufferedImage generatePngImage(BpmnModel paramBpmnModel, double paramDouble);
}