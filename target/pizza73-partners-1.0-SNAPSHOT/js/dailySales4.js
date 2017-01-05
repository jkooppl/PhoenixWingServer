//global setting
var globalObj={errors:new Array(),
               warnings:new Array(),
               cancelAction:false,
               status:true,
               waitPanel:null,
               calendarPanel:null,
               validationNodeList:null,
			   numberOfFrontCounterMachines:1,
			   numberOfWirlessMachines:12,
               cleanUp:function(){
                 this.errors=new Array();
                 this.warnings=new Array();
                 },
              	findValidationNode:function(nodeId){
              	 var i=0;
                 for(i=0;i<this.validationNodeList.length;i++)
                    if(this.validationNodeList[i].nodeId == nodeId) return this.validationNodeList[i];
                },
	            populateValidationNodes:function(){
	             var i=0;
	             this.numberOfFrontCounterMachines=Number(dojo.byId("numberofFrontCounterMachines").value);
	       		 this.numberOfWirlessMachines=Number(dojo.byId("numberofWirelessMachines").value);
	             this.validationNodeList=new Array(new ValidationNode("computerSales","COMPUTER SALES"),
	               						new ValidationNode("walkInSales","WALK-IN SALES"),
	               						new ValidationNode("miscSales","MISC. SALES"),
	               						new ValidationNode("gst","G.S.T"),
	               						new ValidationNode("returns", "RETURNS"),
	               						new ValidationNode("giftCertificateSold","GIFT CERTIFICATE SOLD"),
	               						new ValidationNode("discountsAndAdvertising","DISCOUNTS/ADVERTISING"),
	               						new ValidationNode("coupons", "COUPONS"),
	               						new ValidationNode("oneHundredDollarBill", "Number of $100 bills"),
	               						new ValidationNode("fiftyDollarBill", "Number of $50 bills"),
	               						new ValidationNode("twentyDollarBill", "Number of $20 bills"),
	               						new ValidationNode("tenDollarBill", "Number of $10 bills"),
	               						new ValidationNode("fiveDollarBill", "Number of $5 bills"),
	               						new ValidationNode("twoDollarBill", "Number of $2 coins"),
	               						new ValidationNode("oneDollarBill", "Number of $1 coins"),
	               						new ValidationNode("twentyFiveCentBill", "Number of $0.25 coins"),
	               						new ValidationNode("tenCentBill", "Number of $0.10 coins"),
	               						new ValidationNode("fiveCentBill", "Number of $0.05 coins"),
	               						new ValidationNode("oneCentBill", "Number of $0.01 coins"),
	               						new ValidationNode("chequesTotal", "CHEQUES TOTAL"),
	               						new ValidationNode("giftCertificateRedeemed", "GIFT CERTIFICATE REDEEMED")
	               						);
	               						for(i=1;i<=this.numberOfFrontCounterMachines;i++){
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_debit","DEBIT IN FRONT-COUNTER  MACHINE #"+i);
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_visa","VISA IN FRONT-COUNTER  MACHINE #"+i);
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_mastercard","MASTERCARD IN FRONT-COUNTER  MACHINE #"+i);
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_amex","AMEX IN FRONT-COUNTER  MACHINE #"+i);
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_batchNumber","BATCH NUMBER IN FRONT-COUNTER  MACHINE #"+i);
	              						}
	              						for(i=this.numberOfFrontCounterMachines+1;i<=(this.numberOfFrontCounterMachines+this.numberOfWirlessMachines);i++){
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_debit","DEBIT IN WIRELESS MACHINE #"+(i-this.numberOfFrontCounterMachines));
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_visa","VISA IN WIRELESS MACHINE #"+(i-this.numberOfFrontCounterMachines));
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_mastercard","MASTERCARD IN WIRELESS MACHINE #"+(i-this.numberOfFrontCounterMachines));
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_amex","AMEX IN WIRELESS MACHINE #"+(i-this.numberOfFrontCounterMachines));
	               						this.validationNodeList[this.validationNodeList.length]=new ValidationNode("BatchRecords_"+(i-1)+"_batchNumber","BATCH NUMBER IN WIRELESS MACHINE #"+(i-this.numberOfFrontCounterMachines));
	              						}
	              }              
	              }; 
              
    
              
