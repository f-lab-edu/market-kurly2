package com.hello.kurly.common.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

  private String zipCode; //우편번호
  private String address; //기본주소
  private String addressDetail; //상세주소

  public Address() {
  }

  public Address(String zipCode, String address, String addressDetail) {
    this.zipCode = zipCode;
    this.address = address;
    this.addressDetail = addressDetail;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getAddress() {
    return address;
  }

  public String getAddressDetail() {
    return addressDetail;
  }
}
