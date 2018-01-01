package com.progresssoft.datawarehouseapp.validators;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.progresssoft.datawarehouseapp.junit.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/datawarehouseapp-dispatcher-servlet.xml"})
@WebAppConfiguration
public class FileValidatorTest {

	@Autowired
	private FileValidator fileValidator;
	
	@Autowired
	private TestUtils testUtils;
	
	@Test
	public void isFileEmpty_WhenEmpty(){

		MultipartFile file = testUtils.getMultiPartFile(testUtils.emptyFile);
		assertTrue(fileValidator.isFileEmpty(file));
	}

	@Test
	public void isFileEmpty_WhenNotEmpty(){

		MultipartFile file = testUtils.getMultiPartFile(testUtils.nonEmptyFile);
		assertFalse(fileValidator.isFileEmpty(file));
	}	
	
	@Test
	public void isFileFormatValid_WhenInvalid(){

		MultipartFile file = testUtils.getMultiPartFile(testUtils.invalidFile);
		assertFalse(fileValidator.isFileFormatValid(file));
	}
	
	@Test
	public void isFileFormatValid_WhenValid(){

		MultipartFile file = testUtils.getMultiPartFile(testUtils.validFile);
		assertTrue(fileValidator.isFileFormatValid(file));
	}
	
	@Test
	public void isFileDuplicate_WhenDuplicate() throws Exception{
		
		MultipartFile file = testUtils.getMultiPartFile(testUtils.duplicateFile);
		assertTrue(fileValidator.isFileDuplicate(file));
	}
	
	@Test
	public void isFileDuplicate_WhenUnique() throws Exception{
		
		MultipartFile file = testUtils.getMultiPartFile(testUtils.uniqueFile);
		assertFalse(fileValidator.isFileDuplicate(file));
	}
	
}