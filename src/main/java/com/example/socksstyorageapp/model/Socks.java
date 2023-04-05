package com.example.socksstyorageapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "color")
    String color;
    @Column(name="cotton_part")
    Integer cottonPart;
    @Column(name="quantity")
    int quantity;


}
