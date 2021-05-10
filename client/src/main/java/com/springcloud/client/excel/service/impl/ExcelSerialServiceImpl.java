/*
package com.springcloud.client.excel.service.impl;

import com.springcloud.client.excel.ExcelLog;
import com.springcloud.client.excel.ExcelLogs;
import com.springcloud.client.excel.service.ExcelSerialService;
import com.springcloud.client.excel.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

*/
/***
 *
 * @author
 * @date 2018/9/12
 *//*

@Slf4j
@Service
public class ExcelSerialServiceImpl implements ExcelSerialService {

    private DeptService deptService;
    private DeviceService deviceService;
    private DeviceBrandService deviceBrandService;
    private DeviceTypeService deviceTypeService;
    @Autowired
    private DeviceBrandJpaReository deviceBrandJpaReository;

    @Autowired
    public ExcelSerialServiceImpl(DeptService deptService,
                                  DeviceService deviceService,
                                  DeviceBrandService deviceBrandService,
                                  DeviceTypeService deviceTypeService) {
        this.deptService = deptService;
        this.deviceService = deviceService;
        this.deviceBrandService = deviceBrandService;
        this.deviceTypeService = deviceTypeService;
    }

    @Override
    public <T> Collection<T> importExcel(Class<T> clazz, MultipartFile file, ExcelLogs logs) {
        try {
            Collection<T> excels = ExcelUtil.importExcel(clazz, file.getInputStream(), "", logs);
            if (CollectionUtils.isEmpty(excels)) {
                log.error("文件加载出错");
                throw new FileLoadException("文件加载出错");
            }
            return excels;
        } catch (IOException e) {
            log.error("获取文件输入流出错");
            throw new FileLoadException("获取文件输入流出错");
        }
    }

    @Override
    public <T> void verifyExcelData(Class<T> clazz, Collection<T> excels, ExcelLogs logs) {
        List<ExcelLog> logList = logs.getLogList();
        int position = 0;
        // 用于标记是否重复
        Map<String, Integer> exits = new HashMap<>(excels.size());
        Collection<T> recycleCollection = new HashSet<>();
        if (clazz == DeptExcel.class) {
            for (Iterator<T> it = excels.iterator(); it.hasNext(); ) {
                DeptExcel deptExcel = (DeptExcel) it.next();
                // 导入注解验证没过的，不进行后续验证
                if (logList.get(position).getLog() != null && Strings.isNotBlank(logList.get(position).getLog())) {
                    position++;
                    recycleCollection.add((T) deptExcel);
                    continue;
                }
                String depCode = deptExcel.getCode();
                String depName = deptExcel.getName();
                if (StringUtils.isEmpty(depCode)){
                    logList.get(position).setLog("机构ID为空");
                    position++;
                    recycleCollection.add((T) deptExcel);
                    continue;
                }
                if (depCode.length() != 14) {
                    logList.get(position).setLog("机构ID必须为14位");
                    position++;
                    recycleCollection.add((T) deptExcel);
                    continue;
                }
                if (StringUtils.isEmpty(depName)){
                    logList.get(position).setLog("机构名为空");
                    position++;
                    recycleCollection.add((T) deptExcel);
                    continue;
                }
                // 如果没有前缀则添加前缀
                if (depName.length() > 5 && !"银行".equals(depName.substring(0, 6))) {
                    depName = "银行" + depName;
                    deptExcel.setName(depName);
                }
                if (depName.length() < 6) {
                    depName = "银行" + depName;
                    deptExcel.setName(depName);
                }

                if (depName.length() > 50) {
                    logList.get(position).setLog("机构名称不可超过50字");
                    position++;
                    recycleCollection.add((T) deptExcel);
                    continue;
                }
                Dept dept = deptService.findDeptByCode(depCode);
                Dept dept1 = deptService.findDeptByName(depName);
                if (dept != null || dept1 != null) {
                    logList.get(position).setLog("机构已存在");
                    position++;
                    recycleCollection.add((T) deptExcel);
                    continue;
                }

                int currentDeptId = UserUtil.getCurrentDeptId();
                Dept currentDept = deptService.ffindById(currentDeptId);
                if (ObjectUtils.isEmpty(currentDept)) {
                    logList.get(position).setLog("用户归属机构查询出错");
                    position++;
                    recycleCollection.add((T) deptExcel);
                    continue;
                }
                Integer levelOrder = currentDept.getLevelOrder();

                if (levelOrder == 2) {
                    int currentProvince = currentDept.getProvince();
                    String province = CityUtil.getCodeByName(deptExcel.getProvince());
                    if (currentProvince != Integer.parseInt(province)) {
                        logList.get(position).setLog("归属省份错误");
                        position++;
                        recycleCollection.add((T) deptExcel);
                        continue;
                    }
                }
                String remark = deptExcel.getRemark();
                if (remark != null && remark.length() > 150) {
                    logList.get(position).setLog("备注不可超过150字");
                    position++;
                    recycleCollection.add((T) deptExcel);
                    continue;
                }

                if (levelOrder == 3) {
                    int currentProvince = currentDept.getProvince();
                    int currentCity = currentDept.getCity();
                    String province = CityUtil.getCodeByName(deptExcel.getProvince());
                    String city = CityUtil.getCodeByName(String.valueOf(currentProvince) + deptExcel.getCity());
                    if (currentProvince != Integer.parseInt(province)) {
                        logList.get(position).setLog("归属省份错误");
                        position++;
                        recycleCollection.add((T) deptExcel);
                        continue;
                    } else if (currentCity != Integer.parseInt(city)) {
                        logList.get(position).setLog("归属城市错误");
                        position++;
                        recycleCollection.add((T) deptExcel);
                        continue;
                    }
                }

                if (!CityUtil.cpIsRight(deptExcel.getProvince(), deptExcel.getCity())) {
                    logList.get(position).setLog("省市对应关系错误");
                    position++;
                    recycleCollection.add((T) deptExcel);
                    continue;
                }
                removeRepeatData(exits, depCode, position, logList, recycleCollection);
                removeRepeatData(exits, depName, position, logList, recycleCollection);
                position++;
            }
            excels.removeAll(recycleCollection);
        } else if (clazz == Device.class) {
            //查询已经有的设备列表，将编号存在map里，省得循环查询
            Integer deptId = UserUtil.getCurrentUser().getDeptId();
            List<Device> deviceList = deviceService.findDevicesByDeptId(deptId);
            Map<String,Boolean> mm = new HashMap();
            for(Device d:deviceList){
                mm.put(d.getDeviceNum(),true);
            }

            for (Iterator<T> it = excels.iterator(); it.hasNext(); ) {
                Device deviceExcel = (Device) it.next();
                // 导入注解验证没过的，不进行后续验证
                if (logList.get(position).getLog() != null && Strings.isNotBlank(logList.get(position).getLog())) {
                    position++;
                    recycleCollection.add((T) deviceExcel);
                    continue;
                }
                String deviceExcelName = deviceExcel.getName();
                if (StringUtils.isEmpty(deviceExcelName)){
                    logList.get(position).setLog("设备名称为空");
                    recycleCollection.add((T) deviceExcel);
                    position++;
                    continue;
                }
                if (deviceExcelName.length() > 20) {
                    logList.get(position).setLog("设备名称不可超过20字");
                    recycleCollection.add((T) deviceExcel);
                    position++;
                    continue;
                }
                List<Device> devices = deviceService.findDeviceByNameAndDeptId(deviceExcelName, deptId);
                if (devices.size() != 0) {
                    logList.get(position).setLog("本机构下同名设备已存在");
                    recycleCollection.add((T) deviceExcel);
                    position++;
                    continue;
                }
                String devicePosition = deviceExcel.getPosition();
                if (StringUtils.isEmpty(devicePosition)){
                    logList.get(position).setLog("设备地址为空");
                    position++;
                    recycleCollection.add((T) deviceExcel);
                    continue;
                }
                if (devicePosition.length() > 20) {
                    logList.get(position).setLog("设备地址不可超过20字");
                    position++;
                    recycleCollection.add((T) deviceExcel);
                    continue;
                }
                if (deviceExcel.getStartTime().after(deviceExcel.getEndTime())) {
                    logList.get(position).setLog("维保开始日期必须早于结束日期");
                    position++;
                    recycleCollection.add((T) deviceExcel);
                    continue;
                }
                String deviceNum = deviceExcel.getDeviceNum();
                if(deviceNum.length() > 20){
                    logList.get(position).setLog("设备资产编号不可超过20字");
                    position++;
                    recycleCollection.add((T) deviceExcel);
                    continue;
                }
                // 检查设备类别与品牌
                String brandName = deviceExcel.getBrandName();
                String typeName = deviceExcel.getTypeName();
                DeviceBrand deviceBrand = deviceBrandService.findBrandByName(brandName);
                DeviceType deviceType = deviceTypeService.findTypeByName(typeName);
                if (ObjectUtils.isEmpty(deviceBrand)) {
                    logList.get(position).setLog("设备品牌不存在");
                    position++;
                    recycleCollection.add((T) deviceExcel);
                    continue;
                } else {
                    deviceExcel.setBrand(deviceBrand.getId());
                }

                if (ObjectUtils.isEmpty(deviceType)) {
                    logList.get(position).setLog("设备类型不存在");
                    position++;
                    recycleCollection.add((T) deviceExcel);
                    continue;
                } else {
                    deviceExcel.setType(deviceType.getId());
                }
                if (deviceBrand.getDeviceType().intValue() != deviceType.getId().intValue()){
                    logList.get(position).setLog("所选设备类别下的设备品牌不存在");
                    ++position;
                    recycleCollection.add((T) deviceExcel);
                    continue;
                }
                String remark = deviceExcel.getImportRemark();
                if (remark != null && remark.length() > 150) {
                    logList.get(position).setLog("备注不可超过150字");
                    position++;
                    recycleCollection.add((T) deviceExcel);
                    continue;
                }
                // 判断设备编号在当前机构是否已存在
                if(mm.containsKey(deviceNum)){
                    logs.getLogList().get(position).setLog("机构已存在相同设备资产编号的设备");
                    position++;
                    recycleCollection.add((T)deviceExcel);
                    continue;
                }
                deviceExcel.setRemark(deviceExcel.getImportRemark());
                if (deviceExcelName.equals(deviceNum)){
                    removeRepeatData(exits, deviceExcelName+"deviceName", position, logList, recycleCollection);
                    removeRepeatData(exits, deviceNum+"deviceNum", position, logList, recycleCollection);
                }else {
                    removeRepeatData(exits, deviceExcelName, position, logList, recycleCollection);
                    removeRepeatData(exits, deviceNum, position, logList, recycleCollection);

                }
                position++;
            }
            excels.removeAll(recycleCollection);
        } else {

        }
    }

    private <T> void removeRepeatData(Map<String, Integer> exits, String key, Integer position, List<ExcelLog> logList, Collection<T> recycleCollection) {
        if (!exits.containsKey(key)) {
            exits.put(key, position);
        } else {
            Integer index = exits.get(key);
            ExcelLog preLog = logList.get(index);
            ExcelLog nowLog = logList.get(position);
            preLog.setLog("列表内存在重复数据");
            nowLog.setLog("列表内存在重复数据");
            recycleCollection.add((T) preLog.getObject());
            recycleCollection.add((T) nowLog.getObject());
        }
    }
}
*/
