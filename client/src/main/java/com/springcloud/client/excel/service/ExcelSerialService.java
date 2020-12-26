package com.springcloud.client.excel.service;

import com.springcloud.client.excel.ExcelLogs;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/***
 *
 * @author
 * @date 2018/9/12
 */
public interface ExcelSerialService {

    /**
     * 导入excel
     *
     * @param clazz 导入类型
     * @param file  文件
     * @param logs  错误日志
     * @param <T>   范型
     * @return 对应类型的collection
     */
    <T> Collection<T> importExcel(Class<T> clazz, MultipartFile file, ExcelLogs logs);

    /**
     * 验证数据是否符合格式
     * @param clazz 数据类型
     * @param excels 数据集合
     * @param logs 错误日志
     * @param <T> 范型
     * @return 错误的数据集合
     */
    <T> void verifyExcelData(Class<T> clazz, Collection<T> excels, ExcelLogs logs);
}
