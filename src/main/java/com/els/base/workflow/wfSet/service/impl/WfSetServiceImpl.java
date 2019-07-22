package com.els.base.workflow.wfSet.service.impl;

import com.els.base.core.entity.PageView;
import com.els.base.workflow.wfSet.dao.WfSetMapper;
import com.els.base.workflow.wfSet.entity.WfSet;
import com.els.base.workflow.wfSet.entity.WfSetExample;
import com.els.base.workflow.wfSet.service.WfSetService;
import com.els.base.workflow.wfSet.service.impl.WfSetServiceImpl;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("defaultWfSetService")
public class WfSetServiceImpl implements WfSetService {
    @Resource
    protected WfSetMapper wfSetMapper;

    @CacheEvict(value = { "wfSet" }, allEntries = true)
    public void addObj(WfSet t) {
        this.wfSetMapper.insertSelective(t);
    }

    @CacheEvict(value = { "wfSet" }, allEntries = true)
    public void deleteObjById(String id) {
        this.wfSetMapper.deleteByPrimaryKey(id);
    }

    @CacheEvict(value = { "wfSet" }, allEntries = true)
    public void modifyObj(WfSet t) {
        if (StringUtils.isBlank(t.getId())) {
            throw new NullPointerException("id 为空，无法更新");
        }
        this.wfSetMapper.updateByPrimaryKeySelective(t);
    }

    @Cacheable(value = { "wfSet" }, keyGenerator = "redisKeyGenerator")
    public WfSet queryObjById(String id) {
        return this.wfSetMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = { "wfSet" }, keyGenerator = "redisKeyGenerator")
    public List<WfSet> queryAllObjByExample(WfSetExample example) {
        return this.wfSetMapper.selectByExample(example);
    }

    @Cacheable(value = { "wfSet" }, keyGenerator = "redisKeyGenerator")
    public PageView<WfSet> queryObjByPage(WfSetExample example) {
        PageView<WfSet> pageView = example.getPageView();
        pageView.setQueryResult(this.wfSetMapper.selectByExampleByPage(example));
        return pageView;
    }

    @CacheEvict(value = { "wfSet" }, allEntries = true)
    public void deleteByExample(WfSetExample wfSetExample) {
        this.wfSetMapper.deleteByExample(wfSetExample);
    }
}
