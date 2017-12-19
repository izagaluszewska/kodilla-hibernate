package com.kodilla.hibernate.employee.facade;

import com.kodilla.hibernate.employee.Company;
import com.kodilla.hibernate.employee.Employee;
import com.kodilla.hibernate.employee.dao.CompanyDao;
import com.kodilla.hibernate.employee.dao.EmployeeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class Facade {
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CompanyDao companyDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(Facade.class);

    public List<Employee> searchEmployeeByLastnameWithSubstring(String partLastname) throws SearchingException {
        LOGGER.info("Starting search for employee's lastname containing this letters: " + partLastname);
        partLastname = "%" + partLastname + "%";
        List<Employee> searchedEmployees = employeeDao.findEmployeesByLastnameWithSubstring(partLastname);
        if (searchedEmployees.size() == 0) {
            LOGGER.error(SearchingException.ERR_NO_EMPLOYEE_FIT);
            throw new SearchingException(SearchingException.ERR_NO_EMPLOYEE_FIT);
        } else {
        LOGGER.info("Found " + searchedEmployees.size() + " employee/employees with lastname containing searched letters");
        return searchedEmployees;
        }
    }

    public List<Company> searchCompanyBySubstring(String partName) throws SearchingException {
        LOGGER.info("Starting search for employee's lastname containing this letters: " + partName);
        partName = "%" + partName + "%";
        List<Company> searchedCompanies = companyDao.findCompaniesByNameWithSubstringLike(partName);
        if (searchedCompanies.size() == 0) {
            LOGGER.error(SearchingException.ERR_NO_COMPANY_FIT);
            throw new SearchingException(SearchingException.ERR_NO_COMPANY_FIT);
        } else {
            LOGGER.info("Found " + searchedCompanies.size() + " company/companies with name containing searched letters");
            return searchedCompanies;
        }
    }
}