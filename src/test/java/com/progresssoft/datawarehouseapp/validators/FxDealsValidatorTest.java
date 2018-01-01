package com.progresssoft.datawarehouseapp.validators;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/datawarehouseapp-dispatcher-servlet.xml"})
@WebAppConfiguration
public class FxDealsValidatorTest {

	@Autowired
	private FxDealsValidator fxDealsValidator;
	
	@Test
	public void isValid_WhenAllBlank(){
		String[] whenAllNullArry = ",,,,".split(",");
		assertFalse(fxDealsValidator.isValid(whenAllNullArry));
	}
	
	@Test
	public void isValid_whenOnlyDealIdIsNotBlank(){
		String[] array1 = "1,,,,".split(",");
		assertFalse(fxDealsValidator.isValid(array1));
	}

	@Test
	public void isValid_whenToCurrIsNotBlank(){
		String[] array2 = ",SGD,,,".split(",");
		assertFalse(fxDealsValidator.isValid(array2));
	}

	@Test
	public void isValid_whenFromCurrIsNotBlank(){
		String[] array3 = ",,USD,,".split(",");
		assertFalse(fxDealsValidator.isValid(array3));
	}

	@Test
	public void isValid_whenDealTimeStampIsNotBlank(){
		String[] array4 = ",,,12/25/2017 16:22:01,".split(",");
		assertFalse(fxDealsValidator.isValid(array4));
	}

	@Test
	public void isValid_whenDealAmtIsNotBlank(){
		String[] array5 = ",,,,12100".split(",");
		assertFalse(fxDealsValidator.isValid(array5));
	}

	@Test
	public void isValid_WhenBothCurrInvalid(){
		String[] array4 = "1,ZIO,PPP,12/25/2017 16:22:01,12100".split(",");
		assertFalse(fxDealsValidator.isValid(array4));
	}

	@Test
	public void isValid_WhenToCurrInValid(){
		String[] array1 = "1,XYZ,USD,12/25/2017 16:22:01,12100".split(",");
		assertFalse(fxDealsValidator.isValid(array1));
	}

	@Test
	public void isValid_WhenFromCurrInValid(){
		String[] array1 = "1,USD,XXX,12/25/2017 16:22:01,12100".split(",");
		assertFalse(fxDealsValidator.isValid(array1));
	}

	@Test
	public void isValid_WhenBothCurrencyValid(){
		String[] array1 = "1,USD,AED,12/25/2017 16:22:01,12100".split(",");
		assertTrue(fxDealsValidator.isValid(array1));
	}
	
	@Test
	public void isValid_WhenOnlyDateIsInvalid(){
		String[] array5 = "1,INR,AUD,99/00/1011 99:99:99,12100".split(",");
		assertFalse(fxDealsValidator.isValid(array5));
	}
	
	@Test
	public void isValid_WhenDateIsValid(){
		String[] array1 = "1,USD,AED,12/25/2017 16:22:01,12100".split(",");
		assertTrue(fxDealsValidator.isValid(array1));
	}

	@Test
	public void isValid_WhenMultipleInvalidPassed(){
		String[] array3 = "3,1232,MYR,16:22:01,12100".split(",");
		assertFalse(fxDealsValidator.isValid(array3));
	}

	@Test
	public void isValid_WhenAllValidPassed(){
		String[] array1 = "1,USD,AED,12/25/2017 16:22:01,1000".split(",");
		assertTrue(fxDealsValidator.isValid(array1));
	}
}
