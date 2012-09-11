package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.isg.ifrend.model.bean.Action;
import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.StatusActionType;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.EntityType;
import com.isg.ifrend.model.bean.GenericComboItemBean;
import com.isg.ifrend.model.bean.Operator;

public class GlobalComboItemRenderer implements ComboitemRenderer{

	@Override
	public void render(Comboitem arg0, Object arg1) throws Exception {

		if (arg1 instanceof Action){
			Action action = (Action) arg1;
			this.setValueAndLabel(arg0, action.getAction_id(), action.getAction_desc());
		} else if (arg1 instanceof EntityType){
			EntityType entity = (EntityType) arg1;
			this.setValueAndLabel(arg0, entity.getId(), entity.getDesc());	
		} else if (arg1 instanceof Element){
			Element element = (Element) arg1;
			this.setValueAndLabel(arg0, element.getElement_id(), element.getElement_id() + "-" + element.getElement_desc().substring(0,6));
		} else if (arg1 instanceof Operator){
			Operator operator = (Operator) arg1;
			this.setValueAndLabel(arg0, operator.getOperator_code(), operator.getOperator_desc());
		} else if (arg1 instanceof GenericComboItemBean){
			GenericComboItemBean item = (GenericComboItemBean) arg1;
			this.setValueAndLabel(arg0, item.getId(), item.getDesc());
		} else if (arg1 instanceof ActionType){
			ActionType actionType = (ActionType) arg1;
			this.setValueAndLabel(arg0, actionType.getId(), actionType.getDesc());	
		} else if (arg1 instanceof StatusActionType){
			StatusActionType statType = (StatusActionType) arg1;
			this.setValueAndLabel(arg0, statType.getId(), statType.getDesc());	
		}
	}

	private void setValueAndLabel(Comboitem item, Object id, String desc){
		
		if (id instanceof String){
			item.setValue((String) id);
		} else if (id instanceof Integer){
			item.setValue((Integer) id);
		}
		item.setLabel(desc);
	}
}
