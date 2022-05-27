package com.meituan.catering.management.product.biz.service.impl;

import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.biz.model.request.CreateProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.SearchProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.SwitchProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.UpdateProductBizRequest;
import com.meituan.catering.management.product.biz.model.response.SearchProductBizResponse;
import com.meituan.catering.management.product.dao.mapper.ProductAccessoryMapper;
import com.meituan.catering.management.product.dao.mapper.ProductMapper;
import com.meituan.catering.management.product.dao.mapper.ProductMethodMapper;
import com.meituan.catering.management.product.dao.model.ProductAccessoryDO;
import com.meituan.catering.management.product.dao.model.ProductDO;
import com.meituan.catering.management.product.dao.model.ProductMethodDO;
import com.meituan.catering.management.product.dao.model.request.SearchProductDataRequest;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/25 10:43
 * @ClassName: ProductBizServiceImplTest
 */

@RunWith(JMockit.class)
public class ProductBizServiceImplTest {
    @Tested
    private ProductBizServiceImpl productBizService;

    @Test
    public void testFindById(@Injectable ProductMapper productMapper,
                             @Injectable ProductAccessoryMapper accessoryMapper,
                             @Injectable ProductMethodMapper methodMapper,
                             @Injectable TransactionTemplate template){
        new Expectations(){{
            productMapper.findById(anyLong,anyLong);
            result = mockProductDO();
            accessoryMapper.findAllByProductId(anyLong,anyLong);
            result = mockAccessoryDOS();
            methodMapper.findAllByProductId(anyLong,anyLong);
            result = mockMethodDOS();
        }};
        ProductBO productBO = productBizService.findById(1L, 1L);
        Assert.assertNotNull("查询失败",productBO);
        System.out.println(productBO);
    }

    @Test
    public void testSearchForPage(@Injectable ProductMapper productMapper,
                                  @Injectable ProductAccessoryMapper accessoryMapper,
                                  @Injectable ProductMethodMapper methodMapper,
                                  @Injectable TransactionTemplate template,
                                  @Injectable SearchProductDataRequest request,
                                  @Injectable SearchProductBizRequest requestBiz) {
        new Expectations(){{
            productMapper.countForPage(request);
            result = 5;
            productMapper.searchForPage(request);
            result = mockProductDOS();
        }};

        SearchProductBizResponse searchProductBizResponse = productBizService.searchForPage(requestBiz);
        Assert.assertNotNull(searchProductBizResponse);
        System.out.println(searchProductBizResponse);
    }


    @Test
    public void testInsert(@Injectable ProductMapper productMapper,
                           @Injectable ProductAccessoryMapper accessoryMapper,
                           @Injectable ProductMethodMapper methodMapper,
                           @Injectable TransactionTemplate template,
                           @Injectable ProductDO productDO,
                           @Injectable ProductMethodDO methodDO,
                           @Injectable ProductAccessoryDO accessoryDO,
                           @Injectable CreateProductBizRequest request){
        template.execute(status->{
            Long tenantId = 1L;
            Long userId = 1L;
            new Expectations(){{
                productMapper.insert(productDO);
                result = 1;
                accessoryMapper.insert(accessoryDO);
                result = 1;
                methodMapper.insert(methodDO);
                result = 1;
            }};

            ProductBO productBO = productBizService.insert(tenantId, userId, request);
            System.out.println(productDO);
            return productBO;
        });
    }

    @Test
    public void testUpdate(@Injectable ProductMapper productMapper,
                           @Injectable ProductAccessoryMapper accessoryMapper,
                           @Injectable ProductMethodMapper methodMapper,
                           @Injectable TransactionTemplate template,
                           @Injectable ProductDO productDO,
                           @Injectable List<ProductMethodDO> methodDOS,
                           @Injectable List<ProductAccessoryDO> accessoryDOS,
                           @Injectable UpdateProductBizRequest request){
        template.execute(status->{
            Long tenantId = 1L;
            Long userId = 1L;
            Long productId = 1L;
            new Expectations(){{
                productMapper.updateSelective(productDO);
                result = 1;
                accessoryMapper.deleteByProductId(tenantId,productId);
                result = 1;
                accessoryMapper.batchInsert(accessoryDOS);
                result = 1;
                methodMapper.deleteByProductId(tenantId,productId);
                result = 1;
                methodMapper.batchInsert(methodDOS);
                result = 1;

            }};
            ProductBO productBO = productBizService.update(tenantId, userId, productId,request);
            System.out.println(productBO);
            return productBO;
        });

    }

