function last() {
    $("html, body").animate({ scrollTop: $(document).height() }, "slow");
}



function openPeepProfile(profile){
    ;
}


function openGroup() {
    JSReceiver.openGroup();
}

function openChat() {
       JSReceiver.openChat();
}


function test() {
    alert("IIIIIIIIIIIIIIIItttttttttttttt  workssssssssssssssss!");
    $('#here').text("IIIIIIIIIIIIIIIIIIIt works DUUUUUUUDE");
}

function displaySentMessage(message, time) {
    var $message = $("#local-msg").clone();
    $("#chat").append($message);

    $("p.message", $message).text(message);

    $("p.time", $message).text(time);
    
    $message.show();

    last();
}

function displayChatMessages() {

}

function displayReceivedMessage(){

}

