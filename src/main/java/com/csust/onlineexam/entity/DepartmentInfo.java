package com.csust.onlineexam.entity;

import lombok.Data;

import java.util.List;

/**
 * @author ：Lenovo.
 * @date ：Created in 14:43 2020/2/19
 */
@Data
public class DepartmentInfo {
    private Department department;
    private List<ClassInfo> classInfoList;
}
