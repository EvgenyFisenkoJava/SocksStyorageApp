package com.example.socksstyorageapp.controller;

import com.example.socksstyorageapp.model.Socks;
import com.example.socksstyorageapp.operation.OperationsEnum;
import com.example.socksstyorageapp.repository.SocksRepository;
import com.example.socksstyorageapp.service.impl.SocksServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SocksControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SocksRepository socksRepository;
    @SpyBean
    private SocksServiceImpl socksService;
    @InjectMocks
    private SocksController socksController;

    @Test
    void addSocks() throws Exception {
        Long id = 1L;
        String color = "black";
        Integer cottonPart = 50;
        int quantity = 10;

        JSONObject socksObject = new JSONObject();

        socksObject.put("color", color);
        socksObject.put("cottonPart", cottonPart);
        socksObject.put("quantity", quantity);
        Socks socks = new Socks();
        socks.setId(id);
        socks.setColor(color);
        socks.setCottonPart(cottonPart);
        socks.setQuantity(quantity);


        when(socksRepository.save(any(Socks.class))).thenReturn(socks);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/income")
                        .content(socksObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.cottonPart").value(cottonPart))
                .andExpect(jsonPath("$.quantity").value(quantity));
    }

    @Test
    void removeSocks() throws Exception {
        Long id = 1L;
        String color = "black";
        Integer cottonPart = 50;
        int quantity = 10;

        JSONObject socksObject = new JSONObject();

        socksObject.put("color", color);
        socksObject.put("cottonPart", cottonPart);
        socksObject.put("quantity", quantity);
        Socks socks = new Socks();
        socks.setId(id);
        socks.setColor(color);
        socks.setCottonPart(cottonPart);
        socks.setQuantity(quantity);

        when(socksRepository.save(any(Socks.class))).thenReturn(socks);
        when(socksRepository.findSocksByColorAndCottonPart(
                any(String.class), any(Integer.class))).thenReturn(socks);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/outcome")
                        .content(socksObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.cottonPart").value(cottonPart))
                .andExpect(jsonPath("$.quantity").value(0));
    }

    @Test
    void findSocks() throws Exception {
        OperationsEnum operation = OperationsEnum.EQUALS;
        int count = 10;
        Long id = 1L;
        String color = "black";
        Integer cottonPart = 50;
        int quantity = 10;

        Socks socks = new Socks();
        socks.setId(id);
        socks.setColor(color);
        socks.setCottonPart(cottonPart);
        socks.setQuantity(quantity);
        List<Socks>socksList = new ArrayList<>();
        socksList.add(socks);

        when(socksRepository.findAllByCottonPart(any(Integer.class))).thenReturn(socksList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks")
                        .param("color", color)
                        .param("cottonPart", cottonPart.toString())
                        .param("operation", operation.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(count));

    }
}