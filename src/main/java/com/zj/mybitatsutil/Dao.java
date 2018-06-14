package com.zj.mybitatsutil;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by zhaojie on 18-4-19.
 * 此包下面的类可以作为分布式系统的数据库底层操作,供其他系统使用
 */
public abstract class Dao {

    private SqlSessionWapper sqlSessionWapper;

    /**
     *
     * @param componentName 不同系统的名称,例如 web项目,app项目
     * @param environment 系统的部署环境,例如,开发,测试,线上环境
     */
    public Dao(String componentName, String environment){
        this.sqlSessionWapper=SqlSessionWapper.getInstance(componentName,environment);
    }

    protected Object selectOne(String statment,Object parameter){
        return sqlSessionWapper.selectOne(statment, parameter);
    }

    protected List selectList(String statment){
        return this.selectList(statment,null);
    }

    protected List selectList(String statment,  Object parameter){
        return this.selectList(statment,parameter,0,0);
    }
    protected List selectList(String statment,Object paramter,int offset,int limit){
        RowBounds rowBounds=RowBounds.DEFAULT;
        if(offset>=0 && limit <2147483647 && limit>0){
            rowBounds=new RowBounds(offset,limit);
        }
        return sqlSessionWapper.selectList(statment,paramter,rowBounds);
    }

    protected int insert(String statment){
        return this.insert(statment, null);
    }
    protected int insert(String statment,  Object parameter){
        return sqlSessionWapper.insert(statment,parameter);
    }

    protected int update(String statment){
        return this.update(statment,null);
    }
    protected int update(String statment,Object parameter){
        return sqlSessionWapper.update(statment,parameter);
    }

    protected int delete(String statment){
        return this.delete(statment,null);
    }
    protected int delete(String statment,Object parameter){
        return sqlSessionWapper.delete(statment,parameter);
    }

}
