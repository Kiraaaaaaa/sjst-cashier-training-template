package com.meituan.catering.management.product.biz.service.impl;

import com.google.common.collect.Lists;
import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.biz.model.converter.ProductBOConverter;
import com.meituan.catering.management.product.biz.model.converter.SearchProductBizResponseConverter;
import com.meituan.catering.management.product.biz.model.request.CreateProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.SearchProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.SwitchProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.UpdateProductBizRequest;
import com.meituan.catering.management.product.biz.model.response.SearchProductBizResponse;
import com.meituan.catering.management.product.biz.service.ProductBizService;
import com.meituan.catering.management.product.dao.converter.ProductAccessoryDOConverter;
import com.meituan.catering.management.product.dao.converter.ProductDOConverter;
import com.meituan.catering.management.product.dao.converter.ProductMethodDOConverter;
import com.meituan.catering.management.product.dao.converter.SearchProductDataRequestConverter;
import com.meituan.catering.management.product.dao.mapper.ProductAccessoryMapper;
import com.meituan.catering.management.product.dao.mapper.ProductMapper;
import com.meituan.catering.management.product.dao.mapper.ProductMethodMapper;
import com.meituan.catering.management.product.dao.model.ProductAccessoryDO;
import com.meituan.catering.management.product.dao.model.ProductDO;
import com.meituan.catering.management.product.dao.model.ProductMethodDO;
import com.meituan.catering.management.product.dao.model.request.SearchProductDataRequest;
import io.micrometer.core.instrument.search.Search;
import lombok.experimental.Helper;
import org.apache.catalina.webresources.ExtractingRoot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.support.GroovyWebApplicationContext;

import javax.annotation.Resource;
import java.util.*;

/**
 * {@link ProductBizService}的核心实现
 */
