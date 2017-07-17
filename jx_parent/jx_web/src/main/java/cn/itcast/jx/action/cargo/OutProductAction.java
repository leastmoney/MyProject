package cn.itcast.jx.action.cargo;

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.ContractProduct;
import cn.itcast.jx.service.ContractProductService;
import cn.itcast.jx.util.DownloadUtil;
import cn.itcast.jx.util.UtilFuns;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by leastmoney on 2017/7/7.
 */
public class OutProductAction extends BaseAction{
    private String inputDate;

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }
    private ContractProductService contractProductService;

    public void setContractProductService(ContractProductService contractProductService) {
        this.contractProductService = contractProductService;
    }

    public String toedit(){
        return "toedit";
    }

   /* public String print() throws Exception {
//  先创建工作簿
        Workbook wb = new HSSFWorkbook();
//        创建sheet
        Sheet sheet = wb.createSheet();
//        定义公共变量
        Row nRow = null;//行对象
        Cell nCell = null;//列对象

        int rowNo = 0;//第几行
        int cellNo = 1;//第一列
//        设置列宽 第一个参数：第n列，第二个参数：列宽
        sheet.setColumnWidth(0, 6*256);
        sheet.setColumnWidth(1, 26*256);
        sheet.setColumnWidth(2, 12*256);
        sheet.setColumnWidth(3, 30*256);
        sheet.setColumnWidth(4, 12*256);
        sheet.setColumnWidth(5, 15*256);
        sheet.setColumnWidth(6, 10*256);
        sheet.setColumnWidth(7, 10*256);
        sheet.setColumnWidth(8, 10*256);
*//*******************设置大标题********************//*
//3.创建行对象
        nRow = sheet.createRow(rowNo);//创建行对象
//        创建单元格对象
        nCell = nRow.createCell(cellNo);
//行高+合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));//横向合并单元格
//        设置行高
        nRow.setHeightInPoints(36f);
//        设置数据
        nCell.setCellValue(inputDate.replace("-0","-").replace("-","年")+"月份出货表");
//        设置样式
        nCell.setCellStyle(bigTitle(wb));
        *//*******************设置小标题********************//*
//3.创建行对象
        rowNo++;
        nRow = sheet.createRow(rowNo);
//        设置行高
        nRow.setHeightInPoints(26.25f);
//          定义一个数组.方便加入数据
String titles[] = {"客户","订单号","货号","数量	","工厂","工厂交期","船期","贸易条款"};
//        创建单元格对象
        nCell = nRow.createCell(cellNo);
//        循环遍历,把数据放进单元格中
        for (String title : titles){
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(title);
            nCell.setCellStyle(title(wb));
        }
*//*******************设置出货数据********************//*
//准备数据

//    查询所有符合当前月份的日期
        String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm') = '"+inputDate+"'";
        List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, null);
//        循环遍历,设置值到表格中
        for (ContractProduct cp:list ) {
//          创建行对象
            rowNo++;
//          把列归1gy
            cellNo = 1;
//一行数据创建一次
            nRow = sheet.createRow(rowNo);//创建行对象
//        创建单元格对象
//            "客户","订单号","货号","数量	","工厂","工厂交期","船期","贸易条款"
//            在一行中有几列的数据
            //客户
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContract().getCustomName());
            nCell.setCellStyle(text(wb));

            //订单号
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContract().getContractNo());
            nCell.setCellStyle(text(wb));
            //货号
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getProductNo());
            nCell.setCellStyle(text(wb));
            //数量
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getCnumber());
            nCell.setCellStyle(text(wb));
            //工厂
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getFactoryName());
            nCell.setCellStyle(text(wb));
            //工厂交期
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
            nCell.setCellStyle(text(wb));
            //船期
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
            nCell.setCellStyle(text(wb));
            //贸易条款
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContract().getTradeTerms());
            nCell.setCellStyle(text(wb));
        }

*//*******************下载数据********************//*
//        写入流
        ByteArrayOutputStream bo = new ByteArrayOutputStream();//字节数组输出流

        wb.write(bo);

        HttpServletResponse response = ServletActionContext.getResponse();

        DownloadUtil downloadUtil = new DownloadUtil();
        *//**
         * 第一个参数：流
         * 第二个参数：response
         * 第三个参数：下载的文件名
         *//*

        downloadUtil.download(bo,response,"a.xls");
        return NONE;
    }*/
    /**
     * 使用模板打印的方法：
     * 问题一：需不需要返回值？
     * 答：不需要，直接下载
     * @return
     * @throws Exception
     */
  /*  public String print() throws Exception {
//读取模板，路径
        String path = ServletActionContext.getRequest().getRealPath("/");
        System.out.println(path);
        path += "/make/xlsprint/tOUTPRODUCT.xls";//获取模板在服务器的路径
        FileInputStream is = new FileInputStream(new File(path));

//       1 借助模板创建工作簿
        Workbook wb = new HSSFWorkbook(is);
//        获取工作表
        Sheet sheet = wb.getSheetAt(0);
//定 义公共变量
        Row nRow = null;//行对象
        Cell nCell = null;//单元格对象

        int rowNo = 0;//第几行
        int cellNo = 1;//列对象
*//*******************设置大标题********************//*
//获取行对象
         nRow = sheet.getRow(rowNo);
//        获取单元格对象
        nCell = nRow.getCell(cellNo);
        // 5 设置数据
        *//**           inputDate.replace("-", "年")
         * 2015-02     2015年02月份出货表
         * 2015-11     2015年11月份出货表
         *
         * 2015-02    2015-2  2015年2
         * 2015-11    2015-11 2015年11
         *//*
        nCell.setCellValue(inputDate.replace("-0","-").replace("-","年")+"月份出货表");

*//*******************设置小标题********************//*
        rowNo++;//跳过小标题行
*//*******************设置出货数据********************//*
        rowNo++;//进入数据第一行行
        //获取样式
        nRow = sheet.getRow(rowNo);

        //客户
        CellStyle customerCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //订单号
        CellStyle contractNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //货号
        CellStyle productNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //数量
        CellStyle cnumberCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //工厂
        CellStyle factoryCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //工厂交期
        CellStyle deliveryPeriodCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //船期
        CellStyle shipTimeCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //贸易条款
        CellStyle tradeTermsCellStyle = nRow.getCell(cellNo++).getCellStyle();

        // 准备数据
        //String hql = "from ContractProduct where contract.shipTime like '%"+inputDate+"%'";//mysql支持，oracle不支持
        //to_char可以将Date转成varchar,oracle中的所有的PL/SQL函数都可以直接写在hql语句中
        String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm')='"+inputDate+"'";
        //货物的集合
        List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, null);
        for(ContractProduct cp:list){

            //单元格no归1
            cellNo = 1;

            //一条数据创建一行
            nRow = sheet.createRow(rowNo);
            //在创建每列的数据
            //客户
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContract().getCustomName());
            nCell.setCellStyle(customerCellStyle);

            //订单号
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContract().getContractNo());
            nCell.setCellStyle(contractNoCellStyle);
            //货号
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getProductNo());
            nCell.setCellStyle(productNoCellStyle);
            //数量
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getCnumber());
            nCell.setCellStyle(cnumberCellStyle);
            //工厂
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getFactoryName());
            nCell.setCellStyle(factoryCellStyle);
            //工厂交期
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
            nCell.setCellStyle(deliveryPeriodCellStyle);

            //船期
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
            nCell.setCellStyle(shipTimeCellStyle);
            //贸易条款
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContract().getTradeTerms());
            nCell.setCellStyle(tradeTermsCellStyle);


//			开始操作数据，rowNo++
            rowNo++;
        }





*//***************************************************//*
        // 7 写入流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//字节数组缓冲流
        wb.write(byteArrayOutputStream);

        // 8下  载
        DownloadUtil downloadUtil = new DownloadUtil();
        HttpServletResponse response = ServletActionContext.getResponse();
        *//**
         * 第一个参数：流
         * 第二个参数：response
         * 第三个参数：下载的文件名
         *//*
        downloadUtil.download(byteArrayOutputStream, response, "出货表.xls");

        return NONE;
    }*/

