/**
 * @class
 * @scope public
 */
function KeywordSearchSuggestions() {}



/**
 * Request suggestions for the given autosuggest control. 
 * @scope protected
 * @param oAutoSuggestControl The autosuggest control to provide suggestions for.
 */

KeywordSearchSuggestions.prototype.requestSuggestions = function (oAutoSuggestControl /*:AutoSuggestControl*/,
                                                          bTypeAhead /*:boolean*/) {

    var aSuggestions = [];
    var sTextboxValue = oAutoSuggestControl.textbox.value;

    if(sTextboxValue.length > 0) {
        var url = "/eBay/suggest?q=" + encodeURI(sTextboxValue);

        var req = new XMLHttpRequest();
        req.open("GET", url);
        req.onreadystatechange = function() {
            if(req.readyState == 4 && req.status == 200) {
                var suggestions = req.responseXML.getElementsByTagName("suggestion");

                for(var i=0; i<suggestions.length; i++) {
                    aSuggestions.push(suggestions[i].getAttribute("data"));
                }
                //provide suggestions to the control
                oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead);
            }
        }
        req.send(null);
    }
};
