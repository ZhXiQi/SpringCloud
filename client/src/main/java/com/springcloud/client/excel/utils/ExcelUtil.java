package com.springcloud.client.excel.utils;

import com.springcloud.client.excel.ExcelCell;
import com.springcloud.client.excel.ExcelLog;
import com.springcloud.client.excel.ExcelLogs;
import com.springcloud.client.excel.ExcelSheet;
import com.springcloud.client.excel.FieldForSortting;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The <code>ExcelUtil</code> 与 {@link ExcelCell}搭配使用
 *
 * @author
 * @date
 */
public class ExcelUtil {

    private static Logger LG = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 用来验证excel与Vo中的类型是否一致 <br>
     * Map<栏位类型,只能是哪些Cell类型>
     */
    private static Map<Class<?>, CellType[]> validateMap = new HashMap<>();

    static {
        validateMap.put(String[].class, new CellType[]{CellType.STRING});
        validateMap.put(Double[].class, new CellType[]{CellType.NUMERIC});
        validateMap.put(String.class, new CellType[]{CellType.STRING});
        validateMap.put(Double.class, new CellType[]{CellType.NUMERIC});
        validateMap.put(Date.class, new CellType[]{CellType.NUMERIC, CellType.STRING});
        validateMap.put(Integer.class, new CellType[]{CellType.NUMERIC});
        validateMap.put(Float.class, new CellType[]{CellType.NUMERIC});
        validateMap.put(Long.class, new CellType[]{CellType.NUMERIC});
        validateMap.put(Boolean.class, new CellType[]{CellType.BOOLEAN});
    }

    /**
     * 获取cell类型的文字描述
     *
     * @param cellType <pre>
     * CellType.BLANK
     * CellType.BOOLEAN
     * CellType.ERROR
     * CellType.FORMULA
     * CellType.NUMERIC
     * CellType.STRING
     * </pre>
     * @return null
     */
    private static String getCellTypeByInt(CellType cellType) {
        if (cellType == CellType.BLANK) {
            return "空类型";
        } else if (cellType == CellType.BOOLEAN) {
            return "Boolean类型";
        } else if (cellType == CellType.ERROR) {
            return "Error类型";
        } else if (cellType == CellType.FORMULA) {
            return "式类型";
        } else if (cellType == CellType.NUMERIC) {
            return "数值类型";
        } else if (cellType == CellType.STRING) {
            return "文本类型";
        } else {
            return "未知类型";
        }
    }

