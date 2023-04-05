package com.example.socksstyorageapp.service.impl;

import com.example.socksstyorageapp.exception.NotFoundException;
import com.example.socksstyorageapp.exception.WrongCottonPartException;
import com.example.socksstyorageapp.exception.WrongQuantityException;
import com.example.socksstyorageapp.exception.WrongRemoveQuantityException;
import com.example.socksstyorageapp.model.Socks;
import com.example.socksstyorageapp.operation.OperationsEnum;
import com.example.socksstyorageapp.repository.SocksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocksServiceImplTest {

    @Mock
    private SocksRepository socksRepository;
    @InjectMocks
    private SocksServiceImpl socksService;

    @Test
    public void testAddSocks() throws WrongQuantityException, WrongCottonPartException {
        Socks socks = new Socks(1L, "red", 50, 10);
        Socks socksFound = new Socks(1L, "red", 50, 5);

        when(socksRepository.findSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart()))
                .thenReturn(socksFound);
        when(socksRepository.save(socksFound)).thenReturn(socksFound);

        socksFound.setQuantity(socksFound.getQuantity() + socks.getQuantity());

        Socks result = socksService.addSocks(socksFound);

        verify(socksRepository, times(1)).findSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        verify(socksRepository, times(1)).save(socksFound);
        assertEquals(socksFound.getQuantity(), result.getQuantity());
    }

    @Test
    public void testAddSocksSocksNotFound() throws WrongQuantityException, WrongCottonPartException {
        Socks socks = new Socks(1L, "red", 50, 10);

        when(socksRepository.findSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart()))
                .thenReturn(null);
        when(socksRepository.save(socks)).thenReturn(socks);

        Socks result = socksService.addSocks(socks);

        verify(socksRepository, times(1)).findSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        verify(socksRepository, times(1)).save(socks);
        assertEquals(socks.getQuantity(), result.getQuantity());
    }

    @Test
    public void shouldThrowWrongQuantityExceptionWhenAddSocks() {
        Socks socks = new Socks(1L, "red", 50, 0);

        assertThrows(WrongQuantityException.class, () -> socksService.addSocks(socks));
        verify(socksRepository, never()).save(any());
    }

    @Test
    public void shouldThrowWrongCottonPartExceptionWhenAddSocks() {
        Socks socks = new Socks(1L, "red", 101, 5);

        assertThrows(WrongCottonPartException.class, () -> socksService.addSocks(socks));
        verify(socksRepository, never()).save(any());
    }

    @Test
    void shouldRemoveSocksQuantity() throws NotFoundException, WrongRemoveQuantityException {
        Socks socksToRemove = new Socks(1L, "red", 60, 5);
        Socks socks = new Socks(1L, "red", 60, 20);

        when(socksRepository.findSocksByColorAndCottonPart(socksToRemove.getColor(), socksToRemove.getCottonPart()))
                .thenReturn(socks);

        when(socksRepository.save(socks)).thenReturn(socks);

        Socks result = socksService.removeSocks(socksToRemove);

        assertEquals(socks.getQuantity(), result.getQuantity());
    }

    @Test
    void assertThrowsNotFoundExceptionWhenRemoveSocks() {
        Socks socksToRemove = new Socks(1L, "red", 60, 5);

        when(socksRepository.findSocksByColorAndCottonPart(socksToRemove.getColor(), socksToRemove.getCottonPart()))
                .thenReturn(null);

        assertThrows(NotFoundException.class, () -> socksService.removeSocks(socksToRemove));
    }

    @Test
    void assertThrowsWrongRemoveQuantityExceptionWhenRemoveSocks() {
        Socks socksToRemove = new Socks(1L, "red", 60, 20);
        Socks socks = new Socks(1L, "red", 60, 5);

        when(socksRepository.findSocksByColorAndCottonPart(socksToRemove.getColor(), socksToRemove.getCottonPart()))
                .thenReturn(socks);

        assertThrows(WrongRemoveQuantityException.class, () -> socksService.removeSocks(socksToRemove));
    }

    @Test
    void shouldReturnQuantityWhenFindSocks() throws WrongCottonPartException {

        Socks socks1 = new Socks(1L, "red", 50, 10);
        Socks socks2 = new Socks(2L, "blue", 70, 5);
        Socks socks3 = new Socks(3L, "green", 30, 20);
        List<Socks> socksList1 = new ArrayList<>();
        socksList1.add(socks1);

        List<Socks> socksList2 = new ArrayList<>();
        socksList2.add(socks2);

        List<Socks> socksList3 = new ArrayList<>();
        socksList3.add(socks3);

        List<Socks> allSocksList = new ArrayList<>();
        allSocksList.add(socks1);
        allSocksList.add(socks2);
        allSocksList.add(socks3);

        int totalQuantity = 35;

        when(socksRepository.findAll()).thenReturn(allSocksList);
        when(socksRepository.findAllByColor("red")).thenReturn(socksList1);
        when(socksRepository.findAllByCottonPartLessThan(50)).thenReturn(Arrays.asList(socks1, socks3));
        when(socksRepository.findAllByCottonPartGreaterThan(50)).thenReturn(socksList2);
        when(socksRepository.findAllByCottonPart(50)).thenReturn(socksList1);

        // Test with color filter
        int result1 = socksService.getSocksList("red", null, null);
        assertEquals(socks1.getQuantity(), result1);

        // Test with cottonPart filter and LESS_THAN operation
        int result2 = socksService.getSocksList(null, OperationsEnum.LESSTHAN, 50);
        assertEquals(socks1.getQuantity() + socks3.getQuantity(), result2);

        // Test with cottonPart filter and MORE_THAN operation
        int result3 = socksService.getSocksList(null, OperationsEnum.MORETHAN, 50);
        assertEquals(socks2.getQuantity(), result3);

        // Test with cottonPart filter and EQUALS operation
        int result4 = socksService.getSocksList(null, OperationsEnum.EQUALS, 50);
        assertEquals(socks1.getQuantity(), result4);

        // Test without any filters
        int result5 = socksService.getSocksList(null, null, null);
        assertEquals(totalQuantity, result5);
    }

    @Test
    void assertThrowsWrongCottonPartExceptionWhenFindSocks() {
        assertThrows(WrongCottonPartException.class, () -> socksService.getSocksList(null, OperationsEnum.EQUALS, 150));
    }
}