//cookie
    function loadMainPage(){
       dwr.engine.setErrorHandler(function(){
       document.location="./logout.html";
       document.location.reload();
       });      
       var shopId=getShopInfo();
       var isShopSelectable=(dojo.byId("shopSelector") != null);
       //admin view
       if(isShopSelectable == true){
         if(shopId == null){
            disablePageContent();
            dojo.byId("shopSelectorInfo").innerHTML="Please select a shop from the list.";
            }
         else
            setShop(shopId);
	   }//normal view
	   else{
	     AjaxManager.hasAvailableReports(shopId,function(flag){
	     if(flag == true)
	     displayReport();
	     else
	     dojo.byId("pageContent").innerHTML="<h1 style='color:blue;margin-top:10px;margin-left:5px;'>Dailysales online reporting is not enabled for your shop now.</h1>"; 
	   });
	   }
    }
    
	
	function getShopInfo(){
	   var cookieShop = null;
	   //admin view
	   if(dojo.byId("shopSelector") != null){
	       cookieShop=dojo.fromJson(dojo.cookie("displayShop"));
	       if (cookieShop !=null) 
	         dojo.byId("shopSelector").value=cookieShop;
	       return cookieShop;	   
	   }
	   //normal view
	   else{
	      return dwr.util.getValue("currentShopId");
	   }
	}
	
	function displayReport(){
	if(dojo.byId("shopSelectorInfo")!= null)
	dojo.byId("shopSelectorInfo").innerHTML="";
	enablePageContent();
	var displayConfig=dojo.fromJson(dojo.cookie("displayTag"));
    if(displayConfig != null){
       var date=displayConfig[1]; 
       if(displayConfig[0] == "week")
             swiftToWeek(date);
       else
             swiftToDate(date);
    }
    else{
       swiftToDate();
    }	   	
   }
	
   function changeShop(shopId){
    dojo.cookie("displayShop",dojo.toJson(shopId));
    dojo.cookie("displayTag",null);
           if(isEmpty(dojo.trim(String(shopId)))){
            disablePageContent();
            dojo.byId("shopSelectorInfo").innerHTML="Please select a shop from the list.";
            return ;
            }
           else
           setShop(shopId);
	}
	
	function setShop(shopId){
	     AjaxManager.setShop(shopId,function(flag){
	     if(flag==true)
	       displayReport();
	     else
	       disablePageContent();
	       dojo.byId("shopSelectorInfo").innerHTML="Shop "+shopId+" has no available reports";
	     });	
	}
    
    function storeToCookie(tabType,defaultDate,sunday){
       dojo.cookie("displayTag",dojo.toJson(new Array(tabType,defaultDate,sunday)));
    }  

//DIVs' visibility
	function disablePageContent(){
	   dojo.query("#shopSelectorInfo").style("display","inline");
	   dojo.query("#pageContent").style("display","none");
	}
	
    function enablePageContent(){
       if(dojo.byId("shopSelectorInfo")!= null)
       dojo.byId("shopSelectorInfo").innerHTML="";
	   dojo.query("#shopSelectorInfo").style("display","none");
	   dojo.query("#pageContent").style("display","block");	
	}

//calendar
    function calendarSelectHandler(type,args,obj) { 
    var selected=args[0];
    var selectedDate=this._toDate(selected[0]);
    var year=selectedDate.getFullYear().toString();
    var month=(selectedDate.getMonth()+1).toString();
    var date=selectedDate.getDate().toString();
    if(month.length ==1)
    month='0'+month;
    if(date.length ==1)
    date='0'+date;
    swiftToDate(year+month+date);
    this.hide();
	} 
	  
	function popupACalendar(selectedDate){
	AjaxManager.getAvailableDateRange(function(range){
	   globalObj.calendarPanel= new YAHOO.widget.Calendar('cal2','popupCalendar',
	                                                      {title:'Choose a date:',
	                                                       close:true,
	                                                       mindate:range[0],
	                                                       maxdate:range[1],
	                                                       selected:(selectedDate.substr(4,2)+'/'+selectedDate.substr(6,2)+'/'+selectedDate.substr(0,4)),
	                                                       pagedate:(selectedDate.substr(4,2)+'/'+selectedDate.substr(0,4))
	                                                       });
	   globalObj.calendarPanel.selectEvent.subscribe(calendarSelectHandler,globalObj.calendarPanel,true);
	   globalObj.calendarPanel.render(); 
	   YAHOO.util.Event.addListener("calendarImageTag", "click", globalObj.calendarPanel.show, globalObj.calendarPanel, true); 	   
	   });	
	}
	
	function createTip(node,str){
	    var myTooltip = new YAHOO.widget.Tooltip("Tooltip"+node.id, { context:node.id, text:str,zIndex:5} ); 
	    myTooltip.doShow();	    
	}
	
//tabbing
	function renderTabs(settings,isDisplayingWeeklySales){
	   var i=0;
	   renderTab("lastWeek",settings[0].urlDate,settings[0].label,settings[0].available);
	   for(i=0;i<7;i++){
	     renderTab("day_"+i,settings[i+1].urlDate,settings[i+1].label,settings[i+1].available);
		 if(settings[i+1].defaultDate ==true && isDisplayingWeeklySales == false)
		    dojo.query("#day_"+i).addClass("current");
		 }
	   renderWeeklyTab("weeklySalesTag",settings[1].urlDate, "Weekly Sales", true);
	   if(isDisplayingWeeklySales ==true)
	      dojo.query("#weeklySalesTag").addClass("current");
	   renderTab("nextWeek",settings[8].urlDate,settings[8].label,settings[8].available);
    }

    function renderTab(id,date,label, isAvailable){
	   if(isAvailable)
	      dojo.byId(id).innerHTML="<a href=\"javascript:swiftToDate('"+date+"')\"><b>"+label+"</b></a>";
	   else
	      dojo.byId(id).innerHTML="<span>"+label+"</span>";
    }

	function renderWeeklyTab(id,date,label, isAvailable){
	   if(isAvailable)
	      dojo.byId(id).innerHTML="<a href=\"javascript:swiftToWeek('"+date+"')\"><b>"+label+"</b></a>";
	   else
	      dojo.byId(id).innerHTML="<span>"+label+"</span>";
    }
    
    function swiftToDate(date){            
	   dojo.query(".current").removeClass("current");
	   getLoadingInfo().show();
	   AjaxManager.setDailySalesTabs(date,function(settings){
	           var defaultDate=null;
	           var i=0;
	           for(i=0;i<settings.length;i++){
	              if(settings[i].defaultDate == true)
	                defaultDate=settings[i].urlDate;	                
	           }
	           renderTabs(settings,false);
			   getDailySalesForm(defaultDate);
			   storeToCookie("day",defaultDate,settings[1].urlDate);
			   popupACalendar(defaultDate);	   
	   });
    }
    
   function swiftToWeek(date){
	   dojo.query(".current").removeClass("current");
	   getLoadingInfo().show();
	   AjaxManager.setDailySalesTabs(date,function(settings){
	           var defaultDate=settings[1].urlDate;	                
	           renderTabs(settings,true);
			   getWeeklySalesForm(settings[1].urlDate);
			   storeToCookie("week",defaultDate,settings[1].urlDate);
			   popupACalendar(defaultDate);	   
	   });
	}

    function getLoadingInfo(){
    if(globalObj.waitPanel ==null){
    globalObj.waitPanel= new YAHOO.widget.Panel("wait", { width:"240px",fixedcenter:true,close:false,draggable:false,zindex:1000,modal:true, visible:false}); 
	globalObj.waitPanel.setHeader("Loading, please wait..."); 
	globalObj.waitPanel.setBody('<img src="./images/rel_interstitial_loading.gif" />'); 
	globalObj.waitPanel.render(document.body);
	}
    return  globalObj.waitPanel
    }	

