package com.crazypug.webtest;


import com.crazypug.core.bean.Result;
import com.crazypug.core.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TestController {


    @GetMapping( "")
    public Result<String> testFail() {
        throw new BusinessException("业务错误");
    }

    @GetMapping( "null")
    public Result<String> nullTest() {
        String aaa=null;

        aaa.hashCode();


        return Result.ok();
    }
}
