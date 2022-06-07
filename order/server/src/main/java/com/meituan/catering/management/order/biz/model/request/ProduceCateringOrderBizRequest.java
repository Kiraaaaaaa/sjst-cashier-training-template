package com.meituan.catering.management.order.biz.model.request;

import com.meituan.catering.management.order.api.http.model.request.ProduceCateringOrderHttpRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/5 15:08
 * @ClassName: ProduceCateringOrderBizRequest
 */
@Data
public class ProduceCateringOrderBizRequest {

    private Long tenantId;

    private Long userId;

    private Long orderId;

    private Date lastModifiedAt;

    private Integer version;

    private final List<Item> items = new LinkedList<>();

    @Data
    public static class Item {

        private String seqNo;

        private Integer version;

        private BigDecimal quantityOnProduce;

        private final List<Accessory> accessories = new LinkedList<>();

        @Data
        public static class Accessory {

            private String seqNo;

            private Integer version;

            private BigDecimal quantityOnProduce;

        }
    }
}
