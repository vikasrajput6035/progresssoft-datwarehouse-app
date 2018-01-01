package com.progresssoft.datawarehouseapp.validators;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static com.progresssoft.datawarehouseapp.validators.DateTimeUtil.FORMATTER;
import com.progresssoft.datawarehouseapp.validators.CurrencyCodeValidator;

@Component("fxDealsValidator")
public class FxDealsValidator {
	
    public boolean isValid(String[] FxDealsArray) {

    	try{
    	
	        if (StringUtils.isEmpty(FxDealsArray[0])) {
	            return false;
	        }
	
	        if (StringUtils.isEmpty(FxDealsArray[1])) {
	            return false;
	        } else {
	           if (!CurrencyCodeValidator.ISO_CURRENCY_CODES.contains(FxDealsArray[1])) {
	               return false;
	           }
	        }
	
	        if (StringUtils.isEmpty(FxDealsArray[2])) {
	            return false;
	        } else {
	            if (!CurrencyCodeValidator.ISO_CURRENCY_CODES.contains(FxDealsArray[2])) {
	                return false;
	            }
	        }
	
	        if (StringUtils.isEmpty(FxDealsArray[3])) {
	            return false;
	        } else {
	            try {
	                LocalDateTime.parse(FxDealsArray[3], FORMATTER);
	            } catch (DateTimeParseException e) {
	                return false;
	            }
	        }
	
	        if (StringUtils.isEmpty(FxDealsArray[4])) {
	            return false;
	        } else {
	            try {
	                new BigDecimal(FxDealsArray[4]);
	            } catch (NumberFormatException e) {
	                return false;
	            }
	        }

    	}catch(ArrayIndexOutOfBoundsException ae){ // Throws arrayindexoutofbound when any value is blank in csv file
    		return false;
    	}
    	
        return true;
    }
}
