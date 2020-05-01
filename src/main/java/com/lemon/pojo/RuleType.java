package com.lemon.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ruleType对象", description="")
public class RuleType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型id")
    @TableId(value = "rule_type_id", type = IdType.AUTO)
    private Integer ruleTypeId;

    @ApiModelProperty(value = "类型名称")
    private String ruleTypeName;

    @ApiModelProperty(value = "id类型 1-验证类型 2-比较类型")
    private Integer type;

    @ApiModelProperty(value = "描述")
    private String description;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
