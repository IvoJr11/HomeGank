package com.mindhub.Homebanking.services.implement;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.mindhub.Homebanking.dtos.PDFParamsDTO;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.models.Transaction;
import com.mindhub.Homebanking.services.AccountService;
import com.mindhub.Homebanking.services.ClientService;
import com.mindhub.Homebanking.services.PDFGeneratorService;
import com.mindhub.Homebanking.services.TransactionService;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @Override
    public void PDFGenerator (HttpServletResponse response, Authentication authentication, PDFParamsDTO pdfParamsDTO) throws IOException, DocumentException {

        Client client1 = clientService.getClientCurrent(authentication);

        Set<Transaction> transactions = transactionService.getTransactionsByAccount(pdfParamsDTO.getNumberAccount());


        transactions = transactions.stream().filter(transaction -> transaction.getDate().isAfter(pdfParamsDTO.getDateSince()) && transaction.getDate().isBefore(pdfParamsDTO.getDateUntil())).collect(Collectors.toSet());

        Document document = new Document (PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = new Font(Font.BOLD);
        font.setSize(18);

        Font fontSub = new Font(Font.BOLD);
        fontSub.setSize(10);

        Color color = new Color(195, 211, 241, 221);

        Paragraph title = new Paragraph("Transaction's info", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(12);

        Paragraph subtitle = new Paragraph("Transactions of " + pdfParamsDTO.getNumberAccount() + " account", fontSub);
        subtitle.setAlignment(Paragraph.ALIGN_LEFT);
        subtitle.setIndentationLeft(20);
        subtitle.setSpacingAfter(8);
        subtitle.setSpacingBefore(8);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);

        List<String> headers = new ArrayList<>(List.of(
                "Date",
                "Destination Account",
                "Description",
                "Type",
                "Amount",
                "Balance"
        ));


        headers.forEach(h -> {

            PdfPCell c1 = new PdfPCell(new Phrase(h));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setVerticalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(color);
            table.addCell(c1);

        });

        Comparator<Transaction> idComparator = Comparator.comparing(Transaction::getId);

        transactions.stream().sorted(idComparator.reversed()).forEach(t -> {

            PdfPCell c2 = new PdfPCell(Phrase.getInstance(t.getDate().format(ISO_LOCAL_DATE)));
            table.addCell(c2);
            table.addCell(t.getAccount().getNumber() + "");
            table.addCell(t.getDescription() + "");
            table.addCell(t.getType() + "");
            table.addCell("$" + t.getAmount() + "");
            table.addCell("$" + t.getNewAmount() + "");

        });

        document.add(title);
        document.add(subtitle);
        document.add(table);
        document.close();

    }
}
