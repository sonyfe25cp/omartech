# -*- encoding: utf-8 -*-
# 用于展示案例
class Case
  include DataMapper::Resource
  include DataMapper::Searcher

  property :id, Serial

  property :customer_name, String #客户名
  property :customer_address, String #客户网站地址
  property :description, Text #项目描述
  property :created_at, DateTime #时间
  property :time_last, Integer #时间,单位是天

  has n, :attachments


end