//code for row highlight
	function focusRow(node){
	dojo.query("td",node.id).forEach(
    function(tdNode) {
        if(dojo.hasClass(tdNode,"blueFlag"))
          dojo.addClass(tdNode,"blueFocusTD");
        else
          dojo.addClass(tdNode,"focusTD");
    });
	}
	
    function unfocusRow(node){
	dojo.query("td",node.id).forEach(
    function(tdNode) {
        if(dojo.hasClass(tdNode,"blueFlag"))
          dojo.removeClass(tdNode,"blueFocusTD");
        else
          dojo.removeClass(tdNode,"focusTD");
    });
	}
	
	function highlightRow(node){
	dojo.query("td",node.id).forEach(
    function(tdNode) {
        if(dojo.hasClass(tdNode,"blueFlag"))
          dojo.addClass(tdNode,"blueTD");
        else
          dojo.addClass(tdNode,"highlightTD");
    });
	}
	
    function normalizeRow(node){
	dojo.query("td",node.id).forEach(
    function(tdNode) {
        if(dojo.hasClass(tdNode,"blueFlag"))
          dojo.removeClass(tdNode,"blueTD");
        else
          dojo.removeClass(tdNode,"highlightTD");
    });
	}
	
 	function highlightAutoRow(node){
	dojo.query("td",node.id).addClass("blueTD");
	}
	
    function normalizeAutoRow(node){
	dojo.query("td",node.id).removeClass("blueTD");
	}
	
	function highlightInputField(node){
	dojo.query("#"+node.id).addClass("inputFieldOnFocus");
	dojo.query("#"+node.parentNode.id).addClass("onFocusTD");
	focusRow(node.parentNode.parentNode);
	}
	
	function normalizeInputField(node){
	dojo.query("#"+node.id).removeClass("inputFieldOnFocus");
	dojo.query("#"+node.parentNode.id).removeClass("onFocusTD");
	unfocusRow(node.parentNode.parentNode);	
	}

