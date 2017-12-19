package com.kodilla.hibernate.employee.dao;

import com.kodilla.hibernate.employee.Company;
import com.kodilla.hibernate.employee.Employee;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyDaoTestSuite {
    @Autowired
    CompanyDao companyDao;
    @Autowired
    EmployeeDao employeeDao;

    Employee johnSmith = new Employee("John", "Smith");
    Employee stephanieClarkson = new Employee("Stephanie", "Clarkson");
    Employee lindaKovalsky = new Employee("Linda", "Kovalsky");

    Company softwareMachine = new Company("Software Machine");
    Company dataMaesters = new Company("Data Maesters");
    Company greyMatter = new Company("Grey Matter");

    @Before
    public void beforeTest() {
        softwareMachine.getEmployees().add(johnSmith);
        dataMaesters.getEmployees().add(stephanieClarkson);
        dataMaesters.getEmployees().add(lindaKovalsky);
        greyMatter.getEmployees().add(johnSmith);
        greyMatter.getEmployees().add(lindaKovalsky);

        johnSmith.getCompanies().add(softwareMachine);
        johnSmith.getCompanies().add(greyMatter);
        stephanieClarkson.getCompanies().add(dataMaesters);
        lindaKovalsky.getCompanies().add(dataMaesters);
        lindaKovalsky.getCompanies().add(greyMatter);

        companyDao.save(softwareMachine);
        companyDao.save(dataMaesters);
        companyDao.save(greyMatter);
    }

    @After
    public void afterTest() {
        companyDao.delete(softwareMachine);
        companyDao.delete(dataMaesters);
        companyDao.delete(greyMatter);
    }

    @Test
    public void testSaveCompanyDao() {
        //Given
        int softwareMachineId = softwareMachine.getId();
        int dataMaestersId = dataMaesters.getId();
        int greyMatterId = greyMatter.getId();
        //When
        //Then
        Assert.assertNotEquals(0, softwareMachineId);
        Assert.assertNotEquals(0, dataMaestersId);
        Assert.assertNotEquals(0, greyMatterId);
    }

    @Test
    public void testFindEmployeeByLastname() {
        //Given
        //When
        List<Employee> employeesWithNextLastname = employeeDao.findEmployeesByLastnameLike("Smith");
        int foundedEmployeeId = employeesWithNextLastname.iterator().next().getId();
        Employee readFoundedEmployee = employeeDao.findOne(foundedEmployeeId);
        //Then
        Assert.assertEquals(1, employeesWithNextLastname.size());
        Assert.assertEquals(foundedEmployeeId, readFoundedEmployee.getId());
    }

    @Test
    public void testFindNoExistingEmployeeByLastname() {
        //Given
        //When
        List<Employee> employeesWithNextLastname = employeeDao.findEmployeesByLastnameLike("Clark");
        //Then
        Assert.assertEquals(0, employeesWithNextLastname.size());
    }

    @Test
    public void testFindCompanyByFirstThreeLetters() {
        //Given
        //When
        List<Company> companiesWithNextFirstThreeLetters = companyDao.findCompaniesByNameFirstThreeLettersLike("Sof");
        int foundedCompanyId = companiesWithNextFirstThreeLetters.iterator().next().getId();
        Company readFoundedCompany = companyDao.findOne(foundedCompanyId);
        //Then
        Assert.assertEquals(1, companiesWithNextFirstThreeLetters.size());
        Assert.assertEquals(foundedCompanyId, readFoundedCompany.getId());
    }

    @Test
    public void testFindNoExistingCompanyByFirstThreeLetters() {
        //Given
        //When
        List<Company> companiesWithNextFirstThreeLetters = companyDao.findCompaniesByNameFirstThreeLettersLike("Xyz");
        //Then
        Assert.assertEquals(0, companiesWithNextFirstThreeLetters.size());
    }

    @Test
    public void testFindCompanyByNameWithSubstring() {
        //Given
        //When
        List<Company> companiesWithSubstring = companyDao.findCompaniesByNameWithSubstringLike("%twa%");
        int foundedCompanyId = companiesWithSubstring.iterator().next().getId();
        Company readFoundedCompany = companyDao.findOne(foundedCompanyId);
        //Then
        Assert.assertEquals(1, companiesWithSubstring.size());
        Assert.assertEquals(foundedCompanyId, readFoundedCompany.getId());
    }

    @Test
    public void testFindNoExistingCompanyByNameWithSubstring() {
        //Given
        //When
        List<Company> companiesWithSubstring = companyDao.findCompaniesByNameWithSubstringLike("%xyz%");
        //Then
        Assert.assertEquals(0, companiesWithSubstring.size());
    }

    @Test
    public void testFindEmployeesByLastnameWithSubstring() {
        //Given
        //When
        List<Employee> employeeLastnamesWithSubstring = employeeDao.findEmployeesByLastnameWithSubstring("%ith%");
        int foundedEmployeeId = employeeLastnamesWithSubstring.iterator().next().getId();
        Employee readFoundedEmployees = employeeDao.findOne(foundedEmployeeId);
        //Then
        Assert.assertEquals(1, employeeLastnamesWithSubstring.size());
        Assert.assertEquals(foundedEmployeeId, readFoundedEmployees.getId());
    }

    @Test
    public void testFindNoExistingEmployeeByLastnameWithSubstring() {
        //Given
        //When
        List<Employee> employeeLastnamesWithSubstring = employeeDao.findEmployeesByLastnameWithSubstring("%xyz%");
        //Then
        Assert.assertEquals(0, employeeLastnamesWithSubstring.size());
    }
}
