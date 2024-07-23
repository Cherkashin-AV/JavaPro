package ru.vtb.javapro.task4.dto;


import java.math.BigDecimal;


public record ProductDTO(Long id,
                         String accontNumber,
                         BigDecimal balance,
                         String productType)
{

}
