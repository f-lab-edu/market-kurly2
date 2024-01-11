package com.hello.kurly.orders.v1.repository;

import com.hello.kurly.orders.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface DeliveryRepository extends JpaRepository<Delivery, BigInteger> {
}
