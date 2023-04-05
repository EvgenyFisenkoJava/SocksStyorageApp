package com.example.socksstyorageapp.repository;

import com.example.socksstyorageapp.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocksRepository extends JpaRepository<Socks,Long> {
    List<Socks> findAllByCottonPartLessThan(Integer cottonPart);
    List<Socks> findAllByCottonPartGreaterThan(Integer cottonPart);
    List<Socks> findAllByCottonPart(Integer cottonPart);
    List<Socks> findAllByColor(String color);
    Socks findSocksByColorAndCottonPart(String color, Integer cottonPart);
}

