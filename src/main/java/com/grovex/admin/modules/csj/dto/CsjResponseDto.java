package com.grovex.admin.modules.csj.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class CsjResponseDto {
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Data")
    private Map<String, List<AdDataDto>> data;
}
