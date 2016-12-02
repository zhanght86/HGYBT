
<script> 
var easyScanWin = window;
initEasyScan(easyScanWin);


function initEasyScan(easyScanWin) {
  try {
    if (typeof(easyScanWin.top.fraInterface.divButton) == "undefined" 
        || typeof(easyScanWin.top.fraInterface.spanPic) == "undefined") {
      var funName = "initEasyScan(" + easyScanWin + ")";
      alert(funName);
      setTimeout(funName);
    } else {
      easyScanWin.top.fraInterface.divButton.style.display = "none"; 
      //easyScanWin.spanPic.style.display = "";
      //var innerHTML = "<center><INPUT id=butApprove VALUE=Approve TYPE=button onclick='approvePol(\"" + polNo + "\")'>"; 
      //easyScanWin.spanPic.innerHTML = innerHTML;
    }
  } catch(ex) {
    top.alert("initEasyScan" + ex);
  }
}

</script>