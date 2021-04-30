package com.uoc.myeasyjob.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyEasyJobError {
    private String errorCode;
    private String errorDescription;
    private String errorMessage;
    private Integer status;
}
