package com.qintong.yygh.hosp.controller.api;

import com.qintong.yygh.common.exception.YyghException;
import com.qintong.yygh.common.helper.HttpRequestHelper;
import com.qintong.yygh.common.result.Result;
import com.qintong.yygh.common.result.ResultCodeEnum;
import com.qintong.yygh.common.utils.MD5;
import com.qintong.yygh.hosp.service.DepartmentService;
import com.qintong.yygh.hosp.service.HospitalService;
import com.qintong.yygh.hosp.service.HospitalSetService;
import com.qintong.yygh.hosp.service.ScheduleService;
import com.qintong.yygh.model.hosp.Department;
import com.qintong.yygh.model.hosp.Hospital;
import com.qintong.yygh.model.hosp.Schedule;
import com.qintong.yygh.vo.hosp.DepartmentQueryVo;
import com.qintong.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
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
    private DepartmentService departmentService;
    private ScheduleService scheduleService;

    @Autowired
    public ApiController(HospitalService hospitalService, HospitalSetService hospitalSetService, DepartmentService departmentService, ScheduleService scheduleService) {
        this.hospitalService = hospitalService;
        this.hospitalSetService = hospitalSetService;
        this.departmentService = departmentService;
        this.scheduleService = scheduleService;
    }

    /**
     * 删除排班
     * @param request
     * @return
     */
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号和排班编号
        String hoscode = (String)paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        //TODO 签名校验

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

    /**
     * 查询排班接口
     * @param request
     * @return
     */
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");

        //科室编号
        String depcode = (String)paramMap.get("depcode");
        //当前页 和 每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //TODO 签名校验

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        //调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 上传排班接口
     * @param request
     * @return
     */
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //TODO 签名校验
        scheduleService.save(paramMap);
        return Result.ok();
    }

    /**
     * 删除科室
     */
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        // 获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        // 获取编号
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        // TODO 签名校验

        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

    /**
     * 查询科室接口
     */
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request){
        // 获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        // 获取编号
        String hoscode = (String) paramMap.get("hoscode");
        // 当前页和每页记录数
        Integer page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        Integer limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        // TODO 签名校验

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        // 调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 上传科室
     */
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        // 获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        // 获取编号
        String hoscode = (String) paramMap.get("hoscode");
        // 1 获取医院系统传递来的签名，并进行加密
        String hospSign = (String) paramMap.get("sign");

        // 2 根据传递过来的编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);

        // 3 把查询出来的签名，进行md5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        // 4 判断签名是否一致
        if (!hospSign.equalsIgnoreCase(signKeyMd5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        // 调用service的方法
        departmentService.save(paramMap);
        return Result.ok();
    }

    /**
     * 查询医院
     */
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        // 获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        // 获取编号
        String hoscode = (String) paramMap.get("hoscode");
        // 1 获取医院系统传递来的签名，并进行加密
        String hospSign = (String) paramMap.get("sign");

        // 2 根据传递过来的编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);

        // 3 把查询出来的签名，进行md5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        // 4 判断签名是否一致
        if (!hospSign.equalsIgnoreCase(signKeyMd5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        // 调用service方法，根据编号进行查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
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
