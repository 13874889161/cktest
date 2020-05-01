package com.lemon.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="TestReport对象", description="")
public class TestReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联报告id")
    private Integer reptId;
    
    @ApiModelProperty(value = "用例编号")
    private Integer caseId;

    @ApiModelProperty(value = "接口地址")
    private String requestUrl;

    @ApiModelProperty(value = "请求头")
    private String requestHeaders;

    @ApiModelProperty(value = "请求体数据")
    private String requestBody;

    @ApiModelProperty(value = "响应头")
    private String responseHeaders;

    @ApiModelProperty(value = "响应体")
    private String responseBody;

    @ApiModelProperty(value = "测试结果（通过 or 不通过）")
    private String passFlag;

    @TableField(exist=false) //此注解表示mybatis在操作此对象时不会往数据库操作这个字段
    @ApiModelProperty(value = "案例名称")
    private String caseName;
}
