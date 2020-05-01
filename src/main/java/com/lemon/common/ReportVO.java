package com.lemon.common;

import java.util.List;

import com.lemon.pojo.ReportRecord;
import com.lemon.pojo.TestReport;

import lombok.Data;

@Data
public class ReportVO extends ReportRecord{
	
	private int totalCase;//总数
	private int successes;//成功数
	private int failures;//失败数
	private String username;
	private String suiteName;
	private List<TestReport> testReports ;//测试报告
	
	public int getTotalCase(){
		return testReports.size();
	}
	
	public int getSuccesses(){
		int count1=0,count2=0;
		for (TestReport testReport : testReports) {
			//TestReport testReport=casesListVO.getTestReport();
			//System.out.println("testReport+"+testReport);
			if(testReport!=null){
				//System.out.println(testReport.getPassFlag());
				if(testReport.getPassFlag()!=null&&testReport.getPassFlag().equals("succeed")){
					count1++;
				}else if(testReport.getPassFlag()==null||testReport.getPassFlag().equals("failed")){
					count2++;
				}
			}
		}
		this.successes=count1;
		this.failures=count2;
		return successes;
	}
	
	public int getFailures(){
		return failures;
	}
}
