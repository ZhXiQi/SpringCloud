/*
package com.springcloud.client.excel.service.impl;

import com.springcloud.client.excel.ExcelLogs;
import com.springcloud.client.excel.Importing;
import com.springcloud.client.excel.service.ExcelService;
import com.springcloud.client.excel.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

*/
/***
 *
 * @author
 * @date 2018/9/6
 *//*

@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    */
/**
     * 初始化行数默认为50
     *//*

    private static final Integer INIT_ROWS;
    */
/**
     * 机构表表头
     *//*

    private static final String[] DEP_HEADS;
    */
/**
     * 设备表表头
     *//*

    private static final String[] DEVICE_HEADS;
    */
/**
     * 机构表各列数据类型
     *//*

    private static final HashMap<String, String> DEP_MAPS;
    */
/**
     * 设备表各列数据类型
     *//*

    private static final HashMap<String, String> DEVICE_MAPS;

    */
/**
     * 机构层级对应名称
     *//*

    private static final HashMap<Integer, String> LEVEL_MAP;

    private static final HashMap<String, String> EXPORT_DEPT_HEADER;

    private static final HashMap<String, String> EXPORT_DEVICE_HEADER;

    private static final List<String> DEP_EXAMPLE;

    private static final List<String> DEVICE_EXAMPLE;

    private DeviceBrandService deviceBrandService;
    private DeviceTypeService deviceTypeService;
    private DeptService deptService;
    @Autowired
    private DeviceService deviceService;
    private ExcelSerialService excelSerialService;

    @Autowired
    private DeviceJpaReository deviceJpaReository;
    @Autowired
    private DeptJpaRepository deptJpaRepository;
    @Autowired
    private HandoverService handoverService;
    @Autowired
    private PollingFreqService pollingFreqService;


    static {

        INIT_ROWS = 50;
        DEP_HEADS = new String[]{"机构ID", "机构名称", "所属省", "所属市", "备注"};
        DEVICE_HEADS = new String[]{"设备名","设备资产编号", "设备品牌", "设备类型", "设备地址", "维保开始日期", "维保结束日期", "备注"};

        LEVEL_MAP = new HashMap<>();
        LEVEL_MAP.put(1, "总行");
        LEVEL_MAP.put(2, "省级分行");
        LEVEL_MAP.put(3, "地市级支行");
        LEVEL_MAP.put(4, "县级支行");


        DEVICE_MAPS = new HashMap<>();
        DEVICE_MAPS.put("设备名", "@");
        DEVICE_MAPS.put("设备资产编号", "@");
        DEVICE_MAPS.put("设备品牌", "@");
        DEVICE_MAPS.put("设备类型", "@");
        DEVICE_MAPS.put("设备地址", "@");
        DEVICE_MAPS.put("维保开始日期", "yyyy-MM-dd");
        DEVICE_MAPS.put("维保结束日期", "yyyy-MM-dd");
        DEVICE_MAPS.put("备注", "@");
        DEP_MAPS = new HashMap<>();
        DEP_MAPS.put("机构ID", "@");
        DEP_MAPS.put("机构名称", "@");
        DEP_MAPS.put("所属省", "0");
        DEP_MAPS.put("所属市", "0");
        DEP_MAPS.put("备注", "@");

        EXPORT_DEPT_HEADER = new HashMap<>(9);
        EXPORT_DEPT_HEADER.put("0", "机构ID");
        EXPORT_DEPT_HEADER.put("1", "机构名称");
        EXPORT_DEPT_HEADER.put("2", "机构级次");
        EXPORT_DEPT_HEADER.put("3", "父机构ID");
        EXPORT_DEPT_HEADER.put("4", "父机构名称");
        EXPORT_DEPT_HEADER.put("5", "创建时间");
        EXPORT_DEPT_HEADER.put("6", "机构状态");
        EXPORT_DEPT_HEADER.put("7", "所属地区");
        EXPORT_DEPT_HEADER.put("8", "备注");

        EXPORT_DEVICE_HEADER = new HashMap<>(18);
        EXPORT_DEVICE_HEADER.put("0", "归属机构ID");
        EXPORT_DEVICE_HEADER.put("1", "归属机构名称");
        EXPORT_DEVICE_HEADER.put("2", "设备类别");
        EXPORT_DEVICE_HEADER.put("3", "设备ID");
        EXPORT_DEVICE_HEADER.put("4", "设备名");
        EXPORT_DEVICE_HEADER.put("5", "设备资产编号");
        EXPORT_DEVICE_HEADER.put("6", "设备品牌");
        EXPORT_DEVICE_HEADER.put("7", "设备状态");
        EXPORT_DEVICE_HEADER.put("8", "创建时间");
        EXPORT_DEVICE_HEADER.put("9", "修改时间");
        EXPORT_DEVICE_HEADER.put("10", "设备维保起始日期");
        EXPORT_DEVICE_HEADER.put("11", "设备维保到期日期");
        EXPORT_DEVICE_HEADER.put("12", "设备位置");
        EXPORT_DEVICE_HEADER.put("13", "日巡检最后时间");
        EXPORT_DEVICE_HEADER.put("14", "月巡检最后时间");
        EXPORT_DEVICE_HEADER.put("15", "季巡检最后时间");
        EXPORT_DEVICE_HEADER.put("16", "半年巡检最后时间");
        EXPORT_DEVICE_HEADER.put("17", "年巡检最后时间");
        EXPORT_DEVICE_HEADER.put("18", "备注");

        DEP_EXAMPLE = new ArrayList<>();
        DEP_EXAMPLE.add("A1000000000000");
        DEP_EXAMPLE.add("浙江省杭州市分行");
        DEP_EXAMPLE.add("浙江");
        DEP_EXAMPLE.add("杭州市");
        DEP_EXAMPLE.add("注意:上传前务必删除此条记录");

        DEVICE_EXAMPLE = new ArrayList<>();
        DEVICE_EXAMPLE.add("南楼空调");
        DEVICE_EXAMPLE.add("code11");
        DEVICE_EXAMPLE.add("美的");
        DEVICE_EXAMPLE.add("空调");
        DEVICE_EXAMPLE.add("南楼502");
        DEVICE_EXAMPLE.add("yyyy-mm-dd");
        DEVICE_EXAMPLE.add("yyyy-mm-dd");
        DEVICE_EXAMPLE.add("注意:上传前务必删除此条记录");
    }

    @Autowired
    public ExcelServiceImpl(DeviceBrandService deviceBrandService,
                            DeviceTypeService deviceTypeService,
                            DeptService deptService,
                            DeviceService deviceService,
                            ExcelSerialService excelSerialService) {
        this.deviceBrandService = deviceBrandService;
        this.deviceTypeService = deviceTypeService;
        this.deptService = deptService;
        this.deviceService = deviceService;
        this.excelSerialService = excelSerialService;
    }


    @Override
    public Map<String, Object> importExcel(MultipartFile file, int templateType) throws Exception {
        ExcelLogs logs = new ExcelLogs();
        // templateType 0:机构;1:设备
        int deptId = UserUtil.getCurrentDeptId();
        Dept currentDept = deptService.ffindById(deptId);
        synchronized (Importing.isImporting){
            Importing.isImporting.add(currentDept.getId());
        }
        Date now = new Date();
        JSONObject extraObject = new JSONObject(1);
        Map<String,Object> resultMap = new HashMap<>();
        if (templateType == 0) {
            JSONObject deptObject = new JSONObject();
            Collection<DeptExcel> deptExcels = excelSerialService.importExcel(DeptExcel.class, file, logs);
            excelSerialService.verifyExcelData(DeptExcel.class, deptExcels, logs);

            if (deptExcels.size() == 0){
                Map<String,Object> errorResult = new HashMap<>();
                errorResult.put("result","error");
                errorResult.put("logs",logs);
                synchronized (Importing.isImporting){
                    Importing.isImporting.remove(currentDept.getId());
                }
                return errorResult;
            }
            List<Integer> deptIds = new ArrayList<>();
            for (DeptExcel deptExcel : deptExcels) {
                if (IDConstants.deptId.get() == 0){
                    Integer maxId = deptJpaRepository.findMaxId();
                    if (!Objects.isNull(maxId) && IDConstants.deptId.get()<maxId){
                        IDConstants.deptId.set(maxId);
                    }
                }
                Dept dept = new Dept(deptExcel);
                int newDeptId = IDConstants.deptId.incrementAndGet();
                deptIds.add(newDeptId);
                dept.setId(newDeptId);
                dept.setParentCode(currentDept.getCode());
                dept.setParentId(currentDept.getId());
                dept.setParentName(currentDept.getName());
                dept.setCreateTime(now);
                dept.setOperateTime(now);
                // 获取当前机构层级，用来作为导入机构的父机构层级
                String parentLevel = currentDept.getLevel();
                int lastLevel = currentDept.getId();
                // 当前导入机构的层级
                String currentLevel = parentLevel + "," + lastLevel;
                dept.setLevel(currentLevel);
                // 设置当前层级名称
                int levelLength = currentLevel.split(",").length;
                dept.setLevelName(LEVEL_MAP.get(levelLength));

                dept.setStatus(0);
                deptObject.put(String.valueOf(newDeptId),dept);

            }
            extraObject.put(Constants.TABLE_DEPT,deptObject);
            RedisUtil.set(Constants.KEY_DEPTID+deptIds.get(0),deptObject,Constants.ONE_HOUR);
            String needHashString = ContractUtil.getTransaction(Constants.TABLE_DEPT, UserUtil.getCurrentUser().getAddress(), deptIds.get(0).toString(), MyGson.toJson(extraObject), Constants.SAVE);
            resultMap.put("key",Constants.KEY_DEPTID+deptIds.get(0));
            resultMap.put("needHashString",needHashString);
            // 0 表示机构
            resultMap.put("type","0");
            resultMap.put("id",deptIds.get(0).toString());
            resultMap.put("logs",logs);

        } else {
            Collection<Device> deviceExcels = excelSerialService.importExcel(Device.class, file, logs);
            excelSerialService.verifyExcelData(Device.class, deviceExcels, logs);
            if (deviceExcels.size() == 0){
                Map<String,Object> errorResult = new HashMap<>();
                errorResult.put("result","error");
                errorResult.put("logs",logs);
                synchronized (Importing.isImporting){
                    Importing.isImporting.remove(currentDept.getId());
                }
                return errorResult;
            }
            JSONObject deviceObject = new JSONObject();
            List<Integer> deviceIds = new ArrayList<>();
            Dept dept = deptJpaRepository.findById(UserUtil.getCurrentDeptId()).get();
            PollingFreq pollingFreq = pollingFreqService.getCurrent();
            for (Device device : deviceExcels) {
                if (IDConstants.deviceId.get() == 0){
                    Integer maxId = deviceJpaReository.findMaxId();
                    if (!Objects.isNull(maxId) && IDConstants.deviceId.get()<maxId){
                        IDConstants.deviceId.set(maxId);
                    }
                }
                int newDeviceId = IDConstants.deviceId.incrementAndGet();
                deviceIds.add(newDeviceId);
                device.setId(newDeviceId);
                device.setDeptId(deptId);
                device.setDeptName(currentDept.getName());
                device.setStatus(0);
                device.setCreateTime(now);
                device.setOperateTime(now);
                Handover handover = handoverService.getDeptHandover(deptId);
                // 有值班信息，并且是初次新建设备时，添加定时器
                if (!ObjectUtils.isEmpty(handover)) {
                    DeviceUtil.setDeviceTime(device,dept,true,now,pollingFreq);
                }
                deviceObject.put(String.valueOf(newDeviceId),device);
            }
            extraObject.put(Constants.TABLE_DEVICE,deviceObject);
            RedisUtil.set(Constants.KEY_DEVICEID+deviceIds.get(0),deviceObject,Constants.ONE_HOUR);
            String needHashString = ContractUtil.getTransaction(Constants.TABLE_DEVICE, UserUtil.getCurrentUser().getAddress(), deviceIds.get(0).toString(), MyGson.toJson(extraObject), Constants.SAVE);

            resultMap.put("key",Constants.KEY_DEVICEID+deviceIds.get(0));
            resultMap.put("needHashString",needHashString);
            // 1 表示设备
            resultMap.put("type","1");
            resultMap.put("id",deviceIds.get(0).toString());
            resultMap.put("logs",logs);
        }
        synchronized (Importing.isImporting){
            Importing.isImporting.remove(currentDept.getId());
        }
        return resultMap;
    }

    @Override
    public <T> XSSFWorkbook exportExcel(Class<T> clazz, Collection<T> list) {
        if (clazz == Dept.class) {
            List<ExportDept> depts = new ArrayList<>();
            for (Iterator<T> iterator = list.iterator(); iterator.hasNext(); ) {
                Dept dept = (Dept) iterator.next();
                ExportDept exportDept = new ExportDept(dept);
                // 根据父机构的id查到父机构，并获取code
                int parentId = dept.getParentId();
                Dept parentDept = deptService.ffindById(parentId);
                // 如果没有父机构，父机构code留空
                String parentCode = "";
                if (parentDept != null) {
                    parentCode = parentDept.getCode();
                }
                exportDept.setParentId(parentCode);
                depts.add(exportDept);
            }
            return ExcelUtil.exportExcel(EXPORT_DEPT_HEADER, depts, "yyyy-MM-dd HH:mm:ss");
        } else if (clazz == Device.class) {
            List<DeviceExcel> devices = new ArrayList<>();
            for (Iterator<T> iterator = list.iterator(); iterator.hasNext(); ) {
                Device device = (Device) iterator.next();
                DeviceExcel deviceExcel = new DeviceExcel(device);
                // 设备所属机构code
                Dept dept = deptService.ffindById(device.getDeptId());
                deviceExcel.setDeptId(dept.getCode());

                // 设备ID前面加上 D-
                deviceExcel.setDeviceId("D-" + deviceExcel.getDeviceId());

                // 加入排序字段
                int sortOrder = 0;
                switch (dept.getLevelOrder()) {
                    case 2:
                        sortOrder = dept.getProvince() * 1000000;
                        break;
                    case 3:
                        sortOrder = dept.getCity() * 10000;
                        break;
                    case 4:
                        sortOrder = dept.getCity() * 10000 + dept.getId();
                    default:
                        break;
                }
                deviceExcel.setDeptOrder(sortOrder);

                devices.add(deviceExcel);
            }

            devices.sort(Comparator.comparing(DeviceExcel::getDeptOrder).thenComparing(DeviceExcel::getDeviceType));
            int[] ids = {7, 8, 9, 12, 13, 14, 15, 16, 17};
            return ExcelUtil.exportExcel(EXPORT_DEVICE_HEADER, devices, "yyyy-MM-dd HH:mm:ss", ids);
        }
        return null;
    }

    @Override
    public void downloadTemplate(HttpServletResponse response, int templateType) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 获取输出流
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // templateType 0:机构;1:设备
            if (templateType == 0) {
                // 获取当前机构的id
                User currentUser = UserUtil.getCurrentUser();
                int depId = currentUser.getDeptId();
                response.setHeader("Content-Disposition", "attachment;filename=" + "Department_template.xlsx");
                response.flushBuffer();
                generateDepTemplate(workbook, depId);
                workbook.write(outputStream);
            } else {
                response.setHeader("Content-Disposition", "attachment;filename=" + "Device_template.xlsx");
                response.flushBuffer();
                generateDeviceTemplate(workbook);
                workbook.write(outputStream);
            }
        }finally {
            if (outputStream!=null){
                outputStream.close();
            }
            if (workbook!=null){
                workbook.close();
            }
        }
    }

    private void generateDepTemplate(XSSFWorkbook workbook, int deptId) {
        XSSFSheet sheet = workbook.createSheet("机构信息");
        sheet.setDefaultColumnWidth(20);
        XSSFRow hssfRow = sheet.createRow(0);
        XSSFRow example = sheet.createRow(1);

        XSSFFont exFont = workbook.createFont();
        exFont.setColor(IndexedColors.RED.getIndex());

        for (int i = 0; i < DEP_HEADS.length; i++) {
            XSSFCell cell = hssfRow.createCell(i);
            XSSFCell ex = example.createCell(i);
            CellStyle cellStyle = workbook.createCellStyle();
            CellStyle exStyle = workbook.createCellStyle();
            short formatId = workbook.getCreationHelper().createDataFormat().getFormat(DEP_MAPS.get(DEP_HEADS[i]));
            cellStyle.setDataFormat(formatId);
            sheet.setDefaultColumnStyle(i, cellStyle);
            XSSFRichTextString text = new XSSFRichTextString(DEP_HEADS[i]);
            cell.setCellValue(text);

            exStyle.setDataFormat(formatId);
            exStyle.setFont(exFont);
            ex.setCellStyle(exStyle);

            ex.setCellValue(new XSSFRichTextString(DEP_EXAMPLE.get(i)));
        }


        // 获取所属机构
        Dept dept = deptService.findById(deptId);
        // 获取当前机构层级，用以确定导出模板省市下拉框值
        String levelString = dept.getLevel();
        int level = levelString.split(",").length;
        // 获取机构所在的省、市编码
        int provinceCode = dept.getProvince();
        int cityCode = dept.getCity();

        if (level == 1) {
            addCityMaps(workbook);
        } else if (level == 2) {
            fillProvince(workbook, provinceCode, 0);
        } else {
            fillProvince(workbook, provinceCode, cityCode);
        }
    }

    private void generateDeviceTemplate(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("设备信息");
        sheet.setDefaultColumnWidth(20);
        XSSFRow hssfRow = sheet.createRow(0);
        XSSFRow example = sheet.createRow(1);

        XSSFFont exFont = workbook.createFont();
        exFont.setColor(IndexedColors.RED.getIndex());

        for (int i = 0; i < DEVICE_HEADS.length; i++) {
            XSSFCell cell = hssfRow.createCell(i);
            XSSFCell ex = example.createCell(i);
            CellStyle cellStyle = workbook.createCellStyle();
            CellStyle exStyle = workbook.createCellStyle();
            short formatId = workbook.getCreationHelper().createDataFormat().getFormat(DEVICE_MAPS.get(DEVICE_HEADS[i]));
            cellStyle.setDataFormat(formatId);
            sheet.setDefaultColumnStyle(i, cellStyle);
            XSSFRichTextString text = new XSSFRichTextString(DEVICE_HEADS[i]);
            cell.setCellValue(text);

            exStyle.setDataFormat(formatId);
            exStyle.setFont(exFont);
            ex.setCellStyle(exStyle);
            ex.setCellValue(new XSSFRichTextString(DEVICE_EXAMPLE.get(i)));
        }


        // 获取设备品牌
//        String[] deviceBrands = new String[(int) deviceBrandService.getDAO().count() + 1];
//        deviceBrandService.getAllBrandNames().toArray(deviceBrands);
        String[] deviceBrands = new String[(int) deviceBrandService.getEnableBrandCount() + 1];
        deviceBrandService.getEnableBrandName().toArray(deviceBrands);
        // 加入" " 保证至少又一个元素
        deviceBrands[deviceBrands.length - 1] = " ";

        // 获取设备类别
//        String[] deviceTypes = new String[(int) deviceTypeService.getDAO().count() + 1];
//        deviceTypeService.getAllTypeNames().toArray(deviceTypes);
        String[] deviceTypes = new String[(int) deviceTypeService.getEnableTypeCount() + 1];
        deviceTypeService.getEnableTypeNames().toArray(deviceTypes);
        // 加入" " 保证至少又一个元素
        deviceTypes[deviceTypes.length - 1] = " ";

        // Excel下拉列表生成
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        // 设备品牌数据
        XSSFDataValidationConstraint brandConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(deviceBrands);
        // 设备类型数据
        XSSFDataValidationConstraint typeConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(deviceTypes);
        // 设备品牌范围默认从第一行到第50行
        CellRangeAddressList brandRegion = new CellRangeAddressList(1, INIT_ROWS, 2, 2);
        // 设备类型范围默认从第一行到第50行
        CellRangeAddressList typeRegion = new CellRangeAddressList(1, INIT_ROWS, 3, 3);
        // 设置数据校验
        XSSFDataValidation brandValidation = (XSSFDataValidation) dvHelper.createValidation(brandConstraint, brandRegion);
        XSSFDataValidation typeValidation = (XSSFDataValidation) dvHelper.createValidation(typeConstraint, typeRegion);
        brandValidation.setShowErrorBox(true);
        typeValidation.setShowErrorBox(true);
        sheet.addValidationData(brandValidation);
        sheet.addValidationData(typeValidation);
    }


    */
