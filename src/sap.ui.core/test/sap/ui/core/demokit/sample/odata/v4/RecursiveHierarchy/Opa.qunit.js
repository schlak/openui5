/*!
 * ${copyright}
 */
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"sap/ui/core/sample/common/Helper",
		"sap/ui/core/sample/odata/v4/RecursiveHierarchy/pages/Main",
		"sap/ui/core/sample/odata/v4/RecursiveHierarchy/tests/createEdit",
		"sap/ui/core/sample/odata/v4/RecursiveHierarchy/tests/pageExpandCollapse",
		"sap/ui/core/sample/odata/v4/RecursiveHierarchy/SandboxModel",
		"sap/ui/test/opaQunit"
	], function (Helper, Main, createEdit, pageExpandCollapse, SandboxModel, opaTest) {
		Helper.qUnitModule("sap.ui.core.sample.odata.v4.RecursiveHierarchy",
			/*iTestTimeout*/undefined, /*fnBeforeEach*/null,
			function fnAfterEach() {
				SandboxModel.reset();
			});

		//*****************************************************************************
		[false, true].forEach(function (bTreeTable) {
			var sTitle = "page, expand, collapse; w/ TreeTable: " + bTreeTable;

			opaTest(sTitle, function (Given, When, Then) {
				Main.setTreeTable(bTreeTable);

				pageExpandCollapse(Given, When, Then);
			});
		});

		//*****************************************************************************
		[false, true].forEach(function (bTreeTable) {
			var sTitle = "create, edit; w/ TreeTable: " + bTreeTable;

			opaTest(sTitle, function (Given, When, Then) {
				Main.setTreeTable(bTreeTable);

				createEdit(Given, When, Then);
			});
		});

		QUnit.start();
	});
});
