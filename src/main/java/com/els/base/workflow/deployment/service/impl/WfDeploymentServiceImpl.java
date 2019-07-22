package com.els.base.workflow.deployment.service.impl;

import com.els.base.core.entity.PageView;
import com.els.base.core.exception.CommonException;
import com.els.base.core.utils.Assert;
import com.els.base.core.utils.Constant;
import com.els.base.workflow.deployment.dao.WfDeploymentMapper;
import com.els.base.workflow.deployment.entity.WfDeployment;
import com.els.base.workflow.deployment.entity.WfDeploymentExample;
import com.els.base.workflow.deployment.model.BpmnModelBuilder;
import com.els.base.workflow.deployment.service.WfDeploymentService;
import com.els.base.workflow.deployment.service.impl.WfDeploymentServiceImpl;
import com.els.base.workflow.wfSet.entity.WfSet;
import com.els.base.workflow.wfSet.entity.WfSetExample;
import com.els.base.workflow.wfSet.service.WfSetService;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("defaultWfDeploymentService")
public class WfDeploymentServiceImpl implements WfDeploymentService {
    @Resource
    protected WfDeploymentMapper wfDeploymentMapper;
    @Resource
    protected WfSetService wfSetService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;

    @CacheEvict(value = { "wfDeployment" }, allEntries = true)
    public void addObj(WfDeployment t) {
        t.setIsEnable(Constant.YES_INT);
        t.setCreateTime(new Date());
        this.wfDeploymentMapper.insertSelective(t);
    }

    @Transactional
    @CacheEvict(value = { "wfDeployment" }, allEntries = true)
    public void deleteObjById(String id) {
        WfDeployment deployment = new WfDeployment();
        deployment.setId(id);
        deployment.setIsEnable(Constant.NO_INT);
        this.repositoryService.deleteDeployment(deployment.getId());
        this.wfDeploymentMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value = { "wfDeployment" }, allEntries = true)
    public void deleteObjByIds(List<String> ids) {
        for (String id : ids) {
            WfDeployment deployment = new WfDeployment();
            deployment.setId(id);
            deployment.setIsEnable(Constant.NO_INT);
            this.repositoryService.deleteDeployment(deployment.getId());
            this.wfDeploymentMapper.deleteByPrimaryKey(id);
        }
    }

    @Transactional
    @CacheEvict(value = { "wfDeployment" }, allEntries = true)
    public void modifyObj(WfDeployment wfDeployment) {
        if (StringUtils.isBlank(wfDeployment.getId())) {
            throw new NullPointerException("id 为空，无法更新");
        }

        BpmnModelBuilder builder = new BpmnModelBuilder();
        BpmnModel model = builder.build(wfDeployment);

        (new BpmnAutoLayout(model)).execute();

        Deployment deployment = this.repositoryService.createDeployment()
                .addBpmnModel(wfDeployment.getBusinessCode() + ".bpmn", model)
                .name(wfDeployment.getBusinessCode() + "_deployment").deploy();

        List<WfSet> wfSetList = wfDeployment.getWfSetList();
        String wid = ((WfSet) wfSetList.get(0)).getDeploymentId();
        WfSetExample wfSetExample = new WfSetExample();
        wfSetExample.createCriteria().andDeploymentIdEqualTo(wid);
        this.wfSetService.deleteByExample(wfSetExample);

        for (WfSet wfSet : wfSetList) {

            wfSet.setDeploymentId(wfDeployment.getId());
            this.wfSetService.addObj(wfSet);
        }

        wfDeployment.setUpdateTime(new Date());
        this.wfDeploymentMapper.updateByPrimaryKeySelective(wfDeployment);
    }

    @CacheEvict(value = { "wfDeployment" }, allEntries = true)
    @Transactional
    public void modifyObj(WfDeployment wfDeployment, Map<String, MultipartFile> fileMap) throws IOException {
        Assert.isNotBlank(wfDeployment.getId(), "id为空,无法更新");

        if (MapUtils.isNotEmpty(fileMap)) {
            Set<String> fileKeySet = fileMap.keySet();
            if (fileKeySet.size() > 1) {
                throw new CommonException("不接受多个文件上传", "file_upload_not_accepted");
            }

            MultipartFile file = null;

            Iterator<String> keyIterator = fileKeySet.iterator();
            while (keyIterator.hasNext()) {
                file = (MultipartFile) fileMap.get(keyIterator.next());
            }

            Deployment deployment = this.repositoryService.createDeployment()
                    .addZipInputStream(new ZipInputStream(file.getInputStream())).deploy();
        }

        wfDeployment.setUpdateTime(new Date());
        this.wfDeploymentMapper.updateByPrimaryKeySelective(wfDeployment);
    }

