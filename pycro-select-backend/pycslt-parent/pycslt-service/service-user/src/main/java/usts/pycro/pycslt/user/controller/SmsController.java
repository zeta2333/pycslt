package usts.pycro.pycslt.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.user.service.SmsService;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-23 11:15
 */
@RestController
@RequestMapping("api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/sendCode/{phone}")
    public Result<?> sendCode(@PathVariable String phone) {
        smsService.sendCode(phone);
        return Result.ok(null);
    }

}