    @Test
    public void testEnabled(@Injectable ProductMapper productMapper,
                            @Injectable SwitchProductBizRequest request,
                            @Injectable TransactionTemplate template,
                            @Injectable ProductAccessoryMapper accessoryMapper,
                            @Injectable ProductMethodMapper methodMapper,
                            @Injectable ProductDO productDO){

        new Expectations(){{
           productMapper.updateSelective(productDO);
           result = 1;
        }};

        ProductBO productBO= productBizService.enabled(1L, 1L, 1L, request);
        System.out.println(productBO);
    }

    private ProductDO mockProductDO(){
        ProductDO productDO = new ProductDO();
        productDO.setName("productDO");
        productDO.setUnitPrice(new BigDecimal(10));
        productDO.setUnitOfMeasure("元");
        productDO.setMinSalesQuantity(new BigDecimal(1));
        productDO.setIncreaseSalesQuantity(new BigDecimal(1));
        productDO.setDescription("hello");
        productDO.setEnabled(true);
        productDO.setId(1L);
        productDO.setTenantId(1L);
        productDO.setVersion(1);
        productDO.setCreatedBy(1L);
        productDO.setCreatedAt(new Date());
        productDO.setLastModifiedBy(1L);
        productDO.setLastModifiedAt(new Date());

        return productDO;
    }

    private List<ProductDO> mockProductDOS(){
        List<ProductDO> productDOS = new ArrayList<>();
        ProductDO productDO = new ProductDO();
        productDO.setName("productDO");
        productDO.setUnitPrice(new BigDecimal(10));
        productDO.setUnitOfMeasure("元");
        productDO.setMinSalesQuantity(new BigDecimal(1));
        productDO.setIncreaseSalesQuantity(new BigDecimal(1));
        productDO.setDescription("hello");
        productDO.setEnabled(true);
        productDO.setId(1L);
        productDO.setTenantId(1L);
        productDO.setVersion(1);
        productDO.setCreatedBy(1L);
        productDO.setCreatedAt(new Date());
        productDO.setLastModifiedBy(1L);
        productDO.setLastModifiedAt(new Date());
        productDOS.add(productDO);

        return productDOS;
    }

    private ProductMethodDO mockMethodDO(){
        ProductMethodDO productMethodDO = new ProductMethodDO();
        productMethodDO.setId(1L);
        productMethodDO.setProductId(1L);
        productMethodDO.setTenantId(1L);
        productMethodDO.setName("methodDO");
        productMethodDO.setGroupName("methodGroup");

        return productMethodDO;
    }

    private List<ProductMethodDO> mockMethodDOS(){
        List<ProductMethodDO> productMethodDOS = new ArrayList<>();
        ProductMethodDO productMethodDO = new ProductMethodDO();
        productMethodDO.setId(1L);
        productMethodDO.setProductId(1L);
        productMethodDO.setTenantId(1L);
        productMethodDO.setName("methodDO");
        productMethodDO.setGroupName("methodGroup");
        productMethodDOS.add(productMethodDO);
        return productMethodDOS;
    }

    private ProductAccessoryDO mockAccessoryDO(){
        ProductAccessoryDO productAccessoryDO = new ProductAccessoryDO();
        productAccessoryDO.setId(1L);
        productAccessoryDO.setProductId(1L);
        productAccessoryDO.setTenantId(1L);
        productAccessoryDO.setName("accessoryDO");
        productAccessoryDO.setGroupName("accessoryGroup");
        productAccessoryDO.setUnitPrice(new BigDecimal(1));
        productAccessoryDO.setUnitOfMeasure("元");

        return productAccessoryDO;
    }

    private List<ProductAccessoryDO> mockAccessoryDOS(){
        List<ProductAccessoryDO> productAccessoryDOS = new ArrayList<>();
        ProductAccessoryDO productAccessoryDO = new ProductAccessoryDO();
        productAccessoryDO.setId(1L);
        productAccessoryDO.setProductId(1L);
        productAccessoryDO.setTenantId(1L);
        productAccessoryDO.setName("accessoryDO");
        productAccessoryDO.setGroupName("accessoryGroup");
        productAccessoryDO.setUnitPrice(new BigDecimal(1));
        productAccessoryDO.setUnitOfMeasure("元");
        productAccessoryDOS.add(productAccessoryDO);
        return productAccessoryDOS;
    }
}
