package com.example.miniapp.controllers;

import com.example.miniapp.models.Captain;
import com.example.miniapp.services.CaptainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/captain")
public class CaptainController {

    private final CaptainService captainService;

    @Autowired
    public CaptainController(CaptainService captainService) {
        this.captainService = captainService;
    }

    @GetMapping("/allCaptains")
    public List<Captain> getAll() {
        return captainService.getAllCaptains();
    }

    @GetMapping("/{id}")
    public Captain getById(@PathVariable Long id) {
        return captainService.getCaptainById(id);
    }

    @GetMapping("/filterByRating")
    public List<Captain> filterByRating(@RequestParam Double ratingThreshold) {
        return captainService.getCaptainsByRating(ratingThreshold);
    }

    @GetMapping("/filterByLicenseNumber")
      public ResponseEntity<Captain> filterByLicenseNumber(@RequestParam String licenseNumber) {
               // tests expect 200 OK and a null body if not found
               Captain captain = captainService.getCaptainByLicenseNumber(licenseNumber);
               return ResponseEntity.ok(captain);
           }

    @PostMapping("/addCaptain")
    public Captain add(@RequestBody Captain captain) {
        return captainService.addCaptain(captain);
    }
}
