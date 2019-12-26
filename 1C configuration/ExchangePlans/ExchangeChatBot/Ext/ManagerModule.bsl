#Region ApplicationInterface

Function GetNodeByDefault() Export
		
	Запрос = Новый Запрос;
	Запрос.Текст = 
		"ВЫБРАТЬ ПЕРВЫЕ 1
		|	ExchangeChatBot.Ссылка КАК Ссылка
		|ИЗ
		|	ПланОбмена.ExchangeChatBot КАК ExchangeChatBot
		|ГДЕ
		|	НЕ ExchangeChatBot.ПометкаУдаления
		|	И НЕ ExchangeChatBot.ЭтотУзел";
	
	РезультатЗапроса = Запрос.Выполнить();
	Если РезультатЗапроса.Пустой() Тогда
		Возврат ПланыОбмена.ExchangeChatBot.ПустаяСсылка();		
	КонецЕсли;
	
	Выборка = РезультатЗапроса.Выбрать();
	Выборка.Следующий();
	Возврат Выборка.Ссылка;
	
EndFunction

Procedure InitNodes() Export
	
	ChatBotService.InfoLog(
		NStr("ru = 'Инициализация узлов обмена.'"));
	
	MainNodeRef = ExchangePlans.ExchangeChatBot.ThisNode();
	MainNodeObject = MainNodeRef.GetObject();
	MainNodeObject.Code = "01";
	MainNodeObject.Description = NStr("ru = 'Главный узел'");
	MainNodeObject.Write();
	
	BotNodeObject = ExchangePlans.ExchangeChatBot.CreateNode();
	BotNodeObject.Code = "02";
	BotNodeObject.Description = NStr("ru = 'Бот'");
	BotNodeObject.Write();
	
EndProcedure

Procedure PrepareForInitialExchange() Export
	
	ChatBotService.InfoLog(
		NStr("ru = 'Регистрация данных для первичной выгрузки.'"));
	
	Node = GetNodeByDefault();
	Products = ChatBotOverdiable.GetProductsForInitialExchange();
	Selection = Products.Select();
	While Selection.Next() Do
		ExchangePlans.RecordChanges(Node, Selection.Ref); 
	EndDo;
	
EndProcedure

Procedure FillChagnedProducts(Table) Export
	
	Node = GetNodeByDefault();
	// always zero, coz this is one-way exchange
	MessageNumber = 0; 	
	Selection = ExchangePlans.SelectChanges(Node, MessageNumber);
	While Selection.Next() Do
		NewRow = Table.Add();
		NewRow.Product = Selection.Get().Ref;
	EndDo;
	
EndProcedure

#EndRegion