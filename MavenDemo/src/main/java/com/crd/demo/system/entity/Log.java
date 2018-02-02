package com.crd.demo.system.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.crd.demo.common.domain.BaseCommonEntity;
@Getter
@Setter
@Entity
@Table(name = "log")
@DynamicUpdate
@DynamicInsert
public class Log extends BaseCommonEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String operationCode;
	private String creater;
	private Timestamp createDate;
	private Integer type;
	private String os;
	private String browser;
	private String ip;
	private String mac;
	private Integer executeTime;
	private String description;
	private String requestParam;
}
