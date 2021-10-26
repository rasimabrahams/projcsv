package com.csv.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csv.model.Responce;

@RestController
@RequestMapping("/csv")
public class CSVController {

	@Autowired
	private CsvService service;

	@PostMapping("/validate")
	public Responce validateCsv(@RequestParam("file") MultipartFile file) {
		Map<String, List<String>> errors = service.readAndValidate(file);
		Responce responce = new Responce();
		if (errors.size() == 0) {
			responce.setMsg("Valid Csv file");
		}else {
			responce.setMsg("Not a valid csv file");
			responce.setError(errors);
		}

		return responce;
	}

}
