package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "product_id") // FK hacia Product.productId
    private Product product;


}
