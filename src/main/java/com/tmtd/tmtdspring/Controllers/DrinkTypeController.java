package com.tmtd.tmtdspring.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tmtd.tmtdspring.Models.DrinkType;
import com.tmtd.tmtdspring.Models.LiquidUnit;
import com.tmtd.tmtdspring.Repository.DrinkTypeRepository;
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
public class DrinkTypeController {
    @Autowired
    DrinkTypeRepository drinkTypeRepository;

    @GetMapping("/drinktypes")
    public ResponseEntity<List<DrinkType>> getAllDrinkTypes(){
        try{
            List<DrinkType> drinkTypes = new ArrayList<DrinkType>();
            if(drinkTypes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(drinkTypes,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/drinktype/{id}")
    public ResponseEntity<DrinkType> getDrinkType(@PathVariable("id") long id){
        Optional<DrinkType> drinkType = drinkTypeRepository.findById(id);
        if(drinkType.isPresent()){
            return new ResponseEntity<>(drinkType.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/drinktype/create")
    public ResponseEntity<DrinkType> createDrinkType(@RequestBody DrinkType drinkType){
        try{
            DrinkType drinkType1 = drinkTypeRepository
                    .save(new DrinkType(drinkType.getType()));
            return  new ResponseEntity<>(drinkType1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/drinktype/update/{id}")
    public ResponseEntity<DrinkType> updateDrinkType(@PathVariable("id") long id, @RequestBody DrinkType drinkType) {
        Optional<DrinkType> typeRepository = drinkTypeRepository.findById(id);

        if (typeRepository.isPresent()) {
            DrinkType drinkType1 = typeRepository.get();
            drinkType1.setType(drinkType.getType());
            return new ResponseEntity<>(drinkTypeRepository.save(drinkType1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/drinktype/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDrinkType(@PathVariable("id") long id) {
        try {
            drinkTypeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/drinktypes")
    public ResponseEntity<HttpStatus> deleteAllDrinkTypes() {
        try {
            drinkTypeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
