package cn.util;


public class ErrorInfo
{
  public String msg;
  public int code;
  public String FRIEND_INFO = "亲、由于系统繁忙。";
  public String PROCESS_INFO = "系统已把错误信息发送到后台管理员,会尽快的处理,给您带来的不便,敬请原谅。";

  public ErrorInfo() {
    this.code = 0;
    this.msg = "";
  }

  public ErrorInfo(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public void clear() {
    this.code = 0;
    this.msg = "";
  }

  public String toString()
  {
    return "ErrorInfo [msg=" + this.msg + ", code=" + this.code + ", FRIEND_INFO= " + this.FRIEND_INFO + ", PROCESS_INFO=" + this.PROCESS_INFO + "]";
  }
}