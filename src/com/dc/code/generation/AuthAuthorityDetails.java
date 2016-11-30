package com.dc.code.generation;

import java.util.Date;
public class AuthAuthorityDetails {
	public static final String TABLE_NAME = "auth_authority_details";
	public static final String ID = "id";
	public static final String SERVICE_ID = "service_id";
	public static final String OPERATION_ID = "operation_id";
	public static final String OPERATION_NAME = "operation_name";
	public static final String REMARK = "remark";
	public static final String CHECK_TYPE = "check_type";
	public static final String DEFAULT_LEVEL = "default_level";
	public static final String CUSTOM_LEVEL = "custom_level";
	public static final String LASTTIME = "lasttime";
	public static final String CONFIGURABLE = "configurable";
	private Long id;
	private Long serviceId;
	private Long operationId;
	private String operationName;
	private String remark;
	private Integer checkType;
	private Long defaultLevel;
	private Long customLevel;
	private Date lasttime;
	private Integer configurable;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId=serviceId;
	}
	public Long getOperationId() {
		return operationId;
	}
	public void setOperationId(Long operationId) {
		this.operationId=operationId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName=operationName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark=remark;
	}
	public Integer getCheckType() {
		return checkType;
	}
	public void setCheckType(Integer checkType) {
		this.checkType=checkType;
	}
	public Long getDefaultLevel() {
		return defaultLevel;
	}
	public void setDefaultLevel(Long defaultLevel) {
		this.defaultLevel=defaultLevel;
	}
	public Long getCustomLevel() {
		return customLevel;
	}
	public void setCustomLevel(Long customLevel) {
		this.customLevel=customLevel;
	}
	public Date getLasttime() {
		return lasttime;
	}
	public void setLasttime(Date lasttime) {
		this.lasttime=lasttime;
	}
	public Integer getConfigurable() {
		return configurable;
	}
	public void setConfigurable(Integer configurable) {
		this.configurable=configurable;
	}
}