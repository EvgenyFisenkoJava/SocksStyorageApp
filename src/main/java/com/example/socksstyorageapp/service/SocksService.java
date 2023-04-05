package com.example.socksstyorageapp.service;

import com.example.socksstyorageapp.exception.NotFoundException;
import com.example.socksstyorageapp.exception.WrongCottonPartException;
import com.example.socksstyorageapp.exception.WrongQuantityException;
import com.example.socksstyorageapp.exception.WrongRemoveQuantityException;
import com.example.socksstyorageapp.model.Socks;
import com.example.socksstyorageapp.operation.OperationsEnum;

import java.io.FileNotFoundException;
import java.util.List;

public interface SocksService {
    Socks addSocks(Socks socks) throws WrongQuantityException, WrongCottonPartException;
    Socks removeSocks(Socks socks) throws NotFoundException, WrongRemoveQuantityException;
    int getSocksList(String color, OperationsEnum operation, Integer cottonPart) throws WrongCottonPartException;
}
