package com.objectia.JBD_HandsOnLearning.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;


@Data
public class AccountRequestDTO {
    @NotNull(message = "Balance cannot be null")
    @Positive(message = "Balance must be positive")
    private Float balance;
    @NotBlank(message = "status cannot be blank")
    private String status;
    private UUID accountHolderId;
}
