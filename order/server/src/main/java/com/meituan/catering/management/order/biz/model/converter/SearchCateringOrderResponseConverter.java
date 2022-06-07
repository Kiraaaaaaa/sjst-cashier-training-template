package com.meituan.catering.management.order.biz.model.converter;

import com.meituan.catering.management.order.api.http.model.dto.CateringOrderPageHttpDTO;
import com.meituan.catering.management.order.biz.model.CateringOrderBO;
import com.meituan.catering.management.order.biz.model.response.SearchCateringOrderBizResponse;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/30 17:30
 * @ClassName: SearchCateringOrderBizResponseConverter
 */
public class SearchCateringOrderBizResponseConverter {

    public static SearchCateringOrderBizResponse toSearchCateringOrderBizResponse(Integer pageIndex, Integer pageSize, Integer totalCount, List<CateringOrderDO> orderDOS){

        SearchCateringOrderBizResponse cateringOrderBizResponse = new SearchCateringOrderBizResponse();
        cateringOrderBizResponse.setPageIndex(pageIndex);
        cateringOrderBizResponse.setPageSize(pageSize);
        cateringOrderBizResponse.setTotalCount(totalCount);
        cateringOrderBizResponse.setTotalPageCount(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);

        List<SearchCateringOrderBizResponse.Record> records = cateringOrderBizResponse.getRecords();
        for (CateringOrderDO orderDO : orderDOS) {
            records.add(buildRecord(orderDO));
        }
        return cateringOrderBizResponse;
    }

    private static SearchCateringOrderBizResponse.Record buildRecord(CateringOrderDO orderDO) {

        SearchCateringOrderBizResponse.Record record = new SearchCateringOrderBizResponse.Record();
        record.setId(orderDO.getId());
        record.setTenantId(orderDO.getTenantId());
        record.setVersion(orderDO.getVersion());
        record.setStatus(orderDO.getStatus());
        record.setTableNo(orderDO.getTableNo());
        record.setCustomerCount(orderDO.getCustomerCount());
        record.setTotalPrice(orderDO.getTotalPrice());
        record.setComment(orderDO.getComment());

        record.getAuditing().setLastModifiedBy(orderDO.getLastModifiedBy());
        record.getAuditing().setCreatedAt(orderDO.getCreatedAt());
        record.getAuditing().setCreatedBy(orderDO.getCreatedBy());
        record.getAuditing().setLastModifiedAt(orderDO.getLastModifiedAt());

        record.getShopSnapshotOnPlace().setName(orderDO.getShopNameOnPlace());
        record.getShopSnapshotOnPlace().setId(orderDO.getShopId());
        record.getShopSnapshotOnPlace().setBusinessNo(orderDO.getShopBusinessNo());
        return record;
    }
}
