package com.sap.ui5.modules.librarytests.ux3.tests;

import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.sap.ui5.modules.librarytests.ux3.pages.DataSetPO;
import com.sap.ui5.selenium.common.TestBase;
import com.sap.ui5.selenium.core.UI5PageFactory;

public class DataSetTest extends TestBase {

	private DataSetPO page;

	private final int millisecond = 1000;

	private final int timeOutSeconds = 10;

	private final String targetUrl = "/uilib-sample/test-resources/sap/ui/ux3/visual/DataSet.html";

	@Before
	public void setUp() {
		page = PageFactory.initElements(driver, DataSetPO.class);
		UI5PageFactory.initElements(driver, page);
		driver.get(getFullUrl(targetUrl));
		userAction.mouseClickStartPoint(driver);
		waitForReady(millisecond * 5);
	}

	@Test
	public void testAllElements() {
		waitForReady(1500);
		verifyPage("full-initial");
	}

	@Test
	public void testListViews() {
		String sapAGItemId = page.sapAGItem.getAttribute("id");
		String ibmCorpItemId = page.ibmCorpItem.getAttribute("id");

		// Default View
		userAction.mouseOver(driver, sapAGItemId, 800);
		verifyElement(page.dataSetItemsId, "MouseOver-DefaultView-" + page.sapAGItem.getText());

		userAction.mouseClick(driver, ibmCorpItemId);
		userAction.mouseMoveToStartPoint(driver);
		verifyElement(page.dataSetItemsId, "MouseClick-DefaultView-" + page.ibmCorpItem.getText());

		// List View
		userAction.mouseClick(driver, page.listViewBtn.getAttribute("id"));
		userAction.mouseMoveToStartPoint(driver);
		this.waitForElement(driver, true, page.listViewId, timeOutSeconds);
		verifyElement(page.dataSetItemsId, "ListView");

		userAction.mouseOver(driver, sapAGItemId, millisecond);
		verifyElement(page.dataSetItemsId, "MouseOver-ListView-" + sapAGItemId);

		userAction.mouseClick(driver, ibmCorpItemId);
		userAction.mouseMoveToStartPoint(driver);
		verifyElement(page.dataSetItemsId, "MouseClick-ListView-" + ibmCorpItemId);

		// Bussiness Card View
		userAction.mouseClick(driver, page.cardViewBtn.getAttribute("id"));
		userAction.mouseMoveToStartPoint(driver);
		this.waitForElement(driver, true, page.cardViewId, timeOutSeconds);
		userAction.mouseMoveToStartPoint(driver);
		verifyElement(page.dataSetItemsId, "BussinessCardView");

		userAction.mouseOver(driver, sapAGItemId, millisecond);
		verifyElement(page.dataSetItemsId, "MouseOver-CardView-" + page.sapAGItem.getAttribute("id"));

		userAction.mouseClick(driver, ibmCorpItemId);
		userAction.mouseMoveToStartPoint(driver);
		verifyElement(page.dataSetItemsId, "MouseClick-CardView-" + page.ibmCorpItem.getAttribute("id"));
	}

