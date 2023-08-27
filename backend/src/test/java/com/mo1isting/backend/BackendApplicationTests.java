package com.mo1isting.backend;

import com.mo1isting.backend.entity.Coffee;
import com.mo1isting.backend.mapper.CoffeeMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Resource
    CoffeeMapper coffeeMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testInsert(){
        Coffee coffee = new Coffee();
        coffee.setCoffeeName("PassedChampion");
        coffee.setCoffeeFlavor("白巧克力 核果");
        coffee.setCoffeeOrigin("哥伦比亚 中国");
        coffee.setCoffeePic("null");
        coffee.setCoffeeProcess(1);
        coffee.setCoffeeRoast(3);
        coffee.setCoffeeShop("LetsGrind");
        int insert = coffeeMapper.insert(coffee);
        System.out.println("coffeeId is: " + coffee.getCoffeeId());
    }
}
