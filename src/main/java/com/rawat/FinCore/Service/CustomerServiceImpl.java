package com.rawat.FinCore.Service;

import com.rawat.FinCore.DTO.CustomerRequest;
import com.rawat.FinCore.DTO.CustomerResponse;
import com.rawat.FinCore.Entities.Customer;
import com.rawat.FinCore.Mapper.CustomerMapper;
import com.rawat.FinCore.Repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        customerRepository.findByEmail(request.getEmail())
                .ifPresent(c -> { throw new IllegalArgumentException("Email already in use"); });

        Customer customer = CustomerMapper.toEntity(request);
        customer.setCreatedAt(LocalDateTime.now());
        Customer saved = customerRepository.save(customer);
        return CustomerMapper.toDto(saved);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        CustomerMapper.updateEntity(request, existing);
        Customer saved = customerRepository.save(existing);
        return CustomerMapper.toDto(saved);
    }



    @Override
    public CustomerResponse getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return CustomerMapper.toDto(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
}
