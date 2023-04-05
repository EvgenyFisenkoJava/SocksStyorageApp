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

    /**
     * Add new socks to the storage.
     *
     * @param socks The Socks object to add to the storage.
     * @return A ResponseEntity containing the newly added Socks object.
     * @throws WrongQuantityException   If the quantity of socks to add is negative or zero.
     * @throws WrongCottonPartException If the cotton part of the socks is outside the valid
     * range of [0, 100].
     */
    @PostMapping("/income")
    public ResponseEntity<Socks> addSocks(
            @RequestBody Socks socks) throws WrongQuantityException, WrongCottonPartException {

        return ResponseEntity.ok(socksService.addSocks(socks));
    }

    /**
     * Remove socks from the storage.
     *
     * @param socks The Socks object to remove from the storage.
     * @return A ResponseEntity containing the removed Socks object.
     * @throws NotFoundException If the specified socks are not found in the storage.
     * @throws WrongRemoveQuantityException If the quantity of socks to remove is negative or zero.
     */
    @PostMapping("/outcome")
    public ResponseEntity<Socks> removeSocks(
            @RequestBody Socks socks) throws NotFoundException, WrongRemoveQuantityException {

        return ResponseEntity.ok(socksService.removeSocks(socks));
    }

    /**
     * Get the quantity of socks in the storage that match the specified color, operation, and cotton part.
     *
     * @param color The color of the socks to search for.
     * @param operation The operation to apply to the cotton part comparison.
     * @param cottonPart The cotton part of the socks to search for.
     * @return A ResponseEntity containing the quantity of matching socks in the storage.
     * @throws WrongCottonPartException If the cotton part is outside the valid range of [0, 100].
     */
    @GetMapping()
    public ResponseEntity<Integer> findSocks(
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "operation", required = false) OperationsEnum operation,
            @RequestParam(value = "cottonPart", required = false) Integer cottonPart) throws WrongCottonPartException {

        return ResponseEntity.ok(socksService.getSocksList(color, operation, cottonPart));
    }
}
