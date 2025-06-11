package com.crio.rent_read.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import com.crio.rent_read.enums.AvailabilityStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Table(name = "book")
public class Book {
    
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private String genre;
    // public static boolean isAvailable;


@Enumerated(EnumType.STRING)
@Column(name = "availability_status", nullable = false)
@Builder.Default
private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;


    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "book" , cascade = CascadeType.ALL)
    private List<Rental> rentals = new ArrayList<>();
}





