package com.zj.util;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by jie.zhao on 18-4-12.
 */
public class StringHttpMessageConverter extends org.springframework.http.converter.StringHttpMessageConverter {

    private static final Charset DEAUFELT_CHARSET=Charset.forName("utf-8");
    private Charset charset;
    private boolean writeAcceptCharset;//是否返回AcceptCharset数据

    public StringHttpMessageConverter(){
        super(DEAUFELT_CHARSET);
        this.charset=DEAUFELT_CHARSET;
        this.writeAcceptCharset=true;//默认返回
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        this.writeAcceptCharset = writeAcceptCharset;
    }

    protected void writeInternal(String str, HttpOutputMessage outputMessage) throws IOException {
        if(this.writeAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(this.getAcceptedCharsets());
        }
        MediaType contentType=outputMessage.getHeaders().getContentType();
        Charset charset1;
        if(null == contentType.getCharSet()){
            charset1 = null == this.charset ? DEAUFELT_CHARSET : this.charset;
        }else{
            charset1=contentType.getCharSet();
        }
        StreamUtils.copy(str, charset1, outputMessage.getBody());
    }
}
