package com.it.cglib;

import com.it.proxy.IProducer;

/**
 * 一個生產者
 */
public class Producer {
    public void saleProduct(float money){
        System.out.println("銷售產品 並拿到錢:"+money);
    }
    public void afterService(float money){
        System.out.println("提供售後服務 並拿到錢:"+money);
    }
}
