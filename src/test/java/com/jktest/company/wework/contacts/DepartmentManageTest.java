package com.jktest.company.wework.contacts;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@DisplayName("部门业务线")
class DepartmentManageTest {
//    DepartmentManage departmentManage;
    DepartmentManage departmentManage;
    @BeforeEach
    void setUp(){
        departmentManage = new DepartmentManage();
    }

    @Test
    @DisplayName("查询部门信息")
    void list() {

        departmentManage.list("").then().statusCode(200).body("department.name",hasItems("测试部门"));
        departmentManage.list("2").then().statusCode(200).body("department.find {it.id == 2}.name",equalTo("测试部门"));
        departmentManage.list("3").then().statusCode(200).body("department[0].id",equalTo(3));

    }

    @Test
    @DisplayName("创建部门")
    void create() {
        departmentManage.create("测试部门20","1","20").then().body("errcode",equalTo(0)).body("errmsg",equalTo("created"));
        departmentManage.delete("20").then().body("errcode",equalTo(0));
    }

    @Test
    @DisplayName("删除部门")
    void delete() {
        departmentManage.create("要删除的测试部门","1","10").then().body("errcode",equalTo(0)).body("errmsg",equalTo("created"));
        departmentManage.delete("10").then().body("errcode",equalTo(0));
    }

    @Test
    @DisplayName("刷新部门")
    void update() {
        String newName = "测试部门"+RandomStringUtils.randomAlphanumeric(5);
        departmentManage.update("4",newName).then().body("errcode",equalTo(0));
        departmentManage.list("4").then().body("department.find {it.id == 4}.name",equalTo(newName));
    }


    @Test
    @DisplayName("删除所有部门")
    void deleteAll() {
//        departmentManage.deleteAll();
    }

    @Test
    @DisplayName("通过HAR创建部门")
    void createHar() {
        departmentManage.createHar("/api/create.har.json",".*department/create.*",null).then().body("errcode",equalTo(0)).body("errmsg",equalTo("created"));
        departmentManage.delete("20").then().body("errcode",equalTo(0));
    }

    @Test
    @DisplayName("鱼书搜索")
    void testYushuSearch() {
        departmentManage.yushuSearch("/api/yushu.har.json",".*book/search.*",null);
    }
}