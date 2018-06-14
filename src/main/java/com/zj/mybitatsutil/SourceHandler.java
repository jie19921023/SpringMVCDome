package com.zj.mybitatsutil;

import com.zj.enumutil.Source;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhaojie on 18-4-20.
 * Source枚举类型和jdbc类型的转换器
 */
public class SourceHandler extends BaseTypeHandler {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        if(o !=null && o.getClass() == Source.class){
            preparedStatement.setString(i,String.valueOf(((Source) o).getCode()));
        }

    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
           String result=resultSet.getString(s);
           return Source.parse(Integer.parseInt(result));
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String result=resultSet.getString(i);
        return Source.parse(Integer.parseInt(result));
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String result=callableStatement.getString(i);
        return Source.parse(Integer.parseInt(result));
    }
}
