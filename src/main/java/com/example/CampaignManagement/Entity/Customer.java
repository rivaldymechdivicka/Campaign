package com.example.CampaignManagement.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customerId", nullable = false)
    private Long customerId;

    @Column(name = "Name", nullable = false)
    private String Name;

    @Column(name = "Email", nullable = false)
    private String Email;

    @Column(name = "CountryCode", nullable = true)
    private String countryCode;

}