/**
     * 根据省市确定下拉表
     *
     * @param workbook 需要添加的workbook
     *//*

    private void fillProvince(XSSFWorkbook workbook, int provinceCode, int cityCode) {
        XSSFSheet sheet = workbook.getSheetAt(0);

        String[] provinces;
        String[] cities;

        String provinceName = CityUtil.getNameByCode(String.valueOf(provinceCode));
        provinces = new String[]{provinceName};
        // cityCode = 0 确定省，市下拉列表；否则省市都确定
        if (cityCode == 0) {
            cities = CityUtil.getCityByProvince(provinceName);
        } else {
            String cityName = CityUtil.getNameByCode(String.valueOf(cityCode));
            cities = new String[]{cityName};
        }

        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);

        XSSFDataValidationConstraint provinceConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(provinces);
        XSSFDataValidationConstraint cityConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(cities);

        CellRangeAddressList provinceRegion = new CellRangeAddressList(1, INIT_ROWS, 2, 2);
        CellRangeAddressList cityRegion = new CellRangeAddressList(1, INIT_ROWS, 3, 3);

        XSSFDataValidation provinceValidation = (XSSFDataValidation) dvHelper.createValidation(provinceConstraint, provinceRegion);
        XSSFDataValidation cityValidation = (XSSFDataValidation) dvHelper.createValidation(cityConstraint, cityRegion);
        provinceValidation.createErrorBox("error", "请选择正确的省份");
        cityValidation.createErrorBox("error", "请选择正确的城市");
        provinceValidation.setShowErrorBox(true);
        cityValidation.setShowErrorBox(true);
        sheet.addValidationData(provinceValidation);
        sheet.addValidationData(cityValidation);

    }


    */
