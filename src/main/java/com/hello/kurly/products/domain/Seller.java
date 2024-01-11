package com.hello.kurly.products.domain;

import com.hello.kurly.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "sellers")
@Entity
public class Seller extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private BigInteger id; // 상위 상품 pk

  @OneToOne(fetch = LAZY, mappedBy = "seller")
  private Product product;

  public Seller(BigInteger id, Product product) {
    this.id = id;
    this.product = product;
  }
}
