package com.meituan.catering.management.product.biz.service.impl;

import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.biz.model.converter.ProductBOConverter;
import com.meituan.catering.management.product.biz.model.request.CreateProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.UpdateProductBizRequest;
import com.meituan.catering.management.product.biz.service.ProductBizService;
import com.meituan.catering.management.product.dao.converter.ProductAccessoryDOConverter;
import com.meituan.catering.management.product.dao.converter.ProductDOConverter;
import com.meituan.catering.management.product.dao.converter.ProductMethodDOConverter;
import com.meituan.catering.management.product.dao.mapper.ProductAccessoryMapper;
import com.meituan.catering.management.product.dao.mapper.ProductMapper;
import com.meituan.catering.management.product.dao.mapper.ProductMethodMapper;
import com.meituan.catering.management.product.dao.model.ProductAccessoryDO;
import com.meituan.catering.management.product.dao.model.ProductDO;
import com.meituan.catering.management.product.dao.model.ProductMethodDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;

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
        return transactionTemplate.execute(status -> {
            ProductDO productDO = productMapper.findById(tenantId, id);
            List<ProductAccessoryDO> accessoryDOS = accessoryMapper.findAllByProductId(tenantId, id);
            List<ProductMethodDO> methodDOS = methodMapper.findAllByProductId(tenantId, id);
            return ProductBOConverter.toProductBO(productDO,methodDOS,accessoryDOS);
        });
    }

    @Override
    public Long insert(Long tenantId, Long userId, CreateProductBizRequest request){
        return transactionTemplate.execute(status ->{
            ProductDO productDO = ProductDOConverter.toProductDO(userId, tenantId, request);
            int insert = productMapper.insert(productDO);
            if (insert < 0){
                throw new IllegalStateException();
            }
            List<ProductAccessoryDO> accessoryDOS = ProductAccessoryDOConverter.toProductAccessoryDO(tenantId, productDO.getId(), request);
            if (accessoryDOS.size()!=0){
                accessoryMapper.insert(accessoryDOS);
            }
            List<ProductMethodDO> methodDOS = ProductMethodDOConverter.toProductMethodDO(tenantId, productDO.getId(), request);
            if (methodDOS.size()!=0){
                methodMapper.insert(methodDOS);
            }
            return productDO.getId();
        });
    }

    @Override
    public Long update(Long tenantid, Long userId, Long id, UpdateProductBizRequest request) {
        return transactionTemplate.execute(status ->{
            ProductDO productDO = ProductDOConverter.toProductDO(userId, tenantid, id, request);
            int row = productMapper.updateSelective(productDO);
            if (row<=0){
                throw new NullPointerException();
            }
            List<ProductAccessoryDO> accessoryDOS = ProductAccessoryDOConverter.toProductAccessoryDO(tenantid, id, request);
            accessoryMapper.deleteByProductId(tenantid,id);
            if (accessoryDOS.size()!=0){
                accessoryMapper.insert(accessoryDOS);
            }
            List<ProductMethodDO> methodDOS = ProductMethodDOConverter.toProductMethodDO(tenantid, id, request);
            methodMapper.deleteByProductId(tenantid,id);
            if (accessoryDOS.size()!=0){
                methodMapper.insert(methodDOS);
            }
            return id;
        });
    }
}
