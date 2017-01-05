/*
	Copyright (c) 2004-2007, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

if(!dojo._hasResource["dojo.cookie"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.cookie"] = true;
dojo.provide("dojo.cookie");

/*=====
dojo.__cookieProps = function(kwArgs){
	//	expires: Date|Number?
	//		If a number, seen as the number of days from today. If a date, the
	//		date past which the cookie is invalid. If expires is in the past,
	//		the cookie will be deleted If expires is left out or is 0, the
	//		cookie will expire when the browser closes.
	//	path: String?
	//		The path to use for the cookie.
	//	domain: String?
	//		The domain to use for the cookie.
	//	secure: Boolean?
	//		Whether to only send the cookie on secure connections
}
=====*/


dojo.cookie = function(/*String*/name, /*String?*/value, /*dojo.__cookieProps?*/props){
	//	summary: 
	//		Get or set a cookie.
	//	description:
	// 		If you pass in one argument, the the value of the cookie is returned
	//
	// 		If you pass in two arguments, the cookie value is set to the second
	// 		argument.
	//
	// 		If you pass in three arguments, the cookie value is set to the
	// 		second argument, and the options on the third argument are used for
	// 		extended properties on the cookie
	//	name:
	//		The name of the cookie
	//	value:
	//		Optional. The value for the cookie.
	//	props: 
	//		Optional additional properties for the cookie
	//	example:
	//		set a cookie with the JSON-serialized contents of an object which
	//		will expire 5 days from now:
	//	|	dojo.cookie("configObj", dojo.toJson(config), { expires: 5 });
	//	
	//	example:
	//		de-serialize a cookie back into a JavaScript object:
	//	|	var config = dojo.fromJson(dojo.cookie("configObj"));
	//	
	//	example:
	//		delete a cookie:
	//	|	dojo.cookie("configObj", null);
	var c = document.cookie;
	if(arguments.length == 1){
		var idx = c.lastIndexOf(name+'=');
		if(idx == -1){ return null; }
		var start = idx+name.length+1;
		var end = c.indexOf(';', idx+name.length+1);
		if(end == -1){ end = c.length; }
		return decodeURIComponent(c.substring(start, end)); 
	}else{
		props = props || {};
		value = encodeURIComponent(value);
		if(typeof(props.expires) == "number"){ 
			var d = new Date();
			d.setTime(d.getTime()+(props.expires*24*60*60*1000));
			props.expires = d;
		}
		document.cookie = name + "=" + value 
			+ (props.expires ? "; expires=" + props.expires.toUTCString() : "")
			+ (props.path ? "; path=" + props.path : "")
			+ (props.domain ? "; domain=" + props.domain : "")
			+ (props.secure ? "; secure" : "");
		return null;
	}
};

}

