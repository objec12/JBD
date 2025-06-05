package com.objectia.JBD_HandsOnLearning.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class AccountResponseDTO {

    private UUID id;

    private Float balance;

    private String status;

    private UUID accountHolderId;

}
