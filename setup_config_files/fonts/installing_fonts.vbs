Set ofso = CreateObject("Scripting.FileSystemObject")
SourceFolder = ofso.GetParentFolderName(Wscript.ScriptFullName)

Const FONTS = &H14&

Set objshell = CreateObject("Shell.Application")
Set oSource = objshell.NameSpace(SourceFolder)
Set oWinFonts = objshell.NameSpace(FONTS)

Set rxTTF = new RegExp
Set rxOTF = new RegExp

rxTTF.Pattern = "\.ttf$"
rxOTF.Pattern = "\.otf$"

FOR EACH FontFile in oSource.Items()
     IF rxTTF.Test(FontFile.Path) THEN 
     oWinFonts.CopyHere FontFile.Path
     ELSE IF rxOTF.Test(FontFile.Path) THEN 
     oWinFonts.CopyHere FontFile.Path
     END IF
NEXT