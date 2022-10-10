/*!
 * ${copyright}
 */
sap.ui.define([], function () {
	"use strict";

	return {
		changeContext : function (Given, When, Then, sUIComponent) {
			Given.iStartMyUIComponent({
				autoWait : true,
				componentConfig : {
					name : sUIComponent || "sap.ui.core.sample.odata.v4.SalesOrders"
				}
			});

			When.onTheMainPage.firstSalesOrderIsVisible();

			// CPOUI5ODATAV4-1786: Context#resetChanges for absolute row contexts
			When.onTheMainPage.selectSalesOrder(2);
			When.onTheMainPage.changeNoteInSalesOrders(2, "CPOUI5ODATAV4-1786: A");
			When.onTheMainPage.selectSalesOrder(4);
			When.onTheMainPage.changeNoteInSalesOrders(4, "CPOUI5ODATAV4-1786: B");
			When.onTheMainPage.pressCancelSelectedSalesOrderChangesButton();
			Then.onTheMainPage.checkNote(4, "EPM DG: SO ID 0500000008 Deliver as fast as possible");
			Then.onTheMainPage.checkNote(2, "CPOUI5ODATAV4-1786: A");
			When.onTheMainPage.pressCancelSalesOrderListChangesButton(); // get rid of 2nd change
			Then.onTheMainPage.checkNote(2, "EPM DG: SO ID 0500000002 Deliver as fast as possible");

			// change a sales order line item, change sales order context
			When.onTheMainPage.selectFirstSalesOrder();
			When.onTheMainPage.selectSalesOrderItemWithPosition("0000000010");
			When.onTheMainPage.changeNoteInLineItem(0, "Changed by OPA 1");
			When.onTheMainPage.selectSalesOrderWithId("0500000001");
			// check hasPendingChanges via refresh
			When.onTheMainPage.pressRefreshSalesOrdersButton();
			When.onTheRefreshConfirmation.cancel();
			// reset changes via binding (API)
			When.onTheMainPage.resetSalesOrderListChanges();
			When.onTheMainPage.selectFirstSalesOrder();
			Then.onTheMainPage.checkSalesOrderLineItemNote(0,
				"EPM DG: SO ID 0500000000 Item 0000000010");

			// check the same via Reset All button
			When.onTheMainPage.selectSalesOrderItemWithPosition("0000000010");
			When.onTheMainPage.changeNoteInLineItem(0, "Changed by OPA 2");
			When.onTheMainPage.selectSalesOrderWithId("0500000001");
			// check hasPendingChanges via refresh all button
			When.onTheMainPage.pressRefreshAllButton();
			When.onTheRefreshConfirmation.confirm();
			When.onTheMainPage.selectFirstSalesOrder();
			Then.onTheMainPage.checkSalesOrderLineItemNote(0,
				"EPM DG: SO ID 0500000000 Item 0000000010");

			// select the first Sales Order and delete Business Partner
			When.onTheMainPage.selectFirstSalesOrder();
			When.onTheMainPage.pressValueHelpOnProductID(0);
			// next line requires modification in VH_ProductID.xml
			Then.onTheValueHelpPopover.checkTitle("Value Help: Product ID (Additional)");
			When.onTheValueHelpPopover.close();
			When.onTheMainPage.pressDeleteBusinessPartnerButton();
			When.onTheSuccessInfo.confirm();
			Then.onTheMainPage.checkInputValue("PhoneNumber::detail", "");
			Then.onTheMainPage.checkInputValue("City::detail", "");
			Then.onTheMainPage.checkInputValue("PostalCode::detail", "");

			When.onTheMainPage.selectSalesOrderWithId("0500000001");
			When.onTheMainPage.pressValueHelpOnProductID(0);
			Then.onTheValueHelpPopover.checkTitle("Value Help: H_EPM_PR_SH_Set");
			When.onTheValueHelpPopover.close();

			Then.onAnyPage.checkLog();
			Then.iTeardownMyUIComponent();
		}
	};
});
