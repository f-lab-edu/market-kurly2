package com.hello.kurly.orders.domain;

import com.hello.kurly.common.model.BaseTimeEntity;
import com.hello.kurly.products.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "order_product")
@Entity
public class OrderProduct extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private BigInteger id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  private Integer quantity; // 수량

  private BigInteger productPrice; // 가격

  public OrderProduct(BigInteger id,
                      Order order,
                      Product product,
                      Integer quantity,
                      BigInteger productPrice) {
    this.id = id;
    this.order = order;
    this.product = product;
    this.quantity = quantity;
    this.productPrice = productPrice;
  }
}
