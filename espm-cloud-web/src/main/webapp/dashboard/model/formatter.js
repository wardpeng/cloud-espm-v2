sap.ui.define([
	"sap/ui/core/format/NumberFormat"
], function(NumberFormat) {
	"use strict";

	var fnAmountFormatter = NumberFormat.getCurrencyInstance();
	//返回 01-12 的月份值   
	function getMonth(date){  
	    var month = "";  
	    month = date.getMonth() + 1; //getMonth()得到的月份是0-11  
	    if(month<10){  
	        month = "0" + month;  
	    }  
	    return month;  
	} 
	
	//返回01-30的日期  
	function getDay(date){  
	    var day = "";  
	    day = date.getDate();  
	    if(day<10){  
	        day = "0" + day;  
	    }  
	    return day;  
	}
	//返回小时
	function getHours(date){
	    var hours = "";
	    hours = date.getHours();
	    if(hours<10){  
	        hours = "0" + hours;  
	    }  
	    return hours;  
	}
	//返回分
	function getMinutes(date){
	    var minute = "";
	    minute = date.getMinutes();
	    if(minute<10){  
	        minute = "0" + minute;  
	    }  
	    return minute;  
	}
	//返回秒
	function getSeconds(date){
	    var second = "";
	    second = date.getSeconds();
	    if(second<10){  
	        second = "0" + second;  
	    }  
	    return second;  
	}
	
	return {
		// Formatter for Helpful link - Returns concatenated string with Text and Helpful Count
		formatHelpfulCount: function(iHelpfulCount) {
			return this.getModel().getProperty("/#Review/HelpfulForCurrentUser/@sap:label") + " (" + iHelpfulCount + ")";
		},

		// Formatter for Availability - Displays text or text + number
		formatAvailabilityText: function(iAvailability) {
			var oResourceBundle = this.getModel("i18n").getResourceBundle();
			if (isNaN(iAvailability) || iAvailability < 1) {
				return oResourceBundle.getText("xfld.outOfStock");
			}
			if (iAvailability < 10) {
				return oResourceBundle.getText("xfld.inStockLeft", [iAvailability]);
			}
			return oResourceBundle.getText("xfld.inStock");
		},

		// Formatter for Measures - Returns concatenated string with measure and unit
		formatMeasure: function(fMeasure, sUnit) {
			if (fMeasure && sUnit) {
				var oResourceBundle = this.getModel("i18n").getResourceBundle();
				return oResourceBundle.getText("xfld.formatMeasure", [fMeasure, sUnit]);
			}
			return "";
		},

		// Formatter for amount
		formatAmount: function(fAmount) {
			if (!fAmount) {
				return "";
			}
			return fnAmountFormatter.format(fAmount);
		},
		formatRating: function(fRating){
			if(!fRating){
				return "";
			}
			var value = parseFloat(fRating);
			return value;
		},
		onAddToCart: function(oModel, productContext){
			
			var aData = oModel.getProperty("/ShoppingCart");
			sap.m.MessageToast.show(productContext.Name +" " + " added to the cart");
			
			for(var i=0; i<aData.length; i++){
					var prodId = aData[i];
					if(prodId.ProductId === productContext.ProductId){
						prodId.Quantity += 1;//prodId.Quantity++;
						prodId.Total = prodId.Quantity * prodId.Price;
						oModel.setData({ShoppingCart : aData});
						return;
					}
				}
				productContext.Quantity = 1;
				productContext.Total = productContext.Quantity * productContext.Price;
				aData.push(productContext);
				oModel.setData({ShoppingCart : aData});	

		},
		onAddCountToCart: function(oModel){
			
				var totalQuantity = 0;
				
				var data = oModel.getProperty("/ShoppingCart");
				for(var i=0; i<data.length; i++){
						var prodId = data[i];
						totalQuantity += prodId.Quantity;
					}
			return totalQuantity;		
		},

		/**
		 * Returns a configuration object for the {@link sap.ushell.ui.footerbar.AddBookMarkButton} "appData" property
		 * @public
		 * @param {string} sTitle the title for the "save as tile" dialog
		 * @returns {object} the configuration object
		 */
		formatShareTileData: function(sTitle) {
			return {
				title: sTitle
			};
		},
		
		formatCountryName: function(sText){
			var oResourceBundle = this.getModel("i18n").getResourceBundle();
			return oResourceBundle.getText("sText");
		},
		
		
		//把long类型的时间转换成格式：yyyy-MM-dd 00:00:00
		formatDate: function(longTypeDate){  
		    var datetimeType = "";  
		    var date = new Date();  
		    date.setTime(longTypeDate);  
		    datetimeType+= date.getFullYear();   //年  
		    datetimeType+= "-" + getMonth(date); //月   
		    datetimeType += "-" + getDay(date);   //日  
		    datetimeType+= " " + getHours(date);   //时  
		    datetimeType+= ":" + getMinutes(date);      //分
		    datetimeType+= ":" + getSeconds(date);      //分
		    return datetimeType;
		} 
		 
		

	};
});