package com.example.socksstyorageapp.controller;

import com.example.socksstyorageapp.exception.NotFoundException;
import com.example.socksstyorageapp.exception.WrongCottonPartException;
import com.example.socksstyorageapp.exception.WrongQuantityException;
import com.example.socksstyorageapp.exception.WrongRemoveQuantityException;
import com.example.socksstyorageapp.model.Socks;
import com.example.socksstyorageapp.operation.OperationsEnum;
import com.example.socksstyorageapp.service.SocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
public class SocksController {
    private final SocksService socksService;

    @PostMapping("/income")
    public ResponseEntity<Socks> addSocks(
            @RequestBody Socks socks) throws WrongQuantityException, WrongCottonPartException {

        return ResponseEntity.ok(socksService.addSocks(socks));
    }

    @PostMapping("/outcome")
    public ResponseEntity<Socks> removeSocks(
            @RequestBody Socks socks) throws NotFoundException, WrongRemoveQuantityException {

        return ResponseEntity.ok(socksService.removeSocks(socks));
    }

    @GetMapping()
    public ResponseEntity<Integer> findSocks(
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "operation", required = false) OperationsEnum operation,
            @RequestParam(value = "cottonPart", required = false) Integer cottonPart) throws WrongCottonPartException {

        return ResponseEntity.ok(socksService.getSocksList(color, operation, cottonPart));
    }
}
