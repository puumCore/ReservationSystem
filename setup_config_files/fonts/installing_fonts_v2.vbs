Option Explicit
Dim objShell, objFSO, wshshell
Dim strFontSourcePath, objFolder, objFont, objNameSpace, objFile

Set objShell = CreateObject("Shell.Application")
Set wshshell = CreateObject("WScript.Shell")
Set objFSO = CreateObject("Scripting.Filesystemobject")

strFontSourcePath = Replace(WScript.ScriptFullName, WScript.ScriptName, "")

IF objFSO.FolderExists(strFontSourcePath) THEN 
    Set objNameSpace = objShell.NameSpace(strFontSourcePath)
    Set objFolder = objFSO.getFolder(strFontSourcePath)

    FOR EACH objFile in objFolder.Files
        IF LCase(right(objFile, 4)) = ".ttf" OR LCase(right(objFile, 4)) = ".otf" THEN
            IF objFSO.FileExists(wshshell.SpecialFolders("Fonts") & objFile.Name) = False THEN
               Set objFont = objNameSpace.ParseName(objFile.Name)
               objFont.InvokeVerb("Install")
               WScript.Echo "Installing a font"

               Set objFont = Nothing
            END IF
        END IF
    NEXT
ELSE
    WScript.Echo "Font Source Path does not exists"
END IF