package com.littledoctor.clinicassistant.module.system.dictionary.controller;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DictionaryEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String root = "/system/dictionary";

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void queryTree() {
    }

    @Test
    public void queryPage() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getById() {
        try {
            String result = mockMvc.perform(MockMvcRequestBuilders.get(root + "/getById")     //伪造的mvc执行get请求
//                    .contentType(MediaType.APPLICATION_JSON_UTF8)//Restful风格添加contentType
                    .param("dictionaryId", "63"))
                    .andExpect(MockMvcResultMatchers.status().isOk())    //执行了get请求后，期望返回的状态码
                    //.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))       //期望发挥的数据长度(jsonpath)
                    .andReturn().getResponse().getContentAsString();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}