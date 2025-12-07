package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAddressBookController {
    @Autowired
    private AddressBookService userAddressBookService;

    @PostMapping("/user/addressBook")
    public Result add(@RequestBody AddressBook addressBook){
        userAddressBookService.addAddressBook(addressBook);
        return Result.success();
    }
    @GetMapping("/user/addressBook/list")
    public Result addressBookList(){
        List<AddressBook> addressBookList = userAddressBookService.getList();
        return Result.success(addressBookList);
    }

    @PutMapping("/user/addressBook")
    public Result update(@RequestBody AddressBook addressBook){
        userAddressBookService.updateAddressBook(addressBook);
        return Result.success();
    }

    @DeleteMapping("/user/addressBook")
    public Result delete(@RequestParam Long id){
        userAddressBookService.deleteAddressBook(id);
        return Result.success();
    }

    @GetMapping("/user/addressBook/{id}")
    public Result getById(@PathVariable Long id){
        AddressBook addressBookList = userAddressBookService.getById(id);
        return Result.success(addressBookList);
    }

    @PutMapping("/user/addressBook/default")
    public Result setDefault(@RequestBody AddressBook addressBook){
        userAddressBookService.updateDefault(addressBook);
        return Result.success();
    }

    @GetMapping("/user/addressBook/default")
    public Result getDefault(){
        List<AddressBook> addressBookList = userAddressBookService.getDefault();
        return Result.success(addressBookList);
    }
}
