package com.y687.mavenrabbitmqspringboot.controller;

import com.y687.mavenrabbitmqspringboot.config.MqSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 *
 * @Author bin.yin
 * @createTime 2019/11/8 14:01
 * @Version
 */
@RestController
@Slf4j
@RequestMapping(value = "/sms/msg")
public class TestController {


    @Autowired
    private MqSender msgSender;

    @RequestMapping(method = RequestMethod.POST,value = "/send",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String sendMsg(@RequestPart String msg){
        msgSender.send(msg);
        return "success";
    }
}
