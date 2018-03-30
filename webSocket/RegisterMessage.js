/**
	@author liujie
	@desc 封装 RegisterMessage
*/
var RegisterMessage = (function(window) {
    var RegisterMessage = function(message) {
        return new RegisterMessage.fn.init(message);
    }

    RegisterMessage.fn = RegisterMessage.prototype = {
        constructor: RegisterMessage,
        init: function(message) {
        	this.type = 102; //1
            this.message = message; //message.length
            this.messageLength = message.length
            this.toArray = function() {
                return this.initRegisterMessage();
            }
        },
        initRegisterMessage: function() {
            var sendBuffer = new Array();
			sendBuffer[0] = this.type;
			var messageBufferLength = int2Byte(this.messageLength);
			sendBuffer = sendBuffer.concat(messageBufferLength);
			var messageBuffer = str2UTF8(this.message);
			sendBuffer = sendBuffer.concat(messageBuffer);
			//sendBuffer
			return sendBuffer;
        }
    }

    RegisterMessage.fn.init.prototype = RegisterMessage.fn;

    return RegisterMessage;
})();
