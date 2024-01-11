package com.hello.kurly.users.v1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class AddressResponse {

  private String zipCode;
  private String address;
  private String addressDetail;

  public AddressResponse(String zipCode,
                         String address,
                         String addressDetail) {
    this.zipCode = zipCode;
    this.address = address;
    this.addressDetail = addressDetail;
  }
}
