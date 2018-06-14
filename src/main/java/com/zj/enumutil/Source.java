package com.zj.enumutil;

/**
 * Created by 赵杰 on 18-4-20.
 * IP地址来源
 */
public enum Source {
    PC(1),
    APP(2),
    WX(3);

    private int code;

    private Source(int code){
        this.code=code;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static Source parse(int code){
        for(Source i:Source.values()){
            if(i.getCode() == code){
                return i;
            }
        }
        return null;
    }
}
