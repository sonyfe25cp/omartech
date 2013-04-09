# -*- encoding: utf-8 -*-
class Post
  include DataMapper::Resource
  include DataMapper::Searcher

  property :id, Serial

  property :created_at, DateTime #时间
  property :desc, String #图片描述
  property :image_url, String

  belongs_to :case

end
