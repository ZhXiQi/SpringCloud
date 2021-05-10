package com.springcloud.client.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;



@Slf4j
@Component
public class PDFUtil {


    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static Font tableTitleFont;
    private static Font tableFont;
    private static Font tipFont;


    private static final float IMAGE_HEIGHT = 847.0F;
    private static final float IMAGE_WIDTH = 595.0F;

//    public static File createPDF(EvidenceDetailVO vo, String blockHeight) throws IOException, com.itextpdf.text.DocumentException {
//        return createPDF(new PdfVO(vo, blockHeight), true, false);
//    }
//
//    public static File createPDF(ForensicsDetailVO vo, String blockHeight) throws IOException, com.itextpdf.text.DocumentException {
//        return createPDF(new PdfVO(vo, blockHeight), false, false);
//    }

    public static File createPDF(PdfVO vo, boolean isEvidence) throws IOException, DocumentException {
        return createPDF(vo, isEvidence, false);
    }

    public static File createPDF(PdfVO vo, boolean isEvidence, boolean createQRCode) throws IOException, DocumentException {
        String s = "取证";
        if (isEvidence) {
            s = "存证";
        }
        // 设置 PDF 纸张矩形，大小采用 A4
        Rectangle rectangle = new Rectangle(PageSize.A4);
        //创建一个文档对象，设置初始化大小和页边距
        // 上、下、左、右页间距
        Document document = new Document(rectangle, 0, 0, 0, 0);

        // PDF 的输出位置
        String pdfPath = getPdfPath(vo.getId() + UUID.randomUUID().toString() + ".pdf");
        File pdf = new File(pdfPath);
        if (!pdf.exists()) {
            pdf.createNewFile();
        }

        PdfWriter.getInstance(document, new FileOutputStream(pdf.getPath()));
        // 打开文档对象
        document.open();

        //字体
        BaseFont baseFont = BaseFont.createFont(getFontPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        BaseFont baseFontTitle = BaseFont.createFont(getTitleFontPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        tableFont = new Font(baseFont, 12);
        tableTitleFont = new Font(baseFontTitle, 12);
        tipFont = new Font(baseFont, 10, Font.NORMAL, new BaseColor(19, 23, 46));

        // 设置背景
        String bgPath;
        if (isEvidence) {
            //存证
            bgPath = getPath("path");
        } else {
            //取证
            bgPath = getPath("path");
        }
        Image bg = Image.getInstance(bgPath);
        Image signImage = Image.getInstance(getSignImagePath());
        bg.scaleAbsolute(IMAGE_WIDTH, IMAGE_HEIGHT);
        // （以左下角为原点）设置图片的坐标
        bg.setAbsolutePosition(0, 0);

        //填充表格数据
        PdfPTable dataList = new PdfPTable(2);
        //居中
        dataList.setHorizontalAlignment(Element.ALIGN_CENTER);
        //宽度百分数
        dataList.setWidthPercentage(60);
        //每一列的宽度占比
        dataList.setWidths(new int[]{2, 4});
        //上面的空间
        dataList.setSpacingBefore(270);

        //表格有2列
        PdfPTable dataList2 = new PdfPTable(2);
        //居中
        dataList2.setHorizontalAlignment(Element.ALIGN_CENTER);
        //宽度百分数
        dataList2.setWidthPercentage(60);
        //每一列的宽度占比
        dataList2.setWidths(new int[]{2, 4});
        //上面的空间
        dataList2.setSpacingBefore(35);

//        Paragraph tips = new Paragraph("    ", tipFont);
//        Paragraph tips = new Paragraph("    ", tipFont);
        Paragraph tips = new Paragraph();
        tips.setAlignment(Element.ALIGN_LEFT);
        tips.setSpacingBefore(50);
        tips.setIndentationLeft(95);
        tips.setIndentationRight(95);

        if (createQRCode) {
            //添加二维码
            addImageWatermark(tips, vo.getQrCodePath(), Image.RIGHT, 415, 70, 80, 80);
        }
        //添加logo
        addImageWatermark(tips, ResourceUtils.CLASSPATH_URL_PREFIX + "Config.getPdfConfig().getPdfLogo()",
                Image.RIGHT, 380, 160, 130, 130);
        //添加内容
        Map<String, String> content = new LinkedHashMap<>();
        content.put("保管机构：", "Config.getConfirmcertAuth()");
        content.put("所属区块链：", vo.getChainName());
        addPdfTable(dataList, content);

        content.clear();
        content.put(s + "号：", vo.getId());
        content.put("所在区块号：", vo.getBlockHeight());
        content.put(s + "哈希：", vo.getHash());
        content.put("交易哈希：", vo.getTxHash());
        content.put("证据来源：", vo.getOrigin());
        content.put(s + "名称：", vo.getName());
        content.put(s + "类型：", vo.getType());
        content.put(s + "时间：", vo.getOpTime());
        content.put("上链时间：", vo.getChainTime());
        addPdfTable(dataList2, content);

        signImage.scaleAbsolute(IMAGE_WIDTH / 4.76f, IMAGE_HEIGHT / 6.78f);
        // （以左下角为原点）设置图片的坐标
        signImage.setAbsolutePosition(380, 100);


        //这里开始添加元素
        document.add(bg);
        document.add(new Paragraph("占位符"));
        document.add(dataList);
        document.add(dataList2);
//        PdfContentByte directContent = writer.getDirectContent();
//        directContent.addImage(signImage);

        document.add(tips);
        // 关闭文档
        document.close();
        return pdf;
    }

    public static File createPDFForIslide(Map<String, String> info, String outputName) throws IOException, com.itextpdf.text.DocumentException {
        // 设置 PDF 纸张矩形，大小采用 A4
        Rectangle rectangle = new Rectangle(PageSize.A4);
        //创建一个文档对象，设置初始化大小和页边距
        // 上、下、左、右页间距
        Document document = new Document(rectangle, ptSizeTranslator(212), ptSizeTranslator(212), ptSizeTranslator(225), ptSizeTranslator(225));

        // PDF 的输出位置
        String pdfPath = getPdfPath(outputName + UUID.randomUUID().toString() + ".pdf");
        File pdf = new File(pdfPath);
        if (!pdf.exists()) {
            pdf.createNewFile();
        }

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdf.getPath()));
        document.open();    // 打开文档对象
        document.addTitle("");// 标题
        document.addAuthor("");// 作者
        //字体
        BaseFont baseTitle = BaseFont.createFont(new ClassPathResource("font/SourceHanSerifCN-Bold.ttf").getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        BaseFont basetable = BaseFont.createFont(new ClassPathResource("font/SourceHanSansCN-Regular.ttf").getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        BaseFont basetableTitle = BaseFont.createFont(new ClassPathResource("font/SourceHanSansCN-Medium.ttf").getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        tableFont = new Font(basetable, ptSizeTranslator(60), Font.NORMAL, new BaseColor(144, 133, 121));
        tableTitleFont = new Font(basetableTitle, ptSizeTranslator(60));
        tipFont = new Font(basetable, ptSizeTranslator(40), Font.NORMAL, new BaseColor(144, 133, 121));
        Font titlefont = new Font(baseTitle, 40);

        // 取得图片对象
        Image image = Image.getInstance(getIslideImagePath());
//        // 添加印章
//        // 读图片
        Image signImage = Image.getInstance(getSignImagePathForIslide());

        image.scaleAbsolute(IMAGE_WIDTH, IMAGE_HEIGHT);
        // （以左下角为原点）设置图片的坐标
        image.setAbsolutePosition(0, 0);

        //设置标题
        Paragraph title = new Paragraph("证书", titlefont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(0);
        //表格有2列
        PdfPTable dataList2 = new PdfPTable(2);
        dataList2.setTotalWidth(new float[]{2.22f, 7.48f});
        //宽度百分数
        dataList2.setWidthPercentage(93.2f);
        //每一列的宽度占比
//        dataList2.setWidths(new float[]{2.5f, 7.5f});
        //上面的空间
        dataList2.setSpacingBefore(ptSizeTranslator(120));

        //添加内容
        for (Map.Entry<String, String> entry : info.entrySet()) {
//            PdfPCell keyCell = getTableParagraphForIslide(entry.getKey() + "：", tableTitleFont);
            Paragraph keyParagraph = new Paragraph(ptSizeTranslator(80), entry.getKey(), tableTitleFont);
            PdfPCell keyCell = new PdfPCell();
            keyCell.addElement(keyParagraph);
            keyCell.setBorder(Rectangle.NO_BORDER);
            keyCell.setPaddingBottom(ptSizeTranslator(50));
            dataList2.addCell(keyCell);
            Paragraph paragraph = new Paragraph(ptSizeTranslator(80), entry.getValue(), tableFont);
            PdfPCell valueCell = new PdfPCell();
            valueCell.addElement(paragraph);
            valueCell.setBorder(Rectangle.NO_BORDER);
            valueCell.setPaddingBottom(ptSizeTranslator(50));
            dataList2.addCell(valueCell);
        }
        Paragraph keyParagraph = new Paragraph(ptSizeTranslator(80), "证书说明:", tableTitleFont);
        PdfPCell keyCell = new PdfPCell();
        keyCell.addElement(keyParagraph);
        keyCell.setBorder(Rectangle.NO_BORDER);
        keyCell.setPaddingBottom(ptSizeTranslator(50));
        dataList2.addCell(keyCell);
        String tipStr = "";
        Paragraph elements = new Paragraph(ptSizeTranslator(60), tipStr, tipFont);
        PdfPCell tipCell = new PdfPCell();
        tipCell.addElement(elements);
        tipCell.setBorder(Rectangle.NO_BORDER);
        tipCell.setPaddingTop(ptSizeTranslator(30));
//        tipCell.setPaddingBottom(ptSizeTranslator(60));
        dataList2.addCell(tipCell);


        signImage.scaleAbsolute(IMAGE_WIDTH / 4.76f, IMAGE_HEIGHT / 6.78f);
        // （以左下角为原点）设置图片的坐标
        signImage.setAbsolutePosition(380, 100);

        //这里开始添加元素
        document.add(image);
        document.add(new Paragraph("占位符"));
        document.add(title);
        document.add(dataList2);
        PdfContentByte directContent = writer.getDirectContent();
        directContent.addImage(signImage);
        // 关闭文档
        document.close();
        return pdf;
    }

    /**
     * 添加PDF 表格数据
     *
     * @param pdfPTable PDF表格对象
     * @param content   内容
     */
    private static void addPdfTable(PdfPTable pdfPTable, Map<String, String> content) {
        Set<String> keySet = content.keySet();
        keySet.forEach(key -> {
            pdfPTable.addCell(getTableParagraph(key, true));
            pdfPTable.addCell(getTableParagraph(content.get(key), false));
        });
    }

    /**
     * 添加二维码、印章等图片水印
     *
     * @param paragraph     水印所属段落
     * @param watermarkPath 图片水印路径
     * @param alignment
     * @param absoluteX
     * @param absoluteY
     * @param fitWidth
     * @param fitHeight
     * @throws IOException
     * @throws BadElementException
     */
    private static void addImageWatermark(Paragraph paragraph, String watermarkPath,
                                          int alignment, float absoluteX, float absoluteY,
                                          float fitWidth, float fitHeight
    ) throws IOException, BadElementException {
        //二维码
        Image instance = Image.getInstance(watermarkPath);
        instance.setAlignment(alignment);
        instance.setAbsolutePosition(absoluteX, absoluteY);
        instance.scaleToFit(fitWidth, fitHeight);
        paragraph.add(instance);
    }

    /**
     * 生成PDFvo
     *
     * @param blockChainId  区块链id
     * @param forensicsHash 存/取证hash
     * @param txHash        交易hash
     * @param origin        来源
     * @param chainName     链名称
     * @param forensicsName 存/取证名称
     * @param opTime        操作时间
     * @param chainTime     上链时间
     * @param type          存/取证类型
     * @return
     * @throws Exception
     */
    /*public static PdfVO getPdfVo(String blockChainId, String forensicsHash, String txHash, String origin,
                                 String chainName, String forensicsName, String opTime, String chainTime, String type) throws Exception {
        XXX XXX = new xxxAPI();
        SingleValueReturn txByHash = xxxAPI.getTxByHash(txHash);
        TransInfoVO transInfoVO = JSON.parseObject(txByHash.getResult(), TransInfoVO.class);
        PdfVO pdfVO = PdfVO.builder()
                .id(blockChainId)
                .blockHeight(String.valueOf(Integer.parseInt(transInfoVO.getBlockNumber().substring(2), 16)))
                .hash(forensicsHash)
                .txHash(txHash)
                .origin(origin)
                .chainName(chainName)
                .name(forensicsName)
                .opTime(opTime)
                .chainTime(chainTime)
                .type(type)
                .build();
        return pdfVO;
    }*/

    /*public static File cert(PdfVO pdfVO, boolean createQRCode) throws Exception {
        xxxAPI xxxAPI = new xxxAPI();
        SingleValueReturn singleValueReturn = xxxAPI.getTxByHash(pdfVO.getTxHash());
        if (!singleValueReturn.isSuccess()) {
            throw new BlockException("获取交易hash失败");
        }
        TransInfoVO transInfoVO = JSON.parseObject(singleValueReturn.getResult(), TransInfoVO.class);
        pdfVO.setBlockHeight(transInfoVO.getBlockNumber());
        if (pdfVO.getChainTime() == null) {
            pdfVO.setChainTime(Long.parseLong(transInfoVO.getTimestamp()) / 1000000);
        }
        //这里生成证书
        File cert = PDFUtil.createPDF(pdfVO, false, createQRCode);
        return cert;
    }*/

    /**
     * 快速创建想要的文字
     *
     * @param text
     * @return
     */
    private static PdfPCell getTableParagraph(String text, boolean isTitle) {
        Paragraph paragraph;
        if (isTitle) {
            paragraph = new Paragraph(text, tableTitleFont);
        } else {
            paragraph = new Paragraph(text, tableFont);
        }
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setPaddingTop(13);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    /**
     * 快速创建想要的文字
     *
     * @param text
     * @return
     */
    private static PdfPCell getTableParagraph(String text, Font font) {
        Paragraph paragraph = new Paragraph(text, font);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setPaddingTop(12);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }


    private static String getFontPath() throws FileNotFoundException {
        String path = ResourceUtils.CLASSPATH_URL_PREFIX + "Config.getPdfConfig().getFontPath()";
        return ResourceUtils.getFile(path).getPath();
    }

    private static String getTitleFontPath() throws FileNotFoundException {
        String path = ResourceUtils.CLASSPATH_URL_PREFIX + "Config.getPdfConfig().getTitleFontPath()";
        return ResourceUtils.getFile(path).getPath();
    }

    private static String getIslideImagePath() throws FileNotFoundException {
        return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "pdf_source/islide/img/islide.jpg").getPath();
    }

    private static String getSignImagePath() throws FileNotFoundException {
        return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "pdf_source/common/img/sign.png").getPath();
    }

    private static String getSignImagePathForIslide() throws FileNotFoundException {
        return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "pdf_source/islide/img/sign.png").getPath();
    }

    private static String getPath(String path) throws FileNotFoundException {
        return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + path).getPath();
    }

    private static String getPdfPath(String pdfName) {
        //把PDF放在/tmp文件夹下
        String tmpdir = System.getProperty("java.io.tmpdir");
        log.info("输出PDF到路径：" + tmpdir + "/" + pdfName);
        return new File(tmpdir, pdfName).getAbsolutePath();
    }

    private static float ptSizeTranslator(float designPx) {
        float designDpi = 256.0f;
        float dpi = 72.0f;
        float px = designPx / designDpi * dpi;
        // IText使用的单位是pt而不是px，一帮情况下要想保持原来px的大小需要将px*3/4
        return px;
    }

    private static String getEviImagePath() {
        return ResourceUtils.CLASSPATH_URL_PREFIX + "Config.getPdfConfig().getEviImagePath()";
    }

    private static String getForImagePath() {
        return ResourceUtils.CLASSPATH_URL_PREFIX + "Config.getPdfConfig().getForImagePath()";
    }
}
