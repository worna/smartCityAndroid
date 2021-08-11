package com.worna.sportoo.services.mappers;

import com.worna.sportoo.models.Customer;
import com.worna.sportoo.repositories.web.dto.CustomerDto;

public class CustomerMapper {
    private static CustomerMapper instance = null;

    public static CustomerMapper getInstance() {
        if (instance == null) {
            instance = new CustomerMapper();
        }
        return instance;
    }

    public Customer mapToCustomer(CustomerDto dto) {
        if (dto == null) {
            return null;
        }

        return new Customer(dto.getFirst_name(), dto.getLast_name(), dto.getBirth_date(), dto.getGender(), dto.getPhone_number(), dto.getEmail(), dto.getPassword(), dto.getInscription_date(), dto.getIs_manager(), dto.getIs_instructor(), dto.getLanguage(), dto.getAddress(), dto.getCity_name(), dto.getZip_code(), dto.getCountry());
    }
}
