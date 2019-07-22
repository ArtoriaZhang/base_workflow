package com.els.base.workflow.deployment.entity;

import com.els.base.core.entity.AbstractExample;
import com.els.base.core.entity.PageView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WfDeploymentExample extends AbstractExample<WfDeployment> implements Serializable {
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    protected PageView<WfDeployment> pageView = new PageView<>(1, 10);
    private static final long serialVersionUID = 1L;

    public WfDeploymentExample() {
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

    public PageView<WfDeployment> getPageView() {
        return this.pageView;
    }

    public void setPageView(PageView<WfDeployment> pageView) {
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

    public static class Criteria extends WfDeploymentExample.GeneratedCriteria implements Serializable {
        private static final long serialVersionUID = 1L;
    }

    protected static abstract class GeneratedCriteria implements Serializable {
        protected List<WfDeploymentExample.Criterion> criteria;
        private static final long serialVersionUID = 1L;

        protected GeneratedCriteria() {
            this.criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return (this.criteria.size() > 0);
        }

        public List<WfDeploymentExample.Criterion> getAllCriteria() {
            return this.criteria;
        }

        public List<WfDeploymentExample.Criterion> getCriteria() {
            return this.criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null)
                throw new RuntimeException("Value for condition cannot be null");

            this.criteria.add(new WfDeploymentExample.Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null)
                throw new RuntimeException("Value for " + property + " cannot be null");

            this.criteria.add(new WfDeploymentExample.Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if ((value1 == null) || (value2 == null))
                throw new RuntimeException("Between values for " + property + " cannot be null");

            this.criteria.add(new WfDeploymentExample.Criterion(condition, value1, value2));
        }

        public WfDeploymentExample.Criteria andIdIsNull() {
            addCriterion("ID is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeIsNull() {
            addCriterion("BUSINESS_CODE is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeIsNotNull() {
            addCriterion("BUSINESS_CODE is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeEqualTo(String value) {
            addCriterion("BUSINESS_CODE =", value, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeNotEqualTo(String value) {
            addCriterion("BUSINESS_CODE <>", value, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeGreaterThan(String value) {
            addCriterion("BUSINESS_CODE >", value, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUSINESS_CODE >=", value, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeLessThan(String value) {
            addCriterion("BUSINESS_CODE <", value, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeLessThanOrEqualTo(String value) {
            addCriterion("BUSINESS_CODE <=", value, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeLike(String value) {
            addCriterion("BUSINESS_CODE like", value, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeNotLike(String value) {
            addCriterion("BUSINESS_CODE not like", value, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeIn(List<String> values) {
            addCriterion("BUSINESS_CODE in", values, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeNotIn(List<String> values) {
            addCriterion("BUSINESS_CODE not in", values, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeBetween(String value1, String value2) {
            addCriterion("BUSINESS_CODE between", value1, value2, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessCodeNotBetween(String value1, String value2) {
            addCriterion("BUSINESS_CODE not between", value1, value2, "businessCode");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameIsNull() {
            addCriterion("BUSINESS_NAME is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameIsNotNull() {
            addCriterion("BUSINESS_NAME is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameEqualTo(String value) {
            addCriterion("BUSINESS_NAME =", value, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameNotEqualTo(String value) {
            addCriterion("BUSINESS_NAME <>", value, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameGreaterThan(String value) {
            addCriterion("BUSINESS_NAME >", value, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameGreaterThanOrEqualTo(String value) {
            addCriterion("BUSINESS_NAME >=", value, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameLessThan(String value) {
            addCriterion("BUSINESS_NAME <", value, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameLessThanOrEqualTo(String value) {
            addCriterion("BUSINESS_NAME <=", value, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameLike(String value) {
            addCriterion("BUSINESS_NAME like", value, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameNotLike(String value) {
            addCriterion("BUSINESS_NAME not like", value, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameIn(List<String> values) {
            addCriterion("BUSINESS_NAME in", values, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameNotIn(List<String> values) {
            addCriterion("BUSINESS_NAME not in", values, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameBetween(String value1, String value2) {
            addCriterion("BUSINESS_NAME between", value1, value2, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andBusinessNameNotBetween(String value1, String value2) {
            addCriterion("BUSINESS_NAME not between", value1, value2, "businessName");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableIsNull() {
            addCriterion("IS_ENABLE is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableIsNotNull() {
            addCriterion("IS_ENABLE is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableEqualTo(Integer value) {
            addCriterion("IS_ENABLE =", value, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableNotEqualTo(Integer value) {
            addCriterion("IS_ENABLE <>", value, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableGreaterThan(Integer value) {
            addCriterion("IS_ENABLE >", value, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_ENABLE >=", value, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableLessThan(Integer value) {
            addCriterion("IS_ENABLE <", value, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableLessThanOrEqualTo(Integer value) {
            addCriterion("IS_ENABLE <=", value, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableIn(List<Integer> values) {
            addCriterion("IS_ENABLE in", values, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableNotIn(List<Integer> values) {
            addCriterion("IS_ENABLE not in", values, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableBetween(Integer value1, Integer value2) {
            addCriterion("IS_ENABLE between", value1, value2, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andIsEnableNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_ENABLE not between", value1, value2, "isEnable");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserIsNull() {
            addCriterion("CREATE_USER is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserIsNotNull() {
            addCriterion("CREATE_USER is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserEqualTo(String value) {
            addCriterion("CREATE_USER =", value, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("CREATE_USER <>", value, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserGreaterThan(String value) {
            addCriterion("CREATE_USER >", value, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_USER >=", value, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserLessThan(String value) {
            addCriterion("CREATE_USER <", value, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("CREATE_USER <=", value, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserLike(String value) {
            addCriterion("CREATE_USER like", value, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserNotLike(String value) {
            addCriterion("CREATE_USER not like", value, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserIn(List<String> values) {
            addCriterion("CREATE_USER in", values, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("CREATE_USER not in", values, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("CREATE_USER between", value1, value2, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("CREATE_USER not between", value1, value2, "createUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserIsNull() {
            addCriterion("UPDATE_USER is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserIsNotNull() {
            addCriterion("UPDATE_USER is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserEqualTo(String value) {
            addCriterion("UPDATE_USER =", value, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("UPDATE_USER <>", value, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("UPDATE_USER >", value, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER >=", value, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserLessThan(String value) {
            addCriterion("UPDATE_USER <", value, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_USER <=", value, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserLike(String value) {
            addCriterion("UPDATE_USER like", value, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserNotLike(String value) {
            addCriterion("UPDATE_USER not like", value, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserIn(List<String> values) {
            addCriterion("UPDATE_USER in", values, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("UPDATE_USER not in", values, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("UPDATE_USER between", value1, value2, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("UPDATE_USER not between", value1, value2, "updateUser");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }

        public WfDeploymentExample.Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return ((WfDeploymentExample.Criteria) this);
        }
    }
}