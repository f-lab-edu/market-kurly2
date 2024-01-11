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
@Table(name = "sub_products")
@Entity
public class SubProduct extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private BigInteger id; // 상품 pk

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "category_id")
  private Category category; //카테고리 ID
  private String name; // 상품이름

  private String brand; //브랜드 이름

  private String tag; // 태그 목록들

  private BigInteger basePrice; // 상품 기본 가격

  private BigInteger retailPrice; // 소매 가격

  private Integer discountRate; // 할인율

  private BigInteger discountedPrice; // 할인 금액

  private Integer restock; // 재고량

  private Boolean canRestockNotify; // 남은 재고 여부

  private Integer minQuantity; // 최소주문수량

  private Integer maxQuantity; // 최대주문수량

  private Boolean isSoldOut; // 상품이 다팔렸는지 여부

  private Boolean isPurchaseStatus; // 상품 구매 가능 여부

  private Boolean isExpectedPoint; // 적립 여부
  private String mainImageUrl; // 이미지 URL 주소

  public SubProduct(BigInteger id,
                     Product product,
                     Category category,
                     String name,
                     String brand,
                     String tag,
                     BigInteger basePrice,
                     BigInteger retailPrice,
                     Integer discountRate,
                     BigInteger discountedPrice,
                     Integer restock,
                     Boolean canRestockNotify,
                     Integer minQuantity,
                     Integer maxQuantity,
                     Boolean isSoldOut,
                     Boolean isPurchaseStatus,
                     Boolean isExpectedPoint,
                     String mainImageUrl) {
    this.id = id;
    this.product = product;
    this.category = category;
    this.name = name;
    this.brand = brand;
    this.tag = tag;
    this.basePrice = basePrice;
    this.retailPrice = retailPrice;
    this.discountRate = discountRate;
    this.discountedPrice = discountedPrice;
    this.restock = restock;
    this.canRestockNotify = canRestockNotify;
    this.minQuantity = minQuantity;
    this.maxQuantity = maxQuantity;
    this.isSoldOut = isSoldOut;
    this.isPurchaseStatus = isPurchaseStatus;
    this.isExpectedPoint = isExpectedPoint;
    this.mainImageUrl = mainImageUrl;
  }
}
