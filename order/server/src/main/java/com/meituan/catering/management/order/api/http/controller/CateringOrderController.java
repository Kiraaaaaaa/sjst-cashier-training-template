package com.meituan.catering.management.order.api.http.controller;

import com.meituan.catering.management.order.api.http.model.request.AdjustCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.request.BillCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.request.PlaceCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.request.PrepareCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.request.ProduceCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.request.SearchCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.response.CateringOrderDetailHttpResponse;
import com.meituan.catering.management.order.api.http.model.response.CateringOrderPageHttpResponse;
import com.meituan.catering.management.order.remote.ShopRemoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 订单交易Http API
 */
@Api(tags = "订单交易")
@RestController
@RequestMapping("/order/catering")
public class CateringOrderController {

    @Resource
    private ShopRemoteService shopRemoteService;

    @ApiOperation("分页搜索订单的概要信息列表")
    @PostMapping("/search")
    public CateringOrderPageHttpResponse searchForPage(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("搜索条件") @Valid @RequestBody SearchCateringOrderHttpRequest request) {
        return null;
    }

    @ApiOperation("查看单个订单详情")
    @GetMapping("/{orderId}")
    public CateringOrderDetailHttpResponse findById(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("订单ID") @PathVariable Long orderId) {
        shopRemoteService.findByBusinessNo(tenantId,userId,"1234567890");
        return null;
    }


    @ApiOperation("创建新订单")
    @PostMapping
    public CateringOrderDetailHttpResponse place(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("下单信息") @Valid @RequestBody PlaceCateringOrderHttpRequest request) {
        return null;
    }

    @ApiOperation("针对一个订单进行制作")
    @PostMapping("/{orderId}/prepare")
    public CateringOrderDetailHttpResponse prepare(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("订单ID") @PathVariable Long orderId,
            @ApiParam("制作信息") @Valid @RequestBody PrepareCateringOrderHttpRequest request) {
        return null;
    }

    @ApiOperation("针对一个订单进行出餐")
    @PostMapping("/{orderId}/produce")
    public CateringOrderDetailHttpResponse produce(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("订单ID") @PathVariable Long orderId,
            @ApiParam("出餐信息") @Valid @RequestBody ProduceCateringOrderHttpRequest request) {
        return null;
    }

    @ApiOperation("针对一个订单进行加退菜")
    @PostMapping("/{orderId}/adjust")
    public CateringOrderDetailHttpResponse adjust(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("订单ID") @PathVariable Long orderId,
            @ApiParam("加退菜信息") @Valid @RequestBody AdjustCateringOrderHttpRequest request) {
        return null;
    }

    @ApiOperation("针对一个订单进行结账")
    @PostMapping("/{orderId}/bill")
    public CateringOrderDetailHttpResponse bill(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("订单ID") @PathVariable Long orderId,
            @ApiParam("结账信息") @Valid @RequestBody BillCateringOrderHttpRequest request) {
        return null;
    }
}
