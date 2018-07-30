// 请勿修改，否则可能出错
var Syse = {};
var ua = navigator.userAgent.toLowerCase();
var s;
(s = ua.match(/msie ([\d.]+)/)) ? Syse.ie = s[1] :
(s = ua.match(/firefox\/([\d.]+)/)) ? Syse.firefox = s[1] :
(s = ua.match(/chrome\/([\d.]+)/)) ? Syse.chrome = s[1] :
(s = ua.match(/opera.([\d.]+)/)) ? Syse.opera = s[1] :
(s = ua.match(/version\/([\d.]+).*safari/)) ? Syse.safari = s[1] : 0;
/*
谷歌浏览器事件接管
*/
function OnComplete2(type,code,html)
{
	//alert(type);
	//alert(code);
//	alert(html);
//	alert("SaveToURL成功回调");
}
function OnComplete(type,code,html)
{
	//alert(type);
	//alert(code);
//	alert(html);
//	alert("BeginOpenFromURL成功回调");
}
function OnComplete3(str, doc) {

    TANGER_OCX_OBJ.activeDocument.saved = true; //saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.

    //获取文档控件中打开的文档的文档类型
//    alert(TANGER_OCX_OBJ.doctype);
//    switch (TANGER_OCX_OBJ.doctype) {
//        case 1:
//            fileType = "Word.Document";
//            fileTypeSimple = "wrod";
//            break;
//        case 2:
//            fileType = "Excel.Sheet";
//            fileTypeSimple = "excel";
//            break;
//        case 3:
//            fileType = "PowerPoint.Show";
//            fileTypeSimple = "ppt";
//            break;
//        case 4:
//            fileType = "Visio.Drawing";
//            break;
//        case 5:
//            fileType = "MSProject.Project";
//            break;
//        case 6:
//            fileType = "WPS Doc";
//            fileTypeSimple = "wps";
//            break;
//        case 7:
//            fileType = "Kingsoft Sheet";
//            fileTypeSimple = "et";
//            break;
//        default:
//            fileType = "unkownfiletype";
//            fileTypeSimple = "unkownfiletype";
//    }
    setFileOpenedOrClosed(true);

    TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = true; //设置是否保留痕迹
    TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = true; //设置是否显示痕迹

//    alert(TANGER_OCX_OBJ.ActiveDocument.TrackRevisions);
//    alert(TANGER_OCX_OBJ.ActiveDocument.ShowRevisions);
//    var prop = TANGER_OCX_OBJ.ActiveDocument.BuiltInDocumentProperties(1); //这一句不一样

    getcountgoogle();
}

function getcountgoogle() {
    if (document.getElementById("txtPrimalAmount").value == "") {
        document.getElementById("txtPrimalAmount").value = TANGER_OCX_OBJ.ActiveDocument.ComputeStatistics(3, true);

    }
    if (special != null) {
        if (enable == "false" || special == "1") {
            TANGER_OCX_OBJ.ActiveDocument.Saved = false;
            TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = false;

            TANGER_OCX_OBJ.SetReadOnly(true);
        }
        else {
            TANGER_OCX_OBJ.SetReadOnly(false);
            //TANGER_OCX_OBJ.ActiveDocument.Saved = true;
        }
    }
    else {
        if (enable == "false" ) {
            TANGER_OCX_OBJ.ActiveDocument.Saved = false;
            TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = false;

            TANGER_OCX_OBJ.SetReadOnly(true);
        }
        else {
            TANGER_OCX_OBJ.SetReadOnly(false);
            //TANGER_OCX_OBJ.ActiveDocument.Saved = true;
        }
    }
}
function publishashtml(type,code,html){
//	alert(html);
//	alert("Onpublishashtmltourl成功回调");
}
function publishaspdf(type,code,html){
//alert(html);
//alert("Onpublishaspdftourl成功回调");
}
function saveasotherurl(type,code,html){
//alert(html);
//alert("SaveAsOtherformattourl成功回调");
}
function dowebget(type,code,html){
//alert(html);
//alert("OnDoWebGet成功回调");
}
function webExecute(type,code,html){
//alert(html);
//alert("OnDoWebExecute成功回调");
}
function webExecute2(type,code,html){
//alert(html);
//alert("OnDoWebExecute2成功回调");
}
function FileCommand(TANGER_OCX_str, TANGER_OCX_obj) {
    if (TANGER_OCX_str == 3) {
        alert("不能保存！");
        TANGER_OCX_OBJ.CancelLastCommand = true;
    }
}
function CustomMenuCmd(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID){
alert("第" + menuPos +","+ submenuPos +","+ subsubmenuPos +"个菜单项,menuID="+menuID+",菜单标题为\""+menuCaption+"\"的命令被执行.");
}

