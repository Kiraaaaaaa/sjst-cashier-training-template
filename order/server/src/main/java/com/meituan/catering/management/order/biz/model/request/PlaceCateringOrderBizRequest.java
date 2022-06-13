package com.meituan.catering.management.order.biz.model.request;

import com.meituan.catering.management.order.api.http.model.request.PlaceCateringOrderHttpRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/1 15:02
 * @ClassName: PlaceCateringOrderBizRequest
 */
@Data
public class PlaceCateringOrderBizRequest {

    private Long tenantId;

    private Long userId;

    private String shopBusinessNo;

    private String tableNo;

    private Integer customerCount;

    private BigDecimal totalPrice;

    private String comment;

    private final List<Item> items = new LinkedList<>();

    @Data
    public static class Item {

        private String seqNo;

        private BigDecimal quantity;

        private Long productId;

        private Long productMethodId;

        private final List<Accessory> accessories = new LinkedList<>();

        @Data
        public static class Accessory {

            private String seqNo;

            private BigDecimal quantity;

            private Long productAccessoryId;

        }
    }
}
