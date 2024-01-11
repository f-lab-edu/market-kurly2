package com.hello.kurly.orders.v1.repository;

import com.hello.kurly.orders.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface OrderProductRepository extends JpaRepository<OrderProduct, BigInteger> {

}
