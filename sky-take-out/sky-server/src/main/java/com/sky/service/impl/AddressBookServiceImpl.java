package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper userAddressBookMapper;

    @Override
    public void addAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        userAddressBookMapper.addAddressBook(addressBook);
    }

    @Override
    public List<AddressBook> getList() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> addressBookList = userAddressBookMapper.getByList(addressBook);
        return addressBookList;
    }

    @Override
    public void updateAddressBook(AddressBook addressBook) {
        userAddressBookMapper.updateAddressBook(addressBook);
    }

    @Override
    public void deleteAddressBook(Long id) {
        userAddressBookMapper.deleteAddressBook(id);
    }

    @Override
    public AddressBook getById(Long id) {
        AddressBook addressBook = userAddressBookMapper.getById(id);
        return addressBook;
    }

    @Override
    public void updateDefault(AddressBook addressBook) {
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        userAddressBookMapper.updateDefault(addressBook);

        addressBook.setIsDefault(1);
        userAddressBookMapper.updateDefaultById(addressBook);
    }

    @Override
    public List<AddressBook> getDefault() {
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> addressBookList = userAddressBookMapper.getByList(addressBook);
        return addressBookList;
    }
}
