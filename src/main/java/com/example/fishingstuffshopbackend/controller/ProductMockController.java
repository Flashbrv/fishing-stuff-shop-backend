package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.dto.ProductDto;
import com.example.fishingstuffshopbackend.repository.ProductInMemoryDB;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/standalone/mock")
public class ProductMockController {
    @RequestMapping(value="/create", method= RequestMethod.POST)
    @ResponseBody
    public void create(@RequestBody ProductDto product){
        ProductInMemoryDB.INSTANCE
                .add(product);
    }

    @RequestMapping(value="/findAll", method=RequestMethod.GET)
    public @ResponseBody List<ProductDto> findAll(){
        return ProductInMemoryDB.INSTANCE
                .findAll();
    }

    @RequestMapping(value="/findById/{productId}", method=RequestMethod.GET)
    public @ResponseBody ProductDto findById( @PathVariable("productId") Integer productId){
        return ProductInMemoryDB.INSTANCE
                .findById(productId)
                .orElseThrow();
    }

    @RequestMapping(value="/remove/{productId}", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("productId") Integer productId){
        ProductInMemoryDB.INSTANCE
                .remove(productId);
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST)
    @ResponseBody
    public void edit(@RequestBody ProductDto product){
        ProductInMemoryDB.INSTANCE
                .edit(product);
    }
}
