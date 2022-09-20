$(function(){

    // Set the background color for active data pattern name to highlight color.
    function refreshHighlight(urlPath) {
        $(`tr[data-url-path='${urlPath}']`).each(function(){
            var tr = this
            $(tr).find(`#data-pattern-name`).children().each(function(){
                var e = this
                $(e).removeClass("selected-pattern")
                var dataPatternName = $(tr).attr("data-data-pattern-name")
                var activePattern = $(tr).attr("data-active-pattern")
                if(dataPatternName == activePattern){
                    $(e).addClass("selected-pattern")
                }
            })

            var subNo = $(tr).attr("data-sub-no")
            if(subNo=="1"){
                $(tr).find("#active-data-pattern").addClass("selected-pattern")
            }
        })
    }

    // Open or close API groups
    function openCloseGroup(urlPath, open) {
        if(typeof open === 'undefined'){
            var isOpen = ($(`tr[data-url-path='${urlPath}']`).attr("data-is-open") == "true")
            open = !isOpen
        }
        compositeLocalStorage.setItem("api-group-open", urlPath, open)

        var $trs = $(`tr[data-url-path='${urlPath}']`)
        $trs.each(function(index){
            var $tr = $(this)
            $tr.attr("data-is-open", open)

            // Switch active data pattern between link mode and label mode.
            if(open) {
                $tr.find("#set-data-pattern").show()    // link
                $tr.find("#active-data-pattern").hide() // label
            } else {
                $tr.find("#set-data-pattern").hide()    // link
                $tr.find("#active-data-pattern").show() // label
            }

            var subNo = $(this).attr("data-sub-no")

            // bookmark
            var $bookmark = $tr.find(".bookmark-button")
            if(subNo != 1) {
                $bookmark.hide()
            }

            // button
            var $button = $tr.find(".open-close-button")
            if(subNo == "1") {
                if($trs.length == 1) {
                    $button.hide()
                }
            } else {
                $button.hide()
            }

            if(subNo == '1'){
                if(open){
                    $button.text("Close")
                } else {
                    $button.text("Open")
                }
            } else if(subNo != '1'){
                if(open){
                    $tr.removeClass("row-close")
                } else {
                    $tr.addClass("row-close")
                }
            }
        })
    }

    // Get URL-Path list
    function getUrlPaths() {
        var urls = $(`tr[data-url-path][data-sub-no='1']`).map(function(){
            var urlPath = $(this).attr("data-url-path")
            return urlPath
        })
        return urls.get()
    }

    // Get urlPath for the data pattern
    function getDataPatterns(urlPath) {
        var dataPatterns = $(`tr[data-url-path='${urlPath}']`).map(function(){
            var dataPattern = $(this).attr("data-data-pattern-name")
        })
        return dataPatterns.get()
    }

    // On click (Normal URL-Path)
    $("[data-url-path-normal]").on("click",function(e){
        e.preventDefault();
        var host = this.host
        var href = $(this).attr("href")

        window.open(`http://${host}${href}`,'_blank')
    })

    // On click (URL-Path for decryption)
    $("[data-url-path-decrypted]").on("click",function(e){
        e.preventDefault();
        var host = this.host
        var href = $(this).attr("href")

        window.open(`http://${host}${href}`,'_blank')
    })

    // On click (Data pattern name)
    $("a[data-data-pattern-name]").on("click",function(e){
        e.preventDefault();

        var host = this.host
        var requestUrl = `http://${host}/management/setDataPattern`
        var urlPath = $(this).parent().parent().attr("data-url-path")
        var dataPatternName = $(this).attr("data-data-pattern-name")
        $.get(requestUrl,{ urlPath: urlPath, dataPatternName: dataPatternName }, function(data){
        });

        setSelected(urlPath, dataPatternName)
        refreshHighlight(urlPath)
    });

    // Set data pattern of the urlPath
    function setSelected(urlPath, dataPatternName) {

        var $trs = $(`tr[data-url-path='${urlPath}']`)
        var $trSelected = $trs.filter(`[data-data-pattern-name='${dataPatternName}']`)
        var $tr1 = $trs.filter("[data-sub-no='1']")

        // dataPatternName
        $trs.attr("data-active-pattern", dataPatternName)
        $trs.find("#active-data-pattern").text(dataPatternName)
        $(`tr[data-url-path='${urlPath}']`).find(".selected-pattern").removeClass("selected-pattern")
        $trSelected.find("#set-data-pattern").addClass("selected-pattern")
        $trSelected.find("#active-data-pattern").addClass("selected-pattern")
    }

    // Filter bookmark
    function filterBookmark(isFilterOn) {

        var button = $("#bookmarkFilterButton")

        if(isFilterOn) {
            $(button).addClass("bookmark-filter-on")
            $(button).text("ON")
        } else {
            $(button).removeClass("bookmark-filter-on")
            $(button).text("OFF")
        }

        // row-bookmark-on/row-bookmark-hidden
        $trs = $("#main-table tr:not(.headerrow)")
        $trs.each(function(e) {
            var $tr = $(this)
            if(!$tr.hasClass("row-bookmark-on")) {
                if(isFilterOn) {
                    $tr.addClass("row-bookmark-hidden")
                } else {
                    $tr.removeClass("row-bookmark-hidden")
                }
            }
        })

        compositeLocalStorage.setItem("bookmark-filter", "on", isFilterOn)
    }

    // On click (bookmarkFilterButton)
    $("#bookmarkFilterButton").on("click", function(e){

        var isFilterOn = $(this).hasClass("bookmark-filter-on")
        filterBookmark(!isFilterOn)
    })

    // On click (openAllButton)
    $("#openAllButton").on("click", function(e){
        $(`#main-table tr[data-api-name][data-sub-no!='1']`).each(function(index){
            var urlPath = $(this).attr("data-url-path")
            openCloseGroup(urlPath, true)
        })
    })

    // On click (closeAllButton)
    $("#closeAllButton").on("click", function(e){
        $(`#main-table tr[data-api-name][data-sub-no!='1']`).each(function(index){
            var urlPath = $(this).attr("data-url-path")
            openCloseGroup(urlPath, false)
        })
    })

    // On click (bookmark-button)
    $(".bookmark-button").on("click", function(e){
        var urlPath = $(this).parent().parent().attr("data-url-path")
        $trs = $(`tr[data-url-path='${urlPath}']`)

        if($(this).hasClass("bookmark-checked")) {
            $(this).removeClass("bookmark-checked")
            $trs.removeClass("row-bookmark-on")
            compositeLocalStorage.setItem("bookmark", urlPath, false)
        } else {
            $(this).addClass("bookmark-checked")
            $trs.addClass("row-bookmark-on")
            compositeLocalStorage.setItem("bookmark", urlPath, true)
        }
    })

    // On click (open/close)
    $(".open-close-button").on("click", function(e){
        var urlPath = $(this).parent().parent().attr("data-url-path")
        openCloseGroup(urlPath)
        refreshHighlight(urlPath)
    });

    // Highlight color, API group open/close
    var urlPaths = getUrlPaths()
    urlPaths.forEach(function(urlPath){
        refreshHighlight(urlPath)
        var isOpen = compositeLocalStorage.getItem("api-group-open", urlPath)
        if(isOpen == null){
            isOpen = false
        }
        openCloseGroup(urlPath, isOpen)
    })

    // Resume bookmark
    var bookmarkMap = compositeLocalStorage.getMap("bookmark")
    for(urlPath in bookmarkMap) {
        var v = bookmarkMap[urlPath]
        if(v == true) {
            $trs = $(`tr[data-url-path='${urlPath}']`)
            $trs.addClass("row-bookmark-on")
            $trs.find(".bookmark-button").each(function(){
                $(this).addClass("bookmark-checked")
            })
        }
    }

    // Resume bookmark filter
    var isBookmarkFilterOn = compositeLocalStorage.getItem("bookmark-filter", "on")
    filterBookmark(isBookmarkFilterOn)

});
