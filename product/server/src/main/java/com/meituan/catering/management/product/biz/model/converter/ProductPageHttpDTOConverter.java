package com.meituan.catering.management.product.biz.model.converter;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.common.model.biz.AuditingBO;
import com.meituan.catering.management.product.api.http.model.dto.ProductPageHttpDTO;
import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.biz.model.response.SearchProductBizResponse;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 17:22
 * @ClassName: ProductPageHttpDTOConverter
 */
public class ProductPageHttpDTOConverter {
    public static ProductPageHttpDTO toProductPageHttpDTO(SearchProductBizResponse response){
        ProductPageHttpDTO productPageHttpDTO = new ProductPageHttpDTO();
        productPageHttpDTO.setPageSize(response.getPageSize());
        productPageHttpDTO.setTotalCount(response.getTotalCount());
        productPageHttpDTO.setPageIndex(response.getPageIndex());
        Integer totalPageCount = response.getTotalCount()%response.getPageSize()==0?
                response.getTotalCount()/response.getPageSize():
                response.getTotalCount()/response.getPageSize()+1;
        productPageHttpDTO.setTotalPageCount(totalPageCount);

        List<ProductBO> productBOS = response.getProductBOS();
        for (ProductBO productBO : productBOS) {
            ProductPageHttpDTO.Record record = new ProductPageHttpDTO.Record();
            record.setId(productBO.getId());
            record.setTenantId(productBO.getTenantId());
            record.setVersion(productBO.getVersion());
            record.setName(productBO.getName());
            record.setUnitPrice(productBO.getUnitPrice());
            record.setUnitOfMeasure(productBO.getUnitOfMeasure());
            record.setMinSalesQuantity(productBO.getMinSalesQuantity());
            record.setIncreaseSalesQuantity(productBO.getIncreaseSalesQuantity());
            record.setDescription(productBO.getDescription());
            record.setEnabled(productBO.getEnabled());
            List<ProductBO.MethodGroup> methodGroups = productBO.getMethodGroups();
            for (ProductBO.MethodGroup methodGroup : methodGroups) {
                ProductPageHttpDTO.Record.MethodGroup methodDTO = buildMethod(methodGroup);
                record.getMethodGroups().add(methodDTO);
                methodDTO = null;
            }
            List<ProductBO.AccessoryGroup> accessoryGroups = productBO.getAccessoryGroups();
            for (ProductBO.AccessoryGroup accessoryGroup : accessoryGroups) {
                ProductPageHttpDTO.Record.AccessoryGroup accessoryDTO = buildAccessory(accessoryGroup);
                record.getAccessoryGroups().add(accessoryDTO);
                accessoryDTO = null;
            }

            AuditingHttpModel auditing = record.getAuditing();
            auditing.setLastModifiedBy(productBO.getAuditing().getLastModifiedBy());
            auditing.setCreatedBy(productBO.getAuditing().getCreatedBy());
            auditing.setCreatedAt(productBO.getAuditing().getCreatedAt());
            auditing.setLastModifiedAt(productBO.getAuditing().getLastModifiedAt());

            productPageHttpDTO.getRecords().add(record);
            record = null;
        }

        return productPageHttpDTO;
    }



    private static ProductPageHttpDTO.Record.AccessoryGroup buildAccessory(ProductBO.AccessoryGroup accessoryBiz){

        ProductPageHttpDTO.Record.AccessoryGroup accessoryDTO = new ProductPageHttpDTO.Record.AccessoryGroup();
        accessoryDTO.setName(accessoryBiz.getName());
        List<ProductBO.AccessoryGroup.Option> options = accessoryBiz.getOptions();
        for (ProductBO.AccessoryGroup.Option option : options) {
            ProductPageHttpDTO.Record.AccessoryGroup.Option optionDTO = new ProductPageHttpDTO.Record.AccessoryGroup.Option();
            optionDTO.setId(option.getId());
            optionDTO.setName(option.getName());
            optionDTO.setUnitPrice(option.getUnitPrice());
            optionDTO.setUnitOfMeasure(option.getUnitOfMeasure());
            accessoryDTO.getOptions().add(optionDTO);
            optionDTO = null;
        }
        return accessoryDTO;
    }
    private static ProductPageHttpDTO.Record.MethodGroup buildMethod(ProductBO.MethodGroup methodBiz){
        ProductPageHttpDTO.Record.MethodGroup methodDTO = new ProductPageHttpDTO.Record.MethodGroup();
        methodDTO.setName(methodBiz.getName());
        List<ProductBO.MethodGroup.Option> options = methodBiz.getOptions();
        for (ProductBO.MethodGroup.Option option : options) {
            ProductPageHttpDTO.Record.MethodGroup.Option optionDTO = new ProductPageHttpDTO.Record.MethodGroup.Option();
            optionDTO.setId(option.getId());
            optionDTO.setName(option.getName());
            methodDTO.getOptions().add(optionDTO);
            optionDTO = null;
        }
        return methodDTO;
    }


}
