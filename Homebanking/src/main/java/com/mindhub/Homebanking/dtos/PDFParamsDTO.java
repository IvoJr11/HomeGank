package com.mindhub.Homebanking.dtos;

import java.time.LocalDateTime;

public class PDFParamsDTO {

    private String numberAccount;

    private LocalDateTime dateSince;

    private LocalDateTime dateUntil;

    public PDFParamsDTO (){};

    public PDFParamsDTO (String numberAccount, LocalDateTime dateSince, LocalDateTime dateUntil){

        this.numberAccount = numberAccount;
        this.dateSince = dateSince;
        this.dateUntil = dateUntil;

    };

    public String getNumberAccount() {return numberAccount;}

    public LocalDateTime getDateSince() {return dateSince;}

    public LocalDateTime getDateUntil() {return dateUntil;}
}