	@Test
	public void testFilterFeature() {
		waitForReady(1500);

		// Default view filtering
		page.showFilter.toggle();
		this.waitForElement(driver, true, page.filterId, timeOutSeconds);
		verifyArea(page.filterItemId, 1280, 200, "DefaultView-ShowToolbar-Filter");

		userAction.mouseClick(driver, page.item1f10.getAttribute("id"));
		userAction.mouseClickStartPoint(driver);
		page.checkAttribute(page.item1f10);
		verifyElement(page.dataSetId, "DefaultView-Filter-Company-SAPAG");

		userAction.mouseClick(driver, page.companyFilterAll.getAttribute("id"));
		userAction.mouseClick(driver, page.item3f21.getAttribute("id"));
		userAction.mouseClickStartPoint(driver);
		page.checkAttribute(page.item3f21);
		verifyElement(page.dataSetId, "DefaultView-Filter-Headquarter");

		// List view filtering
		userAction.mouseClick(driver, page.headquarterAll.getAttribute("id"));
		waitForReady(millisecond);
		userAction.mouseOver(driver, page.listViewBtn.getAttribute("id"), millisecond);
		userAction.mouseClick(driver, page.listViewBtn.getAttribute("id"));
		waitForElement(driver, true, page.listViewId, timeOutSeconds);
		userAction.mouseClickStartPoint(driver);
		waitForReady(millisecond);
		verifyElement(page.dataSetId, "ListView-ShowToolbar-Filter");

		userAction.mouseClick(driver, page.item1f11.getAttribute("id"));
		userAction.mouseClickStartPoint(driver);
		page.checkAttribute(page.item1f11);
		verifyElement(page.dataSetId, "ListView-Filter-Company-OracleCorp");

		userAction.mouseClick(driver, page.companyFilterAll.getAttribute("id"));
		userAction.mouseClick(driver, page.item3f21.getAttribute("id"));
		userAction.mouseClickStartPoint(driver);
		page.checkAttribute(page.item3f21);
		verifyElement(page.dataSetId, "ListView-Filter-Headquarter");

		// Bussiness Card view filtering
		userAction.mouseClick(driver, page.headquarterAll.getAttribute("id"));
		userAction.mouseOver(driver, page.cardViewBtn.getAttribute("id"), millisecond);
		userAction.mouseClick(driver, page.cardViewBtn.getAttribute("id"));
		waitForElement(driver, true, page.cardViewId, timeOutSeconds);
		waitForReady(millisecond);
		userAction.mouseClickStartPoint(driver);
		verifyElement(page.dataSetId, "CardView-ShowToolbar-Filter");

		userAction.mouseClick(driver, page.item1f11.getAttribute("id"));
		userAction.mouseClickStartPoint(driver);
		page.checkAttribute(page.item1f11);
		verifyElement(page.dataSetId, "CardView-Filter-Company-OracleCorp");

		userAction.mouseClick(driver, page.companyFilterAll.getAttribute("id"));
		userAction.mouseClick(driver, page.item3f21.getAttribute("id"));
		userAction.mouseClickStartPoint(driver);
		page.checkAttribute(page.item3f21);
		verifyElement(page.dataSetId, "CardView-Filter-Headquarter");

		userAction.mouseClick(driver, page.headquarterAll.getAttribute("id"));

		// Multiple select
		userAction.mouseClick(driver, page.item1f10.getAttribute("id"));
		userAction.getRobot().keyPress(KeyEvent.VK_CONTROL);
		userAction.mouseClick(driver, page.item1f12.getAttribute("id"));
		userAction.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		userAction.mouseClickStartPoint(driver);
		verifyElement(page.dataSetId, "CardView-MultiFilter");

		//Reset Filter
		userAction.mouseClick(driver, page.companyFilterAll.getAttribute("id"));
		userAction.mouseMoveToStartPoint(driver);
		page.checkAttribute(page.companyFilterAll);
		verifyElement(page.dataSetId, "Reset-filter");

		//Hide filter
		page.showFilter.toggle();
		this.waitForElement(driver, false, page.filterId, timeOutSeconds);
		verifyElement(page.dataSetId, "Hide-Filter");
	}

	@Test
	public void testSearchFeature() {

		waitForReady(1500);
		page.showSearchField.toggle();
		this.waitForElement(driver, true, page.searchId, timeOutSeconds);
		verifyElement(page.dataSetId, "DefaultView-SearchField");
		page.sendKeysToSearch("SAP", userAction);
		waitForReady(millisecond);
		verifyElement(page.dataSetItemsId, "DefaultView-Search-SAP");

		userAction.mouseOver(driver, page.listViewBtn.getAttribute("id"), millisecond);
		userAction.mouseClick(driver, page.listViewBtn.getAttribute("id"));
		waitForElement(driver, true, page.listViewId, timeOutSeconds);
		page.sendKeysToSearch("IBM", userAction);
		waitForReady(millisecond);
		verifyElement(page.dataSetItemsId, "ListView-Search-IBM");

		// Search with nothing
		userAction.mouseOver(driver, page.listViewBtn.getAttribute("id"), millisecond);
		userAction.mouseClick(driver, page.cardViewBtn.getAttribute("id"));
		waitForElement(driver, true, page.cardViewId, timeOutSeconds);
		page.sendKeysToSearch("USA", userAction);
		waitForReady(millisecond);
		verifyElement(page.dataSetId, "CardView-Search-nothing");

		// Hide SearchField
		page.showSearchField.toggle();
		this.waitForElement(driver, false, page.searchId, timeOutSeconds);
		verifyElement(page.dataSetId, "Hide-SearchField");
	}

	@Test
	public void testHideShowToolbar() {

		waitForReady(1500);
		page.showToolbar.toggle();
		this.waitForElement(driver, false, page.toolbarId, timeOutSeconds);
		verifyElement(page.dataSetId, "Hide-Toolbar");

		page.showToolbar.toggle();
		this.waitForElement(driver, true, page.toolbarId, timeOutSeconds);
		verifyElement(page.dataSetId, "Show-Tooltip");
	}

	@Test
	public void testMultipleSelect() {

		waitForReady(1500);
		page.showMultipleSelect.toggle();
		userAction.mouseClick(driver, page.sapAGItem.getAttribute("id"));
		userAction.getRobot().keyPress(KeyEvent.VK_CONTROL);
		userAction.mouseClick(driver, page.ibmCorpItem.getAttribute("id"));
		userAction.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		userAction.mouseClickStartPoint(driver);
		verifyElement(page.dataSetId, "Show-MultipleSelect");
	}

}