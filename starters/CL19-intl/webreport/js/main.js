$.getJSON( "report/report.json", function( data ) {
    console.log(data)

    $("#appName").html(data.appName)
    $("#staticNameAPK").val(data.apkName)
    $("#outputFolder").val(data.outputFolder)
    $("#emulatorName").val(data.emulatorName)
    $("#alpha").val(data.alpha)
    $("#dfltLang").val(data.dfltLang)
    $("#hardcodedStrings").val(data.hardcoded)

    var amountIPFS = 0
    for(var key in data.langsReport){
        if(data.langsReport[key].amIPFs){
            amountIPFS+=data.langsReport[key].amIPFs
        }
    }
    // console.log(amountIPFS)

    $("#amIPFS").val(amountIPFS)
    for(var key in data.langsReport){
        if(data.langsReport[key].dflt){
            $("#container").append("<div class=\"row card\"><div class=\"card-header\"><h2>"+data.langsReport[key].lang+"</h2></div><div class=\"card-body\"><form><div class=\"form-group row\"><label for=\"amStates\" class=\"col-sm-4 col-form-label\">Amount States</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"amStates\" value="+data.langsReport[key].amStates+"></h3></div><label for=\"amTrans\" class=\"col-sm-4 col-form-label\">Amount Transitions</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"amTrans\" value="+data.langsReport[key].amTrans+"></h3></div></div></form></div></div>")
        } else {
        }
    }

    for(var key in data.langsReport){
        if(data.langsReport[key].dflt){
        } else {
            if(data.langsReport[key].amIPFs){
                $("#container").append("<div class=\"row card\"><div class=\"card-header\"><h2>"+data.langsReport[key].lang+"</h2></div><div class=\"card-body\"><form><div class=\"form-group row\"><label for=\"amStates\" class=\"col-sm-4 col-form-label\">Amount States</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"amStates\" value="+data.langsReport[key].amStates+"></h3></div><label for=\"amTrans\" class=\"col-sm-4 col-form-label\">Amount Transitions</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"amTrans\" value="+data.langsReport[key].amTrans+"></h3></div></div><div class=\"form-group row\"><label for=\"mpds\" class=\"col-sm-4 col-form-label\">Missing Paired Default States</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"mpds\" value="+data.langsReport[key].missingDfltStates+"></h3></div><label for=\"mpls\" class=\"col-sm-4 col-form-label\">Missing Paired Translated States</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"mpls\" value="+data.langsReport[key].missingLangStates+"></h3></div></div><div class=\"form-group row\"><label for=\"mpds\" class=\"col-sm-4 col-form-label\">Amount IPFs</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"mpds\" value="+data.langsReport[key].amIPFs+"></h3></div></div></form></div></div>")
            } else {
                $("#container").append("<div class=\"row card\"><div class=\"card-header\"><h2>"+data.langsReport[key].lang+"</h2></div><div class=\"card-body\"><form><div class=\"form-group row\"><label for=\"amStates\" class=\"col-sm-4 col-form-label\">Amount States</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"amStates\" value="+data.langsReport[key].amStates+"></h3></div><label for=\"amTrans\" class=\"col-sm-4 col-form-label\">Amount Transitions</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"amTrans\" value="+data.langsReport[key].amTrans+"></h3></div></div><div class=\"form-group row\"><label for=\"mpds\" class=\"col-sm-4 col-form-label\">Missing Paired Default States</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"mpds\" value="+data.langsReport[key].missingDfltStates+"></h3></div><label for=\"mpls\" class=\"col-sm-4 col-form-label\">Missing Paired Translated States</label><div class=\"col-sm-2\"><h3><input type=\"text\" readonly class=\"form-control-plaintext\" id=\"mpls\" value="+data.langsReport[key].missingLangStates+"></h3></div></div></form></div></div>")
            }

        }
    }
  });