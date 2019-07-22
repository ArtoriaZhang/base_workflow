package com.els.base.workflow.wfSet.entity;

import com.els.base.core.entity.AbstractExample;
import com.els.base.core.entity.PageView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WfSetExample extends AbstractExample<WfSet> implements Serializable {
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    protected PageView<WfSet> pageView = new PageView<>(1, 10);
    private static final long serialVersionUID = 1L;

    public WfSetExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public List<Criteria> getOredCriteria() {
        return this.oredCriteria;
    }

    public void or(Criteria criteria) {
        this.oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (this.oredCriteria.size() == 0)
            this.oredCriteria.add(criteria);

        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }

    public PageView<WfSet> getPageView() {
        return this.pageView;
    }

    public void setPageView(PageView<WfSet> pageView) {
        this.pageView = pageView;
    }

    public static class Criterion implements Serializable {
        private String condition;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;
        private String typeHandler;
        private static final long serialVersionUID = 1L;

        public String getCondition() {
            return this.condition;
        }

        public Object getValue() {
            return this.value;
        }

        public Object getSecondValue() {
            return this.secondValue;
        }

        public boolean isNoValue() {
            return this.noValue;
        }

        public boolean isSingleValue() {
            return this.singleValue;
        }

        public boolean isBetweenValue() {
            return this.betweenValue;
        }

        public boolean isListValue() {
            return this.listValue;
        }

        public String getTypeHandler() {
            return this.typeHandler;
        }

        protected Criterion(String condition) {
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List)
                this.listValue = true;
            else
                this.singleValue = true;
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }

    public static class Criteria extends WfSetExample.GeneratedCriteria implements Serializable {
        private static final long serialVersionUID = 1L;
    }

    protected static abstract class GeneratedCriteria implements Serializable {
        protected List<WfSetExample.Criterion> criteria;
        private static final long serialVersionUID = 1L;

        protected GeneratedCriteria() {
            this.criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return (this.criteria.size() > 0);
        }

        public List<WfSetExample.Criterion> getAllCriteria() {
            return this.criteria;
        }

        public List<WfSetExample.Criterion> getCriteria() {
            return this.criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null)
                throw new RuntimeException("Value for condition cannot be null");

            this.criteria.add(new WfSetExample.Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null)
                throw new RuntimeException("Value for " + property + " cannot be null");

            this.criteria.add(new WfSetExample.Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if ((value1 == null) || (value2 == null))
                throw new RuntimeException("Between values for " + property + " cannot be null");

            this.criteria.add(new WfSetExample.Criterion(condition, value1, value2));
        }

        public WfSetExample.Criteria andIdIsNull() {
            addCriterion("ID is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessIsNull() {
            addCriterion("CURRENT_PROCESS is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessIsNotNull() {
            addCriterion("CURRENT_PROCESS is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessEqualTo(String value) {
            addCriterion("CURRENT_PROCESS =", value, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessNotEqualTo(String value) {
            addCriterion("CURRENT_PROCESS <>", value, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessGreaterThan(String value) {
            addCriterion("CURRENT_PROCESS >", value, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessGreaterThanOrEqualTo(String value) {
            addCriterion("CURRENT_PROCESS >=", value, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessLessThan(String value) {
            addCriterion("CURRENT_PROCESS <", value, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessLessThanOrEqualTo(String value) {
            addCriterion("CURRENT_PROCESS <=", value, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessLike(String value) {
            addCriterion("CURRENT_PROCESS like", value, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessNotLike(String value) {
            addCriterion("CURRENT_PROCESS not like", value, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessIn(List<String> values) {
            addCriterion("CURRENT_PROCESS in", values, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessNotIn(List<String> values) {
            addCriterion("CURRENT_PROCESS not in", values, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessBetween(String value1, String value2) {
            addCriterion("CURRENT_PROCESS between", value1, value2, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andCurrentProcessNotBetween(String value1, String value2) {
            addCriterion("CURRENT_PROCESS not between", value1, value2, "currentProcess");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpIsNull() {
            addCriterion("CONDITION_SET_UP is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpIsNotNull() {
            addCriterion("CONDITION_SET_UP is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpEqualTo(Integer value) {
            addCriterion("CONDITION_SET_UP =", value, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpNotEqualTo(Integer value) {
            addCriterion("CONDITION_SET_UP <>", value, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpGreaterThan(Integer value) {
            addCriterion("CONDITION_SET_UP >", value, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpGreaterThanOrEqualTo(Integer value) {
            addCriterion("CONDITION_SET_UP >=", value, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpLessThan(Integer value) {
            addCriterion("CONDITION_SET_UP <", value, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpLessThanOrEqualTo(Integer value) {
            addCriterion("CONDITION_SET_UP <=", value, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpIn(List<Integer> values) {
            addCriterion("CONDITION_SET_UP in", values, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpNotIn(List<Integer> values) {
            addCriterion("CONDITION_SET_UP not in", values, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpBetween(Integer value1, Integer value2) {
            addCriterion("CONDITION_SET_UP between", value1, value2, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionSetUpNotBetween(Integer value1, Integer value2) {
            addCriterion("CONDITION_SET_UP not between", value1, value2, "conditionSetUp");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionIsNull() {
            addCriterion("CONDITION_ is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionIsNotNull() {
            addCriterion("CONDITION_ is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionEqualTo(String value) {
            addCriterion("CONDITION_ =", value, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionNotEqualTo(String value) {
            addCriterion("CONDITION_ <>", value, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionGreaterThan(String value) {
            addCriterion("CONDITION_ >", value, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionGreaterThanOrEqualTo(String value) {
            addCriterion("CONDITION_ >=", value, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionLessThan(String value) {
            addCriterion("CONDITION_ <", value, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionLessThanOrEqualTo(String value) {
            addCriterion("CONDITION_ <=", value, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionLike(String value) {
            addCriterion("CONDITION_ like", value, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionNotLike(String value) {
            addCriterion("CONDITION_ not like", value, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionIn(List<String> values) {
            addCriterion("CONDITION_ in", values, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionNotIn(List<String> values) {
            addCriterion("CONDITION_ not in", values, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionBetween(String value1, String value2) {
            addCriterion("CONDITION_ between", value1, value2, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andConditionNotBetween(String value1, String value2) {
            addCriterion("CONDITION_ not between", value1, value2, "condition");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupIsNull() {
            addCriterion("APPROVE_GROUP is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupIsNotNull() {
            addCriterion("APPROVE_GROUP is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupEqualTo(String value) {
            addCriterion("APPROVE_GROUP =", value, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupNotEqualTo(String value) {
            addCriterion("APPROVE_GROUP <>", value, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupGreaterThan(String value) {
            addCriterion("APPROVE_GROUP >", value, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVE_GROUP >=", value, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupLessThan(String value) {
            addCriterion("APPROVE_GROUP <", value, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupLessThanOrEqualTo(String value) {
            addCriterion("APPROVE_GROUP <=", value, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupLike(String value) {
            addCriterion("APPROVE_GROUP like", value, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupNotLike(String value) {
            addCriterion("APPROVE_GROUP not like", value, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupIn(List<String> values) {
            addCriterion("APPROVE_GROUP in", values, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupNotIn(List<String> values) {
            addCriterion("APPROVE_GROUP not in", values, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupBetween(String value1, String value2) {
            addCriterion("APPROVE_GROUP between", value1, value2, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveGroupNotBetween(String value1, String value2) {
            addCriterion("APPROVE_GROUP not between", value1, value2, "approveGroup");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeIsNull() {
            addCriterion("APPROVE_ASSIGNEE is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeIsNotNull() {
            addCriterion("APPROVE_ASSIGNEE is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeEqualTo(String value) {
            addCriterion("APPROVE_ASSIGNEE =", value, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeNotEqualTo(String value) {
            addCriterion("APPROVE_ASSIGNEE <>", value, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeGreaterThan(String value) {
            addCriterion("APPROVE_ASSIGNEE >", value, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeGreaterThanOrEqualTo(String value) {
            addCriterion("APPROVE_ASSIGNEE >=", value, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeLessThan(String value) {
            addCriterion("APPROVE_ASSIGNEE <", value, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeLessThanOrEqualTo(String value) {
            addCriterion("APPROVE_ASSIGNEE <=", value, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeLike(String value) {
            addCriterion("APPROVE_ASSIGNEE like", value, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeNotLike(String value) {
            addCriterion("APPROVE_ASSIGNEE not like", value, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeIn(List<String> values) {
            addCriterion("APPROVE_ASSIGNEE in", values, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeNotIn(List<String> values) {
            addCriterion("APPROVE_ASSIGNEE not in", values, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeBetween(String value1, String value2) {
            addCriterion("APPROVE_ASSIGNEE between", value1, value2, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andApproveAssigneeNotBetween(String value1, String value2) {
            addCriterion("APPROVE_ASSIGNEE not between", value1, value2, "approveAssignee");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdIsNull() {
            addCriterion("GROUP_ID is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdIsNotNull() {
            addCriterion("GROUP_ID is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdEqualTo(String value) {
            addCriterion("GROUP_ID =", value, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdNotEqualTo(String value) {
            addCriterion("GROUP_ID <>", value, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdGreaterThan(String value) {
            addCriterion("GROUP_ID >", value, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_ID >=", value, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdLessThan(String value) {
            addCriterion("GROUP_ID <", value, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdLessThanOrEqualTo(String value) {
            addCriterion("GROUP_ID <=", value, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdLike(String value) {
            addCriterion("GROUP_ID like", value, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdNotLike(String value) {
            addCriterion("GROUP_ID not like", value, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdIn(List<String> values) {
            addCriterion("GROUP_ID in", values, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdNotIn(List<String> values) {
            addCriterion("GROUP_ID not in", values, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdBetween(String value1, String value2) {
            addCriterion("GROUP_ID between", value1, value2, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andGroupIdNotBetween(String value1, String value2) {
            addCriterion("GROUP_ID not between", value1, value2, "groupId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdIsNull() {
            addCriterion("ASSIGNEE_ID is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdIsNotNull() {
            addCriterion("ASSIGNEE_ID is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdEqualTo(String value) {
            addCriterion("ASSIGNEE_ID =", value, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdNotEqualTo(String value) {
            addCriterion("ASSIGNEE_ID <>", value, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdGreaterThan(String value) {
            addCriterion("ASSIGNEE_ID >", value, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdGreaterThanOrEqualTo(String value) {
            addCriterion("ASSIGNEE_ID >=", value, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdLessThan(String value) {
            addCriterion("ASSIGNEE_ID <", value, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdLessThanOrEqualTo(String value) {
            addCriterion("ASSIGNEE_ID <=", value, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdLike(String value) {
            addCriterion("ASSIGNEE_ID like", value, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdNotLike(String value) {
            addCriterion("ASSIGNEE_ID not like", value, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdIn(List<String> values) {
            addCriterion("ASSIGNEE_ID in", values, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdNotIn(List<String> values) {
            addCriterion("ASSIGNEE_ID not in", values, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdBetween(String value1, String value2) {
            addCriterion("ASSIGNEE_ID between", value1, value2, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andAssigneeIdNotBetween(String value1, String value2) {
            addCriterion("ASSIGNEE_ID not between", value1, value2, "assigneeId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdIsNull() {
            addCriterion("DEPLOYMENT_ID is null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdIsNotNull() {
            addCriterion("DEPLOYMENT_ID is not null");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdEqualTo(String value) {
            addCriterion("DEPLOYMENT_ID =", value, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdNotEqualTo(String value) {
            addCriterion("DEPLOYMENT_ID <>", value, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdGreaterThan(String value) {
            addCriterion("DEPLOYMENT_ID >", value, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdGreaterThanOrEqualTo(String value) {
            addCriterion("DEPLOYMENT_ID >=", value, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdLessThan(String value) {
            addCriterion("DEPLOYMENT_ID <", value, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdLessThanOrEqualTo(String value) {
            addCriterion("DEPLOYMENT_ID <=", value, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdLike(String value) {
            addCriterion("DEPLOYMENT_ID like", value, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdNotLike(String value) {
            addCriterion("DEPLOYMENT_ID not like", value, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdIn(List<String> values) {
            addCriterion("DEPLOYMENT_ID in", values, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdNotIn(List<String> values) {
            addCriterion("DEPLOYMENT_ID not in", values, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdBetween(String value1, String value2) {
            addCriterion("DEPLOYMENT_ID between", value1, value2, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }

        public WfSetExample.Criteria andDeploymentIdNotBetween(String value1, String value2) {
            addCriterion("DEPLOYMENT_ID not between", value1, value2, "deploymentId");
            return ((WfSetExample.Criteria) this);
        }
    }
}