package com.kodilla.hibernate.invoice.dao;

import com.kodilla.hibernate.invoice.Invoice;
import com.kodilla.hibernate.invoice.Item;
import com.kodilla.hibernate.invoice.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemDaoTestSuite {
    @Autowired
    ProductDao productDao;

    @Test
    public void testProductDaoSave() {
        //Given
        Item cheeseGouda = new Item(new BigDecimal(20.00), 2, new BigDecimal(40.00));
        Item cheeseEdam = new Item(new BigDecimal(19.00), 1, new BigDecimal(19.00));
        Item cheeseNoName = new Item(new BigDecimal(15.75), 1, new BigDecimal(15.75));

        Product cheese = new Product("cheese");
        cheese.getItems().add(cheeseEdam);
        cheese.getItems().add(cheeseGouda);
        cheese.getItems().add(cheeseNoName);
        //When
        productDao.save(cheese);
        int id = cheese.getId();
        //Then
        Assert.assertNotEquals(0, id);
        //CleanUp
        //productDao.delete(id);
    }
}

