package com.example.TouristSystem.Controllers;

import org.springframework.web.bind.annotation.*;
import com.example.TouristSystem.Services.SearchService;
import com.example.TouristSystem.Models.Accommodation;
import com.example.TouristSystem.Models.Tourpackage;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/accommodations")
    public List<Accommodation> searchAccommodations(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "false") boolean availableOnly) {

        if (availableOnly && location != null) {
            return searchService.searchAvailableAccommodationByLocation(location);
        }
        if (location != null) {
            return searchService.searchAccommodationByLocation(location);
        }
        if (type != null) {
            return searchService.searchAccommodationByType(type);
        }
        return List.of();
    }

    @GetMapping("/packages")
    public List<Tourpackage> searchPackages(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double maxPrice) {

        if (location != null) {
            return searchService.searchTourPackageByLocation(location);
        }
        if (name != null) {
            return searchService.searchTourPackageByName(name);
        }
        if (maxPrice != null) {
            return searchService.searchTourPackageByMaxPrice(maxPrice);
        }
        return List.of();
    }
}