//---code for validation---//
    function ValidationNode(nodeId,label){
       this.nodeId=nodeId;
       this.label=label;
    }
    
    function validationError(nodeId,message){
       this.nodeId=nodeId;
       this.message=message;
    }
    
    function validate(){
    globalObj.cleanUp();
    var i=0;
    for(i=0;i<globalObj.validationNodeList.length;i++){
        validateNode(globalObj.validationNodeList[i]);
    }
    displayErrorAndWarning();    
    }
    
    function validateNode(validationNode){
       if((validationNode.nodeId.indexOf("Bill") != -1  || validationNode.nodeId.indexOf("cheque") != -1) &&dwr.util.getValue("version")  == 1)
       return ;
	   var val=dojo.trim(dwr.util.getValue(validationNode.nodeId));
	   dwr.util.setValue(validationNode.nodeId,val);
       if(validationNode.nodeId.indexOf('BatchRecords') != -1){
           //process all the input fields in a wireless machine together
           //use batchNumber is an indicator
           if(validationNode.nodeId.indexOf('batchNumber') != -1)
           handleBatchRecord(dojo.byId(validationNode.nodeId).parentNode.parentNode);
       }
       else if(validationNode.nodeId.indexOf("Bill") != -1){
            if(!validateNonNegativeInteger(val)){
             if(isEmpty(val))
                 addError(validationNode.nodeId, validationNode.label+" is mandatory.");
             else if (!isNum(val))
                	 addError(validationNode.nodeId, validationNode.label+" must be a number."); 
              else if (!isNonNegative(val))
                 addError(validationNode.nodeId, validationNode.label+" must be a positive number.");
              else 
                 addError(validationNode.nodeId, validationNode.label+" must be an integer.");            
            }
       }
       // validate all other fileds(they are all digital field)
       else if(!validateDigitField(val)){
             if(isEmpty(val))
                 addError(validationNode.nodeId, validationNode.label+" is mandatory.");
             else if (!isNum(val))
                	 addError(validationNode.nodeId, validationNode.label+" must be a number."); 
              else if (!isNonNegative(val))
                 addError(validationNode.nodeId, validationNode.label+" must be a positive number.");
              else 
                 addError(validationNode.nodeId, validationNode.label+" can have at most 2 digits after decimal point.");
           }            
	}
	
	function handleBatchRecord(node){
	     var AllEmpty=true;
	     var inputNodes=dojo.query("input",node);
	     var i=0;     
	     for(i=0;i<inputNodes.length;i++){
	         AllEmpty=AllEmpty && isEmpty(inputNodes[i].value);	         
	     }
	     if(AllEmpty != true){
	        for(i=0;i<inputNodes.length;i++)
	            handleBatchRecordNode(inputNodes[i]);
	     }	    
	}
	
	function handleBatchRecordNode(node){
	     var nodeId=node.id;
	     var val=node.value;
	     var validationNode=null;
	       
	     if(nodeId.indexOf('batchNumber') != -1){
	       if(!isAtLeast3Digit(val)){
	          validationNode=globalObj.findValidationNode(nodeId);
              if(isEmpty(val))
                 addError(validationNode.nodeId, validationNode.label+" can not be empty. Incomplete machine record.");
              else if (!isAllDigit(val))
                 addError(validationNode.nodeId, validationNode.label+" can only contains digits.");
              else 
                 addError(validationNode.nodeId, validationNode.label+" can have must have at least 3 digits.");	       	       
	      }
	     }
	     else if(!validateDigitField(val)){	             
	             validationNode=globalObj.findValidationNode(nodeId);
              	 if(isEmpty(val))
                	 ;
             	 else if (!isNum(val))
                	 addError(validationNode.nodeId, validationNode.label+" must be a number.");                 
             	 else if (!isNonNegative(val))
                	 addError(validationNode.nodeId, validationNode.label+" must be a positive number.");
              	 else 
                 	 addError(validationNode.nodeId, validationNode.label+" can have at most 2 digits after decimal point.");
           }          
	}
    
    function weeklyValidate(){
    globalObj.cleanUp();
    validateNode(new ValidationNode("openingInventory","OPENING INVENTORY"));
    validateNode(new ValidationNode("commissaryPurchases","COMMISSARY PURCHASES"));
    validateNode(new ValidationNode("sysco","SYSCO"));
    validateNode(new ValidationNode("lilydale","LILYDALE"));
    validateNode(new ValidationNode("pepsi","PEPSI"));
    validateNode(new ValidationNode("pettyCash","PETTY CASH"));
    validateNode(new ValidationNode("others","OTHERS"));
    validateNode(new ValidationNode("closingInventory", "CLOSING INVENTORY"));
    displayErrorAndWarning();
    }
    
    function weeklyCheckBeforeSave(){
    weeklyValidate();
    if(globalObj.errors.length == 0){
       return true;
       }
    else
       return false;    
    }
    
    
    function checkBeforeSave(){
    validate();
    if(globalObj.errors.length == 0){
       formatBatchRecords();
       return true;
       }
    else
       return false;
    }
    
    function checkBeforeCancel(){
    return confirm("Are you sure to cancel your change?");
    }
    
    function weeklyCheckBeforeSubmit(){
    weeklyValidate();
    if(globalObj.errors.length == 0 && globalObj.warnings.length == 0){
       return confirm("Please be aware that you can't make any more change after your submission.Are you sure to submit to the office now?");
       }
    else
       return false;    
    }
    
    function checkBeforeSubmit(){
    validate();
    if(globalObj.errors.length == 0 && globalObj.warnings.length == 0){
       formatBatchRecords();
       var displayConfig=dojo.fromJson(dojo.cookie("displayTag"));
       storeToCookie("week",displayConfig[2],displayConfig[2]);
       return confirm("Please be aware that you can't make any more change after your submission.Are you sure to submit to the office now?");
       }
    else
       return false;
    }
	
	function removeFocusRowError(node){
	     var temp;
	     var i=0;
	     dojo.forEach(dojo.query("input",node.parentNode.parentNode),function(inputNode){
	         if(isEmpty(inputNode.value)){
	            dojo.query("#"+inputNode.id).removeClass("inputFieldError");
				for(i=globalObj.errors.length-1;i>=0;i--){
        		if(inputNode.id+"_error" == globalObj.errors[i].nodeId){  
        		  temp=dojo.byId(globalObj.errors[i].nodeId);
        		  temp.parentNode.removeChild(temp);
        		  globalObj.errors.splice(i,1);
        		}	             
	           }
	         }
	     });
	}
	
	function addError(nodeId,message){
	  globalObj.errors.push(new validationError(nodeId+"_error",message));
	}
	
    function validateDigitField(val){
	  return (/^\d+(\.\d{1,2})?$/).test(val);
	}
	
	function validateNonNegativeInteger(val){
	  return (/^\d+$/).test(val);
	}
	
	function isAllDigit(val){
	  return (/^\d+$/).test(val);
	}
	
	function isAtLeast3Digit(val){
	    return (/^\d{3,}$/).test(val);
	}
	
    function isNonNegative(val){
      return (/^\d+(\.\d+)?$/).test(val);
    }
     
    function isNum(val){
      return (/^(-)?\d+(\.\d+)?$/).test(val);    
    }
    
    function isEmpty(val){
        return (val =='' || val == null);
    }
	
		
	function displayErrorAndWarning(){
	    var i=0;
        dojo.query(".inputFieldError").removeClass("inputFieldError");
        dojo.byId("errorConsole").innerHTML="";
        var errorInfo="";
		for(i=0;i<globalObj.errors.length;i++){
		inputNodeId=globalObj.errors[i].nodeId.substr(0,globalObj.errors[i].nodeId.length- "_error".length);
        dojo.query('#'+inputNodeId).addClass("inputFieldError");
        errorInfo+="<div id=\""+globalObj.errors[i].nodeId+"\" style=\"vertical-align:top\"> <img src=\"./images/error.png\" /><span style=\"vertical-align:top\"> &nbsp;"+globalObj.errors[i].message+"</span></div>";
		}
		dojo.byId("errorConsole").innerHTML=errorInfo;
	}