    /**
     * 获取单元格值
     *
     * @param cell 单元格
     * @return Object
     */
    private static Object getCellValue(Cell cell) {
        boolean existed = cell == null
                || (cell.getCellTypeEnum() == CellType.STRING
                && StringUtils.isBlank(cell.getStringCellValue()));
        if (existed) {
            return null;
        }
        CellType cellType = cell.getCellTypeEnum();
        if (cellType == CellType.BLANK) {
            return null;
        } else if (cellType == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cellType == CellType.ERROR) {
            return cell.getErrorCellValue();
        } else if (cellType == CellType.FORMULA) {
            try {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            } catch (IllegalStateException e) {
                return cell.getRichStringCellValue();
            }
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getNumericCellValue();
            }
        } else if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        } else {
            return null;
        }
    }

    /**
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于单个sheet
     *
     * @param <T>     范型
     * @param headers 表格属性列名数组
     * @param dataSet 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,String[],Double[]
     */
    public static <T> XSSFWorkbook exportExcel(Map<String, String> headers, Collection<T> dataSet) {
        return exportExcel(headers, dataSet, null);
    }

    /**
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于单个sheet
     *
     * @param <T>     范型
     * @param headers 表格属性列名数组
     * @param dataSet 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,String[],Double[]
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public static <T> XSSFWorkbook exportExcel(Map<String, String> headers, Collection<T> dataSet,
                                               String pattern) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet("导出数据");

        write2Sheet(sheet, headers, dataSet, pattern);
        return workbook;

    }

    /**
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于单个sheet
     *
     * @param <T>     范型
     * @param headers 表格属性列名数组
     * @param dataSet 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,String[],Double[]
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public static <T> XSSFWorkbook exportExcel(Map<String, String> headers, Collection<T> dataSet,
                                               String pattern, int[] ids) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet("导出数据");

        write2Sheet(sheet, headers, dataSet, pattern, ids);
        return workbook;

    }

    public static XSSFWorkbook exportExcel(String[][] dataList) {

        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();

        for (int i = 0; i < dataList.length; i++) {
            String[] r = dataList[i];
            XSSFRow row = sheet.createRow(i);
            for (int j = 0; j < r.length; j++) {
                XSSFCell cell = row.createCell(j);
                //cell max length 32767
                if (r[j].length() > 32767) {
                    String s = "--此字段过长(超过32767),已被截断--" + r[j];
                    r[j] = s;
                    r[j] = r[j].substring(0, 32766);
                }
                cell.setCellValue(r[j]);
            }
        }
        //自动列宽
        if (dataList.length > 0) {
            int colCount = dataList[0].length;
            for (int i = 0; i < colCount; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        return workbook;

    }

    /**
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于多个sheet
     *
     * @param <T>    范型
     * @param sheets {@link ExcelSheet}的集合
     */
    public static <T> XSSFWorkbook exportExcel(List<ExcelSheet<T>> sheets) {
        return exportExcel(sheets, null);
    }

    /**
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于多个sheet
     *
     * @param <T>     范型
     * @param sheets  {@link ExcelSheet}的集合
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public static <T> XSSFWorkbook exportExcel(List<ExcelSheet<T>> sheets, String pattern) {
        if (CollectionUtils.isEmpty(sheets)) {
            return null;
        }
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (ExcelSheet<T> sheet : sheets) {
            // 生成一个表格
            XSSFSheet XSSFSheet = workbook.createSheet(sheet.getSheetName());
            write2Sheet(XSSFSheet, sheet.getHeaders(), sheet.getDataset(), pattern);
        }
        return workbook;
    }

    /**
     * 每个sheet的写入
     *
     * @param sheet   页签
     * @param headers 表头
     * @param dataset 数据集合
     * @param pattern 日期格式
     */
    private static <T> void write2Sheet(XSSFSheet sheet, Map<String, String> headers, Collection<T> dataset,
                                        String pattern, int... ids) {
//        //时间格式默认"yyyy-MM-dd"
//        if (StringUtils.isEmpty(pattern)) {
//            pattern = "yyyy-MM-dd";
//        }
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        // 标题行转中文
        Set<String> keys = headers.keySet();
        //存放临时键变量
        String key;
        //标题列数
        int c = 0;
        while (c < headers.size()) {
            key = String.valueOf(c);
            if (headers.containsKey(key)) {
                XSSFCell cell = row.createCell(c);
                XSSFRichTextString text = new XSSFRichTextString(headers.get(key));
                cell.setCellValue(text);
                c++;
            }
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            try {
                if (t instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) t;
                    int cellNum = 0;
                    //遍历列名
                    Iterator<String> it2 = keys.iterator();
                    while (it2.hasNext()) {
                        key = it2.next();
                        if (!headers.containsKey(key)) {
                            LG.error("Map 中 不存在 key [" + key + "]");
                            continue;
                        }
                        Object value = map.get(key);
                        XSSFCell cell = row.createCell(cellNum);
                        String usingPattern = switchPattern(pattern, cellNum, ids);

                        cellNum = setCellValue(cell, value, usingPattern, cellNum, null, row);

                        cellNum++;
                    }
                } else {
                    List<FieldForSortting> fields = sortFieldByAnno(t.getClass());
                    int cellNum = 0;
                    for (FieldForSortting ffs : fields) {
                        XSSFCell cell = row.createCell(cellNum);
                        Field field = ffs.getField();
                        field.setAccessible(true);
                        Object value = field.get(t);
                        String usingPattern = switchPattern(pattern, cellNum, ids);
                        cellNum = setCellValue(cell, value, usingPattern, cellNum, field, row);

                        cellNum++;
                    }
                }
            } catch (Exception e) {
                LG.error(e.toString(), e);
            }
        }
        // 设定自动宽度
        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static int setCellValue(XSSFCell cell, Object value, String pattern, int cellNum, Field field, XSSFRow row) {
        String textValue = null;
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            cell.setCellValue(intValue);
        } else if (value instanceof Float) {
            float fValue = (Float) value;
            cell.setCellValue(fValue);
        } else if (value instanceof Double) {
            double dValue = (Double) value;
            cell.setCellValue(dValue);
        } else if (value instanceof Long) {
            long longValue = (Long) value;
            cell.setCellValue(longValue);
        } else if (value instanceof Boolean) {
            boolean bValue = (Boolean) value;
            cell.setCellValue(bValue);
        } else if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            textValue = sdf.format(date);
        } else if (value instanceof String[]) {
            String[] strArr = (String[]) value;
            for (int j = 0; j < strArr.length; j++) {
                String str = strArr[j];
                cell.setCellValue(str);
                if (j != strArr.length - 1) {
                    cellNum++;
                    cell = row.createCell(cellNum);
                }
            }
        } else if (value instanceof Double[]) {
            Double[] douArr = (Double[]) value;
            for (int j = 0; j < douArr.length; j++) {
                Double val = douArr[j];
                // 值不为空则set Value
                if (val != null) {
                    cell.setCellValue(val);
                }

                if (j != douArr.length - 1) {
                    cellNum++;
                    cell = row.createCell(cellNum);
                }
            }
        } else {
            // 其它数据类型都当作字符串简单处理
            String empty = StringUtils.EMPTY;
            if (field != null) {
                ExcelCell anno = field.getAnnotation(ExcelCell.class);
                if (anno != null) {
                    empty = anno.defaultValue();
                }
            }
            textValue = value == null ? empty : value.toString();
        }
        if (textValue != null) {
            XSSFRichTextString richString = new XSSFRichTextString(textValue);
            cell.setCellValue(richString);
        }
        return cellNum;
    }

    /**
     * 把Excel的数据封装成voList
     *
     * @param clazz       vo的Class
     * @param inputStream excel输入流
     * @param pattern     如果有时间数据，设定输入格式。默认为"yyy-MM-dd"
     * @param logs        错误log集合
     * @param arrayCount  如果vo中有数组类型,那就按照index顺序,把数组应该有几个值写上.
     * @return voList
     * @throws RuntimeException
     */
    public static <T> Collection<T> importExcel(Class<T> clazz, InputStream inputStream,
                                                String pattern, ExcelLogs logs, Integer... arrayCount) {
        Workbook workBook=null;

        List<T> list = new ArrayList<>();
        try {
            workBook = WorkbookFactory.create(inputStream);
            Sheet sheet = workBook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            List<ExcelLog> logList = new ArrayList<>();
            // Map<title,index>
            Map<String, Integer> titleMap = new HashMap<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    if (clazz == Map.class) {
                        // 解析map用的key,就是excel标题行
                        Iterator<Cell> cellIterator = row.cellIterator();
                        Integer index = 0;
                        while (cellIterator.hasNext()) {
                            String value = cellIterator.next().getStringCellValue();
                            titleMap.put(value, index);
                            index++;
                        }
                    }
                    continue;
                }
                // 整行都空，就跳过
                boolean allRowIsNull = true;
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Object cellValue = getCellValue(cellIterator.next());
                    if (cellValue != null) {
                        allRowIsNull = false;
                        break;
                    }
                }
                if (allRowIsNull) {
                    LG.warn("行号: [" + row.getRowNum() + " ] 所有数据为空");
                    continue;
                }
                StringBuilder log = new StringBuilder();
                if (clazz == Map.class) {
                    //初始大小设为26
                    Map<String, Object> map = new HashMap<>(26);
                    for (String k : titleMap.keySet()) {
                        Integer index = titleMap.get(k);
                        Cell cell = row.getCell(index);
                        // 判空
                        if (cell == null) {
                            map.put(k, null);
                        } else {
                            cell.setCellType(CellType.STRING);
                            String value = cell.getStringCellValue();
                            map.put(k, value);
                        }
                    }
                    list.add((T) map);

                } else {
                    T t = clazz.getDeclaredConstructor().newInstance();
                    // 标识当前第几个数组了
                    int arrayIndex = 0;
                    // 标识当前读到这一行的第几个cell了
                    int cellIndex = 0;
                    List<FieldForSortting> fields = sortFieldByAnno(clazz);
                    for (FieldForSortting ffs : fields) {
                        Field field = ffs.getField();
                        field.setAccessible(true);
                        if (field.getType().isArray()) {
                            Integer count = arrayCount[arrayIndex];
                            Object[] value;
                            if (field.getType().equals(String[].class)) {
                                value = new String[count];
                            } else {
                                // 目前只支持String[]和Double[]
                                value = new Double[count];
                            }
                            for (int i = 0; i < count; i++) {
                                Cell cell = row.getCell(cellIndex);
                                String errMsg = validateCell(cell, field, cellIndex);
                                if (StringUtils.isBlank(errMsg)) {
                                    value[i] = getCellValue(cell);
                                } else {
                                    log.append(errMsg);
                                    log.append(";");
                                    logs.setHasError(true);
                                }
                                cellIndex++;
                            }
                            field.set(t, value);
                            arrayIndex++;
                        } else {
                            Cell cell = row.getCell(cellIndex);
                            String errMsg = validateCell(cell, field, cellIndex);
                            if (StringUtils.isBlank(errMsg)) {
                                Object value = null;
                                // 处理特殊情况,Excel中的String,转换成Bean的Date
                                if (field.getType().equals(Date.class)
                                        && cell.getCellTypeEnum() == CellType.STRING) {
                                    Object strDate = getCellValue(cell);
                                    try {
                                        value = new SimpleDateFormat(pattern).parse(strDate.toString());
                                    } catch (ParseException e) {

                                        errMsg =
                                                MessageFormat.format("单元格 [{0}] 无法转为日期类型",
                                                        CellReference.convertNumToColString(cell.getColumnIndex()));
                                    }
                                } else {
                                    value = getCellValue(cell);
                                    // 处理特殊情况,excel的value为String,且bean中为其他,且defaultValue不为空,那就=defaultValue
                                    ExcelCell annoCell = field.getAnnotation(ExcelCell.class);
                                    if (value instanceof String && !field.getType().equals(String.class)
                                            && StringUtils.isNotBlank(annoCell.defaultValue())) {
                                        value = annoCell.defaultValue();
                                    }
                                }
                                field.set(t, value);
                            }
                            if (StringUtils.isNotBlank(errMsg)) {
                                log.append(errMsg);
                                log.append(";");
                                logs.setHasError(true);
                            }
                            cellIndex++;
                        }
                    }

                    list.add(t);
                    logList.add(new ExcelLog(t, log.toString(), row.getRowNum() + 1));
                }
            }
            logs.setLogList(logList);
        } catch (InstantiationException e) {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}",
                    clazz.getSimpleName()), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}",
                    clazz.getSimpleName()), e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            LG.error("load excel file error", e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (workBook!=null){
                try {
                    workBook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 验证Cell类型是否正确
     *
     * @param cell    cell单元格
     * @param field   栏位
     * @param cellNum 第几个栏位,用于errMsg
     * @return
     */
    private static String validateCell(Cell cell, Field field, int cellNum) {
        String columnName = CellReference.convertNumToColString(cellNum);
        String result = null;
        CellType[] cellTypeArr = validateMap.get(field.getType());
        if (cellTypeArr == null) {
            result = MessageFormat.format("不支持的类型 [{0}]", field.getType().getSimpleName());
            return result;
        }
        ExcelCell annoCell = field.getAnnotation(ExcelCell.class);
        boolean isBlank = cell == null
                || (cell.getCellTypeEnum() == CellType.STRING && StringUtils.isBlank(cell
                .getStringCellValue()));
        if (isBlank) {
            if (annoCell != null && !annoCell.valid().allowNull()) {
                result = MessageFormat.format("单元格 [{0}] 不可为空", columnName);
            }
        } else if (cell.getCellTypeEnum() == CellType.BLANK && annoCell.valid().allowNull()) {
            return null;
        } else {
            List<CellType> cellTypes = Arrays.asList(cellTypeArr);

            // 如果类型不在指定范围内,并且没有默认值
            boolean existed = cellTypes.contains(cell.getCellTypeEnum())
                    || StringUtils.isNotBlank(annoCell.defaultValue())
                    && cell.getCellTypeEnum() == CellType.STRING;
            if (!existed) {
                StringBuilder strType = new StringBuilder();
                for (int i = 0; i < cellTypes.size(); i++) {
                    CellType cellType = cellTypes.get(i);
                    strType.append(getCellTypeByInt(cellType));
                    if (i != cellTypes.size() - 1) {
                        strType.append(",");
                    }
                }
                result =
                        MessageFormat.format("单元格 [{0}] 类型必须为 [{1}]", columnName, strType.toString());
            } else {
                // 类型符合验证,但值不在要求范围内的
                // String in
                if (annoCell.valid().in().length != 0 && cell.getCellTypeEnum() == CellType.STRING) {
                    String[] in = annoCell.valid().in();
                    String cellValue = cell.getStringCellValue();
                    boolean isIn = false;
                    for (String str : in) {
                        if (str.equals(cellValue)) {
                            isIn = true;
                        }
                    }
                    if (!isIn) {
                        result = MessageFormat.format("单元格 [{0}] 值必须在 {1}", columnName, in);
                    }
                }
                // 数字型
                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    double cellValue = cell.getNumericCellValue();
                    // 小于
                    if (!Double.isNaN(annoCell.valid().lt())) {
                        if (!(cellValue < annoCell.valid().lt())) {
                            result =
                                    MessageFormat.format("单元格 [{0}] 值必须小于 [{1}]", columnName,
                                            annoCell.valid().lt());
                        }
                    }
                    // 大于
                    if (!Double.isNaN(annoCell.valid().gt())) {
                        if (!(cellValue > annoCell.valid().gt())) {
                            result =
                                    MessageFormat.format("单元格 [{0}] 值必须大于 [{1}]", columnName,
                                            annoCell.valid().gt());
                        }
                    }
                    // 小于等于
                    if (!Double.isNaN(annoCell.valid().le())) {
                        if (!(cellValue <= annoCell.valid().le())) {
                            result =
                                    MessageFormat.format("单元格 [{0}] 值必须小于等于 [{1}]",
                                            columnName, annoCell.valid().le());
                        }
                    }
                    // 大于等于
                    if (!Double.isNaN(annoCell.valid().ge())) {
                        if (!(cellValue >= annoCell.valid().ge())) {
                            result =
                                    MessageFormat.format("单元格 [{0}] 值必须大于等于 [{1}]",
                                            columnName, annoCell.valid().ge());
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 根据annotation的seq排序后的栏位
     *
     * @param clazz
     * @return
     */
    private static List<FieldForSortting> sortFieldByAnno(Class<?> clazz) {
        Field[] fieldsArr = clazz.getDeclaredFields();
        List<FieldForSortting> fields = new ArrayList<>();
        List<FieldForSortting> annoNullFields = new ArrayList<>();
        for (Field field : fieldsArr) {
            ExcelCell ec = field.getAnnotation(ExcelCell.class);
            if (ec == null) {
                // 没有ExcelCell Annotation 视为不汇入
                continue;
            }
            int id = ec.index();
            fields.add(new FieldForSortting(field, id));
        }
        fields.addAll(annoNullFields);
        sortByProperties(fields, true, false, "index");
        return fields;
    }

    private static void sortByProperties(List<? extends Object> list, boolean isNullHigh,
                                         boolean isReversed, String... props) {
        if (CollectionUtils.isNotEmpty(list)) {
            Comparator<?> typeComp = ComparableComparator.getInstance();
            if (isNullHigh) {
                typeComp = ComparatorUtils.nullHighComparator(typeComp);
            } else {
                typeComp = ComparatorUtils.nullLowComparator(typeComp);
            }
            if (isReversed) {
                typeComp = ComparatorUtils.reversedComparator(typeComp);
            }

            List<Object> sortCols = new ArrayList<Object>();

            if (props != null) {
                for (String prop : props) {
                    sortCols.add(new BeanComparator(prop, typeComp));
                }
            }
            if (sortCols.size() > 0) {
                Comparator<Object> sortChain = new ComparatorChain(sortCols);
                Collections.sort(list, sortChain);
            }
        }
    }

    private static String switchPattern(String pattern, int cellNum, int... ids) {
        String s;
        String defaultPattern = "yyyy-MM-dd";
        if (ObjectUtils.isEmpty(pattern)) {
            s = defaultPattern;
        } else {
            if (ObjectUtils.isEmpty(ids)) {
                s = pattern;
            } else {
                int flag = 0;
                for (int id : ids) {
                    if (cellNum == id) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    s = defaultPattern;
                } else {
                    s = pattern;
                }
            }
        }
        return s;
    }

}
