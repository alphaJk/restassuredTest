package com.jktest.company.wework.contacts;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 * Description:部门管理业务线
 */
public class DepartmentManage extends ContactBaseInfo {

    /**
     * 查询部门信息
     * @param id
     * @return
     */
    public Response list(String id){
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        return super.getResponseFromYaml("/api/list.yaml",map);
    }

    /**
     * 创建部门
     * @param name
     * @param parentid
     * @return
     */
    public Response create(String name, String parentid,String id){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("parentid",parentid);
        map.put("id",id);
        return super.getResponseFromYaml("/api/create.yaml",map);
    }


    /**
     * 删除部门
     * @param id
     * @return
     */
    public Response delete(String id){
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",id);
        return super.getResponseFromYaml("/api/delete.yaml",map);
    }

    /**
     * 更新部门
     * @param id
     * @param name
     * @return
     */
    public Response update(String id,String name){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("id",id);
        return super.getResponseFromYaml("/api/update.yaml",map);

    }

    /**
     * 数据清除，慎用
     * @return
     */
    public Response deleteAll(){
        List<Integer> idList =  list("").then().log().all().extract().path("department.id");
        System.out.println(idList);
        for (Integer id : idList) {
            delete(id.toString());
        }
        return null;
    }


    public Response createHar(String path,String pattern,HashMap<String,Object> map){
        return super.getResponesFromHar(path,pattern,map);
    }

    public Response yushuSearch(String path,String pattern,HashMap<String,Object> map){
        return super.getResponesFromHar(path,pattern,map);
    }

}
