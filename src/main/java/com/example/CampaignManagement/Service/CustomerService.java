package com.example.CampaignManagement.Service;

import com.example.CampaignManagement.Dto.CustomerRequest;
import com.example.CampaignManagement.Dto.CustomerResponse;
import com.example.CampaignManagement.Entity.Customer;
import com.example.CampaignManagement.Repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, RedisTemplate<String, Object> redisTemplate) {
        this.customerRepository = customerRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequest, customer);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerResponse customerResponse = convertToDto(savedCustomer);

        // Simpan data customer ke Redis
        redisTemplate.opsForValue().set("customer:" + savedCustomer.getCustomerId(), customerResponse);

        return customerResponse;
    }

    public CustomerResponse getCustomerById(Long customerId) {
        // Coba ambil data dari Redis terlebih dahulu
        CustomerResponse cachedCustomer = (CustomerResponse) redisTemplate.opsForValue().get("customer:" + customerId);
        if (cachedCustomer != null) {
            return cachedCustomer;
        }

        // Jika tidak ada di Redis, ambil dari database
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
        CustomerResponse customerResponse = convertToDto(customer);

        // Simpan data ke Redis untuk akses selanjutnya
        redisTemplate.opsForValue().set("customer:" + customerId, customerResponse);

        return customerResponse;
    }

    public CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
        BeanUtils.copyProperties(customerRequest, customer, "customerId");
        Customer updatedCustomer = customerRepository.save(customer);
        CustomerResponse customerResponse = convertToDto(updatedCustomer);

        // Update data customer di Redis
        redisTemplate.opsForValue().set("customer:" + customerId, customerResponse);

        return customerResponse;
    }

    public void deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }
        customerRepository.deleteById(customerId);

        // Hapus data customer dari Redis
        redisTemplate.delete("customer:" + customerId);
    }

    private CustomerResponse convertToDto(Customer customer) {
        CustomerResponse dto = new CustomerResponse();
        dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setCountryCode(customer.getCountryCode());
        return dto;
    }
}
