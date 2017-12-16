package com.kodilla.hibernate.employee.dao;

import com.kodilla.hibernate.employee.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CompanyDao extends CrudRepository<Company, Integer> {
    @Query
    List<Company> findCompaniesByNameFirstThreeLettersLike(@Param("NAME") String name);
}