var classid = 'C9BC4DFF-4248-4a3c-8A49-63A7D317F404';
if (Syse.ie) {
    document.write('<!-- 用来产生编辑状态的ActiveX控件的JS脚本-->   ');
    document.write('<!-- 因为微软的ActiveX新机制，需要一个外部引入的js-->   ');
    document.write('<object id="TANGER_OCX" classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404" ');
    document.write('codebase="/admin/js/ntko/OfficeControl.cab#version=5,0,2,9" width="100%" height="100%">   ');
    document.write('<param name="ProductCaption" value="江西教育传媒集团">    ');
    document.write('<param name="ProductKey" value="B246F8D24B330724CC08E92177CACD8E43B4CA28">   ');
    document.write('<param name="IsUseUTF8URL" value="-1">   ');
    document.write('<param name="IsUseUTF8Data" value="-1">   ');
    document.write('<param name="BorderStyle" value="1">   ');
    document.write('<param name="BorderColor" value="14402205">   ');
    document.write('<param name="TitlebarColor" value="15658734">   ');
    document.write('<param name="TitlebarTextColor" value="0">   ');
    document.write('<param name="MenubarColor" value="14402205">   ');
    document.write('<param name="MenuButtonColor" VALUE="16180947">   ');
    document.write('<param name="MenuBarStyle" value="3">   ');
    document.write('<param name="MenuButtonStyle" value="7">   ');
    document.write('<param name="Caption" value="审稿控件">   ');
    document.write('<param name="wmode" value="transparent">   ');
    document.write('<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   ');
    document.write('</object>');
}
else if (Syse.firefox) {
    document.write('<object id="TANGER_OCX" clsid="{C9BC4DFF-4248-4a3c-8A49-63A7D317F404}" type="application/ntko-plug"  codebase="OfficeControl.cab#version=5,0,2,9" width="100%" height="100%" ForOnSaveToURL="OnComplete2" ForOnBeginOpenFromURL="OnComplete" ForOndocumentopened="OnComplete3"');
    document.write('ForOnpublishAshtmltourl="publishashtml"');
    document.write('ForOnpublishAspdftourl="publishaspdf"');
    document.write('ForOnSaveAsOtherFormatToUrl="saveasotherurl"');
    document.write('ForOnDoWebGet="dowebget"');
    document.write('ForOnDoWebExecute="webExecute"');
    document.write('ForOnDoWebExecute2="webExecute2"');
    document.write('ForOnFileCommand="FileCommand"');
    document.write('ForOnCustomMenuCmd2="CustomMenuCmd"');
    document.write('_IsUseUTF8URL="-1"   ');
    document.write('_IsUseUTF8Data="-1"   ');
    document.write('_BorderStyle="1"   ');
    document.write('_BorderColor="14402205"   ');
    document.write('_MenubarColor="14402205"   ');
    document.write('_MenuButtonColor="16180947"   ');
    document.write('_MenuBarStyle="3"  ');
    document.write('_MenuButtonStyle="7"   ');
    document.write('_ProductCaption="江西教育传媒集团"    ');
    document.write('_ProductKey="B246F8D24B330724CC08E92177CACD8E43B4CA28"    ');

    document.write('_Caption="审稿控件">');
    document.write('<SPAN STYLE="color:red">尚未安装NTKO Web FireFox跨浏览器插件。请点击<a href="/admin/js/ntko/ntkoplugins.xpi">安装组件</a></SPAN>   ');
    document.write('</object>   ');
} else if (Syse.chrome) {
    document.write('<object id="TANGER_OCX" clsid="{C9BC4DFF-4248-4a3c-8A49-63A7D317F404}"  ForOnSaveToURL="OnComplete2" ForOnBeginOpenFromURL="OnComplete" ForOndocumentopened="OnComplete3"');
    document.write('ForOnpublishAshtmltourl="publishashtml"');
    document.write('ForOnpublishAspdftourl="publishaspdf"');
    document.write('ForOnSaveAsOtherFormatToUrl="saveasotherurl"');
    document.write('ForOnDoWebGet="dowebget"');
    document.write('ForOnDoWebExecute="webExecute"');
    document.write('ForOnDoWebExecute2="webExecute2"');
    document.write('ForOnFileCommand="FileCommand"');
    document.write('ForOnCustomMenuCmd2="CustomMenuCmd"');
    document.write('codebase="/admin/js/ntko/OfficeControl.cab#version=5,0,2,9" width="100%" height="100%" type="application/ntko-plug" ');
    document.write('_IsUseUTF8URL="-1"   ');
    document.write('_IsUseUTF8Data="-1"   ');
    document.write('_BorderStyle="1"   ');
    document.write('_BorderColor="14402205"   ');
    document.write('_MenubarColor="14402205"   ');
    document.write('_MenuButtonColor="16180947"   ');
    document.write('_MenuBarStyle="3"  ');
    document.write('_MenuButtonStyle="7"   ');
    document.write('_ProductCaption="江西教育传媒集团"    ');
    document.write('_ProductKey="B246F8D24B330724CC08E92177CACD8E43B4CA28"    ');
    document.write('<SPAN STYLE="color:red">尚未安装NTKO Web Chrome跨浏览器插件。请点击<a href="/admin/js/ntko/NtkoCrossBrowserSetup.msi">安装组件</a></SPAN>   ');
    document.write('</object>');
} else if (Syse.opera) {
    alert("sorry,ntko web印章暂时不支持opera!");
} else if (Syse.safari) {
    alert("sorry,ntko web印章暂时不支持safari!");
}

