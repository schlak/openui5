/*!
 * ${copyright}
 */

// Provides type module:sap/base/i18n/date/CalendarType.
sap.ui.define([], function() {
	"use strict";

	/**
	 * The types of <code>Calendar</code>.
	 *
	 * @enum {string}
	 * @alias module:sap/base/i18n/date/CalendarType
	 * @private
	 * @ui5-restricted sap.ui.core sap/base/i18n
	 */
	var CalendarType = {

		/**
		 * The Gregorian calendar
		 * @public
		 */
		Gregorian: "Gregorian",

		/**
		 * The Islamic calendar
		 * @public
		 */
		Islamic: "Islamic",

		/**
		 * The Japanese emperor calendar
		 * @public
		 */
		Japanese: "Japanese",

		/**
		 * The Persian Jalali calendar
		 * @public
		 */
		Persian: "Persian",

		/**
		 * The Thai buddhist calendar
		 * @public
		 */
		Buddhist: "Buddhist"
	};

	return CalendarType;

});