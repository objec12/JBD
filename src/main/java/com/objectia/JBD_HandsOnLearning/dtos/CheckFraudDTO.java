package com.objectia.JBD_HandsOnLearning.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CheckFraudDTO {

    BigDecimal transactionAmount;
    Integer trxCount;
    UUID transactionId;
}