/**
     * 为workbook添加省市级联下拉列表
     *
     * @param workbook 需要添加的workbook
     *//*

    private void addCityMaps(XSSFWorkbook workbook) {
        XSSFSheet area = workbook.createSheet("area");
        workbook.setSheetHidden(1, true);

        int rowId = 0;
        XSSFRow provinceRow = area.createRow(rowId++);
        provinceRow.createCell(0).setCellValue("省列表");
        String[] provinces = CityUtil.getProvinces();
        // 设置第一行存省信息
        for (int i = 0; i < provinces.length; i++) {
            XSSFCell provinceCell = provinceRow.createCell(i + 1);
            provinceCell.setCellValue(provinces[i]);
        }
        // 将具体的数据写入每一行，行开头为父级区域，后面为子区域
        for (int i = 0; i < provinces.length; i++) {
            String key = provinces[i];
            // 子区域
            String[] son = CityUtil.getCityByProvince(key);
            XSSFRow row = area.createRow(rowId++);
            row.createCell(0).setCellValue(key);
            for (int j = 0; j < son.length; j++) {
                XSSFCell cell = row.createCell(j + 1);
                cell.setCellValue(son[j]);
            }

            // 添加名称管理器
            String range = getRange(1, rowId, son.length);
            XSSFName name = workbook.createName();
            // key不可重复
            name.setNameName(key);
            String formula = "area!" + range;
            name.setRefersToFormula(formula);
        }


        // 主sheet
        XSSFSheet sheetPro = workbook.getSheetAt(0);
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheetPro);

        // 省规则
        XSSFDataValidationConstraint provConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(provinces);
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList provRangeAddressList = new CellRangeAddressList(1, INIT_ROWS, 2, 2);
        XSSFDataValidation provinceDataValidation = (XSSFDataValidation) dvHelper.createValidation(provConstraint, provRangeAddressList);
        // 验证
        provinceDataValidation.createErrorBox("error", "请选择正确的省份");
        provinceDataValidation.setShowErrorBox(true);
        provinceDataValidation.setSuppressDropDownArrow(true);
        sheetPro.addValidationData(provinceDataValidation);

        // 设置前 INIT_ROWS 行有效性
        for (int i = 2; i < INIT_ROWS; i++) {
            setDataValidation("C", sheetPro, i, 4);
        }
    }

    */
