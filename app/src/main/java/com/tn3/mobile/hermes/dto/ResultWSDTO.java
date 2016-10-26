package com.tn3.mobile.hermes.dto;

public class ResultWSDTO {

    public String url;
    public String method;
    public String stat;
    public String msg;
    public String block;
    public String cause;
    public ManifestosDTO[] content;

    public ResultWSDTO(){}

    public ResultWSDTO(String url, String method, String stat, String msg, String block, String cause, ManifestosDTO[] content) {
        this.url = url;
        this.method = method;
        this.stat = stat;
        this.msg = msg;
        this.block = block;
        this.content = content;
        this.cause = cause;
    }
}
