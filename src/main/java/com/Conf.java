package com;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class Conf extends Configuration {

    @JsonProperty("dbUrl")
    @NotNull
    public String dbUrl;

    @JsonProperty("filePath")
    @NotNull
    public String filePath;

}
