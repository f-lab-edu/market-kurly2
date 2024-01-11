package com.hello.kurly.orders.v1.repository;

import com.hello.kurly.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface OrderRepository extends JpaRepository<Order, BigInteger> {

}
