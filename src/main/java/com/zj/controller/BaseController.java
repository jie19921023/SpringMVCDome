package com.zj.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhaojie on 18-4-18.
 */
public abstract class BaseController {
    //接收前端数据进行类型转换
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
        binder.registerCustomEditor(int.class,new IntegerEditer());
        binder.registerCustomEditor(float.class,new FloatEditer());
        binder.registerCustomEditor(double.class,new DoubleEditer());
        binder.registerCustomEditor(long.class,new LongEditer());
        binder.registerCustomEditor(boolean.class,new BooleanEditer());
    }

    private class IntegerEditer extends PropertiesEditor{

        @Override
        public String getAsText() {
            return getValue().toString();
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            int i=0;
            if(text != null && !"".equals(text)){
                i=Integer.parseInt(text);
            }
            setValue(i);
        }
    }

    private class FloatEditer extends PropertiesEditor{

        @Override
        public String getAsText() {
            return getValue().toString();
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            float i=0;
            if( text != null && !"".equals(text)){
                i=Float.parseFloat(text);
            }
            setValue(i);
        }
    }

    private class DoubleEditer extends PropertiesEditor{
        @Override
        public String getAsText() {
            return getValue().toString();
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            double i=0;
            if(text !=null && !"".equals(text)){
                i=Double.parseDouble(text);
            }
            setValue(i);
        }
    }

    private class LongEditer extends PropertiesEditor{
        @Override
        public String getAsText() {
            return getValue().toString();
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            long i=0;
            if(text !=null && !"".equals(text)){
                i=Long.parseLong(text);
            }
            setValue(i);
        }
    }

    private class BooleanEditer extends  PropertiesEditor{
        @Override
        public String getAsText() {
            return getValue().toString();
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            boolean i=false;
            if("true".equals(text) || "1".equals(text) || "TRUE".equals(text)){
                i=true;
            }
            setValue(i);
        }
    }
}
