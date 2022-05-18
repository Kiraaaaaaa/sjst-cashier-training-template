package com.meituan.catering.management.shop.api.http.controller;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.shop.api.http.model.dto.ShopDetailHttpDTO;
import com.meituan.catering.management.shop.api.http.model.request.CloseShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.CreateShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.OpenShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.SearchShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.UpdateShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.response.ShopDetailHttpResponse;
import com.meituan.catering.management.shop.api.http.model.response.ShopPageHttpResponse;
import com.meituan.catering.management.shop.biz.model.converter.*;
import com.meituan.catering.management.shop.biz.model.request.*;
import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.biz.service.ShopBizService;
import com.meituan.catering.management.shop.biz.validator.ShopBizServiceValidator;
import com.meituan.catering.management.shop.dao.converter.SwitchShopDateRequestConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 门店管理Http API
 *
 * @author dulinfeng
 */
@Api(tags = "门店管理")
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    private ShopBizService shopBizService;

    @Resource
    private ShopBizServiceValidator shopBizServiceValidator;

    @ApiOperation("分页搜索门店的概要信息列表")
    @PostMapping("/search")
    public ShopPageHttpResponse searchForPage(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("搜索条件") @Valid @RequestBody SearchShopHttpRequest request) {
        ShopPageHttpResponse response = new ShopPageHttpResponse();
        try {
            shopBizServiceValidator.searchValid(tenantId,userId,request);
            SearchShopBizRequest searchShopBizRequest = SearchShopBizRequestConverter.toSearchShopBizRequest(request);
            List<ShopBO> shopBOS = shopBizService.searchByConditional(tenantId, userId, searchShopBizRequest);
            response.setStatus(StatusHelper.success());
            response.setData(ShopPageHttpDTOConverter.toShopPageHttpDTO(request.getPageIndex(),request.getPageSize(),shopBOS));
        } catch (BizException e) {
            response.setStatus(StatusHelper.failure(e.getErrorCode()));
        }catch (NullPointerException e){
            response.setStatus(StatusHelper.failure(-1,"未找到指定内容"));
        }

        return response;
    }

    @ApiOperation("查看单个门店详情")
    @GetMapping("/{businessNo}")
    public ShopDetailHttpResponse findByBusinessNo(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("门店业务号") @PathVariable String businessNo) {
        ShopDetailHttpResponse response=new ShopDetailHttpResponse();
        try{
            ShopBO shopBO= shopBizService.findByBusinessNo(tenantId,userId,businessNo);
            ShopDetailHttpDTO shopDetailHttpDTO = ShopDetailHttpDTOConverter.toShopDetailHttpResponse(shopBO);
            response.setStatus(StatusHelper.success());
            response.setData(shopDetailHttpDTO);
        }catch (NullPointerException e){
            response.setStatus(StatusHelper.failure(-1,"未找到指定内容"));
        }

        return response;
    }

    @ApiOperation("创建新门店")
    @PostMapping
    public ShopDetailHttpResponse create(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("门店信息") @Valid @RequestBody CreateShopHttpRequest request) {
        ShopDetailHttpResponse response=new ShopDetailHttpResponse();
        try {
            shopBizServiceValidator.createValid(tenantId,userId,request);
            SaveShopBizRequest saveShopBizRequest= SaveShopBizRequestConverter.toSaveShopBizRequest(request);
            ShopBO shopBO= shopBizService.create(tenantId,userId,saveShopBizRequest);
            response.setStatus(StatusHelper.success());
            response.setData(ShopDetailHttpDTOConverter.toShopDetailHttpResponse(shopBO));
        }catch (BizException e) {
            response.setStatus(StatusHelper.failure(e.getErrorCode()));
        }
        return response;
    }

    @ApiOperation("更新已有门店的信息")
    @PutMapping("/{businessNo}")
    public ShopDetailHttpResponse update(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("门店业务号") @PathVariable String businessNo,
            @ApiParam("门店信息") @Valid @RequestBody UpdateShopHttpRequest request) {
        ShopDetailHttpResponse response = new ShopDetailHttpResponse();
        try{
            shopBizServiceValidator.updateValid(tenantId,userId,businessNo,request);
            UpdateShopBizRequest updateShopBizRequest = UpdateShopBizRequestConverter.toUpdateShopBizRequest(request);
            ShopBO shopBO = shopBizService.update(tenantId, userId, businessNo, updateShopBizRequest);
            response.setStatus(StatusHelper.success());
            response.setData(ShopDetailHttpDTOConverter.toShopDetailHttpResponse(shopBO));
        }catch (BizException e) {
            response.setStatus(StatusHelper.failure(e.getErrorCode()));
        }catch (NullPointerException e){
            response.setStatus(StatusHelper.failure(-1,"请输入正确参数"));
        }
        return response;
    }

    @ApiOperation("开放一个已关闭的门店")
    @PostMapping("/{businessNo}/open")
    public ShopDetailHttpResponse open(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("门店业务号") @PathVariable String businessNo,
            @ApiParam("开放信息") @Valid @RequestBody OpenShopHttpRequest request) {
        ShopDetailHttpResponse response = new ShopDetailHttpResponse();
        try {
            shopBizServiceValidator.openValid(tenantId,userId,businessNo,request);
            OpenShopBizRequest openShopBizRequest = SwitchShopBizRequestConverter.toOpenShopBizRequest(request);
            ShopBO shopBO = shopBizService.open(tenantId, userId, businessNo, openShopBizRequest);
            response.setStatus(StatusHelper.success());
            response.setData(ShopDetailHttpDTOConverter.toShopDetailHttpResponse(shopBO));
        }catch (BizException e){
            response.setStatus(StatusHelper.failure(e.getErrorCode()));
        }catch (NullPointerException e){
            response.setStatus(StatusHelper.failure(-1,"请输入正确参数"));
        }
        return response;
    }

    @ApiOperation("关闭一个已开放的门店")
    @PostMapping("/{businessNo}/close")
    public ShopDetailHttpResponse close(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @PathVariable String businessNo,
            @ApiParam("关闭信息") @Valid @RequestBody CloseShopHttpRequest request) {
        ShopDetailHttpResponse response = new ShopDetailHttpResponse();
        try{
            shopBizServiceValidator.closeValid(tenantId,userId,businessNo,request);
            CloseShopBizRequest closeShopBizRequest = SwitchShopBizRequestConverter.toCloseShopBizRequest(request);
            ShopBO shopBO = shopBizService.close(tenantId, userId, businessNo, closeShopBizRequest);
            response.setStatus(StatusHelper.success());
            response.setData(ShopDetailHttpDTOConverter.toShopDetailHttpResponse(shopBO));
        }catch (BizException e){
            response.setStatus(StatusHelper.failure(e.getErrorCode()));
        }catch (NullPointerException e){
            response.setStatus(StatusHelper.failure(-1,"请输入正确参数"));
        }
        return response;
    }
}
