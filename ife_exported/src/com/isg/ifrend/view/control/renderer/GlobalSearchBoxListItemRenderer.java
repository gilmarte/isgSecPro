package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.model.bean.TmpLabel;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.model.bean.TmpScript;

public class GlobalSearchBoxListItemRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {

		if (data instanceof TmpElement){
			TmpElement tElements = (TmpElement) data;
			this.setListItems(item, 
					tElements.getTmp_elem_id(), 
					tElements.getTmp_elem_desc());

		} else if (data instanceof TmpMli){
			TmpMli tMli = (TmpMli) data;
			this.setListItems(item, 
					tMli.getMliID(), 
					tMli.getDesc());

		} else if (data instanceof TempCriteria){
			TempCriteria tCriteria = (TempCriteria) data;
			this.setListItems(item, 
					tCriteria.getCriteria_id(), 
					tCriteria.getDescription());

		} else if (data instanceof TmpScript){
			TmpScript tScript = (TmpScript) data;
			this.setListItems(item, 
					tScript.getScriptID(), 
					tScript.getDesc());

		} else if (data instanceof TmpCodeType){
			TmpCodeType tCodeType = (TmpCodeType) data;
			this.setListItems(item, 
					tCodeType.getCodeTypeID(), 
					tCodeType.getDesc());

		} else if (data instanceof TmpLabel){
			TmpLabel tLabel = (TmpLabel) data;
			this.setListItems(item, 
					tLabel.getLabelID(), 
					tLabel.getLabelNativeString());
		} else if (data instanceof Element){
			Element elements = (Element) data;
			this.setListItems(item, 
					elements.getElement_id(), 
					elements.getElement_desc());

		} else if (data instanceof Criteria){
			Criteria criteria = (Criteria) data;
			this.setListItems(item, 
					criteria.getCriteria_id(), 
					criteria.getDescription());

		}
	}

	private void setListItems(Listitem item, Object id, String desc){

		String idStr = null;

		if (id instanceof String){
			idStr = (String) id;
		} else if (id instanceof Integer){
			idStr = String.valueOf((Integer) id);
		}

		new Listcell(idStr).setParent(item);
		new Listcell(desc).setParent(item);
	}

}
