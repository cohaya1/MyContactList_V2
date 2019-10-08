package com.example.mycontactlistreform;




public class ContactAddress extends Contact {
    private String streetAddress;

    public ContactAddress() {

    }

    @Override
    public String getStreetAddress() {
        return streetAddress;


    }

    @Override
    public void setStreetAddress(String streetAddress) {
        super.setStreetAddress(streetAddress);
        this.streetAddress = streetAddress;

    }
}