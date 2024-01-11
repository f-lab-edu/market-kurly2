package com.hello.kurly.products.domain;

import com.hello.kurly.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "categories")
@Entity
public class Category extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private BigInteger id;

  private String name; // 카테고리 명

  public Category(BigInteger id, String name) {
    this.id = id;
    this.name = name;
  }
}