@Service
public class ProductBizServiceImpl implements ProductBizService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductAccessoryMapper accessoryMapper;

    @Resource
    private ProductMethodMapper methodMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public ProductBO findById(Long tenantId, Long id) {
        ProductDO productDO = productMapper.findById(tenantId, id);
        List<ProductAccessoryDO> accessoryDOS = accessoryMapper.findAllByProductId(tenantId, id);
        List<ProductMethodDO> methodDOS = methodMapper.findAllByProductId(tenantId, id);
        return ProductBOConverter.toProductBO(productDO,methodDOS,accessoryDOS);
    }

    @Override
    public ProductBO insert(Long tenantId, Long userId, CreateProductBizRequest request){
        return transactionTemplate.execute(status ->{
            ProductDO productDO = ProductDOConverter.toProductDO(userId, tenantId, request);
            int insert = productMapper.insert(productDO);
            if (insert == 0){
                throw new BizException(ErrorCode.PARAM_ERROR);
            }
            List<ProductAccessoryDO> accessoryDOS = ProductAccessoryDOConverter.toProductAccessoryDO(tenantId, productDO.getId(), request);
            if (!accessoryDOS.isEmpty()){
                accessoryMapper.batchInsert(accessoryDOS);
            }
            List<ProductMethodDO> methodDOS = ProductMethodDOConverter.toProductMethodDO(tenantId, productDO.getId(), request);
            if (!methodDOS.isEmpty()){
                methodMapper.batchInsert(methodDOS);
            }
            ProductDO productDOLater = productMapper.findById(tenantId, productDO.getId());
            List<ProductAccessoryDO> accessoryDOLater = accessoryMapper.findAllByProductId(tenantId, productDO.getId());
            List<ProductMethodDO> methodDOLater = methodMapper.findAllByProductId(tenantId, productDO.getId());
            return ProductBOConverter.toProductBO(productDOLater,methodDOLater,accessoryDOLater);
        });
    }

    @Override
    public ProductBO update(Long tenantId, Long userId, Long id, UpdateProductBizRequest request) {
        return transactionTemplate.execute(status ->{
            Boolean flag = false;
            ProductDO productDO = ProductDOConverter.toProductDO(userId, tenantId, id, request);
            int row = productMapper.updateSelective(productDO);
            if (row<=0){
                throw new BizException(ErrorCode.UPDATE_ERROR);
            }
            List<ProductAccessoryDO> accessoryDOS = ProductAccessoryDOConverter.toProductAccessoryDO(tenantId, id, request);
            List<ProductAccessoryDO> accessoryDOSAfter = Lists.newArrayList(accessoryDOS);
            List<ProductAccessoryDO> accessoryDOList = accessoryMapper.findAllByProductId(tenantId,id);
            for (ProductAccessoryDO accessoryDO : accessoryDOList) {
                Long accessoryDOId = accessoryDO.getId();
                for (int i = 0; i < accessoryDOS.size(); i++) {
                    if (accessoryDOId!=null && accessoryDOId.equals(accessoryDOS.get(i).getId())){
                        accessoryMapper.updateById(accessoryDOS.get(i));
                        accessoryDOSAfter.remove(i);
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    accessoryMapper.deleteById(tenantId,accessoryDOId);
                }
            }
            if (!accessoryDOSAfter.isEmpty()){
                accessoryMapper.batchInsert(accessoryDOSAfter);
            }
            flag = false;
            List<ProductMethodDO> methodDOS = ProductMethodDOConverter.toProductMethodDO(tenantId, id, request);
            List<ProductMethodDO> methodDOSAfter = Lists.newArrayList(methodDOS);
            List<ProductMethodDO> methodDOList = methodMapper.findAllByProductId(tenantId, id);
            for (ProductMethodDO methodDO : methodDOList) {
                Long methodDOId = methodDO.getId();
                for (int i = 0; i < methodDOS.size(); i++) {
                    if (methodDOId!=null && methodDOId.equals(methodDOS.get(i).getId())){
                        methodMapper.updateById(methodDOS.get(i));
                        methodDOSAfter.remove(i);
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    methodMapper.deleteById(tenantId,methodDOId);
                }
            }
            if (!methodDOSAfter.isEmpty()){
                methodMapper.batchInsert(methodDOSAfter);
            }

            ProductDO productDOLater = productMapper.findById(tenantId, id);
            List<ProductAccessoryDO> accessoryDOLater = accessoryMapper.findAllByProductId(tenantId, id);
            List<ProductMethodDO> methodDOLater = methodMapper.findAllByProductId(tenantId, id);
            return ProductBOConverter.toProductBO(productDOLater,methodDOLater,accessoryDOLater);
        });
    }

    @Override
    public ProductBO enabled(Long tenantId, Long userId, Long id, SwitchProductBizRequest request) {
        ProductDO productDO = ProductDOConverter.toProductDO(userId, tenantId, id, request);
        int row = productMapper.updateSelective(productDO);
        if (row == 0){
            throw new BizException(ErrorCode.UPDATE_ERROR);
        }
        ProductDO productDOLater = productMapper.findById(tenantId, id);
        List<ProductAccessoryDO> accessoryDOLater = accessoryMapper.findAllByProductId(tenantId, id);
        List<ProductMethodDO> methodDOLater = methodMapper.findAllByProductId(tenantId, id);
        return ProductBOConverter.toProductBO(productDOLater,methodDOLater,accessoryDOLater);
    }

    @Override
    public ProductBO disabled(Long tenantId, Long userId, Long id, SwitchProductBizRequest request) {
        ProductDO productDO = ProductDOConverter.toProductDO(userId, tenantId, id, request);
        int row = productMapper.updateSelective(productDO);
        if (row == 0){
            throw new BizException(ErrorCode.UPDATE_ERROR);
        }
        ProductDO productDOLater = productMapper.findById(tenantId, id);
        List<ProductAccessoryDO> accessoryDOLater = accessoryMapper.findAllByProductId(tenantId, id);
        List<ProductMethodDO> methodDOLater = methodMapper.findAllByProductId(tenantId, id);
        return ProductBOConverter.toProductBO(productDOLater,methodDOLater,accessoryDOLater);
    }

    @Override
    public SearchProductBizResponse searchForPage(SearchProductBizRequest request) {
        SearchProductDataRequest searchProductDataRequest = SearchProductDataRequestConverter.toSearchProductDataRequest(request);
        int totalCount = productMapper.countForPage(searchProductDataRequest);
        if (totalCount == 0){
            return SearchProductBizResponseConverter.toSearchProductBizResponse(request.getPageIndex(),request.getPageSize(),totalCount,null);
        }
        List<ProductDO> productDOS = productMapper.searchForPage(searchProductDataRequest);

        List<ProductBO> productBOS = ProductBOConverter.toProductBOS(productDOS);

        return SearchProductBizResponseConverter.toSearchProductBizResponse(request.getPageIndex(), request.getPageSize(), totalCount, productBOS);
    }
}
