package com.progresssoft.datawarehouseapp.junit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("testUtils")
public class TestUtils {

	public String sampleFileFolders = "C:\\Users\\hp\\Desktop\\sample\\";
	
	/* Used in FileValidatorTest Junit class */
	public String emptyFile = "empty.csv";
	public String nonEmptyFile = "NonEmpty.csv";
	public String invalidFile = "Screenshot (1).png";
	public String validFile = "NonEmpty.csv";
	public String duplicateFile = "ValidInvalid - Copy.csv";
	public String uniqueFile = "UniqueFile.csv";
	
	/* Used in DataWareHouseServiceTest Junit class */
	public String fileWith100KValidRecords = "ValidCSV.csv";
	public String fileWith100KInValidRecords = "100000_test_data.csv";
	public String fileWith100KMixRecords = "ValidInvalid.csv";
	
	public MultipartFile getMultiPartFile(String fileName){
		Path path = Paths.get(sampleFileFolders+fileName);
		String contentType = "text/csv";
		byte[] content = null;
		try {
		    content = Files.readAllBytes(path);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		MultipartFile result = new MockMultipartFile(fileName,fileName, contentType, content);
		return result;
	}

}
