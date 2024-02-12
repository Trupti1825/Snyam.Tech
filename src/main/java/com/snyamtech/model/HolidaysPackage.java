package com.snyamtech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "holiday_packages")
public class HolidaysPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "package_price")
    private Double packagePrice;

    @Column(name = "package_type")
    private String packageType;

    @Column(name = "package_duration")
    private Integer packageDuration;

    @Column(name = "package_description")
    private String packageDescription;

    @Column(name = "destination")
    private String packageDestination;

}
