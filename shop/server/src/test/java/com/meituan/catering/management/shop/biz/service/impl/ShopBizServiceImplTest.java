package com.meituan.catering.management.shop.biz.service.impl;

import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.dao.mapper.ShopMapper;
import com.meituan.catering.management.shop.dao.model.ShopDO;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.Objects;

public class ShopBizServiceImplTest {
    @Tested
    private ShopBizServiceImpl shopBizService;

    @Test
    public void testFindByBusinessNo(@Injectable TransactionTemplate transactionTemplate,
                                     @Injectable ShopMapper shopMapper){
        //录制
        new Expectations(){{
            shopMapper.findByBusinessNo(anyLong,anyLong,anyString);
            result= mockShopDO();
        }};

        //重放
        ShopBO shopBO = shopBizService.findByBusinessNo(1L, 1L, "123");

        //验证
        Assert.assertTrue(Objects.nonNull(shopBO));
    }

    private ShopDO mockShopDO() {
        ShopDO shopDO = new ShopDO();
        shopDO.setBusinessNo("123");
        shopDO.setName("123");
        shopDO.setBusinessType(BusinessTypeEnum.BARBECUE);
        shopDO.setManagementType(ManagementTypeEnum.ALLIANCE);
        shopDO.setContactTelephone("123");
        shopDO.setContactCellphone("123");
        shopDO.setContactName("123");
        shopDO.setContactAddress("123");
        shopDO.setOpeningHoursOpenTime("10:00");
        shopDO.setOpeningHoursCloseTime("18:00");
        shopDO.setBusinessArea("123");
        shopDO.setComment("123");
        shopDO.setEnabled(Boolean.TRUE);
        shopDO.setId(1L);
        shopDO.setTenantId(1L);
        shopDO.setVersion(1);
        shopDO.setCreatedBy(1l);
        shopDO.setCreatedAt(new Date());
        shopDO.setLastModifiedBy(1L);
        shopDO.setLastModifiedAt(new Date());
        return shopDO;
    }
}
