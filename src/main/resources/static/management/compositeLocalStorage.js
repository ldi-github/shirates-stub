
var compositeLocalStorage = new Object()

compositeLocalStorage.getCompositeKey = function(group, key) {

    var k = `[${group}]:${key}`
    return k
}

compositeLocalStorage.setItem = function(group = "", key, value) {

    var k = compositeLocalStorage.getCompositeKey(group, key)
    var v = JSON.stringify(value)
    localStorage.setItem(k, v)
}

compositeLocalStorage.getItem = function(group, key) {

    var k = compositeLocalStorage.getCompositeKey(group, key)
    var v = localStorage.getItem(k)
    if(v == "undefined") {
        return null
    }
    var j = JSON.parse(v)
    return j
}

compositeLocalStorage.removeItem = function(group, key) {

    var k = compositeLocalStorage.getCompositeKey(group, key)
    localStorage.removeItem(k)
}

compositeLocalStorage.clear = function(group) {

    if(typeof group == "undefined") {
        localStorage.clear()
        return
    }

    var k = compositeLocalStorage.getCompositeKey(group, "")
    for(var i = 0; i<localStorage.length; i++) {
        var sk = localStorage.key(i)
        if(sk.startsWith(k)){
            localStorage.removeItem(sk)
        }
    }
}

compositeLocalStorage.hasItem = function(group, key) {

    var k = compositeLocalStorage.getCompositeKey(group, key)
    for(var i = 0; i<localStorage.length; i++) {
        var sk = localStorage.key(i)
        if(sk == k) {
            return true
        }
    }
    return false
}

compositeLocalStorage.getMap = function(group) {

    var m = {}
    var k = compositeLocalStorage.getCompositeKey(group, "")
    for(var i = 0; i<localStorage.length; i++) {
        var sk = localStorage.key(i)
        if(sk.startsWith(k)) {
            var j = localStorage.getItem(sk)
            var v = JSON.parse(j)
            var key = sk.replace(k, "")
            m[key] = v
        }
    }

    return m
}

