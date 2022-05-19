package com.meituan.catering.management.shop.biz.model.converter;



import com.meituan.catering.management.shop.api.http.model.dto.ShopPageHttpDTO;
import com.meituan.catering.management.shop.biz.model.ShopBO;


import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/17 10:08
 * @ClassName: ShopPageHttpDTOConverter
 */
public class ShopPageHttpDTOConverter {

    public static ShopPageHttpDTO toShopPageHttpDTO(Integer pageIndex, Integer pageSize,Integer totalCount,List<ShopBO> shopBOS){
        ShopPageHttpDTO shopPageHttpDTO = new ShopPageHttpDTO();
        shopPageHttpDTO.setPageIndex(pageIndex);
        shopPageHttpDTO.setPageSize(pageSize);
        shopPageHttpDTO.setTotalCount(totalCount);
        Integer totalPageCount = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        shopPageHttpDTO.setTotalPageCount(totalPageCount);

        List<ShopPageHttpDTO.Record> records = shopPageHttpDTO.getRecords();
        for (ShopBO shopBO : shopBOS) {
            ShopPageHttpDTO.Record record = ShopPageHttpDTOConverter.buildRecord(shopBO);
            records.add(record);
        }
        return shopPageHttpDTO;
    }

    private static ShopPageHttpDTO.Record buildRecord(ShopBO shopBO){
        if (shopBO==null){
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
