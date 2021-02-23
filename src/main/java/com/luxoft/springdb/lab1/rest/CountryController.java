package com.luxoft.springdb.lab1.rest;

import com.luxoft.springdb.lab1.dao.CountryDao;
import com.luxoft.springdb.lab1.dao.CountryNotFoundException;
import com.luxoft.springdb.lab1.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {

    private final CountryDao countryDao;

    @Autowired
    public CountryController(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<String> handleNotEnoughFunds(CountryNotFoundException ex) {
        return ResponseEntity.badRequest().body("Country not found");
    }

    @RequestMapping(value = "/country/all", method = RequestMethod.GET)
    public List<Country> getAllCountries() {
        return countryDao.getCountryList();
    }

    @GetMapping(value = "/country/name")
    public Country getCountryByName(@RequestParam("name") String name) throws CountryNotFoundException {
        return countryDao.getCountryByName(name);
    }

    @GetMapping(value = "/country/code")
    public Country getCountryByCode(@RequestParam("code") String code) {
        return countryDao.getCountryByCodeName(code);
    }

    @PutMapping("/country/{code}/holder")
    public void changeCountryName(
            @PathVariable("code") String code,
            @RequestParam("name") String newName
    ) {
        countryDao.updateCountryName(code, newName);
    }

    @RequestMapping(value = "/country/{startWith}", method = RequestMethod.GET)
    public List<Country> getAllCountries( @PathVariable("startWith") String startWith) {
        return countryDao.getCountryListStartWith(startWith);
    }
}
