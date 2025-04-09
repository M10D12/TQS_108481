package com.example.HM.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@EqualsAndHashCode(of = {"name", "location"})
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    
}
