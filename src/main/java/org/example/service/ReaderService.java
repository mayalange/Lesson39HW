package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dto.ReaderDTO;
import org.example.model.Reader;
import org.example.repository.ReaderRepository;

public class ReaderService {
    private final ReaderRepository readerRepository;
    private final Logger logger = LogManager.getLogger();


    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public void addReader(ReaderDTO readerDTO) {
        Reader reader = new Reader();
        reader.setName(readerDTO.getName());
        reader.setEmail(readerDTO.getEmail());
        reader.setPhone(readerDTO.getPhone());

        try {
            readerRepository.create(reader);
            logger.info("Reader created successfully: ID={}, Email={}",
                    reader.getId(), reader.getEmail());
        } catch (Exception e) {
            logger.error("Failed to create reader: {}", e.getMessage(), e);
            throw e;
        }    }
}