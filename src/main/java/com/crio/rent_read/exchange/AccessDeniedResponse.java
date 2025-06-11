package com.crio.rent_read.exchange;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({ "message", "httpStatus", "localDateTime" })  // 👈 Force field order
public class AccessDeniedResponse {
    private String message;
    private String httpStatus;
    private String localDateTime;
}