    @Cacheable(value = { "wfDeployment" }, keyGenerator = "redisKeyGenerator")
    public WfDeployment queryObjById(String id) {
        return this.wfDeploymentMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = { "wfDeployment" }, keyGenerator = "redisKeyGenerator")
    public List<WfDeployment> queryAllObjByExample(WfDeploymentExample example) {
        return this.wfDeploymentMapper.selectByExample(example);
    }

    @Cacheable(value = { "wfDeployment" }, keyGenerator = "redisKeyGenerator")
    public PageView<WfDeployment> queryObjByPage(WfDeploymentExample example) {
        PageView<WfDeployment> pageView = example.getPageView();
        List<WfDeployment> selectByExampleByPage = this.wfDeploymentMapper.selectByExampleByPage(example);
        for (int i = 0; CollectionUtils.isNotEmpty(selectByExampleByPage) && i < selectByExampleByPage.size(); i++) {
            WfSetExample wfSetExample = new WfSetExample();
            wfSetExample.createCriteria().andDeploymentIdEqualTo(((WfDeployment) selectByExampleByPage.get(i)).getId());
            List<WfSet> sets = this.wfSetService.queryAllObjByExample(wfSetExample);
            ((WfDeployment) selectByExampleByPage.get(i)).setWfSetList(sets);
        }
        pageView.setQueryResult(selectByExampleByPage);
        return pageView;
    }

    @CacheEvict(value = { "wfDeployment" }, allEntries = true)
    @Transactional
    public void addObj(WfDeployment wfDeployment, MultipartFile file) throws IOException {
        Deployment deploy = this.repositoryService.createDeployment()
                .addZipInputStream(new ZipInputStream(file.getInputStream())).deploy();

        wfDeployment.setId(deploy.getId());
        wfDeployment.setCreateTime(new Date());
        this.wfDeploymentMapper.insertSelective(wfDeployment);
    }

    @CacheEvict(value = { "wfDeployment" }, allEntries = true)
    @Transactional
    public void setIsEnable(WfDeployment wf) {
        ProcessDefinition processDefinition = (ProcessDefinition) this.repositoryService.createProcessDefinitionQuery()
                .deploymentId(wf.getId()).list().get(0);
        WfDeployment deployment = new WfDeployment();
        deployment.setId(wf.getId());

        if (Constant.NO_INT.equals(wf.getIsEnable())) {

            deployment.setIsEnable(Constant.NO_INT);

            this.wfDeploymentMapper.updateByPrimaryKeySelective(deployment);
            this.repositoryService.suspendProcessDefinitionById(processDefinition.getId());
        } else {

            deployment.setIsEnable(Constant.YES_INT);
            this.wfDeploymentMapper.updateByPrimaryKeySelective(deployment);
            this.repositoryService.activateProcessDefinitionById(processDefinition.getId());
        }
    }

    @CacheEvict(value = { "wfDeployment" }, allEntries = true)
    @Transactional
    public void changeProcess(WfDeployment wfDeployment) {
        WfDeploymentExample deploymentExample = new WfDeploymentExample();
        deploymentExample.createCriteria().andBusinessCodeEqualTo(wfDeployment.getBusinessCode());

        if (this.wfDeploymentMapper.countByExample(deploymentExample) > 0) {
            throw new CommonException("该模块编码已存在,请重新输入");
        }

        BpmnModelBuilder builder = new BpmnModelBuilder();
        BpmnModel model = builder.build(wfDeployment);

        (new BpmnAutoLayout(model)).execute();

        Deployment deployment = this.repositoryService.createDeployment()
                .addBpmnModel(wfDeployment.getBusinessCode() + ".bpmn", model)
                .name(wfDeployment.getBusinessCode() + "_deployment").deploy();

        List<WfSet> wfSetList = wfDeployment.getWfSetList();
        for (WfSet wfSet : wfSetList) {

            wfSet.setDeploymentId(deployment.getId());
            this.wfSetService.addObj(wfSet);
        }

        wfDeployment.setId(deployment.getId());
        wfDeployment.setIsEnable(Constant.YES_INT);
        wfDeployment.setCreateTime(new Date());
        this.wfDeploymentMapper.insertSelective(wfDeployment);
    }
}
