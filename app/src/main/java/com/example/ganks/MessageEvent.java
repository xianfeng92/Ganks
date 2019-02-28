package com.example.ganks;

/**
 * Created By zhongxianfeng on 19-2-28
 * github: https://github.com/xianfeng92
 */
public class MessageEvent<E> {
    private E message;

    public  MessageEvent(E message){
        this.message=message;
    }

    public MessageEvent(){
    }

    public E getMessage() {
        return message;
    }

    public void setMessage(E message) {
        this.message = message;
    }
}
