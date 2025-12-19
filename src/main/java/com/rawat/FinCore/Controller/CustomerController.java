package com.rawat.FinCore.Controller;

import com.rawat.FinCore.DTO.CustomerRequest;
import com.rawat.FinCore.DTO.CustomerResponse;
import com.rawat.FinCore.Service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @PreAuthorize("permitAll()")  // *** ADD THIS - PUBLIC ACCESS ***
    public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // *** ADMIN ONLY ***
    public CustomerResponse update(@PathVariable Long id,
                                   @RequestBody CustomerRequest request) {
        return customerService.updateCustomer(id, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // *** ADMIN ONLY ***
    public CustomerResponse getOne(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")  // *** ADMIN ONLY ***
    public List<CustomerResponse> getAll() {
        return customerService.getAllCustomers();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // *** ADMIN ONLY ***
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
