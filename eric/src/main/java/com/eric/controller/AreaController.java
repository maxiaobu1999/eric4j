package com.eric.controller;

import cn.hutool.core.bean.BeanUtil;
import com.eric.BaseResponse;
import com.eric.constant.Constant;
import com.eric.jwt.JwtUtils;
import com.eric.repository.dto.UserAddrDto;
import com.eric.repository.entity.Area;
import com.eric.repository.entity.Transport;
import com.eric.repository.entity.UserAddr;
import com.eric.repository.enums.AreaLevelEnum;
import com.eric.repository.param.AddrParam;
import com.eric.service.AreaService;
import com.eric.service.UserAddrService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController // 相当于@ResponseBody和@Controller
@RequestMapping(value = "/area")// 配置url映射,一级
@CrossOrigin(origins = "*")// 解决浏览器跨域问题(局部)
@Api(value = "activity", description = "地址选择")
@SuppressWarnings("Duplicates") // 去除代码重复警告
public class AreaController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Resource
    private AreaService mAreaService;

    /**
     * 分页获取
     */
    @GetMapping("/page")
//    @PreAuthorize("@pms.hasPermission('admin:area:page')")
    public BaseResponse<Page<Area>> page(long pid , int pageNum, int pageSize) {
        logger.info("page" + ",pid:" + pid);
        Page<Area> objects = PageHelper.startPage(pageNum, pageSize);
        List<Area> sysUserPage = mAreaService.listByPid(pid);
        int pageTotal = objects.getPages();
        BaseResponse<Page<Area>> res = BaseResponse.success(objects);
        res.setTotal(pageTotal);
        return res;
    }

    /**
     * 获取省市
     */
    @GetMapping("/list")
//    @PreAuthorize("@pms.hasPermission('admin:area:list')")
    public BaseResponse<List<Area>> list(Area area) {
        logger.info("list" + ",area:" + area.getAreaId());
        List<Area> areas = mAreaService.listByPid(area.getParentId());
        return BaseResponse.success(areas);
    }

    /**
     * 通过父级id获取区域列表
     */
    @GetMapping("/listByPid")
    public BaseResponse<List<Area>> listByPid(Long pid) {
        logger.info("listByPid" + ",pid:" + pid);
        List<Area> list = mAreaService.listByPid(pid);
        return BaseResponse.success(list);
    }

    /**
     * 获取信息
     */
    @GetMapping("/info/{id}")
//    @PreAuthorize("@pms.hasPermission('admin:area:info')")
    public BaseResponse<Area> info(@PathVariable("id") Long id) {
        Area area = mAreaService.getById(id);
        return BaseResponse.success(area);
    }

    /**
     * 保存
     */
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('admin:area:save')")
    public BaseResponse<Void> save(@Valid @RequestBody Area area) {
        if (area.getParentId() != null) {
            Area parentArea = mAreaService.getById(area.getParentId());
            area.setLevel(parentArea.getLevel() + 1);
            mAreaService.removeAreaCacheByParentId(area.getParentId());
        }
        mAreaService.save(area);
        return BaseResponse.success();
    }

    /**
     * 修改
     */
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('admin:area:update')")
    public BaseResponse<Void> update(@Valid @RequestBody Area area) {
        Area areaDb = mAreaService.getById(area.getAreaId());
        // 判断当前省市区级别，如果是1级、2级则不能修改级别，不能修改成别人的下级
        if(Objects.equals(areaDb.getLevel(), AreaLevelEnum.FIRST_LEVEL.value()) && !Objects.equals(area.getLevel(),AreaLevelEnum.FIRST_LEVEL.value())){
            throw new RuntimeException("不能改变一级行政地区的级别");
        }
        if(Objects.equals(areaDb.getLevel(),AreaLevelEnum.SECOND_LEVEL.value()) && !Objects.equals(area.getLevel(),AreaLevelEnum.SECOND_LEVEL.value())){
            throw new RuntimeException("不能改变二级行政地区的级别");
        }
        hasSameName(area);
        mAreaService.updateById(area);
        mAreaService.removeAreaCacheByParentId(area.getParentId());
        return BaseResponse.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
//    @PreAuthorize("@pms.hasPermission('admin:area:delete')")
    public BaseResponse<Void> delete(@PathVariable Long id) {
        Area area = mAreaService.getById(id);
        mAreaService.removeById(id);
        mAreaService.removeAreaCacheByParentId(area.getParentId());
        return BaseResponse.success();
    }

    private void hasSameName(Area area) {
        // todo
//        long count = areaService.count(new LambdaQueryWrapper<Area>()
//                .eq(Area::getParentId, area.getParentId())
//                .eq(Area::getAreaName, area.getAreaName())
//                .ne(Objects.nonNull(area.getAreaId()) && !Objects.equals(area.getAreaId(), 0L), Area::getAreaId, area.getAreaId())
//        );
//        if (count > 0) {
//            throw new YamiShopBindException("该地区已存在");
//        }
    }
}
