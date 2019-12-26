
#Region ApplicationInterface

Procedure InfoLog(Text) Export
	
	WriteLogEvent(
		"ChatBot.Exchange",
		EventLogLevel.Information,
		,
		,
		Text);
	
EndProcedure
	
Procedure WarnLog(Text) Export
	
	WriteLogEvent(
		"ChatBot.Exchange",
		EventLogLevel.Warning,
		,
		,
		Text);
	
EndProcedure	

#EndRegion
