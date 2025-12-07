package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    void addAddressBook(AddressBook addressBook);

    List<AddressBook> getByList(AddressBook addressBook);

    void updateAddressBook(AddressBook addressBook);

    void deleteAddressBook(Long id);

    AddressBook getById(Long id);

    void updateDefault(AddressBook addressBook);

    void updateDefaultById(AddressBook addressBook);
}
