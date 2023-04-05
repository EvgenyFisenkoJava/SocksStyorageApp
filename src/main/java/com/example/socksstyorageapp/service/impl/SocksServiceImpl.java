package com.example.socksstyorageapp.service.impl;

import com.example.socksstyorageapp.exception.NotFoundException;
import com.example.socksstyorageapp.exception.WrongCottonPartException;
import com.example.socksstyorageapp.exception.WrongQuantityException;
import com.example.socksstyorageapp.exception.WrongRemoveQuantityException;
import com.example.socksstyorageapp.model.Socks;
import com.example.socksstyorageapp.operation.OperationsEnum;
import com.example.socksstyorageapp.repository.SocksRepository;
import com.example.socksstyorageapp.service.SocksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class SocksServiceImpl implements SocksService {
    private final SocksRepository socksRepository;

    @Override
    public Socks addSocks(Socks socks) throws WrongQuantityException, WrongCottonPartException {
        log.info("Add socks in storage method was invoked: {}", socks );
        Socks socksFound = socksRepository.findSocksByColorAndCottonPart(
                socks.getColor(), socks.getCottonPart());

        if (socksFound != null) {
            int quantity = socksFound.getQuantity() + socks.getQuantity();

            if (socks.getQuantity() < 1) {
                throw new WrongQuantityException("Quantity must be at least 1");
            } else if (socks.getCottonPart() < 0 || socks.getCottonPart() > 100) {
                throw new WrongCottonPartException("CottonPart must be between 0 and 100");
            }

            socksFound.setQuantity(quantity);
            return socksRepository.save(socksFound);
        } else {
            if (socks.getQuantity() < 1) {
                throw new WrongQuantityException("Quantity must be at least 1");
            } else if (socks.getCottonPart() < 0 || socks.getCottonPart() > 100) {
                throw new WrongCottonPartException("CottonPart must be between 0 and 100");
            }

            return socksRepository.save(socks);
        }
    }

    @Override
    public Socks removeSocks(Socks socks) throws NotFoundException, WrongRemoveQuantityException {
        log.info("Remove socks from storage method was invoked: {}", socks);
        Socks socksFound = socksRepository.findSocksByColorAndCottonPart(
                socks.getColor(), socks.getCottonPart());

        if (socksFound != null) {
            int quantity = socksFound.getQuantity() - socks.getQuantity();
            if (socksFound.getQuantity() < socks.getQuantity()) {
                throw new WrongRemoveQuantityException("Quantity to remove is more than quantity in database");
            }
            socksFound.setQuantity(quantity);
            return socksRepository.save(socksFound);
        } else {
                throw new NotFoundException("Socks are not found in database");
            }
    }

    @Override
    public int getSocksList(
            String color, OperationsEnum operation, Integer cottonPart) throws WrongCottonPartException {
        log.info("Find socks quantity by color or cottonPart method was invoked");
        List<Socks> socksList;

        if (color != null) {
            socksList = socksRepository.findAllByColor(color);
        } else {
            socksList = socksRepository.findAll();
        }

        if (cottonPart != null && operation != null) {
            if (operation.equals(OperationsEnum.LESSTHAN)) {
                socksList = socksRepository.findAllByCottonPartLessThan(cottonPart);
            } else if (operation.equals(OperationsEnum.MORETHAN)) {
                socksList = socksRepository.findAllByCottonPartGreaterThan(cottonPart);
            } else if (operation.equals(OperationsEnum.EQUALS)) {
                socksList = socksRepository.findAllByCottonPart(cottonPart);
            }
            else if(cottonPart >100) {
                throw new WrongCottonPartException("CottonPart must be between 0 and 100");
            }
        }
        return socksList.stream()
                .mapToInt(Socks::getQuantity)
                .sum();
    }
}
