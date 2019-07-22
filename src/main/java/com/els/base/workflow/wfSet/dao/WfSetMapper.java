package com.els.base.workflow.wfSet.dao;

import com.els.base.workflow.wfSet.entity.WfSet;
import com.els.base.workflow.wfSet.entity.WfSetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WfSetMapper {
    int countByExample(WfSetExample paramWfSetExample);

    int deleteByExample(WfSetExample paramWfSetExample);

    int deleteByPrimaryKey(String paramString);

    int insert(WfSet paramWfSet);

    int insertSelective(WfSet paramWfSet);

    List<WfSet> selectByExample(WfSetExample paramWfSetExample);

    WfSet selectByPrimaryKey(String paramString);

    int updateByExampleSelective(@Param("record") WfSet paramWfSet, @Param("example") WfSetExample paramWfSetExample);

    int updateByExample(@Param("record") WfSet paramWfSet, @Param("example") WfSetExample paramWfSetExample);

    int updateByPrimaryKeySelective(WfSet paramWfSet);

    int updateByPrimaryKey(WfSet paramWfSet);

    List<WfSet> selectByExampleByPage(WfSetExample paramWfSetExample);
}
