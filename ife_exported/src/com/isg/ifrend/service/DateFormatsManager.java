package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.DateFormats;

public interface DateFormatsManager {

	public boolean save(DateFormats dateFormats);
	public boolean delete(DateFormats dateFormats);
	public DateFormats findById(Integer dateformat_id);
	public List<DateFormats> getDateFormatsList();
}
