
#Region ApplicationInterface

Function GetStores() Export
	
	ChatBotService.InfoLog(
		NStr("ru = 'Начало формирования сообщения о магазинах.'"));

	SetPrivilegedMode(True);
	Stores = ChatBotOverdiable.GetStores();
	Selection = Stores.Select();
	Result = New Array;
	While Selection.Next() Do
		Store = EmptyStoreDescription();
		Store.uuid = GetUuidByRef(Selection.Ref);
		Store.name = Selection.Name;
		Store.address = Selection.Address;
		Store.phoneNumber = Selection.Phone;
		Result.Add(Store);
	EndDo;
	
	ChatBotService.InfoLog(
		NStr("ru = 'Окончание формирования сообщения о магазинах.'"));
	
	Return New Structure("Id, Data", "", DataToJson(Result));
			
EndFunction

Function GetProducts(MessageId) Export
		
	ChatBotService.InfoLog(
		NStr("ru = 'Начало формирования сообщения о товарах.'"));
	
	SetPrivilegedMode(True);
	Node = ExchangePlans.ExchangeChatBot.GetNodeByDefault();
	If Not ValueIsFilled(Node) Then
		ExchangePlans.ExchangeChatBot.InitNodes();
		Node = ExchangePlans.ExchangeChatBot.GetNodeByDefault();
		ExchangePlans.ExchangeChatBot.PrepareForInitialExchange();
	Else
		DeletePreviousMessage(MessageId);
		Message = GetSavedMessage();
		If Not IsBlankString(Message.Data) Then
			ChatBotService.InfoLog(
				StrTemplate(NStr("ru = 'Сообщение о товарах %1 отправлено.'"), Message.Id));
			ChatBotService.InfoLog(
				NStr("ru = 'Окончание формирования сообщения о товарах.'"));
			Return Message;
		EndIf;
	EndIf;
	
	ProcessChangedProducts(Node);
	
	Message = GetSavedMessage();
	If Not IsBlankString(Message.Data) Then
		ChatBotService.InfoLog(
			StrTemplate(NStr("ru = 'Сообщение о товарах %1 отправлено.'"), Message.Id));
	EndIf;
	ChatBotService.InfoLog(
		NStr("ru = 'Окончание формирования сообщения о товарах.'"));
	
	Return Message;
	
EndFunction

#EndRegion

#Region InternalFunctions

Procedure ProcessChangedProducts(Node)
	
	ChangedProducts = ChatBotOverdiable.GetProducts();
	ProductSelection = ChangedProducts.Select(QueryResultIteration.ByGroups);
	Products = New Array();
	ProductsForCancelling = New Array();
	ProductsCountPerMessage = 100;
	While ProductSelection.Next() Do
		
		ProductsForCancelling.Add(ProductSelection.Product);
		ProductUuid = GetUuidByRef(ProductSelection.Product);
		Product = EmptyProductDescription();
		Product.uuid = ProductUuid;
		Product.name = ProductSelection.Name;
		Product.synonym = Lower(ProductSelection.Synonym);
		
		ProductsInStoresSelection = ProductSelection.Select();
		ProductsInStores = New Array;
		While ProductsInStoresSelection.Next() Do
			StoreUuid = GetUuidByRef(ProductsInStoresSelection.Store);
			ProductInStore = EmptyProductInStoreDescription();
			ProductInStore.uuid = ProductUuid+"#"+StoreUuid;
			ProductInStore.product = New Structure("uuid", ProductUuid);
			ProductInStore.store = New Structure("uuid", StoreUuid);
			ProductInStore.price = ProductsInStoresSelection.Price;
			ProductInStore.quantity = ProductsInStoresSelection.Balance;
			ProductsInStores.Add(ProductInStore);
		EndDo;
		Product.Insert("productsInStores", ProductsInStores);
		Products.Add(Product);
		
		If Products.Count() = ProductsCountPerMessage Then
			FlushMessage(Node, Products, ProductsForCancelling);
		EndIf;
		
	EndDo;
	
	If Products.Count() > 0 Then
		FlushMessage(Node, Products, ProductsForCancelling);
	EndIf;

EndProcedure

Procedure DeletePreviousMessage(MessageId)
	
	If IsBlankString(MessageId) Then
		Return;		
	EndIf;
	
	ChatBotService.InfoLog(
		StrTemplate(NStr("ru = 'Удаление сообщения %1.'"), MessageId));

	RecordSet = InformationRegisters.ChatBotMessages.CreateRecordSet();
	RecordSet.Filter.Id.Set(MessageId);
	RecordSet.Read();
	If RecordSet.Count() = 0 Then
		ChatBotService.WarnLog(
			StrTemplate(NStr("ru = 'Сообщение %1 не было удалено.'"), MessageId));			
	EndIf;
	RecordSet.Clear();	
	RecordSet.Write();	
	
EndProcedure

Function GetSavedMessage()
	
	Запрос = Новый Запрос;
	Запрос.Текст = 
		"ВЫБРАТЬ ПЕРВЫЕ 1
		|	ChatBotMessages.Message КАК Message,
		|	ChatBotMessages.Id КАК Id
		|ИЗ
		|	РегистрСведений.ChatBotMessages КАК ChatBotMessages";
	
	QueryResult = Запрос.Выполнить();
	If QueryResult.IsEmpty() Then
		Return New Structure("Id, Data", "", "");		
	EndIf;
	
	Selection = QueryResult.Выбрать();
	Selection.Следующий();
	Return New Structure("Id, Data", Selection.Id, Selection.Message.Get());
	
EndFunction

Procedure FlushMessage(Node, Products, ProductsForCancelling)

	Message = DataToJson(Products);	
	MessageId = GenerateMessageId(Message);
	WriteMessage(Message, MessageId);
	Products.Clear();
	CancelRegistration(Node, ProductsForCancelling);
	
EndProcedure

Function GenerateMessageId(Message)
	
	MemoryStream = New MemoryStream;
	Writer 		 = New DataWriter(MemoryStream);
	Writer.WriteChars(Message, TextEncoding.UTF8);
	BinaryData = MemoryStream.CloseAndGetBinaryData();
	
	DataHashing = New DataHashing(HashFunction.MD5);
	DataHashing.Append(BinaryData);
	
	Return Base64String(DataHashing.HashSum);
	
EndFunction

Procedure CancelRegistration(Node, Array)
	
	For Each Ref In Array Do
		ExchangePlans.DeleteChangeRecords(Node, Ref);
	EndDo;
	Array.Clear();
	
EndProcedure

Procedure WriteMessage(Message, MessageId)
	
	NewRecord = InformationRegisters.ChatBotMessages.CreateRecordManager();
	NewRecord.Id = MessageId;
	NewRecord.Message = New ValueStorage(Message);
	NewRecord.Write();
	
	ChatBotService.InfoLog(
		StrTemplate(NStr("ru = 'Сообщение %1 записано.'"), MessageId));

EndProcedure

Function EmptyStoreDescription()
	Return New Structure("uuid, name, address, phoneNumber");	
EndFunction

Function EmptyProductDescription()
	Return New Structure("uuid, name, synonym");	
EndFunction

Function EmptyProductInStoreDescription()
	Return New Structure("uuid, product, store, price, quantity");	
EndFunction

Function GetUuidByRef(Ref)
	Return String(Ref.UUID());	
EndFunction

Function DataToJson(Data) 
		
	JSONWriter = New JSONWriter;
	JSONWriter.SetString();
	WriteJSON(JSONWriter, Data);
	Return JSONWriter.Close();
	
EndFunction	

#EndRegion

