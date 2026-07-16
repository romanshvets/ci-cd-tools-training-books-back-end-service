package com.books.model;

public class MetaDTO {

    private final String version;
    private final String buildDate;

    public MetaDTO(String version, String buildDate) {
        this.version = version;
        this.buildDate = buildDate;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildDate() {
        return buildDate;
    }
}
