package com.hello.kurly.orders.domain;

import com.hello.kurly.common.model.Address;
import com.hello.kurly.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "deliveries")
@Entity
public class Delivery extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private BigInteger id;

  @OneToOne(fetch = LAZY)
  private Order order;

  @Embedded
  private Address homeAddress;

  @Enumerated(EnumType.STRING)
  private OrderType status; //주문상태(주문완료, 주문취소, 주문불가)

  public Delivery(BigInteger id,
                  Order order,
                  Address homeAddress,
                  OrderType status) {
    this.id = id;
    this.order = order;
    this.homeAddress = homeAddress;
    this.status = status;
  }
}
