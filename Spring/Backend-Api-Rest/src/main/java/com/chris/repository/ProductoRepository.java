package com.chris.repository;

import com.chris.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
   // Select p from producto p where p.nombre like %?%
   List<Producto> findByNombreContainingIgnoreCase(String term);  // Busca por nombre que contenga el term e ignore si es mayuscula o minuscula.
}
