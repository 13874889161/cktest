package com.lemon.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="ReportRecord对象", description="")
public class ReportRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "rept_id", type = IdType.AUTO)
    private Integer reptId;
    
    @ApiModelProperty(value = "项目id")
    private Integer projectId;
    
    @ApiModelProperty(value = "报告名称")
    private String reptName;
   
    
    @ApiModelProperty(value = "创建人")
    private Integer createUser;
   
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill=FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
