package com.meituan.catering.management.product.api.http.controller;

import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.common.validation.annotation.RepeatSubmit;
import com.meituan.catering.management.common.validation.annotation.SqlCheck;
import com.meituan.catering.management.product.api.http.model.dto.ProductDetailHttpDTO;
import com.meituan.catering.management.product.api.http.model.dto.ProductPageHttpDTO;
import com.meituan.catering.management.product.api.http.model.request.CreateProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.DisableProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.EnableProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.SearchProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.UpdateProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.response.ProductDetailHttpResponse;
import com.meituan.catering.management.product.api.http.model.response.ProductPageHttpResponse;
import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.biz.model.converter.*;
import com.meituan.catering.management.product.biz.model.request.CreateProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.SearchProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.SwitchProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.UpdateProductBizRequest;
import com.meituan.catering.management.product.biz.model.response.SearchProductBizResponse;
import com.meituan.catering.management.product.biz.service.ProductBizService;
import com.meituan.catering.management.product.biz.validator.ProductBizServiceValidator;
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


/**
 * 商品管理Http API
 */
@Api(tags = "商品管理")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductBizService productBizService;

    @Resource
    private ProductBizServiceValidator validator;

    @ApiOperation("分页搜索商品的概要信息列表")
    @PostMapping("/search")
    public ProductPageHttpResponse searchForPage(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("搜索条件") @Valid @RequestBody SearchProductHttpRequest request) {
        validator.sqlValid(tenantId,userId,request);
        ProductPageHttpResponse response = new ProductPageHttpResponse();
        SearchProductBizRequest searchProductBizRequest = SearchProductBizRequestConverter.toSearchProductBizRequest(tenantId, userId, request);
        SearchProductBizResponse searchProductBizResponse = productBizService.searchForPage(searchProductBizRequest);
        ProductPageHttpDTO productPageHttpDTO = ProductPageHttpDTOConverter.toProductPageHttpDTO(searchProductBizResponse);
        response.setStatus(StatusHelper.success());
        response.setData(productPageHttpDTO);
        return response;
    }

    @ApiOperation("查看单个商品详情")
    @GetMapping("/{id}")
    public ProductDetailHttpResponse findById(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("商品ID") @PathVariable Long id) {
        validator.baseValid(tenantId,userId);
        ProductDetailHttpResponse response = new ProductDetailHttpResponse();
        ProductBO byId = productBizService.findById(tenantId, id);
        ProductDetailHttpDTO productDetailHttpDTO = ProductDetailHttpDTOConverter.toProductDetailHttpDTO(byId);
        response.setStatus(StatusHelper.success());
        response.setData(productDetailHttpDTO);
        return response;
    }

//    @RepeatSubmit
    @ApiOperation("创建新商品")
    @PostMapping("/create")
    public ProductDetailHttpResponse create(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("创建请求") @Valid @RequestBody CreateProductHttpRequest request) {
        validator.baseValid(tenantId,userId);
        ProductDetailHttpResponse response = new ProductDetailHttpResponse();
        CreateProductBizRequest createProductBizRequest = CreateProductBizRequestConverter.toCreateProductBizRequest(request);
        ProductBO productBO = productBizService.insert(tenantId, userId, createProductBizRequest);
        ProductDetailHttpDTO productDetailHttpDTO = ProductDetailHttpDTOConverter.toProductDetailHttpDTO(productBO);
        response.setStatus(StatusHelper.success());
        response.setData(productDetailHttpDTO);
        return response;
    }

//    @RepeatSubmit
    @ApiOperation("更新已有商品的信息")
    @PutMapping("/{id}")
    public ProductDetailHttpResponse update(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("商品ID") @PathVariable Long id,
            @ApiParam("商品信息") @Valid @RequestBody UpdateProductHttpRequest request) {
        validator.versionValid(tenantId,userId,id,request.getVersion());
        ProductDetailHttpResponse response = new ProductDetailHttpResponse();
        UpdateProductBizRequest updateProductBizRequest = UpdateProductBizRequestConverter.toUpdateProductBizRequest(request);
        ProductBO productBO = productBizService.update(tenantId, userId, id, updateProductBizRequest);
        ProductDetailHttpDTO productDetailHttpDTO = ProductDetailHttpDTOConverter.toProductDetailHttpDTO(productBO);
        response.setStatus(StatusHelper.success());
        response.setData(productDetailHttpDTO);
        return response;
    }

    @ApiOperation("上架一个已下架的商品")
    @PostMapping("/{id}/enable")
    public ProductDetailHttpResponse enable(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("商品ID") @PathVariable Long id,
            @ApiParam("上架信息") @Valid @RequestBody EnableProductHttpRequest request) {
        validator.versionValid(tenantId,userId,id,request.getVersion());
        ProductDetailHttpResponse response = new ProductDetailHttpResponse();
        SwitchProductBizRequest switchProductBizRequest = SwitchProductBizRequestConverter.toSwitchProductBizRequest(request);
        ProductBO productBO = productBizService.enabled(tenantId, userId, id, switchProductBizRequest);
        ProductDetailHttpDTO productDetailHttpDTO = ProductDetailHttpDTOConverter.toProductDetailHttpDTO(productBO);
        response.setStatus(StatusHelper.success());
        response.setData(productDetailHttpDTO);
        return response;
    }

    @ApiOperation("下架一个已上架的商品")
    @PostMapping("/{id}/disable")
    public ProductDetailHttpResponse disable(
            @ApiParam("租户ID") @RequestHeader Long tenantId,
            @ApiParam("用户ID") @RequestHeader Long userId,
            @ApiParam("商品ID") @PathVariable Long id,
            @ApiParam("下架信息") @Valid @RequestBody DisableProductHttpRequest request) {
        validator.versionValid(tenantId,userId,id,request.getVersion());
        ProductDetailHttpResponse response = new ProductDetailHttpResponse();
        SwitchProductBizRequest switchProductBizRequest = SwitchProductBizRequestConverter.toSwitchProductBizRequest(request);
        ProductBO productBO = productBizService.disabled(tenantId, userId, id, switchProductBizRequest);
        ProductDetailHttpDTO productDetailHttpDTO = ProductDetailHttpDTOConverter.toProductDetailHttpDTO(productBO);
        response.setStatus(StatusHelper.success());
        response.setData(productDetailHttpDTO);
        return response;
    }
}
