class Attachment
  include DataMapper::Resource
  include DataMapper::Searcher

  property :id, Serial
  property :task_type, Enum[:images, :attachments]
  property :content, Text
  property :created_at, DateTime, default: Time.new

  mount_uploader :source, AttachmentUploader
  
end
