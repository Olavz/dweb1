<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

    <title>Hello, world!</title>
</head>
<body>


<div class="container">
    <h1>Web Client</h1>

    <div class="row">
        <div class="col-sm">
            <div class="form-group">
                <label for="txtFromServer">Messages from server:</label>
                <textarea disabled="disabled" class="form-control" id="txtFromServer" rows="10"></textarea>
            </div>
            <div class="form-group">
                <input type="text" class="form-control" id="txtInput" placeholder="<message and hit enter>" onkeydown="send(this)">
            </div>
        </div>
    </div>

    <div class="row">
        <a href="http://localhost:8888/webhook" target="_blank">"Webhook"</a>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<script type="text/javascript" src="wsclient.js"></script>
<script type="text/javascript">

    var client = new WebSocketClient('ws', '127.0.0.1', 8888, '/endpoint');
    client.connect();

    var webSocket = client.getWebSocket();
    webSocket.onmessage = function (event) {
        var msg = event.data;
        var txtFromServer = $("#txtFromServer");
        txtFromServer.append(msg + "\n");
        txtFromServer.scrollTop(txtFromServer[0].scrollHeight);
    }

    webSocket.onclose = function (event) {
        txtFromServer.append("Disconnected.\n");
        txtFromServer.scrollTop(txtFromServer[0].scrollHeight);
    }

    webSocket.onopen = function (event) {
        txtFromServer.append("Connected.\n");
        txtFromServer.scrollTop(txtFromServer[0].scrollHeight);
    }

    function send(ele) {
        if(event.key === 'Enter') {
            client.send(ele.value);
            ele.value = "";
        }
    }

</script>
</body>
</html>
