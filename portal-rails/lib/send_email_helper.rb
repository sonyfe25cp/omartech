# -*- encoding: utf-8 -*-
module SendEmailHelper
  def send_new_order_email(order_id)
    if Rails.env.production?
      Thread.start{
        recipient = '822891789@qq.com'
        subject ="打印铺(NoPrinter.cn)系统中有新的订单:"+order_id.to_s+"，请及时确认" + Time.now.to_s
        message = "老板，系统有一份新订单，请及时确认。<a href='http://noprinter.cn/printer/orders/"+order_id.to_s+"' target='_blank'>点这里查看</a>"
        Emailer.contact(recipient, subject, message).deliver
      }
    else
      Thread.start{
        recipient = '284198757@qq.com'
        subject ="打印铺(NoPrinter.cn)系统中有新的订单:"+order_id.to_s+"，请及时确认" + Time.now.to_s
        message = "老板，系统有一份新订单，请及时确认。<a href='http://e.omartech.com/printer/orders/"+order_id.to_s+"' target='_blank'>点这里查看</a>"
        Emailer.contact(recipient, subject, message).deliver
      }
    end
  end
end
