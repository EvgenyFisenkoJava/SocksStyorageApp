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
    /**
     * Adds socks to the storage.
     * If there are already socks with the same color and cottonPart in the storage, the quantity
     * of the existing socks is increased by the quantity of the new socks being added.
     *
     * @param socks The Socks object to add to the storage.
     * @return The saved Socks object.
     * @throws WrongQuantityException if the quantity of socks to add is less than 1.
     * @throws WrongCottonPartException if the cotton part of the socks is not within the valid range of 0 to 100.
     */
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

    /**
     * Removes socks from the storage.
     *
     * @param socks The Socks object to remove from the storage.
     * @return The saved Socks object.
     * @throws NotFoundException if the socks to remove are not found in the storage.
     * @throws WrongRemoveQuantityException if the quantity of socks to remove is greater than the quantity in storage.
     */
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

    /**
     * Returns the total quantity of socks in the storage based on the given color and/or
     * cotton part and operation.
     * If color is null, the total quantity of all socks in the storage is returned.
     * If cottonPart and operation are null, the total quantity of socks with the given color
     * is returned.
     * If cottonPart and operation are not null, the total quantity of socks with the given
     * color and that meet the criteria of the operation is returned.
     *
     * @param color The color of the socks to include in the total quantity. If null, all socks are included.
     * @param operation The operation to apply to the cotton part of the socks. If null, cotton part is not considered.
     * @param cottonPart The cotton part of the socks to apply the operation to. If null, cotton part is not considered.
     * @return The total quantity of socks in the storage based on the given criteria.
     * @throws WrongCottonPartException if the cotton part is not within the valid range of 0 to 100.
     */
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
             if(cottonPart >100) {
                throw new WrongCottonPartException("CottonPart must be between 0 and 100");
            }
        }
        return socksList.stream()
                .mapToInt(Socks::getQuantity)
                .sum();
    }
}
