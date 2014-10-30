class Post
  include DataMapper::Resource
  include DataMapper::Searcher

  property :id, Serial
  property :content, Text
  property :created_at, DateTime

  

end
