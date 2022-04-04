package com.qintong.yygh.hosp.controller.api;

import com.qintong.yygh.common.exception.YyghException;
import com.qintong.yygh.common.helper.HttpRequestHelper;
import com.qintong.yygh.common.result.Result;
import com.qintong.yygh.common.result.ResultCodeEnum;
import com.qintong.yygh.common.utils.MD5;
import com.qintong.yygh.hosp.service.HospitalService;
import com.qintong.yygh.hosp.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xujinfengxu
 */
@RestController
@RequestMapping("/api/hosp")
@CrossOrigin
public class ApiController {

    private HospitalService hospitalService;
    private HospitalSetService hospitalSetService;

    @Autowired
    public ApiController(HospitalService hospitalService, HospitalSetService hospitalSetService) {
        this.hospitalService = hospitalService;
        this.hospitalSetService = hospitalSetService;
    }

    /**
     * 上传医院接口
     */
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request){
        // 获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        // 1 获取医院系统传递来的签名，并进行加密
        String hospSign = (String) paramMap.get("sign");

        // 2 根据传递过来的编码，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        // 3 把查询出来的签名，进行md5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        // 4 判断签名是否一致
        if (!hospSign.equalsIgnoreCase(signKeyMd5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        // 传输过程中 "+" 转换为 " "，因此我们需要转换回去
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);
        // 调用service的方法
        hospitalService.save(paramMap);
        return Result.ok();
    }


}
