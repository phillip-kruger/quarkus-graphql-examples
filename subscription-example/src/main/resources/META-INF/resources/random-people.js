import {html, render} from 'https://cdn.jsdelivr.net/npm/lit-html@1.3.0/lit-html.min.js';

var container;

var request = `{
                "query" : "subscription randomPerson \{randomPerson \{id names\}\}","operationName": "randomPerson"\}
           }`; 


let webSocket = null;
           
const connectHandler = {
  // handleEvent method is required.
  handleEvent(e) { 
    if(webSocket != null){
        console.log("Closing the old websocket connection");
        disconnect();
    }
    
    var new_uri = getWsUrl();           
    webSocket = new WebSocket(new_uri);    
    
    webSocket.onopen = function(event) {
        console.error("WebSocket open observed:", event);
        webSocket.send(request);
        container.getElementById("btnConnect").disabled = true;
        container.getElementById("btnDisconnect").disabled = false;
    };
    webSocket.onmessage = function (event) {
        console.error("WebSocket message observed:", event);
        
        var d = event.data;
        console.log("Message = " + d);
        render(template(d), container);
    };
    webSocket.onerror = function(err) {
        console.error("WebSocket error observed:", err);
        var e = err.type;
        render(template(e), container);
    };
    webSocket.onclose = function(event) {
        console.log("WebSocket close observed:", event);
        disconnect();
    };
    return {
        unsubscribe() {
            console.log("WebSocket unsubscribed observed:", event);
            disconnect();
        }
    };
  },
  // event listener objects can also define zero or more of the event 
  // listener options: capture, passive, and once.
  capture: true,
};           




const disconnectHandler = {
  // handleEvent method is required.
  handleEvent(e) { 
      console.log("Disconnect button has been clicked:", e);
      disconnect();
  }
}

function connect(){
    
}

function disconnect(){
    if(webSocket !== null){
        webSocket.close();
    }
    webSocket = null;
    container.getElementById("btnConnect").disabled = false;
    container.getElementById("btnDisconnect").disabled = true;
}

const style = html`
<style>
    #btnConnect {
        background: green;
    }
    #btnDisconnect {
        background: red;
    }
    #result {
        background: black;
        padding: 20px;
        color: white;
    }
</style>
`;

const button = html`
<button id="btnConnect" @click=${connectHandler}>
    Connect
</button>

<button id="btnDisconnect" @click=${disconnectHandler} disabled>
    Disconnect
</button>
`;

const template = (data) => html`
  ${style}
  ${button} <br/><br/>
  <p>
  <samp id="result">
    <code>
    ${data}
    </code>
  </samp>
  </p>
`;



class RandomPeople extends HTMLElement {

    constructor() {
        super();
        this.root = this.attachShadow({mode: 'open'});
        container = this.root;
    }
    
    connectedCallback(){
        render(template(), this.root);
    }
}

function getWsUrl(){
    const api = '/graphql';
    var new_uri;
    if (window.location.protocol === "https:") {
        new_uri = "wss:";
    } else {
        new_uri = "ws:";
    }
    new_uri += "//" + window.location.host + api;

    return new_uri;
}



customElements.define("random-people", RandomPeople);
