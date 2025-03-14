package com.microsservicos.pikachu.produtor.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservicos.pikachu.produtor.dto.DataDTO;
import com.microsservicos.pikachu.produtor.service.DataDTOService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;


@RestController
@RequestMapping("/datadto")
public class DataDTOController {


		@Autowired
		private DataDTOService dataDtoService;
		
	 	@PostMapping()
        public ResponseEntity<List<DataDTO>> getAll() throws IllegalStateException, FileNotFoundException{
        	
            String fileName = "src/main/resources/data.csv";
            
            CSVParser csvParser = new CSVParserBuilder()
                    .withSeparator(';') // Set your custom delimiter here
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(new FileReader(fileName))
                    .withCSVParser(csvParser)
                    .build();
            
            
            List<DataDTO> beans = new CsvToBeanBuilder<DataDTO>(csvReader)
                    .withType(DataDTO.class)
                    
                    .build()
                    .parse();
            	 dataDtoService.enviarMensagensParaRabbitMQ();
            	
            return ResponseEntity.ok(beans);
           
        }
    
}
