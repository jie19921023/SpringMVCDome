package com.zj.mybitatsutil;

import org.apache.ibatis.exceptions.IbatisException;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhaojie on 18-4-19.
 *
 */
public final class SqlSessionWapper {

    private static final Map<String,SqlSessionWapper> wapperMap=new ConcurrentHashMap<String,SqlSessionWapper>();
    private String componmentName;
    private String environment;
    private SqlSessionWapper(String componmentName,String environment){
        this.componmentName=componmentName;
        this.environment=environment;
    }

    /**
     * 此类不是单例,是单例模式的扩展.这种设计可以减少内存中SqlSessionWapper对象数量
     * @param componeName
     * @param environment
     * @return
     */
    public static SqlSessionWapper getInstance(String componeName,String environment){
        String environmentKey=componeName+"::"+environment;
        if(!wapperMap.containsKey(environmentKey)){
            wapperMap.put(environmentKey,new SqlSessionWapper(componeName,environment));
        }
        return wapperMap.get(environmentKey);
    }

    public Object selectOne(String statment,Object parameter){
        SqlSession sqlSession=this.getSqlSession(componmentName, environment);
        //如果sqlSession为null,直接让抛出异常
        Object obj;
            try{
                obj=sqlSession.selectOne(statment,parameter);
            }catch (Throwable e){
                throw new IbatisException("selectOne error"+statment);
            }finally {
                if(sqlSession !=null){
                    sqlSession.close();
                }
            }
        return obj;
    }

    //mybatis两种分页技术 RowBounds,PageHelper
    public List selectList(String statment,Object parameter,RowBounds rowBounds){
          SqlSession sqlSession=this.getSqlSession(componmentName,environment);
          List list;
        try {
            list=sqlSession.selectList(statment,parameter,rowBounds);
        }catch (Throwable e){
            throw new IbatisException("selectList error"+statment);
        }finally {
            if(sqlSession !=null){
                sqlSession.close();
            }
        }
        return list;
    }

    public int insert(String statment,Object parameter){
        SqlSession sqlSession=this.getSqlSession(componmentName,environment);
        int num;
        try {
            num=sqlSession.insert(statment,parameter);
        }catch (Throwable e){
            throw new IbatisException("insert error"+statment);
        }finally {
            if(sqlSession !=null){
                sqlSession.close();
            }
        }
        return num;
    }

    public int update(String statment,Object parameter){
        SqlSession sqlSession=this.getSqlSession(componmentName,environment);
        int num;
        try {
            num=sqlSession.update(statment,parameter);
        }catch (Throwable e){
            throw new IbatisException("update error"+statment);
        }finally {
            if(sqlSession !=null){
                sqlSession.close();
            }
        }
        return num;
    }

    public int delete(String statment,Object parameter){
        SqlSession sqlSession=this.getSqlSession(componmentName,environment);
        int num;
        try {
            num=sqlSession.delete(statment, parameter);
        }catch (Throwable e){
            throw new IbatisException("delete error"+statment);
        }finally {
            if(sqlSession !=null){
                sqlSession.close();
            }
        }
        return num;
    }


    private SqlSession getSqlSession(String componeName,String environment){
        SqlSession sqlSession=null;
        if(!StringUtils.isEmpty(componeName) && !StringUtils.isEmpty(environment)){
            sqlSession=SqlSessionFactoryMap.getSqlSessionFactoryMap().getSqlSession(componeName,environment);
        }
        return sqlSession;
    }

}