//---code for update and format---//
    function weeklyAutoUpdate(){
        updateTotalPurchases();
        updateCostOfSales();
        updateFoodAndBeverageSales();
        updateFoodCost();
    }
    
    function updateTotalPurchases(){
       var val=formGetValue("commissaryPurchases")+formGetValue("sysco")+formGetValue("lilydale")+formGetValue("pepsi")+formGetValue("pettyCash")+formGetValue("others");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("totalPurchases_cell",String(val));        
    }
    
    function updateCostOfSales(){
       var val=formGetValue("openingInventory")+formGetValue("totalPurchases_cell")-formGetValue("closingInventory");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("costOfSales_cell",String(val));       
    }
    
    function updateFoodAndBeverageSales(){
       var val=formGetValue("netToPizza73Total_cell_sum");
       val=RoundToTwoDigit(val);
       if(dojo.byId("foodAndBeverageSales") != null)
       dwr.util.setValue("foodAndBeverageSales",String(val));
       dwr.util.setValue("foodAndBeverageSales_cell",String(val));
    }
    
    function updateFoodCost(){
       if(formGetValue("foodAndBeverageSales") != 0.00){
       var val=formGetValue("costOfSales_cell")*100/formGetValue("foodAndBeverageSales");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("foodCost_cell",String(val)+"%");
       }  
    }
    
    function autoUpdate(){
        //Net Sales Table
        updateNetToPizza73();
        updateTotalReceipts();
        //Gross Table
        updateNetSales();
        updateGST();
        updateGrossSales();
        //update actual cash table
        if(dwr.util.getValue("version") >1){
        updateOneHundredDollarBill();
        updateFiftyDollarBill();
        updateTwentyDollarBill();
        updateTenDollarBill();
        updateFiveDollarBill();
        updateBillsTotal();
        updateTwoDollarBill();
        updateOneDollarBill();
        updateTwentyFiveCentBill();
        updateTenCentBill();
        updateFiveCentBill();
        updateOneCentBill();
        updateCoinsTotal();
        }
        updateActualCash();
        //batch form table
        updateBatchRecordTotals();
        //In-store Table
        updateInStoreDebitTotal();
        updateInStoreVisaTotal();
        updateInStoreMastercardTotal();
        updateInStoreAmexTotal();
        updateInStoreTotal();
        //Wireless/Driver Table
        updatewirelessDriverDebitTotal();
        updatewirelessDriverVisaTotal();
        updatewirelessDriverMastercardTotal();
        updatewirelessDriverAmexTotal();
        updatewirelessDriverTotal();
        updateMachineTotal();
        //Grand total Table
        updateCashTotal();
        updateGiftCertificateRedeemed();
        updateVisaTotal();
        updateMastercardTotal();
        updateAmexTotal();
        updateDebitTotal();
        updateGrandTotal();
        //cash over/short
        updateOverShort();
    }
    
    function updateOverShort(){
       var val=formGetValue("grandTotal_cell_1")-formGetValue("totalReceipts_cell_1");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("overShort_cell_1",val);       
    }
    
    function updateCashTotal(){
       var val=formGetValue("actualCash");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("cashTotal_cell_1",val);        
    }
    
    function updateGiftCertificateRedeemed(){
       var val=formGetValue("giftCertificateRedeemed");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("giftCertificateRedeemed_cell_2",val);     
    }
    
    function updateVisaTotal(){
       var val=formGetValue("inStoreVisaTotal_cell_1")+formGetValue("wirelessDriverVisaTotal_cell_1");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("visaTotal_cell_1",val);
       dwr.util.setValue("visaTotal_cell_2",val);     
    }
    
    function updateMastercardTotal(){
       var val=formGetValue("inStoreMastercardTotal_cell_1")+formGetValue("wirelessDriverMastercardTotal_cell_1");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("mastercardTotal_cell_1",val);
       dwr.util.setValue("mastercardTotal_cell_2",val);     
    }
    
    function updateAmexTotal(){
       var val=formGetValue("inStoreAmexTotal_cell_1")+formGetValue("wirelessDriverAmexTotal_cell_1");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("amexTotal_cell_1",val);
       dwr.util.setValue("amexTotal_cell_2",val);    
    }
    
    function updateDebitTotal(){
       var val=formGetValue("inStoreDebitTotal_cell_1")+formGetValue("wirelessDriverDebitTotal_cell_1");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("debitTotal_cell_1",val);
       dwr.util.setValue("debitTotal_cell_2",val);      
    }
    
    function updateMachineTotal(){
       var val=formGetValue("inStoreTotal_cell_1")+formGetValue("wirelessDriverTotal_cell_1");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("machineTotal_cell_1",val);
    }
    
    function updateGrandTotal(){
       var val=formGetValue("actualCash")+formGetValue("giftCertificateRedeemed")+formGetValue("inStoreTotal_cell_1")+formGetValue("wirelessDriverTotal_cell_1");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("grandTotal_cell_1",val);
    }
    
    function updatewirelessDriverTotal(){
       var val=formGetValue("wirelessDriverDebitTotal_cell_1")+formGetValue("wirelessDriverVisaTotal_cell_1")+formGetValue("wirelessDriverMastercardTotal_cell_1")+formGetValue("wirelessDriverAmexTotal_cell_1");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("wirelessDriverTotal_cell_1",val); 
       dwr.util.setValue("wirelessDriverTotal_cell_2",val);      
    }
    
    function updateInStoreTotal(){
       var val=formGetValue("inStoreVisaTotal_cell_1")+formGetValue("inStoreMastercardTotal_cell_1")+formGetValue("inStoreAmexTotal_cell_1")+formGetValue("inStoreDebitTotal_cell_1");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("inStoreTotal_cell_1",val);
       dwr.util.setValue("inStoreTotal_cell_2",val);     
    }
    
    function updateActualCash(){
     if(dwr.util.getValue("version") >1){
     var val=formGetValue("bills_cell_1")+formGetValue("coins_cell_1")+formGetValue("chequesTotal");
     val=RoundToTwoDigit(val);
     dwr.util.setValue("actualCash_cell_span_2",val);
     dwr.util.setValue("actualCash_cell_1",val);
     dwr.util.setValue("actualCash",val);
     }
     else
     dwr.util.setValue("actualCash_cell_1",dwr.util.getValue("actualCash"));
    }
    
    function updateCoinsTotal(){
     var val=formGetValue("twoDollarBill_total_span_1")+formGetValue("oneDollarBill_total_span_1")+formGetValue("twentyFiveCentBill_total_span_1")+formGetValue("tenCentBill_total_span_1")+formGetValue("fiveCentBill_total_span_1")+formGetValue("oneCentBill_total_span_1");
     val=RoundToTwoDigit(val);
     dwr.util.setValue("coins_cell_1",val);    
    }
    
    function updateOneCentBill(){
      var val=formGetIntegerValue("oneCentBill")*0.01;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("oneCentBill_total_span_1",val);
    }

    function updateFiveCentBill(){
      var val=formGetIntegerValue("fiveCentBill")*0.05;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("fiveCentBill_total_span_1",val);
    }

    function updateTenCentBill(){
      var val=formGetIntegerValue("tenCentBill")*0.1;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("tenCentBill_total_span_1",val);
    }

    function updateTwentyFiveCentBill(){
      var val=formGetIntegerValue("twentyFiveCentBill")*0.25;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("twentyFiveCentBill_total_span_1",val);
    }

    function updateOneDollarBill(){
      var val=formGetIntegerValue("oneDollarBill");
      val=RoundToTwoDigit(val);
      dwr.util.setValue("oneDollarBill_total_span_1",val);
    }

    function updateTwoDollarBill(){
      var val=formGetIntegerValue("twoDollarBill")*2;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("twoDollarBill_total_span_1",val);
    }
    
    function updateBillsTotal(){
     var val=formGetValue("oneHundredDollarBill_total_span_1")+formGetValue("fiftyDollarBill_total_span_1")+formGetValue("twentyDollarBill_total_span_1")+formGetValue("tenDollarBill_total_span_1")+formGetValue("fiveDollarBill_total_span_1");
     val=RoundToTwoDigit(val);
     dwr.util.setValue("bills_cell_1",val);    
    }

    function updateFiveDollarBill(){
      var val=formGetIntegerValue("fiveDollarBill")*5;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("fiveDollarBill_total_span_1",val);
    }

    function updateTenDollarBill(){
      var val=formGetIntegerValue("tenDollarBill")*10;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("tenDollarBill_total_span_1",val);
    }

    function updateTwentyDollarBill(){
      var val=formGetIntegerValue("twentyDollarBill")*20;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("twentyDollarBill_total_span_1",val);
    }

    function updateFiftyDollarBill(){
      var val=formGetIntegerValue("fiftyDollarBill")*50;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("fiftyDollarBill_total_span_1",val);
    }
    
    function updateOneHundredDollarBill(){
      var val=formGetIntegerValue("oneHundredDollarBill")*100;
      val=RoundToTwoDigit(val);
      dwr.util.setValue("oneHundredDollarBill_total_span_1",val);
    }
    
    function updateGrossSales(){
       var val=formGetValue("netSales_cell_1")+formGetValue("gst_cell_2");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("grossSales_cell_1",val);      
    }
    
    function updateNetSales(){
       var val=formGetValue("netToPizza73_cell_2")+formGetValue("discountsAndAdvertising")+formGetValue("coupons");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("netSales_cell_1",val); 
    }
    
    function updateGST(){
      val=RoundToTwoDigit(dwr.util.getValue("gst"));
      dwr.util.setValue("gst_cell_2",val);
    }
    
    function updateTotalReceipts(){
     var val=formGetValue("netToPizza73_cell_1")+formGetValue("gst") +formGetValue("giftCertificateSold");
     val=RoundToTwoDigit(val);
     dwr.util.setValue("totalReceipts_cell_1",val);
    }
    
    function updateNetToPizza73(){
       var val=formGetValue("computerSales")+formGetValue("walkInSales")+formGetValue("miscSales")-formGetValue("returns");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("netToPizza73_cell_1",val);
       dwr.util.setValue("netToPizza73_cell_2",val);
    }
    
    function formGetValue(nodeId){
       val=dwr.util.getValue(nodeId);
       if(!validateDigitField(val))
           val="0";       
       return Number(val);
    }

    function formGetIntegerValue(nodeId){
       val=dwr.util.getValue(nodeId);
       if(!validateNonNegativeInteger(val))
           val="0";       
       return Number(val);
    }
    
    function formatDigit(formNode){
       if(validateDigitField(formNode.value))
         formNode.value=Number(formNode.value).toFixed(2);
    }
    
    function formatIntegerDigit(formNode){
        formNode.value=dojo.trim(formNode.value);
    }
    
    function RoundToTwoDigit(number) {
    var shift=100;
    var rounded=Math.round(number * shift)/100;
    return rounded.toFixed(2);    
}

    function formatBatchRecords(){
      autoZeroFill();
      BatchRecordsValueReplace("debit");
      BatchRecordsValueReplace("visa");
      BatchRecordsValueReplace("mastercard");
      BatchRecordsValueReplace("amex");
      BatchRecordsValueReplace("batchNumber");
    }
    
    function updateBatchRecordTotal(index){
	     var AllEmpty=true;
	     var node=dojo.byId("BatchRecord_"+index);
	     var inputNodes=dojo.query("input",node);
	     var i=0;	     
	     for(i=0;i<inputNodes.length;i++)
	         AllEmpty=AllEmpty && isEmpty(inputNodes[i].value);
	    var val='';
	    index--;
	    if(AllEmpty != true){
       val=formGetValue("BatchRecords_"+index+"_visa")+formGetValue("BatchRecords_"+index+"_mastercard")+formGetValue("BatchRecords_"+index+"_amex")+formGetValue("BatchRecords_"+index+"_debit");
       val=RoundToTwoDigit(val);
       }
       dwr.util.setValue("BatchRecord_total_cell_"+index,val);	    
	}
	
	function navigationRegistor(formNode){
	   var inputNodes=dojo.query("input",formNode);
	     var i=0;	     
	     for(i=0;i<inputNodes.length;i++)
	        if(inputNodes[i].type != 'submit')
	        dojo.connect(inputNodes[i],'onkeypress',navigationProcess);
	        else
	        dojo.connect(inputNodes[i],'onkeypress',navigationSubmit);	   
	}

	function navigationSubmit(event){
	   key = event.keyCode;
	   if(key == dojo.keys.PAGE_DOWN){
	       navigationDown(event.target);
	       dojo.stopEvent(event);
	   }
	   else if (key == dojo.keys.PAGE_UP){
	       navigationUp(event.target);
	       dojo.stopEvent(event);
	   }
	   return true;
	}	
	
	function navigationProcess(event){
	   key = event.keyCode;
	   if(key == dojo.keys.PAGE_DOWN || key ==dojo.keys.NUMPAD_ENTER ||  key ==dojo.keys.ENTER){
	       navigationDown(event.target);
	       dojo.stopEvent(event);
	   }
	   else if (key == dojo.keys.PAGE_UP){
	       navigationUp(event.target);
	       dojo.stopEvent(event);
	   }
	   return true;
	}
	
	function navigationDown(node){
	   var form=dojo.byId('dailySales');
	   var index=dojo.indexOf(form,node);
	   index++;
	   if(index<form.length)
	   form[index].focus();
	}
	
    function navigationUp(node){
	   var form=dojo.byId('dailySales');
	   var index=dojo.indexOf(form,node);
	   if(index >0){
	   index--;
	   if(form[index].type !='hidden')
	   form[index].focus();
	   }	   
	}
	
	function updateInStoreAmexTotal(){
       var val=0;
       var num=globalObj.numberOfFrontCounterMachines;
       for(i=0;i<num;i++)
       val=val+formGetValue("BatchRecords_"+i+"_amex");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("inStoreAmexTotal_cell_1",val);
       dwr.util.setValue("inStoreAmexTotal_cell_2",val); 
    }

    function updateInStoreMastercardTotal(){
       var val=0;
       var num=globalObj.numberOfFrontCounterMachines;
       for(i=0;i<num;i++)
         val=val+formGetValue("BatchRecords_"+i+"_mastercard");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("inStoreMastercardTotal_cell_1",val);
       dwr.util.setValue("inStoreMastercardTotal_cell_2",val); 
    }

    function updateInStoreVisaTotal(){
       var val=0;
       var num=globalObj.numberOfFrontCounterMachines;
       for(i=0;i<num;i++)
         val=val+formGetValue("BatchRecords_"+i+"_visa");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("inStoreVisaTotal_cell_1",val);
       dwr.util.setValue("inStoreVisaTotal_cell_2",val); 
    }
    
    function updateInStoreDebitTotal(){
       var val=0;
       var num=globalObj.numberOfFrontCounterMachines;
       for(i=0;i<num;i++)
         val=val+formGetValue("BatchRecords_"+i+"_debit");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("inStoreDebitTotal_cell_1",val);
       dwr.util.setValue("inStoreDebitTotal_cell_2",val); 
    }    
    
    function updatewirelessDriverAmexTotal(){
       var val=0;
       var numberofFrontCounterMachines=globalObj.numberOfFrontCounterMachines;
       var numberofWirelessMachines=globalObj.numberOfWirlessMachines
       for(i=numberofFrontCounterMachines;i<(numberofFrontCounterMachines+numberofWirelessMachines);i++)
       val=val+formGetValue("BatchRecords_"+i+"_amex");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("wirelessDriverAmexTotal_cell_1",val);
       dwr.util.setValue("wirelessDriverAmexTotal_cell_2",val); 
    }

    function updatewirelessDriverMastercardTotal(){
       var val=0;
       var numberofFrontCounterMachines=globalObj.numberOfFrontCounterMachines;
       var numberofWirelessMachines=globalObj.numberOfWirlessMachines
       for(i=numberofFrontCounterMachines;i<(numberofFrontCounterMachines+numberofWirelessMachines);i++)
         val=val+formGetValue("BatchRecords_"+i+"_mastercard");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("wirelessDriverMastercardTotal_cell_1",val);
       dwr.util.setValue("wirelessDriverMastercardTotal_cell_2",val); 
    }

    function updatewirelessDriverVisaTotal(){
       var val=0;
       var numberofFrontCounterMachines=globalObj.numberOfFrontCounterMachines;
       var numberofWirelessMachines=globalObj.numberOfWirlessMachines
       for(i=numberofFrontCounterMachines;i<(numberofFrontCounterMachines+numberofWirelessMachines);i++)
         val=val+formGetValue("BatchRecords_"+i+"_visa");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("wirelessDriverVisaTotal_cell_1",val);
       dwr.util.setValue("wirelessDriverVisaTotal_cell_2",val); 
    }
    
    function updatewirelessDriverDebitTotal(){
       var val=0;
       var numberofFrontCounterMachines=globalObj.numberOfFrontCounterMachines;
       var numberofWirelessMachines=globalObj.numberOfWirlessMachines
       for(i=numberofFrontCounterMachines;i<(numberofFrontCounterMachines+numberofWirelessMachines);i++)
         val=val+formGetValue("BatchRecords_"+i+"_debit");
       val=RoundToTwoDigit(val);
       dwr.util.setValue("wirelessDriverDebitTotal_cell_1",val);
       dwr.util.setValue("wirelessDriverDebitTotal_cell_2",val); 
    }
	
	function BatchRecordsValueReplace(field){
       var temp=new Array();
       var val='';
       var i=0;
       var numberofFrontCounterMachines=globalObj.numberOfFrontCounterMachines;
       var numberofWirelessMachines=globalObj.numberOfWirlessMachines
       var totalNum=numberofFrontCounterMachines+numberofWirelessMachines;
       for(i=0;i<numberofFrontCounterMachines;i++){
         val=dojo.byId("BatchRecords_"+i+"_"+field).value;
         if(!isEmpty(val))
           temp.push(val);
       }
       for(i=0;i<numberofFrontCounterMachines;i++){
         if(i<temp.length)
         dojo.byId("BatchRecords_"+i+"_"+field).value=temp[i];
         else
         dojo.byId("BatchRecords_"+i+"_"+field).value='';
       } 
       var temp=new Array();
       var val=null;
       var i=0;
       for(i=numberofFrontCounterMachines;i<totalNum;i++){
         val=dojo.byId("BatchRecords_"+i+"_"+field).value;
         if(!isEmpty(val))
           temp.push(val);
       }
       for(i=numberofFrontCounterMachines;i<totalNum;i++){
         if((i-numberofFrontCounterMachines)<temp.length)
         dojo.byId("BatchRecords_"+i+"_"+field).value=temp[i-numberofFrontCounterMachines];
         else
         dojo.byId("BatchRecords_"+i+"_"+field).value='';
       }          
    }
    
    function updateBatchRecordTotals(){
      var i=1;
      var num=globalObj.numberOfFrontCounterMachines+globalObj.numberOfWirlessMachines
      for(i=1;i<=num;i++)
         updateBatchRecordTotal(i);
    }
    
    function autoZeroFill(){
	  var AllEmpty=true;
	  var header="BatchRecord_"
	  var index=1;
	  var inputNodes=null;
	  var i=0;
	  var num=globalObj.numberOfFrontCounterMachines+globalObj.numberOfWirlessMachines
	  for(;index<=num;index++){
	     inputNodes=dojo.query("input",dojo.byId(header+index));
	     i=0;
	     AllEmpty=true;     
	     for(i=0;i<inputNodes.length;i++){
	         AllEmpty=AllEmpty && isEmpty(inputNodes[i].value);	         
	     }
	     if(AllEmpty != true){
	        for(i=0;i<inputNodes.length;i++){
	            if(isEmpty(inputNodes[i].value) == true && inputNodes[i].id.indexOf('batchNumber') <0)
	              inputNodes[i].value="0.00";
	        }
	     }
	 }	         
    }
