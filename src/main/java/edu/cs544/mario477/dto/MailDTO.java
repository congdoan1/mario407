package edu.cs544.mario477.dto;

import lombok.Data;
import org.bouncycastle.operator.bc.BcContentSignerBuilder;

@Data
public class MailDTO {

    private String from;
    private String to;
    private String subject;
    private String content;


}
