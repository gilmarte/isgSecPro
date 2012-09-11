package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.ElementTypes;

public interface ElementTypesManager {

	public boolean save(ElementTypes elementTypes);
	public boolean delete(ElementTypes elementTypes);
	public ElementTypes findById(Integer elementType_id);
	public List<ElementTypes> getElementTypesList();
	
}
