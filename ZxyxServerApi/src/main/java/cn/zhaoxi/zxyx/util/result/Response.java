package cn.zhaoxi.zxyx.util.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response {
    private Integer code = 0;
    private String msg;
    private Object data;

    public Response() {}
    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(ExceptionMsg msg){
        this.code=msg.getCode();
        this.msg=msg.getMsg();
    }

    public void setResponse(ExceptionMsg exMsg) {
        code = exMsg.getCode();
        msg = exMsg.getMsg();
    }

    public static Response success() {
        return new Response(ExceptionMsg.SUCCESS);
    }

    public static Response paramIsNull() {
        return new Response(ExceptionMsg.ParamIsNull);
    }
}