else {
    document.write('<!-- 用来产生编辑状态的ActiveX控件的JS脚本-->   ');
    document.write('<!-- 因为微软的ActiveX新机制，需要一个外部引入的js-->   ');
    document.write('<object id="TANGER_OCX" classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404"');
    document.write('codebase="/admin/js/ntko/OfficeControl.cab#version=5,0,2,9" width="100%" height="100%">   ');
    document.write('<param name="ProductCaption" value="江西教育传媒集团">    ');
    document.write('<param name="ProductKey" value="B246F8D24B330724CC08E92177CACD8E43B4CA28">   ');
    document.write('<param name="IsUseUTF8URL" value="-1">   ');
    document.write('<param name="IsUseUTF8Data" value="-1">   ');
    document.write('<param name="BorderStyle" value="1">   ');
    document.write('<param name="BorderColor" value="14402205">   ');
    document.write('<param name="TitlebarColor" value="15658734">   ');
    document.write('<param name="TitlebarTextColor" value="0">   ');
    document.write('<param name="MenubarColor" value="14402205">   ');
    document.write('<param name="MenuButtonColor" VALUE="16180947">   ');
    document.write('<param name="MenuBarStyle" value="3">   ');
    document.write('<param name="MenuButtonStyle" value="7">   ');
    document.write('<param name="Caption" value="审稿控件">   ');
    document.write('<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   ');
    document.write('</object>');
}