package com.jktest.company.wework.contacts;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 * Description:
 */
class DepartmentManageTest {
//    DepartmentManage departmentManage;
    DepartmentManage departmentManage;
    @BeforeEach
    void setUp(){
        departmentManage = new DepartmentManage();
    }

    @Test
    void list() {

        departmentManage.list("").then().statusCode(200).body("department.name",hasItems("测试部门"));
        departmentManage.list("2").then().statusCode(200).body("department.find {it.id == 2}.name",equalTo("测试部门"));
        departmentManage.list("3").then().statusCode(200).body("department[0].id",equalTo(3));

    }

    @Test
    void create() {
        departmentManage.create("测试部门20","1","20").then().body("errcode",equalTo(0)).body("errmsg",equalTo("created"));
        departmentManage.delete("20").then().body("errcode",equalTo(0));
    }

    @Test
    void delete() {
        departmentManage.create("要删除的测试部门","1","10").then().body("errcode",equalTo(0)).body("errmsg",equalTo("created"));
        departmentManage.delete("10").then().body("errcode",equalTo(0));
    }

    @Test
    void update() {
        String newName = "测试部门"+RandomStringUtils.randomAlphanumeric(5);
        departmentManage.update("4",newName).then().body("errcode",equalTo(0));
        departmentManage.list("4").then().body("department.find {it.id == 4}.name",equalTo(newName));
    }


    @Test
    void deleteAll() {
//        departmentManage.deleteAll();
    }

    @Test
    void createHar() {
        departmentManage.createHar("/api/create.har.json",".*department/create.*",null);
        departmentManage.delete("20").then().body("errcode",equalTo(0));
    }

    @Test
    void yushuSearch() {
        departmentManage.yushuSearch("/api/yushu.har.json",".*book/search.*",null);
    }
}