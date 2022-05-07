package com.xyl.mydelivery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyl.mydelivery.common.Result;
import com.xyl.mydelivery.entity.User;
import com.xyl.mydelivery.service.UserService;
import com.xyl.mydelivery.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;


// /user/sendMsg'
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Value("${spring.mail.username}")
    private String from;   // 邮件发送人
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    // 发送邮箱验证码
    @PostMapping("/sendMsg") // sendMsg
    public Result<String> sendMsg(@RequestBody User user, HttpSession session){
        //  获取邮箱账号
        String phone = user.getPhone();

        String subject = "迅磊餐购登录验证码";
        if (StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            String context = "欢迎使用迅磊餐购，登录验证码为: " + code + ",五分钟内有效，请妥善保管!";

             log.info("code={}",code);

             userService.sendMsg(phone,subject,context);

            //  将随机生成的验证码保存到session中
            session.setAttribute(phone,code);
            return Result.success("验证码发送成功，请及时查看!");
        }

        return Result.error("验证码发送失败，请重新输入!");
    }

    // 消费者端 用户登录
    @PostMapping("/login")
//    @Async
    public Result<User> login(@RequestBody Map map, HttpSession session){

        log.info("userMap:{}"+map.toString());
        // 获取登录表单的 邮箱账号
        String phone = map.get("phone").toString();
        // 获取 验证码
        String code = map.get("code").toString();

        // 从Session中 获取保存的验证码,session 邮箱账号为 key，验证码为value
        Object codeInSession = session.getAttribute(phone);

        //  页面提交的验证码 和 Session中保存的验证码 进行比对
        if (codeInSession != null && codeInSession.equals(code)){
            //  验证比对无误后，可以成功登录
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if (user == null){  // 数据库中没有当前用户，即当前用户为新用户
                //  新用户 自动注册，其信息保存到数据库中
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);  // 设置用户的状态为1，表示用户可以正常使用，为0，则禁用

                userService.save(user);
            }
            // 用户保存到数据库中后，会自动生成userId,
            session.setAttribute("user",user.getId());
            //  需要在浏览器端保存用户信息，故返回的数据类型为 Result<User>
            return Result.success(user);

        }
        return Result.error("登录失败，请重新登录!");
    }




}
