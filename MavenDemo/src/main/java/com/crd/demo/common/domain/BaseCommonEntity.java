package com.crd.demo.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;



@Data
@MappedSuperclass
public class BaseCommonEntity implements Serializable{
private static final long serialVersionUID = 3313365265810013673L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;
	@Version
	@JsonIgnore
	private Integer version;
}
