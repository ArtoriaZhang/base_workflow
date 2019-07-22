package com.els.base.workflow.common.web.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.els.base.workflow.common.web.controller.RestResourceController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Resource;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestResourceController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(RestResourceController.class);

    final String MODEL_ID = "modelId";
    final String MODEL_NAME = "name";
    final String MODEL_REVISION = "revision";
    final String MODEL_DESCRIPTION = "description";
    final String MODEL_KEY = "key";

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ObjectMapper objectMapper;

    @RequestMapping(value = { "/service/model/{modelId}/save" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, @RequestParam String name, @RequestParam String key,
            @RequestParam String description, @RequestParam String json_xml, @RequestParam String svg_xml)
            throws Exception {
        Model model = this.repositoryService.getModel(modelId);

        ObjectNode modelJson = (ObjectNode) this.objectMapper.readTree(model.getMetaInfo());

        modelJson.put("name", name);
        modelJson.put("description", description);
        modelJson.put("key", key);

        model.setMetaInfo(modelJson.toString());
        model.setName(name);
        model.setKey(key);

        this.repositoryService.saveModel(model);

        this.repositoryService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

        InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
        TranscoderInput input = new TranscoderInput(svgStream);

        PNGTranscoder transcoder = new PNGTranscoder();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(outStream);

        transcoder.transcode(input, output);
        byte[] result = outStream.toByteArray();
        this.repositoryService.addModelEditorSourceExtra(model.getId(), result);
        outStream.close();
    }

    @RequestMapping(value = { "/service/editor/stencilset" }, method = { RequestMethod.GET }, produces = {
            "application/json;charset=utf-8" })
    @ResponseBody
    public String getStencilset() throws IOException {
        InputStream stencilsetStream = getClass().getClassLoader().getResourceAsStream("stencilset.json");
        String stencilsetString = IOUtils.toString(stencilsetStream);
        return JSONUtils.toJSONString(stencilsetString);
    }

    @RequestMapping(value = { "/service/model/{modelId}/json" }, method = { RequestMethod.GET }, produces = {
            "application/json" })
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        ObjectNode modelNode = null;

        Model model = this.repositoryService.getModel(modelId);

        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) this.objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = this.objectMapper.createObjectNode();
                    modelNode.put("name", model.getName());
                }
                modelNode.put("modelId", model.getId());

                ObjectNode editorJsonNode = (ObjectNode) this.objectMapper
                        .readTree(new String(this.repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);
            } catch (Exception e) {
                LOGGER.error("Error creating model JSON", e);
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
        return modelNode;
    }
}
