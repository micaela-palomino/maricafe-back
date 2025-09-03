package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


//Despues vemos si sacamos esta clase o no.
@Entity
@Data
public class Image {
    @Id
    private Integer id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}