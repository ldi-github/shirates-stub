$(function(){

    // clear localStorage
    $("#clearLocalStorageButton").on("click",function(e){

        localStorage.clear()
        alert("localStorage cleared")
    });

    // url -> file name
    function convertUrlToFileName(url, encode) {

        if(!url.trim()) {
            return ""
        }

        var fileName = url.replaceAll("https://","").replaceAll("http://","")
        fileName = fileName.replaceAll("/","~")
        fileName = fileName.replaceAll(":","=")
        fileName = fileName + "..yyyyMMdd HHmmss.SSS"

        if(encode == true){
            fileName = fileName + ".enc"
        } else {
            fileName = fileName + ".plain"
        }

        if(url.endsWith(".json")){
            fileName = fileName + ".json"
        } else {
            fileName = fileName + ".txt"
        }

        return fileName
    }

    // url -> file name
    $("#source-url").change(function(e) {
        var url = $(this).val()
        var encSelected = $("#enc-button").prop("checked")
        var fileName = convertUrlToFileName(url, encSelected)
        $("#dest-filename").val(fileName)
    })

    // on click [Plain]
    $("#plain-button").on("click",function(e){

        $("#source-url").trigger("change")
    })

    // on click [Enc]
    $("#enc-button").on("click",function(e){

        $("#source-url").trigger("change")
    })

});
