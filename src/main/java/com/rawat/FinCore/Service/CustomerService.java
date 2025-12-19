package com.rawat.FinCore.Service;
import com.rawat.FinCore.DTO.CustomerRequest;
import com.rawat.FinCore.DTO.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);
    CustomerResponse updateCustomer(Long id, CustomerRequest request);
    CustomerResponse getCustomer(Long id);
    List<CustomerResponse> getAllCustomers();
    void deleteCustomer(Long id);
}
