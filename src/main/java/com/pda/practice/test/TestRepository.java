package com.pda.practice.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TestRepository {

    @Autowired
    private DataSource dataSource;

    public String getDataSource() {
        return dataSource.toString();
    }
}
