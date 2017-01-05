package com.pizza73.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.pizza73.model.Employee;
import com.pizza73.model.Shop;

public interface EmployeeUpdateFileConverter
{

    public List<Employee> parseEmployees(File excelFile, List<Shop> shops) throws FileNotFoundException, IOException;
}
