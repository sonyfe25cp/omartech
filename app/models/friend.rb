# -*- encoding: utf-8 -*-
class Friend
  include DataMapper::Resource
  include DataMapper::Searcher

  property :id, Serial
  property :name, String
  property :link, String
  property :description, String

  has 1, :attachment

end
