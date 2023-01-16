package com.example.apibasic.validate;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.YearMonth;

public class Card {

    @NotBlank
    private String cardNo;
    @JsonFormat(pattern = "yyyy-MM")
    @Future
    private YearMonth validMonth;

}
