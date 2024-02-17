package com.ghtt.socialplatform.controller.innerController;

import com.ghtt.socialplatform.controller.Code;
import com.ghtt.socialplatform.controller.Result;
import com.ghtt.socialplatform.controller.exceptions.BusinessException;
import com.ghtt.socialplatform.global.ServerProperties;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


@RestController
public class RefreshController {
    @RequestMapping(value = "/refresh")
    public Result refreshToken(@NonNull @RequestHeader(name="Referer") String referer, @org.springframework.lang.NonNull @RequestAttribute String newToken) {
        Assert.notNull(referer,"非法调用");
        if(!ServerProperties.INTERNAL_SERVER_REQUEST.equals(referer)){
            throw new BusinessException(Code.ILLEGAL_ACCESS,"非法调用"+this.getClass().getName());
        }
        Assert.notNull(newToken,"非法调用");
        return new Result(Code.NEW_TOKEN,"newToken",newToken);
    }
}
