package com.hello.kurly.products.domain;

import com.hello.kurly.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "products")
@Entity
public class Product extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private BigInteger id; // 상위 상품 pk

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "storage_id", referencedColumnName = "id")
  private Storage storage;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "seller_id", referencedColumnName = "id")
  private Seller seller;

  @OneToMany(fetch = LAZY)
  private List<SubProduct> subProduct = new ArrayList<>();

  private String shortDescription; //상품 설명

  private String sellerName; // 판매처

  private String deliveryTypeName; // 배송 타입

  private LocalDateTime expirationDate; // 유통기한

  private String mainImageUrl; // 이미지 URL 주소

  public Product(BigInteger id,
                  Storage storage,
                  Seller seller,
                  List<SubProduct> subProduct,
                  String shortDescription,
                  String sellerName,
                  String deliveryTypeName,
                  LocalDateTime expirationDate,
                  String mainImageUrl) {
    this.id = id;
    this.storage = storage;
    this.seller = seller;
    this.subProduct = subProduct;
    this.shortDescription = shortDescription;
    this.sellerName = sellerName;
    this.deliveryTypeName = deliveryTypeName;
    this.expirationDate = expirationDate;
    this.mainImageUrl = mainImageUrl;
  }
}
