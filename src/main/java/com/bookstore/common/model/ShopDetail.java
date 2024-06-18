package com.bookstore.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shop_detail")
public class ShopDetail extends  Common<Integer>{

    @Column(name = "OPERATING_HOURS")
    private String operatingHours;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SHIPPING_POLICY")
    private String shippingPolicy;

    @Column(name = "RETURN_POLICY")
    private String returnPolicy;

    //  (1-1)Shop
    /* Delete ShopDetails, Does Not Delete Shop */
    @OneToOne( mappedBy = "shopDetail",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    private Shop shop;


    @Override
    public String toString() {
        return "ShopDetail{" +
                "operatingHours=" + operatingHours +
                ", description='" + description + '\'' +
                ", shippingPolicy='" + shippingPolicy + '\'' +
                ", returnPolicy='" + returnPolicy + '\'' +
                '}';
    }
}
