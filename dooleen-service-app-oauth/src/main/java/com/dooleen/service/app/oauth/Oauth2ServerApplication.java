package com.dooleen.service.app.oauth;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.utils.CheckTableEntityORME;
import com.dooleen.common.core.utils.DooleenBanner;
import com.dooleen.service.app.oauth.properties.OAuth2Properties;
import com.dooleen.service.app.oauth.utils.JsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description : 启动类
 * @Author : name
 * @Update : 2020-06-06 19:37:42
 */
@RestController
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ComponentScan(basePackages = {
		"com.dooleen.common.core.app.**",
		"com.dooleen.service.app.oauth.config",
		"com.dooleen.service.app.oauth.*",
        "com.dooleen.service.app.oauth.controller",
		"com.dooleen.common.core.utils.aes",
		"com.dooleen.common.core.global.exception",
		"com.dooleen.common.core.config.redis",
		"com.dooleen.common.core.config.ioc"})
//扫描DAO
@MapperScan(basePackages = {
		"com.dooleen.common.core.app.**.mapper"})
@Slf4j
public class Oauth2ServerApplication {

    @Autowired
    private OAuth2Properties oAuth2Properties;
    
    public static void main(String[] args) throws Exception {
		// 检查TableEntityORMEnum中是否有重复元素
		CheckTableEntityORME.checkTableEntityORME();
		
    	AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    	SpringApplication springApplication = new SpringApplication(Oauth2ServerApplication.class);
    	springApplication.setBanner(new DooleenBanner());
        springApplication.run(args); 
    }

    @GetMapping("/userInfo/getUserId" )
    public Object getUserId(Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
        log.info(">>>>>>>   getCurrentUserJwt authentication={}", JsonUtil.toJson(authentication));
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");
        log.debug("header blog="+ token);
        Claims claims = Jwts.parser().setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String userId = (String) claims.get("user_name");
        if(userId == null)
        {
        	userId = "";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserId", userId);
        return map;
    }
	@RequestMapping(value = "/userJwt",method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public JSONObject getCurrentUserJwt(Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
        log.info("【SecurityOauth2Application】 getCurrentUserJwt authentication={}", JsonUtil.toJson(authentication));

        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");

        Claims claims = Jwts.parser().setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String blog = (String) claims.get("blog");
        log.info("【SecurityOauth2Application】 getCurrentUser1 blog={}", blog);
        log.info("ID: " + claims.getId());
		log.info("Subject: " + claims.getSubject());
		log.info("Issuer: " + claims.getIssuer());
		log.info("Expiration: " + claims.getExpiration());
        return JSONObject.parseObject(JsonUtil.toJson(authentication));
    }
    @GetMapping("/user/me")
    public Principal user(Principal user){
        return user;
    }

    @GetMapping("/check")
    @ResponseBody
    public Map<String,Object> test(@RequestParam(required = true) String username, @RequestParam(required = true) String password){
        Map<String,Object> result = new HashMap<>(1);
        System.out.println(MessageFormat.format("用户名：{0}，密码：{1}",username,password));

        //模拟登录
        System.out.println("模拟登录流程");
        result.put("code",200);

        return result;
    }
}
