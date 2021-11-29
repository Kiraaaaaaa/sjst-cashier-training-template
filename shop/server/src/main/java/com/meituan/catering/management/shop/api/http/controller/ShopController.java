package com.meituan.catering.management.shop.api.http.controller;

import com.meituan.catering.management.shop.api.http.model.request.CloseShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.CreateShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.OpenShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.SearchShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.UpdateShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.response.ShopDetailHttpResponse;
import com.meituan.catering.management.shop.api.http.model.response.ShopPageHttpResponse;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * 门店管理Http API
 */
@Api(tags = "门店管理")
@RestController
@RequestMapping("/shop")
public class ShopController {

    @ApiOperation("分页搜索门店的概要信息列表")
    @PostMapping("/search")
    public ShopPageHttpResponse searchForPage(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("搜索条件") @Valid @RequestBody SearchShopHttpRequest request) {
        ShopPageHttpResponse response = new ShopPageHttpResponse();
        response.setPageIndex(1);
        response.setPageSize(10);
        return response;
    }

    @ApiOperation("查看单个门店详情")
    @GetMapping("/{businessNo}")
    public ShopDetailHttpResponse findByBusinessNo(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("门店业务号") @PathVariable String businessNo) {
        return null;
    }

    @ApiOperation("创建新门店")
    @PostMapping
    @ResponseStatus(CREATED)
    public ShopDetailHttpResponse create(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("门店信息") @Valid @RequestBody CreateShopHttpRequest request) {
        return null;
    }

    @ApiOperation("更新已有门店的信息")
    @PutMapping("/{businessNo}")
    public ShopDetailHttpResponse update(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("门店业务号") @PathVariable String businessNo,
            @ApiParam("门店信息") @Valid @RequestBody UpdateShopHttpRequest request) {
        return null;
    }

    @ApiOperation("开放一个已关闭的门店")
    @PostMapping("/{businessNo}/open")
    public ShopDetailHttpResponse open(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("门店业务号") @PathVariable String businessNo,
            @ApiParam("开放信息") @Valid @RequestBody OpenShopHttpRequest request) {
        return null;
    }

    @ApiOperation("关闭一个已开放的门店")
    @PostMapping("/{businessNo}/close")
    public ShopDetailHttpResponse close(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @PathVariable String businessNo,
            @ApiParam("关闭信息") @Valid @RequestBody CloseShopHttpRequest request) {
        return null;
    }
}
