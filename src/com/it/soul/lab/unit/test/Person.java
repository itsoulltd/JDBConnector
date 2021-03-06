package com.it.soul.lab.unit.test;

import java.sql.Date;
import java.sql.Timestamp;

import com.it.soul.lab.sql.SQLExecutor;
import com.it.soul.lab.sql.entity.Entity;
import com.it.soul.lab.sql.entity.PrimaryKey;
import com.it.soul.lab.sql.entity.Column;
import com.it.soul.lab.sql.entity.TableName;
import com.it.soul.lab.sql.query.models.DataType;

/*
 * CREATE TABLE Person
(
    uuid varchar(512) PRIMARY KEY NOT NULL,
    name varchar(512),
    age int,
    isActive boolean,
    salary double,
    dob DATETIME,
    height float
);
 */

@TableName(value = "Person", acceptAll = false)
public class Person extends Entity {
	@Column
	@PrimaryKey(name = "uuid", autoIncrement = false)
	private String uuid;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Column(name="name", defaultValue="towhid-islam")
	private String name_test;
	
	@Column(defaultValue="34", type = DataType.INT)
	private Integer age;
	
	@Column(defaultValue="true", type = DataType.BOOL)
	private Boolean isActive;
	
	@Column(defaultValue="0.00", type = DataType.DOUBLE)
	private Double salary;
	
	private Date dob;
	
	@Column(defaultValue="2010-06-21 21:01:01", type=DataType.SQLTIMESTAMP, parseFormat="yyyy-MM-dd HH:mm:ss")
	private Timestamp createDate;
	
	private Float height;
	
	@Column(defaultValue="2010-06-21" , type=DataType.SQLDATE, parseFormat="yyyy-MM-dd")
	private Date dobDate;
	
	@Column(defaultValue="21:01:01" , type=DataType.SQLTIMESTAMP, parseFormat="HH:mm:ss")
	private Timestamp createTime;
	
	public Person() {
		super();
	}
	public String getName_test() {
		return name_test;
	}
	public void setName_test(String name_test) {
		this.name_test = name_test;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Float getHeight() {
		return height;
	}
	public void setHeight(Float height) {
		this.height = height;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Date getDobDate() {
		return dobDate;
	}
	public void setDobDate(Date dobDate) {
		this.dobDate = dobDate;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public com.it.soul.lab.sql.query.models.Property getPropertyTest(String key, SQLExecutor exe, boolean skipPrimary) {
		return getProperty(key, exe, skipPrimary);
	}
}
