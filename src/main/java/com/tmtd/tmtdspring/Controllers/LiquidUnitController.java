package com.tmtd.tmtdspring.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tmtd.tmtdspring.Models.LiquidUnit;
import com.tmtd.tmtdspring.Repository.LiquidUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class LiquidUnitController {
    @Autowired
    LiquidUnitRepository liquidUnitRepository;

    @GetMapping("/units")
    public ResponseEntity<List<LiquidUnit>> getAllUnits(){
        try{
            List<LiquidUnit> liquidUnits = new ArrayList<LiquidUnit>();
            liquidUnitRepository.findAll().forEach(liquidUnits::add);
            if(liquidUnits.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(liquidUnits,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/units/{id}")
    public ResponseEntity<LiquidUnit> getUnit(@PathVariable("id") long id){
        Optional<LiquidUnit> liquidUnit = liquidUnitRepository.findById(id);
        if(liquidUnit.isPresent()){
            return new ResponseEntity<>(liquidUnit.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/units/create")
    public ResponseEntity<LiquidUnit> createUnit(@RequestBody LiquidUnit liquidUnit){
        try{
            LiquidUnit liquidUnit1 = liquidUnitRepository
                    .save(new LiquidUnit(liquidUnit.getUnit()));
            return  new ResponseEntity<>(liquidUnit1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/units/update/{id}")
    public ResponseEntity<LiquidUnit> updateUnit(@PathVariable("id") long id, @RequestBody LiquidUnit liquidUnit) {
        Optional<LiquidUnit> liquidUnit1 = liquidUnitRepository.findById(id);

        if (liquidUnit1.isPresent()) {
            LiquidUnit liquidUnit2 = liquidUnit1.get();
            liquidUnit2.setUnit(liquidUnit.getUnit());
            return new ResponseEntity<>(liquidUnitRepository.save(liquidUnit2), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/units/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUnit(@PathVariable("id") long id) {
        try {
            liquidUnitRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/units")
    public ResponseEntity<HttpStatus> deleteAllUnits() {
        try {
            liquidUnitRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
