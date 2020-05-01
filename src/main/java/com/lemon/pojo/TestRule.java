package com.lemon.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

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
@ApiModel(value="TestRule对象", description="")
public class TestRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用例编号")
    private Integer caseId;

    @ApiModelProperty(value = "验证方式id")
    private Integer rule;

    @ApiModelProperty(value = "验证表达式")
    private String expression;

    @ApiModelProperty(value = "匹配规则id")
    private Integer operator;

    @ApiModelProperty(value = "期望值")
    private String expected;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;

    @TableField(fill=FieldFill.INSERT) 
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    //运行时临时填写的参数值，不存储在数据库中
    @TableField(exist=false) //此注解表示mybatis在操作此对象时不会往数据库操作这个字段
    @ApiModelProperty(value = "验证方式名称")
    private String ruleName;
    
    //运行时临时填写的参数值，不存储在数据库中
    @TableField(exist=false) //此注解表示mybatis在操作此对象时不会往数据库操作这个字段
    @ApiModelProperty(value = "匹配规则名称")
    private String operatorName;
}
