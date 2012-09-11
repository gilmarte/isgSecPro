package com.isg.ifrend.view.control.renderer;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.isg.ifrend.model.bean.TempEnhancedCriterion;

public class EnhancedCriteriaListItemRenderer  implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {

		TempEnhancedCriterion enhancedCriterionTempBean = (TempEnhancedCriterion) data;
		item.setValue(enhancedCriterionTempBean);

		new Listcell(String.valueOf(enhancedCriterionTempBean.getElement_id())).setParent(item);
		new Listcell(enhancedCriterionTempBean.getOperator_code()).setParent(item);

		if (enhancedCriterionTempBean.getEnhanced_value_element() != null){
			new Listcell(enhancedCriterionTempBean.getEnhanced_value_element()).setParent(item);
		} else if (enhancedCriterionTempBean.getEnhanced_value_character() != null){
			new Listcell(enhancedCriterionTempBean.getEnhanced_value_character()).setParent(item);
		}else if (enhancedCriterionTempBean.getEnhanced_value_date() != null){
			SimpleDateFormat formatter= new SimpleDateFormat(enhancedCriterionTempBean.getDateFormat());
			new Listcell(formatter.format(enhancedCriterionTempBean.getEnhanced_value_date())).setParent(item);
		}else if (enhancedCriterionTempBean.getEnhanced_value_integer() != null){
			new Listcell(String.valueOf(enhancedCriterionTempBean.getEnhanced_value_integer())).setParent(item);
		}
	}
}
