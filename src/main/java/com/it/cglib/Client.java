package com.it.cglib;

import com.it.proxy.IProducer;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

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
         * 基於子類的動態代理:
         *      涉及的類: Enhancer
         *      提供者: 第三方cglib庫
         * 如何創建代理對象:
         *      使用Enhancer類中的create方法
         * 創建代理對象的要求:
         *      被代理類不能是最終類
         * create方法的參數:
         *      Class:
         *          用於指定被代理對象的字節碼
         *      Callback:
         *          讓我們寫如何代理 通常都是寫一個該接口的實現類 通常情況下都是匿名 但是不必須
         *          此接口的實現類都是誰用 誰寫
         *          一般寫的都是該接口的子接口實現類: MethodInterceptor
       */
        Producer cglibProducer = (Producer)Enhancer.create(producer.getClass(), new MethodInterceptor(){
            /**
             * 執行被代理對象的任何方法都會經過該方法
             * @param prooxy
             * @param method
             * @param args
             * 以上三個參數和基於接口的動態代理中invoke方法的參數是一樣的
             * @param methodProxy: 當前執行方法的代理對象
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object prooxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

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
        cglibProducer.saleProduct(12000f);
    }
}
