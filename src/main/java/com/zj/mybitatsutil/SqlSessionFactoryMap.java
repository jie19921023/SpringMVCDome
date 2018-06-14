package com.zj.mybitatsutil;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhaojie on 18-4-19.
 * 通过读取配置文件拿到session对象
 * 此类设计成单列,内部使用ConcurrentHashMap保存不同系统不同环境下的SqlSessionFactory工厂
 */
public final class SqlSessionFactoryMap {


    private static SqlSessionFactoryMap sqlSessionFactoryMap=new SqlSessionFactoryMap();
    private final Map<String,SqlSessionFactory> factoryMap=new ConcurrentHashMap<String,SqlSessionFactory>();//保证多线程安全
    private SqlSessionFactoryMap(){}
    public static SqlSessionFactoryMap getSqlSessionFactoryMap(){
        return  sqlSessionFactoryMap;
    }

    public SqlSession getSqlSession(String componentName,String environment){
        SqlSessionFactory sqlSessionFactory=getSqlSessionFactory(componentName,environment);
        return sqlSessionFactory.openSession(true);//开启自动提交
    }

    @Deprecated
    public SqlSession getBatchSqlSession(String componentName,String environment){
        SqlSessionFactory sqlSessionFactory=getSqlSessionFactory(componentName,environment);
        return sqlSessionFactory.openSession(ExecutorType.BATCH,false);//开启批量插入返回自增id有问题,必须手动提交,自动提交可能会导致内存溢出
    }

    //没有采用同步,因为没有全局变量.理论上是不会引发线程安全的
    private SqlSessionFactory getSqlSessionFactory(String componentName,String environment){
        String environmentKey=componentName +"::"+environment;
        if(!factoryMap.containsKey(environmentKey)){
            SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
            try {
                Reader reader=Resources.getResourceAsReader(componentName + "-mybatis.xml");
                SqlSessionFactory sqlSessionFactory=builder.build(reader,environment);
                factoryMap.put(environmentKey,sqlSessionFactory);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return factoryMap.get(environmentKey);

    }
}
