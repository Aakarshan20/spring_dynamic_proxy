package com.it.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 模擬一個消費者
 */
public class Client {
    public static void main(String[] args) {
        final Producer producer = new Producer();
        /**動態代理
         * 特點class隨用隨創建 隨用隨加載
         * 作用: 不修改源碼的基礎上對方法增強
         * 分類:
         *      基於接口的動態代理
         *          如果該類沒有實現任何接口 會出現代理異常
         *      基於子類的動態代理
         *
         * 基於接口的動態代理:
         *      涉及的類: Proxy
         *      提供者: JDK官方
         * 如何創建代理對象:
         *      使用Proxy類中的newProxyInstance方法
         * 創建代理對象的要求:
         *      被代理類至少實現一個接口, 如果沒有則不能使用
         * newProxyInstance方法的參數:
         *      ClassLoader:
         *          類加載器用於加載代理對象的class 和被代理對象使用相同的類加載器 固定寫法
         *      Class[]
         *          讓代理對象與被代理對象有相同的方法 固定寫法
         *      InvocationHandler
         *          讓我們寫如何代理 通常都是寫一個該接口的實現類 通常情況下都是匿名 但是不必須
         *          此接口的實現類都是誰用 誰寫
       */
        Producer proxyProducer =(Producer) Proxy.newProxyInstance(producer.getClass().getClassLoader(), producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 作用: 執行被代理對象的任何接口方法都會經過該方法
                     * @param proxy     代理對象的引用(一般不用)
                     * @param method    當前執行的方法
                     * @param args      當前執行方法所需的參數
                     * @return          和被代理對象方法有相同的返回值
                     * @throws Throwable
                     */

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object returnValue = null;
                        //提供增強的代碼
                        //1.獲取方法執行的參數
                        Float money = (Float)args[0];
                        //2.判斷當前方法是不是銷售
                        if("saleProduct".equals(method.getName())){
                            returnValue =  method.invoke(producer, money*0.8f);
                        }
                        return returnValue;
                    }
                });
        proxyProducer.saleProduct(10000f);
    }
}
