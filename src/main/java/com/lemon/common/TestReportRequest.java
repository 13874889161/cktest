package com.lemon.common;

import lombok.Data;

@Data
public class TestReportRequest {

	private String reportName;
	private String startTime;
	private String  endTime;
	private String  username;
	private Integer projectId;
}
