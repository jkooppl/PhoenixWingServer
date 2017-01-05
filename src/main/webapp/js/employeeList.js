/**
 * This file contains functions to dynamically update a customers cart.
 * 
 * Frameworks used: 
 * DWR (direct web remoting - http://getahead.ltd.uk/dwr/)
 *  - manages the remote calling of server side code.
 * 
 * YAHOO! UI (http://developer.yahoo.com/yui/)
 *  - javascript utilities to make various things simpler such as: event 
 *    management, effects, etc.
 * 
 * Jack Slocum YAHOO! UI extensions (http://jackslocum.com/yui/index.php)
 *  - more javascript utilities that add onto the YAHOO! UI.
 * 
 * NOTES: 
 * - Event listeners see http://developer.yahoo.com/yui/event/ for a better 
 *   understanding of how to associate listeners to events.  For the most part
 *   in this script we will be using custom objects to send in parameters to
 *   an event listeners callback function.
 */
var Y_EVENT = YAHOO.util.Event;
var initRequired = true;
//var Pizza = {};
function init(e)
{
  Cart.drawCartHtml(); 
}

function priceInit(){  
  Y_EVENT.onAvailable("contentReady", onPageLoadP);	   
}

function onPageLoadP(){
  Cart.TIMER_STARTED = false;
  initPrices();
  Cart.checkForEmptyCart();
  Cart.drawHtmlOrderButtons();
//  PIZZA73.setTall();
  Y_EVENT.onAvailable("startTimer", Cart.autoReload); 
}

function initPrices(){
    var priceAnchors = YAHOO.util.Dom.getElementsByClassName("price", "a");
    //remove listeners first
    for(i = 0; i < priceAnchors.length; i++)
    {
      var anchor = priceAnchors[i];
      YAHOO.util.Event.removeListener(anchor.id + "", "click");
    }
    for(i = 0; i < priceAnchors.length; i++)
    {
      var anchor = priceAnchors[i];
      var menuItem = 
        new PIZZA73_OBJ.MenuItem(anchor.id, anchor.id.substring(3));
      Y_EVENT.addListener(anchor.id + "", "click", addPizzaItem, menuItem);
    }
    
    priceAnchors = YAHOO.util.Dom.getElementsByClassName("promoBlank", "a");
    //remove listeners first
    for(i = 0; i < priceAnchors.length; i++)
    {
      var anchor = priceAnchors[i];
      YAHOO.util.Event.removeListener(anchor.id + "", "click");
    }
    for(i = 0; i < priceAnchors.length; i++)
    {
      var anchor = priceAnchors[i];
      var menuItem = 
        new PIZZA73_OBJ.MenuItem(anchor.id, anchor.id.substring(3));
      Y_EVENT.addListener(anchor.id + "", "click", addPizzaItem, menuItem);
    }
    var extraAnchors = YAHOO.util.Dom.getElementsByClassName("extras", "a");
    //remove listeners first
    for(i = 0; i < extraAnchors.length; i++)
    {
      var anchor = extraAnchors[i];
      YAHOO.util.Event.removeListener(anchor.id + "", "click");
    }
    
    for(i = 0; i < extraAnchors.length; i++)
    {
      var anchor = extraAnchors[i];
      var menuItem = new PIZZA73_OBJ.MenuItem(anchor.id, anchor.id.substring(3));
      Y_EVENT.addListener(anchor.id + "", "click", addPizzaItem, menuItem);
    }
}

//Pizza.addPizzaItem = function(e, obj) {
function addPizzaItem(e, obj){
  	Cart.addItemToCart(obj.menuItemId);
  	return false;
}

YAHOO.util.Event.addListener(window, 'load', init); 