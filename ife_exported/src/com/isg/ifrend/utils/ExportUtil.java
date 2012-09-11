package com.isg.ifrend.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.HistUser;
import com.isg.ifrend.model.bean.HistUserOrganization;
import com.isg.ifrend.model.bean.HstCodeType;
import com.isg.ifrend.model.bean.HstElement;
import com.isg.ifrend.model.bean.HstLabel;
import com.isg.ifrend.model.bean.HstMli;
import com.isg.ifrend.model.bean.HstScript;
import com.isg.ifrend.model.bean.Label;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.SaUserGroup;
import com.isg.ifrend.model.bean.SaUserGroupHistory;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.StatusActionType;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TmpSaUserGroup;
import com.isg.ifrend.model.bean.User;
import com.isg.ifrend.model.bean.UserOrganization;

public class ExportUtil {

	private static HSSFWorkbook workbook;
	private static HSSFSheet sheet;
	private static HSSFRow row;
	private static HSSFCellStyle cellStyleNormal;
	private static HSSFCellStyle cellStyleHeader;

	public static void exportToExcel(String fileName, Listbox listbox)
	throws IOException {
		setupFile(fileName);
		composeHeader(listbox);
		composeItemList(listbox);
		saveFile(fileName);
	}

	private static void setupFile(String fileName) {
		workbook = new HSSFWorkbook();
		cellStyleHeader = workbook.createCellStyle();
		cellStyleNormal = workbook.createCellStyle();

		sheet = workbook.createSheet(fileName);

		HSSFFont fontBold = workbook.createFont();
		fontBold.setColor(HSSFFont.COLOR_NORMAL);
		fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHeader.setFillForegroundColor(new HSSFColor.ROYAL_BLUE().getIndex());
		cellStyleHeader.setWrapText(true);
		cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		cellStyleHeader.setTopBorderColor(new HSSFColor.BLACK().getIndex());

		cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		cellStyleHeader.setLeftBorderColor(new HSSFColor.BLACK().getIndex());

		cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		cellStyleHeader.setRightBorderColor(new HSSFColor.BLACK().getIndex());

		cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		cellStyleHeader.setBottomBorderColor(new HSSFColor.BLACK().getIndex());
		cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleHeader.setFont(fontBold);

		HSSFFont fontNormal = workbook.createFont();
		fontNormal.setColor(HSSFFont.COLOR_NORMAL);
		fontNormal.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyleNormal.setFont(fontNormal);
//		cellStyleNormal.setWrapText(true);
	}

	private static void composeHeader(Listbox listbox) {
		row = sheet.createRow(0);
		int i = 0;

		for (Object head : listbox.getHeads()) {
			for (Object header : ((Listhead) head).getChildren()) {
				HSSFCell cell = row.createCell(i);
				sheet.setColumnWidth(i++, 6000);
				cell.setCellStyle(cellStyleHeader);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(((Listheader) header).getLabel());
			}
			break;
		}
	}

	private static void composeItemList(Listbox listbox) {
		ListModelList modelList = (ListModelList) listbox.getModel();

		for (int i = 0; i < modelList.size(); i++) {
			row = sheet.createRow(i + 1);

			if (modelList.get(i) instanceof Element) {
				composeSearchRow((Element) modelList.get(i));

			} else if (modelList.get(i) instanceof Criteria) {
				composeSearchRow((Criteria) modelList.get(i));

			} else if (modelList.get(i) instanceof Script) {
				composeSearchRow((Script) modelList.get(i));

			} else if (modelList.get(i) instanceof Mli) {
				composeSearchRow((Mli) modelList.get(i));

			} else if (modelList.get(i) instanceof CodeType) {
				composeSearchRow((CodeType) modelList.get(i));

			} else if (modelList.get(i) instanceof Label) {
				composeSearchRow((Label) modelList.get(i));

			} else if (modelList.get(i) instanceof HstElement) {
				composeHistoryRow((HstElement) modelList.get(i));

			} else if (modelList.get(i) instanceof HistCriteria) {
				composeHistoryRow((HistCriteria) modelList.get(i));

			} else if (modelList.get(i) instanceof HstScript) {
				composeHistoryRow((HstScript) modelList.get(i));

			} else if (modelList.get(i) instanceof HstMli) {
				composeHistoryRow((HstMli) modelList.get(i));

			} else if (modelList.get(i) instanceof HstCodeType) {
				composeHistoryRow((HstCodeType) modelList.get(i));

			} else if (modelList.get(i) instanceof HstLabel) {
				composeHistoryRow((HstLabel) modelList.get(i));

			} else {
				composeRow(modelList.get(i));
			}
		}
	}