//使用指定和模板和xlsx打印
    public String print() throws Exception {
        String path = ServletActionContext.getRequest().getRealPath("/");
        System.out.println(path);
        path += "/make/xlsprint/tOUTPRODUCT.xlsx";//获取模板在服务器的路径
        FileInputStream is = new FileInputStream(new File(path));
//        创建新的工作簿
        Workbook wb = new XSSFWorkbook(is);
//        获取sheet
        Sheet sheet = wb.getSheetAt(0);
        //定 义公共变量
        Row nRow = null;//行对象
        Cell nCell = null;//单元格对象

        int rowNo = 0;//第几行
        int cellNo = 1;//列对象
        /*******************设置标题数据********************/
//        获得行对象
        nRow = sheet.getRow(rowNo);
//        获得列对象
        nCell = nRow.getCell(cellNo);
//        设置数据
        nCell.setCellValue(inputDate.replace("-0","-").replace("-","年")+"月份报表");
        /*******************设置小标题数据********************/
        rowNo++;
        /*******************设置出货数据********************/
        rowNo++;
//        获得行对象
            nRow = sheet.getRow(rowNo);
//        获得列所对应对象的样式
        //客户
        CellStyle customerCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //订单号
        CellStyle contractNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //货号
        CellStyle productNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //数量
        CellStyle cnumberCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //工厂
        CellStyle factoryCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //工厂交期
        CellStyle deliveryPeriodCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //船期
        CellStyle shipTimeCellStyle = nRow.getCell(cellNo++).getCellStyle();
        //贸易条款
        CellStyle tradeTermsCellStyle = nRow.getCell(cellNo++).getCellStyle();

//        准备数据
        // 准备数据
        //to_char可以将Date转成varchar,oracle中的所有的PL/SQL函数都可以直接写在hql语句中
        String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm')='"+inputDate+"'";
        //货物的集合
        List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, null);
        for (ContractProduct cp:list) {
//        cellNo归1
            cellNo = 1;
            nRow = sheet.createRow(rowNo);
            //在创建每列的数据
            //客户
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContract().getCustomName());
            nCell.setCellStyle(customerCellStyle);

            //订单号
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContract().getContractNo());
            nCell.setCellStyle(contractNoCellStyle);
            //货号
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getProductNo());
            nCell.setCellStyle(productNoCellStyle);
            //数量
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getCnumber());
            nCell.setCellStyle(cnumberCellStyle);
            //工厂
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getFactoryName());
            nCell.setCellStyle(factoryCellStyle);
            //工厂交期
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
            nCell.setCellStyle(deliveryPeriodCellStyle);

            //船期
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
            nCell.setCellStyle(shipTimeCellStyle);
            //贸易条款
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(cp.getContract().getTradeTerms());
            nCell.setCellStyle(tradeTermsCellStyle);


//			开始操作数据，rowNo++
            rowNo++;
        }
        /***************************************************/
        // 7 写入流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//字节数组缓冲流
        wb.write(byteArrayOutputStream);

        // 8下  载
        DownloadUtil downloadUtil = new DownloadUtil();
        HttpServletResponse response = ServletActionContext.getResponse();
        /**
         * 第一个参数：流
         * 第二个参数：response
         * 第三个参数：下载的文件名
         */
        downloadUtil.download(byteArrayOutputStream, response, "出货表.xlsx");

        return NONE;
    }











    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);					//字体加粗

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中

        return style;
    }
    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中

        style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
        style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
        style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
        style.setBorderRight(CellStyle.BORDER_THIN);				//右细线

        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_LEFT);					//横向居左
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中

        style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
        style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
        style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
        style.setBorderRight(CellStyle.BORDER_THIN);				//右细线

        return style;
    }

}
