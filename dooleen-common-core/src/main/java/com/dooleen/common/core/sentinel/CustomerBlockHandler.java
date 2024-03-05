package com.dooleen.common.core.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.enums.BizResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
public class CustomerBlockHandler {

    /**
     * 自定义限流错误
     * @param commonMsg
     * @param e
     * @return
     */
    public static CommonMsg<Object, Object> customBlockHandler(@RequestBody CommonMsg<Object, Object> commonMsg, BlockException e){
        log.info("====开始进入自定义限流处理==== ");
        BizResponseEnum.SENTINEL_ERROR_FLOW.assertIsTrue(false,commonMsg);
        return null;
    }

    /**
     * 自定义熔断错误
     * @param commonMsg
     * @param e
     * @return
     */
    public static  CommonMsg<Object, Object> customFallback(@RequestBody CommonMsg<Object, Object> commonMsg, BlockException e){
        log.info("====开始进入自定义降级处理==== ");
        BizResponseEnum.SENTINEL_ERROR_DEGRADE.assertIsTrue(false,commonMsg);
        return null;
    }
}