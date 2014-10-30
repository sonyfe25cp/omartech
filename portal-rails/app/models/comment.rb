# -*- encoding: utf-8 -*-
class Comment

  include DataMapper::Resource
  include DataMapper::Searcher


  property :id, Serial
  property :content, Text
  property :created_at, DateTime, default: Time.new

  belongs_to :post


end
