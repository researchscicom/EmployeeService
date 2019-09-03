package com.employee.service;

import com.employee.model.Company;

public interface ProducerService {
    Object sendMsg(Long proId) throws Exception;
}
