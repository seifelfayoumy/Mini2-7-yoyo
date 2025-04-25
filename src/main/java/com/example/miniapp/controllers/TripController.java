package com.example.miniapp.controllers;

import com.example.miniapp.models.Captain;
import com.example.miniapp.models.Customer;
import com.example.miniapp.models.Trip;
import com.example.miniapp.services.TripService;
import com.example.miniapp.services.CaptainService;
import com.example.miniapp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {

    private final TripService tripService;
    private final CaptainService captainService;
    private final CustomerService customerService;

    @Autowired
    public TripController(TripService tripService, CaptainService captainService, CustomerService customerService) {
        this.tripService = tripService;
        this.captainService = captainService;
        this.customerService = customerService;
    }

    // DTO matching your test JSON payload
    public static class TripRequest {
        public LocalDateTime tripDate;
        public String origin;
        public String destination;
        public Double tripCost;
        public Long captainId;
        public Long customerId;
        // getters/setters omitted for brevity
    }

       @PostMapping("/addTrip")
       public ResponseEntity<Trip> addTrip(@RequestBody TripRequest req) {
               Trip trip = new Trip();
               trip.setTripDate(req.tripDate);
               trip.setOrigin(req.origin);
               trip.setDestination(req.destination);
               trip.setTripCost(req.tripCost);
        
               // only fetch if an ID was provided
               if (req.captainId != null) {
                   trip.setCaptain(captainService.getCaptainById(req.captainId));
               }
               if (req.customerId != null) {
                   trip.setCustomer(customerService.getCustomerById(req.customerId));
               }
        
               Trip saved = tripService.addTrip(trip);
               // tests expect 200 OK, not 201 CREATED
               return ResponseEntity.ok(saved);
           }

    @GetMapping("/allTrips")
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        try {
            Trip trip = tripService.getTripById(id);
            return ResponseEntity.ok(trip);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody TripRequest req) {
        try {
            // For updates you can reuse the same DTO
            Trip updated = new Trip();
            updated.setTripDate(req.tripDate);
            updated.setOrigin(req.origin);
            updated.setDestination(req.destination);
            updated.setTripCost(req.tripCost);

            if (req.captainId != null) {
                Captain cap = captainService.getCaptainById(req.captainId);
                updated.setCaptain(cap);
            }
            if (req.customerId != null) {
                Customer cust = customerService.getCustomerById(req.customerId);
                updated.setCustomer(cust);
            }

            Trip result = tripService.updateTrip(id, updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
   public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
               // tests expect 200 OK even if the trip doesn’t exist
               if (tripService.existsById(id)) {
                   tripService.deleteTrip(id);
               }
               return ResponseEntity.ok().build();
           }

    @GetMapping("/findByDateRange")
    public List<Trip> findByDateRange(@RequestParam LocalDateTime startDate,
                                      @RequestParam LocalDateTime endDate) {
        return tripService.findTripsWithinDateRange(startDate, endDate);
    }

    @GetMapping("/findByCaptainId")
    public List<Trip> findByCaptain(@RequestParam Long captainId) {
        return tripService.findTripsByCaptainId(captainId);
    }
}
