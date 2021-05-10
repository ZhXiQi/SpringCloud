package com.springcloud.client.excel.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/***
 *
 * @author
 * @date 2018/9/6
 */
public interface ExcelService {

    /**
     * 下载模板
     * @param response 此次请求的response
     * @param templateType 模板类型 0:机构；1:设备
     * @throws IOException 流异常
     */
    void downloadTemplate(HttpServletResponse response, int templateType) throws IOException;

    /**
     * 导入excel
     * @param file 上传的文件
     * @param templateType 类型 0:机构；1:设备
     * @return 返回导入日志
     */
    Map<String,Object> importExcel(MultipartFile file, int templateType) throws Exception;

    /**
     * 导出excel
     * @param clazz 类型
     * @param list 数据列表
     * @param <T> 范型
     */
    <T> XSSFWorkbook exportExcel(Class<T> clazz, Collection<T> list);
}
