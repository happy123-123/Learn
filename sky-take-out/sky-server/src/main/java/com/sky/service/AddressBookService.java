package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void addAddressBook(AddressBook addressBook);

    List<AddressBook> getList();

    void updateAddressBook(AddressBook addressBook);

    void deleteAddressBook(Long id);

    AddressBook getById(Long id);

    void updateDefault(AddressBook addressBook);

    List<AddressBook> getDefault();
}
