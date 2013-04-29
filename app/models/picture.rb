# -*- encoding: utf-8 -*-
# 用于滚动图片
class Picture
  include DataMapper::Resource
  include DataMapper::Searcher

  property :id, Serial

  property :desc, String #图片描述
  property :href, String #链接地址
  property :created_at, DateTime #时间

  has n, :attachments

end
