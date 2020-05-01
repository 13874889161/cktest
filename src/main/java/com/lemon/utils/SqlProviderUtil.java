package com.lemon.utils;

import com.lemon.common.TestReportRequest;

public class SqlProviderUtil {

	
	public String  getReportList2(TestReportRequest testReportRequest){
		//System.out.println("111"+testReportRequest.toString());
		StringBuffer sql=new StringBuffer("select t1.*,t2.username from report_record t1 left join user t2 "
				+ "on  t1.create_user=t2.id where t1.project_id=#{projectId} ");
		//报告名称
		if(!(testReportRequest.getReportName()==null)&&!(testReportRequest.getReportName().equals(""))){
			sql.append(" and t1.rept_name like '%"+testReportRequest.getReportName()+"%'");
		}
		//人员名称
		if(!(testReportRequest.getUsername()==null)&&!(testReportRequest.getUsername().equals(""))){
			sql.append(" and t2.username like '%"+testReportRequest.getUsername()+"%'");
		}
		sql.append(" ORDER BY create_time desc");
	return sql.toString();
	}
}
