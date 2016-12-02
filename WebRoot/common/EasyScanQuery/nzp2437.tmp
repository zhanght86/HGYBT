//调试随动控制

var topX = 0;         //当前可视屏幕顶点坐标
var topY = 0;

var startX = 0;       //随动框左上角坐标
var startY = 0;

var endX = 0;         //随动框右下角坐标
var endY = 0;

var adjustX = 10;     //图片原点相对窗口原点的位移
var adjustY = 31; 

var showFlag = false; //显示随动框的标志

/**
 * 鼠标移动事件处理，实时显示随动框，以观察效果
 */
function mouseMove() { 
  endX = event.offsetX;
  endY = event.offsetY;
  
  if (showFlag) {
    //因为显示随动框时是使用图片的坐标轴，所以要加上图片相对窗口的位移
    top.fraInterface.showPosition(startX+adjustX, startY+adjustY, 
                                  endX-startX, endY-startY);   
  }
                                
  showCoordinate();         
}

/**
 * 按下鼠标事件处理，显示代码语句，拷贝后用于ProposalAutoMove.js文件
 */
function mouseDown() { 
  if (event.offsetY>adjustY && event.x<(screen.availWidth-40)) {   
    showFlag = !showFlag;
    
    centerPic.innerHTML = 'try { if (objName == "") { goToPic(' 
                          + top.pic_place
                          + '); top.fraPic.scrollTo('
                          + topX + ', ' + topY 
                          + '); showPosition('
                          + (startX+adjustX) + '+hx, ' 
                          + (startY+adjustY) + '+hy, '
                          + (endX-startX) + ', ' 
                          + (endY-startY)
                          + '); } } catch(e) {} ';
                          
    startX = event.offsetX; 
    startY = event.offsetY; 
    
    topX = event.offsetX - event.x + 10;
    topY = event.offsetY - event.y + 31;

    showCoordinate();
  }
}

/**
 * 在状态栏显示坐标信息
 */
function showCoordinate() {
  top.status = ' startX=' + startX 
             + ' startY=' + startY 
             + ' endX=' + endX 
             + ' endY=' + endY
             + ' XLength=' + (endX-startX) 
             + ' YLength=' + (endY-startY); 
}