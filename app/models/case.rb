# -*- encoding: utf-8 -*-
class Case
  include DataMapper::Resource
  include DataMapper::Searcher

  property :id, Serial

  property :costomer_name, String #客户名
  property :costomer_address, String #客户名
  property :created_at, DateTime #时间
  property :time_last, Integer #时间,单位是天

  property :image_url, String #封面图片

  has n, :pictures


end
