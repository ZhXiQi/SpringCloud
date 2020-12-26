package com.springcloud.client.excel;

import java.util.HashSet;
import java.util.Set;

/***
 *
 * @author
 * @date
 */
public class Importing {

    public static final Set<Integer> isImporting = new HashSet<>();

    /**
     * 判断机构是否在importing
     *
     * @param deptId
     * @return
     */
    public static boolean isDeptImporting(Integer deptId) {
        return isImporting.contains(deptId);
    }

    /**
     * 机构是否正在创建设备
     */
    public static final Set<String> isAddDevice = new HashSet<>();
}
