package edu.cs544.mario477.dto;

import lombok.Data;
import org.bouncycastle.operator.bc.BcContentSignerBuilder;

@Data
public class MailDTO {

    private String from;
    private String to;
    private String subject;
    private String text;

    public MailDTO(String from, String to, String subject, String text) {
        this.from = from.isEmpty() ? "admin@email.com" : from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }


}
