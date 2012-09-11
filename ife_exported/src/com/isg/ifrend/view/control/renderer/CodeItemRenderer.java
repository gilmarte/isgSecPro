package com.isg.ifrend.view.control.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.isg.ifrend.model.bean.TmpCode;

public class CodeItemRenderer implements ListitemRenderer {

	@Override
	public void render(Listitem item, Object data) throws Exception {
		final TmpCode g = (TmpCode) data;
		new Listcell(g.getCodeValue()).setParent(item);
		new Listcell(g.getCodeDesc()).setParent(item);
	}
}