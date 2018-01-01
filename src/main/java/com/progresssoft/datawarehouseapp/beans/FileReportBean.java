package com.progresssoft.datawarehouseapp.beans;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="filereporttable")
@DynamicUpdate
public class FileReportBean {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String fileReportId;
	private String fileName;
	private Timestamp processStartTime;
	private Timestamp processEndTime;
	private Integer noOfValidRecords;
	private Integer noOfInValidRecords;
	private Timestamp createddate;
	
	public String getFileReportId() {
		return fileReportId;
	}
	public void setFileReportId(String fileReportId) {
		this.fileReportId = fileReportId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Timestamp getProcessStartTime() {
		return processStartTime;
	}
	public void setProcessStartTime(Timestamp processStartTime) {
		this.processStartTime = processStartTime;
	}
	public Timestamp getProcessEndTime() {
		return processEndTime;
	}
	public void setProcessEndTime(Timestamp processEndTime) {
		this.processEndTime = processEndTime;
	}
	public Integer getNoOfValidRecords() {
		return noOfValidRecords;
	}
	public void setNoOfValidRecords(Integer noOfValidRecords) {
		this.noOfValidRecords = noOfValidRecords;
	}
	public Integer getNoOfInValidRecords() {
		return noOfInValidRecords;
	}
	public void setNoOfInValidRecords(Integer noOfInValidRecords) {
		this.noOfInValidRecords = noOfInValidRecords;
	}
	public Timestamp getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}
		
}
