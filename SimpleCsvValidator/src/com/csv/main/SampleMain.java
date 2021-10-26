package com.csv.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleMain {
	private static final String VALID_CSV = "C:\\Workspace\\SpringA6\\SimpleCsvValidator\\src\\com\\csv\\Valid.csv";
	private static final String IN_VALID_CSV = "C:\\Workspace\\SpringA6\\SimpleCsvValidator\\src\\com\\csv\\In-Valid.csv";

	public static final String TITLE_SERIAL_NO = "Serial No";
	public static final String TITLE_PART_NO = "Part No";
	public static final String TITLE_USER_NAME = "User Name";
	public static final String TITLE_USER_ID = "User Id";
	public static final String TITLE_LOCATION = "Location";
	public static final String TITLE_INVENTORY = "Inventory";
	public static final String TITLE_STATUS = "Status";

	public static final String STATUS_VALUE_AVAILABLE = "Available";
	public static final String STATUS_VALUE_NOT_AVAILABLE = "Not Available";

	public static void main(String[] args) throws IOException {
		String line = "";
		String splitBy = ",";
		int rowNumber = 0;
		Map<String, List<String>> errors = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(IN_VALID_CSV));
		while ((line = br.readLine()) != null) {
			rowNumber++;
			if (rowNumber == 1)
				continue;

			String[] row = line.split(splitBy);

			List<String> list = new ArrayList<>();
			String errorKey = "row number " + rowNumber;
			String serialNo = row[0];
			if (!validate(TITLE_SERIAL_NO, serialNo)) {
				list.add(TITLE_SERIAL_NO);
			}
			String partNo = row[1];
			if (!validate(TITLE_PART_NO, partNo)) {
				list.add(TITLE_PART_NO);
			}
			String userName = row[2];
			if (!validate(TITLE_USER_NAME, userName)) {
				list.add(TITLE_USER_NAME);
			}
			String userId = row[3];
			if (!validate(TITLE_USER_ID, userId)) {
				list.add(TITLE_USER_ID);
			}
			String location = row[4];
			if (!validate(TITLE_LOCATION, location)) {
				list.add(TITLE_LOCATION);
			}
			String inventory = row[5];
			if (!validate(TITLE_INVENTORY, inventory)) {
				list.add(TITLE_INVENTORY);
			}
			String status = row[6];
			if (!validate(TITLE_STATUS, status)) {
				list.add(TITLE_STATUS);
			}

			if (list.size() > 0) {
				errors.put(errorKey, list);
			}

		}

		if (errors.isEmpty()) {
			System.out.println("Valid Csv file");
		} else {
			System.out.println("Not valid Csv file");
			for (Map.Entry<String, List<String>> entry : errors.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}

		}

	}

	public static boolean validate(String column, String value) {
		String regex = null;
		switch (column) {
		case TITLE_SERIAL_NO:
			regex = "[0-9]+";
			break;
		case TITLE_PART_NO:
			regex = "[0-9]+";
			break;
		case TITLE_USER_NAME:
			regex = "[a-zA-Z]+";
			break;
		case TITLE_USER_ID:
			regex = "^[A-Za-z0-9_-]*$";
			break;
		case TITLE_INVENTORY:
			regex = "[0-9]+";
			break;
		case TITLE_LOCATION:
			regex = "^[A-Za-z0-9_-]*$";
			break;

		}
		boolean flage = false;
		if (regex != null) {
			flage = ((!value.equals("")) && (value != null) && (value.matches(regex)));
		} else if (TITLE_STATUS.equalsIgnoreCase(column)) {
			flage = (value.equals(STATUS_VALUE_AVAILABLE) || value.equals(STATUS_VALUE_NOT_AVAILABLE));
		}

		return flage;
	}

}
