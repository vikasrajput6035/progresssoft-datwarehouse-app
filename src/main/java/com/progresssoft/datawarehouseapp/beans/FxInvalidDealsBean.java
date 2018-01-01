package com.progresssoft.datawarehouseapp.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="fxInvalidDealsTable")
public class FxInvalidDealsBean {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long fxInvalidDealID;
	
	private String dealUniqueID;
	private String fromCurrencyISOCode;
	private String toCurrencyISOCode;
	private String dealTimeStamp;
	private String dealAmount;
	public long getFxInvalidDealID() {
		return fxInvalidDealID;
	}
	public void setFxInvalidDealID(long fxInvalidDealID) {
		this.fxInvalidDealID = fxInvalidDealID;
	}
	public String getDealUniqueID() {
		return dealUniqueID;
	}
	public void setDealUniqueID(String dealUniqueID) {
		this.dealUniqueID = dealUniqueID;
	}
	public String getFromCurrencyISOCode() {
		return fromCurrencyISOCode;
	}
	public void setFromCurrencyISOCode(String fromCurrencyISOCode) {
		this.fromCurrencyISOCode = fromCurrencyISOCode;
	}
	public String getToCurrencyISOCode() {
		return toCurrencyISOCode;
	}
	public void setToCurrencyISOCode(String toCurrencyISOCode) {
		this.toCurrencyISOCode = toCurrencyISOCode;
	}
	public String getDealTimeStamp() {
		return dealTimeStamp;
	}
	public void setDealTimeStamp(String dealTimeStamp) {
		this.dealTimeStamp = dealTimeStamp;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
}
