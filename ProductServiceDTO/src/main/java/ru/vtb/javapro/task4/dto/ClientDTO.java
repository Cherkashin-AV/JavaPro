package ru.vtb.javapro.task4.dto;


import java.util.List;


public record ClientDTO(Long id, String clientName, List<ProductDTO> products)
{

}
