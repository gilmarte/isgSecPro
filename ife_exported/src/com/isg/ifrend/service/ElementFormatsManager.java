package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.ElementFormats;

public interface ElementFormatsManager {

	public boolean save(ElementFormats elementFormats);
	public boolean delete(ElementFormats elementFormats);
	public ElementFormats findById(Integer elementFormat_id);
	public List<ElementFormats> getElementFormatsList();
}
