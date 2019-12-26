
#Region ApplicationInterface

Procedure ChatBotProdutsInStoresOnWrite(Source) Export

	If Source.Count() = 0
		Or Source.DataExchange.Load Then
		Return;
	EndIf;
	
	SetPrivilegedMode(True);
	Node = ExchangePlans.ExchangeChatBot.GetNodeByDefault();
	If Not ValueIsFilled(Node) Then
		Return;
	EndIf;
	
	Products = ChatBotOverdiable.GetProductsForRecordingChanges(Source);
	Selection = Products.Select();
	While Selection.Next() Do
		ExchangePlans.RecordChanges(Node, Selection.Ref); 
	EndDo;
	
EndProcedure

#EndRegion

