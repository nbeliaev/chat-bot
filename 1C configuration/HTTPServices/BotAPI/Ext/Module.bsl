
Function GetStores(Request)
	
	ResponseMessage = ChatBot.GetStores();
	Response = New HTTPServiceResponse(200);
	Response.Headers.Insert("Content-Type",   "application/json; charset=UTF-8");
	Response.Headers.Insert("MessageId", ResponseMessage.Id);
	Response.SetBodyFromString(ResponseMessage.Data);
	Return Response;
	
EndFunction

Function GetPorducts(Request)
	
	MessageId = Request.Headers.Get("MessageId");
	MessageId = ?(MessageId = Undefined, "", MessageId);
	ResponseMessage = ChatBot.GetProducts(MessageId);
		
	Response = New HTTPServiceResponse(200);
	Response.Headers.Insert("Content-Type", "application/json; charset=UTF-8");
	Response.Headers.Insert("MessageId", ResponseMessage.Id);
	Response.SetBodyFromString(ResponseMessage.Data);
	Return Response;
	
EndFunction