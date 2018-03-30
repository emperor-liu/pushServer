
/**
    reauest params init 
*/
function getRequestParams(){
    var requestParams = {};
    for(var i=0;i<arguments.length;i++){
        requestParams[arguments[i]]=arguments[i+1];
        i++;
    }
    return requestParams;
}

function str2UTF8(str){  
    var bytes = new Array();   
    var len,c;  
    len = str.length;  
    for(var i = 0; i < len; i++){  
        c = str.charCodeAt(i);  
        if(c >= 0x010000 && c <= 0x10FFFF){  
            bytes.push(((c >> 18) & 0x07) | 0xF0);  
            bytes.push(((c >> 12) & 0x3F) | 0x80);  
            bytes.push(((c >> 6) & 0x3F) | 0x80);  
            bytes.push((c & 0x3F) | 0x80);  
        }else if(c >= 0x000800 && c <= 0x00FFFF){  
            bytes.push(((c >> 12) & 0x0F) | 0xE0);  
            bytes.push(((c >> 6) & 0x3F) | 0x80);  
            bytes.push((c & 0x3F) | 0x80);  
        }else if(c >= 0x000080 && c <= 0x0007FF){  
            bytes.push(((c >> 6) & 0x1F) | 0xC0);  
            bytes.push((c & 0x3F) | 0x80);  
        }else{  
            bytes.push(c & 0xFF);  
        }  
    }  
    return bytes;  
}  
function char2buf(str){
    var out = new ArrayBuffer(str.length*2);
    var u16a = new Uint16Array(out);
    var strs = str.split("");
    for(var i = 0 ; i< strs.length ; i ++){
        u16a[i] = strs[i].charCodeAt();
    }
    return u16a;
}
 
function buf2char(buf){
    var out="";
    var u16a = new Uint16Array(buf);
    var single ;
    for(var i=0 ; i < u16a.length;i++){
        single = u16a[i].toString(16)
        while(single.length<4) single = "0".concat(single);
        out+="\\u"+single;
    }
    return eval("'"+out+ "'");
}
/**
    int ==>> byte[]
*/
function int2Byte(num){
    var myArray=new Array(4)
    myArray[0] = (num >>> 24) & 0xff;
    myArray[1] = (num >>> 16)& 0xff ;  
    myArray[2] = (num >>> 8) & 0xff ;  
    myArray[3] = (num >>> 0) & 0xff ;  
    return myArray;
}