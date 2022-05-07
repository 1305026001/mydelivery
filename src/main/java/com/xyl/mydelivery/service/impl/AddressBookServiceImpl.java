package com.xyl.mydelivery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyl.mydelivery.entity.AddressBook;
import com.xyl.mydelivery.mapper.AddressBookMapper;
import com.xyl.mydelivery.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
   implements AddressBookService{
}
