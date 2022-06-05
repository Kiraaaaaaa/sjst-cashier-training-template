package com.meituan.catering.management.order.biz.model.request;

import com.meituan.catering.management.order.api.http.model.request.PrepareCateringOrderHttpRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/3 9:02
 * @ClassName: PrepareCateringOrderBizRequest
 */
@Data
public class PrepareCateringOrderBizRequest {

    private Long tenantId;

    private Long orderId;

    private Integer version;

    private final List<Item> items = new LinkedList<>();


    @Data
    public static class Item {

        private String seqNo;

        private Integer version;
    }

}