	private static <T extends Global> void composeSearchRow(T item) {
		int i = 0;
		composeCell(item.getId(), i++);
		composeCell(item.getDesc(), i++);
		if (StatusType.PENDING.getId() == item.getStatusID()) {
			composeCell(StatusActionType.getDesc(item.getActionID()), i++);
		} else {
			composeCell(StatusActionType.getDesc(item.getStatusID()), i++);
		}
		composeCell(item.getLastModifierUserID(), i++);
		composeCell(DateUtil.format(item.getDateLastModified()), i);
	}

	private static <T extends Global> void composeHistoryRow(T item) {
		int i = 0;
		composeCell(item.getId(), i++);
		composeCell(item.getDesc(), i++);
		composeCell(StatusType.getDesc(item.getStatusID()), i++);
		composeCell(ActionType.getDesc(item.getActionID()), i++);
		composeCell(item.getLastModifierUserID(), i++);
		composeCell(DateUtil.format(item.getDateLastModified()), i++);
		if (null != item.getApproverUserID()) {
			composeCell(item.getApproverUserID(), i++);
			composeCell(DateUtil.format(item.getDateApproved()), i);
		}
	}

	private static void composeRow(Object item) {
		int i = 0;
		if(item instanceof User) {
			User user = (User) item;
			composeCell(user.getUserID(), i++);
			composeCell(user.getUserName(), i++);
//			composeCell(StatusType.getDesc(user.getStatus()), i++);
//			composeCell(ActionType.getDesc(user.getAction()), i++);
			if(user.getStatus() == StatusType.PENDING.getId()) {
				composeCell(StatusActionType.getDesc(user.getAction()), i++);
			}
			else {
				composeCell(StatusType.getDesc(user.getStatus()), i++);
			}
//			composeCell(user.getCreatedBy(), i++);
//			try {
//				composeCell(DateUtil.format(user.getDateCreated()), i++);
//			}
//			catch (NullPointerException e) {
//				i++;
//			}
			composeCell(user.getLastModifiedBy(), i++);
			try {
				composeCell(DateUtil.format(user.getDateLastModified()), i++);
			}
			catch (NullPointerException e) {
				i++;
			}
//			composeCell(user.getApprovedBy(), i++);
//			try {
//				composeCell(DateUtil.format(user.getDateApproved()), i++);
//			}
//			catch (NullPointerException e) {
//				i++;
//			}
		}
		else if(item instanceof HistUser) {
			HistUser histUser = (HistUser) item;
			composeCell(histUser.getUserID(), i++);
			composeCell(StatusType.getDesc(histUser.getStatus()), i++);
			composeCell(ActionType.getDesc(histUser.getAction()), i++);
			composeCell(histUser.getCreatedBy(), i++);
			try {
				composeCell(DateUtil.format(histUser.getDateCreated()), i++);
			}
			catch (NullPointerException e) {
				i++;
			}
			composeCell(histUser.getLastModifiedBy(), i++);
			try {
				composeCell(DateUtil.format(histUser.getDateLastModified()), i++);
			}
			catch (NullPointerException e) {
				i++;
			}
			composeCell(histUser.getApprovedBy(), i++);
			try {
				composeCell(DateUtil.format(histUser.getDateApproved()), i++);
			}
			catch (NullPointerException e) {
				i++;
			}
		}
		else if(item instanceof UserOrganization) {
			UserOrganization userOrg = (UserOrganization) item;
			composeCell(userOrg.getOrgID(), i++);
			composeCell(userOrg.getOrgDesc(), i++);
//			composeCell(StatusType.getDesc(userOrg.getStatus()), i++);
//			composeCell(ActionType.getDesc(userOrg.getAction()), i++);
			if(userOrg.getStatus() == StatusType.PENDING.getId()) {
				composeCell(StatusActionType.getDesc(userOrg.getAction()), i++);
			}
			else {
				composeCell(StatusType.getDesc(userOrg.getStatus()), i++);
			}
//			composeCell(userOrg.getCreatedBy(), i++);
//			try {
//				composeCell(DateUtil.format(userOrg.getDateCreated()), i++);
//			}
//			catch (NullPointerException e) {
//				i++;
//			}
			composeCell(userOrg.getLastModifiedBy(), i++);
			try {
				composeCell(DateUtil.format(userOrg.getDateLastModified()), i++);
			}
			catch (NullPointerException e) {
				i++;
			}
//			composeCell(userOrg.getApprovedBy(), i++);
//			try {
//				composeCell(DateUtil.format(userOrg.getDateApproved()), i++);
//			}
//			catch (NullPointerException e) {
//				i++;
//			}
		}
		else if(item instanceof HistUserOrganization) {
			HistUserOrganization histUserOrg = (HistUserOrganization) item;
			composeCell(histUserOrg.getOrgID(), i++);
			composeCell(StatusType.getDesc(histUserOrg.getStatus()), i++);
			composeCell(ActionType.getDesc(histUserOrg.getAction()), i++);
			composeCell(histUserOrg.getCreatedBy(), i++);
			try {
				composeCell(DateUtil.format(histUserOrg.getDateCreated()), i++);
			}
			catch (NullPointerException e) {
				i++;
			}
			composeCell(histUserOrg.getLastModifiedBy(), i++);
			try {
				composeCell(DateUtil.format(histUserOrg.getDateLastModified()), i++);
			}
			catch (NullPointerException e) {
				i++;
			}
			composeCell(histUserOrg.getApprovedBy(), i++);
			try {
				composeCell(DateUtil.format(histUserOrg.getDateApproved()), i++);
			}
			catch (NullPointerException e) {
				i++;
			}
		} else if (item instanceof TmpSaUserGroup) {
			composeCell(((TmpSaUserGroup) item).getGroup_id(), i++);
			composeCell(((TmpSaUserGroup) item).getDescription(), i++);
			composeCell(((TmpSaUserGroup) item).getStatus(), i++);
			composeCell(((TmpSaUserGroup) item).getMaker_id(), i++);
			composeCell(((TmpSaUserGroup) item).getChecker_id(), i++);

		} else if (item instanceof SaUserGroup) {
			composeCell(((SaUserGroup) item).getGroup_id(), i++);
			composeCell(((SaUserGroup) item).getDescription(), i++);
			composeCell(((SaUserGroup) item).getStatus(), i++);
			// composeCell(((SaUserGroup) item).getMaker_id(), i++);
			// composeCell(((SaUserGroup) item).getCreation_dt(), i++);
			composeCell(((SaUserGroup) item).getModifier_id(), i++);
			composeCell(((SaUserGroup) item).getModified_dt(), i++);
			// composeCell(((SaUserGroup) item).getChecker_id(), i++);
			// composeCell(((SaUserGroup) item).getDecision_dt(), i++);

		} else if (item instanceof SaUserGroupHistory) {
			composeCell(((SaUserGroupHistory) item).getGroup_id(), i++);
			composeCell(((SaUserGroupHistory) item).getAction(), i++);
			composeCell(((SaUserGroupHistory) item).getStatus(), i++);
			composeCell(((SaUserGroupHistory) item).getMaker_id(), i++);
			composeCell(((SaUserGroupHistory) item).getCreation_dt(), i++);
			composeCell(((SaUserGroupHistory) item).getModified_dt(), i++);
			composeCell(((SaUserGroupHistory) item).getChecker_id(), i++);
			composeCell(((SaUserGroupHistory) item).getDecision_dt(), i++);

		}
	}

	private static void composeCell(String value, int i) {
		HSSFCell cell = row.createCell(i);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyleNormal);
		cell.setCellValue(value);
	}

	private static void saveFile(String fileName) throws IOException {
		StringBuilder sb = new StringBuilder(Commons.EXCEL_FILE_PREFIX)
		.append(Commons.EXCEL_FILE_DELIMITER)
		.append(fileName)
		.append(Commons.EXCEL_FILE_DELIMITER)
		.append(DateUtil.formatDate(DateUtil.getCurrentDate(),
				Commons.EXCEL_FILE_DATE_SUFFIX))
				.append(Commons.EXCEL_FILE_EXTENSION);

		FileOutputStream out = new FileOutputStream(sb.toString());
		workbook.write(out);

		out.flush();
		out.close();

		File file = new File(sb.toString());
		Filedownload.save(file, Commons.EXCEL_FILE_TYPE);
	}
}