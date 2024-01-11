package com.hello.kurly.orders.domain;

import com.hello.kurly.common.model.BaseTimeEntity;
import com.hello.kurly.users.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
@Entity
public class Order extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private BigInteger id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "delivery_id", referencedColumnName = "id")
  private Delivery delivery;

  @OneToMany(mappedBy = "order")
  private List<OrderProduct> orderProduct = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private OrderType status; //주문상태(주문완료, 주문취소, 주문불가)

  public Order(BigInteger id,
                User user,
                Delivery delivery,
                OrderType status) {
    this.id = id;
    this.user = user;
    this.delivery = delivery;
    this.status = status;
  }
}
