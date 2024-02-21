package com.snyamtech.controller;

import com.snyamtech.model.HolidaysPackage;
import com.snyamtech.repository.HolidaysPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
public class HolidaysPackageController {

    @Autowired
    private HolidaysPackageRepository packageRepository;


    /*-------THIS METHOD IS USE FOR GET PACKAGES LIST--------*/
    @GetMapping("/list")
    public List<HolidaysPackage> getAllPackages() {
        return packageRepository.findAll();
    }


    /*-------THIS METHOD IS USE FOR GET PACKAGES BY ID--------*/
    @GetMapping("/{id}")
    public HolidaysPackage getPackageById(@PathVariable Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid package Id:" + id));
    }

    /*-------THIS METHOD IS USE FOR POST PACKAGE--------*/
    @PostMapping("/save")
    public HolidaysPackage createPackage(@RequestBody HolidaysPackage holidaysPackage) {
        return packageRepository.save(holidaysPackage);
    }


    /*-------THIS METHOD IS USE FOR UPDATE PACKAGES--------*/
    @PatchMapping("/{id}")
    public HolidaysPackage updatePackage(@PathVariable Long id, @RequestBody HolidaysPackage updatedPackage) {
        return packageRepository.findById(id)
                .map(pkg -> {
                    if (updatedPackage.getPackageName() != null) {
                        pkg.setPackageName(updatedPackage.getPackageName());
                    }
                    if (updatedPackage.getPackagePrice() != null) {
                        pkg.setPackagePrice(updatedPackage.getPackagePrice());
                    }
                    if (updatedPackage.getPackageType() != null) {
                        pkg.setPackageType(updatedPackage.getPackageType());
                    }
                    if (updatedPackage.getPackageDuration() != null) {
                        pkg.setPackageDuration(updatedPackage.getPackageDuration());
                    }
                    if (updatedPackage.getPackageDescription() != null) {
                        pkg.setPackageDescription(updatedPackage.getPackageDescription());
                    }
                    if (updatedPackage.getPackageDestination() != null) {
                        pkg.setPackageDestination(updatedPackage.getPackageDestination());
                    }
                    return packageRepository.save(pkg);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid package Id:" + id));
    }


    /*-------THIS METHOD IS USE FOR DELETE PACKAGES--------*/
     @DeleteMapping("/delete/{id}")
     public ResponseEntity<String> deletePackage(@PathVariable Long id) {
    if (packageRepository.existsById(id)) {
        packageRepository.deleteById(id);
        return ResponseEntity.ok("Package deleted successfully!");
    } else {
        return ResponseEntity.badRequest().body("Package not found with id: " + id);
    }
}

}
