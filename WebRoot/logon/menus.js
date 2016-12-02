 /***********************************************************************************
*	(c) Ger Versluis 2000 version 8.2 24 April 2002	          *
*	You may use this script on non commercial sites.	          *
*	For info write to menus@burmees.nl		          *
*	You may remove all comments for faster loading	          *		
***********************************************************************************/
//	var NoOffFirstLineMenus=7;		// Number of main menu  items
						// Colorvariables:
						// Color variables take HTML predefined color names or "#rrggbb" strings
						//For transparency make colors and border color ""
	var LowBgColor="#FFFFFF";			// Background color when mouse is not over
	var HighBgColor="#013835";		// Background color when mouse is over
	var FontLowColor="#013835";		// Font color when mouse is not over
	var FontHighColor="White";		// Font color when mouse is over
	var BorderColor="black";		// Border color
	var BorderWidth=1;			// Border width
	var BorderBtwnElmnts=1;			// Border between elements 1 or 0
	var FontFamily="宋体"	// Font family menu items
	var FontSize=9;				// Font size menu items
	var FontBold=0;				// Bold menu items 1 or 0
	var FontItalic=0;			// Italic menu items 1 or 0
	var MenuTextCentered="center";		// Item text position left, center or right
	var MenuCentered="center";		// Menu horizontal position can be: left, center, right, justify,
						//  leftjustify, centerjustify or rightjustify. PartOfWindow determines part of window to use
	var MenuVerticalCentered="top";	// Menu vertical position top, middle,bottom or static
	var ChildOverlap=.1;			// horizontal overlap child/ parent
	var ChildVerticalOverlap=.2;		// vertical overlap child/ parent
	var StartTop=0;				// Menu offset x coordinate
	var StartLeft=0;			// Menu offset y coordinate
	var VerCorrect=0;			// Multiple frames y correction
	var HorCorrect=0;			// Multiple frames x correction
	var LeftPaddng=3;			// Left padding
	var TopPaddng=2;			// Top padding
	var FirstLineHorizontal=1;		// First level items layout horizontal 1 or 0
	var MenuFramesVertical=1;		// Frames in cols or rows 1 or 0
	var DissapearDelay=1000;			// delay before menu folds in
	var UnfoldDelay=100;			// delay before sub unfolds	
	var TakeOverBgColor=1;			// Menu frame takes over background color subitem frame
	var FirstLineFrame="fraMenu";		// Frame where first level appears
	var SecLineFrame="fraInterface";	// Frame where sub levels appear
	var DocTargetFrame="fraInterface";	// Frame where target documents appear
	var TargetLoc="";			// span id for relative positioning
	var MenuWrap=1;				// enables/ disables menu wrap 1 or 0
	var RightToLeft=0;			// enables/ disables right to left unfold 1 or 0
	var BottomUp=0;				// enables/ disables Bottom up unfold 1 or 0
	var UnfoldsOnClick=0;			// Level 1 unfolds onclick/ onmouseover
	var BaseHref="";			// BaseHref lets you specify the root directory for relative links. 
						// The script precedes your relative links with BaseHref
						// For instance: 
						// when your BaseHref= "http://www.MyDomain/" and a link in the menu is "subdir/MyFile.htm",
						// the script renders to: "http://www.MyDomain/subdir/MyFile.htm"
						// Can also be used when you use images in the textfields of the menu
						// "MenuX=new Array("<img src=\""+BaseHref+"MyImage\">"
						// For testing on your harddisk use syntax like: BaseHref="file:///C|/MyFiles/Homepage/"

	var Arrws=["../common/images/tri.gif",5,10,"../tridown.gif",10,5,"../trileft.gif",5,10,"../triup.gif",10,5];

						// Arrow source, width and height.
						// If arrow images are not needed keep source ""

	var MenuUsesFrames=1;			// MenuUsesFrames is only 0 when Main menu, submenus,
						// document targets and script are in the same frame.
						// In all other cases it must be 1

	var RememberStatus=0;			// RememberStatus: When set to 1, menu unfolds to the presetted menu item. 
						// When set to 2 only the relevant main item stays highligthed
						// The preset is done by setting a variable in the head section of the target document.
						// <head>
						//	<script type="text/javascript">var SetMenu="2_2_1";</script>
						// </head>
						// 2_2_1 represents the menu item Menu2_2_1=new Array(.......

	var PartOfWindow=.8;				// PartOfWindow: When MenuCentered is justify, sets part of window width to stretch to

						// Below some pretty useless effects, since only IE6+ supports them
						// I provided 3 effects: MenuSlide, MenuShadow and MenuOpacity
						// If you don't need MenuSlide just leave in the line var MenuSlide="";
						// delete the other MenuSlide statements
						// In general leave the MenuSlide you need in and delete the others.
						// Above is also valid for MenuShadow and MenuOpacity
						// You can also use other effects by specifying another filter for MenuShadow and MenuOpacity.
						// You can add more filters by concanating the strings
	var MenuSlide="";
	var MenuSlide="progid:DXImageTransform.Microsoft.RevealTrans(duration=.5, transition=19)";
	var MenuSlide="progid:DXImageTransform.Microsoft.GradientWipe(duration=.5, wipeStyle=1)";

	var MenuShadow="";
	var MenuShadow="progid:DXImageTransform.Microsoft.DropShadow(color=#888888, offX=2, offY=2, positive=1)";
	var MenuShadow="progid:DXImageTransform.Microsoft.Shadow(color=#888888, direction=135, strength=3)";

	var MenuOpacity="";
	var MenuOpacity="progid:DXImageTransform.Microsoft.Alpha(opacity=75)";
	
	//其实长度也可以规定
	var MenuHeight	//主菜单的高度
	var MenuHeightChild	//子菜单的高度
	if (screen.width == 1024){
		MenuHeight = 18;
		MenuHeightChild = 18;
		MenuHeightBottom = 768-17*18;
	}
	else{
		MenuHeight = 18;
		MenuHeightChild = 18;
		MenuHeightBottom = 600-17*18;
	}

	function BeforeStart(){return}
	function AfterBuild(){return}
	function BeforeFirstOpen(){return}
	function AfterCloseAll(){return}

// Menu tree:菜单的明细介绍，包括个个变量的作用，和方法
// MenuX=new Array("ItemText","Link","background image",number of sub elements,height,width,"bgcolor","bghighcolor",
//	"fontcolor","fonthighcolor","bordercolor","fontfamily",fontsize,fontbold,fontitalic,"textalign","statustext");
// Color and font variables defined in the menu tree take precedence over the global variables
// Fontsize, fontbold and fontitalic are ignored when set to -1.
// For rollover images ItemText format is:  "rollover?"+BaseHref+"Image1.jpg?"+BaseHref+"Image2.jpg" 

