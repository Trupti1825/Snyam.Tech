package com.snyamtech.repository;

import com.snyamtech.model.HolidaysPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidaysPackageRepository extends JpaRepository<HolidaysPackage, Long> {

}
