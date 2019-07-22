package com.els.base.workflow.deployment.dao;

import com.els.base.workflow.deployment.entity.WfDeployment;
import com.els.base.workflow.deployment.entity.WfDeploymentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WfDeploymentMapper {
  int countByExample(WfDeploymentExample paramWfDeploymentExample);
  
  int deleteByExample(WfDeploymentExample paramWfDeploymentExample);
  
  int deleteByPrimaryKey(String paramString);
  
  int insert(WfDeployment paramWfDeployment);
  
  int insertSelective(WfDeployment paramWfDeployment);
  
  List<WfDeployment> selectByExample(WfDeploymentExample paramWfDeploymentExample);
  
  WfDeployment selectByPrimaryKey(String paramString);
  
  int updateByExampleSelective(@Param("record") WfDeployment paramWfDeployment, @Param("example") WfDeploymentExample paramWfDeploymentExample);
  
  int updateByExample(@Param("record") WfDeployment paramWfDeployment, @Param("example") WfDeploymentExample paramWfDeploymentExample);
  
  int updateByPrimaryKeySelective(WfDeployment paramWfDeployment);
  
  int updateByPrimaryKey(WfDeployment paramWfDeployment);
  
  List<WfDeployment> selectByExampleByPage(WfDeploymentExample paramWfDeploymentExample);
}


