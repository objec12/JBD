package com.objectia.JBD_HandsOnLearning.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CardResponseDTO {
    private UUID id;

    private String cvv;

    private String cardNumber;

    private LocalDate expiryDate;
}
