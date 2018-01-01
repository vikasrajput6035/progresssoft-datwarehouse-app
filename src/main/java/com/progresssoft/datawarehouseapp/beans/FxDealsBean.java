package com.progresssoft.datawarehouseapp.beans;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="fxdealstable")
public class FxDealsBean {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long fxDealID;
	
	private long dealUniqueID;
	private String fromCurrencyISOCode;
	private String toCurrencyISOCode;
	private Timestamp dealTimeStamp;
	private Double dealAmount;
	
	
	public long getFxDealID() {
		return fxDealID;
	}
	public void setFxDealID(long fxDealID) {
		this.fxDealID = fxDealID;
	}
	public long getDealUniqueID() {
		return dealUniqueID;
	}
	public void setDealUniqueID(long dealUniqueID) {
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
	public Timestamp getDealTimeStamp() {
		return dealTimeStamp;
	}
	public void setDealTimeStamp(Timestamp dealTimeStamp) {
		this.dealTimeStamp = dealTimeStamp;
	}
	public Double getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(Double dealAmount) {
		this.dealAmount = dealAmount;
	}
	
	public String toString() { 
        return "DealUniqueID :: "+this.getDealUniqueID()+" :: FromCurrencyISOCode :: "+this.getFromCurrencyISOCode()+" :: ToCurrencyISOCode :: "+this.getToCurrencyISOCode()+" :: DealAmount :: "+this.getDealAmount()+" :: DealTimeStamp :: "+this.getDealTimeStamp();
    } 
}
