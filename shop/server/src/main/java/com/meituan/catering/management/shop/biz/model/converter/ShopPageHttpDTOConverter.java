package com.meituan.catering.management.shop.biz.model.converter;


import com.meituan.catering.management.shop.api.http.model.dto.ShopPageHttpDTO;
import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.biz.model.response.SearchShopBizResponse;


import java.util.List;
import java.util.stream.Stream;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/17 10:08
 * @ClassName: ShopPageHttpDTOConverter
 */
public class ShopPageHttpDTOConverter {

    public static ShopPageHttpDTO toShopPageHttpDTO(SearchShopBizResponse response) {

        ShopPageHttpDTO shopPageHttpDTO = new ShopPageHttpDTO();
        shopPageHttpDTO.setPageIndex(response.getPageIndex());
        shopPageHttpDTO.setPageSize(response.getPageSize());
        shopPageHttpDTO.setTotalCount(response.getTotalCount());
        shopPageHttpDTO.setTotalPageCount(response.getTotalPageCount());

        List<ShopBO> shopBOS = response.getRecords();
        for (ShopBO shopBO : shopBOS) {
            shopPageHttpDTO.getRecords().add(buildRecord(shopBO));
        }
        return shopPageHttpDTO;
    }

    private static ShopPageHttpDTO.Record buildRecord(ShopBO shopBO) {
        if (shopBO == null) {
            return null;
        }
        ShopPageHttpDTO.Record record = new ShopPageHttpDTO.Record();
        record.setId(shopBO.getId());
        record.setTenantId(shopBO.getTenantId());
        record.setVersion(shopBO.getVersion());
        record.setBusinessNo(shopBO.getBusinessNo());
        record.setName(shopBO.getName());
        record.setManagementType(shopBO.getManagementType());
        record.setBusinessType(shopBO.getBusinessType());
        record.setBusinessArea(shopBO.getBusinessArea());
        record.setComment(shopBO.getComment());
        record.setEnabled(shopBO.getEnabled());

        record.getAuditing().setCreatedAt(shopBO.getAuditing().getCreatedAt());
        record.getAuditing().setCreatedBy(shopBO.getAuditing().getCreatedBy());
        record.getAuditing().setLastModifiedAt(shopBO.getAuditing().getLastModifiedAt());
        record.getAuditing().setLastModifiedBy(shopBO.getAuditing().getLastModifiedBy());

        record.getContact().setAddress(shopBO.getContactAddress());
        record.getContact().setCellphone(shopBO.getCellphone());
        record.getContact().setName(shopBO.getContactName());
        record.getContact().setTelephone(shopBO.getTelephone());

        record.getOpeningHours().setOpenTime(shopBO.getOpenTime());
        record.getOpeningHours().setCloseTime(shopBO.getCloseTime());

        return record;
    }
}
