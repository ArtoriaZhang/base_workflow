package com.els.base.workflow.deployment.service;

import com.els.base.core.service.BaseService;
import com.els.base.workflow.deployment.entity.WfDeployment;
import com.els.base.workflow.deployment.entity.WfDeploymentExample;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface WfDeploymentService extends BaseService<WfDeployment, WfDeploymentExample, String> {
    void addObj(WfDeployment paramWfDeployment, MultipartFile paramMultipartFile) throws IOException;

    void setIsEnable(WfDeployment paramWfDeployment);

    void modifyObj(WfDeployment paramWfDeployment, Map<String, MultipartFile> paramMap) throws IOException;

    void changeProcess(WfDeployment paramWfDeployment);

    void deleteObjByIds(List<String> paramList);
}
