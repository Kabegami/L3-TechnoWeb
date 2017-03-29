function map(){
    var text = this.text;
    var id = this.id;
    var words = text.match(/\w+/g);
    var tf = {};
    for (var i = 0; i < words.length; i++){
        if (tf[words[i]] == 0){
            tf[words[i]] = 1;
        }
        else {
            tf[words[i]] += 1;
        }
    }
    for (w in tf){
        var ret = {};
        ret[id] = tf[w];
        emit(w, ret);
    }
}

function reduce(key, values){
    var ret = {};
    for (var i = 0; i < values.length; i++){
        for (var d in values[i]){
            ret[d] = values[i][d];
        }
    }
    return ret;
}

function finalize(key, values){
    var df = Object.keys(v).length;
    for (d in values){
        values[d] = values[d] - Math.log(N/df);
    }
    return values;
}