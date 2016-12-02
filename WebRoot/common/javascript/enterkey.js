function onenterkeydown() 
{
    var keycode = event.keyCode;
    var currElement=event.srcElement;
if (keycode == "13") 
{
        for(tmpa=0;tmpa<fm.length;tmpa++)
        {
            if(fm[tmpa].name==currElement.name)
            {
                id=tmpa;
            }
        }
            fm[id+1].focus();

 }
    
}