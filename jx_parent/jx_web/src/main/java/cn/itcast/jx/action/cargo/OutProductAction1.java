package cn.itcast.jx.action.cargo;/**
 * Created by leastmoney on 2017/7/12.
 */

/**
 * 未来的程序员 Mr.钱
 */

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.domain.ContractProduct;
import cn.itcast.jx.service.ContractProductService;
import cn.itcast.jx.util.DownloadUtil;
import cn.itcast.jx.util.UtilFuns;
import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.struts2.ServletActionContext;

import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 * 未来的程序员 Mr.钱
 */

class OutProductAction1 extends BaseAction {
    private String inputDate;

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }
    private ContractProductService contractProductService;

    public void setContractProductService(ContractProductService contractProductService) {
        this.contractProductService = contractProductService;
    }

    public String print() throws Exception {
//        创建文档对象
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
//        输出位子
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document,byteArrayOutputStream);
//        打开文档
        document.open();
//        输入内容
//        设置中文字体
        BaseFont baseFont = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, AsianFontMapper.ChineseSimplifiedEncoding_H, BaseFont.NOT_EMBEDDED);
        /*设置打标题*/
        Font titleFont = new Font(baseFont, 20f, Font.BOLD, BaseColor.LIGHT_GRAY);
        Paragraph titleParagraph  = new Paragraph("出货表", titleFont);
        titleParagraph .setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titleParagraph );
        /*设置表格*/
        PdfPTable table = new PdfPTable(8);
        table.setSpacingBefore(20f);
        Font tableFont = new Font(baseFont, 12f, Font.NORMAL, BaseColor.BLACK);
        String[] titles={"客户","订单号","货号","数量","工厂","交期","船期","贸易条款"};
        for (String title : titles) {
            table.addCell(new PdfPCell(new Phrase(title,tableFont)));
        }
        //		正式数据
        String hql = "from ContractProduct where to_char(contract.shipTime,'YYYY-MM')='"+inputDate+"'";

        List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, null);

//		准备字体
        Font contentFont = new Font(baseFont,12f,Font.NORMAL,BaseColor.BLACK);
        for (ContractProduct cp : list) {
            table.addCell(new PdfPCell(new Phrase(cp.getContract().getCustomName(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(cp.getContract().getContractNo(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(cp.getProductNo(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(cp.getCnumber()+"", contentFont)));
            table.addCell(new PdfPCell(new Phrase(cp.getFactoryName(), contentFont)));
            table.addCell(new PdfPCell(new Phrase(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()), contentFont)));
            table.addCell(new PdfPCell(new Phrase(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()), contentFont)));
            table.addCell(new PdfPCell(new Phrase(cp.getContract().getTradeTerms(), contentFont)));
        }
        document.add(table);

//        关闭文档,
        document.close();
//        下载
        DownloadUtil downloadUtil = new DownloadUtil();
        downloadUtil.download(byteArrayOutputStream, ServletActionContext.getResponse(),"出货表.pdf");

        return NONE;
    }
}

