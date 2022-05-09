package com.meituan.catering.management.product.api.http.controller;

import com.meituan.catering.management.product.api.http.model.request.CreateProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.DisableProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.EnableProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.SearchProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.UpdateProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.response.ProductDetailHttpResponse;
import com.meituan.catering.management.product.api.http.model.response.ProductPageHttpResponse;
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
 * 商品管理Http API
 */
@Api(tags = "商品管理")
@RestController
@RequestMapping("/product")
public class ProductController {

    @ApiOperation("分页搜索商品的概要信息列表")
    @PostMapping("/search")
    public ProductPageHttpResponse searchForPage(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("搜索条件") @Valid @RequestBody SearchProductHttpRequest request) {
        return null;
    }

    @ApiOperation("查看单个商品详情")
    @GetMapping("/{id}")
    public ProductDetailHttpResponse findById(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("商品ID") @PathVariable Long id) {
        return null;
    }


    @ApiOperation("创建新商品")
    @PostMapping
    public ProductDetailHttpResponse create(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("创建请求") @Valid @RequestBody CreateProductHttpRequest request) {
        return null;
    }

    @ApiOperation("更新已有商品的信息")
    @PutMapping("/{id}")
    public ProductDetailHttpResponse update(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("商品ID") @PathVariable Long id,
            @ApiParam("商品信息") @Valid @RequestBody UpdateProductHttpRequest request) {
        return null;
    }


    @ApiOperation("上架一个已下架的商品")
    @PostMapping("/{id}/enable")
    public ProductDetailHttpResponse enable(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("商品ID") @PathVariable Long id,
            @ApiParam("上架信息") @Valid @RequestBody EnableProductHttpRequest request) {
        return null;
    }

    @ApiOperation("下架一个已上架的商品")
    @PostMapping("/{id}/disable")
    public ProductDetailHttpResponse disable(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("商品ID") @PathVariable Long id,
            @ApiParam("下架信息") @Valid @RequestBody DisableProductHttpRequest request) {
        return null;
    }
}
