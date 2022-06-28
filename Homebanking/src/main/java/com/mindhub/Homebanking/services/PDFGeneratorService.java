package com.mindhub.Homebanking.services;

import com.lowagie.text.DocumentException;
import com.mindhub.Homebanking.dtos.PDFParamsDTO;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.models.Transaction;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public interface PDFGeneratorService {

    void PDFGenerator(HttpServletResponse response, Authentication authentication, PDFParamsDTO pdfParamsDTO) throws IOException, DocumentException;
}
