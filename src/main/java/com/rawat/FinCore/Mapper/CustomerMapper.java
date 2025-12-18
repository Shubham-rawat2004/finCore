package com.rawat.FinCore.Mapper;

import com.rawat.FinCore.DTO.CustomerRequest;
import com.rawat.FinCore.DTO.CustomerResponse;
import com.rawat.FinCore.Entities.Customer;

public final class CustomerMapper {

    private CustomerMapper() {
    }

    public static Customer toEntity(CustomerRequest dto) {
        Customer c = new Customer();
        c.setName(dto.getName());
        c.setDateOfBirth(dto.getDateOfBirth());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());
        c.setAddressLine1(dto.getAddressLine1());
        c.setAddressLine2(dto.getAddressLine2());
        c.setCity(dto.getCity());
        c.setState(dto.getState());
        c.setZipCode(dto.getZipCode());
        return c;
    }

    public static void updateEntity(CustomerRequest dto, Customer c) {
        c.setName(dto.getName());
        c.setDateOfBirth(dto.getDateOfBirth());
        c.setPhone(dto.getPhone());
        c.setAddressLine1(dto.getAddressLine1());
        c.setAddressLine2(dto.getAddressLine2());
        c.setCity(dto.getCity());
        c.setState(dto.getState());
        c.setZipCode(dto.getZipCode());
    }

    public static CustomerResponse toDto(Customer c) {
        CustomerResponse dto = new CustomerResponse();
        dto.setId(c.getCustomerId());
        dto.setName(c.getName());
        dto.setDateOfBirth(c.getDateOfBirth());
        dto.setEmail(c.getEmail());
        dto.setPhone(c.getPhone());
        dto.setAddressLine1(c.getAddressLine1());
        dto.setAddressLine2(c.getAddressLine2());
        dto.setCity(c.getCity());
        dto.setState(c.getState());
        dto.setZipCode(c.getZipCode());
        dto.setCreatedAt(c.getCreatedAt());
        return dto;
    }
}