/**
     * 设置有效性
     *
     * @param offSet 主影响单元格所在列，即此单元格由哪个单元格影响联动
     * @param sheet  主sheet
     * @param rowNum 行数
     * @param colNum 列数
     *//*

    private void setDataValidation(String offSet, XSSFSheet sheet, int rowNum, int colNum) {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidation dataValidationList;
        dataValidationList = getDataValidationByFormula(
                "INDIRECT($" + offSet + (rowNum) + ")", rowNum, colNum, dvHelper);
        sheet.addValidationData(dataValidationList);
    }

    */
/**
     * 加载下拉列表内容
     *
     * @param formulaString      表达式字符串
     * @param naturalRowIndex    行索引
     * @param naturalColumnIndex 影响列
     * @param dvHelper           创建的dvhelper
     * @return dataValidationList
     *//*

    private XSSFDataValidation getDataValidationByFormula(
            String formulaString, int naturalRowIndex, int naturalColumnIndex, XSSFDataValidationHelper dvHelper) {
        // 加载下拉列表内容
        // 举例：若formulaString = "INDIRECT($A$2)" 表示规则数据会从名称管理器中获取key与单元格 A2 值相同的数据，
        //如果A2是江苏省，那么此处就是江苏省下的市信息。
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(formulaString);
        // 设置数据有效性加载在哪个单元格上。
        // 四个参数分别是：起始行、终止行、起始列、终止列
        int firstRow = naturalRowIndex - 1;
        int lastRow = naturalRowIndex - 1;
        int firstCol = naturalColumnIndex - 1;
        int lastCol = naturalColumnIndex - 1;
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                lastRow, firstCol, lastCol);
        // 数据有效性对象
        // 绑定
        XSSFDataValidation dataValidationList = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, regions);
        dataValidationList.setEmptyCellAllowed(false);
        dataValidationList.setSuppressDropDownArrow(true);
        dataValidationList.setShowErrorBox(true);

        // 设置输入信息提示信息
        dataValidationList.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
        // 设置输入错误提示信息
        //dataValidationList.createErrorBox("选择错误提示", "你输入的值未在备选列表中，请下拉选择合适的值！");
        return dataValidationList;
    }


    */
/**
     * 计算formula
     *
     * @param offset   偏移量，如果给0，表示从A列开始，1，就是从B列
     * @param rowId    第几行
     * @param colCount 一共多少列
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
     *//*

    private String getRange(int offset, int rowId, int colCount) {
        char start = (char) ('A' + offset);
        if (colCount <= 25) {
            char end = (char) (start + colCount - 1);
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
        } else {
            char endPrefix = 'A';
            char endSuffix = 'A';
            if ((colCount - 25) / 26 == 0 || colCount == 51) {
                if ((colCount - 25) / 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                }
            } else {
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26 - 1);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26);
                }
            }
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
        }
    }
}
*/
