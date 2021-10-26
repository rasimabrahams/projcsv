package com.csv.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csv.util.CsvConstant;

@Service
public class CsvService {

	public Map<String, List<String>> readAndValidate(MultipartFile file) {
		Map<String, List<String>> errors = new HashMap<>();

		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
			CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			int row = 1;
			for (CSVRecord csvRecord : csvRecords) {
				if(row==1) {
					row++;
					continue;
				}
				List<String> list = new ArrayList<>();
				String errorKey = "row number " + row;
				String serialNo = csvRecord.get(0);
				if (!validate(CsvConstant.TITLE_SERIAL_NO, serialNo)) {
					list.add(CsvConstant.TITLE_SERIAL_NO);
				}
				String partNo = csvRecord.get(1);
				if (!validate(CsvConstant.TITLE_PART_NO, partNo)) {
					list.add(CsvConstant.TITLE_PART_NO);
				}
				String userName = csvRecord.get(2);
				if (!validate(CsvConstant.TITLE_USER_NAME, userName)) {
					list.add(CsvConstant.TITLE_USER_NAME);
				}
				String userId = csvRecord.get(3);
				if (!validate(CsvConstant.TITLE_USER_ID, userId)) {
					list.add(CsvConstant.TITLE_USER_ID);
				}
				String location = csvRecord.get(4);
				if (!validate(CsvConstant.TITLE_LOCATION, location)) {
					list.add(CsvConstant.TITLE_LOCATION);
				}
				String inventory = csvRecord.get(5);
				if (!validate(CsvConstant.TITLE_INVENTORY, inventory)) {
					list.add(CsvConstant.TITLE_INVENTORY);
				}
				String status = csvRecord.get(6);
				if (!validate(CsvConstant.TITLE_STATUS, status)) {
					list.add(CsvConstant.TITLE_STATUS);
				}

				if (list.size() > 0) {
					errors.put(errorKey, list);
				}

				row++;
			}
		} catch (IOException e) {
			// TODO: handle exception
		}

		return errors;
	}

	public boolean validate(String column, String value) {
		Map<String, String> map = new HashMap<>();
		String regex = null;
		switch (column) {
		case CsvConstant.TITLE_SERIAL_NO:
			regex = "[0-9]+";
			break;
		case CsvConstant.TITLE_PART_NO:
			regex = "[0-9]+";
			break;
		case CsvConstant.TITLE_USER_NAME:
			regex = "[a-zA-Z]+";
			break;
		case CsvConstant.TITLE_USER_ID:
			regex = "^[A-Za-z0-9_-]*$";
			break;
		case CsvConstant.TITLE_INVENTORY:
			regex = "[0-9]+";
			break;
		case CsvConstant.TITLE_LOCATION:
			regex = "^[A-Za-z0-9_-]*$";
			break;

		}
		boolean flage = false;
		if (regex != null) {
			flage = ((!value.equals("")) && (value != null) && (value.matches(regex)));
		} else if (CsvConstant.TITLE_STATUS.equalsIgnoreCase(column)) {
			flage = (value.equals(CsvConstant.STATUS_VALUE_AVAILABLE)
					|| value.equals(CsvConstant.STATUS_VALUE_NOT_AVAILABLE));
		}

		return flage;
	}

}
