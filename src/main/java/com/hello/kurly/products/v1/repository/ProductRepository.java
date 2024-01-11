package com.hello.kurly.products.v1.repository;

import com.hello.kurly.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ProductRepository extends JpaRepository<Product, BigInteger> {

}
