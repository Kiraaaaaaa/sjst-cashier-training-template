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





}
