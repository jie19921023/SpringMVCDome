package com.zj.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jie.zhao on 18-4-18.
 * 导出简历附件大项中上传的个人简历需要用的sql
 * SELECT a.RESUME_CODE,d.`NAME`,c.SAVE_NAME FROM CR_RESUME a,CR_R_ACCESSORY b,RM_AFFIX c,CR_R_PERSONAL_INFO d WHERE
   a.ID=b.CR_RESUME_ID AND b.ID=c.RECORD_ID AND a.ID=d.CR_RESUME_ID
   AND a.CORP_ID=9104102800000000684
   AND a.RESUME_STATUS='1'
   AND a.SUMBIT_TIME>'2018-03-09  00:00:00'
   AND b.RESUME_FILE='1'
   AND c.BS_KEYWORD='PERSON_RESUMEFILEITEM' ORDER BY SUMBIT_TIME ASC;
 */
public class DownResume {
    //如果简历数量大可以开启多线程
    private static ExecutorService executorService=new ThreadPoolExecutor(10,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    public static String getContent(File f) throws Exception {
        //构建Workbook对象, 只读Workbook对象
        //直接从本地文件创建Workbook
        //从输入流创建Workbook
        FileInputStream fis = new FileInputStream(f);
        StringBuilder sb = new StringBuilder();
        jxl.Workbook rwb = Workbook.getWorkbook(fis);
//一旦创建了Workbook，我们就可以通过它来访问
//Excel Sheet的数组集合(术语：工作表)，
//也可以调用getsheet方法获取指定的工作表
        int count = 0;
        Sheet[] sheet = rwb.getSheets();
        for (int i = 0; i < sheet.length; i++) {
            Sheet rs = rwb.getSheet(i);
            for (int j = 1; j < rs.getRows(); j++) {
                final Cell[] cells = rs.getRow(j);
//               executorService.execute(new Runnable() {
//               @Override
//               public void run() {
                   String fileUrl =cells[2].getContents();
                   String geshi= fileUrl.substring(fileUrl.charAt(fileUrl.lastIndexOf("."))+5,fileUrl.length());
                   String fileName = cells[0].getContents()+"-"+cells[1].getContents();
                   System.out.println("fileName: "+fileName+geshi +"fileUrl: "+fileUrl);
                   //downloadFile(fileUrl,"/home/dajie/resumefile/"+fileName+geshi);
              // }
          // });
            }
        }
        fis.close();
        return sb.toString();
    }

    public static void main(String[] args) {
        //通过sql导出excel文件
        File f = new File("/home/dajie/jiaguwenxin.xls");
        try {
            getContent(f);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    public static void downloadFile(String remoteFilePath, String localFilePath)
    {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try
        {
            if(!f.exists()){
                f.createNewFile();
            }
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection)urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        }
        catch (Exception e)
        {
            System.out.println("error---> remoteFilePath: "+remoteFilePath+" ,localFilePath: "+localFilePath);
            e.printStackTrace();
        }
    }

    /**
     * 给excel添加图片
     * @param filePath
     */
    private  void loadPicture(String filePath){
        try {
            File file =new File(filePath);
            Workbook wb = Workbook.getWorkbook(new FileInputStream(file));
            //log.info("loadPicture--+++"+wb);
            //创建工作副本写回源文件
            WritableWorkbook book = Workbook.createWorkbook(new File(filePath), wb);
            //log.info("loadPicture--+++");
            WritableSheet sheet = book.getSheet("Sheet1");
            //log.info("loadPicture--+++");
            if(sheet !=null){
                Cell cell=sheet.getRow(2)[6];
                if(cell !=null){
                    String imagePath=cell.getContents();
                    //log.info("loadPicture--"+imagePath);
                    File imgFile = new File(imagePath);
                    //col row是图片的起始行起始列  width height是定义图片跨越的行数与列数
                    WritableImage image = new WritableImage(2,6,5,2,imgFile);
                    sheet.addImage(image);
                    book.write();
                    book.close();
                }
            }

        } catch (Exception e) {
            //log.info("-EXCEPTION--------"+e.getMessage());
            e.printStackTrace();
        }

    }


}
