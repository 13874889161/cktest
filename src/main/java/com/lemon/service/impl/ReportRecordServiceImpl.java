package com.lemon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lemon.common.PageRequest;
import com.lemon.common.PageResult;
import com.lemon.common.ReportVO;
import com.lemon.common.TestReportRequest;
import com.lemon.mapper.ReportRecordMapper;
import com.lemon.pojo.ReportRecord;
import com.lemon.service.ReportRecordService;
import com.lemon.utils.PageUtil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
@Service
public class ReportRecordServiceImpl extends ServiceImpl<ReportRecordMapper, ReportRecord> implements ReportRecordService {
	@Autowired
	ReportRecordMapper reportRecordMapper;
	
	@Override
	public List<ReportVO> getReportList(Integer projectId) {
		return reportRecordMapper.getReportList(projectId);
	}

	@Override
	public PageResult getReportListtest(PageRequest pageRequest,Integer projectId) {
		// TODO Auto-generated method stub
		return PageUtil.getPageResult(pageRequest, getPageInfo(pageRequest, projectId));
	}
	
	
	
    /**
     * 动态sql+分页
     */
	@Override
	public PageResult getReportList2(PageRequest pageRequest, TestReportRequest testReportRequest) {
		return PageUtil.getPageResult(pageRequest, getPageInfo2(pageRequest, testReportRequest));
		//return reportRecordMapper.;
	}
	/**
     * 调用分页插件完成分页1
     * @param pageQuery
     * @return
     */
    private PageInfo<ReportVO> getPageInfo(PageRequest pageRequest,Integer projectId) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<ReportVO> reportVOMenus=reportRecordMapper.getReportList(projectId);
       // reportVOMenus = reportVOList;
        return new PageInfo<ReportVO>(reportVOMenus);
    }
    
    /**
     * 调用分页插件完成分页
     * @param testReportRequest 动态sql条件集合
     * @param pageRequest 分页参数
     * @return
     */
    private PageInfo<ReportVO> getPageInfo2(PageRequest pageRequest,TestReportRequest testReportRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<ReportVO> reportVOMenus = reportRecordMapper.getReportList2(testReportRequest);
        return new PageInfo<ReportVO>(reportVOMenus);
    }

	/**
	 * 查询指定报告id的测试报告列表
	 */
    @Override
	public List<ReportVO> getReportListByReptId(Integer reptId) {
		return reportRecordMapper.getReportListByReptId(reptId);
	}
	
	

}
