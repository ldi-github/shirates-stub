$(function () {

        // Sets highlight for active profile
        function refreshHighlight() {
            var url = new URL(window.location.href)
            var profileParam = url.searchParams.get('profile')

            $(`tr[data-instance-profile-row]`).each(function () {
                var tr = this
                var profile = $(tr).attr("data-profile")
                if (profile === profileParam) {
                    $(tr).find("span").addClass("selected-pattern")
                    $(tr).attr("data-selected", true)
                } else if (profile === "default" && (profileParam === null || profileParam === "")) {
                    $(tr).find("span").addClass("selected-pattern")
                    $(tr).attr("data-selected", true)
                } else {
                    $(tr).find("span").removeClass("selected-pattern")
                    $(tr).removeAttr("data-selected")
                }
            })
        }

        // Refresh urls for test control
        function refreshUrl() {

            var profile = getSelectedProfile()
            var query = ""
            if (profile === "default") {
                query = ""
            } else if (profile !== null) {
                query = `?profile=${profile}`
            }

            var $a = undefined
            $a = $("a#data-pattern-changer")
            $a.attr("href", `/management/dataPatternChanger${query}`)
            $a.text(`/management/dataPatternChanger${query}`)

            $a = $("a#get-instance-info")
            $a.attr("href", `/management/getInstanceInfo${query}`)
            $a.text(`/management/getInstanceInfo${query}`)

            $a = $("a#reset-instance")
            $a.attr("href", `/management/resetInstance${query}`)
            $a.text(`/management/resetInstance${query}`)

            $a = $("a#remove-instance")
            $a.attr("href", `/management/removeInstance${query}`)
            $a.text(`/management/removeInstance${query}`)
        }

        // Gets selected instanceKey
        function getSelectedInstanceKey() {

            return $("#instance-profile-table tr[data-selected='true']").attr("data-instance-key")
        }

        // Gets selected profile
        function getSelectedProfile() {

            return $("#instance-profile-table tr[data-selected='true']").attr("data-profile")
        }

        // On click (select button)
        $("button[data-select-button]").on("click", function (e) {
            e.preventDefault()
            var origin = window.location.origin
            var pathname = window.location.pathname
            var baseUrl = `${origin}${pathname}`

            var profile = $(this).parent().parent().attr("data-profile")
            if (profile === "" || profile === "default") {
                window.location.href = `${baseUrl}`
            } else {
                window.location.href = `${baseUrl}?profile=${profile}`
            }
        })

        // On click
        $("a#data-pattern-changer,a#get-instance-profile-map,a#get-instance-info,a#reset-instance").on("click", function (e) {
            e.preventDefault();

            var href = $(this).attr("href")
            window.open(`http://${window.location.host}${href}`, '_blank')
        })
        // On click(remove-instance)
        $("a#remove-instance").on("click", function (e) {
            e.preventDefault();

            var href = $(this).attr("href")
            window.open(`http://${window.location.host}${href}`, '_blank')
            setTimeout(function () {
                window.location.href = `http://${window.location.host}${window.location.pathname}`
            }, 1000)
        })

        // On click(register-instance-button)
        $("button#register-instance-button").on("click", function (e) {
            e.preventDefault();

            var instanceKey = $("input#input-instanceKey").val()
            if (instanceKey === "") {
                alert("Input instanceKey.")
                return
            }
            var profile = $("input#input-profile").val()
            if (profile === "") {
                alert("Input profile.")
                return;
            }

            var host = window.location.host
            var requestUrl = `http://${host}/management/registerInstance`
            $.get(requestUrl, {instanceKey: instanceKey, profile: profile}, function (data) {
            });

            setTimeout(function () {
                window.location.href = `http://${window.location.host}${window.location.pathname}?profile=${profile}`
            }, 1000)
        })

        refreshHighlight()
        refreshUrl()
    }
)
;
