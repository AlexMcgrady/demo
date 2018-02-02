package com.crd.demo.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.crd.demo.common.domain.BaseCommonEntity;
import com.crd.demo.common.enumbean.EnableSysEnum;
import com.crd.demo.common.enumbean.GenderSysEnum;
import com.crd.demo.common.enumbean.StatusSysEnum;
@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class User extends BaseCommonEntity{
	/**
	 * long
	 */
	private static final long serialVersionUID = -1707579500726049781L;
	@Column(length = 60)
	private String name;				// 名称
	@Column(length = 60)
	private String nickName;			// 昵称
	@Column(length = 60)
	private String loginName;			// 登录名
	private Integer organizationId;		// 所属机构ID
	@Column(length = 60)
	private String password;			// 密码
	@Column(length = 100)
	private String salt;				// 盐值
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;				// 出生日期
	@Enumerated(EnumType.STRING)
	private GenderSysEnum gender;		// 性别
	@Column(length = 50)
	private String email;				// 邮箱
	@Column(length = 20)
	private String phone;				// 手机号码
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;			// 创建时间
	@Column(length = 500)
	private String description;			// 描述
	@Enumerated(EnumType.STRING)
	private EnableSysEnum enable;		// 启用状态
	@Enumerated(EnumType.STRING)
	private StatusSysEnum status;		// 状态，用于逻辑删除
}
